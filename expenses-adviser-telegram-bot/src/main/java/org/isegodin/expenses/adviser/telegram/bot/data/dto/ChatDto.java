package org.isegodin.expenses.adviser.telegram.bot.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://core.telegram.org/bots/api#chat
 *
 * @author isegodin
 */
@NoArgsConstructor
@Data
public class ChatDto {

    Long id;

    /**
     * Type of chat, can be either “private”, “group”, “supergroup” or “channel”
     */
    String type;
    String title;
    String username;
    String first_name;
    String last_name;

}
