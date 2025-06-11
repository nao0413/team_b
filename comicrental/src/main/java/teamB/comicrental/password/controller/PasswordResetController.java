// package teamB.comicrental.password.controller;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.ModelAttribute;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import teamB.comicrental.account.repository.AccountMapper; // AccountMapperをインポート
// import teamB.comicrental.login.repository.LoginModel; // LoginModelをインポート
// import teamB.comicrental.password.model.ResetPasswordForm; // 作成するフォーム用モデル

// @Controller
// @RequestMapping("/password") // パスワード関連のURLのベースパス
// public class PasswordResetController {

//     @Autowired
//     private AccountMapper accountMapper; // AccountMapperを再利用

//     // パスワードリセットリクエストページの表示
//     // URL: http://localhost:8080/password/request
//     @GetMapping("/request")
//     public String showResetPasswordRequestPage(Model model) {
//         // フォームオブジェクトをModelに追加
//         // Thymeleafでth:object="${resetPasswordForm}"としてバインドするため
//         model.addAttribute("resetPasswordForm", new ResetPasswordForm());
//         return "password/reset_request"; // templates/password/reset_request.html を返す
//     }

//     // 名前とメールアドレスの確認フォームの送信を受け取る
//     @PostMapping("/check") // 例: フォームのactionが "/password/check"
//     public String checkUserForPasswordReset(@ModelAttribute ResetPasswordForm form, Model model) {

//         // データベースに名前とメールアドレスが一致するユーザーがいるかチェック
//         // accountMapper.findByUsernameAndEmail() メソッドが必要
//         LoginModel user = accountMapper.findByUsernameAndEmail(form.getName(), form.getEmail());

//         if (user != null) {
//             // ユーザーが見つかった場合、次のステップ（パスワード入力画面など）へリダイレクト
//             // ここでは仮に、新しいパスワードを入力する画面へリダイレクトするとします。
//             // 実際には、セキュリティのためにワンタイムトークンなどを発行してメールで送るフローが一般的ですが、
//             // 今回は直接的な画面遷移で進めます。
//             System.out.println("ユーザー確認成功: " + user.getUsername());
//             // TODO: 次のステップのURLにリダイレクト。例: return "redirect:/password/reset";
//             // 現時点では確認メッセージを表示してリクエストページに戻る
//             model.addAttribute("resetMessage", "ユーザー情報を確認しました。次のステップへ進んでください。");
//             return "password/reset_request";
//         } else {
//             // ユーザーが見つからない場合、エラーメッセージを表示してフォームに戻る
//             model.addAttribute("resetError", "入力された名前とメールアドレスに一致するユーザーは見つかりませんでした。");
//             return "password/reset_request";
//         }
//     }
// }

package teamB.comicrental.password.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession; // HttpSession をインポート

import teamB.comicrental.account.repository.AccountMapper;
import teamB.comicrental.login.repository.LoginModel;
import teamB.comicrental.password.model.ResetPasswordForm; // フォーム用モデル

@Controller
@RequestMapping("/password")
public class PasswordResetController {

    @Autowired
    private AccountMapper accountMapper;

    // パスワードリセットリクエストページの表示
    // URL: http://localhost:8080/password/request
    @GetMapping("/request")
    public String showResetPasswordRequestPage(Model model) {
        model.addAttribute("resetPasswordForm", new ResetPasswordForm());
        return "password/reset_request";
    }

    // 名前とメールアドレスの確認フォームの送信を受け取る
    @PostMapping("/check")
    public String checkUserForPasswordReset(@ModelAttribute ResetPasswordForm form,
            Model model,
            HttpSession session, // セッションを追加
            RedirectAttributes redirectAttributes) {

        LoginModel user = accountMapper.findByUsernameAndEmail(form.getName(), form.getEmail());

        if (user != null) {
            // ユーザーが見つかった場合、セッションにユーザー名（またはID）を一時保存
            // これにより、次のページでどのユーザーのパスワードを変更するか識別できる
            session.setAttribute("testAttribute", "hello");
            session.setAttribute("resettingUsername", user.getUsername());
            // session.setMaxInactiveInterval(5 * 60); // パスワードリセットセッションの有効期限を5分に設定

            // 次のステップ（新しいパスワード入力画面）へリダイレクト
            return "redirect:/password/reset"; // ★★★ ここを変更 ★★★
        } else {
            model.addAttribute("resetError", "入力された名前とメールアドレスに一致するユーザーは見つかりませんでした。");
            return "password/reset_request";
        }
    }

    // 新しいパスワード入力ページの表示
    // URL: http://localhost:8080/password/reset
    @GetMapping("/reset")
    public String showResetPasswordPage(Model model, HttpSession session) {
        String testAttribute = (String) session.getAttribute("testAttribute"); // ★★★ 追加 ★★★
        System.out.println("Test Attribute: " + testAttribute);
        // セッションにリセット対象のユーザー名があるか確認
        String resettingUsername = (String) session.getAttribute("resettingUsername");
        if (resettingUsername == null) {
            // セッションに情報がない場合、リクエストページに戻す（不正アクセス対策）
            return "redirect:/password/request";
        }
        model.addAttribute("resetPasswordForm", new ResetPasswordForm()); // 新しいパスワード入力フォーム
        return "password/reset_password"; // templates/password/reset_password.html を返す
    }

    // 新しいパスワードの送信を受け取り、DBを更新する
    @PostMapping("/reset")
    public String resetPassword(@ModelAttribute ResetPasswordForm form,
            Model model,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        String resettingUsername = (String) session.getAttribute("resettingUsername");

        // セッションにユーザー名がない、またはフォームの名前と一致しない場合は不正アクセスとみなす
        if (resettingUsername == null || !resettingUsername.equals(form.getName())) {
            session.invalidate(); // セッションを破棄
            return "redirect:/password/request"; // リクエストページに戻す
        }

        // パスワードとパスワード再入力が一致するかチェック (このフォームでも必要)
        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            model.addAttribute("newPasswordError", "パスワードとパスワード再入力が一致しません。");
            return "password/reset_password"; // エラーメッセージ付きでフォームに戻る
        }

        try {
            // データベースのパスワードを更新
            // AccountMapperにupdatePasswordByUsernameAndEmailなどのメソッドが必要
            // 今回はユーザー名だけで更新するメソッド (findByUsernameAndEmailでユーザーが特定できているため)
            accountMapper.updatePasswordByUsername(resettingUsername, form.getPassword());

            session.removeAttribute("resettingUsername"); // パスワードリセットセッション情報を削除

            redirectAttributes.addFlashAttribute("loginSuccessMessage", "パスワードが正常に変更されました。新しいパスワードでログインしてください。");
            return "redirect:/password/success"; // パスワード変更確認画面にリダイレクト
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("newPasswordError", "パスワードの更新中にエラーが発生しました。再度お試しください。");
            return "password/reset_password";
        }
    }

    // パスワード変更完了ページの表示
    // URL: http://localhost:8080/password/success
    @GetMapping("/success")
    public String showPasswordChangedSuccessPage() {
        return "password/password_changed_success"; // templates/password/password_changed_success.html を返す
    }
}