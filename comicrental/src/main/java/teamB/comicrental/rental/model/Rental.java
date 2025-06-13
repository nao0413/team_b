package teamB.comicrental.rental.model;

import java.util.Date;

public class Rental {

    private int rental_id;
    private int customer_id;
    private int comic_id;

    private int rentalId;
    private int comicId;

    private String title;
    private String comicImage;
    private Date rental_date;
    private Date return_date;


    // Getter & Setter
    public int getRental_id() { return rental_id; }
    public void setRental_id(int rental_id) { this.rental_id = rental_id; }

    public int getCustomer_id() { return customer_id; }
    public void setCustomer_id(int customer_id) { this.customer_id = customer_id; }

    public int getComic_id() { return comic_id; }
    public void setComic_id(int comic_id) { this.comic_id = comic_id; }

    public int getRentalId() { return rentalId; }
    public void setRentalId(int rentalId) { this.rentalId = rentalId; }


    public int getComicId() { return comicId; }
    public void setComicId(int comicId) { this.comicId = comicId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

   public String getComicImage() { return comicImage; }
    public void setComicImage(String comicImage) { this.comicImage = comicImage; }

    public Date getRental_date() { return rental_date; }
    public void setRental_date(Date rental_date) { this.rental_date = rental_date; }

    public Date getReturn_date() { return return_date; }
    public void setReturn_date(Date return_date) { this.return_date = return_date; }
}

