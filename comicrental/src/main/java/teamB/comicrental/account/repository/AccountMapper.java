package teamB.comicrental.account.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
//import org.apache.ibatis.annotations.Param;
//import org.apache.ibatis.annotations.Select;
import teamB.comicrental.login.repository.LoginModel; // customerテーブルに対応するモデル

@Mapper
public interface AccountMapper {

        // 新しいアカウントをデータベースに挿入するSQL
        @Insert("INSERT INTO customer (customer_name, customer_email, customer_pw, is_subscribed) " +
                        "VALUES (#{username}, #{email}, #{password}, #{isSubscribed})") // LoginModelのフィールド名と一致
        void insertAccount(LoginModel account);

        // ユーザー名の重複チェック用
        // @Select("SELECT customer_id, customer_name AS username, customer_pw AS
        // password, customer_email AS email " +
        // "FROM customer WHERE customer_name = #{username}")
        // LoginModel findByUsername(@Param("username") String username);
}