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
    public int getId() {
        return Id;
    }
    public void setId(int id) {
        Id = id;
    }
    
}
