package com.xinyao.mapper.usc;

import com.xinyao.bean.Bo.UserBo.UscUserCarBo;
import com.xinyao.bean.usc.UscUserCar;
import org.apache.ibatis.annotations.Mapper;

import javax.annotation.Resource;

@Mapper
@Resource
public interface UscUserCarDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UscUserCar record);

    int insertSelective(UscUserCarBo record);

    UscUserCar selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UscUserCar record);

    int updateByPrimaryKey(UscUserCar record);

    /**
     * 绑定个人银行卡信息
     */

}