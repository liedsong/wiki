package com.song.wiki;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;


@SpringBootApplication
public class JavaWikiApplication {

    private static final Logger LOG = LoggerFactory.getLogger(JavaWikiApplication.class);

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(JavaWikiApplication.class);
        Environment env = app.run(args).getEnvironment();
        LOG.info("启动成功！！");
        //通过env.getProperty("server.port")得到端口地址
        LOG.info("地址: \thttp://127.0.0.1:{}", env.getProperty("server.port"));
    }

}
