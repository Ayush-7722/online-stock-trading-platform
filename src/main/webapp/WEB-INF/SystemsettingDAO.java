import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class SystemSettingsDAO {
    private static final String URL = "jdbc:mysql://localhoast:3306/stock_trading_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Qryg4yu8mf";

    // Method to establish connection to the database
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // Method to add a new system setting
    public void addSystemSetting(String settingName, String settingValue) {
        String sql = "INSERT INTO SystemSettings (setting_name, setting_value) VALUES (?, ?)";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, settingName);
            stmt.setString(2, settingValue);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to retrieve the value of a specific system setting by setting_name
    public String getSystemSettingValue(String settingName) {
        String sql = "SELECT setting_value FROM SystemSettings WHERE setting_name = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, settingName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("setting_value");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if the setting is not found
    }

    // Method to retrieve all system settings as a map of setting_name -> setting_value
    public Map<String, String> getAllSystemSettings() {
        Map<String, String> settings = new HashMap<>();
        String sql = "SELECT * FROM SystemSettings";
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                settings.put(rs.getString("setting_name"), rs.getString("setting_value"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return settings;
    }

    // Method to update the value of an existing system setting
    public void updateSystemSetting(String settingName, String settingValue) {
        String sql = "UPDATE SystemSettings SET setting_value = ?, last_updated = CURRENT_TIMESTAMP WHERE setting_name = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, settingValue);
            stmt.setString(2, settingName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete a system setting by setting_name
    public void deleteSystemSetting(String settingName) {
        String sql = "DELETE FROM SystemSettings WHERE setting_name = ?";
        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, settingName);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

// SystemSetting model class to represent a system setting
class SystemSetting {
    private int settingId;
    private String settingName;
    private String settingValue;
    private Timestamp lastUpdated;

    public SystemSetting(int settingId, String settingName, String settingValue, Timestamp lastUpdated) {
        this.settingId = settingId;
        this.settingName = settingName;
        this.settingValue = settingValue;
        this.lastUpdated = lastUpdated;
    }

    // Getters and setters for each field
    public int getSettingId() { return settingId; }
    public String getSettingName() { return settingName; }
    public String getSettingValue() { return settingValue; }
    public Timestamp getLastUpdated() { return lastUpdated; }

    @Override
    public String toString() {
        return "SystemSetting [settingId=" + settingId + ", settingName=" + settingName + 
               ", settingValue=" + settingValue + ", lastUpdated=" + lastUpdated + "]";
    }
}
