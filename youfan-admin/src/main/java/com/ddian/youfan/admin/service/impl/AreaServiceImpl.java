package com.ddian.youfan.admin.service.impl;

import com.ddian.youfan.admin.dao.AreaDao;
import com.ddian.youfan.admin.dao.DeptDao;
import com.ddian.youfan.admin.dao.UserAreaDao;
import com.ddian.youfan.admin.domain.AreaDO;
import com.ddian.youfan.admin.domain.DeptDO;
import com.ddian.youfan.admin.domain.Tree;
import com.ddian.youfan.admin.domain.UserDO;
import com.ddian.youfan.admin.service.AreaService;
import com.ddian.youfan.admin.utils.BuildTree;
import com.ddian.youfan.common.context.FilterContextHandler;
import com.ddian.youfan.common.utils.DataScopeHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AreaServiceImpl extends DataScopeHelper implements AreaService {
    @Autowired
    private AreaDao areaMapper;
    @Autowired
    private DeptDao deptMapper;
    @Autowired
    private UserAreaDao userAreaMapper;

    @Override
    public AreaDO get(Long areaId) {
        return areaMapper.get(areaId);
    }

    @Override
    public List<AreaDO> list(Map<String, Object> map) {
        return areaMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return areaMapper.count(map);
    }

    @Override
    public int save(AreaDO sysArea) {
        String parentIds = sysArea.getParentIds() + "," + sysArea.getParentId();
        sysArea.setParentIds(parentIds);
        return areaMapper.save(sysArea);
    }

    @Override
    public int update(AreaDO sysArea) {
        return areaMapper.update(sysArea);
    }

    @Override
    public int remove(Long areaId) {
        return areaMapper.remove(areaId);
    }

    @Override
    public int batchRemove(Long[] areaIds) {
        return areaMapper.batchRemove(areaIds);
    }

    @Override
    public Tree<AreaDO> getTree() {
        List<Tree<AreaDO>> trees = new ArrayList<Tree<AreaDO>>();
        Map<String, Object> map = new HashMap<>(16);
        String areaId = "0";
        if (!FilterContextHandler.isAdmin()) {  // 不是管理员需要加过滤条件
            AreaDO areaDO = findAreaByDeptId(Long.valueOf(FilterContextHandler.getDeptID()));
            areaId = String.valueOf(areaDO.getAreaId());
            map.put("parentIds", areaDO.getParentIds());
            map.put("areaId", areaDO.getAreaId());
        }

        List<AreaDO> sysAreas = areaMapper.list(map);
        for (AreaDO sysArea : sysAreas) {
            Tree<AreaDO> tree = new Tree<AreaDO>();
            tree.setId(sysArea.getAreaId().toString());
            tree.setParentId(sysArea.getParentId().toString());
            tree.setText(sysArea.getName());
            tree.setObject(sysArea);
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<AreaDO> t = BuildTree.build(trees, areaId);
        return t;
    }

    @Override
    public boolean checkAreaHasUser(Long areaId) {
        // TODO Auto-generated method stub
        //查询部门以及此部门的下级部门
        int result = areaMapper.getAreaDeptNumber(areaId);
        return result == 0 ? true : false;
    }

    @Override
    public AreaDO findAreaByDeptId(Long deptId) {
        DeptDO deptDO = deptMapper.get(deptId);
        return areaMapper.get(deptDO.getAreaId());
    }


    //通过用户获取区域树
    @Override
    public List<Tree<AreaDO>> getTreeByUserId(UserDO userDO) {
        if (userDO != null) {
            DeptDO deptDO = deptMapper.get(userDO.getDeptId());
            AreaDO areaDO = areaMapper.get(deptDO.getAreaId());
            List<Tree<AreaDO>> trees = new ArrayList<Tree<AreaDO>>();
            List<AreaDO> sysAreas = areaMapper.listByTCode(areaDO.gettCode());
            for (AreaDO sysArea : sysAreas) {
                Tree<AreaDO> tree = new Tree<AreaDO>();
                tree.setId(sysArea.getAreaId().toString());
                tree.setParentId(sysArea.getParentId().toString());
                tree.setText(sysArea.getName());
                tree.setObject(sysArea);
                Map<String, Object> state = new HashMap<>(16);
                state.put("opened", true);
                tree.setState(state);
                trees.add(tree);
            }
            // 默认顶级菜单为０，根据数据库实际情况调整
            List<Tree<AreaDO>> t = BuildTree.buildList(trees, areaDO.getParentId().toString());
            return t;
        }
        return null;
    }

    /**
     * 获取用户的区域id
     *
     * @param userId
     * @return 区域id
     */

    @Override
    public List<Long> getAreaIdsByUserId(Long userId) {
        return userAreaMapper.listAreaId(userId);
    }

    //获取所有子节点
    @Override
    public List<AreaDO> getChildAreaByAreaId(Long areaId) {
        return areaMapper.getChildAreaByAreaId(areaId);
    }


    //塔式编码生成工具方法
    public static Long createNewCode(AreaDO parentAreaDo, AreaDO maxTCodeAreaDo, AreaDO sysArea) {
        Long newTCode = 0L;
        if (parentAreaDo != null) {
            if (maxTCodeAreaDo != null && maxTCodeAreaDo.gettCode() != null && maxTCodeAreaDo.gettCode() > 0) {
                newTCode = maxTCodeAreaDo.gettCode() + 1;
            } else {
                Long parentTCode = parentAreaDo.gettCode();
                newTCode = Long.parseLong(parentTCode + "001");
            }
        } else {
            newTCode = 1L;
        }
        return newTCode;
    }
}
