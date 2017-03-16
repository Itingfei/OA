package com.dongao.oa.service;

import com.dongao.oa.pojo.ProcessPersonnel;
import com.dongao.oa.utils.PageInfo;

import java.util.List;

/**
 * Created by yangjie on 2016/8/15.
 * 职位管理
 */
public interface ProcessPersonnelService {
    /**
     * 根据ID查询职位
     * @param processPersonnelId
     * @return
     */
    ProcessPersonnel selectOne(Long processPersonnelId);

    /**
     * 查询全部职位
     * @return
     */
    PageInfo<ProcessPersonnel> selectAll(ProcessPersonnel processPersonnel);

    int createProcessPersonnel(ProcessPersonnel processPersonnel);

    int updateProcessPersonnel(ProcessPersonnel processPersonnel);
    ProcessPersonnel selectPPByTaskNameAndProcessKey(String taskName,String processKey);

    int deleteProcessPersonnel(Long id);
}
