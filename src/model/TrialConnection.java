package model;

//STEP 1. Import required packages
import java.sql.*; 
import java.util.Date;

import org.jasypt.util.password.*;

import com.ibm.icu.text.SimpleDateFormat;

public class TrialConnection
{
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
	static final String DB_URL = "jdbc:mysql://localhost/";

	//  Database credentials
	static final String USER = "root";
	static final String PASS = "root";

	private Connection conn = null;
	private Statement stmt = null;
	private BasicPasswordEncryptor bpe = null;

	public TrialConnection()
	{
		initialize();
	}

	private void initialize()
	{
		try
		{
			//STEP 2: Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");

			//STEP 3: Open a connection
			conn = DriverManager.getConnection(DB_URL, USER, PASS);

			stmt = conn.createStatement();
		}
		catch(SQLException se)
		{
			//Handle errors for JDBC
			se.printStackTrace();
		}
		catch(Exception e)
		{
			//Handle errors for Class.forName
			e.printStackTrace();
		}
	}

	public void remove()
	{
		try
		{

		}
		finally 
		{
			try 
			{
				if(stmt != null)
					stmt.close();
			}
			catch(SQLException se2)
			{

			}
			try 
			{
				if(conn != null)
				{
					conn.close();
				}  
			}
			catch(SQLException se)
			{
				se.printStackTrace();
			}
		} 
	}


	public int checkPassword(String username, String password)
	{
		int key = 1;

		try
		{
			String tempPass = "";
			String sql = "SELECT user_password FROM mydatabase.users WHERE user_name = '" + username + "'";
			ResultSet resultSet = stmt.executeQuery(sql);
			while (resultSet.next())
			{
				tempPass = (String) resultSet.getObject("user_password");
				key = 2;
			}

			BasicPasswordEncryptor bpe = new BasicPasswordEncryptor();
			if (bpe.checkPassword(password, tempPass))
			{
				key = 0;
			} 
		}
		catch(Exception e)
		{
			//do nothing
		}
		return key;
	}

	public int checkUserType(String username)
	{
		String type = null;
		try
		{
			String sql = "SELECT user_type FROM mydatabase.user_types WHERE user_name = '" + username + "'";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				type = (String)rs.getObject("user_type");
			}

			if (type.equals("super"))
			{
				return 0;
			}
			else if (type.equals("admin"))
			{
				return 1;
			}
		}catch(Exception e){}

