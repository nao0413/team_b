package teamB.comicrental.top.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import teamB.comicrental.top.model.Comic;

import java.util.List;

@Mapper
public interface TopComicMapper {

    @Select("""
        SELECT comic_id,title, author, comic_image AS comicImage, rentaltimes,
               category_id AS genre, arrival_date AS arrivalDate
        FROM comic
        ORDER BY rentaltimes DESC
        LIMIT 5
    """)
    List<Comic> findTopComics();

    @Select("""
        SELECT comic_id,title, author, comic_image AS comicImage, rentaltimes,
               category_id AS genre, arrival_date AS arrivalDate
        FROM comic
        ORDER BY arrival_date DESC
        LIMIT 5
    """)
    List<Comic> findRecentComics();

    @Select("""
        SELECT title, author, comic_image AS comicImage, rentaltimes,
               category_id AS genre, arrival_date AS arrivalDate
        FROM comic
        WHERE category = #{genre}
        ORDER BY rentaltimes DESC
    """)
    List<Comic> findComicsByGenre(String genre);

    @Select("""
        SELECT title, author, comic_image AS comicImage, rentaltimes,
               category_id AS genre, arrival_date AS arrivalDate
        FROM comic
        WHERE title = #{title}
        LIMIT 1
    """)
    Comic findComicByTitle(String title);

    @Select("""
        SELECT title, author, comic_image AS comicImage, rentaltimes,
               category_id AS genre, arrival_date AS arrivalDate
        FROM comic
        WHERE title ILIKE #{title}
    """)
    List<Comic> findComicsByTitleLike(String title);
}