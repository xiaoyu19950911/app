package com.sharding.app.order;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @program: app
 * @description:
 * @author: XiaoYu
 * @create: 2018-06-15 17:20
 **/
public interface UserRepository extends JpaRepository<User,Integer> {

    User findFirstById(Integer id);
}
