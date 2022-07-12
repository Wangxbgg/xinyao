package generate;

import generate.FundAmountDetail;

public interface FundAmountDetailDao {
    int deleteByPrimaryKey(Long id);

    int insert(FundAmountDetail record);

    int insertSelective(FundAmountDetail record);

    FundAmountDetail selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FundAmountDetail record);

    int updateByPrimaryKey(FundAmountDetail record);
}