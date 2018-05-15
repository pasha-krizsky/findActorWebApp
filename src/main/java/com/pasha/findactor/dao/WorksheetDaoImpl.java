package com.pasha.findactor.dao;

import com.pasha.findactor.model.Worksheet;
import com.pasha.findactor.model.constants.WorksheetFields;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
@Slf4j
@Component
public class WorksheetDaoImpl extends AbstractDao<Integer, Worksheet> implements WorksheetDao {

    private static final String STATUS_REVIEWED = "R";
    private static final String STATUS_CASTING = "C";
    private static final String STATUS_OFFER = "O";

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<Worksheet> findAllWorksheets() {
        log.info("Search for all worksheets in database");
        Criteria criteria = createEntityCriteria();
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Worksheet>) criteria.list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<Worksheet> findWorksheetsReviewed() {
        log.info("Search for worksheets in database with status: {}", STATUS_REVIEWED);
        Criteria criteria = createEntityCriteria().add(Restrictions.eq(WorksheetFields.STATUS, STATUS_REVIEWED));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Worksheet>) criteria.list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<Worksheet> findWorksheetsCasting() {
        log.info("Search for worksheets in database with status: {}", STATUS_CASTING);
        Criteria criteria = createEntityCriteria().add(Restrictions.eq(WorksheetFields.STATUS, STATUS_CASTING));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Worksheet>) criteria.list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public List<Worksheet> findWorksheetsOffer() {
        log.info("Search for worksheets in database with status: {}", STATUS_OFFER);
        Criteria criteria = createEntityCriteria().add(Restrictions.eq(WorksheetFields.STATUS, STATUS_OFFER));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (List<Worksheet>) criteria.list();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void saveWorksheet(Worksheet worksheet) {
        log.info("Save worksheet: {}", worksheet);
        persist(worksheet);
    }

    @Override
    @Transactional
    public Worksheet findById(Integer worksheetId) {
        log.info("Search for worksheet with id: {}", worksheetId);
        Worksheet worksheet = getByKey(worksheetId);
        return worksheet;
    }

    @Override
    @Transactional
    public Worksheet findByUserId(Integer userId) {
        log.info("Search for worksheet by user id: {}", userId);
        Criteria criteria = createEntityCriteria().createAlias("user", "user")
                .add(Restrictions.eq("user.id", userId));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return (Worksheet) criteria.uniqueResult();
    }

    @Override
    @Transactional
    public void updateWorksheet(Worksheet worksheet) {
        log.info("Update worksheet");
        update(worksheet);
    }
}
