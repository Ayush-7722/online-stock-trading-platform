import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TradeActivityLogDAO {
    private static final String URL = "jdbc:mysql://localhoast:3306/stock_trading_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Qryg4yu8mf";

    // Method to establish connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to add a trade activity log entry
    public void addTradeActivityLog(int userId, String actionType, String actionDetails) {
        String sql = "INSERT INTO TradeActivityLog (user_id, action_type, action_details) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, actionType);
            stmt.setString(3, actionDetails);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve all trade activity logs
    public List<TradeActivityLog> getAllTradeActivityLogs() {
        List<TradeActivityLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM TradeActivityLog ORDER BY action_timestamp DESC";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                logs.add(new TradeActivityLog(
                    rs.getInt("log_id"),
                    rs.getInt("user_id"),
                    rs.getString("action_type"),
                    rs.getString("action_details"),
                    rs.getTimestamp("action_timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    // Method to retrieve trade activity logs for a specific user
    public List<TradeActivityLog> getTradeActivityLogsByUserId(int userId) {
        List<TradeActivityLog> logs = new ArrayList<>();
        String sql = "SELECT * FROM TradeActivityLog WHERE user_id = ? ORDER BY action_timestamp DESC";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                logs.add(new TradeActivityLog(
                    rs.getInt("log_id"),
                    rs.getInt("user_id"),
                    rs.getString("action_type"),
                    rs.getString("action_details"),
                    rs.getTimestamp("action_timestamp")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return logs;
    }

    // Method to delete a trade activity log by log_id
    public void deleteTradeActivityLog(int logId) {
        String sql = "DELETE FROM TradeActivityLog WHERE log_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, logId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// TradeActivityLog model class to represent a trade activity log entry
class TradeActivityLog {
    private int logId;
    private int userId;
    private String actionType;
    private String actionDetails;
    private Timestamp actionTimestamp;

    public TradeActivityLog(int logId, int userId, String actionType, String actionDetails, Timestamp actionTimestamp) {
        this.logId = logId;
        this.userId = userId;
        this.actionType = actionType;
        this.actionDetails = actionDetails;
        this.actionTimestamp = actionTimestamp;
    }

    // Getters and setters for each field
    public int getLogId() { return logId; }
    public int getUserId() { return userId; }
    public String getActionType() { return actionType; }
    public String getActionDetails() { return actionDetails; }
    public Timestamp getActionTimestamp() { return actionTimestamp; }

    @Override
    public String toString() {
        return "TradeActivityLog [logId=" + logId + ", userId=" + userId + 
               ", actionType=" + actionType + ", actionDetails=" + actionDetails + 
               ", actionTimestamp=" + actionTimestamp + "]";
    }
}
