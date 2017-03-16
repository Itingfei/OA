package com.dongao.oa.controller.base;
import com.dongao.oa.controller.BaseController;
import com.dongao.oa.pojo.*;
import com.dongao.oa.service.OrganizationService;
import com.dongao.oa.service.PositionAndOrganizationService;
import com.dongao.oa.service.PositionService;
import com.dongao.oa.service.UserService;
import com.dongao.oa.utils.PageInfo;
import com.dongao.oa.utils.UserUtils;
import com.github.pagehelper.PageHelper;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * 组织机构管理
 * yangjie
 */
@Controller
@RequestMapping("/organization")
public class OrganizationController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	// 上级目录
	private static final String prefix = "organization/";
	// 重定向地址
	private static final String redirectUrl = "redirect:/organization";
	@Autowired
	private OrganizationService organizationService;
	@Autowired
	private UserService userService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private PositionAndOrganizationService positionAndOrganizationService;
	/**
	 * 组织机构列表展示
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("menu:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Organization organization,Model model) {
		List<Organization> organizationList = new ArrayList<Organization>();
		if (StringUtils.isEmpty(organization.getOrgName()) && organization.getStartCreateDate()==null && organization.getEndCreateDate()==null)
			organization.setParentId(0l);
		List<Organization> findOrganizationList = organizationService.findOrganizationByOrganization(organization);
		selectAllChildren(findOrganizationList,organizationList);
		addAttributesToModel(model,"organizationList,organization",organizationList,organization);
		return  prefix + "organizationList";
	}

	/**
	 * 调到添加组织机构页面
	 * @param parentId
	 * @param model
     * @return
     */
	@RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.GET)
	public String showAppendChildForm(@PathVariable("parentId") Long parentId, Model model) {
		Organization organization = new Organization();
		organization.setParentId(parentId);
		Organization parent = null;
		if (parentId!=null && parentId==0)
			 parent = organizationService.findOrganizationByOrganization(organization).get(0);
		else
		     parent = organizationService.selectOne(parentId);
		List<Organization> organizations = organizationService.selectAll();
		addAttributesToModel(model,"parent,parentId,organizations",parent,parentId,organizations);
		return prefix + "organizationEdit";
	}
	/**
	 * 添加子组织机构数据
	 * @return
	 */
