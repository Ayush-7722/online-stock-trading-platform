import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StocksDAO {
    private static final String URL = "jdbc:mysql://localhoast:3306/stock_trading_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Qryg4yu8mf";

    // Method to establish connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to add a stock
    public void addStock(String stockSymbol, String stockName, double currentPrice) {
        String sql = "INSERT INTO Stocks (stock_symbol, stock_name, current_price) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, stockSymbol);
            stmt.setString(2, stockName);
            stmt.setDouble(3, currentPrice);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get a stock by ID
    public Stock getStockById(int stockId) {
        String sql = "SELECT * FROM Stocks WHERE stock_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, stockId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Stock(
                    rs.getInt("stock_id"),
                    rs.getString("stock_symbol"),
                    rs.getString("stock_name"),
                    rs.getBigDecimal("current_price"),
                    rs.getTimestamp("last_updated")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to update stock price
    public void updateStockPrice(int stockId, double newPrice) {
        String sql = "UPDATE Stocks SET current_price = ?, last_updated = CURRENT_TIMESTAMP WHERE stock_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDouble(1, newPrice);
            stmt.setInt(2, stockId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a stock
    public void deleteStock(int stockId) {
        String sql = "DELETE FROM Stocks WHERE stock_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, stockId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get all stocks
    public List<Stock> getAllStocks() {
        List<Stock> stocks = new ArrayList<>();
        String sql = "SELECT * FROM Stocks";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                stocks.add(new Stock(
                    rs.getInt("stock_id"),
                    rs.getString("stock_symbol"),
                    rs.getString("stock_name"),
                    rs.getBigDecimal("current_price"),
                    rs.getTimestamp("last_updated")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return stocks;
    }
}

// Stock model class to represent a stock object
class Stock {
    private int stockId;
    private String stockSymbol;
    private String stockName;
    private BigDecimal currentPrice;
    private Timestamp lastUpdated;

    public Stock(int stockId, String stockSymbol, String stockName, BigDecimal currentPrice, Timestamp lastUpdated) {
        this.stockId = stockId;
        this.stockSymbol = stockSymbol;
        this.stockName = stockName;
        this.currentPrice = currentPrice;
        this.lastUpdated = lastUpdated;
    }

    // Getters and setters for each field
    public int getStockId() { return stockId; }
    public String getStockSymbol() { return stockSymbol; }
    public String getStockName() { return stockName; }
    public BigDecimal getCurrentPrice() { return currentPrice; }
    public Timestamp getLastUpdated() { return lastUpdated; }

    @Override
    public String toString() {
        return "Stock [stockId=" + stockId + ", stockSymbol=" + stockSymbol + ", stockName=" + stockName +
               ", currentPrice=" + currentPrice + ", lastUpdated=" + lastUpdated + "]";
    }
}
