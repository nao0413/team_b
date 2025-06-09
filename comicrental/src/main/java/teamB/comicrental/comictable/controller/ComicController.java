package teamB.comicrental.comictable.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.ui.Model;

import teamB.comicrental.comictable.model.ComicPageModel;
import teamB.comicrental.comictable.repository.ComicMapper;
import teamB.comicrental.comictable.repository.ComicModel;
import teamB.comicrental.shoppingcart.CartService;
import teamB.comicrental.shoppingcart.model.Cart;
import teamB.comicrental.shoppingcart.repository.CartMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;



/**
 *ComicController 
 */

 @Controller
 @RequestMapping("comics")
public class ComicController {

    @Autowired
    private ComicMapper comicMapper;
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    private CartService cartService;

    //漫画を一覧で表示する
    @GetMapping("table")
    public String showComictable(Model model,@RequestParam(value ="customerId", required=false)Integer customerId,RedirectAttributes redirectAttributes){
        if (customerId == null) {
        redirectAttributes.addFlashAttribute("errorMessage", "ログインしてください。");
        return "redirect:/login/login";
        }
        List<ComicModel> comicList=comicMapper.findAllComicsWithCategoryAndRentalStatus(customerId);
        model.addAttribute("comicList",comicList);
        model.addAttribute("customerId", customerId);
        ComicPageModel page=new ComicPageModel();
        page.setId(customerId);
        model.addAttribute("page",page);
        return "comictable/comictable";
    }

    //レンタルしたい漫画を追加する処理
    @PostMapping("addToCart")
    public String addToCart(@RequestParam("comicId") Integer comicId,@RequestParam(value = "volume",required = false) Integer volume,@RequestParam("customerId") Integer customerId,RedirectAttributes redirectAttributes){
        if (customerId == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "ログインしていません。");
            return "redirect:/login/login";
        }
        //漫画の巻数がNULLの場合、１を設定する（今回は巻数の情報を入れてないため全て１になる）
        if(volume==null){
            volume=1;
        }
        //買い物かごへ追加したい漫画の情報をセットする
        Cart cartItem=new Cart();
        cartItem.setCustomer_id(customerId);
        cartItem.setComic_id(comicId);
        cartItem.setVolume(volume);
        Date rentalExpireDate = Date.from(LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant());
        cartItem.setRental_expire(rentalExpireDate);
        cartItem.setIs_deleted(false);

        try{
            //買い物かごへ漫画を追加する処理
            cartMapper.insert(cartItem);
            redirectAttributes.addFlashAttribute("successMessage","漫画をカートに追加しました！");
            return "redirect:/comics/table?customerId="+customerId;
        }catch(Exception e){
            System.err.println("買い物かご登録エラー：" + e.getMessage());
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("errorMessage", "カートへの追加に失敗しました。");
            return "redirect:/comics/table?customerId="+customerId;
        }
        }

    }
    


