package com.thinking.machines.hr.bl.managers;
import com.thinking.machines.tmcommon.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import java.util.*;
import java.io.*;
public class DesignationManager implements DesignationManagerInterface
{
private Map<Integer,DesignationInterface> codeWiseMap;
private Map<String,DesignationInterface> titleWiseMap;
private List<DesignationInterface> codeWiseOrderedList;
private List<DesignationInterface> titleWiseOrderedList;
final private static DesignationManagerInterface designationManager;
private DesignationManager()
{
populateDataStructures();
}
static
{
designationManager=new DesignationManager();
}
public static DesignationManagerInterface getInstance()
{
return designationManager;
}
private void populateDataStructures()
{
codeWiseMap=new HashMap<>();
titleWiseMap=new HashMap<>();
codeWiseOrderedList=new LinkedList<>();
titleWiseOrderedList=new LinkedList<>();
try
{
List<DesignationDTOInterface> dlDesignations;
dlDesignations=new DesignationDAO().getAll();
DesignationInterface designation;
int vCode;
String vTitle;
for(DesignationDTOInterface dlDesignation :dlDesignations)
{
designation=new Designation();
POJOCopier.copy(designation,dlDesignation);
vCode=designation.getCode();
vTitle=designation.getTitle().toUpperCase();
codeWiseMap.put(new Integer(vCode),designation);
titleWiseMap.put(vTitle,designation);
codeWiseOrderedList.add(designation);
titleWiseOrderedList.add(designation);
}
Collections.sort(codeWiseOrderedList);
Collections.sort(titleWiseOrderedList,new Comparator<DesignationInterface>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
String leftTitle=left.getTitle().toUpperCase();
String rightTitle=right.getTitle().toUpperCase();
return leftTitle.compareTo(rightTitle);
}
});
}catch(DAOException daoException)
{
System.out.println(daoException.getMessage());
}
}
public void add(DesignationInterface designation)throws BLException
{
BLException blException=new BLException();
if(designation==null)blException.addException("designation","Required Designation");
int vCode=designation.getCode();
String vTitle=designation.getTitle();
if(vTitle==null||vTitle.trim().length()==0)blException.addException("title","Title Required");
vTitle=vTitle.trim();
if(vCode!=0) blException.addException("code","Code should be zero.");
if( vTitle.trim().length()>35)blException.addException("title","Title cannot exceed 35 characters.");
String dTitle="";
int i=0;
while(i<titleWiseOrderedList.size())
{
dTitle=titleWiseOrderedList.get(i).getTitle();
if(dTitle.equalsIgnoreCase(vTitle))
{
blException.addException("title","Title already exists.");
}
i++;
}
if(blException.hasException("code") || blException.hasException("title")||blException.hasException("designation"))
{
throw blException;
}
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
POJOCopier.copy(designationDTO,designation);
try{
new DesignationDAO().add(designationDTO);
}catch(DAOException daoException)
{
System.out.println(daoException);
}
vCode=designationDTO.getCode();
DesignationInterface dsDesignation;
dsDesignation=new Designation();
POJOCopier.copy(dsDesignation,designationDTO);
codeWiseMap.put(new Integer(vCode),dsDesignation);
titleWiseMap.put(vTitle.toUpperCase(),dsDesignation);

if(codeWiseOrderedList.size()==0)
{
codeWiseOrderedList.add(dsDesignation);
}
else
{
Comparator<DesignationInterface> codeComparator=new Comparator<>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
int leftCode=left.getCode();
int rightCode=right.getCode();
return leftCode-rightCode;
}
}; 
DesignationInterface tmp;
tmp=codeWiseOrderedList.get(0);
if(codeComparator.compare(dsDesignation,tmp)<=0)
{
codeWiseOrderedList.add(0,dsDesignation);
}
else
{
tmp=codeWiseOrderedList.get(codeWiseOrderedList.size()-1);
if(codeComparator.compare(dsDesignation,tmp)>=0)
{
codeWiseOrderedList.add(dsDesignation);
}
else
{
int lb,ub,mid;
lb=0;
ub=codeWiseOrderedList.size()-1;
int x;
while(lb<=ub)
{
mid=(lb+ub)/2;
x=codeComparator.compare(codeWiseOrderedList.get(mid),dsDesignation);
if(x==0)
{
codeWiseOrderedList.add(mid,dsDesignation);
break;
}
else 
{
if(x<0)
{
if(codeComparator.compare(codeWiseOrderedList.get(mid+1),dsDesignation)>0)
{
codeWiseOrderedList.add(mid+1,dsDesignation);
break;
}
lb=mid+1;
}
else
{
if(x>0)
{
if(codeComparator.compare(codeWiseOrderedList.get(mid-1),dsDesignation)<0)
{
codeWiseOrderedList.add(mid,dsDesignation);
break;
}
ub=mid-1;
}
}
}
}
}
}
}

