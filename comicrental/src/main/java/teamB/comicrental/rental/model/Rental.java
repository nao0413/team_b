package teamB.comicrental.rental.model;

import java.util.Date;

public class Rental {
    private int rentalId;
    private int comicId;
    private String title;
    private String comicImage;
    private Date rentalDate;
    private Date returnDate;

    public int getRentalId() { return rentalId; }
    public void setRentalId(int rentalId) { this.rentalId = rentalId; }

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
