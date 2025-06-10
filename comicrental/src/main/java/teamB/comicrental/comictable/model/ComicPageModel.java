package teamB.comicrental.comictable.model;

import java.util.List;
import teamB.comicrental.comictable.repository.ComicModel;


/**
 * SubscriptionPageModel.java
 */

public class ComicPageModel {
    public String title;
    public int Id;
    public List<ComicModel> list;
    public int comic_id;

    public List<ComicModel> getList() {
        return list;
    }
    public void setList(List<ComicModel> list) {
        this.list = list;
    }
    public int getComic_id() {
        return comic_id;
    }
    public void setComic_id(int comic_id) {
        this.comic_id = comic_id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    
}
