package com.xqy.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xqy.config.XqyPageQuery;
import com.xqy.bean.XqyCustomer;
import com.xqy.query.XqyCustomerQuery;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

/**
 * @author xu
 * @description 针对表【customer】的数据库操作Mapper
 * @createDate 2023-09-29 11:31:27
 * @Entity com.xqy.entity.XqyCustomer
 */
public interface XqyCustomerMapper extends BaseMapper<XqyCustomer> {
    //多条件查询
    public List<XqyCustomer> selectByParams(XqyPageQuery baseQuery) throws DataAccessException;

    //查询流失的客户
    public List<XqyCustomer> queryLossCustomers();

    //更新客户状态
    int updateCustomerStateByIds(List<Integer> lossCusIds);

    //查询客户贡献
    public List<Map<String, Object>> queryCustomerContributionByParams(XqyCustomerQuery customerQuery);

    //统计客户构成
    public List<Map<String, Object>> countCustomerMake();

    //统计客户服务
    public List<Map<String, Object>> countCustomerServe();
}




