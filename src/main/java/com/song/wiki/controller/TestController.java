package com.song.wiki.controller;

import com.song.wiki.domain.Test;
import com.song.wiki.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
public class TestController {

    @Value("${test.hello:TEST}")
    private String testHello;

    @Resource
    private TestService testService;

    @Resource
    private RedisTemplate redisTemplate;

    private static final Logger LOG = LoggerFactory.getLogger(TestController.class);

    /**
     * GET,POST,PUT,DELETE
     * <p>
     * /user?id=1
     * /user/1
     *
     * @return
     */

//    @PostMapping
//    @PutMapping
//    @DeleteMapping
//    @RequestMapping(value = "/user/1",method = RequestMethod.GET)
//    @RequestMapping(value = "/user/1",method = RequestMethod.DELETE)
    @GetMapping("/hello")
    public String hello() {
        return "Hello World" + testHello;
    }

    @PostMapping("/hello/post")
    public String hellopost(String name) {
        return "Hello World! post. " + name;
    }

    @GetMapping("/test/list")
    public List<Test> list() {
        return testService.list();
    }

    @RequestMapping("/redis/set/{key}/{value}")
    public String set(@PathVariable Long key, @PathVariable String value) {
        redisTemplate.opsForValue().set(key, value, 3600, TimeUnit.SECONDS);
        LOG.info("key:{},value:{}", key, value);
        return "success";
    }

    @GetMapping("/redis/get/{key}")
    public Object get(@PathVariable Long key) {
        Object object = redisTemplate.opsForValue().get(key);
        LOG.info("key:{},value:{}",key, object);
        return object;
    }
}
