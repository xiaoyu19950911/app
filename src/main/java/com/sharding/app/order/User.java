package com.sharding.app.order;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @program: app
 * @description:
 * @author: XiaoYu
 * @create: 2018-06-15 17:18
 **/
@Entity
@Data
public class User{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nickName;

    private String userName;

    private String passWord;
}
