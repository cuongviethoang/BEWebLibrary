package com.project.projectBook.dto;

public class BuyBookDto {
    private Long id;
    private String title;
    private Long sold;
    private Long restBook;

    private Long price;
    private Long totalPrice;

    public BuyBookDto() {
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

    public Long getSold() {
        return sold;
    }

    public void setSold(Long sold) {
        this.sold = sold;
    }

    public Long getRestBook() {
        return restBook;
    }

    public void setRestBook(Long restBook) {
        this.restBook = restBook;
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
