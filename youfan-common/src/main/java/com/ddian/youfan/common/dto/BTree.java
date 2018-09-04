package com.ddian.youfan.common.dto;

import java.util.List;

@SuppressWarnings("unused")
public interface BTree {
    String getId();
    void setId(String id);
    List<BTree> getChildren();
    void setChildren(List<BTree> bTrees);
}
