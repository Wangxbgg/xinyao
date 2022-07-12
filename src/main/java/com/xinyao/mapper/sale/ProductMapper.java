package com.xinyao.mapper.sale;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.sale.Product;
import com.xinyao.bean.sale.vo.ProductVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 商品表 Mapper 接口
 * </p>
 *
 * @author WangXB
 * @since 2022-07-07
 */
@Mapper
public interface ProductMapper extends BaseMapper<Product> {

    IPage<ProductVo> getAllList(@Param("page") Page<Product> page, @Param("product") Product product);

}
