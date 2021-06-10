import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import java.sql.*;
import java.util.*;
class getAll
{
public static void main(String gg[])
{
try
{
DesignationDAOInterface designationDAO;
designationDAO=new DesignationDAO();
List<DesignationDTOInterface> list;
list=designationDAO.getAll();
for(DesignationDTOInterface designations :list)
{
System.out.printf("Code : %d, Title %s\n",designations.getCode(),designations.getTitle());
}
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}