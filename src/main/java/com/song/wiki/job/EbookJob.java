package com.song.wiki.job;

import com.song.wiki.service.DocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class EbookJob {

    @Resource
    private DocService docService;

    private static final Logger LOG = LoggerFactory.getLogger(EbookJob.class);

    /**
     * 每30秒更新电子书信息
     */
    @Scheduled(cron = "5/30 * * * * ?")
    public void cron() {
        LOG.info("开始更新电子书下的文档数据");
        long start = System.currentTimeMillis();
        docService.updateEbookInfo();
        LOG.info("更新电子书下的文档数据结束，耗时:{}毫秒",System.currentTimeMillis()-start);
    }

}
