package com.dongao.oa.controller.base;

import com.dongao.oa.controller.BaseController;
import com.dongao.oa.pojo.Result;
import com.dongao.oa.pojo.Classify;
import com.dongao.oa.service.ClassifyService;
import com.dongao.oa.service.ClassifyService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 采购商品分类管理
 * yangjie
 */
@RequestMapping("/classify")
@Controller
public class ClassifyController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	// 上级目录
	private static final String prefix = "classify";
	@Autowired
	private ClassifyService classifyService;
	/**
	 * 采购物品分类列表展示
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("menu:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Classify classify, Model model) {
	    createPageHelper(classify);
		addAttributesToModel(model,"page,classify",classifyService.selectAll(classify),classify);
		return  prefix + "/classifyList";
	}
	/**
	 * 添加采购物品分类页面
	 */
	@RequestMapping("classifyEdit")
	public String add(Model model, Long id) {
		if(id != null && id > 0)
			model.addAttribute("classify", classifyService.selectOne(id));
		return  prefix + "/classifyEdit";
	}
	/**
	 * 保存采购物品分类 / 更新采购物品分类
	 *
	 * @param classify
	 * @return
	 */
	@ResponseBody
	@RequestMapping("save")
	public Result<?> save(Classify classify) {
		Result<Classify> result = new Result<Classify>();
		String loginName = UserUtils.getCurrentUser().getUserName();
		if (classify.getId() == null || classify.getId() == 0) {
			classify.setDelFlag(0);
			Date date = new Date();
			classify.setCreateBy(loginName);
			classify.setCreateDate(date);
			classify.setUpdateBy(loginName);
			classify.setUpdateDate(date);
			Classify one = classifyService.selectByName(classify.getName());
			if(one == null){
				if(classifyService.createClassify(classify) > 0) {
					result.setMsg("添加采购物品分类成功!");
				}
				else{
					result.setCode("1");
					result.setMsg("添加采购物品分类失败!");
				}
			}else{
				result.setCode("1");
				result.setMsg("该采购物品分类已存在!");
			}
		} else {
			if(classifyService.updateClassify(classify) > 0){
				result.setMsg("修改采购物品分类成功!");
			}
			else {
				result.setCode("1");
				result.setMsg("修改采购物品分类失败!");
			}
		}
		return result;
	}
	/**
	 * 删除采购物品分类
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/{id}/delete",method = RequestMethod.POST)
	public Result<?> update(@PathVariable("id") Long id) {
		Result<Classify> result = new Result<Classify>();
		if (id==null && id<0){
			result.setCode("1");
			result.setMsg("请选择要删除的采购物品分类!");
		}else {
			Classify classify = classifyService.selectOne(id);
			if (classify==null){
				result.setCode("1");
				result.setMsg("要删除的采购物品分类不存在!");
			}else{
				classify.setDelFlag(1);
				int count = classifyService.updateClassify(classify);
				if (count > 0) {
					result.setMsg("删除采购物品分类成功!");
				} else {
					result.setCode("1");
					result.setMsg("删除采购物品分类失败!");
				}
			}
		}
		return result;
	}
}
