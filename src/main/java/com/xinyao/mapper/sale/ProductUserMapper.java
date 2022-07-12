package com.xinyao.mapper.sale;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.sale.Product;
import com.xinyao.bean.sale.ProductUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyao.bean.sale.vo.ProductVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户商品表 Mapper 接口
 * </p>
 *
 * @author WangXB
 * @since 2022-07-12
 */
public interface ProductUserMapper extends BaseMapper<ProductUser> {

    IPage<ProductVo> getInfoByUserId(@Param("page") Page<ProductUser> page, @Param("userId") Long userId, @Param("collectionsId") Integer collectionsId);

    IPage<ProductVo> getPageByProductId(@Param("page")Page<ProductUser> page, @Param("userId") Long userId, @Param("productId") Long productId);

    List<ProductVo> getListByProductId(@Param("userId") Long userId, @Param("productId") Long productId, @Param("status") Integer status);

}
