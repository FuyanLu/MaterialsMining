package Mining;

// import the objects within io;
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
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;




public class MatrialMining {
	
	// Global variable
	private static int index=0;
	
	
	// This is input you can change 
	// input is the number of bands one need to get gapped phase
	private static final int input=4;
	
	private static final String[] spg={ "220"/*
		"4","7","9","11","14_1","14_2","15",
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
		"218","219","222","223","224","226" */
	};
	
	public static void main(String[] args) throws NumberFormatException, IOException{
		// This is input you can change 
		// input is the number of bands one need to get gapped phase
		
		System.out.println(Arrays.toString(spg));
		//########################################################
		
		//import the element electron data into hashmap map
		
		FileInputStream fstream1 = new FileInputStream("element.txt");
		BufferedReader br1 = new BufferedReader(new InputStreamReader(fstream1));
		String strLine1;
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		
		while((strLine1 = br1.readLine()) != null){
			String[] temp = strLine1.split("\t");
			//System.out.println(temp.length);
			
			
		    map.put(temp[1], Integer.parseInt(temp[0]));
			//System.out.println(strLine1);
			
		}
		map.put("D", 1);
		map.put("th", 90);
		//System.out.println(map);
		
		br1.close();
		
		//end of the import of element electron data
		
		//################################################################
		
		// the loop to address data files
		for(int nof=0;nof<spg.length;nof++)
		{
		   System.out.println(spg[nof]);
			screen(spg[nof],map);
		
		}
		
		
	}
	
	
	
