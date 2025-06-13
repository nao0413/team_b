package teamB.comicrental.rental.model;

import java.util.Date;

public class Rental {
    private int customer_id;
    private int comic_id;

    private int rentalId;    // SQLの r.rental_id に対応
    private int customerId;  // SQLの r.customer_id に対応
    private int comicId;     // SQLの c.comic_id に対応


    private String title;      // SQLの c.title に対応
    private String comicImage; // SQLの c.comic_image AS comicImage に対応
    private Date rentalDate;   // SQLの r.rental_start_date AS rentalDate に対応
    private Date returnDate;   // SQLの r.rental_expire AS returnDate (または rental_end_date) に対応



    // --- Getter & Setter ---

    public int getCustomer_id() { return customer_id; }
    public void setCustomer_id(int customer_id) { this.customer_id = customer_id; }

    public int getComic_id() { return comic_id; }
    public void setComic_id(int comic_id) { this.comic_id = comic_id; }

    public int getRentalId() { return rentalId; }
    public void setRentalId(int rentalId) { this.rentalId = rentalId; }

    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }

    public int getComicId() { return comicId; }
    public void setComicId(int comicId) { this.comicId = comicId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }


    public String getComicImage() { return comicImage; }
    public void setComicImage(String comicImage) { this.comicImage = comicImage; }

    public Date getRentalDate() { return rentalDate; }
    public void setRentalDate(Date rentalDate) { this.rentalDate = rentalDate; }


    public Date getReturnDate() { return returnDate; }
    public void setReturnDate(Date returnDate) { this.returnDate = returnDate; }
} 


