package com.marius.urlShortener.services;

import com.marius.urlShortener.exception.ShortUrlNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UrlShortenerService {

    private static final String REDIS_KEY_PREFIX = "shorturl:";
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public String shortenUrl(String longUrl) {
        String shortUrl = generateShortUrl(longUrl);
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX + shortUrl, longUrl);
        return shortUrl;
    }

    public String getLongUrl(String shortUrl) {
        String longUrl = redisTemplate.opsForValue().get(REDIS_KEY_PREFIX + shortUrl);
        if (longUrl == null) {
            throw new ShortUrlNotFoundException("Short URL not found: " + shortUrl);
        }
        return longUrl;
    }

    private String generateShortUrl(String longUrl) {
        // You can use more sophisticated methods here.
        String encoded = Base64.getEncoder().encodeToString(longUrl.getBytes());
        return encoded.substring(0, 8); // Adjust the length of the short URL as needed.
    }
}
