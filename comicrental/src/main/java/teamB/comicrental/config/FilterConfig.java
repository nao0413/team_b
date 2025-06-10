package teamB.comicrental.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilterRegistration() {
        FilterRegistrationBean<LoginFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new LoginFilter()); // LoginFilterのインスタンスを設定
        registration.addUrlPatterns("/*"); // 全てのURLパターンにフィルターを適用
        registration.setOrder(1); // フィルターの実行順序
        return registration;
    }
}