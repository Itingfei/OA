import com.dongao.oa.Application;
import com.dongao.oa.pojo.Category;
import com.dongao.oa.service.CategoryService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
//import sms.DaSmsManager;

import java.util.Date;

/**
 * Created by Thinkpad on 2016/9/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@SpringApplicationConfiguration(Application.class)
public class test {
    @Autowired
    private CategoryService categoryService;
    @Test
    public void test(){
//        Category category = categoryService.selectOne(49l);
//        System.out.println(category.getAssignee()+"修改前当前审批人");
//        category.setAssignee(117l);
//        categoryService.updateCategory(category,null,null);
//        System.out.println(category.getAssignee()+"修改后当前审批人");
        String dasmskey = "124#103#2#b9fc0e6a5151ca39056f9763f5f281cd";//东奥短信平台提供的秘钥
        String phoneNo = "13146950818";//接收短信的手机号码
        String content = "验证码";//信息内容
        Date date = new Date();//发送日期
        //String result = DaSmsManager.smsSender(dasmskey, phoneNo, content, date);
        //System.out.println(result);//返回结果代码
    }
}
