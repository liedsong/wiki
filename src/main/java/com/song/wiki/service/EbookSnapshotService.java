package com.song.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.song.wiki.domain.Ebook;
import com.song.wiki.domain.EbookExample;
import com.song.wiki.mapper.EbookMapper;
import com.song.wiki.mapper.EbookSnapshotMapper;
import com.song.wiki.mapper.MyEbookSnapshotMapper;
import com.song.wiki.req.EbookQueryReq;
import com.song.wiki.req.EbookSaveReq;
import com.song.wiki.resp.EbookQueryResp;
import com.song.wiki.resp.EbookSnapshotResp;
import com.song.wiki.resp.PageResp;
import com.song.wiki.util.CopyUtil;
import com.song.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class EbookSnapshotService {
    
    private static final Logger LOG = LoggerFactory.getLogger(EbookSnapshotService.class);

    @Resource
    private MyEbookSnapshotMapper myEbookSnapshotMapper;

    public void getSnapshot(){
        myEbookSnapshotMapper.getSnapshot();
    }

    public List<EbookSnapshotResp> getStatistic(){
        List<EbookSnapshotResp> list= myEbookSnapshotMapper.getStatistic();
        return list;
    }


    /**
     * 30天数值统计
     * @return
     */
    public List<EbookSnapshotResp> get30Statistic() {
        return myEbookSnapshotMapper.get30Statistic();
    }
}
