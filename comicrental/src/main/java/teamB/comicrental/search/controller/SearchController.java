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
    public String showSearchForm() {
        return "search/search";
    }

    @GetMapping("/search/result")
    public String searchByTitle(@RequestParam("title") String title,
                                 @RequestParam(value = "message", required = false) String message,
                                 @RequestParam(value = "status", required = false) String status,
                                 Model model) {
        List<Comic> results = comicMapper.findComicsByTitleLike("%" + title + "%");
        model.addAttribute("results", results);
        model.addAttribute("message", message);
        model.addAttribute("status", status); // 成功 or 失敗
        return "search/result";
    }
}
