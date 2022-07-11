package com.xinyao.service.usc;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyao.bean.usc.User;

/**
 * <p>
 * 用户中心-用户信息表 服务类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-08
 */
public interface IUserService extends IService<User> {

    User login(String phone, String code);

    IPage<User> getAllList(Page<User> page, User user);

    Integer createOrUpdate(User user);

    boolean deleteById(Long id);

    boolean setDealPassword(String dealPassword);
}
