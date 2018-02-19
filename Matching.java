package Matching;
//import the objects within io;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;


//import the objects to address excel import and export










import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;


import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




public class Match{
	

	
	
	private static final String[] spg4={ "4","7","9","11","14_1","14_2","15",
		"17","18","20","24","26","27","28","30",
		"31","32","34","36","37","39","40","41",
		"43","45","46","48","49","50","51","53",
		"55","58","59","63","64","67","68","70",
		"72","74","77","80","84","85","86","88",
		"90","93","94","98","100","101","102",
		"103","104","105","108","109","112","113",
		"114","116","117","118","120","122","124",
		"125","126","127","128","129","131","132",
		"134","136","137","140","141","158","159",
		"161","163","165","167","173","176","182",
		"184","185","186","188","190","192","193",
		"194","199","201","203","208","210","214",
		"218","219","220","222","223","224","226"
	};
	
      private static final String[] spg6={
		
		"144","145","151","152","153","154","171","172","180","181"
		
	};
      private static final String[] spg8={
	"19","29","33","52","54","56","57","60","61","62_1","62_2","73",
	"78","91","92","95","96","106","110","130","133","135","138","142",
	"205","206","212","213","228","230" };
      
      private static final String[] spg12={"169","170","178","179"};
      private static final String filepath="C:\\Users\\lufuyan\\Desktop\\Project\\1ongoing project\\Dirac Semi Metal data\\afterscreen\\";
	
	public static void main(String[] args) throws NumberFormatException, IOException{
		// This is input you can change 
		// input is the number of bands one need to get gapped phase
		
	
		//########################################################
		
		//import the element electron data into hashmap map
		// File excelFile = new File("nl6b05229_si_002"+".xls");
		FileInputStream fis = new FileInputStream(new File("nl6b05229_si_002"+".xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook (fis);
		XSSFSheet sheet = workbook.getSheetAt(1);
		
		 
	  	HashMap<String,Row> map=new HashMap<String, Row>();
	  	HashMap<String,Row> outmap= new HashMap<String,Row>();
	  	
	  	Iterator<Row> ite = sheet.rowIterator();
		while(ite.hasNext()){
			Row row = ite.next();

			if(row.getLastCellNum()==2){break;}
			
            
			short minColIx = row.getFirstCellNum();
			short maxColIx = row.getLastCellNum();
			if(maxColIx==5){
				map.put(row.getCell(1).toString(),row);		
			}
			System.out.println(Short.toString(minColIx)+"  "+Short.toString(maxColIx));
			
			for(short colIx=minColIx; colIx<maxColIx; colIx++) {
			   Cell cell = row.getCell(colIx);
			   
			   if(cell == null) {
			     continue;
			   }
			   else{
				  
				   System.out.print(cell.toString()+"   ");
			   }
			 }
			System.out.println();
			
			
		}
		workbook.close();
		fis.close();
		
		
		//end of the import of element electron data
		
		//################################################################
		
		// circle to address data files
		for(int nof=0;nof<spg4.length;nof++)
		{
		   System.out.println(spg4[nof]);
			find(spg4[nof],map,outmap,"4n");
		
		}
		for(int nof=0;nof<spg6.length;nof++)
		{
		   System.out.println(spg6[nof]);
			find(spg6[nof],map,outmap,"6n");
		
		}
		for(int nof=0;nof<spg8.length;nof++)
		{
		   System.out.println(spg8[nof]);
			find(spg8[nof],map,outmap,"8n");
		
		}
		for(int nof=0;nof<spg12.length;nof++)
		{
		   System.out.println(spg12[nof]);
			find(spg12[nof],map,outmap,"12n");
		
		}
		XSSFWorkbook outworkbook = new XSSFWorkbook();
		XSSFSheet outsheet = outworkbook.createSheet("Screened List");
		int rowNum=0;
		
		for (HashMap.Entry<String, Row> entry : outmap.entrySet()){
			
			Row maprow=entry.getValue();
			Row outrow=outsheet.createRow(rowNum++);
			short minRolIx=maprow.getFirstCellNum();
			short maxRolIx=maprow.getLastCellNum();
			for(short i=minRolIx;i<maxRolIx;i++){
				Cell cell=outrow.createCell(i);
				cell.setCellValue(maprow.getCell(i).toString());
			}
			System.out.println("the outrow is:"+outrow.getCell(1).toString());
			
		}
		try{	
			FileOutputStream outputStream = new FileOutputStream(new File(filepath+"Screened"+".xlsx"));    			
	         outworkbook.write(outputStream);
	         outworkbook.close();
	     } catch (FileNotFoundException e) {
	         e.printStackTrace();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
		
	}
	
	public static void find(String spg, HashMap<String, Row> map,HashMap<String, Row> outmap, String group)throws NumberFormatException, IOException{
		
		
		
		FileInputStream fis = new FileInputStream(new File(filepath+group+"\\"+"Re"+spg+".xlsx"));
		XSSFWorkbook workbook = new XSSFWorkbook (fis);
		XSSFSheet sheet = workbook.getSheetAt(0);
		
	//	FileOutputStream fos = new FileOutputStream(new File(filepath+"Final"+spg+".xlsx"));
		
		
		Iterator<Row> ite = sheet.rowIterator();
		while(ite.hasNext()){
			Row row = ite.next();
			short maxColIx = row.getLastCellNum();
			if(maxColIx<2){
				continue;
			}
			String theName=row.getCell(0).toString();
			String theENum=row.getCell(1).toString();
		//	System.out.println(theName);
			theName=theName.replaceAll("\\s","");
		//	System.out.println("after parsing the name is :"+theName);
			Row maprow=map.get(theName);	
			if(maprow==null){continue;}
			else if(!outmap.containsKey(theName)){
				Row outrow=maprow;
				try{
				Cell newcell=outrow.createCell(outrow.getLastCellNum());
				newcell.setCellValue(group);
				String groupNum=group.replace("n", "");
				Integer residue=((int)Double.parseDouble(theENum))%Integer.parseInt(groupNum);
				Cell seccell=outrow.createCell(outrow.getLastCellNum());
				seccell.setCellValue(residue.toString());
				outmap.put(theName, outrow);
				}
				catch( NullPointerException e){
					e.printStackTrace();
				}
				
			}
			
			workbook.close();
			
	/*		if(maprow!=null){
				System.out.println(theName+"  match with  "+maprow.getCell(1).toString());
				Row outrow=outsheet.createRow(rowNum++);
				short minRolIx=maprow.getFirstCellNum();
				short maxRolIx=maprow.getLastCellNum();
				for(short i=minRolIx;i<maxRolIx;i++){
					Cell cell=outrow.createCell(i);
					cell.setCellValue(maprow.getCell(i).toString());
				}
				System.out.println("the outrow is:"+outrow.getCell(1).toString());
				Cell cell0=outrow.createCell(5);
				cell0.setCellValue(row.getCell(1).toString());		
					
	*/		
		}
	}		
		
	/*	workbook.close();
		try{	
			FileOutputStream outputStream = new FileOutputStream(new File(filepath+"Final"+spg+".xlsx"));    			
	         outworkbook.write(outputStream);
	         outworkbook.close();
	     } catch (FileNotFoundException e) {
	         e.printStackTrace();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
		*/
              
	

	


}
