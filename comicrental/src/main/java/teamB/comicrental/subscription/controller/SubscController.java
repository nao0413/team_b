package teamB.comicrental.subscription.controller;

import java.time.OffsetDateTime;
import java.time.YearMonth;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
//import java.util.concurrent.Flow.Subscription;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

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
//import org.springframework.web.bind.annotation.RequestBody;

import jakarta.servlet.http.HttpSession;

/**
 * SubscriptionController.java
 */

@Controller
@RequestMapping("subscription")
public class SubscController {
    @Autowired
    SubscMapper subscMapper;
    // ログ出力
    private static final Logger log = LoggerFactory.getLogger(SubscController.class);

    // サブスクリプション確認画面にて、会員情報を表示する
    @GetMapping("list")
    public String list(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        SubscPageModel page = new SubscPageModel();
        log.info("テストログ出力");
        page.title = "サブスクリプション状況";
        // ログイン状態のとき（前のページからcustomer_idを受け取っている場合）
        String loggedInUsername = (String) session.getAttribute("loggedInUser");
        Integer customerId = (Integer) session.getAttribute("loggedInUserId");
        if (loggedInUsername == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です。");
            return "redirect:/login/loginpage";
        }
        // 会員IDがDBに登録されている場合
        Optional<SubscModel> subscDateOptional = subscMapper.findById(customerId);
        if (subscDateOptional.isPresent()) {
            SubscModel subscData = subscDateOptional.get();
            page.setId(subscData.getCustomer_id());
            page.setName(subscData.getCustomer_name());
            page.setSubscribed(subscData.isIs_subscribed());
        } else {
            // 会員IDがDBに登録されていない場合
            page.title = "ユーザー情報が見つかりません";
            page.setId(null);
            page.setName("不明");
            page.setSubscribed(false);
        }
        model.addAttribute("page", page);
        return "subscription/subscription";
    }

