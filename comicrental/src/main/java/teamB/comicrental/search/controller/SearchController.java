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

    // 検索フォーム表示用
    @GetMapping("/search")
    public String showSearchForm() {
        return "search/search"; // ← 検索フォーム用のHTMLを用意
    }

    // 検索実行後の結果表示
    @GetMapping("/search/result")
    public String searchByTitle(@RequestParam("title") String title, Model model) {
        List<Comic> results = comicMapper.findComicsByTitleLike("%" + title + "%");
        model.addAttribute("results", results);
        return "search/result";
    }
}

