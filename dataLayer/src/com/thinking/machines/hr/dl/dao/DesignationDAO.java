package com.thinking.machines.hr.dl.dao;
import java.io.*;
import java.util.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
public class DesignationDAO implements DesignationDAOInterface
{
public void add(DesignationDTOInterface designationDTO) throws DAOException
{
try
{
File file=new File(DESIGNATION_DATA_FILE);
int lastGeneratedCode=0;
int count=0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.writeBytes("0         \n0         \n");
}
randomAccessFile.seek(0);
lastGeneratedCode=Integer.parseInt(randomAccessFile.readLine().trim());
count=Integer.parseInt(randomAccessFile.readLine().trim());
String vTitle=designationDTO.getTitle().trim();
String fTitle;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
fTitle=randomAccessFile.readLine();
if(vTitle.equalsIgnoreCase(fTitle))
{
randomAccessFile.close();
throw new DAOException("Designation:"+vTitle+"exists.");
}
}
int vCode=lastGeneratedCode+1;
randomAccessFile.writeBytes(String.valueOf(vCode));
randomAccessFile.writeBytes("\n");
randomAccessFile.writeBytes(vTitle);
randomAccessFile.writeBytes("\n");
randomAccessFile.seek(0);
String codeString=String.valueOf(vCode);
count++;
String countString=String.valueOf(count);
while(codeString.length()<10) codeString+=" ";
while(countString.length()<10) countString+=" ";
randomAccessFile.writeBytes(codeString+"\n"+countString+"\n");
randomAccessFile.close();
designationDTO.setCode(vCode);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void update(DesignationDTOInterface designationDTO) throws DAOException
{
int vCode=designationDTO.getCode();
String vTitle=designationDTO.getTitle().trim();
try
{
File file=new File(DESIGNATION_DATA_FILE);
if(!file.exists())
{
throw new DAOException("Invalid designation code:"+vCode);
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid designation code:"+vCode);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode;
String fTitle;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
if(fCode==vCode)
{
found=true;
break;
}
randomAccessFile.readLine();
}
if(!found)
{
randomAccessFile.close();
throw new DAOException("Invalid designation code:"+vCode);
}
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fTitle.equalsIgnoreCase(vTitle) && fCode!=vCode)
{
randomAccessFile.readLine();
throw new DAOException("Designation :"+vTitle+" exists.");
}
}
randomAccessFile.seek(0);
File tmpFile=new File("fhaltu.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine());
fTitle=randomAccessFile.readLine();
if(fCode!=vCode)
{
tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(fTitle);
tmpRandomAccessFile.writeBytes("\n");
}
else
{
tmpRandomAccessFile.writeBytes(String.valueOf(vCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(vTitle);
tmpRandomAccessFile.writeBytes("\n");
}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
randomAccessFile.close();

}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public void delete(int code) throws DAOException
{
try
{
File file=new File(DESIGNATION_DATA_FILE);
if(!file.exists())
{
throw new DAOException("Invalid designation code:"+code);
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid designation code:"+code);
}
randomAccessFile.readLine();
randomAccessFile.readLine();
int fCode;
String fTitle;
boolean found=false;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine().trim());
if(fCode==code)
{
found=true;
break;
}
randomAccessFile.readLine();
}
if(!found)
{
randomAccessFile.close();
throw new DAOException("Invalid designation code:"+code);
}
randomAccessFile.seek(0);
File tmpFile=new File("fhaltu.tmp");
if(tmpFile.exists()) tmpFile.delete();
RandomAccessFile tmpRandomAccessFile=new RandomAccessFile(tmpFile,"rw");
tmpRandomAccessFile.writeBytes(randomAccessFile.readLine()+"\n");

int count=Integer.parseInt(randomAccessFile.readLine().trim());
count--;
String countString=String.valueOf(count);
while(countString.length()<10) countString+=" ";
tmpRandomAccessFile.writeBytes(countString+"\n");
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
fCode=Integer.parseInt(randomAccessFile.readLine().trim());
fTitle=randomAccessFile.readLine();
if(fCode!=code)
{
tmpRandomAccessFile.writeBytes(String.valueOf(fCode));
tmpRandomAccessFile.writeBytes("\n");
tmpRandomAccessFile.writeBytes(fTitle);
tmpRandomAccessFile.writeBytes("\n");

}
}
randomAccessFile.seek(0);
tmpRandomAccessFile.seek(0);
while(tmpRandomAccessFile.getFilePointer()<tmpRandomAccessFile.length())
{
randomAccessFile.writeBytes(tmpRandomAccessFile.readLine()+"\n");
}
randomAccessFile.setLength(tmpRandomAccessFile.length());
tmpRandomAccessFile.setLength(0);
tmpRandomAccessFile.close();
randomAccessFile.close();
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public DesignationDTOInterface getByCode(int code) throws DAOException
{
try
{
File file=new File(DESIGNATION_DATA_FILE);
if(!file.exists())
{
throw new DAOException("Invalid designation code:"+code);
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid designation code:"+code);
}
int vCode;
String vTitle;
randomAccessFile.readLine();
randomAccessFile.readLine();
DesignationDTOInterface designationDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
vTitle=randomAccessFile.readLine();
if(vCode==code)
{
randomAccessFile.close();
designationDTO=new DesignationDTO();
designationDTO.setCode(vCode);
designationDTO.setTitle(vTitle);
return designationDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid designation code:"+code);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public DesignationDTOInterface getByTitle(String title) throws DAOException
{
try
{
File file=new File(DESIGNATION_DATA_FILE);
if(!file.exists())
{
throw new DAOException("Invalid designation "+title);
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid designation :"+title);
}
int vCode;
String vTitle;
randomAccessFile.readLine();
randomAccessFile.readLine();
DesignationDTOInterface designationDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
vTitle=randomAccessFile.readLine();
if(vTitle.equals(title))
{
randomAccessFile.close();
designationDTO=new DesignationDTO();
designationDTO.setCode(vCode);
designationDTO.setTitle(vTitle);
return designationDTO;
}
}
randomAccessFile.close();
throw new DAOException("Invalid designation :"+title);
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public List<DesignationDTOInterface> getAll() throws DAOException
{
try
{
List<DesignationDTOInterface> list;
list=new LinkedList<DesignationDTOInterface>();
File file;
file=new File(DESIGNATION_DATA_FILE);
if(!file.exists()) return list;
RandomAccessFile randomAccessFile;
randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return list;
}
DesignationDTOInterface designationDTO;
randomAccessFile.readLine();
randomAccessFile.readLine();
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
designationDTO=new DesignationDTO();
designationDTO.setCode(Integer.parseInt(randomAccessFile.readLine()));
designationDTO.setTitle(randomAccessFile.readLine());
list.add(designationDTO);
}
randomAccessFile.close();
return list;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}

public int getCount() throws DAOException
{
try
{
File file=new File(DESIGNATION_DATA_FILE);
if(!file.exists()) return 0;
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
return 0;
}
randomAccessFile.readLine();
int count=Integer.parseInt(randomAccessFile.readLine().trim());
randomAccessFile.close();
return count;
}
catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean exists(int code) throws DAOException
{
try
{
File file=new File(DESIGNATION_DATA_FILE);
if(!(file.exists()))
{
throw new DAOException("Invalid designation code:"+code);
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid designation code:"+code);
}
int vCode;
randomAccessFile.seek(0);
randomAccessFile.readLine();
randomAccessFile.readLine();
DesignationDTOInterface designationDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
vCode=Integer.parseInt(randomAccessFile.readLine());
randomAccessFile.readLine();
if(code==vCode)
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}
}
public boolean exists(String title) throws DAOException
{
try
{
File file=new File(DESIGNATION_DATA_FILE);
if(!file.exists())
{
throw new DAOException("Invalid designation :"+title);
}
RandomAccessFile randomAccessFile=new RandomAccessFile(file,"rw");
if(randomAccessFile.length()==0)
{
randomAccessFile.close();
throw new DAOException("Invalid designation :"+title);
}
String vTitle;
randomAccessFile.readLine();
randomAccessFile.readLine();
DesignationDTOInterface designationDTO;
while(randomAccessFile.getFilePointer()<randomAccessFile.length())
{
randomAccessFile.readLine();
vTitle=randomAccessFile.readLine();
if(vTitle.equals(title))
{
randomAccessFile.close();
return true;
}
}
randomAccessFile.close();
return false;
}catch(IOException ioException)
{
throw new DAOException(ioException.getMessage());
}


}


}