package teamB.comicrental.comictable.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * ComicMapper.java
 */

 @Mapper
public interface ComicMapper {
    @Select("select*from comic ORDER BY comic_id")
    public List<ComicModel> findAll();

    @Select("select c.comic_id,c.title,c.author,c.explanatory,c.category_id,ca.catedory_name,c.comic_image from comic c LEFT JOIN category ca ON c.category_id=ca.category_id ORDER BY comic_id")
    public List<ComicModel> findAllCategory();
    
}
