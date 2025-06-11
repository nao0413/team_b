package teamB.comicrental.comictable.repository;

import java.sql.Date;

public class ComicModel {
    private Integer comic_id;
    private String title;
    private String author;
    private String explanatory;
    private int rentaltimes;
    private int category_id;
    private Date arrival_date;
    private String comic_image;
    private String category_name;
    private int customer_id;
    private Boolean isRented;
    private Boolean is_recommended;
    private String recommend_type;
    private String recommend_text;
    private String recommend_image; 

    public Integer getComic_id() {
        return comic_id;
    }
    public void setComic_id(Integer comic_id) {
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
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    public Boolean getIsRented() {
        return isRented;
    }
    public void setIsRented(Boolean isRented) {
        this.isRented = isRented;
    }
     public Boolean getIs_recommended() {
        return is_recommended;
    }
    public void setIs_recommended(Boolean is_recommended) {
        this.is_recommended = is_recommended;
    }
    public String getRecommend_type() {
        return recommend_type;
    }
    public void setRecommend_type(String recommend_type) {
        this.recommend_type = recommend_type;
    }
    public String getRecommend_text() {
        return recommend_text;
    }
    public void setRecommend_text(String recommend_text) {
        this.recommend_text = recommend_text;
    }
    public String getRecommend_image() {
        return recommend_image;
    }
    public void setRecommend_image(String recommend_image) {
        this.recommend_image = recommend_image;
    }
}
