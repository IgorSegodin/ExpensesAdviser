package org.isegodin.expenses.adviser.telegram.listener;

import org.isegodin.expenses.adviser.telegram.bot.data.dto.UpdateDto;
import org.isegodin.expenses.adviser.telegram.bot.data.request.UpdateRequest;
import org.isegodin.expenses.adviser.telegram.bot.data.response.UpdateResponse;
import org.isegodin.expenses.adviser.telegram.bot.service.TelegramService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author i.segodin
 */
@Component
public class TelegramUpdateListener {

    private static Logger logger = LoggerFactory.getLogger(TelegramUpdateListener.class);

    private final TelegramService telegramService;

    @Autowired
    public TelegramUpdateListener(TelegramService telegramService) {
        this.telegramService = telegramService;
    }

    @PostConstruct
    public void postConstruct() {
        Timer timer = new Timer();
        timer.schedule(new UpdatePollingTask(), 0, TimeUnit.SECONDS.toMillis(2));
    }

    private class UpdatePollingTask extends TimerTask {

        private volatile Long offset;

        @Override
        public void run() {
            UpdateResponse response = telegramService.getUpdates(
                    UpdateRequest.builder()
                            .offset(offset)
                            .limit(10)
                            .build()
            );
            List<UpdateDto> updates = response.getResult();

            if (!updates.isEmpty()) {
                offset = response.getResult().get(updates.size() - 1).getUpdate_id() + 1;
            }

            if (!updates.isEmpty()) {
                System.out.println(response);
            }

//            boolean exit = false;

            for (UpdateDto update : updates) {
                logger.info("Update: {}", update);
                String text = update.getMessage().getText();

                if (text == null || text.isEmpty()) {
                    continue;
                }

//                Long chatId = update.getMessage().getChat().getId();

                // MessageDto msg = service.sendMessage(MessageRequest.builder().chat_id(chatId).text("Echo: " + text).build());
//                    System.out.println("Message: " + text);

//                if (text.equalsIgnoreCase("/die")) {
//                    telegramService.getUpdates(
//                            UpdateRequest.builder()
//                                    .offset(offset)
//                                    .limit(0)
//                                    .build()
//                    );
//                    offset++;
//                    cancel();
//                    exit = true;
//                } else {
////                    MessageDto msg = service.sendMessage(MessageRequest.builder().chat_id(chatId).text("Echo: " + text).build());
//                    System.out.println("Message: " + text);
//                }
            }

//            if (exit) {
//                System.exit(0);
//            }
        }
    }
}
