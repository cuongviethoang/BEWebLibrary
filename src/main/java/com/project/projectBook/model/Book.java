package com.project.projectBook.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="author")
    private String author;

    @Column(name = "release_date")
    private String releaseDate;

    @Column(name="length")
    private Integer length;

    @Column(name="sold")
    private Integer sold;

    @ManyToMany(fetch = FetchType.LAZY,
        cascade = {
            CascadeType.MERGE,
                CascadeType.PERSIST
        }
    )
    @JoinTable(name = "book_geners",
        joinColumns = {@JoinColumn(name = "book_id")},
            inverseJoinColumns = {@JoinColumn(name = "genre_id")}
    )
    private Set<Genre> genres = new HashSet<>();

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();

    public Book() {
    }

    public Book(String title, String author, String releaseDate, Integer length, Integer sold) {
        this.title = title;
        this.author = author;
        this.releaseDate = releaseDate;
        this.length = length;
        this.sold = sold;
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

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getSold() {
        return sold;
    }

    public void setSold(Integer sold) {
        this.sold = sold;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void addgenre(Genre genre) {
        this.genres.add(genre);
        genre.getBooks().add(this);
    }

    public void removeGenre(long genreId) {
        Genre genre = this.genres.stream().filter(t -> t.getId() == genreId).findFirst().orElse(null);

        if(genre != null) {
            this.genres.remove(genre);
            genre.getBooks().remove(this);
        }
    }


}
