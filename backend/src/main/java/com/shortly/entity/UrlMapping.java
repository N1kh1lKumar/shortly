
package com.shortly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_mapping")
@Getter
@Setter
public class UrlMapping {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "long_url", length = 2048, nullable = false)
    private String longUrl;

    @Column(name = "short_code", length = 10, unique = true, nullable = false)
    private String shortCode;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "click_count")
    private Long clickCount;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }

        if (clickCount == null) {
            clickCount = 0L;
        }
    }
}
