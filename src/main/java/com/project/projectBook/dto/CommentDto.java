package com.project.projectBook.dto;


import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class CommentDto implements Serializable {

    private Long id;
    private String content;
    private LocalDate date;
    private LocalTime time;
    private Long bookId;
    private Long userId;
    private String username;

    private String imgUser;

    public CommentDto() {
    }

    public CommentDto(Long id, String content, LocalDate date, LocalTime time, Long bookId, Long userId, String username, String imgUser) {
        this.id = id;
        this.content = content;
        this.date = date;
        this.time = time;
        this.bookId = bookId;
        this.userId = userId;
        this.username = username;
        this.imgUser = imgUser;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
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

    public String getImgUser() {
        return imgUser;
    }

    public void setImgUser(String imgUser) {
        this.imgUser = imgUser;
    }
}
