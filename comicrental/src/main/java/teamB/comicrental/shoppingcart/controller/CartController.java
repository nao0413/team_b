package teamB.comicrental.shoppingcart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import teamB.comicrental.rental.RentalService;
import teamB.comicrental.rental.repository.RentalMapper;
import teamB.comicrental.shoppingcart.CartService;
import teamB.comicrental.shoppingcart.model.Cart;
import teamB.comicrental.subscription.model.SubscPageModel;
import teamB.comicrental.subscription.repository.SubscMapper;
import teamB.comicrental.subscription.repository.SubscModel;

import java.util.Optional;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private RentalService rentalService;

    @Autowired
    private RentalMapper rentalMapper;

    @Autowired
    private SubscMapper subscMapper;

    private final int maxLimit = 30;

    @GetMapping("")
    public String redirectToCartTable() {
        return "redirect:/cart/table";
    }

    @GetMapping("/table")
public String showCart(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
    Integer customer_id = (Integer) session.getAttribute("loggedInUserId");
    if (customer_id == null) {
        return "redirect:/login/loginpage";
    }

    List<Cart> cartList = cartService.getCartList(customer_id);
    int cartCount = cartList.size();

    // サブスク加入状況を取得
    Optional<SubscModel> subscOptional = subscMapper.findById(customer_id);
    boolean isSubscribed = subscOptional.isPresent() && subscOptional.get().isIs_subscribed();
    model.addAttribute("isSubscribed", isSubscribed);

    // 今月の期間
    LocalDate now = LocalDate.now();
    Date sqlStartDate = Date.valueOf(now.withDayOfMonth(1));
    Date sqlEndDate = Date.valueOf(now.withDayOfMonth(now.lengthOfMonth()));

    int alreadyRented = rentalMapper.countMonthlyRentals(customer_id, sqlStartDate, sqlEndDate);
    int remaining = maxLimit - (alreadyRented + cartCount);

    // ✅ サブスク加入者だけに超過エラー出す
    if (isSubscribed && remaining < 0) {
        model.addAttribute("errorMessage", "レンタル可能数を超えています。カートを見直してください。");
    }

    model.addAttribute("cartList", cartList);
    model.addAttribute("totalCount", cartCount);
    model.addAttribute("remainingLimit", Math.max(0, remaining));

    return "cart/cart";
}

    @PostMapping("/delete")
    public String deleteItem(@RequestParam("cart_id") int cart_id) {
        cartService.deleteCart(cart_id);
        return "redirect:/cart/table";
    }

    @PostMapping("/deleteAll")
    public String deleteAll(HttpSession session) {
        Integer customer_id = (Integer) session.getAttribute("loggedInUserId");
        if (customer_id == null) {
            return "redirect:/login/loginpage";
        }
        cartService.deleteAllCart(customer_id);
        return "redirect:/cart/table";
    }

    @GetMapping("/confirm")
public String confirmCart(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
    Integer customer_id = (Integer) session.getAttribute("loggedInUserId");
    if (customer_id == null) {
        return "redirect:/login/loginpage";
    }

    List<Cart> cartList = cartService.getCartList(customer_id);
    if (cartList.isEmpty()) {
        redirectAttributes.addFlashAttribute("errorMessage", "カートに商品がありません。");
        return "redirect:/cart/table";
    }

    LocalDate now = LocalDate.now();
    Date sqlStartDate = Date.valueOf(now.withDayOfMonth(1));
    Date sqlEndDate = Date.valueOf(now.withDayOfMonth(now.lengthOfMonth()));
    int alreadyRented = rentalMapper.countMonthlyRentals(customer_id, sqlStartDate, sqlEndDate);
    int cartCount = cartList.size();

    if ((alreadyRented + cartCount) > maxLimit) {
        redirectAttributes.addFlashAttribute("errorMessage", "レンタル可能数を超えています。カートを見直してください。");
        return "redirect:/cart/table";
    }

    Optional<SubscModel> subscOptional = subscMapper.findById(customer_id);
    boolean isSubscribed = subscOptional.isPresent() && subscOptional.get().isIs_subscribed();
    model.addAttribute("isSubscribed", isSubscribed);

    model.addAttribute("cartList", cartList);
    model.addAttribute("totalCount", cartService.getTotalCount(cartList));
    model.addAttribute("customerId", customer_id);
    return "cart/cart_confirm";
}
    
    @PostMapping("/complete")
    public String completeRental(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
    Integer customer_id = (Integer) session.getAttribute("loggedInUserId");
    if (customer_id == null) {
        return "redirect:/login/loginpage";
    }

    Optional<SubscModel> subscOptional = subscMapper.findById(customer_id);
    if (subscOptional.isEmpty() || !subscOptional.get().isIs_subscribed()) {
        redirectAttributes.addFlashAttribute("errorMessage", "※レンタルを確定するにはサブスクリプションへの加入が必要です。");
        return "redirect:/cart/table";  
    }
        List<Cart> cartList = cartService.getCartList(customer_id);
        if (cartList.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "カートが空のため、レンタルを確定できません。");
            return "redirect:/cart/confirm";
        }

        // 今月の期間を取得
        LocalDate now = LocalDate.now();
        Date sqlStartDate = Date.valueOf(now.withDayOfMonth(1));
        Date sqlEndDate = Date.valueOf(now.withDayOfMonth(now.lengthOfMonth()));

        // 今月すでに借りた冊数
        int alreadyRented = rentalMapper.countMonthlyRentals(customer_id, sqlStartDate, sqlEndDate);

        // カートにある冊数
        int cartCount = cartList.size();

        // 合計冊数が30超えたらエラー
        if ((alreadyRented + cartCount) > maxLimit) {
            redirectAttributes.addFlashAttribute("errorMessage", "今月のレンタル可能数を超えています。カートを見直してください。");
            return "redirect:/cart/confirm";
        }

        // レンタル登録処理
        rentalService.confirmRental(customer_id);

        // レンタル期限（例：7日後）
        Date expireDate = Date.valueOf(now.plusDays(7));
        cartService.deleteAllCart(customer_id);

        model.addAttribute("rentalExpire", expireDate);
        model.addAttribute("remainingLimit", maxLimit - (alreadyRented + cartCount));

        return "cart/cart_complete";
    }
    }
