package com.ddian.youfan.admin.service.impl;

import com.ddian.youfan.admin.dao.DeptDao;
import com.ddian.youfan.admin.domain.DeptDO;
import com.ddian.youfan.admin.domain.Tree;
import com.ddian.youfan.admin.service.DeptService;
import com.ddian.youfan.admin.utils.BuildTree;
import com.ddian.youfan.common.context.FilterContextHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class DeptServiceImpl implements DeptService {
    @Autowired
    private DeptDao sysDeptMapper;


    @Override
    public DeptDO get(Long deptId) {
        return sysDeptMapper.get(deptId);
    }

    @Override
    public List<DeptDO> list(Map<String, Object> map) {
        /*map.put("scopeFilter", DataScopeHelper.dataScopeSubFilter(FilterContextHandler.getUserID(), FilterContextHandler.getDeptID(), CommonConstants.DATA_SCOPE_DEPT_AND_CHILD, "", "dept_id"));*/
        return sysDeptMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return sysDeptMapper.count(map);
    }

    @Override
    public int save(DeptDO sysDept) {
        String parentIds = sysDept.getParentIds() + "," + sysDept.getParentId();
        sysDept.setParentIds(parentIds);
        return sysDeptMapper.save(sysDept);
    }


    @Override
    public int update(DeptDO sysDept) {
        return sysDeptMapper.update(sysDept);
    }

    @Override
    public int remove(Long deptId) {
        return sysDeptMapper.remove(deptId);
    }

    @Override
    public int batchRemove(Long[] deptIds) {
        return sysDeptMapper.batchRemove(deptIds);
    }

    @Override
    public Tree<DeptDO> getTree() {
        List<Tree<DeptDO>> trees = new ArrayList<Tree<DeptDO>>();
        Map<String, Object> map = new HashMap<>(16);
        String deptId = "0";
        if(!FilterContextHandler.isAdmin()){  // 不是管理员需要加过滤条件
            deptId = FilterContextHandler.getDeptID();
        }
        List<DeptDO> sysDepts = this.list(map);
        for (DeptDO sysDept : sysDepts) {
            Tree<DeptDO> tree = new Tree<DeptDO>();
            tree.setId(sysDept.getDeptId().toString());
            tree.setParentId(sysDept.getParentId().toString());
            tree.setText(sysDept.getName());
            tree.setObject(sysDept);
            Map<String, Object> state = new HashMap<>(16);
            state.put("opened", true);
            tree.setState(state);
            trees.add(tree);
        }
        // 默认顶级菜单为０，根据数据库实际情况调整
        Tree<DeptDO> t = BuildTree.build(trees, deptId);
        return t;
    }

    @Override
    public boolean checkDeptHasUser(Long deptId) {
        // TODO Auto-generated method stub
        //查询部门以及此部门的下级部门
        int result = sysDeptMapper.getDeptUserNumber(deptId);
        return result == 0 ? true : false;
    }


    public static Long createNewCode(DeptDO parentDeptDO, DeptDO maxTCodeDeptDO, DeptDO sysDept) {
        Long newTCode = 0L;
        if (parentDeptDO != null) {
            if (maxTCodeDeptDO != null && maxTCodeDeptDO.gettCode() != null && maxTCodeDeptDO.gettCode() > 0) {
                newTCode = maxTCodeDeptDO.gettCode() + 1;
            } else {
                Long parentTCode = parentDeptDO.gettCode();
                newTCode = Long.parseLong(parentTCode + "001");
            }
        } else {
            if (maxTCodeDeptDO != null && maxTCodeDeptDO.gettCode() != null && maxTCodeDeptDO.gettCode() > 0) {
                newTCode = maxTCodeDeptDO.gettCode() + 1;
            } else {
                newTCode = 1L;
            }
        }
        return newTCode;
    }
}
