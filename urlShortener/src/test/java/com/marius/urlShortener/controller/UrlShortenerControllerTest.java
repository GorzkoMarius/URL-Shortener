package com.marius.urlShortener.controller;

import com.marius.urlShortener.controller.UrlShortenerController;
import com.marius.urlShortener.dto.UrlShortenRequestDTO;
import com.marius.urlShortener.exception.ShortUrlNotFoundException;
import com.marius.urlShortener.services.UrlShortenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UrlShortenerControllerTest {

    private MockMvc mockMvc;

    @Mock
    private UrlShortenerService urlShortenerService;

    @InjectMocks
    private UrlShortenerController urlShortenerController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(urlShortenerController).build();
    }

    @Test
    public void testShortenUrl() throws Exception {
        UrlShortenRequestDTO requestDTO = new UrlShortenRequestDTO();
        requestDTO.setLongUrl("https://www.example.com");

        String shortUrl = "ABC123"; // Sample shortened URL

        when(urlShortenerService.shortenUrl("https://www.example.com")).thenReturn(shortUrl);

        mockMvc.perform(MockMvcRequestBuilders.post("/shorten")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"longUrl\": \"https://www.example.com\"}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testRedirectToLongUrl() throws Exception {
        String shortUrl = "ABC123"; // Sample shortened URL
        String longUrl = "https://www.example.com";

        when(urlShortenerService.getLongUrl(shortUrl)).thenReturn(longUrl);

        mockMvc.perform(MockMvcRequestBuilders.get("/r/{shortUrl}", shortUrl))
                .andExpect(status().isFound()); // Should be redirected to long URL
    }

    @Test
    public void testShortUrlNotFound() throws Exception {
        String shortUrl = "INVALID"; // An invalid short URL

        when(urlShortenerService.getLongUrl(shortUrl)).thenThrow(new ShortUrlNotFoundException("Short URL not found"));

        mockMvc.perform(MockMvcRequestBuilders.get("/{shortUrl}", shortUrl))
                .andExpect(status().isNotFound()); // Should return 404 Not Found
    }
}
