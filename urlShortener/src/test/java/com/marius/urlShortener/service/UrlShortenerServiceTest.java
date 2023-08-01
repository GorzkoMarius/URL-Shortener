package com.marius.urlShortener.service;

import com.marius.urlShortener.exception.ShortUrlNotFoundException;
import com.marius.urlShortener.services.UrlShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class UrlShortenerServiceTest {

    @Mock
    private RedisTemplate<String, String> redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private UrlShortenerService urlShortenerService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);

        // Mock the RedisTemplate's opsForValue() method to return the mock ValueOperations
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
    }

    @Test
    public void testShortenUrl() {
        String longUrl = "https://www.example.com";
        String shortUrl = urlShortenerService.shortenUrl(longUrl);

        // Ensure that the RedisTemplate's opsForValue().set method is called with the correct arguments
        verify(valueOperations, times(1)).set("shorturl:" + shortUrl, longUrl);

        // You can also add more assertions based on your specific implementation
        assertEquals(8, shortUrl.length());
    }

    @Test
    public void testGetLongUrl() {
        String shortUrl = "ABC123"; // Sample shortened URL
        String longUrl = "https://www.example.com";

        when(valueOperations.get("shorturl:" + shortUrl)).thenReturn(longUrl);

        String actualLongUrl = urlShortenerService.getLongUrl(shortUrl);
        assertEquals(longUrl, actualLongUrl);

        // Ensure that the RedisTemplate's opsForValue().get method is called with the correct argument
        verify(valueOperations, times(1)).get("shorturl:" + shortUrl);
    }

    @Test
    public void testShortUrlNotFound() {
        String shortUrl = "INVALID"; // An invalid short URL

        when(valueOperations.get("shorturl:" + shortUrl)).thenReturn(null);

        // Ensure that the method throws a ShortUrlNotFoundException when the short URL is not found in Redis
        assertThrows(ShortUrlNotFoundException.class, () -> urlShortenerService.getLongUrl(shortUrl));

        // Ensure that the RedisTemplate's opsForValue().get method is called with the correct argument
        verify(valueOperations, times(1)).get("shorturl:" + shortUrl);
    }
}
