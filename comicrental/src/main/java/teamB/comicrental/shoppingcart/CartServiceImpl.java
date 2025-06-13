package teamB.comicrental.shoppingcart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import teamB.comicrental.shoppingcart.model.Cart;
import teamB.comicrental.shoppingcart.repository.CartMapper;
import java.util.List;
import java.sql.Date;


// CartService の実装クラス：実際の処理内容をここで定義
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartMapper cartMapper;

    // 顧客のカート一覧を取得
    @Override
    public List<Cart> getCartList(int customer_id) {
        return cartMapper.findCartByCustomerId(customer_id);
    }

    // カートの合計冊数
    @Override
    public int getTotalCount(List<Cart> cartList) {
        return cartList.size();
    }

    // 残りレンタル可能数
    @Override
    public int getRemainingLimit(int customer_id, int maxLimit) {
        int currentCount = getCartList(customer_id).size();
        return maxLimit - currentCount;
    }

    // 1件削除
    @Override
    public void deleteCart(int cart_id) {
        cartMapper.deleteById(cart_id);
    }

    // 顧客の全カートを削除
    @Override
    public void deleteAllCart(int customer_id) {
        cartMapper.deleteAll(customer_id);
    }

    @Override
public int countMonthlyRentals(int customerId, Date startDate, Date endDate) {
    return cartMapper.countMonthlyRentals(customerId, startDate, endDate);
}

}
