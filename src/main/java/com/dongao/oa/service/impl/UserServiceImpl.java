package com.dongao.oa.service.impl;

import com.dongao.oa.dao.*;
import com.dongao.oa.pojo.*;
import com.dongao.oa.service.RoleService;
import com.dongao.oa.service.UserService;
import com.dongao.oa.utils.PageInfo;
import com.dongao.oa.utils.PasswordHelper;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.*;


/**
 * Created by fengjifei on 2016/8/4.
 */
@Transactional(readOnly = true)
@Service
@CacheConfig(cacheNames = "fengjifei")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserAndRoleMapper userAndRoleMapper;
    @Autowired
    private PositionAndOrganizationMapper positionAndOrganizationMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RoleService roleService;
    @Autowired
    private PositionUserOrganizationMapper positionUserOrganizationMapper;
    @Autowired
    private PositionMapper positionMapper;

    @Override
    @Cacheable(key = "#p0")
    public User findByLoginName(String username) {
        User user = new User();
        user.setUserName(username);
        user.setDeleteFlag(0);
        user = userMapper.selectOne(user);
//        userMapper.findByLoginName(username);
        return user;
    }

    @Override
    public User selectOne(Long userId) {
        return userMapper.selectByPrimaryKey(userId);
    }

    public PageInfo<User> findAll() {
        List<User> list1 = userMapper.selectAll();
        PageInfo pageInfo = new PageInfo(list1);
        return pageInfo;
    }
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,rollbackFor = RuntimeException.class,readOnly=false)
    @Override
    public int createUser(User user, PositionAndOrganization positionAndOrganization, Long[] roles) {
        String salt = (new Random(6)).toString();
        user.setSalt(salt);
        user.setStatus(2);//默认用户为激活状态
        user.setLockStatus(0);//锁定状态
        // 加密密码
        user.setPassword(DigestUtils.md5Hex(DigestUtils.md5Hex(user.getPassword())+salt));
        int result = userMapper.insert(user);//保存用户信息
        if (roles != null && roles.length > 0) {
            for (Long roleId : roles) {// 保存用户关联的角色
                UserAndRole userAndRole = new UserAndRole();
                userAndRole.setDeleteFlag(0);
                userAndRole.setUserId(user.getId());
                userAndRole.setRoleId(roleId);
                result = userAndRoleMapper.insert(userAndRole);
            }
        }
        //保存组织机构下职位
        if (positionAndOrganization.getOrgId() != null && positionAndOrganization.getPositionId() != null) {
            result = savePUO(user, positionAndOrganization, result);
        }
        return result;
    }

    private int savePUO(User user, PositionAndOrganization positionAndOrganization, int result) {
        PositionAndOrganization updatePositionAndOrganization = positionAndOrganizationMapper.selectOne(new PositionAndOrganization(positionAndOrganization.getPositionId(), positionAndOrganization.getOrgId()));
        //查出该用户上级ID
        List<Position> positions = positionMapper.selectByOrgIdAndPositionId(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId());
        if (positions != null && positions.size()>0 && positions.get(0).getIsLeader() == 1) {
            result = -1;
        } else {
            PositionUserOrganization positionUserOrganization = new PositionUserOrganization();
            List<PositionUserOrganization> leaders = positionUserOrganizationMapper.selectLeader(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId()); //查找要添加的用户有没有上级领导
            List<PositionUserOrganization> skipLeaders = positionUserOrganizationMapper.selectSkipLeader(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId()); //跃级查找要添加的用户有没有上级领导
            List<PositionUserOrganization> skipOrgLeaders = positionUserOrganizationMapper.selectSkipOrgLeader(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId()); //跃级查找要添加的用户有没有上级领导
            if (leaders!=null && leaders.size()>0) {
                for (PositionUserOrganization leader : leaders) {
                    if (leader != null && leader.getUserId() != null) {
                        positionUserOrganization.setLeaderId(leader.getUserId());//上级领导ID
                    }
                }
            }else if(skipLeaders!=null && skipLeaders.size()>0){
             //   List<PositionUserOrganization> skipLeaders = positionUserOrganizationMapper.selectSkipLeader(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId()); //跃级查找要添加的用户有没有上级领导
//                if (skipLeaders!=null && skipLeaders.size()>0){
                    positionUserOrganization.setLeaderId(skipLeaders.get(0).getUserId());
//                }
            }else if (skipOrgLeaders!=null && skipOrgLeaders.size()>0){
                if (skipOrgLeaders!=null && skipOrgLeaders.size()>0){
                    positionUserOrganization.setLeaderId(skipOrgLeaders.get(0).getUserId());
                }
            }
            positionUserOrganization.setUserId(user.getId());
            positionUserOrganization.setPositionOrganizationId(updatePositionAndOrganization.getId());
            positionUserOrganizationMapper.insert(positionUserOrganization);
            List<PositionUserOrganization> lowerLevels = positionUserOrganizationMapper.selectLowerLevel(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId());//查出下级
            if (lowerLevels!=null && lowerLevels.size()>0) {
                for (PositionUserOrganization puo:lowerLevels) {
                    if (puo!=null){
                            puo.setLeaderId(user.getId());//上级领导ID
                    }
                    positionUserOrganizationMapper.updateByPrimaryKey(puo);
                }
            }else{
                //查询下级用户等级
                List<Position> ps = positionMapper.selectLevelByOrgIdAndPid(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId());
                int maxLevel = positionMapper.selectMaxLevel();
                Long sign = null;
                for (Position p:ps){
                    if (p.getLevel()!=maxLevel){
                        sign = 1l;
                    }
                }
                List<PositionUserOrganization> skipLowerLevels = positionUserOrganizationMapper.selectSkipLowerLevel(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId(),sign);//越级查出下级
                if (skipLowerLevels!=null) {
                    for (PositionUserOrganization puo:skipLowerLevels) {
                        if (puo!=null){
                            puo.setLeaderId(user.getId());//上级领导ID
                        }
                        positionUserOrganizationMapper.updateByPrimaryKey(puo);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public PageInfo<Map<String, Object>> selectAll(Map map) {
        List<Map<String, Object>> maps = userMapper.selectAllByCondition(map);
        if (maps.size() > 0) {
            for (Map<String, Object> userMap : maps) {
                List<Map<String, Object>> roles = roleMapper.selectRolesByUserId((Long) userMap.get("userId"));
                String privilege = "";
                if (roles != null) {
                    for (Map<String, Object> role : roles) {
                        if (privilege.length() > 0)
                            privilege += ",";
                        privilege += role.get("NAME");
                    }
                }

                Object leaderId = userMap.get("leader_id");
                if (leaderId != null) {
                    User leaderUser = userMapper.selectByPrimaryKey(leaderId);
                    if (leaderUser!=null)
                         userMap.put("leaderName", leaderUser.getRealName());
                }
                userMap.put("roleName", privilege);
            }
        }
       PageInfo pageInfo = new PageInfo(maps);
        pageInfo.setList(maps);
        return pageInfo;
    }

    @Override
    public Map<String, Object> selectUserInfoByUserId(Long id) {
        return userMapper.selectUserInfoByUserId(id);
    }
    @Transactional(isolation= Isolation.DEFAULT,propagation= Propagation.REQUIRED,readOnly=false)
    @Override
    public int updateUser(User user, PositionAndOrganization positionAndOrganization, Long[] roles) {
        int flag = userMapper.updateByPrimaryKeySelective(user);// 更新用户信息
        UserAndRole deleteUserAndRole = new UserAndRole();
        deleteUserAndRole.setUserId(user.getId());
        userAndRoleMapper.delete(deleteUserAndRole);  // 删除 该用户下的 所有角色
        if (roles != null && roles.length > 0) {
            for (Long role : roles) {// 保存用户关联的角色
                UserAndRole userAndRole = new UserAndRole(user.getId(), role, 0);
                flag = userAndRoleMapper.insert(userAndRole);
            }
        }
        PositionUserOrganization findPUO = new PositionUserOrganization();
        findPUO.setUserId(user.getId());
        if (positionAndOrganization.getOrgId()!=null && positionAndOrganization.getPositionId()!=null) {
            PositionUserOrganization updatePUO = positionUserOrganizationMapper.selectOne(findPUO); //查出要编辑用户组织机构和职位关联对象
            if (updatePUO != null) {
                PositionAndOrganization pao = positionAndOrganizationMapper.selectByPrimaryKey(updatePUO.getPositionOrganizationId());
                if (pao.getPositionId() != positionAndOrganization.getPositionId() || pao.getOrgId() != positionAndOrganization.getOrgId()) {//组织机构和职位信息被改变
                    PositionAndOrganization updatePositionAndOrganization = positionAndOrganizationMapper.selectOne(new PositionAndOrganization(positionAndOrganization.getPositionId(), positionAndOrganization.getOrgId()));
                    //查出该用户上级ID
                    List<Position> positions = positionMapper.selectByOrgIdAndPositionId(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId());
                    if (positions != null && positions.size()>0 && positions.get(0).getIsLeader() == 1) {
                        flag = -1;
                    } else {
                        List<PositionUserOrganization> leaders = positionUserOrganizationMapper.selectLeader(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId()); //查找要添加的用户有没有上级领导
                        List<PositionUserOrganization> skipLeaders = positionUserOrganizationMapper.selectSkipLeader(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId()); //跃级查找要添加的用户有没有上级领导
                        List<PositionUserOrganization> skipOrgLeaders = positionUserOrganizationMapper.selectSkipOrgLeader(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId()); //跃级查找要添加的用户有没有上级领导
                        if (leaders!=null && leaders.size()>0) {
                           PositionUserOrganization leader = leaders.get(0);
                           if (leader != null && leader.getUserId() != null) {
                               updatePUO.setLeaderId(leader.getUserId());//上级领导ID
                           }
                        }else if (skipLeaders!=null && skipLeaders.size()>0){
//                            if (skipLeaders!=null && skipLeaders.size()>0){
                                updatePUO.setLeaderId(skipLeaders.get(0).getUserId());
//                            }
                        }else if (skipOrgLeaders!=null && skipOrgLeaders.size()>0){
                            if (skipOrgLeaders!=null && skipOrgLeaders.size()>0){
                                updatePUO.setLeaderId(skipOrgLeaders.get(0).getUserId());
                            }
                        }
                        setLeaderNull(updatePUO.getUserId());//把员工上级领导置为null
                        updatePUO.setUserId(user.getId());
                        updatePUO.setPositionOrganizationId(updatePositionAndOrganization.getId());
                        positionUserOrganizationMapper.updateByPrimaryKey(updatePUO);
                        List<PositionUserOrganization> lowerLevels = positionUserOrganizationMapper.selectLowerLevel(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId());//查出下级
                        if (lowerLevels!=null && lowerLevels.size()>0) {
                            for (PositionUserOrganization puo:lowerLevels) {
                                if (puo!=null){
                                    puo.setLeaderId(user.getId());//上级领导ID
                                }
                                positionUserOrganizationMapper.updateByPrimaryKey(puo);
                            }
                        }else{
                            //查询下级用户等级
                            List<Position> ps = positionMapper.selectLevelByOrgIdAndPid(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId());
                            int maxLevel = positionMapper.selectMaxLevel();
                            Long sign = null;
                            for (Position p:ps){
                                if (p.getLevel()!=maxLevel){
                                   // positionAndOrganization.setFlag(1);
                                    sign = 1l;
                                }
                            }
                            List<PositionUserOrganization> skipLowerLevels = positionUserOrganizationMapper.selectSkipLowerLevel(positionAndOrganization.getOrgId(), positionAndOrganization.getPositionId(),sign);//越级查出下级
                            if (skipLowerLevels!=null) {
                                for (PositionUserOrganization puo:skipLowerLevels) {
                                    if (puo!=null){
                                        puo.setLeaderId(user.getId());//上级领导ID
                                    }
                                    positionUserOrganizationMapper.updateByPrimaryKey(puo);
                                }
                            }
                        }
                    }
                }
            }else{ //之前在添加用户时没分配部门和职位
                flag = savePUO(user, positionAndOrganization, flag);
            }
        }else{ //说明要解除组织机构职位用户关联关系
            setLeaderNull(user.getId());//把员工上级领导置为null
            positionUserOrganizationMapper.delete(findPUO);
        }
        return flag;
    }

    /**
     * 清空员工上级领导
     * @param userId
     */
    private void setLeaderNull(Long userId) {
        //把该用户下的下属职员的上级领导ID设置为null
        Example example = new Example(PositionUserOrganization.class);
        example.createCriteria().andEqualTo("leaderId", userId);
        List<PositionUserOrganization> puos = positionUserOrganizationMapper.selectByExample(example);
        if (puos!=null && puos.size()>0) {
            for (PositionUserOrganization p:puos) {
                if (p != null) {
                    p.setLeaderId(null);
                    positionUserOrganizationMapper.updateByPrimaryKey(p);
                }
            }
        }
    }

    @Override
    public int updateUserStatus(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public Set<String> findPermissions(String username) {
        User user = findByLoginName(username);
        if (user == null) {
            return Collections.emptySet();
        }
        Example daSysUserRoleExample = new Example(UserAndRole.class);
        Example.Criteria criteria = daSysUserRoleExample.createCriteria();
        criteria.andEqualTo("userId", user.getId());
        List<UserAndRole> UserRoleList = userAndRoleMapper.selectByExample(daSysUserRoleExample);
        List<Long> roleIds = new ArrayList<Long>();
        for (int i = 0; i < UserRoleList.size(); i++) {
            UserAndRole daSysUserRole = UserRoleList.get(i);
            Role role = roleMapper.selectByPrimaryKey(daSysUserRole.getRoleId());
            if (role != null) {
                roleIds.add(daSysUserRole.getRoleId());
            }
        }
        return roleService.findPermissions(roleIds.toArray(new Long[0]));
    }

    @Override
    public List<Map<String, Object>> selectPUOByUserId(Long userId) {
        return positionUserOrganizationMapper.selectPUOByUserId(userId);
    }

    @Override
    public Set<String> findRoles(String username) {
        User user = findByLoginName(username);
        if (user == null) {
            return Collections.emptySet();
        }
        Example example = new Example(UserAndRole.class);
        example.createCriteria().andEqualTo("userId",user.getId());
        List<UserAndRole> userRolelist = userAndRoleMapper.selectByExample(example);
        List<Long> roleIds = new ArrayList<Long>();
        for(int i=0;i<userRolelist.size();i++){
            UserAndRole daSysUserRole = userRolelist.get(i);
            roleIds.add(daSysUserRole.getRoleId());
        }
        return roleService.findRoles(roleIds.toArray(new Long[0]));
    }

    @Override
    public PositionUserOrganization selectLeaderId(Long userId) {
        PositionUserOrganization positionUserOrganization = new PositionUserOrganization();
        positionUserOrganization.setUserId(userId);
        return positionUserOrganizationMapper.selectOne(positionUserOrganization);
    }

    @Override
    public List<User> selectAllUser() {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("deleteFlag",0);
        return userMapper.selectByExample(example);
    }

    @Override
    public List<PositionUserOrganization> selectByPositionName(String positionName) {
        return positionUserOrganizationMapper.selectByPositionName(positionName);
    }

    @Override
    public int updatePassword(User user) {
        return userMapper.updateByPrimaryKey(user);
    }

    @Override
    public List<Map<String, Object>> selectAddressBookSortLetters(String name) {
        return userMapper.selectAddressBookSortLetters(name);
    }
}
