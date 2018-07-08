package org.isegodin.expenses.adviser.telegram.listener;

import org.isegodin.expenses.adviser.backend.api.PaymentServiceApi;
import org.isegodin.expenses.adviser.backend.api.UserServiceApi;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentRequest;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentResponse;
import org.isegodin.expenses.adviser.backend.api.dto.UserRequest;
import org.isegodin.expenses.adviser.backend.api.dto.UserResponse;
import org.isegodin.expenses.adviser.telegram.bot.data.dto.UpdateDto;
import org.isegodin.expenses.adviser.telegram.bot.data.dto.UserDto;
import org.isegodin.expenses.adviser.telegram.bot.data.request.UpdateRequest;
import org.isegodin.expenses.adviser.telegram.bot.data.response.UpdateResponse;
import org.isegodin.expenses.adviser.telegram.bot.service.TelegramService;
import org.isegodin.expenses.adviser.telegram.data.dto.UpdateEventDto;
import org.isegodin.expenses.adviser.telegram.service.JsonService;
import org.isegodin.expenses.adviser.telegram.service.UpdateEventService;
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

    private final UpdateEventService updateEventService;

    private final JsonService jsonService;

    @Autowired
    private UserServiceApi userServiceApi;

    @Autowired
    private PaymentServiceApi paymentServiceApi;

    @Autowired
    public TelegramUpdateListener(TelegramService telegramService, UpdateEventService updateEventService, JsonService jsonService) {
        this.telegramService = telegramService;
        this.updateEventService = updateEventService;
        this.jsonService = jsonService;
    }

    @PostConstruct
    public void postConstruct() {
        Timer timer = new Timer();
        timer.schedule(new UpdatePollingTask(), 0, TimeUnit.SECONDS.toMillis(2));
    }

    private Long extractUserId(UpdateDto update) {
        if (update.getMessage() == null) {
            return null;
        }
        UserDto user = update.getMessage().getFrom();

        return user != null ? user.getId() : null;
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

            for (UpdateDto update : updates) {
                try {
                    if (updateEventService.isExists(update.getUpdate_id())) {
                        logger.info("Update was already saved: {}", update);
                        continue;
                    }
                    UpdateEventDto event = new UpdateEventDto();
                    event.setId(update.getUpdate_id());
                    event.setRawUpdate(jsonService.toJson(update));
                    event.setTelegramUserId(extractUserId(update));

                    updateEventService.save(event);
                    logger.info("Update saved: {}", update);

                    UserDto user = update.getMessage().getFrom();

                    UserRequest userRequest = new UserRequest();
                    userRequest.setFirstName(user.getFirst_name());
                    userRequest.setLastName(user.getLast_name());
                    UserResponse userResponse = userServiceApi.createUser(userRequest);
                    logger.info("User created: {}", userResponse);
                    PaymentRequest paymentRequest = new PaymentRequest();
                    paymentRequest.setValue(100);
                    paymentRequest.setUserId(userResponse.getId());
                    paymentServiceApi.createPayment(paymentRequest);

                    List<PaymentResponse> paymentResponses = paymentServiceApi.listUserPayments(userResponse.getId());

                    logger.info("Payments created: {}", paymentResponses.size());
                } catch (Exception e) {
                    logger.warn("Can not process update: " + update, e);
                }
            }
        }
    }
}
