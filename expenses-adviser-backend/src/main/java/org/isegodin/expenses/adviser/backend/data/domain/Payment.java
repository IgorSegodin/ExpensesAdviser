package org.isegodin.expenses.adviser.backend.data.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author isegodin
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", updatable = false, nullable = false)
    UUID id;

    @Column
    Integer value;

    @Column
    Boolean income;

    @Column
    String description;

    @Column
    OffsetDateTime date;

    @ManyToOne(optional = false)
    @JoinColumn(name = "id_user", nullable = false, updatable = false)
    User user;
}
