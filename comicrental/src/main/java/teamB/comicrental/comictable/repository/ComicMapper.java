package teamB.comicrental.comictable.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import teamB.comicrental.subscription.repository.SubscModel;

/**
 * ComicMapper.java
 */

 @Mapper
public interface ComicMapper {
    //comicとcategoryテーブルを合体させて情報を取得するSQL
    @Select("select c.comic_id,c.title,c.author,c.explanatory,c.category_id,ca.category_name,c.comic_image from comic c LEFT JOIN category ca ON c.category_id=ca.category_id ORDER BY comic_id")
    public List<ComicModel> findAllComicsWithCategory();
    
    //上記のに合わせて、rentalテーブルも合体させて情報を取得するSQL
    @Select("select c.comic_id,c.title,c.author,c.explanatory,c.category_id,ca.category_name,c.comic_image,CASE WHEN r.rental_status='renting' THEN TRUE ELSE FALSE END AS is_rented from comic c LEFT JOIN category ca ON c.category_id=ca.category_id LEFT JOIN rental r ON c.comic_id=r.comic_id AND r.customer_id=#{customer_id} AND r.rental_status='renting' ORDER BY c.comic_id")
    public List<ComicModel> findAllComicsWithCategoryAndRentalStatus(@Param("customer_id")int customerId);
}
