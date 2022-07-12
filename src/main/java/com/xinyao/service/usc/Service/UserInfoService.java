package com.xinyao.service.usc.Service;

import cn.hutool.system.UserInfo;
import com.xinyao.bean.Bo.UserBo.UscUserCarBo;
import com.xinyao.bean.usc.User;
import com.xinyao.mapper.usc.UscUserCarDao;
import com.xinyao.mapper.usc.UserMapper;
import com.xinyao.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoService {
    @Autowired
    private UserMapper userDao;

    @Autowired
    private UscUserCarDao uscUserCarDao;

    public R users(Long Id) {
        Map<String,Object> map = new HashMap<>();
        User user = userDao.selectByPrimaryKey(Id);
        map.put("user",user);
        return R.ok(map);
    }

    public R addUscUser(UscUserCarBo paramBo) {
        uscUserCarDao.insertSelective(paramBo);
        return R.ok();
    }

}
