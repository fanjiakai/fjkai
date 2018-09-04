package com.ddian.youfan.admin.service.impl;

import com.ddian.youfan.admin.dao.DictDao;
import com.ddian.youfan.admin.domain.DictDO;
import com.ddian.youfan.admin.service.DictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: WenheZhu
 * @Description:
 * @Date: Created in 14:042018/6/21
 * @Modified By:
 */
@Service
public class DictServiceImpl implements DictService {
    @Autowired
    private DictDao sysDictMapper;


    @Override
    public List<Map> list(Map<String, Object> map) {
        return sysDictMapper.list(map);
    }

    @Override
    public int count(Map<String, Object> map) {
        return sysDictMapper.count(map);
    }

    @Override
    public int save(DictDO dict) {
        return sysDictMapper.save(dict);
    }

    @Override
    public int remove(Long id) {
        return sysDictMapper.remove(id);
    }

    @Override
    public int update(DictDO dict) {
        return sysDictMapper.update(dict);
    }
}
