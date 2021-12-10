package com.song.wiki.job;

import com.song.wiki.service.DocService;
import com.song.wiki.service.EbookSnapshotService;
import com.song.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class EbookSnapshotJob {

    @Resource
    private EbookSnapshotService ebookSnapshotService;

    @Resource
    private SnowFlake snowFlake;

    private static final Logger LOG = LoggerFactory.getLogger(EbookSnapshotJob.class);

    /**
     * 自定义cron表达式
     * 只有等上一次执行完成，下一次才会在下一个时间点执行，错过就错过
     * 每30秒更新电子书信息
     */
    @Scheduled(cron = "0/5 * * * * ?")
    public void cron() {
        //内置的函数,增加日志流水号
        MDC.put("LOG_ID", String.valueOf(snowFlake.nextId()));
        LOG.info("生成今日电子书快照开始");
        long start = System.currentTimeMillis();
        ebookSnapshotService.getSnapshot();
        LOG.info("生成今日电子书快照结束，耗时:{}毫秒",System.currentTimeMillis()-start);
    }

}
