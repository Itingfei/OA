package com.dongao.oa.dao;

import com.dongao.oa.pojo.PositionUserOrganization;
import com.dongao.oa.utils.MapperUtils;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface PositionUserOrganizationMapper extends MapperUtils<PositionUserOrganization> {
    /**
     * 查出要添加用户领导
     * @param orgId
     * @param positionId
     * @return
     */
    List<PositionUserOrganization> selectLeader(Long orgId, Long positionId);

    List<Map<String,Object>> selectPUOByUserId(Long userId);

    List<PositionUserOrganization> selectLowerLevel(Long orgId, Long positionId);

    List<PositionUserOrganization> selectUserByOrgIdAndPostionName(Map<String,Object> condition);

    List<PositionUserOrganization> selectSkipLowerLevel(Long orgId, Long positionId,@Param("sign") Long sign);

    List<PositionUserOrganization> selectSkipLeader(Long orgId, Long positionId);

    List<PositionUserOrganization> selectByPositionName(String positionName);

    List<PositionUserOrganization> selectSkipOrgLeader(Long orgId, Long positionId);
}