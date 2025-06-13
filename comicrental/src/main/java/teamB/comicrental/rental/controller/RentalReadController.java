package teamB.comicrental.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import teamB.comicrental.top.model.Comic;
import teamB.comicrental.rental.repository.RentalMapper;

@Controller
public class RentalReadController {

    @Autowired
    private RentalMapper rentalMapper;

    @GetMapping("/rental/read/{id}")
    public String readComic(@PathVariable("id") int comicId, Model model) {
        Comic comic = rentalMapper.findComicById(comicId); // comic_idで1件取得
        if (comic == null) {
            return "redirect:/rental/status";
        }

        model.addAttribute("comic", comic);
        return "rental/read"; // → templates/rental/read.html に遷移
    }
}
