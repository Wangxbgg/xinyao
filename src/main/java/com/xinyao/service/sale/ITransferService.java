package com.xinyao.service.sale;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xinyao.bean.sale.Transfer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xinyao.bean.sale.vo.TransferVo;

/**
 * <p>
 * 转赠记录表 服务类
 * </p>
 *
 * @author WangXB
 * @since 2022-07-12
 */
public interface ITransferService extends IService<Transfer> {

    IPage<TransferVo> getPageList(Page<Transfer> page, Integer status, Integer type);

    boolean cancelTransfer(Long id);

    boolean takeItProduct(Long id);

    TransferVo getOneById(Long id);
}
