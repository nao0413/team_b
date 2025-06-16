package teamB.comicrental.comictable.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import org.springframework.ui.Model;

import teamB.comicrental.comictable.model.ComicPageModel;
import teamB.comicrental.comictable.repository.ComicMapper;
import teamB.comicrental.comictable.repository.ComicModel;
import teamB.comicrental.comictable.repository.ComicService;
import teamB.comicrental.shoppingcart.model.Cart;
import teamB.comicrental.shoppingcart.repository.CartMapper;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * ComicController
 */

@Controller
@RequestMapping("comics")
public class ComicController {

   @Autowired
   private ComicMapper comicMapper;
   @Autowired
   private CartMapper cartMapper;
   @Autowired
   private ComicService comicService;

   // 漫画を一覧で表示する
   @GetMapping("table")
   public String showComictable(@RequestParam(name = "sort", defaultValue = "comicId") String sortOrder, Model model,
         HttpSession session, RedirectAttributes redirectAttributes) {
      String loggedInUsername = (String) session.getAttribute("loggedInUser");
      Integer customerId = (Integer) session.getAttribute("loggedInUserId");
      if (loggedInUsername == null) {
         redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です。");
         return "redirect:/login/loginpage";
      }
      List<ComicModel> comicList = comicService.getComicsBasedOnSortOrder(sortOrder, customerId);
      model.addAttribute("comicList", comicList);
      model.addAttribute("customerId", customerId);
      model.addAttribute("currentSort", sortOrder);
      ComicPageModel page = new ComicPageModel();
      page.setId(customerId);
      model.addAttribute("page", page);
      return "comictable/comictable";
   }

   // レンタルしたい漫画を追加する処理
   @PostMapping("addToCart")
   public String addToCart(@RequestParam("comicId") Integer comicId,
         @RequestParam(value = "volume", required = false) Integer volume, HttpSession session,
         RedirectAttributes redirectAttributes) {
      String loggedInUsername = (String) session.getAttribute("loggedInUser");
      Integer customerId = (Integer) session.getAttribute("loggedInUserId");
      if (loggedInUsername == null) {
         redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です。");
         return "redirect:/login/loginpage";
      }
      // 漫画の巻数がNULLの場合、１を設定する（今回は巻数の情報を入れてないため全て１になる）
      if (volume == null) {
         volume = 1;
      }
      // 買い物かごへ追加したい漫画の情報をセットする
      Cart cartItem = new Cart();
      cartItem.setCustomer_id(customerId);
      cartItem.setComic_id(comicId);
      cartItem.setVolume(volume);
      Date rentalExpireDate = Date.from(LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant());
      cartItem.setRental_expire(rentalExpireDate);
      cartItem.setIs_deleted(false);

      try {
         // 買い物かごへ漫画を追加する処理
         cartMapper.insert(cartItem);
         redirectAttributes.addFlashAttribute("successMessage", "漫画をカートに追加しました！");
         return "redirect:/comics/table";
      } catch (Exception e) {
         System.err.println("買い物かご登録エラー：" + e.getMessage());
         e.printStackTrace();
         redirectAttributes.addFlashAttribute("errorMessage", "カートへの追加に失敗しました。");
         return "redirect:/comics/table";
      }
   }

   @PostMapping("addToCartDetail")
   public String addToCartDetail(@RequestParam("comicId") Integer comicId,
         @RequestParam(value = "volume", required = false) Integer volume, HttpSession session,
         RedirectAttributes redirectAttributes) {
      String loggedInUsername = (String) session.getAttribute("loggedInUser");
      Integer customerId = (Integer) session.getAttribute("loggedInUserId");
      if (loggedInUsername == null) {
         redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です。");
         return "redirect:/login/loginpage";
      }
      // 漫画の巻数がNULLの場合、１を設定する（今回は巻数の情報を入れてないため全て１になる）
      if (volume == null) {
         volume = 1;
      }
      // 買い物かごへ追加したい漫画の情報をセットする
      Cart cartItem = new Cart();
      cartItem.setCustomer_id(customerId);
      cartItem.setComic_id(comicId);
      cartItem.setVolume(volume);
      Date rentalExpireDate = Date.from(LocalDate.now().plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant());
      cartItem.setRental_expire(rentalExpireDate);
      cartItem.setIs_deleted(false);

      try {
         // 買い物かごへ漫画を追加する処理
         cartMapper.insert(cartItem);
         redirectAttributes.addFlashAttribute("successMessage", "漫画をカートに追加しました！");
         return "redirect:/comics/detail/" + comicId;
      } catch (Exception e) {
         System.err.println("買い物かご登録エラー：" + e.getMessage());
         e.printStackTrace();
         redirectAttributes.addFlashAttribute("errorMessage", "カートへの追加に失敗しました。");
         return "redirect:/comics/detail/" + comicId;
      }
   }

   @GetMapping("/detail/{comicId}")
   public String showComicDetail(Model model, @PathVariable("comicId") Integer comicId, HttpSession session,
         RedirectAttributes redirectAttributes) {
      String loggedInUsername = (String) session.getAttribute("loggedInUser");
      Integer customerId = (Integer) session.getAttribute("loggedInUserId");
      if (loggedInUsername == null) {
         redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です。");
         return "redirect:/login/loginpage";
      }
      Optional<ComicModel> comicOptional = comicMapper.findByComicId(comicId,customerId);
      if (comicOptional.isPresent()) {
         ComicModel comic = comicOptional.get();
         model.addAttribute("comic", comic);
         model.addAttribute("customerId", customerId);
         return "comictable/comicdetail";
      } else {
         redirectAttributes.addFlashAttribute("errorMessage", "指定された漫画が見つかりません。");
         return "redirect:comics/table";
      }
   }

   @GetMapping("recommend")
   public String showReccomendedComics(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
      String loggedInUsername = (String) session.getAttribute("loggedInUser");
      Integer customerId = (Integer) session.getAttribute("loggedInUserId");
      if (loggedInUsername == null) {
         redirectAttributes.addFlashAttribute("errorMessage", "ログインが必要です。");
         return "redirect:/login/loginpage";
      }
      ComicPageModel page = new ComicPageModel();
      page.setTitle("おすすめ漫画 2.5次元舞台化特集");
      List<ComicModel> recommendedComics = comicMapper.findRecommendedComics();
      model.addAttribute("recommendedComics", recommendedComics);
      model.addAttribute("customerId", customerId);
      model.addAttribute("page", page);
      return "comictable/comicrecommend";
   }

}
