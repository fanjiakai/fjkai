package com.ddian.youfan.admin.service;

import com.ddian.youfan.admin.domain.DictDO;

import java.util.List;
import java.util.Map;

/**
 * @Author: WenheZhu
 * @Description:
 * @Date: Created in 14:032018/6/21
 * @Modified By:
 */
public interface DictService {

    List<Map> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(DictDO dict);

    int remove(Long id);

    int update(DictDO dict);
}
