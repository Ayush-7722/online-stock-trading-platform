import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MarketUpdatesDAO {
    private static final String URL = "jdbc:mysql://localhoast:3306/stock_trading_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Qryg4yu8mf";

    // Method to establish connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to add a market update for a stock
    public void addMarketUpdate(int stockId, String updateText) {
        String sql = "INSERT INTO MarketUpdates (stock_id, update_text) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, stockId);
            stmt.setString(2, updateText);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get a market update by update ID
    public MarketUpdate getMarketUpdateById(int updateId) {
        String sql = "SELECT * FROM MarketUpdates WHERE update_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, updateId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new MarketUpdate(
                    rs.getInt("update_id"),
                    rs.getInt("stock_id"),
                    rs.getString("update_text"),
                    rs.getTimestamp("update_timestamp")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get all market updates for a stock
    public List<MarketUpdate> getMarketUpdatesByStockId(int stockId) {
        List<MarketUpdate> updates = new ArrayList<>();
        String sql = "SELECT * FROM MarketUpdates WHERE stock_id = ? ORDER BY update_timestamp DESC";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, stockId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                updates.add(new MarketUpdate(
                    rs.getInt("update_id"),
                    rs.getInt("stock_id"),
                    rs.getString("update_text"),
                    rs.getTimestamp("update_timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updates;
    }

    // Method to get all market updates (for all stocks)
    public List<MarketUpdate> getAllMarketUpdates() {
        List<MarketUpdate> updates = new ArrayList<>();
        String sql = "SELECT * FROM MarketUpdates ORDER BY update_timestamp DESC";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                updates.add(new MarketUpdate(
                    rs.getInt("update_id"),
                    rs.getInt("stock_id"),
                    rs.getString("update_text"),
                    rs.getTimestamp("update_timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return updates;
    }

    // Method to delete a market update by ID
    public void deleteMarketUpdate(int updateId) {
        String sql = "DELETE FROM MarketUpdates WHERE update_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, updateId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// MarketUpdate model class to represent a market update
class MarketUpdate {
    private int updateId;
    private int stockId;
    private String updateText;
    private Timestamp updateTimestamp;

    public MarketUpdate(int updateId, int stockId, String updateText, Timestamp updateTimestamp) {
        this.updateId = updateId;
        this.stockId = stockId;
        this.updateText = updateText;
        this.updateTimestamp = updateTimestamp;
    }

    // Getters and setters for each field
    public int getUpdateId() { return updateId; }
    public int getStockId() { return stockId; }
    public String getUpdateText() { return updateText; }
    public Timestamp getUpdateTimestamp() { return updateTimestamp; }

    @Override
    public String toString() {
        return "MarketUpdate [updateId=" + updateId + ", stockId=" + stockId + ", updateText=" + updateText + 
               ", updateTimestamp=" + updateTimestamp + "]";
    }
}
