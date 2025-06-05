package teamB.comicrental.shoppingcart.repository;

import org.apache.ibatis.annotations.*;
import teamB.comicrental.shoppingcart.model.Cart;
import java.util.List;

// MyBatisのMapper：カート情報をDBとやりとりする
@Mapper
public interface CartMapper {

    // 顧客IDからカート一覧を取得
    @Select("""
                SELECT * FROM cart
                WHERE customer_id = #{customer_id} AND is_deleted = false
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
}
