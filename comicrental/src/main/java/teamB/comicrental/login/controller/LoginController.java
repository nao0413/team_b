package teamB.comicrental.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import teamB.comicrental.login.model.LoginPageModel;
import teamB.comicrental.login.repository.LoginMapper;

/**
 * CustomerController
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    LoginMapper loginmapper;

    @GetMapping("login")
    public String list(Model model) {

        LoginPageModel page = new LoginPageModel();
        page.title = "ログイン画面";
        model.addAttribute("page", page);

        return "login/loginpage"; // テンプレートファイルを指定
    }
}