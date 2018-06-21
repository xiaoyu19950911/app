package com.sharding.app.order;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @program: app
 * @description:
 * @author: XiaoYu
 * @create: 2018-06-15 12:10
 **/
@Data
public class OrderCreateRequest {

    private Long id;

    private String title;

    private BigDecimal price;
}
