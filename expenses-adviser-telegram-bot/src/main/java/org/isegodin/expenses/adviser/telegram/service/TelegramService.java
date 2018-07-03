package org.isegodin.expenses.adviser.telegram.service;

import org.isegodin.expenses.adviser.telegram.data.dto.MessageDto;
import org.isegodin.expenses.adviser.telegram.data.request.MessageRequest;
import org.isegodin.expenses.adviser.telegram.data.request.UpdateRequest;
import org.isegodin.expenses.adviser.telegram.data.response.UpdateResponse;

/**
 * @author isegodin
 */
public interface TelegramService {

    UpdateResponse getUpdates(UpdateRequest request);

    MessageDto sendMessage(MessageRequest request);

}
