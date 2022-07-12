package com.xinyao.service.sale;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyao.bean.sale.Product;
import com.xinyao.bean.sale.vo.ProductVo;
import org.springframework.web.bind.annotation.RequestBody;
import org.tianhe.thbc.sdk.abi.datatypes.Int;

import java.util.List;

/**
 * <p>
 * 商品表 服务类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-07
 */
public interface IProductService extends IService<Product> {

    IPage<ProductVo> getAllList(Page<Product> page, Product product);

    boolean deleteById(Long id);

    Integer createOrUpdate(Product product);

    ProductVo getInfoById(Long id);

}
