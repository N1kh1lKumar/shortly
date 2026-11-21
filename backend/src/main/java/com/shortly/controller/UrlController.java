
package com.shortly.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.shortly.dto.ShortenRequest;
import com.shortly.dto.ShortenResponse;
import com.shortly.service.UrlService;

import jakarta.validation.Valid;

@RestController

public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {

        this.urlService = urlService;
    }

    @PostMapping("/api/v1/url/shorten")
    @ResponseStatus(HttpStatus.CREATED)
    public ShortenResponse shortenUrl(@Valid @RequestBody ShortenRequest request) {
        return urlService.shortenUrl(request);
    }


    @GetMapping("/{shortCode:[a-zA-Z0-9]{6}}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode) {

        String originalUrl = urlService.getOriginalUrl(shortCode);

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(originalUrl))
                .build();
    }

}
