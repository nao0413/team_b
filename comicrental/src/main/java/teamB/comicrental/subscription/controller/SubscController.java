package teamB.comicrental.subscription.controller;

import java.time.OffsetDateTime;
import java.util.Optional;
//import java.util.concurrent.Flow.Subscription;


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


/**
 * SubscriptionController.java
 */

@Controller
@RequestMapping("subscription")
public class SubscController {
    @Autowired
    SubscMapper subscMapper;
    //ログ出力
    private static final Logger log = LoggerFactory.getLogger(SubscController.class);

    @GetMapping("list")
    public String list(Model model, @RequestParam(value ="customerId", required=false) Integer customerId,RedirectAttributes redirectAttributes) {
        SubscPageModel page = new SubscPageModel();
        log.info("テストログ出力");
        page.title = "サブスクリプション状況";
        if (customerId != null) {
            Optional<SubscModel> subscDateOptional = subscMapper.findById(customerId);
            if (subscDateOptional.isPresent()) {
                SubscModel subscData = subscDateOptional.get();
                page.setId(subscData.getCustomer_id());
                page.setName(subscData.getCustomer_name());
                page.setSubscribed(subscData.isIs_subscribed());
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

    @GetMapping("join")
    public String showJoinPage(Model model, @RequestParam(value ="customerId", required=false)Integer customerId,RedirectAttributes redirectAttributes) {
        SubscPageModel page = new SubscPageModel();
//        Integer customerId = getCustomerIdFromCookie(request);
//        Integer customerId = (Integer)request.getAttribute("customerId");

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
                return "redirect:/subscription/list?customerId="+ customerId;
            } else {
                page.setTitle("サブスクリプション加入画面");
                model.addAttribute("page", page);
                return "subscription/subscriptionJoin";
            }
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "お客様のユーザー情報が見つかりませんでした。");
            page.setId(customerId); 
            page.setTitle("ユーザー情報が見つかりません"); 
            page.setName("不明なユーザー"); 
            page.setSubscribed(false); 
            model.addAttribute("page", page); 
            return "redirect:/subscription/list?customerId="+ customerId;
        }
    }

    @PostMapping("doJoin")
    public String doJoin(
            @RequestParam("customerId")Integer customerId,
            @RequestParam("cardNumber") String cardNumber,
            @RequestParam("cardHolderName") String cardHolderName,
            @RequestParam("expiryDate") String expiryDate,
            @RequestParam("securityCode") String securityCode,
            RedirectAttributes redirectAttributes, Model model) {
        if (customerId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ログインしていません。");
            return "subscription/subscription";// 本当はログイン画面
        }

        if (cardNumber.isEmpty() || cardHolderName.isEmpty() || expiryDate.isEmpty() || securityCode.isEmpty()) {
            model.addAttribute("errorMessage", "全てのクレジットカード情報を入力してください。");
            SubscPageModel page = new SubscPageModel();
            page.setTitle("サブスクリプション加入画面");
            page.setCardNumber(cardNumber);
            page.setCardHolderName(cardHolderName);
            page.setExpiryDate(expiryDate);
            page.setSecurityCode(securityCode);
            model.addAttribute("page", page);
            return "subscription/subscriptionJoin";
        }

        SubscModel updateCustomerData = new SubscModel();
        updateCustomerData.setCustomer_id(customerId);
        updateCustomerData.setIs_subscribed(true);
        updateCustomerData.setsubscription_plan_id(1);
        updateCustomerData.setSubscription_start_date(OffsetDateTime.now());
        updateCustomerData.setCredit_number(cardNumber);
        updateCustomerData.setCredit_name(cardHolderName);
        updateCustomerData.setCredit_date(expiryDate);
        updateCustomerData.setSecurity_code(securityCode);

        try {
            subscMapper.updateSubscriptionInfo(updateCustomerData);
            redirectAttributes.addFlashAttribute("successMessage", "サブスクリプションに加入しました！");
            return "redirect:/subscription/list?customerId="+ customerId;
        } catch (Exception e) {
            System.err.println("サブスクリプション登録エラー：" + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "サブスクリプションの登録に失敗しました。");
            model.addAttribute("errorMessage", "サブスクリプションの登録に失敗しました。（DBエラー）"); 
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

    @GetMapping("withdrawal")
    public String showWithdrawalPage(Model model,@RequestParam(value="customerId",required = false) Integer customerId,RedirectAttributes redirectAttributes){
        SubscPageModel page=new SubscPageModel();
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

            if (! data.isIs_subscribed()) {
                redirectAttributes.addFlashAttribute("errorMessage","お客様はサブスクリプションに加入していません。");
                return "redirect:/subscription/list?customerId="+ customerId;
            } else {
                page.setTitle("サブスクリプション退会確認");
                model.addAttribute("page", page);
                return "subscription/subscriptionWithdrawal";
            }
        }else{
            redirectAttributes.addFlashAttribute("errorMessage", "お客様のユーザー情報が見つかりませんでした。");
            return "redirect:/subscription/list?customerId=" + customerId;
        }
       
    }

    @PostMapping("doWithdrawal")
    public String doWithdrawal(Model model,@RequestParam(value="customerId",required = false) Integer customerId,RedirectAttributes redirectAttributes){
        SubscPageModel page=new SubscPageModel();
        if (customerId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ログインしてください。");
            return "subscription/subscription";// 本当はログインページ
        }
        try{
            subscMapper.unsubscribe(customerId);
            page.setTitle("退会完了");
            page.setId(customerId);
            redirectAttributes.addFlashAttribute("successMessage","サブスクリプションを退会しました。");
            model.addAttribute("page", page);
            return "subscription/subscriptionWithdrawalCon";
        }catch(Exception e){
            System.err.println("サブスクリプション退会エラー"+e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "サブスクリプションの退会に失敗しました。");
            return "redirect:/subscription/withdrawal?customerId=" + customerId;
        }
    }
    
    
}
    

