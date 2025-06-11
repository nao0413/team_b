package teamB.comicrental.account.repository;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.Param;
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

        @Select("SELECT customer_id, customer_name AS username, customer_pw AS password, customer_email AS email " +
                        "FROM customer WHERE customer_name = #{name} AND customer_email = #{email}")
        LoginModel findByUsernameAndEmail(@Param("name") String name, @Param("email") String email);

        @Update("UPDATE customer SET customer_pw = #{newPassword} WHERE customer_name = #{username}")
        void updatePasswordByUsername(@Param("username") String username, @Param("newPassword") String newPassword);

        @Select("SELECT customer_id, customer_name AS username, customer_pw AS password, customer_email AS email " +
                        "FROM customer WHERE customer_name = #{username}")
        LoginModel findByUsername(@Param("username") String username);

        @Select("SELECT customer_id AS userId, customer_name AS username, customer_pw AS password, customer_email AS email, is_subscribed "
                        +
                        "FROM customer WHERE customer_id = #{userId}")
        LoginModel findByUserId(@Param("userId") Integer userId);
}