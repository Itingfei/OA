package com.dongao.oa.controller;
import com.dongao.oa.pojo.BaseEntity;
import com.github.pagehelper.PageHelper;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thinkpad on 2016/8/11.
 */
public class BaseController {
    public static final Logger log = Logger.getLogger(BaseController.class);
    @Autowired
    public HttpServletRequest request;
    public void addAttributesToModel(Model model, String attributeFields, Object... attributeValues) {
        model.addAllAttributes(getParamValues(attributeFields, attributeValues));
    }
    public static Map<String, Object> getParamValues(String paramNames, Object[] values) {
        String[] params = paramNames.split(",");
        Assert.isTrue(params.length == values.length, "参数和值的个数应该相等。");
        Map pvs = new HashMap();
        for (int i = 0; i < params.length; i++) {
            pvs.put(params[i], values[i]);
        }
        return pvs;
    }

    public void createPageHelper(BaseEntity baseEntity){
        PageHelper.startPage(baseEntity.getPageNum(),baseEntity.getPageSize());
    }
    public Map<String, Object> getAPPUser(HttpServletRequest request) {
        return (Map<String, Object>) request.getSession(true).getAttribute("user");
    }
}
