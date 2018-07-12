package org.isegodin.expenses.adviser.telegram.processor.impl;

import org.isegodin.expenses.adviser.backend.api.PaymentServiceApi;
import org.isegodin.expenses.adviser.backend.api.UserServiceApi;
import org.isegodin.expenses.adviser.backend.api.dto.PaymentRequest;
import org.isegodin.expenses.adviser.backend.api.dto.UserRequest;
import org.isegodin.expenses.adviser.backend.api.dto.UserResponse;
import org.isegodin.expenses.adviser.telegram.bot.data.dto.MessageDto;
import org.isegodin.expenses.adviser.telegram.bot.data.dto.UpdateDto;
import org.isegodin.expenses.adviser.telegram.bot.data.dto.UserDto;
import org.isegodin.expenses.adviser.telegram.data.dto.UserLinkDto;
import org.isegodin.expenses.adviser.telegram.processor.UpdateEventProcessor;
import org.isegodin.expenses.adviser.telegram.service.UserLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author isegodin
 */
@Component
public class PaymentUpdateEventProcessor implements UpdateEventProcessor {

    private final Pattern PAYMENT_PATTERN = Pattern.compile("^(?<income>[+-]?)(?<value>\\d+(?:[.,]\\d+)?)\\s+(?<description>.+)$");

    private final UserLinkService userLinkService;
    private final UserServiceApi userServiceApi;
    private final PaymentServiceApi paymentServiceApi;

    @Autowired
    public PaymentUpdateEventProcessor(UserLinkService userLinkService, UserServiceApi userServiceApi, PaymentServiceApi paymentServiceApi) {
        this.userLinkService = userLinkService;
        this.userServiceApi = userServiceApi;
        this.paymentServiceApi = paymentServiceApi;
    }

    @Override
    public boolean canProcess(UpdateDto updateDto) {
        MessageDto message = updateDto.getMessage();
        String text = message.getText();

        return PAYMENT_PATTERN.matcher(text).matches();
    }

    @Override
    public boolean process(UpdateDto updateDto) {
        MessageDto message = updateDto.getMessage();
        UserDto user = message.getFrom();
        String text = message.getText();

        Matcher matcher = PAYMENT_PATTERN.matcher(text);
        if (!matcher.matches()) {
            throw new IllegalStateException("Pattern does not match text, but processor was invoked.");
        }

        Optional<UUID> optionalUserId = userLinkService.getBackendUserIdByTelegramUserId(user.getId());

        UUID userId = optionalUserId.orElseGet(() -> {
            UserRequest request = new UserRequest();
            request.setFirstName(user.getFirst_name());
            request.setLastName(user.getLast_name());
            UserResponse userResponse = userServiceApi.createUser(request);

            UserLinkDto newLink = new UserLinkDto();
            newLink.setTelegramUserId(user.getId());
            newLink.setBackendUserId(userResponse.getId());
            newLink.setFirstName(user.getFirst_name());
            newLink.setLastName(user.getLast_name());
            newLink.setUsername(user.getUsername());
            newLink.setLanguageCode(user.getLanguage_code());

            userLinkService.create(newLink);

            return userResponse.getId();
        });

        Boolean income = Optional.of(matcher.group("income")).orElse("-").equals("+");
        String valueString = matcher.group("value");
        String description = matcher.group("description");

        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setValue(Integer.valueOf(valueString));
        paymentRequest.setDescription(description);
        paymentRequest.setUserId(userId);
        paymentRequest.setIncome(income);

        paymentServiceApi.createPayment(paymentRequest);

        return true;
    }
}
