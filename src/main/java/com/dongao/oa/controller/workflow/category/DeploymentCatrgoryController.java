package com.dongao.oa.controller.workflow.category;


import com.dongao.oa.controller.BaseController;
import com.dongao.oa.pojo.DeploymentCategory;
import com.dongao.oa.pojo.Result;
import com.dongao.oa.service.DeploymentCatrgoryService;
import com.dongao.oa.utils.PageInfo;
import com.dongao.oa.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.List;

import static com.dongao.oa.utils.UserUtils.getCurrentUser;

/**
 * Created by fengjifei on 2016/8/23.
 * 流程分类操作controller
 */

@Controller
@RequestMapping("/workflow/catrgory")
public class DeploymentCatrgoryController extends BaseController {
    // 上级目录
    private static final String prefix = "workflow/catrgory/";
    // 重定向地址
    private static final String redirectUrl = "redirect:/workflow/catrgory/";
    @Autowired
    public DeploymentCatrgoryService deploymentCatrgoryService;

    /**
     * 分类列表页
     *
     * @param redirectAttributes
     * @param model
     * @param deploymentCategory
     * @return
     */
    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(RedirectAttributes redirectAttributes, Model model, DeploymentCategory deploymentCategory) {
        try {
            createPageHelper(deploymentCategory);
            List<DeploymentCategory> list = deploymentCatrgoryService.selectAll();
            PageInfo pageInfo = new PageInfo(list);
            model.addAttribute("page", pageInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prefix + "catrgorylist";
    }

    /**
     * 编辑和新增的form页
     *
     * @param redirectAttributes
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/update", method = {RequestMethod.GET})
    public String showUpdate(RedirectAttributes redirectAttributes, Model model, @PathVariable("id") Long id) {
        DeploymentCategory deploymentCategory = new DeploymentCategory();
        if (id == 0) {//create
            model.addAttribute("deploy", deploymentCategory);
            return prefix + "catrgoryEdit";
        } else {
            deploymentCategory = deploymentCatrgoryService.selectOne(id);
            model.addAttribute("deploy", deploymentCategory);
            return prefix + "catrgoryEdit";
        }
    }

    /**
     * 添加保存分类
     *
     * @param redirectAttributes
     * @param model
     * @param deploymentCategory
     * @return
     */
    @RequestMapping(value = "/update", method = {RequestMethod.POST})
    @ResponseBody
    public Result<?> update(RedirectAttributes redirectAttributes, Model model, DeploymentCategory deploymentCategory) {
        Result<String> result = new Result<String>();
        if (deploymentCategory.getId() != null) {//更新
            deploymentCategory.setUpdateBy(UserUtils.getCurrentUser().getUserName());
            deploymentCategory.setUpdateDate(new Date());
            int i = deploymentCatrgoryService.update(deploymentCategory);
            if (i > 0) {
                result.setMsg("修改成功");
            } else {
                result.setCode("1");
                result.setMsg("修改失败");
            }
        } else {//保存
            deploymentCategory.setCreateBy(getCurrentUser().getUserName());
            deploymentCategory.setCreateDate(new Date());
            deploymentCategory = deploymentCatrgoryService.save(deploymentCategory);
            if (deploymentCategory.getId() != null) {
                result.setMsg("新增成功");
            } else {
                result.setCode("1");
                result.setMsg("新增失败");
            }
        }
        System.out.println("保存");
        return result;
    }

    /**
     * 删除分类
     *
     * @param redirectAttributes
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}/delete", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Result<?> delete(RedirectAttributes redirectAttributes, Model model, @PathVariable("id") Long id) {
        Result<String> result = new Result<String>();
        try {
            System.out.println("删除分类" + id);
            int i = deploymentCatrgoryService.delete(id);
            if (i > 0) {
                result.setMsg("删除成功");
            } else {
                result.setCode("1");
                result.setMsg("删除失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
