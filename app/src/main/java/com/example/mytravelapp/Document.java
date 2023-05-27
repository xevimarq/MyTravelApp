package com.example.mytravelapp;

public class Document {
    private String title;
    private String description;
    private String pdfFilePath;

    // Constructor sin argumentos
    public Document() {
        // Constructor vacío requerido por Firebase Firestore
    }

    // Constructor con argumentos
    public Document(String title, String description, String pdfFilePath) {
        this.title = title;
        this.description = description;
        this.pdfFilePath = pdfFilePath;
    }

    // Getters y setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPdfFilePath() {
        return pdfFilePath;
    }

    public void setPdfFilePath(String pdfFilePath) {
        this.pdfFilePath = pdfFilePath;
    }
}
