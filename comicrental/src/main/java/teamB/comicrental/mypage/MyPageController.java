package teamB.comicrental.mypage; // あなたのパッケージに合わせて変更

import jakarta.servlet.http.HttpSession; // HttpSessionをインポート
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import teamB.comicrental.login.repository.LoginModel; // LoginModelをインポート (会員情報を取得するため)
import teamB.comicrental.account.repository.AccountMapper; // AccountMapperをインポート (DBから会員情報を取得するため)
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/mypage") // マイページ関連のURLのベースパス
public class MyPageController {

    @Autowired
    private AccountMapper accountMapper; // データベースから会員情報を取得するため

    @GetMapping // URL: http://localhost:8080/mypage
    public String showMyPage(HttpSession session, Model model) {
        // セッションからログイン中のユーザーidを取得
        Integer loggedInUserId = (Integer) session.getAttribute("loggedInUserId");

        if (loggedInUserId == null) {
            // セッションにユーザー情報がない場合（ログインしていない、またはセッション切れ）
            // ログインページにリダイレクトする
            return "redirect:/login/loginpage";
        }

        // ログイン中のユーザー名を使って、データベースから詳細な会員情報を取得
        // AccountMapperにfindByUsernameメソッドを実装済みのはずです
        LoginModel user = accountMapper.findByUserId(loggedInUserId);

        if (user == null) {
            // セッションにはユーザー名があるが、DBからユーザーが見つからない（データ不整合など）
            session.invalidate(); // セッションを破棄
            return "redirect:/login/loginpage"; // ログインページにリダイレクト
        }

        // HTML（Thymeleaf）で表示するためにモデルに会員情報を追加
        model.addAttribute("loggedInUsername", user.getUsername());
        // LoginModelにcustomer_idがなければ、適宜変更してください
        model.addAttribute("memberId", user.getUserId()); // 例: 会員番号
        model.addAttribute("email", user.getEmail()); // 例: メールアドレス

        return "mypage/mypage"; // resources/templates/mypage/mypage.html を返す
    }

    @GetMapping("/logoutConfirm")
    public String showLogoutConfirmPage() {
        // ログアウト確認用のHTMLテンプレートを返す
        // ファイル名を 'logout_confirmation.html' に変更
        return "login/logout_confirmation"; // ★ここを変更しました
    }

    @PostMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate();
        model.addAttribute("registrationSuccess", true);
        return "/login/logout_success";
    }
}
