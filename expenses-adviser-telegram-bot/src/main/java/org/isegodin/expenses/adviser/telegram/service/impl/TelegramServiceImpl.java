package org.isegodin.expenses.adviser.telegram.service.impl;

import org.isegodin.expenses.adviser.telegram.data.dto.MessageDto;
import org.isegodin.expenses.adviser.telegram.data.request.MessageRequest;
import org.isegodin.expenses.adviser.telegram.data.request.UpdateRequest;
import org.isegodin.expenses.adviser.telegram.data.response.UpdateResponse;
import org.isegodin.expenses.adviser.telegram.service.TelegramService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

/**
 * @author isegodin
 */
public class TelegramServiceImpl implements TelegramService {

    private static final String API_URL = "https://api.telegram.org";

    private final String token;
    private final RestTemplate restTemplate = new RestTemplate();

    public TelegramServiceImpl(String token) {
        this.token = token;
    }

    @Override
    public UpdateResponse getUpdates(UpdateRequest request) {
        return sendApiRequest("getUpdates", request, UpdateResponse.class);
    }

    @Override
    public MessageDto sendMessage(MessageRequest request) {
        return sendApiRequest("sendMessage", request, MessageDto.class);
    }

    private <REQUEST, RESPONSE> RESPONSE sendApiRequest(String method, REQUEST request, Class<RESPONSE> responseType) {
        try {
            URI uri = UriComponentsBuilder.fromUriString(API_URL).path("/bot{token}/{method}")
                    .buildAndExpand(token, method).toUri();
            ResponseEntity<RESPONSE> response = restTemplate.exchange(uri, HttpMethod.POST, new HttpEntity<>(request), responseType);
            return response.getBody();
        } catch (HttpClientErrorException e) {
            System.err.println(e.getResponseBodyAsString());
            throw e;
        } catch (RestClientException e) {
            throw e;
        }
    }
}
