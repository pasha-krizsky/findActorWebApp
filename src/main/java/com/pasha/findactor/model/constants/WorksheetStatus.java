package com.pasha.findactor.model.constants;

import lombok.Getter;

/**
 * This enumeration contains all possible worksheet statuses.
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
public enum WorksheetStatus {
    REVIEWED("R"),
    CASTING("C"),
    DECLINED("D"),
    OFFER("O");

    @Getter
    private final String status;

    WorksheetStatus(String status) {
        this.status = status;
    }
}
