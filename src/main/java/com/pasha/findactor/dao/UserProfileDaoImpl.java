package com.pasha.findactor.dao;

import com.pasha.findactor.model.UserProfile;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
@Slf4j
@Repository
public class UserProfileDaoImpl extends AbstractDao<Integer, UserProfile>
        implements UserProfileDao {

    /**
     * {@inheritDoc}
     */
    @Override
    public UserProfile findById(int id) {
        log.info("Search for user profile in database with id: {}", id);
        return getByKey(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserProfile findByType(String type) {
        log.info("Search for user profile in database with type: {}", type);
        Criteria criteria = createEntityCriteria();
        criteria.add(Restrictions.eq("type", type));
        return (UserProfile) criteria.uniqueResult();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<UserProfile> findAll() {
        log.info("Search for all user profiles in database");
        Criteria criteria = createEntityCriteria();
        criteria.addOrder(Order.asc("type"));
        List<UserProfile> profiles = (List<UserProfile>) criteria.list();
        return profiles;
    }
}
