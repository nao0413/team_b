package teamB.comicrental.shoppingcart;

import java.util.List;
import teamB.comicrental.shoppingcart.model.Cart;

public interface CartService {
    List<Cart> getCartList(int customer_id); //指定された顧客IDに紐づくカートの中身を取得する。

    int getTotalCount(List<Cart> cartList); //カート内の商品（漫画）の合計冊数をカウントする。

    void deleteCart(int cart_id); //指定されたカートIDの商品をカートから削除する。

    void deleteAllCart(int customer_id); //指定された顧客のすべてのカート内商品を削除する。

    int getRemainingLimit(int customer_id, int maxLimit); //月のレンタル上限数と現在のカート内冊数から、残り借りられる冊数を計算する
    
}