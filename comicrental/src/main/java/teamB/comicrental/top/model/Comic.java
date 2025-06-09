package teamB.comicrental.top.model;

public class Comic {
    private String title;
    private String author;
    private String comicImage;
    private int rentaltimes;

    // Getter / Setter
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getComicImage() { return comicImage; }
    public void setComicImage(String comicImage) { this.comicImage = comicImage; }

    public int getRentaltimes() { return rentaltimes; }
    public void setRentaltimes(int rentaltimes) { this.rentaltimes = rentaltimes; }
}
