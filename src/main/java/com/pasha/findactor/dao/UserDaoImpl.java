package com.pasha.findactor.dao;

import com.pasha.findactor.model.User;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * An implementation of {@link UserDao}.
 *
 * @author Pavel.Krizskiy
 * @see User
 * @see UserDao
 * @see AbstractDao
 * @since 1.0.0
 */
@Slf4j
@Repository
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public User findById(int id) {
        log.info("Search for user with id: {}", id);
        User user = getByKey(id);
        if (user != null) {
            Hibernate.initialize(user.getUserProfiles());
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User findBySso(String sso) {
        log.info("Search for user with sso: {}", sso);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("ssoId", sso));
        User user = (User) criteria.uniqueResult();
        if (user != null) {
            Hibernate.initialize(user.getUserProfiles());
        }
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<User> findAll() {
        log.info("Search for all users in database");
        Criteria criteria = createEntityCriteria().addOrder(Order.asc("firstName"));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<User>) criteria.list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void save(User user) {
        log.info("Save users in database: {}", user);
        persist(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteBySso(String sso) {
        log.info("Delete user from database with sso: {}", sso);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("ssoId", sso));
        User user = (User) criteria.uniqueResult();
        delete(user);
    }
}
