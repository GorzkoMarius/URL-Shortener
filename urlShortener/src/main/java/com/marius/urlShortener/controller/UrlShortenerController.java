package com.marius.urlShortener.controller;

import com.marius.urlShortener.dto.UrlShortenRequestDTO;
import com.marius.urlShortener.dto.UrlShortenSendDTO;
import com.marius.urlShortener.exception.ShortUrlNotFoundException;
import com.marius.urlShortener.services.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@CrossOrigin("http://localhost:4200")
@RestController
public class UrlShortenerController {

    private static final String SHORT_URL_PREFIX = "http://localhost:8080/r/";
    @Autowired
    private UrlShortenerService urlShortenerService;

    //@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    @PostMapping("/shorten")
    public UrlShortenSendDTO shortenUrl(@Valid @RequestBody UrlShortenRequestDTO requestDTO) {
        UrlShortenSendDTO url = new UrlShortenSendDTO();
        url.setShortUrl(SHORT_URL_PREFIX + urlShortenerService.shortenUrl(requestDTO.getLongUrl()));
        return url;
    }

    //@CrossOrigin(origins = "http://localhost:4200", allowedHeaders = "*")
    @GetMapping("/r/{shortUrl}")
    public void redirectToLongUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        try {
            String longUrl = urlShortenerService.getLongUrl(shortUrl);
            response.sendRedirect(longUrl);
        } catch (ShortUrlNotFoundException e) {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Short URL not found: " + shortUrl);
        }
    }
}
