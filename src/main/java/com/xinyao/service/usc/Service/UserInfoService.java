package com.xinyao.service.usc.Service;

import cn.hutool.system.UserInfo;
import com.xinyao.bean.Bo.UserBo.UscUserCarBo;
import com.xinyao.bean.Bo.fundBo.AmountDetaliBo;
import com.xinyao.bean.Bo.fundBo.FlowingBo;
import com.xinyao.bean.Bo.fundBo.WaterBo;
import com.xinyao.bean.Vo.AmountDetailVo;
import com.xinyao.bean.usc.User;
import com.xinyao.mapper.fund.AmountDetailDao;
import com.xinyao.mapper.fund.AmountDetailMapper;
import com.xinyao.mapper.usc.UscUserCarDao;
import com.xinyao.mapper.usc.UserMapper;
import com.xinyao.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserInfoService {
    @Autowired
    private UserMapper userDao;

    @Autowired
    private UscUserCarDao uscUserCarDao;

    @Autowired
    private AmountDetailDao amountDetailDao;

    public R users(Long Id) {
        Map<String,Object> map = new HashMap<>();
        User user = userDao.selectByPrimaryKey(Id);
        if(user!=null){
            if(user.getAmount()==null){
                BigDecimal d1 = new BigDecimal("0");
                user.setAmount(d1);
            }
        }
        map.put("user",user);
        return R.ok(map);
    }

    public R addUscUser(UscUserCarBo paramBo) {
        uscUserCarDao.insertSelective(paramBo);
        return R.ok();
    }
    public R addRecharge(AmountDetaliBo paramBo) {
        if(paramBo.getUserId()==null){
            return R.ok("请先登入");
        }
        Double aoe = amountDetailDao.GetMoneny(paramBo.getUserId());
        if(aoe==null){
            aoe=0.00;
        }
        Double aoeMoney = aoe + paramBo.getAmount();
//        paramBo.setAmount(aoeMoney);
        amountDetailDao.recharge(aoeMoney,paramBo.getUserId());
        amountDetailDao.insertSelective(paramBo);
        return R.ok();
    }

    public R addWithdrawal(AmountDetaliBo paramBo) {
        if(paramBo.getUserId()==null){
            return R.ok("请先登入");
        }
        Double aoe = amountDetailDao.GetMoneny(paramBo.getUserId());
        if(aoe==null){
            aoe=0.00;
        }
        Double aoeMoney = aoe - paramBo.getAmount();
//        paramBo.setAmount(aoeMoney);
        amountDetailDao.withdrawal(aoeMoney,paramBo.getUserId());
        amountDetailDao.insertSelective(paramBo);
        return R.ok();
    }

    public R flowinGlist(FlowingBo paramBo) {
        Map<String, Object> map = new HashMap<String, Object>();
        List<AmountDetailVo> data = amountDetailDao.flowinGlist(paramBo);
        int total = amountDetailDao.ConutflowinGlist(paramBo);
        map.put("data",data);
        map.put("total",total);
        return R.ok(map);
    }

}
