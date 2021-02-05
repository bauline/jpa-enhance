package cn.liuqx.data.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author liuqx
 * @date 2021/2/5
 */
@Entity
@Data
public class OrderGoods {
    @Id
    String id;
    String orderId;
    String goodsId;
    String goodsName;
}
