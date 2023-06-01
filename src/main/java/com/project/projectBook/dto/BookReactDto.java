package com.project.projectBook.dto;

public class BookReactDto {
    private Long id;
    private String title;
    private Double averageRate;
    private Long comments;
    private Long tyms;

    public BookReactDto() {
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

    public Double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(Double averageRate) {
        this.averageRate = averageRate;
    }

    public Long getComments() {
        return comments;
    }

    public void setComments(Long comments) {
        this.comments = comments;
    }

    public Long getTyms() {
        return tyms;
    }

    public void setTyms(Long tyms) {
        this.tyms = tyms;
    }
}
