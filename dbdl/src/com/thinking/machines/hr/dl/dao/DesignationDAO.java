package com.thinking.machines.hr.dl.dao;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.dto.*;
import java.util.*;
import java.sql.*;
public class DesignationDAO implements DesignationDAOInterface
{
public void add(DesignationDTOInterface designationDTO)throws DAOException
{
String title=designationDTO.getTitle().trim();
if(title==null) throw new DAOException("Title should not be null");
if(title.length()==0 || title.length()>35) throw new DAOException("Title should not be 0 or greater than 35");
Connection connection;
connection=DAOConnection.getConnection();
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException(title+" already exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("insert into designation (title) values (?)",Statement.RETURN_GENERATED_KEYS);
preparedStatement.setString(1,title);
preparedStatement.executeUpdate();
resultSet=preparedStatement.getGeneratedKeys();
resultSet.next();
int code=resultSet.getInt(1);	
designationDTO.setCode(code);	 
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}
public void update(DesignationDTOInterface designationDTO)throws DAOException
{
int code=designationDTO.getCode();
String title=designationDTO.getTitle();
if(code<0)throw new DAOException("Invalid code : "+code);
if(title==null)throw new DAOException("Title should not be null : "+title);
if(title.length()==0 || title.length()>35) throw new DAOException("Title should not be 0 or greater than 35");
Connection connection;
connection=DAOConnection.getConnection();
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Code does not exists : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("select * from designation where title=? and code!=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==true)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException(title+" already exists");
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("update designation set title=? where code=?");
preparedStatement.setString(1,title);
preparedStatement.setInt(2,code);
preparedStatement.executeUpdate();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}
public void delete(int code)throws DAOException
{
if(code<0)throw new DAOException("Invalid code : "+code);
Connection connection;
connection=DAOConnection.getConnection();
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Code does not exists : "+code);
}
resultSet.close();
preparedStatement.close();
preparedStatement=connection.prepareStatement("delete from designation where code=?");
preparedStatement.setInt(1,code);
preparedStatement.executeUpdate();		
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
}
public DesignationDTOInterface getByCode(int code)throws DAOException
{
if(code<0) throw new DAOException("Invalid code  : "+code);
Connection connection;
connection=DAOConnection.getConnection();
DesignationDTOInterface designationDTO;
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Code does not exists : "+code);
}
int vCode;
String vTitle;
vCode=resultSet.getInt("code");
vTitle=resultSet.getString("title").trim();
designationDTO=new DesignationDTO();
designationDTO.setCode(vCode);
designationDTO.setTitle(vTitle);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return designationDTO;
}
public DesignationDTOInterface getByTitle(int title)throws DAOException
{
if(title==null)throw new DAOException("Title should not be null : "+title);
Connection connection;
connection=DAOConnection.getConnection();
DesignationDTOInterface designationDTO;
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
if(resultSet.next()==false)
{
resultSet.close();
preparedStatement.close();
connection.close();
throw new DAOException("Title does not exists : "+title);
}
int vCode;
String vTitle;
vCode=resultSet.getInt("code");
vTitle=resultSet.getString("title").trim();
designationDTO=new DesignationDTO();
designationDTO.setCode(vCode);
designationDTO.setTitle(vTitle);
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return designationDTO;
}
public List<DesignationDTOInterface> getAll()throws DAOException
{
Connection connection;
connection=DAOConnection.getConnection();
List<DesignationDTOInterface> list;
DesignationDTOInterface designationDTO;
try
{
list=new LinkedList<>();
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select * from designation");
int code;
String title;
while(resultSet.next())
{
code=resultSet.getInt("code");
title=resultSet.getString("title").trim();
designationDTO=new DesignationDTO();
designationDTO.setCode(code);
designationDTO.setTitle(title);
list.add(designationDTO);
}
resultSet.close();
statement.close();
connection.close(); 
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return list;
}
public int getCount()throws DAOException
{
Connection connection;
connection=DAOConnection.getConnection();	
int count=0;
try
{
Statement statement;
statement=connection.createStatement();
ResultSet resultSet;
resultSet=statement.executeQuery("select count(*) as no_of_designation from designation");
resultSet.next();
count=resultSet.getInt("no_of_designation");
resultSet.close();
statement.close();
connection.close(); 
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return count;
}
public boolean exists(int code)throws DAOException
{
if(code<0) throw new DAOException("Invalid code  : "+code);
boolean codeExists=false;
Connection connection;
connection=DAOConnection.getConnection();
DesignationDTOInterface designationDTO;
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where code=?");
preparedStatement.setInt(1,code);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
codeExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return codeExists;
}
}
public boolean exists(String title)throws DAOException
{
if(title==null)throw new DAOException("Title should not be null : "+title);
boolean titleExists=false;
Connection connection;
connection=DAOConnection.getConnection();
DesignationDTOInterface designationDTO;
try
{
PreparedStatement preparedStatement;
preparedStatement=connection.prepareStatement("select * from designation where title=?");
preparedStatement.setString(1,title);
ResultSet resultSet;
resultSet=preparedStatement.executeQuery();
titleExists=resultSet.next();
resultSet.close();
preparedStatement.close();
connection.close();
}catch(Exception e)
{
throw new DAOException(e.getMessage());
}
return titleExists;
}
}