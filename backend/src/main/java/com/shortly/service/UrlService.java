package com.shortly.service;

import com.shortly.dto.ShortenRequest;
import com.shortly.dto.ShortenResponse;
import com.shortly.entity.UrlMapping;
import com.shortly.exception.UrlNotFoundException;
import com.shortly.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.security.SecureRandom;
import java.util.Optional;

@Service
public class UrlService {

    private static final String CHARACTERS =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int SHORT_CODE_LENGTH = 6;

    private final UrlRepository urlRepository;
    private final ShortCodeGenerator shortCodeGenerator;
    private final SecureRandom random = new SecureRandom();
    private final String shortUrlBase;

    public UrlService(
            UrlRepository urlRepository,
            ShortCodeGenerator shortCodeGenerator,
            @Value("${app.short-url-base:http://localhost:8080}") String shortUrlBase
    ) {
        this.urlRepository = urlRepository;
        this.shortCodeGenerator = shortCodeGenerator;
        this.shortUrlBase = shortUrlBase.replaceAll("/+$", "");
    }

    @Transactional
    public ShortenResponse shortenUrl(ShortenRequest request) {
        if (request == null || request.longUrl() == null || request.longUrl().isBlank()) {
            throw new IllegalArgumentException("URL must not be blank");
        }

        String longUrl = request.longUrl().trim();
        URI uri;

        try {
            uri = URI.create(longUrl);
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("Please enter a valid URL");
        }

        String scheme = uri.getScheme();
        if (scheme == null
                || !(scheme.equalsIgnoreCase("http") || scheme.equalsIgnoreCase("https"))
                || uri.getHost() == null) {
            throw new IllegalArgumentException("URL must be a valid HTTP or HTTPS URL");
        }

        Optional<UrlMapping> existing = urlRepository.findByLongUrl(longUrl);
        if (existing.isPresent()) {
            return new ShortenResponse(shortUrlBase + "/" + existing.get().getShortCode());
        }

        String shortCode;
        do {
            shortCode = shortCodeGenerator.generate(SHORT_CODE_LENGTH, CHARACTERS, random);
        } while (urlRepository.existsByShortCode(shortCode));

        UrlMapping mapping = new UrlMapping();
        mapping.setLongUrl(longUrl);
        mapping.setShortCode(shortCode);

        UrlMapping saved = urlRepository.save(mapping);
        return new ShortenResponse(shortUrlBase + "/" + saved.getShortCode());
    }

    @Transactional
    public String getOriginalUrl(String shortCode) {
        UrlMapping mapping = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found"));

        mapping.setClickCount(
                mapping.getClickCount() == null ? 1L : mapping.getClickCount() + 1L
        );
        return mapping.getLongUrl();
    }
}