    // サブスクリプションに加入する画面に表示する情報
    @GetMapping("join")
    public String showJoinPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        SubscPageModel page = new SubscPageModel();
        String loggedInUsername = (String) session.getAttribute("loggedInUser");
        Integer customerId = (Integer) session.getAttribute("loggedInUserId");
        if (loggedInUsername == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です。");
            return "redirect:/login/loginpage";
        }
        Optional<SubscModel> existingSubscData = subscMapper.findById(customerId);
        // customer_idがDBに登録されている場合
        if (existingSubscData.isPresent()) {
            SubscModel data = existingSubscData.get();
            page.setId(data.getCustomer_id());
            page.setName(data.getCustomer_name());
            page.setSubscribed(data.isIs_subscribed());
            // サブスクリプションに加入済みの場合
            if (data.isIs_subscribed()) {
                redirectAttributes.addFlashAttribute("infoMessage", "お客様はすでにサブスクリプションに加入済みです。");
                return "redirect:/subscription/list?customerId=" + customerId;
            } else {
                // サブスクリプションに未加入の場合
                page.setTitle("サブスクリプション 加入");
                model.addAttribute("page", page);
                return "subscription/subscriptionJoin";
            }
            // customer_idがDBに登録されていない場合
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "お客様のユーザー情報が見つかりませんでした。");
            page.setId(customerId);
            page.setTitle("ユーザー情報が見つかりません");
            page.setName("不明なユーザー");
            page.setSubscribed(false);
            model.addAttribute("page", page);
            return "redirect:/subscription/list?customerId=" + customerId;
        }
    }

    // 入力されたクレジットカード情報を登録し、サブスクリプション状況を更新する
    @PostMapping("doJoin")
    public String doJoin(
            @RequestParam("cardNumber") String cardNumber,
            @RequestParam("cardHolderName") String cardHolderName,
            @RequestParam("expiryDate") String expiryDate,
            @RequestParam("securityCode") String securityCode,
            RedirectAttributes redirectAttributes, HttpSession session, Model model) {
        String loggedInUsername = (String) session.getAttribute("loggedInUser");
        Integer customerId = (Integer) session.getAttribute("loggedInUserId");
        if (loggedInUsername == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です。");
            return "redirect:/login/loginpage";
        }
        Map<String, String> errors = new HashMap<>();
        // 1. 必須チェック (isEmpty() または isBlank())
        if (cardNumber == null || cardNumber.isEmpty()) {
            errors.put("cardNumber", "カード番号は必須です。");
        }
        if (cardHolderName == null || cardHolderName.isEmpty()) {
            errors.put("cardHolderName", "名義人は必須です。");
        }
        if (expiryDate == null || expiryDate.isEmpty()) {
            errors.put("expiryDate", "有効期限は必須です。");
        }
        if (securityCode == null || securityCode.isEmpty()) {
            errors.put("securityCode", "セキュリティコードは必須です。");
        }
        // 2. 形式チェック (正規表現や文字数制限)
        if (cardNumber != null && !cardNumber.isEmpty() && !Pattern.matches("^\\d{4}-\\d{4}-\\d{4}-\\d{4}$", cardNumber)) {
            errors.put("cardNumber", "カード番号はXXXX-XXXX-XXXX-XXXXの形で入力してください。");
            }
        if (cardHolderName != null && !cardHolderName.isEmpty() && cardHolderName.length() > 50) {
            errors.put("cardHolderName", "名義人は50文字以内で入力してください。");
        }
        if (expiryDate != null && !expiryDate.isEmpty()
                && !Pattern.matches("^(0[1-9]|1[0-2])\\/([0-9]{2})$", expiryDate)) {
            errors.put("expiryDate", "有効期限はMM/YY形式で入力してください (例: 12/25)。");
        }
        if (securityCode != null && !securityCode.isEmpty() && !Pattern.matches("^[0-9]{3}$", securityCode)) {
            errors.put("securityCode", "セキュリティコードは3桁の半角数字で入力してください。");
        }
        if (!errors.containsKey("expiryDate") && expiryDate != null && !expiryDate.isEmpty()) {
            try {
                String[] parts = expiryDate.split("/");
                int month = Integer.parseInt(parts[0]);
                int year = 2000 + Integer.parseInt(parts[1]); // YYを20YYに変換

                YearMonth expiry = YearMonth.of(year, month);
                if (expiry.isBefore(YearMonth.now())) { // 現在の年月より過去である場合
                    errors.put("expiryDate", "有効期限が過ぎています。");
                }
            } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                // ここでエラーになるのは形式が壊れている場合なので、既にPatternで弾かれているはずだが念のため
                errors.put("expiryDate", "有効期限の形式が不正です。");
            }
        }
        // 一つでも入力されていない場所がある場合の処理
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            model.addAttribute("errorMessage", "入力内容に誤りがあります。");
            SubscPageModel page = new SubscPageModel();
            page.setTitle("サブスクリプション  加入");
            page.setCardNumber(cardNumber);
            page.setCardHolderName(cardHolderName);
            page.setExpiryDate(expiryDate);
            page.setSecurityCode(securityCode);
            model.addAttribute("page", page);
            return "subscription/subscriptionJoin";
        }
        // DBをupdateするために、入力されたデータを受け渡すためにSubscModelにセットする
        SubscModel updateCustomerData = new SubscModel();
        updateCustomerData.setCustomer_id(customerId);
        updateCustomerData.setIs_subscribed(true);
        updateCustomerData.setsubscription_plan_id(1);
        updateCustomerData.setSubscription_start_date(OffsetDateTime.now());
        updateCustomerData.setCredit_number(cardNumber);
        updateCustomerData.setCredit_name(cardHolderName);
        updateCustomerData.setCredit_date(expiryDate);
        updateCustomerData.setSecurity_code(securityCode);

        // DBにうまく登録できた場合
        try {
            subscMapper.updateSubscriptionInfo(updateCustomerData);
            redirectAttributes.addFlashAttribute("successMessage", "サブスクリプションに加入しました！");
            return "redirect:/subscription/list?customerId=" + customerId;
        } catch (Exception e) {
            // DBに登録できなかった場合
            System.err.println("サブスクリプション登録エラー：" + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "サブスクリプションの登録に失敗しました。");
            model.addAttribute("errorMessage", "サブスクリプションの登録に失敗しました。（DBエラー）");
            // 登録できなかったときに、情報をページに保持したままにするための処理
            SubscPageModel page = new SubscPageModel();
            page.setTitle("サブスクリプション加入画面");
            page.setCardNumber(cardNumber);
            page.setCardHolderName(cardHolderName);
            page.setExpiryDate(expiryDate);
            page.setSecurityCode(securityCode);
            model.addAttribute("page", page);
            return "subscription/subscriptionJoin";
        }
    }

    // サブスクリプションから退会するための処理
    @GetMapping("withdrawal")
    public String showWithdrawalPage(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        SubscPageModel page = new SubscPageModel();
        String loggedInUsername = (String) session.getAttribute("loggedInUser");
        Integer customerId = (Integer) session.getAttribute("loggedInUserId");
        if (loggedInUsername == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です。");
            return "redirect:/login/loginpage";
        }
        // 前のページからcustomer_idが受け取れた場合
        Optional<SubscModel> existingSubscData = subscMapper.findById(customerId);
        // customer_idがDBに登録されている場合
        if (existingSubscData.isPresent()) {
            SubscModel data = existingSubscData.get();
            page.setId(data.getCustomer_id());
            page.setName(data.getCustomer_name());
            page.setSubscribed(data.isIs_subscribed());
            // サブスクリプションに加入していない場合
            if (!data.isIs_subscribed()) {
                redirectAttributes.addFlashAttribute("errorMessage", "お客様はサブスクリプションに加入していません。");
                return "redirect:/subscription/list?customerId=" + customerId;
            } else {
                // サブスクリプションに加入している場合
                page.setTitle("サブスクリプション退会確認");
                model.addAttribute("page", page);
                return "subscription/subscriptionWithdrawal";
            }
        } else {
            // customer_idがDBに登録されていない場合
            redirectAttributes.addFlashAttribute("errorMessage", "お客様のユーザー情報が見つかりませんでした。");
            return "redirect:/subscription/list?customerId=" + customerId;
        }

    }

    @PostMapping("doWithdrawal")
    public String doWithdrawal(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        SubscPageModel page = new SubscPageModel();
        String loggedInUsername = (String) session.getAttribute("loggedInUser");
        Integer customerId = (Integer) session.getAttribute("loggedInUserId");
        if (loggedInUsername == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です。");
            return "redirect:/login/loginpage";
        }
        // 退会処理がうまくできたとき
        try {
            subscMapper.unsubscribe(customerId);
            // page.setTitle("退会が完了しました");
            page.setId(customerId);
            redirectAttributes.addFlashAttribute("successMessage", "サブスクリプション退会手続きが完了しました。");
            model.addAttribute("page", page);
            return "redirect:/subscription/withdrawalCon";
            // 退会処理ができなかったとき
        } catch (Exception e) {
            System.err.println("サブスクリプション退会エラー" + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "サブスクリプションの退会に失敗しました。");
            return "redirect:/subscription/withdrawalCon";
        }
    }

    @GetMapping("withdrawalCon")
    public String showWithdrawalConfirmation(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Integer customerId = (Integer) session.getAttribute("loggedInUserId");
        SubscPageModel page = new SubscPageModel();
        Optional<SubscModel> subscDataOptional = subscMapper.findById(customerId);
        if (subscDataOptional.isPresent()) {
            SubscModel subscData = subscDataOptional.get();
            page.setId(subscData.getCustomer_id());
        } else {
            // データが見つからない場合（異常系だが、ページは開くように）
            page.setTitle("退会手続き完了（ユーザー情報なし）");
            page.setId(customerId); // customerIdは取得できるので設定しても良い
        }
        model.addAttribute("page", page);
        return "subscription/subscriptionWithdrawalCon";
    }
}
