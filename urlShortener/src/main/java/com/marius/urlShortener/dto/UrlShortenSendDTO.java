package com.marius.urlShortener.dto;

public class UrlShortenSendDTO {
    private String shortUrl;

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String longUrl) {
        this.shortUrl = longUrl;
    }
}
