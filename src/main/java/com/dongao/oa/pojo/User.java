package com.dongao.oa.pojo;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

@Table(name = "da_oa_sys_user")
public class User extends BaseEntity {
    /**
     * 组织机构ID
     */
    @Column(name = "org_id")
    private Long orgId;

    /**
     * 用户名
     */
    @Column(name = "user_name")
    private String userName;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 盐值
     */
    private String salt;

    /**
     * 真实姓名
     */
    @Column(name = "real_name")
    private String realName;

    /**
     * 昵称
     */
    @Column(name = "nick_name")
    private String nickName;

    /**
     * 备注
     */
    private String description;

    /**
     * 联系电话
     */
    @Column(name = "link_phone")
    private String linkPhone;

    /**
     * 办公地点
     */
    private String address;

    /**
     * 座机
     */
    private String telephone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 职业：1老师，后续待扩展
     */
    private Integer profession;

    /**
     * 状态：1新建、2激活、3隐藏
     */
    private Integer status;

    /**
     * 锁定状态0未锁定，1已锁定
     */
    @Column(name = "lock_status")
    private Integer lockStatus;

    /**
     * 删除状态0未删除，1，已删除
     */
    @Column(name = "delete_flag")
    private Integer deleteFlag = 0;

    /**
     * 创建人
     */
    @Column(name = "create_by")
    private String createBy;

    /**
     * 创建时间
     */
    @Column(name = "create_date")
    private Date createDate;

    /**
     * 修改人
     */
    @Column(name = "update_by")
    private String updateBy;

    /**
     * 修改时间
     */
    @Column(name = "update_date")
    private Date updateDate;
    /**
     * 拼音排序
     */
    @Column(name = "sortLetters")
    private String sortLetters;
    /**
     * 姓名全拼
     */
    @Column(name = "py_name")
    private String pyNmae;
    /**
     * 性别 1,男,2女
     */
    @Column(name = "gender")
    private Integer gender;
    /**
     * 头像url
     */
    @Column(name = "img_url")
    private String imgUrl;
    /**
     * 生日
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "birthday")
    private Date birthday;
    /**
     * 入职日期
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "entry_date")
    private Date entryDate;
    public String getPyNmae() {
        return pyNmae;
    }

    public void setPyNmae(String pyNmae) {
        this.pyNmae = pyNmae;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    /**
     * 获取组织机构ID
     *
     * @return org_id - 组织机构ID
     */
    public Long getOrgId() {
        return orgId;
    }

    /**
     * 设置组织机构ID
     *
     * @param orgId 组织机构ID
     */
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }

    /**
     * 获取用户名
     *
     * @return user_name - 用户名
     */
    public String getUserName() {
        return userName;
    }

    /**
     * 设置用户名
     *
     * @param userName 用户名
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * 获取用户密码
     *
     * @return password - 用户密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置用户密码
     *
     * @param password 用户密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 获取盐值
     *
     * @return salt - 盐值
     */
    public String getSalt() {
        return salt;
    }

    /**
     * 设置盐值
     *
     * @param salt 盐值
     */
    public void setSalt(String salt) {
        this.salt = salt;
    }

    /**
     * 获取真实姓名
     *
     * @return real_name - 真实姓名
     */
    public String getRealName() {
        return realName;
    }

    /**
     * 设置真实姓名
     *
     * @param realName 真实姓名
     */
    public void setRealName(String realName) {
        this.realName = realName;
    }

    /**
     * 获取昵称
     *
     * @return nick_name - 昵称
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * 设置昵称
     *
     * @param nickName 昵称
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * 获取备注
     *
     * @return description - 备注
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置备注
     *
     * @param description 备注
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 获取联系电话
     *
     * @return link_phone - 联系电话
     */
    public String getLinkPhone() {
        return linkPhone;
    }

    /**
     * 设置联系电话
     *
     * @param linkPhone 联系电话
     */
    public void setLinkPhone(String linkPhone) {
        this.linkPhone = linkPhone;
    }

    /**
     * 获取办公地点
     *
     * @return address - 办公地点
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置办公地点
     *
     * @param address 办公地点
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 获取座机
     *
     * @return telephone - 座机
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置座机
     *
     * @param telephone 座机
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * 获取职业：1老师，后续待扩展
     *
     * @return profession - 职业：1老师，后续待扩展
     */
    public Integer getProfession() {
        return profession;
    }

    /**
     * 设置职业：1老师，后续待扩展
     *
     * @param profession 职业：1老师，后续待扩展
     */
    public void setProfession(Integer profession) {
        this.profession = profession;
    }

    /**
     * 获取状态：1新建、2激活、3隐藏
     *
     * @return status - 状态：1新建、2激活、3隐藏
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态：1新建、2激活、3隐藏
     *
     * @param status 状态：1新建、2激活、3隐藏
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取锁定状态0未锁定，1已锁定
     *
     * @return lock_status - 锁定状态0未锁定，1已锁定
     */
    public Integer getLockStatus() {
        return lockStatus;
    }

    /**
     * 设置锁定状态0未锁定，1已锁定
     *
     * @param lockStatus 锁定状态0未锁定，1已锁定
     */
    public void setLockStatus(Integer lockStatus) {
        this.lockStatus = lockStatus;
    }

    /**
     * 获取删除状态0未删除，1，已删除
     *
     * @return delete_flag - 删除状态0未删除，1，已删除
     */
    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    /**
     * 设置删除状态0未删除，1，已删除
     *
     * @param deleteFlag 删除状态0未删除，1，已删除
     */
    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    /**
     * 获取创建人
     *
     * @return create_by - 创建人
     */
    public String getCreateBy() {
        return createBy;
    }

    /**
     * 设置创建人
     *
     * @param createBy 创建人
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    /**
     * 获取创建时间
     *
     * @return create_date - 创建时间
     */
    public Date getCreateDate() {
        return createDate;
    }

    /**
     * 设置创建时间
     *
     * @param createDate 创建时间
     */
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    /**
     * 获取修改人
     *
     * @return update_by - 修改人
     */
    public String getUpdateBy() {
        return updateBy;
    }

    /**
     * 设置修改人
     *
     * @param updateBy 修改人
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    /**
     * 获取修改时间
     *
     * @return update_date - 修改时间
     */
    public Date getUpdateDate() {
        return updateDate;
    }

    /**
     * 设置修改时间
     *
     * @param updateDate 修改时间
     */
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}