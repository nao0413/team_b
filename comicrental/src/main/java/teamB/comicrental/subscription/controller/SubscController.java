package teamB.comicrental.subscription.controller;

import java.time.OffsetDateTime;
import java.util.Optional;
//import java.util.concurrent.Flow.Subscription;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import teamB.comicrental.subscription.model.SubscPageModel;
import teamB.comicrental.subscription.repository.SubscMapper;
import teamB.comicrental.subscription.repository.SubscModel;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;

/**
 * SubscriptionController.java
 */

@Controller
@RequestMapping("subscription")
public class SubscController {
    @Autowired
    SubscMapper subscMapper;

    @GetMapping("list")
    public String list(Model model, HttpServletRequest request) {
        SubscPageModel page = new SubscPageModel();
        page.title = "サブスクリプション状況";

        // クッキーからcustomer_idを取得
        Integer customerId = null;
        jakarta.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("customerId".equals(cookie.getName())) {
                    try {
                        customerId = Integer.parseInt(cookie.getValue());
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Cookie 'customerId' value is not a valid number:" + cookie.getValue());
                    }
                }
            }
        }
        if (customerId != null) {
            Optional<SubscModel> subscDateOptional = subscMapper.findById(customerId);
            if (subscDateOptional.isPresent()) {
                SubscModel subscDate = subscDateOptional.get();
                page.setId(subscDate.getCustomer_id());
                page.setName(subscDate.getCustomer_name());
                page.setSubscribed(subscDate.isIs_subscribed());
            } else {
                page.title = "ユーザー情報が見つかりません";
                page.setId(null);
                page.setName("不明");
                page.setSubscribed(false);
            }
        } else {
            page.title = "ログインしていません";
            page.setId(null);
            page.setName("ゲスト");
            page.setSubscribed(false);
        }
        model.addAttribute("page", page);
        return "subscription/subscription";
    }

    // ★★★ クッキーをセットするテスト用のメソッド（開発中に便利）
    // ★★★ 実際にはログイン処理でクッキーがセットされるはずです。
    @GetMapping("set-cookie")
    public String setCookie(HttpServletResponse response) {
        Cookie cookie = new Cookie("customerId", "2");
        cookie.setMaxAge(60 * 60 * 24); // 1日有効
        cookie.setPath("/"); // アプリケーション全体で有効
        response.addCookie(cookie);
        return "redirect:/subscription/list"; // listページへリダイレクト
    }

    @GetMapping("join")
    public String showJoinPage(Model model, HttpServletRequest request, RedirectAttributes redirectAttributes) {
        SubscPageModel page = new SubscPageModel();
        Integer customerId = getCustomerIdFromCookie(request);

        if (customerId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ログインしてください。");
            page.setTitle("ログインが必要です");//ログインページができたらいらない
            model.addAttribute("page", page);//ログインページができたらいらない
            return "subscription/subscription";// 本当はログインページ
        }
        Optional<SubscModel> existingSubscData = subscMapper.findById(customerId);
        if (existingSubscData.isPresent()) {
            SubscModel data = existingSubscData.get();
            page.setId(data.getCustomer_id());
            page.setName(data.getCustomer_name());
            page.setSubscribed(data.isIs_subscribed());

            if (data.isIs_subscribed()) {
                redirectAttributes.addFlashAttribute("infoMessage","お客様はすでにサブスクリプションに加入済みです。");
                return "reditrect:/subscription/list";
            } else {
                page.setTitle("サブスクリプション加入画面");
                model.addAttribute("page", page);
                return "redirect:/subscription/join";
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "お客様のユーザー情報が見つかりませんでした。");
            return "redirect:/subscription/list"; 
        }
    }

    @PostMapping("doJoin")
    public String doJoin(
            HttpServletRequest request,
            @RequestParam("cardNumber") String cardNumber,
            @RequestParam("cardHolderName") String cardHolderName,
            @RequestParam("expiryDate") String expiryDate,
            @RequestParam("securityCode") String securityCode,
            RedirectAttributes redirectAttributes, Model model) {
        Integer customerId = getCustomerIdFromCookie(request);
        if (customerId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ログインしていません。");
            return "subscription/subscription";// 本当はログイン画面
        }

        if (cardNumber.isEmpty() || cardHolderName.isEmpty() || expiryDate.isEmpty() || securityCode.isEmpty()) {
            model.addAttribute("errorMessage", "全てのクレジットカード情報を入力してください。");
            SubscPageModel page = new SubscPageModel();
            page.setTitle("サブスクリプション加入画面");
            model.addAttribute("page", page);
            return "subscription/join";
        }

        SubscModel updateCustomerData = new SubscModel();
        updateCustomerData.setCustomer_id(customerId);
        updateCustomerData.setIs_subscribed(true);
        updateCustomerData.setPlan_type("single_plan");
        updateCustomerData.setSubscription_start_date(OffsetDateTime.now());
        updateCustomerData.setCredit_number(cardNumber);
        updateCustomerData.setCredit_name(cardHolderName);
        updateCustomerData.setCredit_date(expiryDate);
        updateCustomerData.setSecurity_code(securityCode);

        try {
            subscMapper.updateSubscriptionInfo(updateCustomerData);
            redirectAttributes.addFlashAttribute("successMessage", "サブスクリプションに加入しました！");
            return "subscription/subscription";
        } catch (Exception e) {
            System.err.println("サブスクリプション登録エラー：" + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "サブスクリプションの登録に失敗しました。");
            return "subscription/join";
        }
    }

    private Integer getCustomerIdFromCookie(HttpServletRequest request) {
        Integer customerId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("customerId".equals(cookie.getName())) {
                    try {
                        customerId = Integer.parseInt(cookie.getValue());
                        break;
                    } catch (NumberFormatException e) {
                        System.err.println("Cookie 'customerId' value is not a valid number:" + cookie.getValue());
                    }
                }
            }
        }
        return customerId;
    }
}
