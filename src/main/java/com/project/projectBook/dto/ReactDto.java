package com.project.projectBook.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class ReactDto implements Serializable {
    private Long id;
    private Integer voted;
    private String message;
    private LocalDate date;
    private LocalTime time;
    private Long bookId;
    private Long userId;
    private String username;

    private String imgUser;

    public ReactDto() {
    }

    public ReactDto(Long id, Integer voted, String message, LocalDate date, LocalTime time, Long bookId, Long userId, String username, String imgUser) {
        this.id = id;
        this.voted = voted;
        this.message = message;
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

    public Integer getVoted() {
        return voted;
    }

    public void setVoted(Integer voted) {
        this.voted = voted;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
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
