package com.pasha.findactor.dao;

import com.pasha.findactor.model.Worksheet;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * This DAO is aimed to work with {@link com.pasha.findactor.model.constants.Tables#WORKSHEET} entity
 * and used for saving worksheets, finding worksheets, etc.
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
public interface WorksheetDao {

    /**
     * Retrieves all worksheets from database.
     */
    List<Worksheet> findAllWorksheets();

    /**
     * Retrieves worksheets to be reviewed by {@link com.pasha.findactor.model.constants.UserProfileType#AGENT}.
     */
    List<Worksheet> findWorksheetsReviewed();

    /**
     * Retrieves worksheets already reviewed by {@link com.pasha.findactor.model.constants.UserProfileType#AGENT}
     * and passed to the next stage (casting).
     */
    List<Worksheet> findWorksheetsCasting();

    /**
     * Retrieves worksheets which are successfully passed casting.
     */
    List<Worksheet> findWorksheetsOffer();

    /**
     * Saves new worksheet to database.
     */
    void saveWorksheet(Worksheet worksheet);

    /**
     * Finds and returns {@link Worksheet} by its id.
     */
    Worksheet findById(Integer worksheetId);

    /**
     * Finds and returns {@link Worksheet} by user id.
     */
    Worksheet findByUserId(Integer userId);

    void updateWorksheet(Worksheet worksheet);

    SessionFactory getSessionFactory();
}
