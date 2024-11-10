import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotificationsDAO {
    private static final String URL = "jdbc:mysql://localhoast:3306/stock_trading_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Qryg4yu8mf";

    // Method to establish connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to add a new notification
    public void addNotification(int userId, String message) {
        String sql = "INSERT INTO Notifications (user_id, message) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setString(2, message);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve unread notifications for a specific user
    public List<Notification> getUnreadNotificationsByUserId(int userId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE user_id = ? AND is_read = FALSE ORDER BY created_at DESC";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notifications.add(new Notification(
                    rs.getInt("notification_id"),
                    rs.getInt("user_id"),
                    rs.getString("message"),
                    rs.getBoolean("is_read"),
                    rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    // Method to retrieve all notifications for a specific user
    public List<Notification> getAllNotificationsByUserId(int userId) {
        List<Notification> notifications = new ArrayList<>();
        String sql = "SELECT * FROM Notifications WHERE user_id = ? ORDER BY created_at DESC";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                notifications.add(new Notification(
                    rs.getInt("notification_id"),
                    rs.getInt("user_id"),
                    rs.getString("message"),
                    rs.getBoolean("is_read"),
                    rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return notifications;
    }

    // Method to mark a notification as read
    public void markNotificationAsRead(int notificationId) {
        String sql = "UPDATE Notifications SET is_read = TRUE WHERE notification_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notificationId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a notification
    public void deleteNotification(int notificationId) {
        String sql = "DELETE FROM Notifications WHERE notification_id = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, notificationId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// Notification model class to represent a notification
class Notification {
    private int notificationId;
    private int userId;
    private String message;
    private boolean isRead;
    private Timestamp createdAt;

    public Notification(int notificationId, int userId, String message, boolean isRead, Timestamp createdAt) {
        this.notificationId = notificationId;
        this.userId = userId;
        this.message = message;
        this.isRead = isRead;
        this.createdAt = createdAt;
    }

    // Getters and setters for each field
    public int getNotificationId() { return notificationId; }
    public int getUserId() { return userId; }
    public String getMessage() { return message; }
    public boolean isRead() { return isRead; }
    public Timestamp getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Notification [notificationId=" + notificationId + ", userId=" + userId + 
               ", message=" + message + ", isRead=" + isRead + ", createdAt=" + createdAt + "]";
    }
}
