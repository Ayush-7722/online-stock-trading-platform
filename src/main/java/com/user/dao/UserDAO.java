package com.user.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.user.model.User;

public class UserDAO {

	private String jdbcURL = "jdbc:mysql://localhoast:3306/stock_trading_db";
	private String jdbcUserName = "root";
	private String jdbcPassword = "Qryg4yu8mf";

	private static final String INSERT_USER_SQL = "INSERT INTO Users" + "(name,email,password_hash,role) VALUES "
			+ "(?,?,?,?);";
	private static final String SELECT_USER_BY_ID = "SELECT * FROM USERS WHERE ID=?;";
	private static final String SELECT_ALL_USERS = "SELECT * FROM USERS;";
	private static final String DELETE_USERS_SQL = "DELETE FROM USER WHERE ID=?;";
	private static final String UPDATE_USERS_SQL = "UPDATE USER SET NAME=?, EMAIL=?, PASSWORD=?, role=? WHERE ID=?;";

	public UserDAO() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Connection getConnection()

	{
		Connection Connection = null;

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection = DriverManager.getConnection(jdbcURL, jdbcUserName, jdbcPassword);
		}

		catch (SQLException | ClassNotFoundException e)

		{
			e.printStackTrace();

		}

		catch (Exception e)

		{
			e.printStackTrace();

		}
		return Connection;

	}

	public void insertUser(User user) {
		UserDAO dao = new UserDAO();

		try (Connection connection = dao.getConnection())

		{
			PreparedStatement preparedStatement = connection.prepareStatement(INSERT_USER_SQL);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getPasswordHash());
			preparedStatement.setString(4, user.getRole());
		}

		catch (Exception e) {
			e.printStackTrace();
		}

	}

	public User selectUser(int id) {
		User user = new User();
		UserDAO dao = new UserDAO();

		try (Connection connection = dao.getConnection())

		{
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_ID);
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			while(resultSet.next()) {
				user.setUserId(id);
				user.setName(resultSet.getString("name"));
				user.setEmail(resultSet.getString("email"));
				user.setRole(resultSet.getString("role"));
				user.setPasswordHash(resultSet.getString("password_hash"));
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public List<User> selectAllUsers(){
		List<User> users = new ArrayList<User>();
		UserDAO dao = new UserDAO();

		try (Connection connection = dao.getConnection())

		{
			PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_USERS);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			
			while(resultSet.next()) {
				int id = resultSet.getInt("id");
				String name = resultSet.getString("name");
				String email = resultSet.getString("email");
				String role = resultSet.getString("role");
				String passwordHash = resultSet.getString("passwordHash");
				
				users.add(new User(id, name, email, passwordHash, role));
			}
		}

		catch (Exception e) {
			e.printStackTrace();
		}
		return users;
		
	}
	
	
	public boolean deleteUser(int id)
	{
		boolean status=false;
		UserDAO dao=new UserDAO();
		try(Connection connection=dao.getConnection())
		{
			PreparedStatement preparedStatement=connection.prepareStatement(DELETE_USERS_SQL);
			preparedStatement.setInt(1, id);
			
			status=preparedStatement.execute();
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return status;
	}
	
	
	public boolean updateUser(User user)
	{
		boolean status=false;
		UserDAO dao=new UserDAO();
		try(Connection connection=dao.getConnection())
		{
			PreparedStatement preparedStatement=connection.prepareStatement(UPDATE_USERS_SQL);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getRole());
			preparedStatement.setString(4, user.getPasswordHash());
			
			
			
			status=preparedStatement.executeUpdate()>0;
			
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		
		return status;
	}
	
	
	public static void main(String args[])
	   {
		   UserDAO dao=new UserDAO();
		   if(dao.getConnection()!=null)
		   {
			   System.out.println("Successfully connected to the database!!");
		   }
		   else
		   {   
			   System.out.println("Problem in database connection!!");
		   }
		   
		   //Data insertion
		   User user=new User(0, "test","test@abc.com","admin@123","admin");
		   
		   //dao.insertUser(user);
		   
		   //select data by user id
		   User user1=dao.selectUser(1);
		   System.out.println(user1);
		   
		   //select all users data
		   List<User> users=dao.selectAllUsers();
		   
		  for(User u:users)
		  {
			  System.out.println(u);
		  }
		  
		  //Update user
		  
		  user=new User(0, "test1","test1@abc.com","trader","test@123");
		  Boolean status=dao.updateUser(user);
		  System.out.println(status);
			   
	   }

}
