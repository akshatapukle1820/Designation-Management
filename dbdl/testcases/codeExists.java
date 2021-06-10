import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import java.sql.*;
class codeExists
{
public static void main(String gg[])
{
int code=Integer.parseInt(gg[0]);
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
boolean codeExists=designationDAO.exists(code);
System.out.println("Designation code "+code+" exists : "+codeExists);
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}