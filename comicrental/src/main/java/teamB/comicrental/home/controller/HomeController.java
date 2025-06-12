package teamB.comicrental.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import teamB.comicrental.top.repository.TopComicMapper;
import teamB.comicrental.top.model.Comic;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private TopComicMapper comicMapper;

    @GetMapping("/top")
    public String showTopPage(Model model) {
        List<Comic> topComics = comicMapper.findTopComics();
        model.addAttribute("topComics", topComics);
        return "top/top"; // maps to templates/top/top.html
    }
}

