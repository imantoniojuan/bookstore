package com.anthony.bookstore.entities;

import java.util.List;

import jakarta.persistence.*;

@Entity
@Table(name = "book")
public class Book extends BaseEntity{
    
    @Column(length = 20, unique = true, nullable = false)
    private String isbn;

    @Column(length = 200, nullable = false)
    private String title;

    private Integer year;

    private Float price;

    @Column(length = 100)
    private String genre;

    @ManyToOne
    @JoinColumn(name = "updated_by")
    private User updatedBy;

    @Transient
    private List<Long> authorIds;

    public Book(){

    }

    public Book(Book book){
        this.setId(book.getId());
        this.isbn = book.getIsbn();
        this.title = book.getTitle();
        this.year = book.getYear();
        this.price = book.getPrice();
        this.genre = book.getGenre();
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public User getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(User updatedBy) {
        this.updatedBy = updatedBy;
    }

    public List<Long> getAuthorIds() {
        return authorIds;
    }

    public void setAuthorIds(List<Long> authorIds) {
        this.authorIds = authorIds;
    }

    @Override
    public String toString() {
        return "Book [id=" + this.getId() + ", isbn=" + isbn + ", title=" + title + ", year=" + year + ", price=" + price + ", genre=" + genre
                + ", updatedBy=" + updatedBy + ", authorIds=" + authorIds + "]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        result = prime * result + ((year == null) ? 0 : year.hashCode());
        result = prime * result + ((price == null) ? 0 : price.hashCode());
        result = prime * result + ((genre == null) ? 0 : genre.hashCode());
        result = prime * result + ((updatedBy == null) ? 0 : updatedBy.hashCode());
        result = prime * result + ((authorIds == null) ? 0 : authorIds.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Book other = (Book) obj;
        if (isbn == null) {
            if (other.isbn != null)
                return false;
        } else if (!isbn.equals(other.isbn))
            return false;
        if (title == null) {
            if (other.title != null)
                return false;
        } else if (!title.equals(other.title))
            return false;
        if (year == null) {
            if (other.year != null)
                return false;
        } else if (!year.equals(other.year))
            return false;
        if (price == null) {
            if (other.price != null)
                return false;
        } else if (!price.equals(other.price))
            return false;
        if (genre == null) {
            if (other.genre != null)
                return false;
        } else if (!genre.equals(other.genre))
            return false;
        if (updatedBy == null) {
            if (other.updatedBy != null)
                return false;
        } else if (!updatedBy.equals(other.updatedBy))
            return false;
        if (authorIds == null) {
            if (other.authorIds != null)
                return false;
        } else if (!authorIds.equals(other.authorIds))
            return false;
        return true;
    }
}
