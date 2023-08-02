package com.marius.urlShortener.services;

import com.marius.urlShortener.exception.ShortUrlNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Random;

@Service
public class UrlShortenerService {

    private static final String REDIS_KEY_PREFIX = "shorturl:";
    private static final int SHORT_URL_LENGTH = 8;

    private RedisTemplate<String, String> redisTemplate;
    private Random random;

    @Autowired
    public UrlShortenerService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.random = new Random();
    }

    public String shortenUrl(String longUrl) {
        String shortUrl = generateShortUrl();
        redisTemplate.opsForValue().set(REDIS_KEY_PREFIX + shortUrl, longUrl);
        return shortUrl;
    }

    public String getLongUrl(String shortUrl) throws ShortUrlNotFoundException {
        String longUrl = redisTemplate.opsForValue().get(REDIS_KEY_PREFIX + shortUrl);
        if (longUrl == null) {
            throw new ShortUrlNotFoundException("Short URL not found: " + shortUrl);
        }
        return longUrl;
    }

    private String generateShortUrl() {
        byte[] randomBytes = new byte[SHORT_URL_LENGTH];
        random.nextBytes(randomBytes);
        String encoded = Base64.getUrlEncoder().encodeToString(randomBytes);
        return encoded.substring(0, SHORT_URL_LENGTH);
    }
}
