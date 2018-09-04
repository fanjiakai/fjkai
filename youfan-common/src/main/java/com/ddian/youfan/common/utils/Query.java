package com.ddian.youfan.common.utils;

import com.ddian.youfan.common.context.FilterContextHandler;

import java.util.LinkedHashMap;
import java.util.Map;

public class Query extends LinkedHashMap<String, Object> {
    private static final long serialVersionUID = 1L;
    protected int offset;
    protected int limit;
    protected int page;
    protected Map<String, String> sqlMap;

    /**
     * 可以根据 offset和limit查询，也可以根据page和limit查询
     *
     * @param params
     */
    public Query(Map<String, Object> params, boolean filter) {
        this.putAll(params);
        if (null != params.get("limit")) {
            this.limit = Integer.parseInt(params.get("limit").toString());
            if (null != params.get("offset")) {
                this.offset = Integer.parseInt(params.get("offset").toString());
                this.put("limit", limit);
            }
            if (null != params.get("page")) {
                this.page = Integer.parseInt(params.get("page").toString());
                this.put("offset", (page - 1) * limit);
                this.put("limit", limit);
            }
        }

        if (filter) {
            this.put("paging", true);
            this.put("filter", filter);
            this.put("currentUserId", FilterContextHandler.getUserID());
            this.put("isAdmin", FilterContextHandler.isAdmin());
            this.put("currentDeptId", FilterContextHandler.getDeptID());
        }
    }

    public Map<String, String> getSqlMap() {
        return sqlMap;
    }

    public void setSqlMap(Map<String, String> sqlMap) {
        this.put("sqlMap", sqlMap);
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.put("offset", offset);
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
