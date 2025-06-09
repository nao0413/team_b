package teamB.comicrental.shoppingcart.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import teamB.comicrental.shoppingcart.model.Cart;
import java.sql.Date;
import java.time.LocalDate;

@Component
public class CartDataLoader {

    @Autowired
    private CartMapper cartMapper;

   @PostConstruct
public void testAddComic() {
    int daysToAdd = 7;

    if (cartMapper.countByCustomerAndComic(1, 1, 1) == 0) { //ヒロアカ
        Cart cart1 = new Cart();
        cart1.setCustomer_id(1);
        cart1.setComic_id(1);
        cart1.setVolume(1);
        cart1.setRental_expire(Date.valueOf(LocalDate.now().plusDays(daysToAdd)));
        cart1.setIs_deleted(false);
        cartMapper.insert(cart1);
    }

    if (cartMapper.countByCustomerAndComic(1, 3, 1) == 0) { //ワンピ
        Cart cart2 = new Cart();
        cart2.setCustomer_id(1);
        cart2.setComic_id(3);
        cart2.setVolume(1);
        cart2.setRental_expire(Date.valueOf(LocalDate.now().plusDays(daysToAdd)));
        cart2.setIs_deleted(false);
        cartMapper.insert(cart2);
    }
}
}