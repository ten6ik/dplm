package bstu.dplm.dao;

import org.hibernate.SessionFactory;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.List;

public abstract class GenericDao<T> {
    @Resource
    SessionFactory sessionFactory;

    private Class<T> clazz;

    protected GenericDao(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T saveOrUpdate(T t) {
        sessionFactory.getCurrentSession().saveOrUpdate(t);

        return t;
    }

    public List<T> getAll() {
        return sessionFactory.getCurrentSession().createCriteria(clazz).list();
    }

    public T getById(Serializable id) {
        return (T) sessionFactory.getCurrentSession().load(clazz, id);
    }
}
