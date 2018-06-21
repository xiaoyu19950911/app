package com.sharding.app.order;

import com.hero.fitness.annotation.RequestValid;
import com.hero.fitness.utils.Result;
import com.hero.fitness.utils.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * @program: app
 * @description:
 * @author: XiaoYu
 * @create: 2018-06-15 17:21
 **/
@RestController
@Slf4j
public class UserWeb {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/createUser")
    public Result createUser(@RequestBody User request, BindingResult bindingResult) {
        return ResultUtils.success(userRepository.save(request));
    }

    @GetMapping("/getUser/{id}")
    @RequestValid
    public Result<User> getUser(@PathVariable Integer id) {
        log.info(id.toString());
        User user=userRepository.getOne(id);
        User user1=userRepository.findFirstById(id);
        return ResultUtils.success(user1);
    }
}
