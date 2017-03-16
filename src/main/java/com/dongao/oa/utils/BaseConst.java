package com.dongao.oa.utils;

import com.dongao.oa.pojo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

/**
 * Created by Thinkpad on 2016/9/19.
 */
public class BaseConst {
    public static final String SUCCEED = "0";
    public static final String LOCK = "1"; //锁定
    public static final String TIMEOUT = "2";//超时
    public static final String WRONG = "3"; //用户密码错误
    public static final String NONENTITY = "4";//不存在
    public static final String NOTNULL = "5";//不能为空
    public static final String FAILURE = "7";//失败
    public static final String NOTLOGIN = "8";//未登陆
    public static final String USERNOTLOGIN = "用户未登陆";
    public static final String ITEMSNOTNULL = "采购单项不能为空";
    public static final String CATETNULL = "采购单不能为空";
    public static final String ITEMSFAILURE = "申请采购失败";
    public static final String CATEGORYIDNULL = "请传入采购申请ID";
    public static final String CATEGORYSUCCEED = "填写采购表单成功";
    public static final String TASKNOTNULL = "请选择要办理的任务";
    public static final String USERIDNULL = "请传入用户ID";
    public static final String CATEGORYNOTNULL = "要办理的任务没有采购表单";
    public static final String TASKSUCCEED = "办理任务成功";
    public static final String TASKFAILURE = "办理任务失败";
    public static final String CATEGORYFAILURE = "查看采购详情失败";
    public static final String BookSortLettersFAILURE = "查看通讯录失败";
    public static final String FAILURESTATUS = "查询失败";//失败
    public static final String NOTSTART = "0";
    public static final String INTHEREVIEW = "1";
    public static final String REJECT = "2";
    public static final String AGREE = "3";
    public static final String ENQUIRY = "4";
    public static final String AWAITLOAN = "5";
    public static final String ALREADYLOAN = "6";
    public static final String PURCHASESUCCEED = "7";
    public static final String ISTART = "8";//我发起的
    public static final String NOTSTARTSTATE = "未发起";
    public static final String INTHEREVIEWSTATE = "待审批";
    public static final String REJECTSTATE = "驳回";
    public static final String AGREESTATE = "同意";
    public static final String ENQUIRYSTATE = "询价中";
    public static final String AWAITLOANSTATE = "待放款";
    public static final String ALREADYLOANSTATE = "已放款";
    public static final String PURCHASESUCCEEDSTATE = "采购完成";
    public static final String NOTINVENTORYCODE = "0";
    public static final String INVENTORYMEETCODE = "1";
    public static final String NOTINVENTORY = "库存不足";
    public static final String INVENTORYMEET = "库存充足";
    public static final String PURCHASE = "1"; //采购事项
    public static final String USERFAILURE ="查看详细资料失败" ;
    public static final String SENDMESSAGESUCCEED ="发送通知成功" ;
    public static final String SENDMESSAGEFAILURE ="发送通知失败" ;
    public static String getCategoryStatus(String code) {
        if (code.equals(NOTSTART))
            return NOTSTARTSTATE;
        else if (code.equals(INTHEREVIEW))
            return INTHEREVIEWSTATE;
        else if (code.equals(REJECT))
            return REJECTSTATE;
        else if (code.equals(AGREE))
            return AGREESTATE;
        else if (code.equals(ENQUIRY))
            return ENQUIRYSTATE;
        else if (code.equals(AWAITLOAN))
            return AWAITLOANSTATE;
        else if (code.equals(ALREADYLOAN))
            return ALREADYLOANSTATE;
        return PURCHASESUCCEEDSTATE;
    }
    public static String getInventoryStatus(String code) {
        if (code.equals(NOTINVENTORYCODE))
            return NOTINVENTORY;
        else
            return INVENTORYMEET;
    }
}
