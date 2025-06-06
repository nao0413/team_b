package teamB.comicrental.login.model;

import java.util.List;
import teamB.comicrental.login.repository.LoginModel;

public class LoginPageModel {
    private String username;
    private String password;
    public List<LoginModel> LoginPageModel;

    // ゲッター・セッター
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
