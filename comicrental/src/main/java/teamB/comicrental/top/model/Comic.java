package teamB.comicrental.top.model;

import java.util.Date;

public class Comic {
    private String title;
    private String author;
    private String comicImage;
    private int rentaltimes;
    private int comic_id;
    private Date arrivalDate;
    

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getComicImage() { return comicImage; }
    public void setComicImage(String comicImage) { this.comicImage = comicImage; }

    public int getRentaltimes() { return rentaltimes; }
    public void setRentaltimes(int rentaltimes) { this.rentaltimes = rentaltimes; }

    public int getComic_id() {
        return comic_id;
    }
    public void setComic_id(int comic_id) {
        this.comic_id = comic_id;
    }
    public Date getArrivalDate() {
        return arrivalDate;
    }
    public void setArrivalDate(Date arrivalDate) {
        this.arrivalDate = arrivalDate;
    }

    
}
