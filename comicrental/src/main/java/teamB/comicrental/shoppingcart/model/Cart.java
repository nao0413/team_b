package teamB.comicrental.shoppingcart.model;

import java.util.Date;

public class Cart {
    private int cart_id; // カートID（主キー）
    private int customer_id; // 顧客ID（外部キー）
    private int comic_id; // 漫画ID（外部キー）
    private int volume; // 巻数
    private Date rental_expire; // 閲覧可能期限
    private Boolean is_deleted; // 削除フラグ（

    public int getCart_id() {
        return cart_id;
    }

    public void setCart_id(int cart_id) {
        this.cart_id = cart_id;
    }

    public Integer getCustomer_id() {
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
}
