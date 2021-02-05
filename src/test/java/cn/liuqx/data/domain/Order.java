package cn.liuqx.data.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.util.List;

/**
 * @author liuqx
 * @date 2021/2/5
 */
@Entity
@Data
public class Order {
    @Id
    private String id;
    private String sku;
    @Transient
    List<OrderGoods> goods;
}
