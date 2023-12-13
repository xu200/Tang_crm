package com.xqy.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xqy.bean.XqyModule;
import com.xqy.utils.XqyZTree;

import java.util.List;

/**
 * @author xu
 * @description 针对表【t_module】的数据库操作Mapper
 * @createDate 2023-09-29 11:31:27
 * @Entity com.xqy.entity.XqyModule
 */
public interface XqyModuleMapper extends BaseMapper<XqyModule> {
    //查询所有可授权资源
    public List<XqyZTree> queryAllModules();
}




