package org.isegodin.expenses.adviser.telegram;

import org.isegodin.expenses.adviser.telegram.data.dto.MessageDto;
import org.isegodin.expenses.adviser.telegram.data.dto.UpdateDto;
import org.isegodin.expenses.adviser.telegram.data.request.MessageRequest;
import org.isegodin.expenses.adviser.telegram.data.request.UpdateRequest;
import org.isegodin.expenses.adviser.telegram.data.response.UpdateResponse;
import org.isegodin.expenses.adviser.telegram.service.TelegramService;
import org.isegodin.expenses.adviser.telegram.service.impl.TelegramServiceImpl;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

/**
 * @author isegodin
 */
public class App {

    private static TelegramService service;

    public static void main(String[] args) {
        service = new TelegramServiceImpl(System.getProperty("token"));

        Timer timer = new Timer();

        timer.schedule(new UpdatePollingTask(), 0, TimeUnit.SECONDS.toMillis(2));
    }

    private static class UpdatePollingTask extends TimerTask {

        private volatile Long offset;

        @Override
        public void run() {
            UpdateResponse response = service.getUpdates(
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

            boolean exit = false;

            for (UpdateDto update : updates) {
                Long chatId = update.getMessage().getChat().getId();
                String text = update.getMessage().getText();
                if (text.equalsIgnoreCase("/die")) {
                    service.getUpdates(
                            UpdateRequest.builder()
                                    .offset(offset)
                                    .limit(0)
                                    .build()
                    );
                    offset++;
                    cancel();
                    exit = true;
                } else {
                    MessageDto msg = service.sendMessage(MessageRequest.builder().chat_id(chatId).text("Echo: " + text).build());
                    System.out.println("Message: " + text);
                }
            }

            if (exit) {
                System.exit(0);
            }
        }
    }
}
