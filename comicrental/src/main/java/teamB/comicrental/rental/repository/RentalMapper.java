package teamB.comicrental.rental.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;

import teamB.comicrental.rental.model.Rental;
import teamB.comicrental.top.model.Comic;

import java.util.List;

@Mapper
public interface RentalMapper {

    // レンタル中の漫画を取得
    @Select("""
                SELECT
                    r.rental_id,
                    r.customer_id,
                    c.comic_id AS comicId,
                    c.title,
                    c.comic_image AS comicImage,
                    r.rental_start_date AS rentalDate,
                    r.rental_expire AS returnDate
                FROM rental r
                JOIN comic c ON r.comic_id=c.comic_id

                WHERE r.rental_status='レンタル中' AND r.customer_id=#{customerId}
                ORDER BY r.rental_expire ASC
            """)
    List<Rental> findCurrentRentals(@Param("customerId") Integer customerId);

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
                JOIN comic c ON r.comic_id=c.comic_id
                WHERE r.rental_status='返却済み'
                ORDER BY r.rental_end_date DESC
            """)
    List<Rental> findRentalHistory();

    // タイトルで検索
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

    // レンタル登録
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

    // 月間レンタル数カウント
    @Select("""
                SELECT COUNT(*) FROM rental
                WHERE customer_id = #{customer_id}
                AND rental_start_date BETWEEN #{startDate} AND #{endDate}
            """)
    int countMonthlyRentals(
            @Param("customer_id") int customerId,
            @Param("startDate") java.sql.Date startDate,
            @Param("endDate") java.sql.Date endDate);

    @Select("""
                SELECT comic_id, title, author, comic_image AS comicImage, rentaltimes, category_id AS genre, arrival_date AS arrivalDate
                FROM comic
                WHERE comic_id = #{comicId}
            """)
    Comic findComicById(@Param("comicId") int comicId);

    @Update("""
            UPDATE rental
            SET rental_status='期限切れ',
                rental_end_date=CURRENT_DATE
            WHERE rental_status='レンタル中'
              AND rental_expire<CURRENT_DATE
            """)
    int updateExpiredRentals();
    
    @Update("""
        UPDATE comic
        SET rentaltimes = rentaltimes + 1
        WHERE comic_id = #{comicId}
    """)
    int incrementRentalTimes(@Param("comicId") int comicId);
}
