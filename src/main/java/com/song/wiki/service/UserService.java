package com.song.wiki.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.song.wiki.domain.User;
import com.song.wiki.domain.UserExample;
import com.song.wiki.exception.BusinessException;
import com.song.wiki.exception.BusinessExceptionCode;
import com.song.wiki.mapper.UserMapper;
import com.song.wiki.req.UserQueryReq;
import com.song.wiki.req.UserSaveReq;
import com.song.wiki.resp.UserQueryResp;
import com.song.wiki.resp.PageResp;
import com.song.wiki.util.CopyUtil;
import com.song.wiki.util.SnowFlake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    @Resource
    private UserMapper userMapper;

    @Resource
    private SnowFlake snowFlake;

    public PageResp<UserQueryResp> list(UserQueryReq req){
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        if(!ObjectUtils.isEmpty(req.getLoginName())){
            criteria.andLoginNameEqualTo(req.getLoginName());
        }
        //PageHelper只对第一个遇到的sql起作用
        PageHelper.startPage(req.getPage(),req.getSize());
        List<User> userList = userMapper.selectByExample(userExample);

        PageInfo<User> pageInfo = new PageInfo<>(userList);
        LOG.info("总行数:{}", pageInfo.getTotal());
        LOG.info("总页数:{}", pageInfo.getPages());

        List<UserQueryResp> respList = new ArrayList<>();
//        for (User user : userList) {
//            UserResp userResp = new UserResp();
//            BeanUtils.copyProperties(user,userResp);
//            //对象复制
//            UserResp userResp = CopyUtil.copy(user, UserResp.class);
//            respList.add(userResp);
//        }

        //列表复制
        List<UserQueryResp> list = CopyUtil.copyList(userList, UserQueryResp.class);

        PageResp<UserQueryResp> pageResp = new PageResp();
        pageResp.setTotal(pageInfo.getTotal());
        pageResp.setList(list);
        return pageResp;
    }

    /**
     * 保存
     */
    public void save(UserSaveReq req) {
        User user = CopyUtil.copy(req,User.class);
        if(ObjectUtils.isEmpty(req.getId())){
            User userDB = selectByLoginName(req.getLoginName());
            if(ObjectUtils.isEmpty(userDB)){
                //新增
                user.setId(snowFlake.nextId());
                userMapper.insert(user);
            }else{
                //用户名已存在
                throw new BusinessException(BusinessExceptionCode.USER_LOGIN_NAME_EXIST);

            }

        } else{
            //更新
            user.setLoginName(null);
            userMapper.updateByPrimaryKeySelective(user);
        }

    }

    public void delete(Long id){
        userMapper.deleteByPrimaryKey(id);
    }

    public User selectByLoginName(String LoginName) {
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andLoginNameEqualTo(LoginName);

        //PageHelper只对第一个遇到的sql起作用
        List<User> userList = userMapper.selectByExample(userExample);
        if(CollectionUtils.isEmpty(userList)){
            return null;
        }else{
            return userList.get(0);
        }
    }
}