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
public class SearchController {

    @Autowired
    private TopComicMapper comicMapper;

    @GetMapping("/search")
    public String showSearchResults(@RequestParam(value = "genre", required = false) String genre,
                                     @RequestParam(value = "title", required = false) String title,
                                     Model model) {
        List<Comic> results;

        if (genre != null && !genre.isEmpty()) {
            results = comicMapper.findComicsByGenre(genre);
        } else if (title != null && !title.isEmpty()) {
            results = comicMapper.findComicsByTitleLike("%" + title + "%");
        } else {
            results = List.of();
        }

        model.addAttribute("results", results);
        return "search/search";
    }
}
