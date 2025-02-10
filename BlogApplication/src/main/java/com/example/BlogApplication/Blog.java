package com.example.BlogApplication;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "blogs")
public class Blog {

    @Id
    private String prodId;
    private String title;
    private String content;
    private String category;
    private byte[] imageData;
    private int randomId;


    public Blog() {
    }


    public Blog(String prodId, String title, String content, String category, byte[] imageData, int randomId) {
        this.prodId = prodId;
        this.title = title;
        this.content = content;
        this.category = category;
        this.imageData = imageData;
        this.randomId = randomId;
    }


    public String getProdId() { return prodId; }
    public void setProdId(String prodId) { this.prodId = prodId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public byte[] getImageData() {
        return imageData;
    }

    public void setImageData(byte[] imageData) {
        this.imageData = imageData;
    }

    public int getRandomId() {
        return randomId;
    }

    public void setRandomId(int randomId) {
        this.randomId = randomId;
    }
}
