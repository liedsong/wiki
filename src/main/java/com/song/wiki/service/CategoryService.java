package com.song.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.song.wiki.domain.Category;
import com.song.wiki.domain.CategoryExample;
import com.song.wiki.mapper.CategoryMapper;
import com.song.wiki.req.CategoryQueryReq;
import com.song.wiki.req.CategorySaveReq;
import com.song.wiki.resp.CategoryQueryResp;
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
public class CategoryService {
    
    private static final Logger LOG = LoggerFactory.getLogger(CategoryService.class);

    @Resource
    private CategoryMapper categoryMapper;

    @Resource
    private SnowFlake snowFlake;

    public List<CategoryQueryResp> all(){
        CategoryExample categoryExample = new CategoryExample();
        categoryExample.setOrderByClause("sort asc");
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        List<CategoryQueryResp> respList = new ArrayList<>();
        //列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        return list;
    }


    public PageResp<CategoryQueryResp> list(CategoryQueryReq req){
        CategoryExample categoryExample = new CategoryExample();
        CategoryExample.Criteria criteria = categoryExample.createCriteria();
        categoryExample.setOrderByClause("sort asc");
//        if(!ObjectUtils.isEmpty(req.getName())){
//            criteria.andNameLike("%" + req.getName() + "%");
//        }
        //PageHelper只对第一个遇到的sql起作用
        PageHelper.startPage(req.getPage(),req.getSize());
        List<Category> categoryList = categoryMapper.selectByExample(categoryExample);

        PageInfo<Category> pageInfo = new PageInfo<>(categoryList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        List<CategoryQueryResp> respList = new ArrayList<>();
//        for (Category category : categoryList) {
//            CategoryResp categoryResp = new CategoryResp();
//            BeanUtils.copyProperties(category,categoryResp);
//            //对象复制
//            CategoryResp categoryResp = CopyUtil.copy(category, CategoryResp.class);
//            respList.add(categoryResp);
//        }

        //列表复制
        List<CategoryQueryResp> list = CopyUtil.copyList(categoryList, CategoryQueryResp.class);

        PageResp<CategoryQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * 保存
     */
    public void save(CategorySaveReq req) {
        Category category = CopyUtil.copy(req,Category.class);
        if(ObjectUtils.isEmpty(req.getId())){
            //新增
            category.setId(snowFlake.nextId());
            categoryMapper.insert(category);
        } else{
            //更新
            categoryMapper.updateByPrimaryKey(category);
        }

    }

    public void delete(Long id){
        categoryMapper.deleteByPrimaryKey(id);
    }
}
