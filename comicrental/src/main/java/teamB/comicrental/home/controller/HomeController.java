package teamB.comicrental.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import teamB.comicrental.top.repository.TopComicMapper;

@Controller
public class HomeController {

    @Autowired
    private TopComicMapper comicMapper;

    @GetMapping("/home")
    public String showHome(Model model) {
        model.addAttribute("topComics", comicMapper.findTopComics()); // topと同じ名前で渡す
        return "home/home"; // HTMLの場所は home/home.html にします
    }
}
