package com.ddian.youfan.admin.dao;

import com.ddian.youfan.admin.domain.DictDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @Author: WenheZhu
 * @Description:
 * @Date: Created in 14:002018/6/21
 * @Modified By:
 */
@Mapper
public interface DictDao {

    List<Map> list(Map<String, Object> map);

    int count(Map<String, Object> map);

    int save(DictDO dict);

    int update(DictDO dict);

    int remove(Long id);

}
