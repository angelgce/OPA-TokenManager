package com.pirate.arena.app.dynamoDB;

import com.amazonaws.services.dynamodbv2.document.Item;

import java.util.List;
import java.util.Optional;

public interface IServiceQueries {

    //Users
    Optional<Item> getUserByEmail(String email);

    Optional<List<Item>> getUserByUsername(String username);

    void addUser(Item item);

    void editUser(String email, String key, String value);


}
