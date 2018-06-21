package com.sharding.app.order;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @program: app
 * @description:
 * @author: XiaoYu
 * @create: 2018-06-15 12:05
 **/
@Entity
@Data
@Table(name = "t_order_1")
public class Order1 {

    @Id
    private Long id;

    private BigDecimal price;

    private String title;

    private Long createUserId;

}
