package Parser;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Test;

import Exception.ComponentsNumberException;
import Exception.DateException;
import Exception.FlightNumberException;
import Exception.FromTimeException;
import Exception.PlaneAgeException;
import Exception.PlaneIdException;
import Exception.PlaneSeatsException;
import Exception.PlaneTypeException;
import Exception.ToTimeException;

public class ParserTest {

	/* Testing strategy
	 * 测试checkwhethercorrect方法
	 * 按照字符串是否符合要求划分：测试所有异常
     * 覆盖每个取值如下：
     */
	
	String fileoneline,fileentry;
	BufferedReader thisfile;
	 
	 @Test(expected = ArrayIndexOutOfBoundsException.class)
	 public void shouldGetArrayIndexOutOfBoundsException() throws ArrayIndexOutOfBoundsException, ComponentsNumberException, DateException, FlightNumberException, FromTimeException, ToTimeException, PlaneIdException, PlaneTypeException, PlaneSeatsException, PlaneAgeException{
		 fileentry="";
		 try {
				thisfile = new BufferedReader(new FileReader("src/txt/ArrayIndexOutOfBoundsException.txt"));
			} catch (FileNotFoundException e1) {
				System.out.println("文件不存在，请重新输入文件名\n");
			}
		 try {
				while((fileoneline=thisfile.readLine())!=null) { 
						fileentry=fileentry+fileoneline+"\n"; //记得加上换行符
				}
			} catch (IOException e1) {
				System.out.println("文件读入出现错误\n");
			}
			try {
				thisfile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 Parser temp=new Parser();
		 temp.checkwhethercorrect(fileentry);
	}
	 
	 @Test(expected = DateException.class)
	 public void shouldGetDateException1() throws ArrayIndexOutOfBoundsException, ComponentsNumberException, DateException, FlightNumberException, FromTimeException, ToTimeException, PlaneIdException, PlaneTypeException, PlaneSeatsException, PlaneAgeException{
		 fileentry="";
		 try {
				thisfile = new BufferedReader(new FileReader("src/txt/DateException1.txt"));
			} catch (FileNotFoundException e1) {
				System.out.println("文件不存在，请重新输入文件名\n");
			}
		 try {
				while((fileoneline=thisfile.readLine())!=null) { 
						fileentry=fileentry+fileoneline+"\n"; //记得加上换行符
				}
			} catch (IOException e1) {
				System.out.println("文件读入出现错误\n");
			}
			try {
				thisfile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 Parser temp=new Parser();
		 temp.checkwhethercorrect(fileentry);
	 }
	 
	 @Test(expected = DateException.class)
	 public void shouldGetDateException2() throws ArrayIndexOutOfBoundsException, ComponentsNumberException, DateException, FlightNumberException, FromTimeException, ToTimeException, PlaneIdException, PlaneTypeException, PlaneSeatsException, PlaneAgeException{
		 fileentry="";
		 try {
				thisfile = new BufferedReader(new FileReader("src/txt/DateException2.txt"));
			} catch (FileNotFoundException e1) {
				System.out.println("文件不存在，请重新输入文件名\n");
			}
		 try {
				while((fileoneline=thisfile.readLine())!=null) { 
						fileentry=fileentry+fileoneline+"\n"; //记得加上换行符
				}
			} catch (IOException e1) {
				System.out.println("文件读入出现错误\n");
			}
			try {
				thisfile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 Parser temp=new Parser();
		 temp.checkwhethercorrect(fileentry);
	 }
	
	 @Test(expected = FlightNumberException.class)
	 public void shouldGetFlightNumberException() throws ArrayIndexOutOfBoundsException, ComponentsNumberException, DateException, FlightNumberException, FromTimeException, ToTimeException, PlaneIdException, PlaneTypeException, PlaneSeatsException, PlaneAgeException{
		 fileentry="";
		 try {
				thisfile = new BufferedReader(new FileReader("src/txt/FlightNumberException.txt"));
			} catch (FileNotFoundException e1) {
				System.out.println("文件不存在，请重新输入文件名\n");
			}
		 try {
				while((fileoneline=thisfile.readLine())!=null) { 
						fileentry=fileentry+fileoneline+"\n"; //记得加上换行符
				}
			} catch (IOException e1) {
				System.out.println("文件读入出现错误\n");
			}
			try {
				thisfile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 Parser temp=new Parser();
		 temp.checkwhethercorrect(fileentry);
	 }
	 
	 @Test(expected = FromTimeException.class)
	 public void shouldGetFromTimeException() throws ArrayIndexOutOfBoundsException, ComponentsNumberException, DateException, FlightNumberException, FromTimeException, ToTimeException, PlaneIdException, PlaneTypeException, PlaneSeatsException, PlaneAgeException{
		 fileentry="";
		 try {
				thisfile = new BufferedReader(new FileReader("src/txt/FromTimeException.txt"));
			} catch (FileNotFoundException e1) {
				System.out.println("文件不存在，请重新输入文件名\n");
			}
		 try {
				while((fileoneline=thisfile.readLine())!=null) { 
						fileentry=fileentry+fileoneline+"\n"; //记得加上换行符
				}
			} catch (IOException e1) {
				System.out.println("文件读入出现错误\n");
			}
			try {
				thisfile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 Parser temp=new Parser();
		 temp.checkwhethercorrect(fileentry);
	 }
	 
	 @Test(expected = ToTimeException.class)
	 public void shouldGetToTimeException() throws ArrayIndexOutOfBoundsException, ComponentsNumberException, DateException, FlightNumberException, FromTimeException, ToTimeException, PlaneIdException, PlaneTypeException, PlaneSeatsException, PlaneAgeException{
		 fileentry="";
		 try {
				thisfile = new BufferedReader(new FileReader("src/txt/ToTimeException.txt"));
			} catch (FileNotFoundException e1) {
				System.out.println("文件不存在，请重新输入文件名\n");
			}
		 try {
				while((fileoneline=thisfile.readLine())!=null) { 
						fileentry=fileentry+fileoneline+"\n"; //记得加上换行符
				}
			} catch (IOException e1) {
				System.out.println("文件读入出现错误\n");
			}
			try {
				thisfile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 Parser temp=new Parser();
		 temp.checkwhethercorrect(fileentry);
	 }
	 
	 @Test(expected = PlaneIdException.class)
	 public void shouldGetPlaneIdException() throws ArrayIndexOutOfBoundsException, ComponentsNumberException, DateException, FlightNumberException, FromTimeException, ToTimeException, PlaneIdException, PlaneTypeException, PlaneSeatsException, PlaneAgeException{
		 fileentry="";
		 try {
				thisfile = new BufferedReader(new FileReader("src/txt/PlaneIdException.txt"));
			} catch (FileNotFoundException e1) {
				System.out.println("文件不存在，请重新输入文件名\n");
			}
		 try {
				while((fileoneline=thisfile.readLine())!=null) { 
						fileentry=fileentry+fileoneline+"\n"; //记得加上换行符
				}
			} catch (IOException e1) {
				System.out.println("文件读入出现错误\n");
			}
			try {
				thisfile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 Parser temp=new Parser();
		 temp.checkwhethercorrect(fileentry);
	 }
	 
	 
	 @Test(expected = PlaneTypeException.class)
	 public void shouldGetPlaneTypeException() throws ArrayIndexOutOfBoundsException, ComponentsNumberException, DateException, FlightNumberException, FromTimeException, ToTimeException, PlaneIdException, PlaneTypeException, PlaneSeatsException, PlaneAgeException{
		 fileentry="";
		 try {
				thisfile = new BufferedReader(new FileReader("src/txt/PlaneTypeException.txt"));
			} catch (FileNotFoundException e1) {
				System.out.println("文件不存在，请重新输入文件名\n");
			}
		 try {
				while((fileoneline=thisfile.readLine())!=null) { 
						fileentry=fileentry+fileoneline+"\n"; //记得加上换行符
				}
			} catch (IOException e1) {
				System.out.println("文件读入出现错误\n");
			}
			try {
				thisfile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 Parser temp=new Parser();
		 temp.checkwhethercorrect(fileentry);
	 }
	 
	 @Test(expected = PlaneSeatsException.class)
	 public void shouldGetPlaneSeatsException() throws ArrayIndexOutOfBoundsException, ComponentsNumberException, DateException, FlightNumberException, FromTimeException, ToTimeException, PlaneIdException, PlaneTypeException, PlaneSeatsException, PlaneAgeException{
		 fileentry="";
		 try {
				thisfile = new BufferedReader(new FileReader("src/txt/PlaneSeatsException.txt"));
			} catch (FileNotFoundException e1) {
				System.out.println("文件不存在，请重新输入文件名\n");
			}
		 try {
				while((fileoneline=thisfile.readLine())!=null) { 
						fileentry=fileentry+fileoneline+"\n"; //记得加上换行符
				}
			} catch (IOException e1) {
				System.out.println("文件读入出现错误\n");
			}
			try {
				thisfile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 Parser temp=new Parser();
		 temp.checkwhethercorrect(fileentry);
	 }
	 
	 @Test(expected = PlaneAgeException.class)
	 public void shouldGetPlaneAgeException() throws ArrayIndexOutOfBoundsException, ComponentsNumberException, DateException, FlightNumberException, FromTimeException, ToTimeException, PlaneIdException, PlaneTypeException, PlaneSeatsException, PlaneAgeException{
		 fileentry="";
		 try {
				thisfile = new BufferedReader(new FileReader("src/txt/PlaneAgeException.txt"));
			} catch (FileNotFoundException e1) {
				System.out.println("文件不存在，请重新输入文件名\n");
			}
		 try {
				while((fileoneline=thisfile.readLine())!=null) { 
						fileentry=fileentry+fileoneline+"\n"; //记得加上换行符
				}
			} catch (IOException e1) {
				System.out.println("文件读入出现错误\n");
			}
			try {
				thisfile.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		 Parser temp=new Parser();
		 temp.checkwhethercorrect(fileentry);
	 }
	 
	/* Testing strategy
	 * 测试getAllinformation方法
	 * 测试某个字符串的返回值即可
     */
	@Test
	public void getAllinformationtest() {
		Parser temp=new Parser();
		assertEquals("2020-01-16,AA018",temp.getAllinformation("Flight:", "Flight:2020-01-16,AA018"+"\n"+
		"{"+"\n"+"DepartureAirport:Hongkong"+"\n"+"ArrivalAirport:Shenyang"+"\n"+"DepatureTime:2020-01-16 22:40"
				+"\n"+"ArrivalTime:2020-01-17 03:51"+"\n"+"Plane:B6967"+"\n"+"{"+"\n"+"Type:A340"+"\n"+"Seats:332"
		+"\n"+"Age:23.7"+"\n"+"}"+"\n"+"}"+"\n"));
	}
}
