package teamB.comicrental.shoppingcart.model;

import java.util.Date;

public class Cart {
    private int cart_id;
    private int customer_id;
    private int comic_id;
    private int volume;
    private Date rental_expire;
    private boolean is_deleted;
    private String comic_image;
    private String title;

    // Getter & Setter
    public int getCart_id() { return cart_id; }
    public void setCart_id(int cart_id) { this.cart_id = cart_id; }

    public int getCustomer_id() { return customer_id; }
    public void setCustomer_id(int customer_id) { this.customer_id = customer_id; }

    public int getComic_id() { return comic_id; }
    public void setComic_id(int comic_id) { this.comic_id = comic_id; }

    public int getVolume() { return volume; }
    public void setVolume(int volume) { this.volume = volume; }

    public Date getRental_expire() { return rental_expire; }
    public void setRental_expire(Date rental_expire) { this.rental_expire = rental_expire; }

    public boolean getIs_deleted() { return is_deleted; }
    public void setIs_deleted(boolean is_deleted) { this.is_deleted = is_deleted; }

public String getComic_image() {
    return comic_image;
}

public void setComic_image(String comic_image) {
    this.comic_image = comic_image;
}
public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}