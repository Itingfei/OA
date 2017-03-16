package com.dongao.oa.service.impl;
import com.dongao.oa.dao.OrganizationMapper;
import com.dongao.oa.dao.PositionMapper;
import com.dongao.oa.dao.ProcessPersonnelMapper;
import com.dongao.oa.dao.UserMapper;
import com.dongao.oa.pojo.Organization;
import com.dongao.oa.pojo.Position;
import com.dongao.oa.pojo.ProcessPersonnel;
import com.dongao.oa.service.ProcessPersonnelService;
import com.dongao.oa.utils.PageInfo;
import com.dongao.oa.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * Created by fengjifei on 2016/9/1
 */
@Service
public class ProcessPersonnelServiceImpl implements ProcessPersonnelService {
    @Autowired
    private ProcessPersonnelMapper processPersonnelMapper;
    @Autowired
    private PositionMapper positionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrganizationMapper organizationMapper;
    @Override
    public ProcessPersonnel selectOne(Long processPersonnelId) {
        return processPersonnelMapper.selectByPrimaryKey(processPersonnelId);
    }

    @Override
    public PageInfo<ProcessPersonnel> selectAll(ProcessPersonnel processPersonnel) {
        Example example = initExample(processPersonnel);
        List<ProcessPersonnel> processPersonnelList = new ArrayList<ProcessPersonnel>();
        List<ProcessPersonnel> processPersonnels = processPersonnelMapper.selectByExample(example);
        for (ProcessPersonnel p:processPersonnels){
            Organization organization = organizationMapper.selectByPrimaryKey(p.getOrgId());
            if (organization!=null)
                p.setOrgName(organization.getOrgName());
            Position position = positionMapper.selectByPrimaryKey(p.getPositionId());
            if (position!=null)
                p.setPositionName(position.getName());
            processPersonnelList.add(p);
        }
        PageInfo pageInfo = new PageInfo();
        pageInfo.setList(processPersonnelList);
        return pageInfo;
    }

    @Override
    public int createProcessPersonnel(ProcessPersonnel processPersonnel) {
        return processPersonnelMapper.insert(processPersonnel);
    }

    @Override
    public int updateProcessPersonnel(ProcessPersonnel processPersonnel) {
        return processPersonnelMapper.updateByPrimaryKeySelective(processPersonnel);
    }

    @Override
    public ProcessPersonnel selectPPByTaskNameAndProcessKey(String taskName, String processKey) {
        ProcessPersonnel t = new ProcessPersonnel();
        t.setDelFlag(0);
        t.setTaskname(taskName);
        t.setProcessKey(processKey);
        return processPersonnelMapper.selectOne(t);
    }

    @Override
    public int deleteProcessPersonnel(Long id) {
        ProcessPersonnel personnel = selectOne(id);
        personnel.setUpdateBy(UserUtils.getCurrentUser().getUserName());
        personnel.setUpdateDate(new Date());
        personnel.setDelFlag(1);
        return processPersonnelMapper.updateByPrimaryKey(personnel);
    }

    /**
     * 查询条件组装器
     * @param record
     * @return
     */
    public Example initExample(ProcessPersonnel record){
        Example example = new Example(ProcessPersonnel.class);
        Example.Criteria criteria = example.createCriteria().andEqualTo("delFlag",0);//去除删除的职位，只查询有效职位;
        if (record.getStartCreateDate()!=null)
            criteria.andGreaterThanOrEqualTo("createDate",record.getStartCreateDate());
        if (record.getEndCreateDate()!=null)
            criteria.andLessThanOrEqualTo("createDate",record.getEndCreateDate());
        if (StringUtils.isNotEmpty(record.getTaskname()))
            criteria.andLike("taskname","%"+record.getTaskname()+"%");
        if (StringUtils.isNotEmpty(record.getProcessKey()))
            criteria.andEqualTo("processKey",record.getProcessKey());
        example.setOrderByClause(" create_date desc");
        return example;
    }
}


