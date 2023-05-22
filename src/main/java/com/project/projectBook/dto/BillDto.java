package com.project.projectBook.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class BillDto implements Serializable {
    private Long id;
    private LocalDate date;
    private LocalTime time;

    private Long usedBuy;
    private Long idUser;

    private String username;

    private Long idBook;

    private String bookTitle;

    private String img;

    public BillDto() {
    }

    public BillDto(Long id, LocalDate date, LocalTime time, Long usedBuy, Long idUser,String username, Long idBook, String bookTitle, String img) {
        this.id = id;
        this.date = date;
        this.time = time;
        this.idUser = idUser;
        this.username = username;
        this.idBook = idBook;
        this.bookTitle = bookTitle;
        this.usedBuy = usedBuy;
        this.img = img;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Long getUsedBuy() {
        return usedBuy;
    }

    public void setUsedBuy(Long usedBuy) {
        this.usedBuy = usedBuy;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getIdBook() {
        return idBook;
    }



    public void setIdBook(Long idBook) {
        this.idBook = idBook;
    }


    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
