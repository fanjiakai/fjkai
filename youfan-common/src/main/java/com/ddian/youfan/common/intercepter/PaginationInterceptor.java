package com.ddian.youfan.common.intercepter;

import com.ddian.youfan.common.persistence.dialect.Dialect;
import com.ddian.youfan.common.utils.CacheCup;
import com.ddian.youfan.common.utils.Reflections;
import com.ddian.youfan.common.utils.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

/**
 * 多数据库分页支持组件
 * @Author: WenheZhu
 * @Description:
 * @Date: Created in 15:082018/6/5
 * @Modified By:
 */
@SuppressWarnings("all")
@Intercepts({@Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class PaginationInterceptor implements Interceptor {

    protected Log log = LogFactory.getLog(this.getClass());
    protected Dialect dialect;

    public PaginationInterceptor(Dialect dialect) {
        if (dialect == null) {
            log.debug("Dialect is Null!");
        }
        this.dialect = dialect;
        CacheCup.put(CacheCup.DB_TYPE,dialect.dialectName());
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        final MappedStatement mappedStatement = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];//获取分页参数对象
       if (parameter instanceof Map) {
           Map page = (Map) parameter;
           setProperty(page);
           BoundSql boundSql = mappedStatement.getBoundSql(parameter);
           if (page.get("page") != null && page.get("limit") != null && mappedStatement.getId().toLowerCase().indexOf("count") == -1) {
                int offset = Integer.parseInt(String.valueOf(page.get("offset")));
                int limit = Integer.parseInt(String.valueOf(page.get("limit")));
                if (StringUtils.isBlank(boundSql.getSql())) {
                    return null;
                }
                String originalSql = boundSql.getSql().trim();
                String pageSql = SQLHelper.generatePageSql(originalSql, offset, limit, dialect);
                invocation.getArgs()[2] = new RowBounds(RowBounds.NO_ROW_OFFSET, RowBounds.NO_ROW_LIMIT);
                BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), pageSql, boundSql.getParameterMappings(), boundSql.getParameterObject());
                //解决MyBatis 分页foreach 参数失效 start
                if (Reflections.getFieldValue(boundSql, "metaParameters") != null) {
                    MetaObject mo = (MetaObject) Reflections.getFieldValue(boundSql, "metaParameters");
                    Reflections.setFieldValue(newBoundSql, "metaParameters", mo);
                }
                //解决MyBatis 分页foreach 参数失效 end
                MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
                invocation.getArgs()[0] = newMs;
                if (log.isDebugEnabled()) {
                    log.debug("Page SQL:" + newBoundSql.getSql());
                }
            }
        }
        return invocation.proceed();
    }

    /***
     * 参数对象赋值
     * @param obj
     */
    private void setProperty(Object obj) {
        try {
            //TODO: 根据需要，将相关属性赋上默认值
            BeanUtils.setProperty(obj, "dbName", dialect.dialectName());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms,
                                                    SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(),
                ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null) {
            for (String keyProperty : ms.getKeyProperties()) {
                builder.keyProperty(keyProperty);
            }
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.cache(ms.getCache());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    @Override
    public Object plugin(Object o) {
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    public static class BoundSqlSqlSource implements SqlSource {
        BoundSql boundSql;

        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}
