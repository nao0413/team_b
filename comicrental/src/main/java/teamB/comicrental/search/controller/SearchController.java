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
        return "search/search"; // 検索フォーム画面
    }

    @GetMapping("/search/result")
    public String searchByTitle(@RequestParam("title") String title, Model model) {
        List<Comic> searchResults = comicMapper.findComicsByTitleLike("%" + title + "%");
        model.addAttribute("searchResults", searchResults);
        return "search/result"; // 検索結果表示画面
    }
}
