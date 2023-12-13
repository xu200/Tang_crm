package com.xqy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.xqy.bean.XqyCustomerLinkman;
import com.xqy.dao.XqyCustomerLinkmanMapper;
import com.xqy.service.XqyCustomerLinkmanService;
import com.xqy.utils.XqyAssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author xu
 * @description 针对表【t_customer_linkman】的数据库操作Service实现
 * @createDate 2023-09-29 11:31:27
 */
@Service
public class XqyCustomerLinkmanServiceImpl extends ServiceImpl<XqyCustomerLinkmanMapper, XqyCustomerLinkman>
        implements XqyCustomerLinkmanService {

    @Resource
    private XqyCustomerLinkmanMapper linkmanMapper;

    //更新联系人
    @Override
    public void updateCustomerLinkman(XqyCustomerLinkman customerLinkman) {
        //参数校验
        checkParams(customerLinkman.getLinkName(), customerLinkman.getPhone(), customerLinkman.getPosition());
        //查找是否存在
        XqyCustomerLinkman temp = linkmanMapper.selectOne(new QueryWrapper<XqyCustomerLinkman>()
                .eq("cus_id", customerLinkman.getCusId()));
        XqyAssertUtil.isTrue(null == temp, "待更新的客户记录不存在!");
        //更新时间
        customerLinkman.setUpdateDate(new Date());
        XqyAssertUtil.isTrue(linkmanMapper.updateById(customerLinkman) < 1, "客户记录更新失败!");
    }

    //参数校验
    private void checkParams(String name, String phone, String position) {
        XqyAssertUtil.isTrue(StringUtils.isBlank(name), "请指定联系人姓名！");
        XqyAssertUtil.isTrue(StringUtils.isBlank(phone), "非法的手机格式！");
        XqyAssertUtil.isTrue(StringUtils.isBlank(position), "请指定联系人的职位！");
    }
}




