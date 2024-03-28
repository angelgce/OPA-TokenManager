package com.pirate.arena.app.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Item;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServiceQueries implements IServiceQueries {

    private final ServiceDynamoDB serviceDynamoDB;

    @Override
    public Optional<Item> getUserByEmail(String email) {
        return Optional.ofNullable(serviceDynamoDB.getItemByKey("users", "email", email));
    }

    @Override
    public Optional<List<Item>> getUserByUsername(String username) {
        return Optional.ofNullable(serviceDynamoDB.getItemByIndex("users", "username", username, "username-index"));
    }

    @Override
    public void addUser(Item item) {
        serviceDynamoDB.putItem("users", item);
    }

    @Override
    public void editUser(String email, String key, String value) {
        serviceDynamoDB.updateItem("users", "email", email, key, value);
    }

}
