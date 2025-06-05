package teamB.comicrental.subscription.model;

import java.util.List;

import teamB.comicrental.subscription.repository.SubscModel;

/**
 * SubscriptionPageModel.java
 */

public class SubscPageModel {
    public String title;
    public List<SubscModel> list;
    private Integer id;
    private String name;
    private boolean subscribed;
    //public String massege;
    
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<SubscModel> getList() {
        return list;
    }
    public void setList(List<SubscModel> list) {
        this.list = list;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public boolean isSubscribed() {
        return subscribed;
    }
    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
    // public String getMassege() {
    //     return massege;
    // }
    // public void setMassege(String massege) {
    //     this.massege = massege;
    // }
    
}
