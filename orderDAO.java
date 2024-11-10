package com.user.dao;

import com.user.model.Order;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/stock_trading_db";
    private String jdbcUserName = "root";
    private String jdbcPassword = "Qryg4yu8mf";

    private static final String INSERT_ORDER_SQL = "INSERT INTO Orders (user_id, stock_id, type, quantity, price) VALUES (?, ?, ?, ?, ?);";
    private static final String SELECT_ORDERS_BY_USER_ID = "SELECT * FROM Orders WHERE user_id = ?;";
    private static final String UPDATE_ORDER_STATUS = "UPDATE Orders SET status = ? WHERE id = ?;";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void insertOrder(Order order) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_ORDER_SQL)) {
            preparedStatement.setInt(1, order.getUserId());
            preparedStatement.setInt(2, order.getStockId());
            preparedStatement.setString(3, order.getType());
            preparedStatement.setInt(4, order.getQuantity());
            preparedStatement.setBigDecimal(5, order.getPrice());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> selectOrdersByUserId(int userId) {
        List<Order> orders = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDERS_BY_USER_ID)) {
            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int stockId = resultSet.getInt("stock_id");
                String type = resultSet.getString("type");
                int quantity = resultSet.getInt("quantity");
                BigDecimal price = resultSet.getBigDecimal("price");
                String status = resultSet.getString("status");
                orders.add(new Order(id, userId, stockId, type, quantity, price, status));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    public boolean updateOrderStatus(int orderId, String status) {
        boolean rowUpdated = false;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_STATUS)) {
            preparedStatement.setString(1, status);
            preparedStatement.setInt(2, orderId);
            rowUpdated = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowUpdated;
    }
}
