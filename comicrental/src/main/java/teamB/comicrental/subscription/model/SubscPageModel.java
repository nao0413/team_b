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
    private String cardNumber;
    private String cardHolderName;

    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    public String getCardHolderName() {
        return cardHolderName;
    }
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    public String getSecurityCode() {
        return securityCode;
    }
    public void setSecurityCode(String securityCode) {
        this.securityCode = securityCode;
    }
    private String expiryDate;
    private String securityCode;

    
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
}
