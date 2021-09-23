package com.biemo.cloud.core.data;

import com.biemo.cloud.core.util.SpringContextHolder;
import com.biemo.cloud.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import java.util.Collection;

/**
 * 批量对实体进行数据库操作
 *
 *
 * @date 2020-01-29-7:23 下午
 */
@Slf4j
public class BatchSqlExe {

    /**
     * 批量插入
     *
     *
     * @Date 2020/1/29 7:25 下午
     */
    public static <T> void saveBatch(SqlSessionFactory sqlSessionFactory, Class<T> clazz, Collection<T> entityList) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            String sqlStatement = SqlHelper.table(clazz).getSqlStatement(SqlMethod.INSERT_ONE.getMethod());
            for (Object entity : entityList) {
                sqlSession.insert(sqlStatement, entity);
            }
            sqlSession.flushStatements();
            sqlSession.commit();
        } catch (Throwable t) {
            sqlSession.rollback();
            log.error("批量执行sql错误！", t);
            throw new ServiceException(500, "批量执行sql错误!");
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 批量插入
     *
     *
     * @Date 2020/1/29 7:25 下午
     */
    public static <T> void saveBatch(Class<T> clazz, Collection<T> entityList) {
        SqlSessionFactory sqlSessionFactory = SpringContextHolder.getBean(SqlSessionFactory.class);
        saveBatch(sqlSessionFactory, clazz, entityList);
    }

    /**
     * 批量更新
     *
     *
     * @Date 2020/1/29 7:25 下午
     */
    @SuppressWarnings("all")
    public static <T> void updateBatchById(SqlSessionFactory sqlSessionFactory, Class<T> clazz, Collection<T> entityList) {
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        try {
            String sqlStatement = SqlHelper.table(clazz).getSqlStatement(SqlMethod.UPDATE_BY_ID.getMethod());
            for (T anEntityList : entityList) {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, anEntityList);
                sqlSession.update(sqlStatement, param);
            }
            sqlSession.flushStatements();
            sqlSession.commit();
        } catch (Throwable t) {
            sqlSession.rollback();
            log.error("批量执行sql错误！", t);
            throw new ServiceException(500, "批量执行sql错误!");
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 批量更新
     *
     *
     * @Date 2020/1/29 7:25 下午
     */
    public static <T> void updateBatchById(Class<T> clazz, Collection<T> entityList) {
        SqlSessionFactory sqlSessionFactory = SpringContextHolder.getBean(SqlSessionFactory.class);
        updateBatchById(sqlSessionFactory, clazz, entityList);
    }

}
