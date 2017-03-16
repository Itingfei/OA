package com.dongao.oa.controller.message;
import com.dongao.oa.config.SystemSettings;
import com.dongao.oa.controller.BaseController;
import com.dongao.oa.pojo.Category;
import com.dongao.oa.pojo.Message;
import com.dongao.oa.pojo.PositionUserOrganization;
import com.dongao.oa.pojo.Result;
import com.dongao.oa.service.CategoryService;
import com.dongao.oa.service.MessageService;
import com.dongao.oa.service.UserService;
import com.dongao.oa.utils.BaseConst;
import com.dongao.oa.utils.UserUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;
import java.util.List;

/**
 * 消息管理
 * yangjie
 */
@RequestMapping("/message")
@Controller
public class MessageController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	// 上级目录
	private static final String prefix = "message";
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private MessageService messageService;
	@Autowired
	private UserService userService;
	@Autowired
	private SystemSettings systemSettings;
	/**
	 * 消息列表展示
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("menu:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Message message, Model model) {
	    createPageHelper(message);
		addAttributesToModel(model,"page,message",messageService.selectAll(message),message);
		return  prefix + "/messageList";
	}
	/**
	 * 添加消息页面
	 */
	@RequestMapping("messageEdit")
	public String add(Model model, Message message) {
		if(message != null && message.getId() !=null)
			model.addAttribute("message", messageService.selectOne(message.getId()));
		model.addAttribute("message",message);
		model.addAttribute("categorys",categoryService.selectAllNoPage());
		return  prefix + "/messageEdit";
	}
	/**
	 * 保存消息 / 更新消息
	 *
	 * @param message
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save")
	public Result<?> save(Message message) {
		Result<Message> result = new Result<Message>();
		try {
			String loginName = UserUtils.getCurrentUser().getUserName();
			Date date = new Date();
			message.setCreateBy(loginName);
			message.setCreateDate(date);
			message.setUpdateBy(loginName);
			message.setUpdateDate(date);
			message.setSender(UserUtils.getCurrentUser().getId());
			if (message.getCategoryId()!=null){ //流程消息
				Category category = categoryService.selectOne(message.getCategoryId());
				Object positionName = UserUtils.getUserInfo().get("positionName");
				if (positionName!=null && positionName.equals(systemSettings.getMoneyLender())){ //流程结束后,出纳发送放款消息给采购专员
					List<PositionUserOrganization> positionUserOrganizations = userService.selectByPositionName(systemSettings.getPurchase());
					if (positionUserOrganizations!=null && positionUserOrganizations.size()>0) {
						for (PositionUserOrganization puo : positionUserOrganizations) {
							if (category.getBuyerId()==puo.getUserId()){//是当前采购员负责
								message.setRecipient(category.getBuyerId()); //设置收件人是负责当前流程的采购专员
								message.setSendTime(new Date());
								category.setStatus(BaseConst.ALREADYLOAN); //设为已放款
								category.setAssignee(category.getBuyerId()); //当前办理人
								categoryService.updateCategory(category,null,null);
							}
						}
					}
				}
				if (positionName!=null && positionName.equals(systemSettings.getPurchase())){ //采购完成后,发送消息给申请人
					Long userId = categoryService.selectOne(message.getCategoryId()).getUserId();
					message.setRecipient(userId);
					message.setSendTime(new Date());
					category.setStatus(BaseConst.PURCHASESUCCEED); //设为采购完成
					category.setAssignee(userId); //当前办理人
					categoryService.updateCategory(category,null,null);
				}
			}
			if (message.getId() == null || message.getId() == 0) {
                Message one = messageService.selectMessageByTitle(message.getTitle());
                if(one == null){
                    if(messageService.createMessage(message) > 0) {
                        result.setMsg("添加消息成功!");
                    }
                    else{
                        result.setCode("1");
                        result.setMsg("添加消息失败!");
                    }
                }else{
                    result.setCode("1");
                    result.setMsg("该消息标题已存在,请更换!");
                }
            } else {
                if(messageService.updateMessage(message) > 0){
                    result.setMsg("修改消息成功!");
                }
                else {
                    result.setCode("1");
                    result.setMsg("修改消息失败!");
                }
            }
		} catch (Exception e) {
			if (message.getId() == null || message.getId() == 0) {
				result.setCode("1");
				result.setMsg("添加消息失败!");
			}else{
				result.setCode("1");
				result.setMsg("修改消息失败!");
			}
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 删除消息
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}/delete",method = RequestMethod.POST)
	public Result<?> update(@PathVariable("id") Long id) {
		Result<Message> result = new Result<Message>();
		if (id==null && id<0){
			result.setCode("1");
			result.setMsg("请选择要删除的消息!");
		}else {
			Message message = messageService.selectOne(id);
			if (message==null){
				result.setCode("1");
				result.setMsg("要删除的消息不存在!");
			}else{
				message.setDelFlag(1);
				int count = messageService.updateMessage(message);
				if (count > 0) {
					result.setMsg("删除消息成功!");
				} else {
					result.setCode("1");
					result.setMsg("删除消息失败!");
				}
			}
		}
		return result;
	}
	@RequestMapping(value = "myMessage",method = RequestMethod.GET)
	public String myMessage(Message message, Model model) {
		createPageHelper(message);
		message.setRecipient(UserUtils.getCurrentUser().getId());
		addAttributesToModel(model,"page,message",messageService.selectAll(message),message);
		if (message.getId()!=null) {
			Message updatemessage = messageService.selectOne(message.getId());
			updatemessage.setStatus(1);
			messageService.updateMessage(updatemessage);
		}
		return  prefix + "/myMessageList";
	}
}
