package teamB.comicrental.home.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import teamB.comicrental.top.model.Comic;

@Mapper
public interface ComicHomeMapper {

    // 人気タイトル（レンタル回数降順、上位10件）
    @Select("SELECT * FROM comics ORDER BY rentaltimes DESC LIMIT 10")
    List<Comic> findTopComics();

    // 最近入荷タイトル（入荷日降順、上位10件）
    @Select("SELECT * FROM comics ORDER BY arrival_date DESC LIMIT 10")
    List<Comic> findRecentComics();

    // ジャンル指定で人気タイトル取得
    @Select("SELECT * FROM comics WHERE genre = #{genre} ORDER BY rentaltimes DESC LIMIT 10")
    List<Comic> findTopComicsByGenre(String genre);

    // ジャンル指定で最近入荷タイトル取得
    @Select("SELECT * FROM comics WHERE genre = #{genre} ORDER BY arrival_date DESC LIMIT 10")
    List<Comic> findRecentComicsByGenre(String genre);
}
