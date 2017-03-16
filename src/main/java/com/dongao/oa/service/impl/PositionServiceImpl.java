package com.dongao.oa.service.impl;
import com.dongao.oa.dao.PositionMapper;
import com.dongao.oa.dao.PositionUserOrganizationMapper;
import com.dongao.oa.pojo.Menu;
import com.dongao.oa.pojo.Position;
import com.dongao.oa.pojo.PositionUserOrganization;
import com.dongao.oa.service.PositionService;
import com.dongao.oa.utils.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by yangjie on 2016/8/15.
 */
@Service
public class PositionServiceImpl implements PositionService {
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private PositionUserOrganizationMapper positionUserOrganizationMapper;
    @Override
    public List<Position> findPositionByParentId(Position position) {
        Example example = new Example(Position.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("delFlag",0);//去除删除的职位，只查询有效职位;
        if (position.getParentId()!=null)
            criteria.andEqualTo("parentId",position.getParentId());
        if (StringUtils.isNotEmpty(position.getName()))
            criteria.andLike("name","%"+position.getName()+"%");
        if (position.getStartCreateDate()!=null)
            criteria.andGreaterThanOrEqualTo("createDate",position.getStartCreateDate());
        if (position.getEndCreateDate()!=null)
            criteria.andLessThanOrEqualTo("createDate",position.getEndCreateDate());
        example.setOrderByClause(" create_date desc");
        List<Position> selectByExample = positionMapper.selectByExample(example);
        return selectByExample;
    }

    @Override
    public Position selectOne(Long positionId) {
        return positionMapper.selectByPrimaryKey(positionId);
    }

    @Override
    public List<Position> selectAll() {
        Example example = new Example(Position.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("delFlag", 0);
        return positionMapper.selectByExample(example);
    }

    @Override
    public int createPosition(Position position) {
        return positionMapper.insert(position);
    }

    @Override
    public int updatePosition(Position position) {
        return positionMapper.updateByPrimaryKeySelective(position);
    }

    @Override
    public void deleteAllChildrenPosition(Long positionId) {
        //此处删除选用逻辑删除 只是把删除标识改为1
        //修改此id的职位对象的删除标识
        Position findOne = selectOne(positionId);
        findOne.setDelFlag(1);
        updatePosition(findOne);
        //条件查询父id为此值的职位，递归，修改查询出来的所有职位对象的删除标识
        List<Position> menuList = new ArrayList<Position>();
        Position p = new Position();
        p.setParentId(positionId);
        List<Position> findPositionByParentId = findPositionByParentId(p);
        selectAllChildren(findPositionByParentId,menuList);
        for (Position position : menuList) {
            position.setDelFlag(1);
            updatePosition(position);
        }
    }

    @Override
    public List<PositionUserOrganization> selectUserByOrgIdAndPostionName(Long orgId, String positionName) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("orgId",orgId);
        map.put("positionName",positionName);
        return positionUserOrganizationMapper.selectUserByOrgIdAndPostionName(map);
    }

    @Override
    public PositionUserOrganization selectByPOByPOId(Long positionOrganizationId) {
        PositionUserOrganization puo = new PositionUserOrganization();
        puo.setPositionOrganizationId(positionOrganizationId);
        return positionUserOrganizationMapper.selectOne(puo);
    }

    /**
     * 递归查询所有子职位
     * @param positionList 根据id作为父id查询直接所属的子职位
     * @param positionList2   得到所有子职位
     */
    public void selectAllChildren(List<Position> positionList,List<Position> positionList2){
        if(positionList.size()>0){
            for (Position position : positionList) {
                positionList2.add(position);
                Position findPosition = new Position();
                findPosition.setParentId(position.getId());
                List<Position> findMenuByParentId = findPositionByParentId(findPosition);
                selectAllChildren(findMenuByParentId,positionList2);
            }
        }
    }

}