if(titleWiseOrderedList.size()==0)
{
titleWiseOrderedList.add(dsDesignation);
}
else
{
Comparator<DesignationInterface> titleComparator=new Comparator<>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
String leftTitle=left.getTitle().toUpperCase();
String rightTitle=right.getTitle().toUpperCase();
return leftTitle.compareTo(rightTitle);
}
}; 
DesignationInterface tmp;
tmp=titleWiseOrderedList.get(0);
if(titleComparator.compare(dsDesignation,tmp)<=0)
{
titleWiseOrderedList.add(0,dsDesignation);
}
else
{
tmp=titleWiseOrderedList.get(titleWiseOrderedList.size()-1);
if(titleComparator.compare(dsDesignation,tmp)>=0)
{
titleWiseOrderedList.add(dsDesignation);
}
else
{
int lb,ub,mid;
lb=0;
ub=titleWiseOrderedList.size()-1;
int x;
while(lb<=ub)
{
mid=(lb+ub)/2;
x=titleComparator.compare(titleWiseOrderedList.get(mid),dsDesignation);
if(x==0)
{
titleWiseOrderedList.add(mid,dsDesignation);
break;
}
else 
{
if(x<0)
{
if(titleComparator.compare(titleWiseOrderedList.get(mid+1),dsDesignation)>0)
{
titleWiseOrderedList.add(mid+1,dsDesignation);
break;
}
lb=mid+1;
}
else
{
if(x>0)
{
if(titleComparator.compare(titleWiseOrderedList.get(mid-1),dsDesignation)<0)
{
titleWiseOrderedList.add(mid,dsDesignation);
break;
}
ub=mid-1;
}
}
}
}
}
}
}
designation.setCode(vCode);
}
public void update(DesignationInterface designation)throws BLException
{
BLException blException=new BLException();
if(designation==null)blException.addException("designation","Required designation");
int vCode=designation.getCode();
String vTitle=designation.getTitle();
if(vTitle==null||vTitle.trim().length()==0 )blException.addException("title","Required title");
vTitle=vTitle.trim();
if(vCode<=0)blException.addException("code","Invalid Code.");
if(vTitle.length()>35)blException.addException("title","Title cannot exceed 35 Characters.");
int dCode=0;
String dTitle="";
boolean foundCode=false;
boolean foundTitle=false;
int i=0;
while(i<titleWiseOrderedList.size())
{
dCode=codeWiseOrderedList.get(i).getCode();
if(foundCode==false && vCode==dCode)
{
foundCode=true;
}
dTitle=titleWiseOrderedList.get(i).getTitle();
if(foundTitle==false && vCode!=dCode && dTitle.equalsIgnoreCase(vTitle))
{
foundTitle=true;
}
if(foundCode==true && foundTitle==true) break;
i++;
}
if(foundCode==false)blException.addException("code","Invalid Code.");
if(foundTitle==true)blException.addException("title","Title already exists.");
if(blException.hasException("code") || blException.hasException("title")|| blException.hasException("designation"))
{
throw blException;
}
DesignationDTOInterface designationDTO;
designationDTO=new DesignationDTO();
POJOCopier.copy(designationDTO,designation);
try
{
new DesignationDAO().update(designationDTO);
}catch(DAOException daoException)
{
System.out.println(daoException);
}
DesignationInterface designationObjectToBeRemoved=codeWiseMap.get(vCode);
codeWiseMap.remove(vCode);
titleWiseMap.remove(designationObjectToBeRemoved.getTitle().toUpperCase());
codeWiseOrderedList.remove(designationObjectToBeRemoved);
titleWiseOrderedList.remove(designationObjectToBeRemoved);
DesignationInterface dsDesignation;
dsDesignation=new Designation();
POJOCopier.copy(dsDesignation,designation);
codeWiseMap.put(new Integer(vCode),dsDesignation);
titleWiseMap.put(vTitle.toUpperCase(),dsDesignation);
if(codeWiseOrderedList.size()==0)
{
codeWiseOrderedList.add(dsDesignation);
}
else
{
Comparator<DesignationInterface> codeComparator=new Comparator<>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
int leftCode=left.getCode();
int rightCode=right.getCode();
return leftCode-rightCode;
}
}; 
DesignationInterface tmp;
tmp=codeWiseOrderedList.get(0);
if(codeComparator.compare(dsDesignation,tmp)<=0)
{
codeWiseOrderedList.add(0,dsDesignation);
}
else
{
tmp=codeWiseOrderedList.get(codeWiseOrderedList.size()-1);
if(codeComparator.compare(dsDesignation,tmp)>=0)
{
codeWiseOrderedList.add(dsDesignation);
}
else
{
int lb,ub,mid;
lb=0;
ub=codeWiseOrderedList.size()-1;
int x;
while(lb<=ub)
{
mid=(lb+ub)/2;
x=codeComparator.compare(codeWiseOrderedList.get(mid),dsDesignation);
if(x==0)
{
codeWiseOrderedList.add(mid,dsDesignation);
break;
}
else 
{
if(x<0)
{
if(codeComparator.compare(codeWiseOrderedList.get(mid+1),dsDesignation)>0)
{
codeWiseOrderedList.add(mid+1,dsDesignation);
break;
}
lb=mid+1;
}
else
{
if(x>0)
{
if(codeComparator.compare(codeWiseOrderedList.get(mid-1),dsDesignation)<0)
{
codeWiseOrderedList.add(mid,dsDesignation);
break;
}
ub=mid-1;
}
}
}
}
}
}
}

