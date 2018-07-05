package org.isegodin.expenses.adviser.telegram.bot.service;

import org.isegodin.expenses.adviser.telegram.bot.data.dto.MessageDto;
import org.isegodin.expenses.adviser.telegram.bot.data.request.MessageRequest;
import org.isegodin.expenses.adviser.telegram.bot.data.request.UpdateRequest;
import org.isegodin.expenses.adviser.telegram.bot.data.response.UpdateResponse;

/**
 * @author isegodin
 */
public interface TelegramService {

    UpdateResponse getUpdates(UpdateRequest request);

    MessageDto sendMessage(MessageRequest request);

}