	public static void screen(String sg,HashMap<String,Integer> map)throws NumberFormatException, IOException{
		

		// the data from txt file before screening
	/*	FileInputStream fstream = new FileInputStream("the"+sg+".txt");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
*/		
		
		
		
		
		// the result data file after screening
/*		File file = new File("Re"+sg+".txt");
*		PrintWriter writer = new PrintWriter(file);
*		writer.print("");
*		writer.close();
*		BufferedWriter bwr=null;
*		PrintWriter pwr=null;
*/		
		
		
		
		//the result excel data file after screening
		
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("#"+sg);
        int rowNum=0;
		
		
     // the data from excel file before screening
         File excelFile = new File("the"+sg+".xls");
     	 Scanner scanner=new Scanner(excelFile);
     	 
     	 //skip the header line for the file
     	 if(scanner.hasNextLine()){
     	 scanner.nextLine();
     	 }
     	 else{
     		scanner.close();
     		try{	
     			 FileOutputStream outputStream = new FileOutputStream("Re"+sg+".xlsx");
     	         workbook.write(outputStream);
     	         workbook.close();
     	     } catch (FileNotFoundException e) {
     	         e.printStackTrace();
     	     } catch (IOException e) {
     	         e.printStackTrace();
     	     }
     		 return ;}
        
		String strLine;
        //Read File Line By Line
		while (scanner.hasNext())   {
		 
			// skip the control line for each row
			if(scanner.hasNextLine()){
		     	 scanner.nextLine();
		     	 }
		     	 else{
		     		scanner.close();
		     		try{	
		     			 FileOutputStream outputStream = new FileOutputStream("Re"+sg+".xlsx");
		     	         workbook.write(outputStream);
		     	         workbook.close();
		     	     } catch (FileNotFoundException e) {
		     	         e.printStackTrace();
		     	     } catch (IOException e) {
		     	         e.printStackTrace();
		     	     }
		     		 return ;}
			
			// store the string of line into theLine
			String theLine;
			if(scanner.hasNextLine()){
				theLine=scanner.nextLine();
		     	 }
		     	 else{
		     		scanner.close();
		     		try{	
		     			 FileOutputStream outputStream = new FileOutputStream("Re"+sg+".xlsx");
		     	         workbook.write(outputStream);
		     	         workbook.close();
		     	     } catch (FileNotFoundException e) {
		     	         e.printStackTrace();
		     	     } catch (IOException e) {
		     	         e.printStackTrace();
		     	     }
		     		 return ;}
			
			
			// split the string based on </td>
			String[] theList=theLine.split("</td>");
			
			//choose the third column 
			String the3rdColumn="";
			try{
			 the3rdColumn=theList[2];
			}catch(ArrayIndexOutOfBoundsException e){
				System.out.println(theLine);
			}
			
			//get rid of first four chars <td>
			strLine=the3rdColumn.substring(4);
					
			System.out.println("*********" + strLine);
			index = 0;
			
			double sum = 0;
			
			
			while(index < strLine.length()){
				if(strLine.charAt(index) == '('){
					// jump into the "()"
					index++;
					System.out.println("include begin ###########");
					sum+=tempsum(strLine,map);
					
					
	
					
				}
				
				else if(index < strLine.length() && isCharacter(strLine.charAt(index))){
					StringBuilder builder = new StringBuilder();
					StringBuilder number = new StringBuilder();
					while(index < strLine.length() && isCharacter(strLine.charAt(index))){
						builder.append(strLine.charAt(index));
						index++;
					}
					// element is recorded in the builder.toString
					if(index < strLine.length() && (strLine.charAt(index) >'9' || strLine.charAt(index) < '0')){
						number.append('1');
				
					}
					else if(index == strLine.length()){
						number.append('1');
					}
					else{
					while(index < strLine.length() && ((strLine.charAt(index)>= '0'&& strLine.charAt(index) <= '9') || strLine.charAt(index) == '.')){
						
						number.append(strLine.charAt(index));
						index++;
					}
					}
					
					
					
					//System.out.println(builder.toString());
					//System.out.println(number.toString());
					
					int elenum = map.get(builder.toString());
					//System.out.println(builder.toString());
					double eletime = Double.parseDouble(number.toString());
					
					double current = (double)elenum * eletime;
					//System.out.println(current);
					
					
						sum += current;
					
					
				}
				else{index++;}
			}
			
			System.out.println(sum);
			
			int resultsum = (int)sum;
			if(sum - (double)resultsum > 0){
				continue;
			}
			int res = resultsum%input;
			if(res != 0 && res%2 == 0){
				System.out.print(strLine + "\t" + sum);
				System.out.println();
/*				try {
*					bwr= new BufferedWriter(new FileWriter(file,true));
*					pwr= new PrintWriter(bwr);
*		            pwr.println(strLine + "\t" + sum);
*		        } catch ( IOException e ) {
*		            e.printStackTrace();
*		        } finally {
*		          if ( pwr != null ) {
*		            pwr.close();
*		          }
*		        }
*/				
				Row row=sheet.createRow(rowNum++);
				Cell cell0=row.createCell(0);
				cell0.setCellValue(strLine);
				Cell cell1=row.createCell(1);
				cell1.setCellValue(sum);
				
				
			}
			
			// sum is the result
			//break;
			
			
			//String[] temp1 = strLine.split(" ");
			
			//System.out.println(temp1.length);
			
			//break;
			
			
			//System.out.println(value);
		  //System.out.println (strLine);
		}
	scanner.close();
	try{	
		 FileOutputStream outputStream = new FileOutputStream("Re"+sg+".xlsx");
         workbook.write(outputStream);
         workbook.close();
     } catch (FileNotFoundException e) {
         e.printStackTrace();
     } catch (IOException e) {
         e.printStackTrace();
     }
		
	}
	
	
	public static double tempsum(String str,HashMap<String,Integer> map){
		
		double thesum=0;
		while(index < str.length()){		
			
			
			//System.out.println("the index is :"+index+"\t The length of str a is:"+str.length());
			
		if(str.charAt(index) == '('){
			// jump into the "()"
			index++;
			System.out.println("include begin ##########");		
			thesum+=tempsum(str,map);
				
		         }
		
		else if(str.charAt(index) == ')'){
			//System.out.println("include end");
			
			StringBuilder number = new StringBuilder();
			// if there is no number after ")" the time should be one
			
			if(index == str.length()-1){
				number.append('1');
				index++;
		
			}
			else if( index<str.length()-1){
				if( str.charAt(index+1) == ' '||str.charAt(index+1)==')'||str.charAt(index+1)=='('){number.append('1');}
				index++;
			}
			else{
				index++;
			}
			// else there must be a number to denote the times of "()"
			while(index < str.length() && ((str.charAt(index)>= '0'&& str.charAt(index) <= '9') || str.charAt(index) == '.')){
				
				number.append(str.charAt(index));
				index++;
			}
			System.out.println("-------------" + number);
			double eletimes = Double.parseDouble(number.toString());
			System.out.println("The number inside is: "+thesum*eletimes);
			return (double)thesum*eletimes;
		}
		
		else if(index < str.length() && isCharacter(str.charAt(index))){
			StringBuilder builder = new StringBuilder();
			StringBuilder number = new StringBuilder();
			while(index < str.length() && isCharacter(str.charAt(index))){
				builder.append(str.charAt(index));
				index++;
			}
			// element is recorded in the builder.toString
			if(index < str.length() && (str.charAt(index) >'9' || str.charAt(index) < '0')){
				number.append('1');
		        
			}
			else if(index == str.length()){
				number.append('1');
			}
			else{
			while(index < str.length() && ((str.charAt(index)>= '0'&& str.charAt(index) <= '9') || str.charAt(index) == '.')){
				number.append(str.charAt(index));
				index++;
			}
			}
						
			//System.out.println(builder.toString());
			//System.out.println(number.toString());
			
			int elenum = map.get(builder.toString());
			//System.out.println(builder.toString());
			double eletime = Double.parseDouble(number.toString());
			
			double current = (double)elenum * eletime;
			//System.out.println(current);
			
			thesum+=current;
			
		}
		else{index++;}
		//System.out.println("the index is :"+index+"\t The length of str bf is:"+str.length());
		//System.out.println("the index is :"+index+"\t The length of str af is:"+str.length());
		
		}
		System.out.println("There is an error");
		return 0.0;
	}
	
	public static boolean isCharacter(char a){
		if((a >= 'A' && a <= 'Z') || (a >= 'a' && a <= 'z')){
			return true;
		}
		return false;
	}

}
