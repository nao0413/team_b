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
    @Select("select*from customer ORDER BY customer_id")
    public List<SubscModel> findAll();

    @Select("select customer_id,customer_name,is_subscribed from customer where customer_id=#{customerId} limit 1")
    public Optional<SubscModel> findById(@Param("customerId") int customerId);

    // @Update("update customer set
    //         is_subscribed=#{is_subscribed},
    //         credit_number=#{credit_number}
    //         credit_name=#{credit_name}
    //         credit_date=#{credit_date}
    //         security_code=#{security_code}
    //         subsciption_start_date=#{subscription_start_date} 
    //         WHERE customer_id=#{customer_id}")
    //         public void updateSubscriptionInfo(SubscModel subscModel
    //             @Param("customer_id")int customer_id,
    //             @Param("is_subscribed")boolean is_subscribed,
    //             @Param("credit_number")String credit_number,
    //             @Param("credit_name")String credit_name,
    //             @Param("credit_code")String credit_code,
    //             @Param("security_code")String security_code,
    //             @Param("subsciption_start_date")OffsetDateTime subscription_start_date;
    //             );
}