if(titleWiseOrderedList.size()==0)
{
titleWiseOrderedList.add(dsDesignation);
}
else
{
Comparator<DesignationInterface> titleComparator=new Comparator<>(){
public int compare(DesignationInterface left,DesignationInterface right)
{
String leftTitle=left.getTitle().toUpperCase();
String rightTitle=right.getTitle().toUpperCase();
return leftTitle.compareTo(rightTitle);
}
}; 
DesignationInterface tmp;
tmp=titleWiseOrderedList.get(0);
if(titleComparator.compare(dsDesignation,tmp)<=0)
{
titleWiseOrderedList.add(0,dsDesignation);
}
else
{
tmp=titleWiseOrderedList.get(titleWiseOrderedList.size()-1);
if(titleComparator.compare(dsDesignation,tmp)>=0)
{
titleWiseOrderedList.add(dsDesignation);
}
else
{
int lb,ub,mid;
lb=0;
ub=titleWiseOrderedList.size()-1;
int x;
while(lb<=ub)
{
mid=(lb+ub)/2;
x=titleComparator.compare(titleWiseOrderedList.get(mid),dsDesignation);
if(x==0)
{
titleWiseOrderedList.add(mid,dsDesignation);
break;
}
else 
{
if(x<0)
{
if(titleComparator.compare(titleWiseOrderedList.get(mid+1),dsDesignation)>0)
{
titleWiseOrderedList.add(mid+1,dsDesignation);
break;
}
lb=mid+1;
}
else
{
if(x>0)
{
if(titleComparator.compare(titleWiseOrderedList.get(mid-1),dsDesignation)<0)
{
titleWiseOrderedList.add(mid,dsDesignation);
break;
}
ub=mid-1;
}
}
}
}
}
}
}
}
public void delete(int code)throws BLException
{
BLException blException=new BLException();
if(code<=0)blException.addException("code","Invalid code.");
int dCode=0;
boolean foundCode=false;
int k=0;
while(k<codeWiseOrderedList.size())
{
dCode=codeWiseOrderedList.get(k).getCode();
if(dCode==code)
{
foundCode=true;
break;
}
k++;
}
if(foundCode==false)blException.addException("code","Invalid Code");
if(blException.hasException("code"))throw blException;
if(EmployeeManager.getEmployeeManager().isDesignationAlloted(code))
{
blException.addException("code","EmployeeId exists against this designation");
}
if(blException.hasException("code"))throw blException;
try
{
new DesignationDAO().delete(code);
}catch(DAOException daoException)
{
System.out.println(daoException);
}
DesignationInterface itemToBeRemoved=codeWiseMap.get(code);
String title=itemToBeRemoved.getTitle();
codeWiseMap.remove(code);
titleWiseMap.remove(title.toUpperCase());
codeWiseOrderedList.remove(itemToBeRemoved);
titleWiseOrderedList.remove(itemToBeRemoved);
}
public int getCount() throws BLException
{
return codeWiseOrderedList.size();
}
public List<DesignationInterface> getDesignations(DesignationInterface.ATTRIBUTES ...orderBy)throws BLException
{
List<DesignationInterface> list=null;
if(orderBy.length==0||orderBy[0]==DesignationInterface.TITLE)
{
list=this.titleWiseOrderedList;
}
else
{
list=this.codeWiseOrderedList;
}
List<DesignationInterface> designations=new LinkedList<>();
DesignationInterface designation;
for(DesignationInterface d:list)
{
designation=new Designation();
/*designation.setCode(d.getCode());
designation.setTitle(d.getTitle());*/
POJOCopier.copy(designation,d);
designations.add(designation);
}
return designations;
}
public DesignationInterface getByCode(int code)throws BLException
{
BLException blException=new BLException();
if(code<=0)blException.addException("code","Invalid Code");
DesignationInterface designation;
designation=codeWiseMap.get(code);
if(designation==null)blException.addException("code","Invalid code.");
if(blException.hasException("code"))throw blException;
DesignationInterface designations=new Designation();
POJOCopier.copy(designations,designation);
return designations;
}
public DesignationInterface getByTitle(String title)throws BLException
{
BLException blException=new BLException();
if(title==null||title.trim().length()==0)blException.addException("title","Required title");
if(title.trim().length()>35)blException.addException("title","Title cannot exceed 35 Characters");
DesignationInterface designation;
designation=titleWiseMap.get(title.trim().toUpperCase());
if(designation==null)blException.addException("title","Invalid title");
if(blException.hasException("title"))throw blException;
DesignationInterface designations=new Designation();
POJOCopier.copy(designations,designation);
return designations;
}
public boolean codeExists(int code)throws BLException
{
BLException blException=new BLException();
if(code<=0)blException.addException("code","Invalid code.");
if(blException.hasException("code"))throw blException;
return codeWiseMap.containsKey(code);
}
public boolean titleExists(String title)throws BLException
{
BLException blException=new BLException();
if(title==null||title.trim().length()==0) blException.addException("title","Required title");
if(title.trim().length()>35)blException.addException("title","Title cannot exceed 35 characters");
if(blException.hasException("title"))throw blException;
return titleWiseMap.containsKey(title.trim().toUpperCase());
}
public boolean isAttachedToAnEmployee(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0)blException.addException("code","Invalid code");
if(blException.hasException("code"))throw blException;
return EmployeeManager.getEmployeeManager().isDesignationAlloted(code);
}
public int getEmployeesCountWithDesignation(int code) throws BLException
{
BLException blException=new BLException();
if(code<=0)blException.addException("code","Invalid Code");
if(blException.hasException("code"))throw blException;
if(EmployeeManager.getEmployeeManager().isDesignationAlloted(code))
{
return EmployeeManager.getEmployeeManager().countOfEmployeesWithSameDesignation(code);
}
throw new BLException("No Employee with given designation code");
}
}