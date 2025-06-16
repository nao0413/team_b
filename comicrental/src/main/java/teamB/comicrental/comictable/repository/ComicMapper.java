package teamB.comicrental.comictable.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


/**
 * ComicMapper.java
 */

 @Mapper
public interface ComicMapper {
    //comicとcategoryテーブルを合体させて情報を取得するSQL
    @Select("select c.comic_id,c.title,c.author,c.explanatory,c.category_id,ca.category_name,c.comic_image from comic c LEFT JOIN category ca ON c.category_id=ca.category_id ORDER BY comic_id")
    public List<ComicModel> findAllComicsWithCategory();
    
    //上記のに合わせて、rentalテーブルも合体させて情報を取得するSQL（デフォルト）
    @Select("select c.comic_id,c.title,c.author,c.explanatory,c.category_id,ca.category_name,c.comic_image,CASE WHEN r.rental_status='レンタル中' THEN TRUE ELSE FALSE END AS is_rented from comic c LEFT JOIN category ca ON c.category_id=ca.category_id LEFT JOIN rental r ON c.comic_id=r.comic_id AND r.customer_id=#{customer_id} ORDER BY c.comic_id")
    public List<ComicModel> findAllComicsWithCategoryAndRentalStatus(@Param("customer_id")int customerId);

    //詳細ページのため
    @Select("select c.comic_id,c.title,c.author,c.explanatory,c.category_id,ca.category_name,c.comic_image,CASE WHEN r.rental_status='レンタル中' THEN TRUE ELSE FALSE END AS is_rented from comic c LEFT JOIN category ca ON c.category_id=ca.category_id LEFT JOIN rental r ON c.comic_id = r.comic_id AND r.customer_id = #{customerId} WHERE c.comic_id =#{comicId} LIMIT 1")
    public Optional<ComicModel> findByComicId(@Param("comicId")int comicId,@Param("customerId")Integer customerId);

    //漫画紹介ページのため
    @Select("select comic_id,title,author,explanatory,comic_image,is_recommended,recommend_type,recommend_text,recommend_image from comic where is_recommended=true ORDER BY comic_id ")
    List<ComicModel>findRecommendedComics();
    
    //入荷順
    @Select("select c.comic_id,c.title,c.author,c.explanatory,c.category_id,ca.category_name,c.comic_image,c.arrival_date,CASE WHEN r.rental_status='レンタル中' THEN TRUE ELSE FALSE END AS is_rented from comic c LEFT JOIN category ca ON c.category_id=ca.category_id LEFT JOIN rental r ON c.comic_id=r.comic_id AND r.customer_id=#{customer_id} ORDER BY c.arrival_date")
    public List<ComicModel>findAllComicsSortedByArrivalDate (@Param("customer_id")int customerId);

    //レンタル回数順
    @Select("select c.comic_id,c.title,c.author,c.explanatory,c.category_id,ca.category_name,c.comic_image,c.rentaltimes,CASE WHEN r.rental_status='レンタル中' THEN TRUE ELSE FALSE END AS is_rented from comic c LEFT JOIN category ca ON c.category_id=ca.category_id LEFT JOIN rental r ON c.comic_id=r.comic_id AND r.customer_id=#{customer_id} ORDER BY c.rentaltimes DESC")
    public List<ComicModel>findAllComicsSortedByRentaltimes (@Param("customer_id")int customerId);
}
