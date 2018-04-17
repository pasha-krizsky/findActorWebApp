package com.pasha.findactor.model;

import com.pasha.findactor.model.constants.PersistentLoginFields;
import com.pasha.findactor.model.constants.Tables;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;

/**
 * This class represents {@link Tables#PERSISTENT_LOGINS} table in database and used
 * to store rememberme related stuff.
 *
 * @author Pavel.Krizskiy
 * @since 1.0.0
 */
@Getter
@Setter
@ToString(exclude = "token")
@Entity
@Table(name = Tables.PERSISTENT_LOGINS)
public final class PersistentLogin implements Serializable {

    @Id
    private String series;

    @Column(name = PersistentLoginFields.USERNAME, unique = true, nullable = false)
    private String username;

    @Column(name = PersistentLoginFields.TOKEN, unique = true, nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = PersistentLoginFields.LAST_USED)
    private Date lastUsed;
}
