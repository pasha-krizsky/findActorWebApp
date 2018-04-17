package com.pasha.findactor.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 * This class is a simple wrapper around hibernate. It contains
 * methods to reuse in concrete dao objects.
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
public abstract class AbstractDao<PK extends Serializable, T> {

    /**
     * Class of entity.
     */
    private final Class<T> persistentClass;

    /**
     * Initializes {@link this#persistentClass} field by given generic parameter.
     */
    @SuppressWarnings("unchecked")
    public AbstractDao() {
        this.persistentClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    }

    /**
     * Factory to create {@link Session} objects.
     */
    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Returns current session to interact with database.
     *
     * @return current session
     */
    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    /**
     * Returns the entity from database by its primary key.
     *
     * @param key primary key to identify the entity
     * @return the entity or null
     */
    @SuppressWarnings("unchecked")
    public T getByKey(PK key) {
        return (T) getSession().get(persistentClass, key);
    }

    /**
     * Saves the entity to database.
     *
     * @param entity entity to save
     * @see Session#persist(Object)
     */
    public void persist(T entity) {
        getSession().persist(entity);
    }

    /**
     * Updates the existing entity in database.
     *
     * @param entity entity to update
     * @see Session#update(Object)
     */
    public void update(T entity) {
        getSession().update(entity);
    }

    /**
     * Deletes the entity from database.
     *
     * @param entity entity to delete
     * @see Session#delete(Object)
     */
    public void delete(T entity) {
        getSession().delete(entity);
    }

    /**
     * Creates {@link Criteria} object to be used later by concrete dao objects.
     *
     * @return {@link Criteria} object
     * @see Session#createCriteria(Class)
     */
    protected Criteria createEntityCriteria() {
        return getSession().createCriteria(persistentClass);
    }

}
