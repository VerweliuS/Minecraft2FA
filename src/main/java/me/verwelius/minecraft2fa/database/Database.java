package me.verwelius.minecraft2fa.database;

import org.bukkit.plugin.Plugin;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Database implements IDatabase {

    private String connUrl;
    private String user;
    private String password;
    private final Plugin plugin;

    public Database(Plugin plugin) {
        this.plugin = plugin;
        prepare();

    }

    private void prepare() {
        // Preparing params
        connUrl = plugin.getConfig().getString("db-conn-url", "jdbc:mysql://url");
        user = plugin.getConfig().getString("db-user", "root");
        password = plugin.getConfig().getString("db-pw", "password");

        // Creating table
        String query = "CREATE TABLE IF NOT EXISTS tglinks (\n `name` TEXT,\n `id` BIGINT\n);";
        try (Connection conn = getConnection();/*/*/Statement statement = conn.createStatement()) {
            statement.execute(query);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private Connection getConnection() {
        try {
            return DriverManager.getConnection(connUrl, user, password);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }

    @Override
    public void reload() {
        prepare();
    }

    @Override
    public void addPlayer(String name, long id) {
        try (Connection conn = getConnection();/*/*/Statement statement = conn.createStatement()) {
            String query = getIdOfPlayer(name) == -1
                    ? "INSERT INTO tglinks (`name`, `id`) VALUES ('%s', '%d');"
                    : "UPDATE tglinks WHERE name = %s SET id = %d";
            statement.executeUpdate(String.format(query, name, id));
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public long getIdOfPlayer(String name) {
        long id = -1;
        String query = "SELECT * FROM tglinks WHERE name = %s;";
        try (Connection conn = getConnection();/*/*/Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(String.format(query, name));
            id = resultSet.getLong("id");
            resultSet.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return id;
    }

    @Override
    public Map<String, Long> getAll() {
        Map<String, Long> ids = new HashMap<>();

        String query = "SELECT * FROM tglinks;";
        try (Connection conn = getConnection();/*/*/Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()) ids.put(
                    resultSet.getString("name"),
                    resultSet.getLong("id")
            );
            resultSet.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        return ids;
    }
}
