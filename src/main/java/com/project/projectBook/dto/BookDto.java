package com.project.projectBook.dto;

import java.io.Serializable;

public class BookDto implements Serializable {
    private Long id;
    private String title;
    private String author;
    private Integer sold;

    private Long userId;
    private String username;

    public BookDto() {
    }

    public BookDto(Long id, String title, String author, Integer sold, Long userId, String username) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.sold = sold;
        this.userId = userId;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
