package teamB.comicrental.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import teamB.comicrental.top.model.Comic;
import teamB.comicrental.top.repository.TopComicMapper;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
    private TopComicMapper comicMapper;

    @GetMapping("/home")
    public String showHome(Model model) {
        List<Comic> popularComics = comicMapper.findTopComics();
        model.addAttribute("popularComics", popularComics);
        return "home/home"; // templates/home/home.html
    }
}
