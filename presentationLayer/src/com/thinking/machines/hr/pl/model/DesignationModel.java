package com.thinking.machines.hr.pl.model;
import com.thinking.machines.hr.pl.exception.*;
import com.thinking.machines.hr.bl.interfaces.*;
import com.thinking.machines.hr.bl.exceptions.*;
import com.thinking.machines.hr.bl.pojo.*;
import com.thinking.machines.hr.bl.managers.*;
import com.thinking.machines.hr.dl.interfaces.*;
import com.thinking.machines.hr.dl.exceptions.*;
import com.thinking.machines.hr.dl.dto.*;
import com.thinking.machines.hr.dl.dao.*;
import com.thinking.machines.tmcommon.*;
import com.itextpdf.text.Font.FontFamily;
import java.util.*;
import java.io.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import java.net.*;
import com.itextpdf.text.Font;
import java.util.Date.*;
import java.io.FileOutputStream.*;
import javax.swing.ImageIcon.*;
import java.awt.Dimension.*;
/*class HeaderFooterManager extends PdfPageEventHelper
{
private PdfTemplate template;
private com.itextpdf.text.Image total;
public void onOpenDocument(PdfWriter writer,Document document)
{
try
{
template=writer.getDirectContent().createTemplate(50,30);
total=com.itextpdf.text.Image.getInstance(template);
total.setRole(PdfName.ARTIFACT);
}
catch(BadElementException baException)
{
System.out.println(baException);
}
}
public void onStartPage(PdfWriter writer,Document document)
{
try
{
PdfPTable list=new PdfPTable(1);
list.setTotalWidth(527);
list.setLockedWidth(true);
list.getDefaultCell().setFixedHeight(20);
list.getDefaultCell().setBorder(com.itextpdf.text.Rectangle.BOTTOM);
list.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
list.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
list.addCell(new Paragraph("List Of Designations"));
PdfPTable header=new PdfPTable(3);
header.setWidths(new int[]{10,24,12});
header.setLockedWidth(true);
header.setTotalWidth(527);
header.getDefaultCell().setFixedHeight(40);
header.getDefaultCell().setBorder(com.itextpdf.text.Rectangle.BOTTOM);
header.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
com.itextpdf.text.Image image=com.itextpdf.text.Image.getInstance("c:\\forAll\\logggoHR.png");
header.addCell(image);
header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
PdfPCell text=new PdfPCell();
text.setPaddingBottom(20);
text.setPaddingLeft(20);
text.setBorder(com.itextpdf.text.Rectangle.BOTTOM);
text.setBorderColor(BaseColor.LIGHT_GRAY);
Paragraph p1=new Paragraph("Designation Form");
p1.setAlignment(Element.ALIGN_CENTER);
text.addElement(p1);
header.addCell(text);
header.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
header.addCell(new Phrase(String.format("Page %d of 1",writer.getPageNumber())));
header.writeSelectedRows(0, -1, 34, 803, writer.getDirectContent());
list.writeSelectedRows(0,-1,34,760,writer.getDirectContent());
}
catch(MalformedURLException mue)
{
System.out.println(mue);
}
catch(IOException ioException)
{
System.out.println(ioException);
}
catch(DocumentException documentException)
{
System.out.println(documentException);
}
}
public void onEndPage(PdfWriter writer,Document document)
{
PdfPTable footer=new PdfPTable(2);
footer.setTotalWidth(527);
footer.getDefaultCell().setBorder(com.itextpdf.text.Rectangle.TOP);
footer.getDefaultCell().setBorderColor(BaseColor.LIGHT_GRAY);
footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
footer.addCell(new Phrase("Software By:Shweta Yagnik"));
footer.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
footer.addCell(new Phrase(new SimpleDateFormat("dd/MM/yy").format(new java.util.Date())));
PdfContentByte canvas=writer.getDirectContent();
canvas.beginMarkedContentSequence(PdfName.ARTIFACT);
footer.writeSelectedRows(0,-1,34,30,canvas);
canvas.endMarkedContentSequence();
}
}*/
public class DesignationModel extends AbstractTableModel
{
private DesignationManagerInterface d;
private String title[];
private Object data[];
java.util.List<DesignationInterface> list;
private JTable table;
public DesignationModel()
{
d=DesignationManager.getInstance();
populateDataStructures();
table=new JTable(data.length,title.length);
}
private void populateDataStructures()
{
title=new String[2];
title[0]="S.No";
title[1]="Designation";
try
{
list=d.getDesignations(DesignationInterface.TITLE);
}catch(BLException blException)
{
System.out.println(blException.getGenericException());
}
data=new Object[list.size()];
int i=0;
while(i<list.size())
{
data[i]=list.get(i).getTitle();
i++;
}
}
public int getRowCount()
{
return data.length;
}
public int getColumnCount()
{
return title.length;
}
public boolean isCellEditable(int columnIndex)
{
return false;
}
public String getColumnName(int columnIndex)
{
return title[columnIndex];
}
public Object getValueAt(int rowIndex,int columnIndex)
{
if(columnIndex==0) return rowIndex+1;
return data[rowIndex];
}
public Class getColumnClass(int columnIndex)
{
if(columnIndex==0)return Integer.class;
else
{
return String.class;
}
}
public void setValueAt(Object d,int rowIndex,int columnIndex)
{
data[rowIndex]=d;
}
public DesignationInterface getDesignationAt(int e) throws ModelException
{
if(e<0 || e>list.size())throw new ModelException("Invalid index");
return list.get(e);
}
public void addDesignation(DesignationInterface designation) throws ModelException
{
try
{
d.add(designation);
//list=d.getDesignations();
}catch(BLException b)
{
java.util.List<String> list=b.getExceptions();
for(int i=0;i<list.size();i++)
{
String g=list.get(i);
throw new ModelException(g);
}
}
populateDataStructures();
fireTableDataChanged();
}
public void updateDesignation(DesignationInterface designation) throws ModelException
{
try
{
d.update(designation);
//list=d.getDesignations();
}catch(BLException b)
{
java.util.List<String> list=b.getExceptions();
for(int i=0;i<list.size();i++)
{
String g=list.get(i);
throw new ModelException(g);
}
}
populateDataStructures();
fireTableDataChanged();
}
public void deleteDesignation(int code)throws ModelException
{
try
{
d.delete(code);
//list=d.getDesignations();
}catch(BLException b)
{
java.util.List<String> list=b.getExceptions();
for(int i=0;i<list.size();i++)
{
String g=list.get(i);
throw new ModelException(g);
}
}
populateDataStructures();
fireTableDataChanged();
}
public DesignationInterface getDesignation(String designation,boolean caseSensitive,boolean partialMatch)throws ModelException
{
int found=0;
int i=0;
if(caseSensitive==false)
{
for(i=0;i<list.size();i++)
{
String d=list.get(i).getTitle().toLowerCase();
if(designation.equalsIgnoreCase(d) || d.startsWith(designation.toLowerCase()))
{
found=1;
break;
}
}
}
else
{
for(i=0;i<list.size();i++)
{
String d=list.get(i).getTitle();
if(designation.equals(d) || d.startsWith(designation))
{
found=1;
break;
}
}
}
if(found==0)
{
throw new ModelException("Invalid Designation Entered");
}
DesignationInterface clone=new Designation();
POJOCopier.copy(clone,list.get(i));
return clone;
}
public int IndexOf(String title)
{
int e=0;
try
{
e=list.indexOf(d.getByTitle(title));
}catch(BLException b)
{
System.out.println(b.getGenericException());
}
return e;
}
public int getIndexOf(DesignationInterface designation)
{
boolean found=false;
int e=0;
while(e<list.size())
{
if(designation.equals(list.get(e)))
{
found=true;
break;
}
e++;
}
return e;
}


/*public void pdfMaker(String filesName)
{
int pageSize=10;
int sNo=0;
boolean newPage=true;
Document document=new Document();
try
{
PdfWriter pdfWriter=PdfWriter.getInstance(document,new FileOutputStream(new File(filesName)));
HeaderFooterManager hfm=new HeaderFooterManager();
pdfWriter.setPageEvent(hfm);
document.setMargins(120,115,120,110);
document.setMarginMirroring(false);
document.setPageSize(PageSize.A4);
Font f=new Font(Font.FontFamily.HELVETICA,16.0f,Font.BOLD,BaseColor.RED);
Paragraph pol=new Paragraph("S.No.");
pol.setFont(f);
pol.setAlignment(Element.ALIGN_CENTER);
Paragraph poll=new Paragraph("Designations");
poll.setFont(f);
poll.setAlignment(Element.ALIGN_CENTER);
float[] columnWidths=new float[2];
columnWidths[0]=2.0f;
columnWidths[1]=7.0f;
PdfPTable pTable=null;
document.open();
int i=0;
while(i<list.size())
{
if(newPage)
{
pTable=new PdfPTable(columnWidths);
pTable.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
pTable.getDefaultCell().setFixedHeight(20);
pTable.addCell(pol);
pTable.addCell(poll);
newPage=false;
}
pTable.addCell(String.valueOf(i+1));
pTable.addCell(list.get(i).getTitle());
sNo++;
i++;
if(sNo>=pageSize)
{
if(sNo<list.size())
{
document.add(pTable);
document.newPage();
newPage=true;
}
if(sNo>list.size())
 {
break;
}
}
}
document.close();
}
catch(DocumentException de)
{
System.out.println(de);
}
catch(IOException ioException)
{
System.out.println(ioException);
}
}
*/

public void exportToPdf(File selectedFile)
{
try
{
DesignationInterface designation;
String title="";
Font titleFont;
Font dataFont;
titleFont=new Font(FontFamily.HELVETICA,15,Font.BOLD,BaseColor.BLACK);
dataFont=new Font(FontFamily.HELVETICA,10,Font.BOLD,BaseColor.BLACK);
PdfPTable table1=null;
int size=list.size();
int pageSize=25;
int noOfPages;
int sn=0;
int cp=0;
Document document=new Document();
int result=(size)%(pageSize);
if(result!=0)
{
noOfPages=(size/pageSize)+1;
}
else
{
noOfPages=(size/pageSize);
}//if else ends here
boolean newPage=true;
int i=0;
PdfWriter.getInstance(document,new FileOutputStream(selectedFile));
document.open();
while(i<size)
{
if(newPage==true)
{
cp++;
Font font =new Font(FontFamily.HELVETICA,20,Font.BOLD,BaseColor.GREEN);
PdfPTable table=new PdfPTable(2);
table.setWidths(new int[]{2,24});
table.setTotalWidth(527);
table.setLockedWidth(true);
table.getDefaultCell().setFixedHeight(40);
Image img=Image.getInstance("C:\\javaeg\\hr\\pl\\classes\\images\\hr.png");
PdfPCell cell1=new PdfPCell(img,true);
cell1.setRowspan(2);
Font font1 =new Font(FontFamily.HELVETICA,23,Font.BOLD,BaseColor.BLACK);
PdfPCell cell2=new PdfPCell(new Paragraph("HR Automation ",font1));
cell2.setRowspan(2);
cell2.setHorizontalAlignment(Element.ALIGN_CENTER);
cell2.setVerticalAlignment(Element.ALIGN_MIDDLE);
PdfPCell cell3=new PdfPCell(new Paragraph(" "));
cell3.setColspan(2);
PdfPCell cell4=new PdfPCell(new Paragraph("List Of Designations",new Font(FontFamily.HELVETICA,16,Font.BOLD,BaseColor.BLACK) ));
cell4.setColspan(2);
cell4.setHorizontalAlignment(Element.ALIGN_CENTER);
PdfPCell cell5=new PdfPCell(new Paragraph("Page"+cp+"/"+noOfPages));
cell5.setColspan(2);
cell5.setHorizontalAlignment(Element.ALIGN_RIGHT);
cell1.setBorder(PdfPCell.NO_BORDER);
cell2.setBorder(PdfPCell.NO_BORDER);
cell3.setBorder(PdfPCell.NO_BORDER);
cell4.setBorder(PdfPCell.NO_BORDER);
cell5.setBorder(PdfPCell.NO_BORDER);
table.addCell(cell1);
table.addCell(cell2);
table.addCell(cell3);
table.addCell(cell4);
table.addCell(cell5);
document.add(table);
document.add(new Paragraph(" "));
document.add(new Paragraph(" "));
table1=new PdfPTable(2);
table1.setWidthPercentage(100);
table1.setTotalWidth(288);
table1.setLockedWidth(true);
PdfPCell id=new PdfPCell(new Paragraph("S.No.",new Font(FontFamily.HELVETICA,14,Font.BOLD,BaseColor.BLACK)));
PdfPCell designationTitle=new PdfPCell(new Paragraph("Designation",new Font(FontFamily.HELVETICA,14,Font.BOLD,BaseColor.BLACK)));
id.setHorizontalAlignment(Element.ALIGN_RIGHT);
designationTitle.setHorizontalAlignment(Element.ALIGN_CENTER);
table1.addCell(id);
table1.addCell(designationTitle);
newPage=false;
}
//extract ith object from ds
designation=list.get(i);
sn++;
title=designation.getTitle();
PdfPCell snCell=new PdfPCell(new Paragraph(" "+sn));
snCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
PdfPCell titleCell=new PdfPCell(new Paragraph(title,dataFont));
titleCell.setHorizontalAlignment(Element.ALIGN_LEFT);
table1.addCell(snCell);
table1.addCell(titleCell);
//footer
if((sn%pageSize)==0||sn==size)
{
document.add(table1);
document.add(new Paragraph(""));
document.add(new Paragraph(""));
document.add(new Paragraph(""));
document.add(new Paragraph("Software by:- Akshata Pukle",new Font(FontFamily.HELVETICA,15,Font.BOLD,BaseColor.BLACK)));
if(sn<size)
{
document.newPage();
newPage=true;
}
}
i++;
}//while loop ktm
document.close();
}catch(Exception e)
{
e.printStackTrace();
}
}

}
