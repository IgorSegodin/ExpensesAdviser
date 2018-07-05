package org.isegodin.expenses.adviser.telegram.bot.data.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * https://core.telegram.org/bots/api#message
 *
 * @author isegodin
 */
@NoArgsConstructor
@Data
public class MessageDto {

    Long message_id;
    UserDto from;
    Integer date;
    ChatDto chat;
    String text;
}
