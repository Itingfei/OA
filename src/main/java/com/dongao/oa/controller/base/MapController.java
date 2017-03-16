package com.dongao.oa.controller.base;

import com.dongao.oa.pojo.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by 张亚飞 on 2017/3/14.
 * 签到功能模块
 */
@RequestMapping("/qiandao")
@Controller
public class MapController {

    private Logger logger = LoggerFactory.getLogger(getClass());
    // 上级目录
    private static final String prefix = "qiandao";
    // 重定向地址
    private static final String redirectUrl = "redirect:/qiandao";

    /**
     * 地图展示
     * @param model
     * @return
     */
//	@RequiresPermissions("menu:view")
    @RequestMapping(method = RequestMethod.GET)
    public String list(Model model) {
        return  prefix + "/map";
    }
}
