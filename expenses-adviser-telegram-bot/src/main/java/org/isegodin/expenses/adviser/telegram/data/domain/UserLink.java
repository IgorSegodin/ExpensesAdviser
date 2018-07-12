package org.isegodin.expenses.adviser.telegram.data.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

/**
 * @author isegodin
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "user_link")
public class UserLink {

    @Id
    @Column(name = "telegram_user_id")
    Long telegramUserId;

    @Column(name = "backend_user_id")
    UUID backendUserId;

    @Column(name = "first_name")
    String firstName;

    @Column(name = "last_name")
    String lastName;

    @Column(name = "username")
    String username;

    @Column(name = "language_code")
    String languageCode;

}
