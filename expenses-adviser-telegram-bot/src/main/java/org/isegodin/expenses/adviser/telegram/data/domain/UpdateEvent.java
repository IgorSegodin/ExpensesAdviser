package org.isegodin.expenses.adviser.telegram.data.domain;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author isegodin
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "update_event")
@TypeDefs({
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
public class UpdateEvent {

    @Id
    Long id;

    @Type(type = "jsonb")
    @Column(name = "raw_update")
    String rawUpdate;
}
