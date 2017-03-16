package com.dongao.oa.controller.workflow;

import com.dongao.oa.controller.BaseController;
import com.dongao.oa.form.WorkflowBean;
import com.dongao.oa.pojo.Category;
import com.dongao.oa.pojo.Result;
import com.dongao.oa.pojo.User;
import com.dongao.oa.service.CategoryService;
import com.dongao.oa.service.WorkflowService;
import com.dongao.oa.utils.PageInfo;
import com.dongao.oa.utils.UserUtils;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Created by fengjifei on 2016/8/3.
 * 工作流操作controller
 */

@Controller
@RequestMapping("/workflow")
public class WorkFlowController extends BaseController{
    // 上级目录
    private static final String prefix = "workflow/";
    private static final String childFolder1 ="deployment/";
    private static final String childFolder2 ="task/";
    // 重定向地址
    private static final String redirectUrl = "redirect:/workflow";
//    @Autowired
    public RepositoryService repositoryService;
    @Autowired
    public WorkflowService workflowService;
    @Autowired
    private CategoryService categoryService;
    /**
     * showuploadform
     * @param model
     * @param user
     * @return
     */
    @RequestMapping("/showUploadForm")
    public String showUploadForm(Model model, User user){
        return prefix+childFolder1+"deploymentupload";
    }

