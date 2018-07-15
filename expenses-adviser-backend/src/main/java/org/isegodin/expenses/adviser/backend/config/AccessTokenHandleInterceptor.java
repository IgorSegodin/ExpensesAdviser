package org.isegodin.expenses.adviser.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;
import java.util.UUID;

/**
 * @author isegodin
 */
@Component
public class AccessTokenHandleInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(AccessTokenHandleInterceptor.class);

    private static final String TOKEN_HEADER = "access-token";

    private String accessToken;

    @Autowired
    public void setAccessToken(@Value("${expenses-adviser.backend.accessToken}") String accessToken) {
        if (accessToken == null || accessToken.trim().isEmpty()) {
            accessToken = Base64.getEncoder().encodeToString(UUID.randomUUID().toString().getBytes());
            logger.info("New access token: {}", accessToken);
        }
        this.accessToken = accessToken;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader(TOKEN_HEADER);
        if (token == null || !accessToken.equals(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        return true;
    }
}
