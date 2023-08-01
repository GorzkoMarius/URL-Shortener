package com.marius.urlShortener.controller;

import com.marius.urlShortener.dto.UrlShortenRequestDTO;
import com.marius.urlShortener.exception.ShortUrlNotFoundException;
import com.marius.urlShortener.services.UrlShortenerService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public String shortenUrl(@Valid @RequestBody UrlShortenRequestDTO requestDTO) {
        return urlShortenerService.shortenUrl(requestDTO.getLongUrl());
    }

    @PostMapping(value = "/shorten", consumes = "multipart/form-data")
    public String shortenUrlFromFormData(@RequestParam("longUrl") @Valid String longUrl) {
        return urlShortenerService.shortenUrl(longUrl);
    }

    @GetMapping("/{shortUrl}")
    public void redirectToLongUrl(@PathVariable String shortUrl, HttpServletResponse response) throws IOException {
        String longUrl = urlShortenerService.getLongUrl(shortUrl);
        if (longUrl != null) {
            response.sendRedirect(longUrl);
        } else {
            response.sendError(HttpStatus.NOT_FOUND.value(), "Short URL not found: " + shortUrl);
        }
    }
}
