package com.xinyao.service.sale.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.Bo.TransferBo;
import com.xinyao.bean.common.StatusEnum;
import com.xinyao.bean.sale.Product;
import com.xinyao.bean.sale.ProductUser;
import com.xinyao.bean.sale.Transfer;
import com.xinyao.bean.sale.vo.ProductVo;
import com.xinyao.bean.usc.User;
import com.xinyao.mapper.sale.ProductUserMapper;
import com.xinyao.service.sale.IProductUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyao.service.sale.ITransferService;
import com.xinyao.service.usc.IUserService;
import com.xinyao.util.JWTUtil;
import com.xinyao.util.MD5Util;
import com.xinyao.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 用户商品表 服务实现类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-12
 */
@Service
public class ProductUserServiceImpl extends ServiceImpl<ProductUserMapper, ProductUser> implements IProductUserService {

    @Autowired
    private IUserService userService;

    @Autowired
    private ITransferService transferService;

    @Autowired
    private IProductUserService productUserService;

    @Override
    public IPage<ProductVo> getInfoByUserId(Page<ProductUser> page, Integer collectionsId) {
        IPage<ProductVo> productVoIPage = this.baseMapper.getInfoByUserId(page, JWTUtil.getUserId(), collectionsId);
        for (ProductVo productVo : productVoIPage.getRecords()) {
            productVo.setProductUser(productUserService.getById(productVo.getUserProductId()));
        }
        return productVoIPage;
    }

    @Override
    public IPage<ProductVo> getPageByProductId(Page<ProductUser> page, Long productId) {
        IPage<ProductVo> productVoIPage = this.baseMapper.getPageByProductId(page, JWTUtil.getUserId(), productId);
        for (ProductVo productVo : productVoIPage.getRecords()) {
            productVo.setProductUser(productUserService.getById(productVo.getUserProductId()));
        }
        return productVoIPage;
    }

    @Override
    public List<ProductVo> getListByProductId(Long userId, Long productId, Integer status) {
        return this.baseMapper.getListByProductId(userId, productId, status);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean transfer(TransferBo transferBo) {
        // 校验转赠商品信息
        List<ProductVo> productVoList = this.getListByProductId(JWTUtil.getUserId(), transferBo.getProductId(), 0);
        if (transferBo.getProductQuantity() > productVoList.size()) {
            throw new RuntimeException("剩余数量不足！！！");
        }
        // 校验交易密码
        String userDealPassword = userService.getDealPassword();
        if (StringUtils.isNullOrBlank(userDealPassword)) {
            throw new RuntimeException("您尚未设置支付密码\n请设置后支付");
        }
        if (!MD5Util.encrypt(transferBo.getDealPassword(), JWTUtil.getAccount()).equals(userDealPassword)) {
            throw new RuntimeException("您的交易密码错误");
        }
        // 获取受赠人信息
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", transferBo.getPhone());
        User user = userService.getOne(queryWrapper);
        // 生成转赠记录
        Transfer transfer = new Transfer();
        transfer.setTransferId(JWTUtil.getUserId());
        transfer.setTransferPhone(JWTUtil.getAccount());
        transfer.setTransferQuantity(transferBo.getProductQuantity());
        transfer.setGiftedId(user.getId());
        transfer.setGiftedPhone(user.getMobile());
        transfer.setProductId(transferBo.getProductId());
        transfer.setStatus(StatusEnum.TransferStatus.PROGRESS.code);
        this.transferService.save(transfer);
        // 修改用户商品状态
        List<ProductUser> productUserList = new ArrayList<>();
        for (ProductVo productVo:productVoList) {
            if (productUserList.size() != transferBo.getProductQuantity()) {
                ProductUser productUser = this.baseMapper.selectById(productVo.getUserProductId());
                productUser.setStatus(1);
                productUserList.add(productUser);
            }
        }
        this.saveOrUpdateBatch(productUserList);
        return true;
    }

}
