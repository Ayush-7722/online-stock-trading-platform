create database stock_trading_db;

use stock_trading_db;

-- Users Table: Stores user information
CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('Admin', 'Trader') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Stocks Table: Stores information on available stocks
CREATE TABLE Stocks (
    stock_id INT PRIMARY KEY AUTO_INCREMENT,
    stock_symbol VARCHAR(10) UNIQUE NOT NULL,
    stock_name VARCHAR(255) NOT NULL,
    current_price DECIMAL(15, 2) NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trades Table: Stores buy/sell transactions
CREATE TABLE Trades (
    trade_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    stock_id INT NOT NULL,
    trade_type ENUM('Buy', 'Sell') NOT NULL,
    quantity INT NOT NULL,
    price_at_trade DECIMAL(15, 2) NOT NULL,
    trade_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (stock_id) REFERENCES Stocks(stock_id)
);

-- Portfolio Table: Tracks portfolio details for each trader
CREATE TABLE Portfolio (
    portfolio_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    stock_id INT NOT NULL,
    quantity INT NOT NULL,
    average_price DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (stock_id) REFERENCES Stocks(stock_id)
);

-- Market Updates Table: Stores real-time updates and news based on user preferences
CREATE TABLE MarketUpdates (
    update_id INT PRIMARY KEY AUTO_INCREMENT,
    stock_id INT,
    update_text TEXT NOT NULL,
    update_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (stock_id) REFERENCES Stocks(stock_id)
);

-- User Preferences Table: Stores notification and market update preferences for traders
CREATE TABLE UserPreferences (
    preference_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    receive_updates BOOLEAN DEFAULT TRUE,
    alert_threshold DECIMAL(15, 2) DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- System Settings Table: Stores system-wide settings managed by admins
CREATE TABLE SystemSettings (
    setting_id INT PRIMARY KEY AUTO_INCREMENT,
    setting_name VARCHAR(255) UNIQUE NOT NULL,
    setting_value VARCHAR(255) NOT NULL,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Trade Activity Log: Tracks all trade activity for admin monitoring
CREATE TABLE TradeActivityLog (
    log_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    action_type ENUM('Trade Executed', 'Trade Canceled') NOT NULL,
    action_details TEXT,
    action_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);

-- Notifications Table: Stores alerts and notifications for traders
CREATE TABLE Notifications (
    notification_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT NOT NULL,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
