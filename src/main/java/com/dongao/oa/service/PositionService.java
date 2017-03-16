package com.dongao.oa.service;

import com.dongao.oa.pojo.Menu;
import com.dongao.oa.pojo.Position;
import com.dongao.oa.pojo.PositionUserOrganization;
import com.dongao.oa.utils.PageInfo;

import java.util.List;

/**
 * Created by yangjie on 2016/8/15.
 * 职位管理
 */
public interface PositionService {
    /**
     * 根据父级ID查询所有的子职位
     * @param position
     * @return
     */
    List<Position> findPositionByParentId(Position position);

    /**
     * 根据ID查询职位
     * @param positionId
     * @return
     */
    Position selectOne(Long positionId);

    /**
     * 查询全部职位
     * @return
     */
    List<Position> selectAll();

    int createPosition(Position position);

    int updatePosition(Position position);
    /**
     * 根据id删除该职位以及职位下的所有子职位
     * @param positionId
     */
    void deleteAllChildrenPosition(Long positionId);

    /**
     * 根据部门ID和职位名称查询员工
     * @param orgId
     * @param positionName
     * @return
     */
    List<PositionUserOrganization> selectUserByOrgIdAndPostionName(Long orgId,String positionName);

    /**
     * 查询部门下职位是否被用户关联
     * @param positionOrganizationId
     * @return
     */
    public PositionUserOrganization selectByPOByPOId(Long positionOrganizationId);

}
