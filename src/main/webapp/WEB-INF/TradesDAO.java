import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TradesDAO {
    private static final String URL = "jdbc:mysql://localhoast:3306/stock_trading_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Qryg4yu8mf";

    // Establish a connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to add a trade
    public void addTrade(int userId, int stockId, String tradeType, int quantity, double priceAtTrade) {
        String sql = "INSERT INTO Trades (user_id, stock_id, trade_type, quantity, price_at_trade) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, stockId);
            stmt.setString(3, tradeType);
            stmt.setInt(4, quantity);
            stmt.setDouble(5, priceAtTrade);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get a trade by ID
    public Trade getTradeById(int tradeId) {
        String sql = "SELECT * FROM Trades WHERE trade_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tradeId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Trade(
                    rs.getInt("trade_id"),
                    rs.getInt("user_id"),
                    rs.getInt("stock_id"),
                    rs.getString("trade_type"),
                    rs.getInt("quantity"),
                    rs.getBigDecimal("price_at_trade"),
                    rs.getTimestamp("trade_timestamp")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to delete a trade
    public void deleteTrade(int tradeId) {
        String sql = "DELETE FROM Trades WHERE trade_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, tradeId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get all trades
    public List<Trade> getAllTrades() {
        List<Trade> trades = new ArrayList<>();
        String sql = "SELECT * FROM Trades";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                trades.add(new Trade(
                    rs.getInt("trade_id"),
                    rs.getInt("user_id"),
                    rs.getInt("stock_id"),
                    rs.getString("trade_type"),
                    rs.getInt("quantity"),
                    rs.getBigDecimal("price_at_trade"),
                    rs.getTimestamp("trade_timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trades;
    }

    // Method to get all trades by a specific user
    public List<Trade> getTradesByUserId(int userId) {
        List<Trade> trades = new ArrayList<>();
        String sql = "SELECT * FROM Trades WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                trades.add(new Trade(
                    rs.getInt("trade_id"),
                    rs.getInt("user_id"),
                    rs.getInt("stock_id"),
                    rs.getString("trade_type"),
                    rs.getInt("quantity"),
                    rs.getBigDecimal("price_at_trade"),
                    rs.getTimestamp("trade_timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return trades;
    }
}

// Trade model class to represent a trade object
class Trade {
    private int tradeId;
    private int userId;
    private int stockId;
    private String tradeType;
    private int quantity;
    private BigDecimal priceAtTrade;
    private Timestamp tradeTimestamp;

    public Trade(int tradeId, int userId, int stockId, String tradeType, int quantity, BigDecimal priceAtTrade, Timestamp tradeTimestamp) {
        this.tradeId = tradeId;
        this.userId = userId;
        this.stockId = stockId;
        this.tradeType = tradeType;
        this.quantity = quantity;
        this.priceAtTrade = priceAtTrade;
        this.tradeTimestamp = tradeTimestamp;
    }

    // Getters and setters for each field
    public int getTradeId() { return tradeId; }
    public int getUserId() { return userId; }
    public int getStockId() { return stockId; }
    public String getTradeType() { return tradeType; }
    public int getQuantity() { return quantity; }
    public BigDecimal getPriceAtTrade() { return priceAtTrade; }
    public Timestamp getTradeTimestamp() { return tradeTimestamp; }

    @Override
    public String toString() {
        return "Trade [tradeId=" + tradeId + ", userId=" + userId + ", stockId=" + stockId +
               ", tradeType=" + tradeType + ", quantity=" + quantity + ", priceAtTrade=" + priceAtTrade +
               ", tradeTimestamp=" + tradeTimestamp + "]";
    }
}
