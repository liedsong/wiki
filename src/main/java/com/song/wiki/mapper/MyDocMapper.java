package com.song.wiki.mapper;

import com.song.wiki.domain.Test;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MyDocMapper {

    public void updateViewCount(@Param("id") Long id);

    public void updateVoteCount(@Param("id") Long id);

    public void updateEbookInfo();
}
