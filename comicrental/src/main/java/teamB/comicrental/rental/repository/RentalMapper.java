package teamB.comicrental.rental.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import teamB.comicrental.rental.model.Rental;
import org.apache.ibatis.annotations.Param;
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

    @Insert("""
                INSERT INTO rental (
                    customer_id,
                    comic_id,
                    rental_start_date,
                    rental_end_date,
                    rental_status,
                    rental_expire
                ) VALUES (
                    #{customer_id},
                    #{comic_id},
                    CURRENT_DATE,
                    CURRENT_DATE + INTERVAL '7 days',
                    'レンタル中',
                    CURRENT_DATE + INTERVAL '7 days'
                )
            """)
    void insertRental(Rental rental);

    @Select("""
                SELECT COUNT(*) FROM rental
                WHERE customer_id = #{customer_id}
                AND rental_start_date BETWEEN #{startDate} AND #{endDate}
            """)
    int countMonthlyRentals(
            @Param("customer_id") int customerId,
            @Param("startDate") java.sql.Date startDate,
            @Param("endDate") java.sql.Date endDate);

          


}

