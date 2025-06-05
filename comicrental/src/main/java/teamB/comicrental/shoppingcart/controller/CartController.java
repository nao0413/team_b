package teamB.comicrental.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import teamB.comicrental.shoppingcart.CartService;
import teamB.comicrental.shoppingcart.model.Cart;
import java.util.List;

@Controller
@RequestMapping("/cart") // /cart で始まるURLをこのコントローラーで処理
public class CartController {

    @Autowired
    private CartService cartService; // CartService を自動注入

    private final int customer_id = 1; // ユーザーID
    private final int maxLimit = 30; // 月にレンタルできる最大冊数

    // カート一覧画面を表示する

    @GetMapping
    public String showCart(Model model) {
        // カート内のアイテム一覧を取得
        List<Cart> cartList = cartService.getCartList(customer_id);
        // テンプレートにデータを渡す
        model.addAttribute("cartList", cartList);
        model.addAttribute("totalCount", cartService.getTotalCount(cartList)); // 合計冊数
        model.addAttribute("remainingLimit", cartService.getRemainingLimit(customer_id, maxLimit)); // 残りレンタル可能冊数
        return "cart"; // cart.html を表示
    }

    // 指定されたアイテムをカートから削除

    @PostMapping("/delete")
    public String deleteItem(@RequestParam("cart_id") int cart_id) {
        cartService.deleteCart(cart_id); // cart_id に対応するデータを削除
        return "redirect:/cart"; // 一覧画面にリダイレクト
    }

    // カート内の全アイテムを削除（論理削除）

    @PostMapping("/deleteAll")
    public String deleteAll() {
        cartService.deleteAllCart(customer_id); // 指定ユーザーのカートを全削除
        return "redirect:/cart"; // 一覧画面にリダイレクト
    }

    // レンタル確認画面を表示する

    @GetMapping("/confirm")
    public String confirmCart(Model model) {
        List<Cart> cartList = cartService.getCartList(customer_id); // カート内容を取得
        model.addAttribute("cartList", cartList); // テンプレートに渡す
        return "cart_confirm"; // cart_confirm.html を表示
    }

    // レンタル完了処理を実行し、完了画面を表示する

    @PostMapping("/complete")
    public String completeRental() {
        cartService.deleteAllCart(customer_id); // レンタル完了＝カート全削除
        return "cart_complete"; // 完了画面(cart_complete.html)を表示
    }
}
