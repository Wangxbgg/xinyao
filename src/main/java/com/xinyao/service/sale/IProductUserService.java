package com.xinyao.service.sale;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.Bo.TransferBo;
import com.xinyao.bean.sale.Product;
import com.xinyao.bean.sale.ProductUser;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyao.bean.sale.vo.ProductVo;

import java.util.List;

/**
 * <p>
 * 用户商品表 服务类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-12
 */
public interface IProductUserService extends IService<ProductUser> {

    IPage<ProductVo> getInfoByUserId(Page<ProductUser> page, Integer collectionsId);

    IPage<ProductVo> getPageByProductId(Page<ProductUser> page, Long productId);

    List<ProductVo> getListByProductId(Long userId, Long productId, Integer status);

    boolean transfer(TransferBo transferBo);
}
