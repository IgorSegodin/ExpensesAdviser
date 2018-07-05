package org.isegodin.expenses.adviser.telegram.bot.data.request;

import lombok.Builder;
import lombok.Data;

/**
 * https://core.telegram.org/bots/api#sendmessage
 *
 * @author isegodin
 */
@Data
@Builder
public class MessageRequest {

    /**
     * Unique identifier for the target chat or username of the target channel (in the format @channelusername)
     */
    Long chat_id;

    String text;

    /**
     * Send Markdown or HTML, if you want Telegram apps to show bold, italic, fixed-width text or inline URLs in your bot's message.
     */
    @Builder.Default
    String parse_mode = "HTML";

    /**
     * Disables link previews for links in this message
     */
    Boolean disable_web_page_preview;

    /**
     * Sends the message silently. Users will receive a notification with no sound.
     */
    Boolean disable_notification;

    /**
     * If the message is a reply, ID of the original message
     */
    Long reply_to_message_id;
}
