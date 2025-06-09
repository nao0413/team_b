package teamB.comicrental.top.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import teamB.comicrental.top.model.Comic;

@Mapper
public interface ComicMapper {

    @Select("SELECT title, author, comic_image AS comicImage, rentaltimes " +
            "FROM comic ORDER BY rentaltimes DESC LIMIT 5")
    List<Comic> findTopComics();
}
