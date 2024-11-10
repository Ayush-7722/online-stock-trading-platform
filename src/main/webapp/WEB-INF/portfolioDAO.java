import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PortfolioDAO {
    private static final String URL = "jdbc:mysql://localhoast:3306/stock_trading_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Qryg4yu8mf";

    // Method to establish connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to add a stock to the portfolio
    public void addStockToPortfolio(int userId, int stockId, int quantity, double averagePrice) {
        String sql = "INSERT INTO Portfolio (user_id, stock_id, quantity, average_price) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, stockId);
            stmt.setInt(3, quantity);
            stmt.setDouble(4, averagePrice);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to update portfolio when stocks are bought or sold
    public void updatePortfolio(int userId, int stockId, int newQuantity, double newAveragePrice) {
        String sql = "UPDATE Portfolio SET quantity = ?, average_price = ? WHERE user_id = ? AND stock_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newQuantity);
            stmt.setDouble(2, newAveragePrice);
            stmt.setInt(3, userId);
            stmt.setInt(4, stockId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get portfolio details for a user
    public List<Portfolio> getPortfolioByUserId(int userId) {
        List<Portfolio> portfolio = new ArrayList<>();
        String sql = "SELECT * FROM Portfolio WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                portfolio.add(new Portfolio(
                    rs.getInt("portfolio_id"),
                    rs.getInt("user_id"),
                    rs.getInt("stock_id"),
                    rs.getInt("quantity"),
                    rs.getBigDecimal("average_price")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return portfolio;
    }

    // Method to get portfolio details for a specific user and stock
    public Portfolio getPortfolioByUserAndStock(int userId, int stockId) {
        String sql = "SELECT * FROM Portfolio WHERE user_id = ? AND stock_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, stockId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Portfolio(
                    rs.getInt("portfolio_id"),
                    rs.getInt("user_id"),
                    rs.getInt("stock_id"),
                    rs.getInt("quantity"),
                    rs.getBigDecimal("average_price")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to remove a stock from a user's portfolio
    public void removeStockFromPortfolio(int userId, int stockId) {
        String sql = "DELETE FROM Portfolio WHERE user_id = ? AND stock_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, stockId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Portfolio model class to represent a portfolio object
class Portfolio {
    private int portfolioId;
    private int userId;
    private int stockId;
    private int quantity;
    private BigDecimal averagePrice;

    public Portfolio(int portfolioId, int userId, int stockId, int quantity, BigDecimal averagePrice) {
        this.portfolioId = portfolioId;
        this.userId = userId;
        this.stockId = stockId;
        this.quantity = quantity;
        this.averagePrice = averagePrice;
    }

    // Getters and setters for each field
    public int getPortfolioId() { return portfolioId; }
    public int getUserId() { return userId; }
    public int getStockId() { return stockId; }
    public int getQuantity() { return quantity; }
    public BigDecimal getAveragePrice() { return averagePrice; }

    @Override
    public String toString() {
        return "Portfolio [portfolioId=" + portfolioId + ", userId=" + userId + ", stockId=" + stockId +
               ", quantity=" + quantity + ", averagePrice=" + averagePrice + "]";
    }
}
