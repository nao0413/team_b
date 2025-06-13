package teamB.comicrental.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import teamB.comicrental.rental.repository.RentalMapper;
import teamB.comicrental.rental.model.Rental;

import java.util.List;

@Controller
@RequestMapping("/rental")
public class RentalReadController {

    @Autowired
    private RentalMapper rentalMapper;

    @GetMapping("/read/{id}")
    public String readComic(@PathVariable("id") int comicId, Model model) {
        // 該当コミックのページ画像リストを取得（ここでは仮にテーブルがあると仮定）
        List<String> pages = rentalMapper.findComicPages(comicId);
        model.addAttribute("pages", pages);
        return "rental/read";
    }
}
