package com.dongao.oa.dao;
import com.dongao.oa.pojo.Position;
import com.dongao.oa.utils.MapperUtils;
import java.util.List;
public interface PositionMapper extends MapperUtils<Position> {
    List<Position> selectByOrgIdAndPositionId(Long orgId, Long positionId);

    /**
     * 查出某用户的具体职位
     * @param userId
     * @return
     */
    Position selectPositionByUserId(Long userId);

    /**
     * 查询某部门下某个职位等级
     * @param orgId
     * @param positionId
     * @return
     */
    List<Position> selectLevelByOrgIdAndPid(Long orgId, Long positionId);

    int selectMaxLevel();

}