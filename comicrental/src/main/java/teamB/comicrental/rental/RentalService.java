package teamB.comicrental.rental;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
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
            rentalMapper.incrementRentalTimes(item.getComic_id());
        }

        cartMapper.clearCart(customer_id);
    }

    private static final Logger log = LoggerFactory.getLogger(RentalService.class);

    @Scheduled(fixedRate = 86400000)
    public void updateExpiredRentals() {
        log.info("期限切れレンタル更新ジョブを開始します。");
        try {
            int updatedRows = rentalMapper.updateExpiredRentals();
            log.info("期限切れのレンタル {} 件を更新しました。", updatedRows);
        } catch (Exception e) {
            log.error("期限切れレンタル更新中にエラーが発生しました: {}", e.getMessage(), e);
        }
    }


}
