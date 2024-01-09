package org.caykhe.contentservice.services;

import lombok.RequiredArgsConstructor;
import org.caykhe.contentservice.dtos.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserService {
    private final RestTemplate restTemplate;
    private final JwtService jwtService;
    
    @Value("${user.service.url}")
    private String userServiceUrl;

    public Optional<User> getByUsername(String username) {
        String url = userServiceUrl + "/users/" + username;
        try {
            User user = restTemplate.getForObject(url, User.class);
            return Optional.ofNullable(user);
        } catch (HttpClientErrorException.NotFound e) {
            return Optional.empty();
        }
    }
}
