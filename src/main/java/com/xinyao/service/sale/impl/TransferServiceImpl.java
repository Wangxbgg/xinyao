package com.xinyao.service.sale.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.common.StatusEnum;
import com.xinyao.bean.sale.Product;
import com.xinyao.bean.sale.ProductUser;
import com.xinyao.bean.sale.Transfer;
import com.xinyao.bean.sale.vo.ProductVo;
import com.xinyao.bean.sale.vo.TransferVo;
import com.xinyao.mapper.sale.TransferMapper;
import com.xinyao.service.sale.IProductService;
import com.xinyao.service.sale.IProductUserService;
import com.xinyao.service.sale.ITransferService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyao.util.JWTUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 转赠记录表 服务实现类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-12
 */
@Service
public class TransferServiceImpl extends ServiceImpl<TransferMapper, Transfer> implements ITransferService {

    @Autowired
    private IProductUserService productUserService;

    @Autowired
    private IProductService productService;

    @Override
    public IPage<TransferVo> getPageList(Page<Transfer> page, Integer status, Integer type) {
        // 获取转赠信息
        IPage<TransferVo> transferVoIPage = this.baseMapper.getPageList(page, status, type, JWTUtil.getUserId());
        for (TransferVo transferVo : transferVoIPage.getRecords()) {
            // 获取转赠商品信息
            Product product = productService.getById(transferVo.getProductId());
            ProductVo productVo = new ProductVo();
            BeanUtils.copyProperties(product, productVo);
            transferVo.setProductVo(productVo);
        }
        return transferVoIPage;
    }

    @Override
    public boolean cancelTransfer(Long id) {
        // 获取转赠信息
        Transfer transfer = this.baseMapper.selectById(id);
        // 修改转赠状态
        transfer.setStatus(StatusEnum.TransferStatus.CANCEL.code);
        this.baseMapper.updateById(transfer);
        // 获取用户商品信息
        List<ProductVo> productVoList = this.productUserService.getListByProductId(JWTUtil.getUserId(), transfer.getProductId(), 1);
        // 修改用户商品状态
        List<ProductUser> productUserList = new ArrayList<>();
        for (ProductVo productVo:productVoList) {
            if (productUserList.size() != transfer.getTransferQuantity()) {
                ProductUser productUser = this.productUserService.getById(productVo.getUserProductId());
                productUser.setStatus(0);
                productUserList.add(productUser);
            }
        }
        this.productUserService.saveOrUpdateBatch(productUserList);
        return true;
    }

    @Override
    public boolean takeItProduct(Long id) {
        // 获取转赠信息
        Transfer transfer = this.baseMapper.selectById(id);
        // 修改转赠状态
        transfer.setStatus(StatusEnum.TransferStatus.FINISH.code);
        this.baseMapper.updateById(transfer);
        // 获取用户商品信息
        List<ProductVo> productVoList = this.productUserService.getListByProductId(transfer.getTransferId(), transfer.getProductId(), 1);
        // 修改用户商品状态
        List<ProductUser> productUserList = new ArrayList<>();
        for (ProductVo productVo:productVoList) {
            if (productUserList.size() != transfer.getTransferQuantity()) {
                ProductUser productUser = this.productUserService.getById(productVo.getUserProductId());
                productUser.setStatus(0);
                productUser.setUserId(JWTUtil.getUserId());
                productUserList.add(productUser);
            }
        }
        if (!CollectionUtils.isEmpty(productUserList)) {
            this.productUserService.saveOrUpdateBatch(productUserList);
        }
        return true;
    }

    @Override
    public TransferVo getOneById(Long id) {
        // 获取转赠记录信息
        Transfer transfer = this.baseMapper.selectById(id);
        TransferVo transferVo = new TransferVo();
        BeanUtils.copyProperties(transfer, transferVo);
        // 获取转赠商品信息
        Product product = productService.getById(transferVo.getProductId());
        ProductVo productVo = new ProductVo();
        BeanUtils.copyProperties(product, productVo);
        transferVo.setProductVo(productVo);
        return transferVo;
    }
}
