package teamB.comicrental.login.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface LoginMapper {
        @Select("SELECT customer_name AS username, customer_pw AS password " + "FROM customer "
                        + "WHERE customer_name = #{username} AND customer_pw = #{password}")
        LoginModel findByUsernameAndPassword(@Param("username") String username,
                        @Param("password") String password);
}
