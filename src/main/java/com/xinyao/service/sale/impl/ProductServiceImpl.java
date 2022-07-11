package com.xinyao.service.sale.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xinyao.bean.sale.vo.ProductVo;
import com.xinyao.mapper.sale.ProductMapper;
import com.xinyao.bean.sale.Product;
import com.xinyao.service.sale.IProductService;
import com.xinyao.util.JWTUtil;
import com.xinyao.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-07
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public IPage<ProductVo> getAllList(Page<Product> page, Product product) {
        IPage<ProductVo> productVoIPage = productMapper.getAllList(page, product);
        for (ProductVo productVo : productVoIPage.getRecords()) {
            productVo.setBookingDateTime(productVo.getBookingTime().getTime());

            // 计算发售时间与当前时间相差多久
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date date = new Date();
            long dayM = 1000 * 24 * 60 * 60;
            long hourM = 1000 * 60 * 60;

            long hour = 0;
            try {
                Date parse1 = simpleDateFormat.parse(simpleDateFormat.format(date));
                Date parse2 = simpleDateFormat.parse(simpleDateFormat.format(productVo.getBookingTime()));
                long differ = parse2.getTime() - parse1.getTime();
                hour = differ % dayM / hourM + 24 * (differ / dayM);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (productVo.getQuantity().compareTo(BigDecimal.ZERO) == 0) {
                productVo.setStatus(3);
            } else if (hour < 24 && hour > 0) {
                productVo.setStatus(1);
            } else if (hour >= 24) {
                productVo.setStatus(0);
            } else {
                productVo.setStatus(2);
            }
        }
        return productVoIPage;
    }

    @Override
    public boolean deleteById(Long id) {
        return productMapper.deleteById(id) > 0;
    }

    @Override
    public Integer createOrUpdate(Product product) {
        Integer check = checkProduct(product);
        if (check > 1) {
            return check;
        }
        if (StringUtils.isNotNullOrBlank(product.getId())) {
            return productMapper.updateById(product);
        }
        return productMapper.insert(product);
    }

    @Override
    public Product getInfoById(Long id) {
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0);
        queryWrapper.eq("is_shelves", 1);
        return productMapper.selectOne(queryWrapper);
    }

    @Override
    public IPage<ProductVo> getInfoByUserId(Page<Product> page, Integer collectionsId) {
        return productMapper.getInfoByUserId(page, JWTUtil.getUserId(), collectionsId);
    }

    /**
     * 校验商品唯一性
     */
    private Integer checkProduct(Product product){
        QueryWrapper<Product> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("is_deleted", 0);
        if (StringUtils.isNotNullOrBlank(product.getId())) {
            queryWrapper.ne("id", product.getId());
        }
        if (StringUtils.isNotNullOrBlank(product.getName())) {
            queryWrapper.eq("name", product.getName());
            Product p = productMapper.selectOne(queryWrapper);
            return p!=null ? 2: 1;
        }
        if (StringUtils.isNotNullOrBlank(product.getNumber())) {
            queryWrapper.eq("number", product.getNumber());
            Product p = productMapper.selectOne(queryWrapper);
            return p!=null ? 3: 1;
        }
        return 1;
    }
}
