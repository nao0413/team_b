package teamB.comicrental.rental;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamB.comicrental.rental.model.Rental;
import teamB.comicrental.rental.repository.RentalMapper;
import teamB.comicrental.shoppingcart.model.Cart;
import teamB.comicrental.shoppingcart.repository.CartMapper;

import java.util.List;

@Service
public class RentalService {

    @Autowired
    private RentalMapper rentalMapper;

    @Autowired
    private CartMapper cartMapper;

    // カート内の漫画をレンタルテーブルに保存する
    public void confirmRental(int customer_id) {
        List<Cart> cartList = cartMapper.findCartByCustomerId(customer_id);
        for (Cart item : cartList) {
            Rental rental = new Rental();
            rental.setCustomer_id(customer_id);
            rental.setComic_id(item.getComic_id());
            rentalMapper.insertRental(rental);

           
        }

        cartMapper.clearCart(customer_id);
    }
}
