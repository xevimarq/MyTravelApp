package com.example.mytravelapp;

public class Document {
    private String title;
    private String description;
    private String pdfFilePath;
    public Document(String title, String pdfFilePath) {
        this.title = title;
        this.pdfFilePath = pdfFilePath;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPdfFilePath() {
        return pdfFilePath;
    }

}

