package com.xinyao.service.usc.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyao.bean.usc.User;
import com.xinyao.mapper.usc.UserMapper;
import com.xinyao.service.usc.IUserService;
import com.xinyao.util.JWTUtil;
import com.xinyao.util.MD5Util;
import com.xinyao.util.RedisUtil;
import com.xinyao.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * <p>
 * 用户中心-用户信息表 服务实现类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-08
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public User login(String phone, String code) {
        /*String oldCode = redisUtil.get(GlobalField.REDISKEY+phone).toString();
        if (!code.equals(oldCode)) {
            throw new RuntimeException("验证码不正确，请重新输入！！！");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", phone);
        queryWrapper.eq("is_deleted", 0);
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("手机号错误或不存在！！！");
        }*/
        User user = this.baseMapper.selectById(1);
        return user;
    }

    @Override
    public User authPassword(String phone, String password) {
        /*QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", phone);
        queryWrapper.eq("is_deleted", 0);
        User user = this.baseMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("手机号错误或不存在！！！");
        }
        if (!MD5Util.encrypt(password, user.getMobile()).equals(user.getPassword())) {
            throw new RuntimeException("密码错误！！！");
        }*/
        User user = this.baseMapper.selectById(1);
        return user;
    }

    @Override
    public boolean setPassword(String password) {
        return this.baseMapper.setPassword(MD5Util.encrypt(password, JWTUtil.getAccount()), JWTUtil.getUserId(), new Date()) > 0;
    }

    @Override
    public IPage<User> getAllList(Page<User> page, User user) {
        IPage<User> productVoIPage = this.baseMapper.getAllList(page, user);
        return productVoIPage;
    }

    @Override
    public Integer createOrUpdate(User user) {
        Integer check = checkProduct(user);
        if (check > 1) {
            return check;
        }
        if (StringUtils.isNotNullOrBlank(user.getPassword())) {
            user.setPassword(MD5Util.encrypt(user.getPassword(), user.getMobile()));
        }
        if (StringUtils.isNotNullOrBlank(user.getId())) {
            return this.baseMapper.updateById(user);
        }
        return this.baseMapper.insert(user);
    }

    @Override
    public boolean deleteById(Long id) {
        return this.baseMapper.deleteById(id) > 0;
    }

    @Override
    public boolean setDealPassword(String dealPassword) {
        return this.baseMapper.setDealPassword(MD5Util.encrypt(dealPassword, JWTUtil.getAccount()), JWTUtil.getUserId(), new Date()) > 0;
    }

    @Override
    public BigDecimal getAccountBalance() {
        User user = this.baseMapper.selectById(JWTUtil.getUserId());
        return user.getAmount();
    }

    @Override
    public String getDealPassword() {
        User user = this.baseMapper.selectById(JWTUtil.getUserId());
        return user.getDealPassword();
    }

    @Override
    public boolean isAttestation() {
        User user = this.baseMapper.selectById(JWTUtil.getUserId());
        return user.getIsAttestation() == 1;
    }

    @Override
    public boolean isSetDealPassword() {
        User user = this.baseMapper.selectById(JWTUtil.getUserId());
        return StringUtils.isNotNullOrBlank(user.getDealPassword());
    }

    /**
     * 校验用户唯一性
     */
    private Integer checkProduct(User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0);
        if (StringUtils.isNotNullOrBlank(user.getId())) {
            queryWrapper.ne("id", user.getId());
        }
        if (StringUtils.isNotNullOrBlank(user.getAccount())) {
            queryWrapper.eq("account", user.getAccount());
            User u = this.baseMapper.selectOne(queryWrapper);
            return u!=null ? 2: 1;
        }
        if (StringUtils.isNotNullOrBlank(user.getName())) {
            queryWrapper.eq("name", user.getName());
            User u = this.baseMapper.selectOne(queryWrapper);
            return u!=null ? 3: 1;
        }
        if (StringUtils.isNotNullOrBlank(user.getMobile())) {
            queryWrapper.eq("mobile", user.getMobile());
            User u = this.baseMapper.selectOne(queryWrapper);
            return u!=null ? 4: 1;
        }
        return 1;
    }
}
