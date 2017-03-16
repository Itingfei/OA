package com.dongao.oa.controller.base;
import com.dongao.oa.controller.BaseController;
import com.dongao.oa.pojo.Position;
import com.dongao.oa.pojo.Result;
import com.dongao.oa.service.PositionService;
import com.dongao.oa.service.UserService;
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
import java.util.*;
/**
 * 职位管理
 * yangjie
 */
@Controller
@RequestMapping("/position")
public class PositionController extends BaseController{
	private Logger logger = LoggerFactory.getLogger(getClass());
	// 上级目录
	private static final String prefix = "position/";
	// 重定向地址
	private static final String redirectUrl = "redirect:/position";
	@Autowired
	private PositionService positionService;
	@Autowired
	public UserService userService;
	/**
	 * 职位列表展示
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("menu:view")
	@RequestMapping(method = RequestMethod.GET)
	public String list(Position position,Model model) {
		List<Position> positionList = new ArrayList<Position>();
		position.setParentId(0l);
		List<Position> findPositionByParentId = positionService.findPositionByParentId(position);
		selectAllChildren(findPositionByParentId,positionList);
		model.addAttribute("positionList", positionList);
		model.addAttribute("position",position);
		return  prefix + "positionList";
	}

	/**
	 * 调到添加职位页面
	 * @param parentId
	 * @param model
     * @return
     */
	@RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.GET)
	public String showAppendChildForm(@PathVariable("parentId") Long parentId, Model model) {
		Position parent = positionService.selectOne(parentId);
		List<Position> menuList = positionService.selectAll();
		addAttributesToModel(model,"parent,parentId,menuList",parent,parentId,menuList);
		return prefix + "positionEdit";
	}
	/**
	 * 添加子职位数据
	 * @return
	 */
//	@RequiresPermissions("menu:create")
	@ResponseBody
	@RequestMapping(value = "/{parentId}/appendChild", method = RequestMethod.POST)
	public Result<?> create(Position position) {
		Result<Position> result = new Result<Position>();

		try {
			Long parentId = position.getParentId();
			if (position.getId()!=null){
				if (parentId==0) {
					position.setLevel(1);//父职位ID为0说该职位是一级职位
				}
				else {
					position.setLevel(positionService.selectOne(parentId).getLevel()+1);//查询出父级职位的等级+1
				}
				//更新修改人与修改时间
				String loginName = UserUtils.getCurrentUser().getUserName();
				position.setUpdateBy(loginName);
				position.setUpdateDate(new Date());
				//修改职位数据
				positionService.updatePosition(position);
				result.setMsg("职位编辑成功");
			}else {
				//创建时间 创建人  修改时间  修改人
				String loginName = UserUtils.getCurrentUser().getUserName();
				Date date = new Date();
				position.setCreateBy(loginName);
				position.setCreateDate(date);
				position.setUpdateBy(loginName);
				position.setUpdateDate(date);
				if (parentId==0) {
					position.setLevel(1);//父职位ID为0说该职位是一级职位
				}
				else {
					position.setLevel(positionService.selectOne(parentId).getLevel()+1);//查询出父级职位的等级+1
				}
				//删除标识
				position.setDelFlag(0);
				positionService.createPosition(position);
				result.setMsg("职位添加成功");
			}
		} catch (Exception e) {
			result.setCode("1");
			if (position.getId()!=null)
				result.setMsg("职位编辑失败");
			else
				result.setMsg("职位添加失败");
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 取出职位数据，进入修改职位页面
	 * @param model
	 * @return
	 */
//	@RequiresPermissions("menu:update")
	@RequestMapping(value = "/{id}/update", method = RequestMethod.GET)
	public String showUpdateForm(@PathVariable("id") Long id, Model model) {
		Position position = positionService.selectOne(id);
		Position parent = positionService.selectOne(position.getParentId());
		List<Position> menuList = positionService.selectAll();
        addAttributesToModel(model,"menuList,position,parent",menuList,position,parent);
		return prefix + "positionEdit";
	}
	/**
	 * 删除职位数据，逻辑删除
	 * @param id
	 * @return
	 */
//	@RequiresPermissions("menu:delete")
    @ResponseBody
	@RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
	public Result<?> delete(@PathVariable("id") Long id) {
        Result<Object> result = new Result<Object>();
        try {
            positionService.deleteAllChildrenPosition(id);
            result.setMsg("删除成功!");
        } catch (Exception e) {
			result.setCode("1");
            result.setMsg("删除失败!");
            e.printStackTrace();
        }
        return result;
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
				List<Position> findPositionByParentId = positionService.findPositionByParentId(findPosition);
				selectAllChildren(findPositionByParentId,positionList2);
			}
		}
	}
}
