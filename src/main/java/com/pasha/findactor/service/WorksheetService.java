package com.pasha.findactor.service;

import com.pasha.findactor.model.Worksheet;

import java.util.List;

/**
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
public interface WorksheetService {
    List<Worksheet> findAllWorksheets();

    List<Worksheet> findWorksheetsReviewed();

    List<Worksheet> findWorksheetsCasting();

    List<Worksheet> findWorksheetsOffer();

    void saveWorksheet(Worksheet worksheet);

    void saveWorksheet(Worksheet worksheet, String ssoId);

    Worksheet findById(Integer worksheetId);

    Worksheet findByUserId(Integer userId);

    void updateWorksheet(Worksheet worksheet);
}
