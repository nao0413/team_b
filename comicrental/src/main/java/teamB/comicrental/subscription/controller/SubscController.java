package teamB.comicrental.subscription.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import jakarta.servlet.http.Cookie;          
import jakarta.servlet.http.HttpServletRequest; 
//import jakarta.servlet.http.HttpServletResponse;
import teamB.comicrental.subscription.model.SubscPageModel;
import teamB.comicrental.subscription.repository.SubscMapper;
import teamB.comicrental.subscription.repository.SubscModel;

import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



/**
 * SubscriptionController.java
 */

 @Controller
public class SubscController {
    @Autowired
    SubscMapper subscMapper;

    @GetMapping("/subscription")
    public String list(Model model, HttpServletRequest request){
        SubscPageModel page=new SubscPageModel();
        page.title="サブスクリプション状況";
        
        //クッキーからcustomer_idを取得
        Integer customerId=null;
        jakarta.servlet.http.Cookie[] cookies=request.getCookies();
        if(cookies !=null){
            for(Cookie cookie:cookies){
                if("customerId".equals(cookie.getName())){
                    try{
                        customerId=Integer.parseInt(cookie.getValue());
                        break;
                    }catch(NumberFormatException e){
                        System.err.println("Cookie 'customerId' value is not a valid number:"+cookie.getValue());
                    }
                }
            }
        }
        if(customerId !=null){
            Optional<SubscModel>subscDateOptional=subscMapper.findById(customerId);
            if(subscDateOptional.isPresent()){
                SubscModel subscDate=subscDateOptional.get();
                page.setId(subscDate.getCustomer_id());
                page.setName(subscDate.getCustomer_name());
                page.setSubscribed(subscDate.isIs_subscribed());
            }else{
                page.title="ユーザー情報が見つかりません";
                page.setId(null);
                page.setName("不明");
                page.setSubscribed(false);
            }
        }else{
            page.title="ログインしていません";
            page.setId(null);
            page.setName("ゲスト");
            page.setSubscribed(false);
        }
        model.addAttribute("page",page);
        return "subscription/subscription";
         // ★★★ クッキーをセットするテスト用のメソッド（開発中に便利）
    // ★★★ 実際にはログイン処理でクッキーがセットされるはずです。
    //@GetMapping("set-cookie")
    //public String setCookie(HttpServletResponse response) {
    //    Cookie cookie = new Cookie("customerId", "1"); 
    //    cookie.setMaxAge(60 * 60 * 24); // 1日有効
    //    cookie.setPath("/"); // アプリケーション全体で有効
    //    response.addCookie(cookie);
    //    return "redirect:/customer/list"; // listページへリダイレクト
    // }
    // @GetMapping("join")
    // public String showJoinPage(Model model, HttpServletRequest request,RedirectAttributes redirectAttributes) {
    //     SubscPageModel page=new SubscPageModel();
    //     Integer customerId=getCustomerIdFromCookie(request);

    //     if(customerId==null){
    //         redirectAttributes.addFlashAttribute("errorMessage","ログインしてください。");
    //         return "subscription/subscription";//本当はログインページ
    //     }

    //     Optional<SubscModel>existingSubscData=subscMapper.findById(customerId);
    //     if(existingSubscData.isPresent()){
    //         SubscModel data=existingSubscDate.get();
    //         page
    //     }
    }
    
    
}
