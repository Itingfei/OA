package com.dongao.oa.controller;
import com.dongao.oa.pojo.Menu;
import com.dongao.oa.pojo.Message;
import com.dongao.oa.pojo.User;
import com.dongao.oa.service.MenuService;
import com.dongao.oa.service.MessageService;
import com.dongao.oa.service.RoleService;
import com.dongao.oa.service.UserService;
import com.dongao.oa.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * Created by fengjifei on 2016/8/2.
 * 主页controller
 */
@Controller
public class IndexController extends BaseController{
    @Autowired
    private MenuService menuService;
    @Autowired
    private MessageService messageService;
    @RequestMapping("/index")
    public String index(HttpServletRequest request,Model model) {
        System.out.println("成功跳转");
        // 加载 系统菜单， 并放到session中， 以便于 每次请求调用
        HttpSession session = request.getSession(true);
        List<Message> messages = messageService.selectByRecipient(UserUtils.getCurrentUser().getId());//查询我的未读消息
        if(session.getAttribute("admin_menu_root") == null){
            User user = (User) session.getAttribute("admin");
            List<Menu> rootList = new ArrayList<Menu>(), levelList = new ArrayList<Menu>();
            Map<String, Object> levelMap = new HashMap<String, Object>();
            Map<String,Object> findMap = new HashMap<String,Object>();
            findMap.put("userId",UserUtils.getCurrentUser().getId());
            findMap.put("type",0);
            List<Menu> menus = menuService.findMenusByUserId(findMap);
            if(user != null){
                for(Menu menu : menus){
                    if(menu.getParentId() == 0)
                        rootList.add(menu);
                    else
                        levelList.add(menu);
                }
                // 封装一级下的 二级菜单
                for(Menu menu : rootList){
                    List<Menu> level = new ArrayList<Menu>();
                    for(Menu mm : levelList){
                        if(mm.getParentId().equals(menu.getId())){
                            level.add(mm);
                        }
                    }
                    levelMap.put(menu.getId().toString(), level);
                }
                session.setAttribute("admin_menu_root", rootList);
                session.setAttribute("admin_menu_level", levelMap);
                session.setAttribute("messages", messages);
            }
        }
//        model.addAttribute("messages",messages);
        return "index";
    }
}
