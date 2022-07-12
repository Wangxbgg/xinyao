package generate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

/**
 * fund_amount_detail
 * @author 
 */
@Data
public class FundAmountDetail implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 订单编号
     */
    private String orderSn;

    /**
     * ("NORMAL_PAY","正常支付"),
("NORMAL_REVENUE","正常收入"),
("BANK_TRANSFER_IN","银行转入"),
("BANK_TRANSFER_OUT","银行转出");
     */
    private String type;

    /**
     * 运算符号1:+  2:-
     */
    private Integer operationSymbol;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 创建人id
     */
    private Long createId;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人姓名
     */
    private String createName;

    /**
     * 状态(0：成功  1：失败)
     */
    private Integer status;

    /**
     * 备注
     */
    private String comment;

    /**
     * 交易流水号
     */
    private String transactionNum;

    /**
     * 交易日
     */
    private String transactionDate;

    /**
     * 银行账号
     */
    private String bankCard;

    private static final long serialVersionUID = 1L;
}