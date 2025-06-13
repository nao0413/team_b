package teamB.comicrental.shoppingcart.repository;

import org.apache.ibatis.annotations.*;
import teamB.comicrental.shoppingcart.model.Cart;
import java.util.List;

@Mapper
public interface CartMapper {

    // 顧客IDからカート一覧を取得（画像URLとタイトルをJOINで取得）
    @Select("""
    SELECT 
        c.cart_id,
        c.customer_id,
        c.comic_id,             -- ★ここ追加！★
        c.volume,
        c.rental_expire,
        c.is_deleted,
        cm.comic_image AS comic_image,
        cm.title AS title
    FROM cart c
    JOIN comic cm ON c.comic_id = cm.comic_id
    WHERE c.customer_id = #{customer_id}
    AND c.is_deleted = false
""")
List<Cart> findCartByCustomerId(@Param("customer_id") int customer_id);

    // カートに新しい商品を追加
    @Insert("""
                INSERT INTO cart (customer_id, comic_id, volume, rental_expire, is_deleted)
                VALUES (#{customer_id}, #{comic_id}, #{volume}, #{rental_expire}, false)
            """)
    void insert(Cart cart);

    // 指定されたカートIDの商品を削除
    @Update("UPDATE cart SET is_deleted = true WHERE cart_id = #{cart_id}")
    void deleteById(@Param("cart_id") int cart_id);

    // 指定された顧客のカートをすべて削除
    @Update("UPDATE cart SET is_deleted = true WHERE customer_id = #{customer_id}")
    void deleteAll(@Param("customer_id") int customer_id);

    // 「今月のレンタル済み冊数」を数える処理
    @Select("""
                SELECT COUNT(*) FROM cart
                WHERE customer_id = #{customer_id}
                AND rental_expire BETWEEN #{startDate} AND #{endDate}
            """)
    int countMonthlyRentals(@Param("customer_id") int customerId,
            @Param("startDate") java.sql.Date startDate,
            @Param("endDate") java.sql.Date endDate);

            @Delete("DELETE FROM cart WHERE customer_id = #{customerId}")
void clearCart(int customerId);


}
