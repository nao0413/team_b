package teamB.comicrental.account.controller; // パッケージ名は適宜変更してください

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes; // リダイレクト時にメッセージを渡すため追加

import teamB.comicrental.account.model.CreateAccountForm; // フォーム用モデル
import teamB.comicrental.account.repository.AccountMapper; // Mapper
import teamB.comicrental.login.repository.LoginModel; // ログイン成功後のユーザー情報（customerテーブル対応）

// 必要であれば、バリデーションのために追加
// import jakarta.validation.Valid;
// import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("account") // アカウント関連のURLのベースパス
public class AccountController {

    @Autowired
    private AccountMapper accountMapper;

    // アカウント新規作成ページの表示
    @GetMapping("create") // 例: http://localhost:8080/account/create にアクセス
    public String showCreateAccountForm(Model model) {
        // フォームオブジェクトをModelに追加
        // Thymeleafでth:object="${createAccountForm}"としてバインドするため
        model.addAttribute("createAccountForm", new CreateAccountForm());
        return "account/create_new_account"; // templates/account/create_new_account.html を返す
    }

    // アカウント新規作成フォームの送信を受け取る
    @PostMapping("register") // 例: フォームのactionが "/account/register"
    public String registerAccount(@ModelAttribute CreateAccountForm form, // @Valid と BindingResult を追加するとバリデーションが可能
            Model model,
            RedirectAttributes redirectAttributes) {

        // パスワードの確認（パスワードとパスワード再入力が一致するか）
        if (!form.getPassword().equals(form.getPasswordConfirm())) {
            model.addAttribute("createAccountError", "パスワードとパスワード再入力が一致しません。");
            return "account/create_new_account"; // エラーメッセージ付きでフォームに戻る
        }

        // 既に同じユーザー名が存在しないかチェック (Mapperにメソッドを追加)
        // LoginModel existingUser = accountMapper.findByUsername(form.getName());
        // if (existingUser != null) {
        // model.addAttribute("createAccountError", "このユーザー名は既に存在します。");
        // return "account/create_new_account";
        // }

        // データベースに登録する処理
        // AccountModel は LoginModel とほぼ同じ内容になることが多い
        // 今回は LoginModel (customerテーブルのエンティティ) を再利用します
        LoginModel newAccount = new LoginModel();
        newAccount.setUsername(form.getName());
        newAccount.setEmail(form.getEmail()); // AccountModel/LoginModelにemailフィールドが必要
        newAccount.setPassword(form.getPassword());
        newAccount.setSubscribed(false);

        try {
            // Mapperを使ってデータベースに挿入
            accountMapper.insertAccount(newAccount); // MapperにinsertAccountメソッドが必要

            // 今回はダミーで成功とする
            System.out.println("アカウント登録成功: " + newAccount.getUsername());

            // 登録成功したら、登録完了ページへリダイレクト
            // リダイレクト先にメッセージを渡す場合はRedirectAttributesを使用
            redirectAttributes.addFlashAttribute("registrationSuccess", "アカウントの作成が完了しました！ログインしてください。");
            return "redirect:/account/confirmation"; // "/account/confirmation" へリダイレクト
        } catch (Exception e) {
            // データベースエラーなどが発生した場合
            e.printStackTrace(); // ログに出力
            model.addAttribute("createAccountError", "アカウントの作成中にエラーが発生しました。再度お試しください。");
            return "account/create_new_account"; // エラーメッセージ付きでフォームに戻る
        }
    }

    // アカウント作成完了ページの表示。
    @GetMapping("confirmation")
    public String showConfirmationPage() {
        return "account/confirmation_new_account"; // templates/account/confirmation_new_account.html を返す
    }
}
