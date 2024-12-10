package org.example.taskresttemplate.service;


import org.example.taskresttemplate.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RestApiClient {

    private static final String URL = "http://94.198.50.185:7081/api/users";
    private final RestTemplate restTemplate;
    private String sessionId;

    @Autowired
    public RestApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void execute() {

        getAllUsers();

        String part1 = saveUser(new User(3L, "James", "Brown", (byte) 25));

        String part2 = updateUser(new User(3L, "Thomas", "Shelby", (byte) 25));

        String part3 = deleteUser(3L);

        String result = part1 + part2 + part3;
        System.out.println("Итоговый код: " + result);
    }

    private void getAllUsers() {
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, null, String.class);
        HttpHeaders headers = response.getHeaders();
        List<String> cookies = headers.get("Set-Cookie");

        if (cookies != null && !cookies.isEmpty()) {
            sessionId = cookies.get(0).split(";")[0];
            System.out.println("Session ID: " + sessionId);
        }
        System.out.println("Users: " + response.getBody());
    }

    private String saveUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", sessionId);

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.POST, request, String.class);

        System.out.println("Save User Response: " + response.getBody());
        return response.getBody();
    }

    private String updateUser(User user) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.add("Cookie", sessionId);

        HttpEntity<User> request = new HttpEntity<>(user, headers);
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.PUT, request, String.class);

        System.out.println("Update User Response: " + response.getBody());
        return response.getBody();
    }

    private String deleteUser(Long userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cookie", sessionId);

        HttpEntity<Void> request = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(URL + "/" + userId, HttpMethod.DELETE, request, String.class);

        System.out.println("Delete User Response: " + response.getBody());
        return response.getBody();
    }
}
