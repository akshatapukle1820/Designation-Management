import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import java.sql.*;
class ConnectionTestCase
{
public static void main(String gg[])
{
try
{
Connection c=DAOConnection .getConnection();
System.out.println("Connection established");
c.close();
System.out.println("Connection closed");
}catch(DAOException daoException)
{
System.out.println(daoException);
}catch(SQLException sql)
{
System.out.println(sql);
}
}
}
