package teamB.comicrental.top.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import teamB.comicrental.top.repository.ComicMapper;
import java.util.List;
import teamB.comicrental.top.model.Comic;

@Controller
public class topcontroller {

    @Autowired
    private ComicMapper comicMapper;

    @GetMapping("/top")
    public String showTop(Model model) {
        List<Comic> topComics = comicMapper.findTopComics();
        model.addAttribute("topComics", topComics);
        return "top/top"; // → resources/templates/top/top.html を表示する
    }
}
