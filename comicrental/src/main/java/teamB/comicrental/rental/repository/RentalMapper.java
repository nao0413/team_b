package teamB.comicrental.rental.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import teamB.comicrental.rental.model.Rental;

import java.util.List;

@Mapper
public interface RentalMapper {

    @Select("""
        SELECT 
            r.rental_id, 
            c.title, 
            c.comic_image AS comicImage, 
            r.rental_date, 
            r.return_date
        FROM rental r
        JOIN comic c ON r.comic_id = c.comic_id
        WHERE r.return_date IS NULL
    """)
    List<Rental> findCurrentRentals();

    @Select("""
        SELECT 
            r.rental_id, 
            c.title, 
            c.comic_image AS comicImage, 
            r.rental_date, 
            r.return_date
        FROM rental r
        JOIN comic c ON r.comic_id = c.comic_id
        WHERE r.return_date IS NOT NULL
        ORDER BY r.return_date DESC
    """)
    List<Rental> findRentalHistory();

    @Select("""
        SELECT 
            r.rental_id, 
            c.title, 
            c.comic_image AS comicImage, 
            r.rental_date, 
            r.return_date
        FROM rental r
        JOIN comic c ON r.comic_id = c.comic_id
        WHERE c.title = #{title}
        ORDER BY r.rental_date DESC
    """)
    List<Rental> findRentalByTitle(String title);
} 

