package teamB.comicrental.top.model;

import java.time.LocalDate;

public class Comic {
    private int comic_id;              // 一意のID（必要なら）
    private String title;
    private String author;
    private String comicImage;
    private int rentaltimes;
    private String genre;             // category_id AS genre に対応
    private LocalDate arrivalDate;    // arrival_date AS arrivalDate に対応

    // --- Getter & Setter ---

    public int getComic_id() {
        return comic_id;
    }

    public void setComic_id(int comic_id) {
        this.comic_id = comic_id;
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

    public String getComicImage() {
        return comicImage;
    }

    public void setComicImage(String comicImage) {
        this.comicImage = comicImage;
    }

    public int getRentaltimes() {
        return rentaltimes;
    }

    public void setRentaltimes(int rentaltimes) {
        this.rentaltimes = rentaltimes;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public LocalDate getArrivalDate() {
        return arrivalDate;
    }

    public void setArrivalDate(LocalDate arrivalDate) {
        this.arrivalDate = arrivalDate;
    }
}
