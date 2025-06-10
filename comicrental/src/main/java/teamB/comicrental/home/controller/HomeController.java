package teamB.comicrental.home.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import teamB.comicrental.top.model.Comic;
import teamB.comicrental.home.repository.ComicHomeMapper;

import java.util.List;

@Controller
public class HomeController {

    @Autowired
private ComicHomeMapper comicHomeMapper;  // 変数名を統一

@GetMapping("/home")
public String showHomePage(Model model) {
    List<Comic> popularComics = comicHomeMapper.findTopComics();
    List<Comic> recentComics = comicHomeMapper.findRecentComics();
    model.addAttribute("popularComics", popularComics);
    model.addAttribute("recentComics", recentComics);
    return "home/home";
}
}