package com.project.projectBook.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class BillDto implements Serializable {
    private Long id;
    private LocalDate date;
    private LocalTime time;

    private Integer usedBuy;

    private String address;

    private String sdt;
    private Long idUser;

    private String username;

    private Long idBook;

    private String bookTitle;

    private String img;

    private Long price;

    private Long totalPrice;

    public BillDto() {
    }

//    public BillDto(Long id, LocalDate date, LocalTime time, Long usedBuy, Long idUser,String username, Long idBook, String bookTitle, String img, Long price, Long totalPrice) {
//        this.id = id;
//        this.date = date;
//        this.time = time;
//        this.idUser = idUser;
//        this.username = username;
//        this.idBook = idBook;
//        this.bookTitle = bookTitle;
//        this.usedBuy = usedBuy;
//        this.img = img;
//        this.price = price;
//        this.totalPrice = totalPrice;
//    }

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

    public Integer getUsedBuy() {
        return usedBuy;
    }

    public void setUsedBuy(Integer usedBuy) {
        this.usedBuy = usedBuy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
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

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }
}