		return 2; //normal
	}

	public boolean createUser(String username, String password, String type)
	{
		try
		{
			String sql = "SELECT user_name FROM mydatabase.users WHERE user_name = '" + username + "'";
			ResultSet resultSet = stmt.executeQuery(sql);

			while (resultSet.next())
			{
				if (username.equals(resultSet.getObject("user_name")))
				{
					return false;
				}
			}

			sql = "INSERT INTO mydatabase.users (user_name, user_password) VALUES ('" + username + "', '" + password + "')";
			stmt.executeUpdate(sql);
			sql = "INSERT INTO mydatabase.user_types VALUES ('" + username + "', '" + type + "')";
			stmt.executeUpdate(sql);

		}catch(Exception e){}

		return true;
	}


	public ResultSet accessActivities()
	{
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM mydatabase.activities";
			rs = stmt.executeQuery(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet accessLogIn()
	{
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM mydatabase.login";
			rs = stmt.executeQuery(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet accessTrustAccounts()
	{
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM mydatabase.trustaccounts";
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return rs;
	}

	public ResultSet accessGeneralFund()
	{
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM mydatabase.generalfund";
			rs = stmt.executeQuery(sql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return rs;

	}

	public ResultSet accessProfessionalServices()
	{
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM mydatabase.professionalservices";
			rs = stmt.executeQuery(sql);
		}catch(Exception e) {}

		return rs;
	}

	public ResultSet accessFacultyProfile()
	{
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM mydatabase.facultyprofile";
			rs = stmt.executeQuery(sql);
		}catch(Exception e) {}

		return rs;
	}

	public ResultSet accessRank()
	{
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM mydatabase.rank;";
			rs = stmt.executeQuery(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return rs;
	}
	
	public ResultSet accessSalary(String position, String rank)
	{
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM mydatabase.salary WHERE position = '" + position + "' AND rank = " + rank;
			rs = stmt.executeQuery(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return rs;
	} 

	public ResultSet accessPosition()
	{
		ResultSet rs = null;
		try
		{
			String sql = "SELECT * FROM mydatabase.position;";
			rs = stmt.executeQuery(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return rs;
	}

	public void createRank(int rank, String desc)
	{
		try
		{
			if (desc == null || desc.length() == 0)
			{
				desc = "null";
			}
			else
			{
				desc = "'" + desc + "'";
			}
			String sql = "INSERT INTO mydatabase.rank VALUES (" + rank + ", " + desc + ");";
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void createActivity(String user, String activity, String table)
	{
		try
		{
			final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd,yyyy HH:mm");
			String sql = "";
			if (table.equals(""))
			{
				sql = "INSERT INTO mydatabase.activities(time, user, activity)  VALUES ('" + dateFormat.format(new Date()) + "', '" + user + "', '" 
						+ activity + "');" ;
			}
			else
			{
				sql = "INSERT INTO mydatabase.activities VALUES ('" + dateFormat.format(new Date()) + "', '" + user + "', '" 
						+ activity + "', '" + table + "');" ;
			}

			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void createLogin(String user)
	{
		try
		{
			final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd,yyyy HH:mm");
			String sql = "INSERT INTO mydatabase.login(user, login) VALUES ('" + user + "', '" + dateFormat.format(new Date()) + "')";
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void logout(String user)
	{
		try
		{
			final SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd,yyyy HH:mm");
			String sql = "UPDATE mydatabase.login SET logout = '" + dateFormat.format(new Date()) + "' WHERE user = '" + user 
					+ "' AND logout IS NULL";
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void createPosition(String name, String desc)
	{
		try
		{
			if (desc == null || desc.length() == 0)
			{
				desc = "null";
			}
			else
			{
				desc = "'" + desc + "'";
			}
			String sql = "INSERT INTO mydatabase.position VALUES ('" + name + "', " + desc + ");";
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void createTrustAccount(String name, Double amount, String benefactor, String usage)
	{
		try
		{
			String sql = "";
			if (benefactor.equals("") && usage.equals(""))
			{
				sql = "INSERT INTO mydatabase.trustaccounts (account_name, amount) VALUES ('" + name + "', " + amount + ")"; 
			}
			else if (benefactor.equals(""))
			{
				sql = "INSERT INTO mydatabase.trustaccounts (account_name, amount, account_usage) VALUES ('" + name + "', " + amount + ", '"
						+ usage + "')";
			}
			else if (usage.equals(""))
			{
				sql = "INSERT INTO mydatabase.trustaccounts (account_name, amount, benefactor) VALUES ('" + name + "', " + amount + ", '"
						+ benefactor + "')";
			}
			else
			{
				sql = "INSERT INTO mydatabase.trustaccounts (account_name, amount, benefactor, account_usage) VALUES ('" + name + "', " + amount + ", '"
						+ benefactor + "', '" + usage + "')";
			}

			stmt.executeUpdate(sql);

		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public void createGeneralFund(String payee, String particulars, Double amount, Double totalAmount)
	{
		try
		{
			String sql = "";

			sql = "INSERT INTO mydatabase.generalfund (payee, particulars, amount, totalAmount) VALUES ('" + payee +"', '" + particulars +
					"' ," + amount + ", " + totalAmount + ");";

			stmt.executeUpdate(sql);

			sql = "SELECT * FROM mydatabase.generalfund WHERE seqNo = 1;";

			ResultSet rs = stmt.executeQuery(sql);

			Double amt = 0.00;
			while (rs.next()){
				amt = rs.getDouble("amount");
				amt = amt + amount;
			}

			sql = "UPDATE mydatabase.generalfund SET amount = " + amt + " WHERE seqNo = 1;";
			stmt.executeUpdate(sql);


		}
		catch(Exception e)
		{
			e.printStackTrace(); 
		}
	}

	public void createProfessionalServices(String name, String position, int rank, Double salary, Double others)
	{
		try
		{
			String sql = "";

			sql = "INSERT INTO mydatabase.professionalservices values ('" + name + "', '" + position + "', " + rank + ", " + salary + ", " + others + ')';
			stmt.executeUpdate(sql);

			sql = "SELECT * FROM mydatabase.professionalservices WHERE faculty = 'TOTAL:'";

			ResultSet rs = stmt.executeQuery(sql);

			Double totalSalary = 0.00;
			Double totalOthers = 0.00;

			while (rs.next())
			{
				totalSalary = rs.getDouble("salary");
				totalOthers = rs.getDouble("others");

				totalSalary = totalSalary + salary;
				totalOthers = totalOthers + others;
			}

			sql = "UPDATE mydatabase.professionalservices SET salary = " + totalSalary + " WHERE faculty = 'TOTAL:'";
			stmt.executeUpdate(sql);

			sql = "UPDATE mydatabase.professionalservices SET others = " + totalOthers + " WHERE faculty = 'TOTAL:'";
			stmt.executeUpdate(sql);
		}catch(Exception e) {}
	}

	public void updateTrustAccount(String name, Double amount)
	{
		try
		{
			String sql = "UPDATE mydatabase.TrustAccounts SET amount = " + amount + " WHERE account_name = '" + name + "'";
			stmt.executeUpdate(sql);
		}catch(Exception e) {}
	}

	public void updateFacultyProfile(String currentName, String newName, String position, int rank, Double salary, String Remarks)
	{
		try
		{
			int seqNo = -1;
			String sql = "SELECT * FROM mydatabase.facultyprofile WHERE faculty = '" + currentName + "'";
			ResultSet rs = stmt.executeQuery(sql);

			while(rs.next())
			{
				seqNo = rs.getInt("seqNo"); 
			}
			if (!newName.equals(""))
			{
				sql = "UPDATE mydatabase.facultyprofile SET faculty = '" + newName + "' WHERE faculty = '" + currentName + "'";
				stmt.executeUpdate(sql);
			}
			else if (!position.equals(""))
			{
				sql = "UPDATE mydatabase.facultyprofile SET position = '" + position + "' WHERE faculty = '" + currentName + "'";
				stmt.executeUpdate(sql);
			}
			else if (rank != -1)
			{
				sql = "UPDATE mydatabase.faculty profile SET rank = " + rank + " WHERE faculty = '" + currentName + "'";
				stmt.executeUpdate(sql);
			}
			else if (salary != -1.0)
			{
				sql = "UPDATE mydatabase.faculty profile SET salary = " + salary + " WHERE faculty = '" + currentName + "'";
				stmt.executeUpdate(sql);
			}
			else if (!Remarks.equals(""))
			{
				sql = "UPDATE mydatabase.facultyprofile SET Remarks = '" + Remarks + "' WHERE faculty = '" + currentName + "'";
				stmt.executeUpdate(sql);
			}
		}catch(Exception e) {}
	}

	public void updateGeneralFund(String name, Double amount, Double totalAmount)
	{

		try
		{
			String sql = "UPDATE mydatabase.generalfund SET amount = " + amount + " WHERE payee = '" + name + "'";
			stmt.executeUpdate(sql);

			sql = "UPDATE mydatabase.generalfund SET totalAmount = " + totalAmount + " WHERE payee = '" + name + "'";
			stmt.executeUpdate(sql);

			sql = "SELECT * FROM mydatabase.generalfund WHERE seqNo != 1";

			ResultSet rs = stmt.executeQuery(sql);

			Double total = 0.00;
			while (rs.next())
			{
				Double temp = rs.getDouble("amount");
				total = total + temp;
			}

			sql = "UPDATE mydatabase.generalfund SET amount = " + total + " WHERE seqNo = 1;";
			stmt.executeUpdate(sql);

		}catch(Exception e) {}
	}

	public void extendGeneralFund(Double amount, Double change, int ctr)
	{
		String sql = "";
		ResultSet rs = null;
		try
		{
			/*switch (ctr)
			{
			case 1: sql = "SELECT travel FROM mydatabase.generalfund WHERE amount = " + amount + ";";
			break;
			case 2: sql = "SELECT insurance FROM mydatabase.generalfund WHERE amount = " + amount + ";";
			break;
			case 3: sql = "SELECT transportation FROM mydatabase.generalfund WHERE amount = " + amount + ";";
			break;
			case 4: sql = "SELECT mall FROM mydatabase.generalfund WHERE amount = " + amount + ";";
			break;
			case 5: sql = "SELECT communication FROM mydatabase.generalfund WHERE amount = " + amount + ";";
			break;
			case 6: sql = "UPDATE mydatabase.generalfund SET officesupplies = " + change + " WHERE amount = " + amount + ";";
			break;
			case 7: sql = "UPDATE mydatabase.generalfund SET industrialsupplies = " + change + " WHERE amount = " + amount + ";";
			break;
			case 8: sql = "UPDATE mydatabase.generalfund SET labsupplies = " + change + " WHERE amount = " + amount + ";";
			break;
			case 9: sql = "UPDATE mydatabase.generalfund SET notary = " + change + " WHERE amount = " + amount + ";";
			break;
			case 10: sql = "UPDATE mydatabase.generalfund SET equipment = " + change + " WHERE amount = " + amount + ";";
			break;	
			case 11: sql = "UPDATE mydatabase.generalfund SET building = " + change + " WHERE amount = " + amount + ";";
			break;
			case 12: sql = "UPDATE mydatabase.generalfund SET photocopy = " + change + " WHERE amount = " + amount + ";";
			break;
			case 13: sql = "UPDATE mydatabase.generalfund SET printingexpenses = " + change + " WHERE amount = " + amount + ";";
			break;
			case 14: sql = "UPDATE mydatabase.generalfund SET representation = " + change + " WHERE amount = " + amount + ";";
			break;
			}
			rs = stmt.executeQuery(sql);*/

			switch (ctr)
			{
			case 1: sql = "UPDATE mydatabase.generalfund SET travel = " + change + " WHERE amount = " + amount + ";";
			break;
			case 2: sql = "UPDATE mydatabase.generalfund SET insurance = " + change + " WHERE amount = " + amount + ";";
			break;
			case 3: sql = "UPDATE mydatabase.generalfund SET transportation = " + change + " WHERE amount = " + amount + ";";
			break;
			case 4: sql = "UPDATE mydatabase.generalfund SET mall = " + change + " WHERE amount = " + amount + ";";
			break;
			case 5: sql = "UPDATE mydatabase.generalfund SET communication = " + change + " WHERE amount = " + amount + ";";
			break;
			case 6: sql = "UPDATE mydatabase.generalfund SET officesupplies = " + change + " WHERE amount = " + amount + ";";
			break;
			case 7: sql = "UPDATE mydatabase.generalfund SET industrialsupplies = " + change + " WHERE amount = " + amount + ";";
			break;
			case 8: sql = "UPDATE mydatabase.generalfund SET labsupplies = " + change + " WHERE amount = " + amount + ";";
			break;
			case 9: sql = "UPDATE mydatabase.generalfund SET notary = " + change + " WHERE amount = " + amount + ";";
			break;
			case 10: sql = "UPDATE mydatabase.generalfund SET equipment = " + change + " WHERE amount = " + amount + ";";
			break;	
			case 11: sql = "UPDATE mydatabase.generalfund SET building = " + change + " WHERE amount = " + amount + ";";
			break;
			case 12: sql = "UPDATE mydatabase.generalfund SET photocopy = " + change + " WHERE amount = " + amount + ";";
			break;
			case 13: sql = "UPDATE mydatabase.generalfund SET printingexpenses = " + change + " WHERE amount = " + amount + ";";
			break;
			case 14: sql = "UPDATE mydatabase.generalfund SET representation = " + change + " WHERE amount = " + amount + ";";
			break;
			}
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void updateProfessionalServices(String name, Double amount)
	{
		try
		{
			String sql = "UPDATE mydatabase.professionalservices SET others = " + amount + " WHERE faculty = '" + name + "'";
			stmt.executeUpdate(sql);

			sql = "SELECT * FROM mydatabase.professionalservices where faculty != 'TOTAL:'";

			ResultSet rs = stmt.executeQuery(sql);

			Double total = 0.00;
			while (rs.next())
			{
				Double temp = rs.getDouble("others");
				total = total + temp;
			}

			sql = "UPDATE mydatabase.professionalservices SET others = " + total + " WHERE faculty = 'TOTAL:'";
			stmt.executeUpdate(sql);
		}catch(Exception e) {}
	}

	public void deleteTrustAccount(String name)
	{
		try
		{
			String sql = "DELETE FROM mydatabase.trustaccounts WHERE account_name = '" + name + "'";
			stmt.executeUpdate(sql);
		}catch(Exception e) {}

	}

	public void deleteFacultyMember(String name)
	{
		try
		{ 
			String sql = "DELETE FROM mydatabase.facultyprofile WHERE faculty = '" + name + "'";
			stmt.executeUpdate(sql);
		}catch (Exception e) {}
	}
	public void deleteGeneralFund(String name)
	{
		try
		{
			Double amount = 0.00;
			String sql = "SELECT * FROM mydatabase.generalfund WHERE payee = '" + name + "'";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				Double temp = rs.getDouble("amount");
				amount = amount + temp;
			}

			Double total = 0.00;
			sql = "SELECT * FROM mydatabase.generalfund WHERE seqNo = 1;";
			rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				Double temp = rs.getDouble("amount");
				total = total + temp;
			}

			total = total - amount;

			sql = "DELETE FROM mydatabase.generalfund WHERE payee = '" + name + "'";
			stmt.executeUpdate(sql);

			sql = "UPDATE mydatabase.generalfund SET amount = " + total + " WHERE seqNo = 1;";
			stmt.executeUpdate(sql);
		}catch(Exception e) {}
	}

	public void deleteProfessionalServices(String name)
	{
		try
		{
			Double salary = 0.00;
			Double others = 0.00;

			String sql = "SELECT * FROM mydatabase.professionalservices WHERE faculty = '" + name + "'";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				Double tempSalary = rs.getDouble("salary");
				Double tempOthers = rs.getDouble("others");

				salary = salary + tempSalary;
				others = others + tempOthers;
			}

			Double totalSalary = 0.00;
			Double totalOthers = 0.00;

			sql = "SELECT * FROM mydatabase.professionalservices WHERE faculty = 'TOTAL:'";
			rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				Double tempSalary = rs.getDouble("salary");
				Double tempOthers = rs.getDouble("others");

				totalSalary = totalSalary + tempSalary;
				totalOthers = totalOthers + tempOthers;
			}

			totalSalary = totalSalary - salary;
			totalOthers = totalOthers - others;

			sql = "DELETE FROM mydatabase.professionalservices WHERE faculty = '" + name + "'";
			stmt.executeUpdate(sql);

			sql = "UPDATE mydatabase.professionalservices SET salary = " + totalSalary + " WHERE faculty = 'TOTAL:'";
			stmt.executeUpdate(sql);

			sql = "UPDATE mydatabase.professionalservices SET others = " + totalOthers + " WHERE faculty = 'TOTAL:'";
			stmt.executeUpdate(sql);
		}catch(Exception e) {}
	}

	public boolean checkPassword(String username, String current, String change)
	{
		bpe = new BasicPasswordEncryptor();
		try
		{
			String sql = "SELECT * FROM mydatabase.users WHERE user_name = '" + username + "'";
			ResultSet rs = stmt.executeQuery(sql);

			while (rs.next())
			{
				int id = rs.getInt("user_id");
				String key = (String) rs.getObject("user_password");
				if (bpe.checkPassword(current, key))
				{
					String newPass = bpe.encryptPassword(change);
					sql = "UPDATE mydatabase.users SET user_password = '" + newPass + "' WHERE user_name = '" + username + "' AND user_id = " + id;
					stmt.executeUpdate(sql);
					return true;
				}
			}
		}catch(Exception e) {}
		return false;
	}

	public void createFacultyProfile(String name, String position, int rank, Double salary, String remarks)
	{
		try
		{
			String sql = "INSERT INTO mydatabase.facultyprofile(faculty, position, rank, salary, Remarks) VALUES ('" + 
					name + "', '" + position + "', " + rank + ", " + salary + ", '" + remarks + "')";
			stmt.executeUpdate(sql);
		}catch(Exception e) {}
	}

	public void deleteAllLogIn()
	{
		try
		{
			String sql = "DELETE FROM mydatabase.login WHERE logout IS NOT NULL;";
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void deleteAllActivities()
	{
		try
		{
			String sql = "DELETE FROM mydatabase.activities;";
			stmt.executeUpdate(sql);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public void createSalary(String position, String rank, Double salary)
	{
		String sql = "";
		ResultSet rs = null;
		try
		{
			sql = "SELECT * FROM mydatabase.salary WHERE position = '" + position + "' AND rank = " + rank + ";";
			rs = stmt.executeQuery(sql);
			
			if (rs.next())
			{
				sql = "UPDATE mydatabase.salary SET salary = " + salary + " WHERE position = '" + position + "' AND rank = " + rank + ";";
			}
			else
			{
				sql = "INSERT INTO mydatabase.salary VALUES ('" + position + "', " + rank + ", " + salary + ");";
			}
			stmt.executeUpdate(sql);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}

