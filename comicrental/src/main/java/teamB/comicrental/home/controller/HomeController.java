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
private TopComicMapper comicMapper;  // 変数名を統一

@GetMapping("/home")
public String showHomePage(Model model) {
    List<Comic> popularComics = comicMapper.findTopComics();
    List<Comic> recentComics = comicMapper.findRecentComics();
    model.addAttribute("popularComics", popularComics);
    model.addAttribute("recentComics", recentComics);
    return "home/home";
}
}