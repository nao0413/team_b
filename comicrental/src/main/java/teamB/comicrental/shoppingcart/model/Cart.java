// Cart.java（修正後）
package teamB.comicrental.shoppingcart.model;

import java.util.Date;

public class Cart {
    private int cart_id;
    private int customer_id;
    private int comic_id;
    private int volume;
    private Date rental_expire;
    private Boolean is_deleted;

    // 表示用フィールド（DBにはない）
    private String title;
    private String imageUrl;

    // --- getter & setter ---
    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public int getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }

    public int getComic_id() {
        return comic_id;
    }

    public void setComic_id(int comic_id) {
        this.comic_id = comic_id;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public Date getRental_expire() {
        return rental_expire;
    }

    public void setRental_expire(Date rental_expire) {
        this.rental_expire = rental_expire;
    }

    public Boolean getIs_deleted() {
        return is_deleted;
    }

    public void setIs_deleted(Boolean is_deleted) {
        this.is_deleted = is_deleted;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
