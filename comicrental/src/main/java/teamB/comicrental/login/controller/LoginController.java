package teamB.comicrental.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping; // 明示的にインポート
import org.springframework.web.bind.annotation.ModelAttribute; // 明示的にインポート
import org.springframework.web.bind.annotation.PostMapping; // 明示的にインポート
import org.springframework.web.bind.annotation.RequestMapping;

import teamB.comicrental.login.model.LoginPageModel;
//import teamB.comicrental.login.repository.LoginModel;
import teamB.comicrental.login.repository.LoginMapper;

/**
 * CustomerController
 */
@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    private LoginMapper loginMapper;

    @GetMapping("loginpage")
    public String showLoginPage(Model model) {
        model.addAttribute("loginPageModel", new LoginPageModel());
        return "login/loginpage";
    }

    @PostMapping("logincheck")
    public String login(@ModelAttribute LoginPageModel loginPageModel, Model model) {
        // LoginModel user =
        // loginMapper.findByUsernameAndPassword(loginPageModel.getUsername(),
        // loginPageModel.getPassword());

        var user = loginMapper.findByUsernameAndPassword(loginPageModel.getUsername(), loginPageModel.getPassword());

        if (user != null) {
            return "HOME/home";
        } else {
            model.addAttribute("error", "ユーザー名またはパスワードが間違っています。");
            return "login/loginpage";
        }
    }

}