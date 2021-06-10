import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.tmcommon.*;
import java.sql.*;
import java.math.*;
import java.text.*;
import java.io.*;
import java.util.*;
class getCountEmployee
{
public static void main(String gg[])
{
try
{
System.out.println("Total Count is : "+new EmployeeDAO().getCount());
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
}
