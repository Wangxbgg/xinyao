package com.xinyao.mapper.usc;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.usc.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import javax.annotation.Resource;
import java.util.Date;

/**
 * <p>
 * 用户中心-用户信息表 Mapper 接口
 * </p>
 *
 * @author WangXB
 * @since 2022-07-08
 */
@Mapper
@Resource
public interface UserMapper extends BaseMapper<User> {

    IPage<User> getAllList(Page<User> page, User user);

    Integer setDealPassword(@Param("dealPassword") String dealPassword, @Param("id") Long id, @Param("dateTime") Date dateTime);

    int updateByPrimaryKey(Long id,Integer isAttestation);
}
