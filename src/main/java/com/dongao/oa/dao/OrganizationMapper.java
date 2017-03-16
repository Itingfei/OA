package com.dongao.oa.dao;
import com.dongao.oa.pojo.Organization;
import com.dongao.oa.pojo.Position;
import com.dongao.oa.utils.MapperUtils;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;
public interface OrganizationMapper extends MapperUtils<Organization> {
     String selectMaxOrgCode(Organization organization);

     List<Map<String,Object>> selectByCondition(@Param("entity")Map<String,Object> map);
}