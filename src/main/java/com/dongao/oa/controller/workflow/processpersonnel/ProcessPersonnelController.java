package com.dongao.oa.controller.workflow.processpersonnel;
import com.dongao.oa.controller.BaseController;
import com.dongao.oa.pojo.Organization;
import com.dongao.oa.pojo.ProcessPersonnel;
import com.dongao.oa.pojo.Result;
import com.dongao.oa.service.OrganizationService;
import com.dongao.oa.service.ProcessPersonnelService;
import com.dongao.oa.service.WorkflowService;
import com.dongao.oa.utils.PageInfo;
import com.dongao.oa.utils.UserUtils;
import org.activiti.engine.repository.ProcessDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 * 上下级管理
 * fengjifei
 */
@Controller
@RequestMapping("/processpersonnel")
public class ProcessPersonnelController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	// 上级目录
	private static final String prefix = "processpersonnel/";
	@Autowired
	private ProcessPersonnelService processPersonnelService;
	@Autowired
	public WorkflowService workflowService;
	@Autowired
	public OrganizationService organizationService;
	@ModelAttribute("organizations")
	public List<Organization> organizationList(){
		Organization organization = new Organization();
		organization.setDelFlag(0);
		return organizationService.findOrganizationByOrganization(organization);
	}
	/**
	 * 审批人关系列表展示
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("menu:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(ProcessPersonnel processPersonnel,Model model) {
		createPageHelper(processPersonnel);
		model.addAttribute("page", processPersonnelService.selectAll(processPersonnel));
		model.addAttribute("processpersonnel",processPersonnel);
		System.out.println("我就测是下");
		return  prefix + "processpersonnelList";
	}

	/**
	 * 调到添加审批人关系页面
	 * @param id
	 * @param model
     * @return
     */
	@RequestMapping("addPersonnel")
	public String add(Model model, Long id) {
		//2：查询流程定义表，获取流程定义的集合，返回List<ProcessDefinition>
		List<ProcessDefinition> pdList = workflowService.findProcessDefinitionList();
		model.addAttribute("pdList",pdList);
		if(id != null && id > 0)
			model.addAttribute("personnel", processPersonnelService.selectOne(id));
		return  prefix + "processpersonnelEdit";
	}
//	/**
//	 * 保存审批关系 / 更新审批关系
//	 *
//	 * @param processPersonnel
//	 * @return
//	 */
	@ResponseBody
	@RequestMapping("save")
	public Result<?> save(ProcessPersonnel processPersonnel) {
		Result<ProcessPersonnel> result = new Result<ProcessPersonnel>();
		try {
			String loginName = UserUtils.getCurrentUser().getUserName();
			if (processPersonnel.getId() == null || processPersonnel.getId() == 0) {
                processPersonnel.setDelFlag(0);
                Date date = new Date();
                processPersonnel.setCreateBy(loginName);
                processPersonnel.setCreateDate(date);
                processPersonnel.setUpdateBy(loginName);
                processPersonnel.setUpdateDate(date);
                ProcessPersonnel one = processPersonnelService.selectPPByTaskNameAndProcessKey(processPersonnel.getTaskname(), processPersonnel.getProcessKey());
                if(one == null){
                    if(processPersonnelService.createProcessPersonnel(processPersonnel) > 0) {
                        result.setMsg("添加审批人关系成功!");
                    }
                    else{
                        result.setCode("1");
                        result.setMsg("添加审批人关系失败!");
                    }
                }else{
                    result.setCode("1");
                    result.setMsg("任务名称和流程不能相同!");
                }
            } else {
                processPersonnel.setUpdateBy(loginName);
                processPersonnel.setUpdateDate(new Date());
                if(processPersonnelService.updateProcessPersonnel(processPersonnel) > 0){
                    result.setMsg("修改审批人关系成功!");
                }
                else {
                    result.setCode("1");
                    result.setMsg("修改审批人关系失败!");
                }
            }
		} catch (Exception e) {
			if (processPersonnel.getId() == null || processPersonnel.getId() == 0) {
				result.setCode("1");
				result.setMsg("添加审批人关系失败!");
			}else{
				result.setCode("1");
				result.setMsg("修改审批人关系失败!");
			}
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 删除审批人关系数据，逻辑删除
	 * @param id
	 * @return
	 */
//	@RequiresPermissions("menu:delete")
    @ResponseBody
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public Result<?> delete(@PathVariable("id") Long id) {
        Result<Object> result = new Result<Object>();
        try {
			if (processPersonnelService.deleteProcessPersonnel(id)>0){
				result.setMsg("删除成功!");
			}else{
				result.setCode("1");
				result.setMsg("删除失败!");
			}
        } catch (Exception e) {
			result.setCode("1");
            result.setMsg("删除失败!");
            e.printStackTrace();
        }
        return result;
	}
}
