package com.zistrong.literacy.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;

/**
 * JPA使用原生Sql元素
 * 
 * @author Administrator
 *
 */
public class BaseNativeSqlRepository {

    @PersistenceUnit
    private EntityManagerFactory emf;

    /**
     * 查询多个属性 返回List<Object[]>数组形式的，数组中内容按照查询字段先后
     * 
     * @author savingyu
     * @author 2018年8月30日
     * @param sql
     * @return
     */
    public List<Object[]> sqlArrayList(String sql) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery(sql);
        List<Object[]> list = query.getResultList();
        em.close();
        return list;
    }

    public List<Object[]> sqlNativeArrayList(String sql) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNativeQuery(sql);
        List<Object[]> list = query.getResultList();
        em.close();
        return list;
    }

    public Object sqlSingleNativeArrayList(String sql) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNativeQuery(sql);
        Object list = query.getSingleResult();
        em.close();
        return list;
    }

    public void exeSqlNative(String sql) {
        EntityManager em = emf.createEntityManager();
        em.createNativeQuery(sql).executeUpdate();
        em.close();
    }

    /**
     * 查询多个属性 返回List<Object>对象形式的List，Object为Class格式对象
     * 
     * @author zhangziqiang
     * @author 2018年8月30日
     * @param sql
     * @param obj
     * @return
     */
    public List sqlObjectList(String sql, Object obj) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery(sql, obj.getClass());
        List list = query.getResultList();
        em.close();
        return list;
    }

    /**
     * 查询单个属性
     * 
     * @author savingyu
     * @author 2018年8月30日
     * @param sql
     * @return
     */
    public List sqlSingleList(String sql) {
        EntityManager em = emf.createEntityManager();
        Query query = em.createNamedQuery(sql);
        List list = query.getResultList();
        em.close();
        return list;
    }
}
