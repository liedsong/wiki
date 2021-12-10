package com.song.wiki.mapper;

import com.song.wiki.resp.EbookSnapshotResp;

import java.util.List;

public interface MyEbookSnapshotMapper {
    void getSnapshot();

    List<EbookSnapshotResp> getStatistic();

    List<EbookSnapshotResp> get30Statistic();
}
