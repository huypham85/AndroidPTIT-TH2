package com.example.th2.model;

import java.io.Serializable;

public class MyModel implements Serializable {
    int id;
    String bookName;
    String author;
    String releaseDate;
    String publisher;
    double price;

    public MyModel() {
    }

    public MyModel(int id, String bookName, String author, String releaseDate, String publisher, double price) {
        this.id = id;
        this.bookName = bookName;
        this.author = author;
        this.releaseDate = releaseDate;
        this.publisher = publisher;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}

