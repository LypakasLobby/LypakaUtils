package com.lypaka.lypakautils.SQL;

import com.lypaka.lypakautils.LypakaUtils;

import java.sql.*;
import java.util.*;

public class DatabaseManager {
    private final ConnectionManager mysqlConnectionManager;

    public DatabaseManager(ConnectionManager connectionManager) {
        this.mysqlConnectionManager = connectionManager;
    }

    public void saveOrUpdateData(String tableName, Map<String, Object> data, String condition) {
        if (dataExists(tableName, condition)) {
            updateData(tableName, data, condition);
        } else {
            saveData(tableName, data);
        }
    }

    public void saveData(String tableName, Map<String, Object> data) {
        try (Connection connection = mysqlConnectionManager.getDataSource().getConnection()) {
            // Build the SQL INSERT statement dynamically based on the provided data
            StringBuilder sqlBuilder = new StringBuilder("INSERT INTO ")
                    .append(tableName)
                    .append(" (");

            StringBuilder placeholders = new StringBuilder();
            for (String fieldName : data.keySet()) {
                sqlBuilder.append(fieldName).append(",");
                placeholders.append("?,");
            }
            sqlBuilder.deleteCharAt(sqlBuilder.length() - 1); // Remove the trailing comma
            placeholders.deleteCharAt(placeholders.length() - 1); // Remove the trailing comma

            sqlBuilder.append(") VALUES (").append(placeholders).append(")");

            // Prepare and execute the SQL statement with parameters
            try (PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString())) {
                int parameterIndex = 1;
                for (Object value : data.values()) {
                    statement.setObject(parameterIndex++, value);
                }
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> loadData(String tableName, String[] fields, String condition) {
        Map<String, Object> data = new HashMap<>();
        String selectQuery = "SELECT " + String.join(", ", fields) + " FROM " + tableName + " WHERE " + condition;

        LypakaUtils.logger.info("Executing query: {}", selectQuery); // Log query

        try (Connection connection = mysqlConnectionManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) { // Iterate through all rows
                for (String field : fields) {
                    Object value = resultSet.getObject(field);
                    data.put(field, value);
                }
                LypakaUtils.logger.debug("Row retrieved: {}", data); // Log retrieved row (optional)
            }

        } catch (SQLException e) {
            LypakaUtils.logger.error("Error executing query: {}", e.getMessage());
            throw new RuntimeException("Failed to load data: " + e.getMessage(), e);
        }

        return data;
    }

    public Map<String, Object> loadData(String tableName, String[] fields) {
        Map<String, Object> data = new HashMap<>();
        String selectQuery = "SELECT " + String.join(", ", fields) + " FROM " + tableName;

        LypakaUtils.logger.info("Executing query: {}", selectQuery); // Log query

        try (Connection connection = mysqlConnectionManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                for (String field : fields) {
                    Object value = resultSet.getObject(field);
                    data.put(field, value);
                }
                LypakaUtils.logger.debug("Row retrieved: {}", data); // Log retrieved row (optional)
            }

        } catch (SQLException e) {
            LypakaUtils.logger.error("Error executing query: {}", e.getMessage());
            throw new RuntimeException("Failed to load data: " + e.getMessage(), e);
        }

        return data;
    }

    public List<Map<String, Object>> loadDataByRows(String tableName, String[] fields) {
        List<Map<String, Object>> rowDataList = new ArrayList<>();
        String selectQuery = "SELECT " + String.join(", ", fields) + " FROM " + tableName;

        LypakaUtils.logger.info("Executing query: {}", selectQuery); // Log query

        try (Connection connection = mysqlConnectionManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                Map<String, Object> rowData = new HashMap<>();
                for (String field : fields) {
                    Object value = resultSet.getObject(field);
                    rowData.put(field, value);
                }
                rowDataList.add(rowData);
            }

        } catch (SQLException e) {
            LypakaUtils.logger.error("Error executing query: {}", e.getMessage());
            throw new RuntimeException("Failed to load data: " + e.getMessage(), e);
        }

        return rowDataList;
    }


    public void deleteData(String tableName, String condition) {
        String deleteQuery = "DELETE FROM " + tableName + " WHERE " + condition;

        try (Connection connection = mysqlConnectionManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean dataExists(String tableName, String condition) {
        String selectQuery = "SELECT EXISTS(SELECT 1 FROM " + tableName + " WHERE " + condition + ")";

        try (Connection connection = mysqlConnectionManager.getDataSource().getConnection();
             PreparedStatement statement = connection.prepareStatement(selectQuery)) {

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getBoolean(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void createTable(String tableName, String tableDefinition) {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS " + tableName + " (" + tableDefinition + ")";
        try (Connection connection = mysqlConnectionManager.getDataSource().getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableQuery);
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception (e.g., log it, throw it, etc.)
        }
    }

    public void updateData(String tableName, Map<String, Object> newData, String condition) {
        try (Connection connection = mysqlConnectionManager.getDataSource().getConnection()) {
            StringBuilder sqlBuilder = new StringBuilder("UPDATE ").append(tableName).append(" SET ");
            Iterator<Map.Entry<String, Object>> iterator = newData.entrySet().iterator();

            while (iterator.hasNext()) {
                Map.Entry<String, Object> entry = iterator.next();
                sqlBuilder.append(entry.getKey()).append(" = ?");
                if (iterator.hasNext()) {
                    sqlBuilder.append(", ");
                }
            }

            sqlBuilder.append(" WHERE ").append(condition);
            try (PreparedStatement statement = connection.prepareStatement(sqlBuilder.toString())) {
                int parameterIndex = 1;
                for (Object value : newData.values()) {
                    statement.setObject(parameterIndex++, value);
                }

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            LypakaUtils.logger.error("Error executing update query: {}", e.getMessage());
            throw new RuntimeException("Failed to update data: " + e.getMessage(), e);
        }
    }
}
