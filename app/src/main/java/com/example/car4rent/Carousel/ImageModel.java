package com.example.car4rent.Carousel;

public class ImageModel {
    private String imageUrl;
    private String description;

    public ImageModel(String imageUrl, String description) {
        this.imageUrl = imageUrl;
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getDescription() {
        return description;
    }
}