//	@RequiresPermissions("menu:create")
	@ResponseBody
	@RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.POST)
	public Result<?> create(Organization organization) {
		Result<Organization> result = new Result<Organization>();
		try {
			if (organization.getId()!=null){
				//更新修改人与修改时间
				String loginName = UserUtils.getCurrentUser().getUserName();
				organization.setUpdateBy(loginName);
				organization.setUpdateDate(new Date());
				//修改组织机构数据
				organizationService.updateOrganization(organization);
				result.setMsg("组织机构编辑成功");
			}else {
				//创建时间 创建人  修改时间  修改人
				String loginName =UserUtils.getCurrentUser().getUserName();
				Date date = new Date();
				organization.setCreateBy(loginName);
				organization.setCreateDate(date);
				organization.setUpdateBy(loginName);
				organization.setUpdateDate(date);
				organization.setStatus(1);//新建状态
				//删除标识
				organization.setDelFlag(0);
				organizationService.createOrganization(organization);
				result.setMsg("组织机构添加成功");
			}
		} catch (Exception e) {
			result.setCode("1");
			if (organization.getId()!=null)
				result.setMsg("组织机构编辑失败");
			else
				result.setMsg("组织机构添加失败");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 取出组织机构数据，进入修改组织机构页面
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("menu:update")
	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		Organization organization = organizationService.selectOne(id);
		Organization parent = organizationService.selectOne(organization.getParentId());
		List<Organization> organizations = organizationService.selectAll();
        addAttributesToModel(model,"organizations,organization,parent",organizations,organization,parent);
		return prefix + "organizationEdit";
	}
	/**
	 * 删除组织机构数据，逻辑删除
	 * @param id
	 * @return
	 */
//	@RequiresPermissions("menu:delete")
    @ResponseBody
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public Result<?> delete(@PathVariable("id") Long id) {
        Result<Object> result = new Result<Object>();
        try {
			List<Map<String, Object>> maps = organizationService.selectByOrgId(id);
			if (maps!=null && maps.size()>0){
				result.setCode("1");
				result.setMsg("请解除部门和职位关联关系!");
			}else {
				organizationService.deleteAllChildrenOrganization(id);
			}
            result.setMsg("删除成功!");
        } catch (Exception e) {
        	result.setCode("1");
            result.setMsg("删除失败!");
            e.printStackTrace();
        }
        return result;
	}
	/**
	 * 取出菜单数据，进入查看组织机构详细页面
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("menu:update")
	@RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
	public String showDetailForm(@PathVariable("id") Long id, Model model) {
		Organization organization = organizationService.selectOne(id);
		Organization parent = new Organization();
		if(organization.getParentId()!=0){
			parent = organizationService.selectOne(organization.getParentId());
		}
		addAttributesToModel(model,"organization,parent",organization,parent);
		return prefix + "organizationDetail";
	}
	/**
	 * 递归查询所有子组织机构
	 * @param organizationList 根据id作为父id查询直接所属的子组织机构
	 * @param organizationList2   得到所有子组织机构
	 */
	public void selectAllChildren(List<Organization> organizationList,List<Organization> organizationList2){
		if(organizationList.size()>0){
			for (Organization organization : organizationList) {
				organizationList2.add(organization);
				Organization findOrganization = new Organization();
				findOrganization.setParentId(organization.getId());
				List<Organization> findOrganizationByOrganization = organizationService.findOrganizationByOrganization(findOrganization);
				selectAllChildren(findOrganizationByOrganization,organizationList2);
			}
		}
	}
	/**
	 * 跳到组织机构添加职位页面
	 * @param organizationId
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/{organizationId}/addPosition", method = RequestMethod.GET)
	public String showAddPosition(@PathVariable("organizationId") Long organizationId, Model model) {
		Organization organization = organizationService.selectOne(organizationId);
		List<Position> positions = positionService.findPositionByParentId(new Position());
		addAttributesToModel(model,"organization,positions",organization,positions);
		return prefix + "addPosition";
	}
	@ResponseBody
	@RequestMapping(value = "/addPosition", method = RequestMethod.POST)
	public Result<?> addPosition(PositionAndOrganization positionAndOrganization) {
		Result<Object> result = new Result<Object>();
		try {
			if (positionAndOrganization.getPositionId()!=null && positionAndOrganization.getOrgId()!=null){
				Organization organization = organizationService.selectOne(positionAndOrganization.getOrgId());
				PositionAndOrganization findPositionAndOrganization = positionAndOrganizationService.selectByOrgIdAndPositionId(positionAndOrganization.getOrgId(),positionAndOrganization.getPositionId());
				if (findPositionAndOrganization==null) {
					positionAndOrganizationService.createPositionAndOrganization(positionAndOrganization);
					result.setMsg(organization.getOrgName()+"添加职位成功!");
				}else{
					result.setCode("1");
					result.setMsg(organization.getOrgName()+"已经存在该职位!");
				}
			}else{
				result.setCode("1");
				result.setMsg("请选择添加的职位或者组织机构!");
			}
		} catch (Exception e) {
			result.setCode("1");
			result.setMsg("添加职位失败!");
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 查看组织机构下职位
	 * @param organizationId
	 * @param model
     * @return
     */
	@RequestMapping(value="/{organizationId}/infoPosition" ,method = {RequestMethod.GET,RequestMethod.POST})
	public String saveDeployment(@PathVariable("organizationId") Long organizationId,Organization organization,Position position,Model model) {
		try {
			createPageHelper(organization);
    		PageInfo pageInfo  = organizationService.selectByCondition(organizationId,position.getName());
			addAttributesToModel(model,"page,organization,organizationId,position",pageInfo,organizationService.selectOne(organizationId),organizationId,position);
		}  catch (Exception e) {
			e.printStackTrace();
		}
		return prefix + "infoPosition";
	}
	@RequestMapping(value = "getPositionByOrgId", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getPositionByOrgId(Long orgId) {
		Map<String, Object> map = new HashMap<String, Object>();
		if (orgId!=null) {
			List<Map<String, Object>> list = organizationService.selectByOrgId(orgId);
			map.put("positionList", list);
		}
		return map;
	}

	/**
	 *
	 * @param id
	 * @return
     */
	@ResponseBody
	@RequestMapping(value = "/{id}/deletePosition", method = RequestMethod.POST)
	public Result<?> deletePosition(@PathVariable("id") Long id) {
		Result<Object> result = new Result<Object>();
		try {
			PositionAndOrganization positionAndOrganization = positionAndOrganizationService.selectOne(id);
			if (positionAndOrganization!=null){
				PositionUserOrganization positionUserOrganization = positionService.selectByPOByPOId(id);
				if (positionUserOrganization!=null){
					result.setCode("1");
					result.setMsg("该职位已经和用户关联,不能删除!");
				}else {
					positionAndOrganizationService.deletePositionAndOrganization(positionAndOrganization);
					result.setMsg("删除成功!");
				}
			}else{
				result.setCode("1");
				result.setMsg("该部门不存在该职位!");
			}
		} catch (Exception e) {
			result.setCode("1");
			result.setMsg("删除失败!");
			e.printStackTrace();
		}
		return result;
	}
}
