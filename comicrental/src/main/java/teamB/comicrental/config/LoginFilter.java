package teamB.comicrental.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import org.springframework.stereotype.Component;

@Component
public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init!!");
        // ☆☆ここにログインしていなくても開けるページ☆☆
        // ログイン関連
        urls.add("^/login/loginpage$");
        urls.add("^/login$");
        urls.add("^/login/logincheck$");
        urls.add("^/login/logout_success");

        // アカウント作成関連
        urls.add("^/account/create$");
        urls.add("^/account/register$");
        urls.add("^/account/confirmation$");

        // パスワード変更関連
        urls.add("^/password/request$");
        urls.add("^/password/check$");
        urls.add("^/password/reset$");
        urls.add("^/password/success$");

        // 最初のトップ画面
        urls.add("^/top$");

        // マイページ（今だけ）
        urls.add("^/mypage$");

    }

    private List<String> urls = new ArrayList<String>();

    private boolean checkURL(String checkurl) {
        boolean flag = false;
        for (String url : urls) {
            if (checkurl.matches(url)) {
                flag = true;
                break;
            }
        }
        return flag;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String url = request.getServletPath();
        // ログイン画面に戻す処理
        if (url == null) {
            response.sendRedirect("/login/loginpage");
            return;
        }
        // セッション無しで移動できるページの指定と処理
        // } else if (url.matches("^/login") || url.matches("^/login/check") ||
        // url.equals("/top") || url.matches("^/search") || url.matches("^/search/s")||
        // url.matches("^/forget") || url.matches("^/forget/edit") ||
        // url.matches("^/newcreate") || url.matches("^/top") ||url.matches("^/faq")||
        // url.matches("^/forget")|| url.matches("^/newcreate/newcheck")||
        // url.matches("^/newcreate/login")|| url.matches("^/newcreate/newcreate") ||
        // url.matches("^/AboutUs") || url.matches("^/newcreate/newcomplete")) {

        // フィルターをかけない拡張子の指定
        if (url.matches(".+\\.(css|png|jpg|js|ico|gif|woff|ttf)$"))

        {
            chain.doFilter(request, response);
            return;
        }

        if (checkURL(url)) {
            chain.doFilter(request, response);
            return;
        }
        HttpSession session = request.getSession(false);
        if (null == session || session.getAttribute("loggedInUser") == null) {
            response.sendRedirect("/login/loginpage");
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("destroy!!");
    }
}