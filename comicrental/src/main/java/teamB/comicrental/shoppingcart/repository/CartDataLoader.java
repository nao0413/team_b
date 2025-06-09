/*
package teamB.comicrental.shoppingcart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import teamB.comicrental.shoppingcart.model.Cart;
import jakarta.annotation.PostConstruct;
import java.sql.Date;
import java.time.LocalDate;

@Component
public class CartDataLoader {

    @Autowired
    private CartMapper cartMapper;


    public void testAddComic() {
        int daysToAdd = 7;

        // ヒロアカ（comic_id = 1）
        Cart cart1 = new Cart();
        cart1.setCustomer_id(1);
        cart1.setComic_id(1); // 僕のヒーローアカデミア
        cart1.setVolume(1);
        cart1.setRental_expire(Date.valueOf(LocalDate.now().plusDays(daysToAdd)));
        cart1.setIs_deleted(false);
        cartMapper.insert(cart1);

        // ワンピース（comic_id = 3）
        Cart cart2 = new Cart();
        cart2.setCustomer_id(1);
        cart2.setComic_id(3); // ONE PIECE
        cart2.setVolume(1);
        cart2.setRental_expire(Date.valueOf(LocalDate.now().plusDays(daysToAdd)));
        cart2.setIs_deleted(false);
        cartMapper.insert(cart2);
    }
}
*/