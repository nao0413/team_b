package teamB.comicrental.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

    @PostMapping
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

    @GetMapping("/create_new_account.html")
    public String createNewAccountPage() {
        return "login/create_new_account"; // resources/templates/login/create_new_account.html を指す
    }

    @GetMapping("/before_reset_password.html")
    public String beforeResetPasswordPage() {
        return "login/before_reset_password"; // resources/templates/login/before_reset_password.html を指す
    }

}