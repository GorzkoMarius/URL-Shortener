package com.marius.urlShortener.controller;

import com.marius.urlShortener.dto.UrlShortenRequestDTO;
import com.marius.urlShortener.exception.ShortUrlNotFoundException;
import com.marius.urlShortener.services.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public String shortenUrl(@Valid @RequestBody UrlShortenRequestDTO requestDTO) {
        return urlShortenerService.shortenUrl(requestDTO.getLongUrl());
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<String> redirectToLongUrl(@PathVariable String shortUrl) {
        try {
            String longUrl = urlShortenerService.getLongUrl(shortUrl);
            return ResponseEntity.status(HttpStatus.OK).body(longUrl);
        } catch (ShortUrlNotFoundException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }
}
