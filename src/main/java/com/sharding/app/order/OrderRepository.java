package com.sharding.app.order;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: app
 * @description:
 * @author: XiaoYu
 * @create: 2018-06-15 12:08
 **/
public interface OrderRepository extends JpaRepository<Order,Long> {

    Order findFirstById(Long id);
}
