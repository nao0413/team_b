package teamB.comicrental.search.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import teamB.comicrental.top.model.Comic;
import teamB.comicrental.top.repository.TopComicMapper;

import java.util.List;

@Controller
public class SearchResultController {

    @Autowired
    private TopComicMapper comicMapper;

    @GetMapping("/search/result")
    public String showSearchResult(@RequestParam("title") String title, Model model) {
        List<Comic> results = comicMapper.findComicsByTitleLike("%" + title + "%");
        model.addAttribute("results", results);
        return "search/result";
    }
}
