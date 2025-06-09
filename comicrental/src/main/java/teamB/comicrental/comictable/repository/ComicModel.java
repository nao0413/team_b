package teamB.comicrental.comictable.repository;

import java.sql.Date;

public class ComicModel {
    public int comic_id;
    public String title;
    public String author;
    public String explanatory;
    public int rentaltimes;
    public int category_id;
    public Date arrival_date;
    public String comic_image;
    public String category_name;
    
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
    public String getExplanatory() {
        return explanatory;
    }
    public void setExplanatory(String explanatory) {
        this.explanatory = explanatory;
    }
    public int getRentaltimes() {
        return rentaltimes;
    }
    public void setRentaltimes(int rentaltimes) {
        this.rentaltimes = rentaltimes;
    }
    public int getCategory_id() {
        return category_id;
    }
    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }
    public Date getArrival_date() {
        return arrival_date;
    }
    public void setArrival_date(Date arrival_date) {
        this.arrival_date = arrival_date;
    }
    public String getComic_image() {
        return comic_image;
    }
    public void setComic_image(String comic_image) {
        this.comic_image = comic_image;
    }
    public String getCategory_name() {
        return category_name;
    }
    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
    
}
