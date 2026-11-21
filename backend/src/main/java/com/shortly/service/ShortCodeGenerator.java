package com.shortly.service;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class ShortCodeGenerator {

    public String generate(int length, String characters, SecureRandom random) {
        if (length <= 0 || characters == null || characters.isEmpty() || random == null) {
            throw new IllegalArgumentException("Invalid short-code generation parameters");
        }

        StringBuilder code = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }
}
