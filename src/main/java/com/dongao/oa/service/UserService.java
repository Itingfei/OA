package com.dongao.oa.service;

import com.dongao.oa.pojo.PositionAndOrganization;
import com.dongao.oa.pojo.PositionUserOrganization;
import com.dongao.oa.pojo.User;
import com.dongao.oa.utils.PageInfo;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by fengjifei on 2016/8/4.
 */
public interface UserService {
    public User findByLoginName(String username);
    User selectOne(Long userId);
    public PageInfo<User> findAll();
    int createUser(User user,PositionAndOrganization positionAndOrganization,Long [] roles);

    PageInfo<Map<String,Object>> selectAll(Map map);

    Map<String,Object> selectUserInfoByUserId(Long id);

    int updateUser(User user, PositionAndOrganization positionAndOrganization, Long[] roles);

    int updateUserStatus(User user);
    /**
     * 根据用户名查找其权限
     * @param username
     * @return
     */
    Set<String> findPermissions(String username);

    /**
     * 根据用户ID查询用户.组织机构.职位关联关系
     * @param id
     * @return
     */
    List<Map<String,Object>> selectPUOByUserId(Long id);

    Set<String> findRoles(String username);
    PositionUserOrganization selectLeaderId(Long userId);
    List<User> selectAllUser();
    List<PositionUserOrganization> selectByPositionName(String positionName);
    int updatePassword(User user);

    /**
     * 通讯录按字母排序
     * @param name
     * @return
     */
    List<Map<String,Object>> selectAddressBookSortLetters(String name);
}
