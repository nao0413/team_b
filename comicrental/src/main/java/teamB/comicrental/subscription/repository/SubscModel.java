package teamB.comicrental.subscription.repository;

import java.time.OffsetDateTime;

/**
 * SubscriptionModel.java
 */
public class SubscModel {
    public int customer_id;
    public String customer_name;
    public boolean is_subscribed;
    public String credit_number;
    public String credit_name;
    public String credit_date;
    public String security_code;
    public int subscription_plan_id;
    public OffsetDateTime subscription_start_date;
    
    public int getCustomer_id() {
        return customer_id;
    }
    public void setCustomer_id(int customer_id) {
        this.customer_id = customer_id;
    }
    public String getCustomer_name() {
        return customer_name;
    }
    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
    public boolean isIs_subscribed() {
        return is_subscribed;
    }
    public void setIs_subscribed(boolean is_subscribed) {
        this.is_subscribed = is_subscribed;
    }
    public String getCredit_number() {
        return credit_number;
    }
    public void setCredit_number(String credit_number) {
        this.credit_number = credit_number;
    }
    public String getCredit_name() {
        return credit_name;
    }
    public void setCredit_name(String credit_name) {
        this.credit_name = credit_name;
    }
    public String getCredit_date() {
        return credit_date;
    }
    public void setCredit_date(String credit_date) {
        this.credit_date = credit_date;
    }
    public String getSecurity_code() {
        return security_code;
    }
    public void setSecurity_code(String security_code) {
        this.security_code = security_code;
    }
    
    public OffsetDateTime getSubscription_start_date() {
        return subscription_start_date;
    }
    public void setSubscription_start_date(OffsetDateTime subscription_start_date) {
        this.subscription_start_date = subscription_start_date;
    }
    public int getsubscription_plan_id() {
        return subscription_plan_id;
    }
    public void setsubscription_plan_id(int subscription_plan_id) {
        this.subscription_plan_id = subscription_plan_id;
    }
    
}
