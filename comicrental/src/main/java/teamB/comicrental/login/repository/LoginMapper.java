package teamB.comicrental.login.repository;

import java.util.List;
import org.apache.ibatis.annotations.Select;

/**
 * CustomerMapper
 */
public interface LoginMapper {
    @Select("select * from customer")
    public List<LoginModel> findAll();
}