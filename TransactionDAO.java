package com.user.dao;

import com.user.model.Transaction;
import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    private String jdbcURL = "jdbc:mysql://localhost:3306/stock_trading_db";
    private String jdbcUserName = "root";
    private String jdbcPassword = "Qryg4yu8mf";

    private static final String INSERT_TRANSACTION_SQL = "INSERT INTO Transactions (order_id, transaction_amount) VALUES (?, ?);";
    private static final String SELECT_TRANSACTIONS_BY_ORDER_ID = "SELECT * FROM Transactions WHERE order_id = ?;";

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

    public void insertTransaction(Transaction transaction) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_TRANSACTION_SQL)) {
            preparedStatement.setInt(1, transaction.getOrderId());
            preparedStatement.setBigDecimal(2, transaction.getTransactionAmount());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Transaction> selectTransactionsByOrderId(int orderId) {
        List<Transaction> transactions = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_TRANSACTIONS_BY_ORDER_ID)) {
            preparedStatement.setInt(1, orderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                BigDecimal transactionAmount = resultSet.getBigDecimal("transaction_amount");
                Timestamp transactionDate = resultSet.getTimestamp("transaction_date");
                transactions.add(new Transaction(id, orderId, transactionAmount, transactionDate));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}
