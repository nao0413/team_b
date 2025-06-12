package teamB.comicrental.rental.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import teamB.comicrental.rental.model.Rental;
import java.util.List;

@Mapper
public interface RentalMapper {

    // レンタル中の漫画を取得
    @Select("""
        SELECT 
            r.rental_id, 
            c.comic_id, 
            c.title, 
            c.comic_image AS comicImage, 
            r.rental_start_date AS rentalDate, 
            r.rental_expire AS returnDate
        FROM rental r
        JOIN comic c ON r.comic_id = c.comic_id
        WHERE r.rental_status = '貸出中'
    """)
    List<Rental> findCurrentRentals();

    // 過去のレンタル履歴
    @Select("""
        SELECT 
            r.rental_id, 
            c.comic_id, 
            c.title, 
            c.comic_image AS comicImage, 
            r.rental_start_date AS rentalDate, 
            r.rental_end_date AS returnDate
        FROM rental r
        JOIN comic c ON r.comic_id = c.comic_id
        WHERE r.rental_status = '返却済み'
        ORDER BY r.rental_end_date DESC
    """)
    List<Rental> findRentalHistory();
}
