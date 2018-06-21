package com.sharding.app.order;

import com.hero.fitness.utils.Result;
import com.hero.fitness.utils.ResultUtils;
import io.shardingjdbc.core.keygen.DefaultKeyGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @program: app
 * @description:
 * @author: XiaoYu
 * @create: 2018-06-15 12:09
 **/
@RestController
@Slf4j
public class OrderWeb {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    DefaultKeyGenerator defaultKeyGenerator;

    @PostMapping("/createOrder")
    public Result createOrder(@Valid @RequestBody Order request, BindingResult bindingResult) {
        //Long id = defaultKeyGenerator.generateKey().longValue();
        //log.info("id={}", id.toString());
        //request.setId(id);
        orderRepository.save(request);
        return ResultUtils.success();
    }

    @GetMapping("/getOrder/{id}")
    public Result getOrder(@PathVariable Long id) {
        log.info(id.toString());
        Order order=orderRepository.findFirstById(id);
        return ResultUtils.success(orderRepository.getOne(id));
    }
}
