package com.song.wiki.controller;

import com.song.wiki.resp.CommonResp;
import com.song.wiki.resp.EbookSnapshotResp;
import com.song.wiki.service.EbookSnapshotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/ebook-snapshot")
public class EbookSnapshotController {

    @Resource
    private EbookSnapshotService ebookSnapshotService;

    @GetMapping("/get-statistic")
    public CommonResp getStatistic(){
        List<EbookSnapshotResp> ebookSnapshotResp = ebookSnapshotService.getStatistic();
        CommonResp<List<EbookSnapshotResp>> commonResp = new CommonResp<>();
        commonResp.setContent(ebookSnapshotResp);
        return commonResp;
    }
}