    /**
     * 上传流程定义
     * @param file
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value="/upload" ,method = {RequestMethod.GET,RequestMethod.POST})
    public String saveDeployment(@RequestParam(value = "file", required = false) MultipartFile file, RedirectAttributes redirectAttributes, Model model) {
        try {
            workflowService.saveDeployment(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "form_webuploader";
    }

//
//    @RequestMapping(value="/list" ,method = {RequestMethod.GET,RequestMethod.POST})
//    public String saveDeployment(RedirectAttributes redirectAttributes, Model model) {
//        try {
//            List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()//
//            .orderByProcessDefinitionVersion().asc()//
//            .listPage(1,20);
//            PageInfo pageInfo = new PageInfo(list);
//            model.addAttribute("page",pageInfo);
//        }  catch (Exception e) {
//            e.printStackTrace();
//        }
//        return "form_webuploader";
//    }

    /**
     * 流程主页
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value="/deployHome" ,method = {RequestMethod.GET,RequestMethod.POST})
    public String deployHome(RedirectAttributes redirectAttributes, Model model) {
        try {
            //1：查询部署对象表，获取部署对象的集合，返回List<Deployment>
            List<Deployment> deploymentList = workflowService.findDeploymentList();
            //2：查询流程定义表，获取流程定义的集合，返回List<ProcessDefinition>
            List<ProcessDefinition> pdList = workflowService.findProcessDefinitionList();
            model.addAttribute("deploymentList",deploymentList);
            model.addAttribute("pdList",pdList);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return prefix+childFolder1+"deployHome";
    }
    /**
     * 流程发起
     * @param redirectAttributes
     * @param model
     * @return
     */
    @RequestMapping(value="/deploystart" ,method = {RequestMethod.GET,RequestMethod.POST})
    public String deploystart(RedirectAttributes redirectAttributes, Model model) {
        try {
            //1：查询部署对象表，获取部署对象的集合，返回List<Deployment>
            List<Deployment> deploymentList = workflowService.findDeploymentList();
            //2：查询流程定义表，获取流程定义的集合，返回List<ProcessDefinition>
            List<ProcessDefinition> pdList = workflowService.findProcessDefinitionList();
            model.addAttribute("deploymentList",deploymentList);
            model.addAttribute("pdList",pdList);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return prefix+childFolder1+"deploystart";
    }
    /**
     * 删除流程定义文件
     * @param redirectAttributes
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/delete" ,method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Result<?> delDeployment(RedirectAttributes redirectAttributes, Model model,@PathVariable("id") Long id) {
        Result<String> result = new Result<String>();
        try {
            System.out.println("删除流程定义"+id);
            workflowService.deleteDeploymentById(id+"");
            result.setMsg("删除成功");
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 查看流程图片
     * @param response
     * @param redirectAttributes
     * @param model
     * @param id
     * @param resourceName
     * @return
     */
    @RequestMapping(value="/viewImage" ,method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Result<?> viewImage(HttpServletResponse response ,RedirectAttributes redirectAttributes, Model model, String id,String resourceName) {
        Result<String> result = new Result<String>();
        try {
            //1：获取页面上传递的部署ID和资源图片名称
            //资源图片名称
            //2：使用部署ID和资源图片名称，查询资源文件表，获取图片的输入流
            InputStream in = workflowService.findInputStreamWithImage(id,resourceName);
            //3：将输入流写到response的输出流中，完成查看流程图
            OutputStream os = response.getOutputStream();
//            PrintWriter pw = response.getWriter();
            for(int b=-1;(b=in.read())!=-1;){
                os.write(b);
            }
            os.close();
            in.close();
            return result;
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 启动流程
     * @param redirectAttributes
     * @param model
     * @param id
     * @return
     */
    @RequestMapping(value="/{id}/startProcess" ,method = {RequestMethod.GET,RequestMethod.POST})
    public String startProcess(RedirectAttributes redirectAttributes, Model model,@PathVariable("id") Long id) {
        try {
            workflowService.saveStartProcess(id);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return prefix+childFolder1+"deployHome";
    }

    /**
     * 个人任务查询
     * @param user
     * @param model
     * @return
     */
    @RequestMapping(value="/tasklist" ,method = {RequestMethod.GET,RequestMethod.POST})
    public String tasklist(User user,Model model) {
        try {
            Long userid = UserUtils.getCurrentUser().getId();
            user.setId(userid);
            createPageHelper(user);
            PageInfo<Task> pageInfo =  workflowService.findPersonalTaskById(user);
            model.addAttribute("page",pageInfo);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return prefix+childFolder2+"taskList";
    }
    /**
     * 提交任务
     * @param redirectAttributes
     * @param model
     * @param workflowBean
     * @return
     */
    @RequestMapping(value="/submitTask" ,method = {RequestMethod.GET,RequestMethod.POST})
    public String submitTask(RedirectAttributes redirectAttributes, Model model, WorkflowBean workflowBean) {
//        try {
//            Long userid = UserUtils.getCurrentUser().getId();
//            List<Task> list =  workflowService.findPersonalTaskById(userid+"");
//            model.addAttribute("page",list);
//        }  catch (Exception e) {
//            e.printStackTrace();
//        }
        return prefix+childFolder1+"deployHome";//返回提交成功的页面
    }

    /**
     * 查看流程进度图
     * @param redirectAttributes
     * @param model
     * @param taskId
     * @return
     */
    @RequestMapping(value="/viewCurrentImage" ,method = {RequestMethod.GET,RequestMethod.POST})
    public String viewCurrentImage(RedirectAttributes redirectAttributes, Model model,String taskId){
        ProcessDefinition processDefinition = workflowService.findProcessDefinitionByTaskID(taskId);
        model.addAttribute("deploymentId",processDefinition.getDeploymentId());
        model.addAttribute("imageName",processDefinition.getDiagramResourceName());
        Map<String, Object> map = workflowService.findCoodingListByTaskID(taskId);
        model.addAttribute("acs",map);
        return  prefix+childFolder1+"deployimage";
    }

    @RequestMapping(value="/viewHisComment" ,method = {RequestMethod.GET,RequestMethod.POST})
    public String viewHisComment(RedirectAttributes redirectAttributes, Model model, Long id) {
        try {
            Category category = new Category();
            List<Comment> commentList = workflowService.getCommentListByObject(category);
            model.addAttribute("commentList", commentList);
        }  catch (Exception e) {
            e.printStackTrace();
        }
        return prefix+childFolder1+"deployHome";//返回查询历史的页面
    }

}
