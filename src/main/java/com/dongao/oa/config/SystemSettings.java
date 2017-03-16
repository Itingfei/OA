package com.dongao.oa.config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by yangjie on 2016/9/13.
 * 系统配置信息
 */
@ConfigurationProperties(locations = "classpath:system.properties")
@Component
public class SystemSettings {
    private String moneyLender; //放款人
    private String purchase; //采购者
    private String flowEndTitle; //流程结束告知出纳员消息标题
    private String flowEndContent; //流程结束告知出纳员消息内容
    private String flowBeginTitle; //流程开始告知领导消息标题
    private String flowBeginContent; //流程开始告知领导消息内容
    private String leaderItemName;
    private String noleaderItemName;
    private String agree;
    private String reject;
    private String awaitloanMenu;
    private String purchaseMenu;
    private String enquiryMenu;
    private String inthereviewMenu;
    private String rejectMenu;
    private String agreeMenu;
    private String istartMenu;
    private String dasmskey;
    public String getFlowBeginTitle() {
        return flowBeginTitle;
    }

    public void setFlowBeginTitle(String flowBeginTitle) {
        this.flowBeginTitle = flowBeginTitle;
    }

    public String getFlowBeginContent() {
        return flowBeginContent;
    }

    public void setFlowBeginContent(String flowBeginContent) {
        this.flowBeginContent = flowBeginContent;
    }

    public String getMoneyLender() {
        return moneyLender;
    }

    public void setMoneyLender(String moneyLender) {
        this.moneyLender = moneyLender;
    }

    public String getPurchase() {
        return purchase;
    }

    public void setPurchase(String purchase) {
        this.purchase = purchase;
    }

    public String getFlowEndTitle() {
        return flowEndTitle;
    }

    public void setFlowEndTitle(String flowEndTitle) {
        this.flowEndTitle = flowEndTitle;
    }

    public String getFlowEndContent() {
        return flowEndContent;
    }

    public void setFlowEndContent(String flowEndContent) {
        this.flowEndContent = flowEndContent;
    }

    public String getLeaderItemName() {
        return leaderItemName;
    }

    public void setLeaderItemName(String leaderItemName) {
        this.leaderItemName = leaderItemName;
    }

    public String getNoleaderItemName() {
        return noleaderItemName;
    }

    public void setNoleaderItemName(String noleaderItemName) {
        this.noleaderItemName = noleaderItemName;
    }

    public String getAgree() {
        return agree;
    }

    public void setAgree(String agree) {
        this.agree = agree;
    }

    public String getReject() {
        return reject;
    }

    public void setReject(String reject) {
        this.reject = reject;
    }

    public String getAwaitloanMenu() {
        return awaitloanMenu;
    }

    public void setAwaitloanMenu(String awaitloanMenu) {
        this.awaitloanMenu = awaitloanMenu;
    }

    public String getPurchaseMenu() {
        return purchaseMenu;
    }

    public void setPurchaseMenu(String purchaseMenu) {
        this.purchaseMenu = purchaseMenu;
    }

    public String getEnquiryMenu() {
        return enquiryMenu;
    }

    public void setEnquiryMenu(String enquiryMenu) {
        this.enquiryMenu = enquiryMenu;
    }

    public String getInthereviewMenu() {
        return inthereviewMenu;
    }

    public void setInthereviewMenu(String inthereviewMenu) {
        this.inthereviewMenu = inthereviewMenu;
    }

    public String getRejectMenu() {
        return rejectMenu;
    }

    public void setRejectMenu(String rejectMenu) {
        this.rejectMenu = rejectMenu;
    }

    public String getAgreeMenu() {
        return agreeMenu;
    }

    public void setAgreeMenu(String agreeMenu) {
        this.agreeMenu = agreeMenu;
    }

    public String getIstartMenu() {
        return istartMenu;
    }

    public void setIstartMenu(String istartMenu) {
        this.istartMenu = istartMenu;
    }

    public String getDasmskey() {
        return dasmskey;
    }

    public void setDasmskey(String dasmskey) {
        this.dasmskey = dasmskey;
    }
}
