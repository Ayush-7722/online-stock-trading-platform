import java.sql.*;
import java.util.Optional;

public class UserPreferencesDAO {
    private static final String URL = "jdbc:mysql://localhoast:3306/stock_trading_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Qryg4yu8mf";

    // Method to establish connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to add user preferences to the table
    public void addUserPreferences(int userId, boolean receiveUpdates, Double alertThreshold) {
        String sql = "INSERT INTO UserPreferences (user_id, receive_updates, alert_threshold) VALUES (?, ?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setBoolean(2, receiveUpdates);
            if (alertThreshold != null) {
                stmt.setDouble(3, alertThreshold);
            } else {
                stmt.setNull(3, Types.DOUBLE);  // Null for optional alert threshold
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve user preferences by userId
    public Optional<UserPreferences> getUserPreferencesByUserId(int userId) {
        String sql = "SELECT * FROM UserPreferences WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                UserPreferences preferences = new UserPreferences(
                    rs.getInt("preference_id"),
                    rs.getInt("user_id"),
                    rs.getBoolean("receive_updates"),
                    rs.getDouble("alert_threshold")
                );
                return Optional.of(preferences);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();  // Return an empty Optional if no preferences found
    }

    // Method to update user preferences
    public void updateUserPreferences(int userId, Boolean receiveUpdates, Double alertThreshold) {
        String sql = "UPDATE UserPreferences SET receive_updates = ?, alert_threshold = ? WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setBoolean(1, receiveUpdates);
            if (alertThreshold != null) {
                stmt.setDouble(2, alertThreshold);
            } else {
                stmt.setNull(2, Types.DOUBLE);  // Null for optional alert threshold
            }
            stmt.setInt(3, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete user preferences
    public void deleteUserPreferences(int userId) {
        String sql = "DELETE FROM UserPreferences WHERE user_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// UserPreferences model class to represent user preferences
class UserPreferences {
    private int preferenceId;
    private int userId;
    private boolean receiveUpdates;
    private Double alertThreshold;

    public UserPreferences(int preferenceId, int userId, boolean receiveUpdates, Double alertThreshold) {
        this.preferenceId = preferenceId;
        this.userId = userId;
        this.receiveUpdates = receiveUpdates;
        this.alertThreshold = alertThreshold;
    }

    // Getters and setters for each field
    public int getPreferenceId() { return preferenceId; }
    public int getUserId() { return userId; }
    public boolean isReceiveUpdates() { return receiveUpdates; }
    public Double getAlertThreshold() { return alertThreshold; }

    @Override
    public String toString() {
        return "UserPreferences [preferenceId=" + preferenceId + ", userId=" + userId + 
               ", receiveUpdates=" + receiveUpdates + ", alertThreshold=" + alertThreshold + "]";
    }
}
