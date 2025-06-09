package teamB.comicrental.subscription.repository;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

/**
 * SubscriptionMapper.java
 */

 @Mapper
public interface SubscMapper {
    //customerテーブルの情報を全て得る
    @Select("select*from customer ORDER BY customer_id")
    public List<SubscModel> findAll();
    //customer_idに合わせて情報を検索する
    @Select("select customer_id,customer_name,is_subscribed from customer where customer_id=#{customerId} limit 1")
    public Optional<SubscModel> findById(@Param("customerId") int customerId);
    //サブスクリプションに加入した際にDBを更新する
    @Update("UPDATE customer SET is_subscribed=#{is_subscribed},subscription_plan_id=#{subscription_plan_id},credit_number=#{credit_number},credit_name=#{credit_name},credit_date=#{credit_date},security_code=#{security_code},subscription_start_date=#{subscription_start_date} WHERE customer_id=#{customer_id}")
            public void updateSubscriptionInfo(SubscModel subscModel);
    //サブスクリプションを退会した際にDBを更新する 
    @Update("UPDATE customer SET is_subscribed=FALSE,subscription_plan_id=NULL,credit_number=NULL,credit_name=NULL,credit_date=NULL,security_code=NULL WHERE customer_id=#{customer_id}")
            public void unsubscribe(@Param("customer_id") int customer_id);
}
