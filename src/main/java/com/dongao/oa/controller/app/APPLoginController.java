package com.dongao.oa.controller.app;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dongao.oa.pojo.PositionAndOrganization;
import com.dongao.oa.pojo.User;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
/**
 * app登录控制类
 *
 * @author yangjie
 */
@Controller
public class APPLoginController {
@Autowired
public UserService userService;
    @RequestMapping(value = "/appLogin", method = {RequestMethod.POST,RequestMethod.GET})
    @ResponseBody
    public AppResultModel appLogin(@RequestBody String content, HttpServletRequest request) {
        AppResultModel appResultModel = new AppResultModel();
        ResultMessage resultMessage = new ResultMessage();
        try {
            content = JsonUtils.getString(content);
            User user = JsonUtils.jsonToBean(content,User.class);
            if (user!=null && StringUtils.isNotEmpty(user.getUserName()) && StringUtils.isNotEmpty(user.getPassword())){
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("userName",user.getUserName());
                PageInfo<Map<String,Object>> pageInfo= userService.selectAll(map);
                if (pageInfo.getList()!=null && pageInfo.getList().size()>0) {
                    Map<String, Object> currUser = pageInfo.getList().get(0);
                    if (currUser.get("password").equals(DigestUtils.md5Hex(DigestUtils.md5Hex(user.getPassword()) + currUser.get("salt")))) {
                        Object lock_status = currUser.get("lock_status");
                        if (((int)lock_status) == 1) {
                            resultMessage.setCode(BaseConst.LOCK);
                            resultMessage.setMsg("该用户已经被锁定!");
                        } else {
                            resultMessage.setCode(BaseConst.SUCCEED);
                            resultMessage.setMsg("登录成功");
                            appResultModel.setBody(currUser);
                            HttpSession session = request.getSession();
                            session.setAttribute("user",currUser);
                        }
                    } else {
                        resultMessage.setCode(BaseConst.WRONG);
                        resultMessage.setMsg("用户密码不正确");
                    }
                }else{
                    resultMessage.setCode(BaseConst.NONENTITY);
                    resultMessage.setMsg("用户不存在");
                }
            }else{
                resultMessage.setCode(BaseConst.NOTNULL);
                resultMessage.setMsg("用户和密码不能为空");
            }
        } catch (Exception e) {
            resultMessage.setCode(BaseConst.FAILURE);
            resultMessage.setMsg("登录失败");
            e.printStackTrace();
        }
        appResultModel.setResult(resultMessage);
        return appResultModel;
    }


}
