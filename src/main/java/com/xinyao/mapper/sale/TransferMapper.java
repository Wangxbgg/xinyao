package com.xinyao.mapper.sale;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.sale.Transfer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xinyao.bean.sale.vo.TransferVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 转赠记录表 Mapper 接口
 * </p>
 *
 * @author WangXB
 * @since 2022-07-12
 */
public interface TransferMapper extends BaseMapper<Transfer> {

    IPage<TransferVo> getPageList(@Param("page") Page<Transfer> page, @Param("status") Integer status, @Param("type") Integer type, @Param("userId") Long userId);

}
