package me.verwelius.minecraft2fa.database;

import java.util.Map;

public interface IDatabase {

    void reload();
    void addPlayer(String name, long id);
    long getIdOfPlayer(String name);
    Map<String, Long> getAll();



}
