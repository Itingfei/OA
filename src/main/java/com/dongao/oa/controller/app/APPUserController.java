package com.dongao.oa.controller.app;
import com.alibaba.fastjson.JSONObject;
import com.dongao.oa.controller.BaseController;
import com.dongao.oa.pojo.Organization;
import com.dongao.oa.pojo.User;
import com.dongao.oa.service.OrganizationService;
import com.dongao.oa.service.UserService;
import com.dongao.oa.utils.BaseConst;
import com.dongao.oa.utils.JsonUtils;
import com.dongao.oa.utils.PageInfo;
import com.dongao.oa.utils.resultType.AppResultModel;
import com.dongao.oa.utils.resultType.ResultMessage;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
/**
 * app用户控制类
 *
 * @author yangjie
 */
@Controller
@RequestMapping("app")
public class APPUserController extends BaseController {
    @Autowired
    public UserService userService;
    @Autowired
    public OrganizationService organizationService;

    /**
     * 当前用户修改自己的密码
     *
     * @return
     */
    @RequestMapping("modifyPass")
    @ResponseBody
    public AppResultModel modifyPass(@RequestBody String content, HttpServletRequest request) {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage result = new ResultMessage();
        String msg = "";
        Map<String, Object> user = (Map<String, Object>) request.getSession(true).getAttribute("user");
        if (user != null && user.size() > 0) {
            content = JsonUtils.getString(content);
            JSONObject jsonObject = JsonUtils.toJsonObject(content);
            String passwordOld = (String) jsonObject.get("passwordOld");
            String passwordNew = (String) jsonObject.get("passwordNew");
            user.put("password", passwordOld);
            User adminUser = userService.selectOne((Long) user.get("userId"));
            if (adminUser != null) {
                if (adminUser.getPassword().equals(DigestUtils.md5Hex(DigestUtils.md5Hex(String.valueOf(user.get("password"))) + String.valueOf(adminUser.getSalt())))) {
                    try {
                        adminUser.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(passwordNew) + adminUser.getSalt()));//设置新加密的密码
                        userService.updatePassword(adminUser);
                        msg = "更新密码成功";
                    } catch (Exception e) {
                        result.setCode(BaseConst.FAILURE);
                        msg = "更新密码失败";
                    }
                } else {
                    result.setCode(BaseConst.WRONG);
                    msg = "原始密码不正确";
                }
            } else {
                result.setCode(BaseConst.NONENTITY);
                msg = "该用户不存在";
            }
        } else {
            result.setCode(BaseConst.NOTLOGIN);
            msg = "用户未登录";
        }
        result.setMsg(msg);
        appResultModel.setResult(result);
        return appResultModel;
    }


    /**
     * 通讯录按字母排序
     *
     * @return
     */
    @RequestMapping("bookSortLetters")
    @ResponseBody
    public AppResultModel addressBookSortLetters(@RequestBody String content,HttpServletRequest request) {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage resultMessage = new ResultMessage();
        Map<String, Object> sortLetters = new TreeMap<String, Object>();
        try {
            Map<String, Object> user = (Map<String, Object>) request.getSession(true).getAttribute("user");
            if (user != null && user.size() > 0) {
                String name ="";
                if (StringUtils.isNotEmpty(content)) {
                    JSONObject jsonObject = JsonUtils.toJsonObject(JsonUtils.getString(content));
                    name = (String) jsonObject.get("name");
                }
                List<Map<String, Object>> mapList = userService.selectAddressBookSortLetters(name);
                for (Map<String, Object> map : mapList) {
                    List<Map<String, Object>> sortLettersList = new ArrayList<Map<String, Object>>();
                    String result = (String) map.get("result");
                    if(StringUtils.isNotEmpty(result)) {
                        String[] split = result.split(",");
                        for (String res : split) {
                            String[] users = res.split(":");
                            Map<String, Object> userInfo = new HashMap<String, Object>();
                            userInfo.put("userId", users[0]);
                            userInfo.put("realName", users[1]);
                            userInfo.put("imgUrl", users[2]);
                            sortLettersList.add(userInfo);
                            sortLetters.put((String) map.get("sortLetters"), sortLettersList);
                        }
                    }
                }
            } else {
                resultMessage.setCode(BaseConst.NOTLOGIN);
                resultMessage.setMsg(BaseConst.USERNOTLOGIN);
            }
        } catch (Exception e) {
            resultMessage.setCode(BaseConst.FAILURE);
            resultMessage.setMsg(BaseConst.BookSortLettersFAILURE);
            e.printStackTrace();
        }
        appResultModel.setResult(resultMessage);
        appResultModel.setBody(sortLetters);
        return appResultModel;
    }

    /**
     * 通讯录按部门分类
     *
     * @return
     */
    @RequestMapping("bookDepartment")
    @ResponseBody
    public AppResultModel addressBookbookDepartment(String name) {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage resultMessage = new ResultMessage();
        Map<String, Object> sortDepartmentMap = new HashMap<String, Object>();
        try {
            Map<String, Object> user = (Map<String, Object>) request.getSession(true).getAttribute("user");
            if (user != null && user.size() > 0) {
                Organization organization = new Organization();
                organization.setPageSize(10000);
                organization.setParentId(1l);
                List<Organization> organizations = organizationService.findOrganizationByOrganization(organization);
                for (Organization org : organizations) {
                    Map<String, Object> map = new HashMap<String, Object>();
                    map.put("orgCode", org.getOrgCode());
                    createPageHelper(organization);
                    PageInfo<Map<String, Object>> pageInfo = userService.selectAll(map);
                    List<Map<String, Object>> list = pageInfo.getList();
                    List<Map<String, Object>> sortDepartmentList = new ArrayList<Map<String, Object>>();
                    for (Map<String, Object> depMap : list) {
                        Map<String, Object> userInfo = new HashMap<String, Object>();
                        userInfo.put("userId", depMap.get("userId"));
                        userInfo.put("realName", depMap.get("real_name"));
                        userInfo.put("imgUrl", depMap.get("img_url"));
                        sortDepartmentList.add(userInfo);
                    }
                    sortDepartmentMap.put(org.getOrgName(), sortDepartmentList);
                }
            } else {
                resultMessage.setCode(BaseConst.NOTLOGIN);
                resultMessage.setMsg(BaseConst.USERNOTLOGIN);
            }
        } catch (Exception e) {
            resultMessage.setCode(BaseConst.FAILURE);
            resultMessage.setMsg(BaseConst.BookSortLettersFAILURE);
            e.printStackTrace();
        }
        appResultModel.setResult(resultMessage);
        appResultModel.setBody(sortDepartmentMap);
        return appResultModel;
    }
    @RequestMapping(value = "/userDetail", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public AppResultModel categoryDetail(@RequestBody String userId) {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage resultMessage = new ResultMessage();
        try {
            Map<String, Object> user = getAPPUser(request);
            if (user != null && user.size() > 0) {
                JSONObject jsonObject = JsonUtils.toJsonObject(JsonUtils.getString(userId));
                userId = (String) jsonObject.get("id");
                if (StringUtils.isNotEmpty(userId)) {
                    appResultModel.setBody(userService.selectUserInfoByUserId(Long.valueOf(userId)));
                } else {
                    resultMessage.setCode(BaseConst.NOTNULL);
                    resultMessage.setMsg(BaseConst.USERIDNULL);
                }
            } else {
                resultMessage.setCode(BaseConst.NOTLOGIN);
                resultMessage.setMsg(BaseConst.USERNOTLOGIN);
            }
        } catch (Exception e) {
            resultMessage.setCode(BaseConst.FAILURE);
            resultMessage.setMsg(BaseConst.USERFAILURE);
            e.printStackTrace();
        }
        appResultModel.setResult(resultMessage);
        return appResultModel;
    }
}
