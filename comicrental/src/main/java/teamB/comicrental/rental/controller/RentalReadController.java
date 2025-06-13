package teamB.comicrental.rental.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import teamB.comicrental.top.model.Comic;
import teamB.comicrental.top.repository.TopComicMapper;

@Controller
public class RentalReadController {

    @Autowired
    private TopComicMapper comicMapper;

    @GetMapping("/rental/read/{id}")
    public String readComic(@PathVariable("id") int comicId, Model model) {
        Comic comic = comicMapper.findComicById(comicId);  // comic_idで取得するメソッドが必要
        if (comic == null) {
            return "redirect:/rental/status";
        }
        model.addAttribute("comic", comic);
        return "rental/read";
    }
}

