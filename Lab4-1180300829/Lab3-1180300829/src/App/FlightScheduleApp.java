package App;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Board.FlightEntryBoard;
import EntryState.*;
import Exception.*;
import Factory.FlightEntryFactory;
import Location.*;
import Parser.Parser;
import PlanningEntry.*;
import PlanningEntryAPIs.PlanningEntryAPIs;
import Resource.Flight;
import Timeslot.*;

public class FlightScheduleApp {

	private static List<FlightEntry<Flight>> flightlist=new ArrayList<>(); //储存所有FlightEntry的集合
	private final static Logger logger = Logger.getLogger(FlightScheduleApp.class.getName());
	
	 /**
     * 菜单
     */
	public static void menu() {
		System.out.println("1.创建一个新的航班计划项并设置信息(为了方便在信息版检测，设置时间时请设置为当前时间一小时前后的时间)");
		System.out.println("2.为某个航班计划项分配资源");
		System.out.println("3.变更某个航班计划项的位置");
		System.out.println("4.变更某个航班计划项的资源");
		System.out.println("5.启动某个航班计划项");
		System.out.println("6.结束某个航班计划项");
		System.out.println("7.取消某个航班计划项");
		System.out.println("8.查看某个航班计划项的状态");
		System.out.println("9.从文件中读入航班计划项并加入计划项集合中(读取大的文件可能耗时很长)");
		System.out.println("****(注意：执行10,11,12的操作需要为每一个计划项分配资源后执行,否则异常)****");
		System.out.println("10.检测航班计划项中是否存在位置和资源独占冲突");
		System.out.println("11.针对某个飞机资源，列出所有使用该资源的航班计划项");
		System.out.println("12.针对某个飞机资源，选中含有该资源的某个航班计划项，列出它的前序计划项(输出一个即可)");
		System.out.println("13.选定某个位置，展示当前时刻该位置的信息版");
		System.out.println("14.显示当前含有航班计划项的个数");
		System.out.println("15.按照过滤条件进行日志查询");
		System.out.println("16.写入日志到文件并结束程序");
	}
	
	
	/**
	 * 
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args)   {
		TwoLocationEntryImpl a;
		OneDistinguishResourceEntryImpl<Flight> b;
		NoBlockableEntryImpl c;
		String choice,weidu="北纬40度",jingdu="东经112度";
		Scanner scanner=new Scanner(System.in);
		FlightState state;
		Flight myflight;
		Calendar canceltimeone;
		PlanningEntryAPIs myapis=new PlanningEntryAPIs<>();
		String fileentry,fileoneline;
		List<String> allfileentry=new ArrayList<>();
		System.out.println("最开始请执行第一步");
		
		File file=new File("log/FlightScheduleLog.txt");
		try {
			if(!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter=new FileWriter(file);
			fileWriter.write("");
			fileWriter.flush();
			fileWriter.close();
		}catch(IOException e) {
			System.out.println("创建文件失败");
		}
		
		try {		
			while(true) {
				String[] temp;
				String yixie;
				menu();
				Locale.setDefault(new Locale("en", "EN"));
				logger.setLevel(Level.INFO);
				FileHandler fileHandler;	
				fileHandler = new FileHandler("log/FlightScheduleLog.txt", true);
				fileHandler.setFormatter(new SimpleFormatter());
				logger.addHandler(fileHandler);	
				logger.setUseParentHandlers(false);
				System.out.println("请输入你的操作：");
				choice=scanner.nextLine();
				switch(choice) {
				case "1":
					a=new TwoLocationEntryImpl();
					b=new OneDistinguishResourceEntryImpl<Flight>();
					c=new NoBlockableEntryImpl();
					PlanningEntry originflight=new FlightEntryFactory().getFlightEntry(a, b, c); //用工厂方法创建对象
					FlightEntry<Flight> flight=(FlightEntry<Flight>)originflight;
					System.out.println("此航班计划项已经创建完成,下面输入一些信息完成初始状态的建立：");
					System.out.println("请输入航班号(eg AH3567)：");
					String flightname=scanner.nextLine();
					flight.setplanningentryname(flightname);
					logger.log(Level.INFO,"true:setplanningentryname,计划项"+flightname);
				    System.out.println("计划项名字设置成功");
					System.out.println("请输入起点站名称(eg 北京)：");
					String tempname;
					tempname=scanner.nextLine();
					FlightTrainLocation from=new FlightTrainLocation(weidu,jingdu,tempname);
					System.out.println("请输入终点站名称(eg 武汉)：");
					tempname=scanner.nextLine();
					FlightTrainLocation to=new FlightTrainLocation(weidu,jingdu,tempname);
					try {
						if(flight.setlocations(from, to)) {
							logger.log(Level.INFO,"true:setlocation,计划项"+flightname);
						}
					} catch (SameLocationException e2) {
						logger.log(Level.SEVERE,"flase:setlocation,SameLocationException->operation again,计划项"+flightname,e2);
						System.out.println("存在两个相同飞机场位置，请重新执行1操作，用正确格式输入\n");
						break;
					}
					System.out.println("请输入起飞和降落时间(用逗号隔开)(eg 2020-01-01 12:00,2020-01-01 14:00)：");
					try {
						temp=(scanner.nextLine()).split(",");
						Timeslot mytime=new Timeslot(temp[0],temp[1]);
						if(flight.settimeslot(mytime)) {
							logger.log(Level.INFO,"true:settimeslot,计划项"+flightname);
							System.out.println("一个时间对设置成功");
						}
					}catch(ArrayIndexOutOfBoundsException e) {
						 logger.log(Level.SEVERE,"flase:settimeslot,ArrayIndexOutOfBoundsException->operation again,计划项"+flightname,e);
						 System.out.println("未用,隔开，请重新执行1操作，用正确格式输入\n");
						 break;
					}catch(ParseException e) {
						logger.log(Level.SEVERE,"flase:settimeslot,ParseException->operation again,计划项"+flightname,e);
						System.out.println("时间不符合yyyy-MM-dd HH:mm的要求，请重新执行1操作，用正确格式输入\n");
						break;
					} catch (BeginEndTimeException e) {
						logger.log(Level.SEVERE,"flase:settimeslot,BeginEndTimeException->operation again,计划项"+flightname,e);
						System.out.println("起始时间晚于终止时间，请重新执行1操作，用正确格式输入\n");
						break;
					}
					state = FlightWaitingState.instance;
					flight.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					System.out.println("信息设置完成");
					flightlist.add(flight);
					System.out.println("\n");
					break;
				case "2":
					System.out.println("请输入你想加入资源的航班计划项的航班号(eg AH3567)：");
					tempname=scanner.nextLine();
					int i;
					for(i=0;i<flightlist.size();i++) {
						if(flightlist.get(i).getplanningentryname().equals(tempname) ){
							break;
						}
					}
					if(i==flightlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println();
						break;
					}
					List<FlightEntry<Flight>> tempflightlist=new ArrayList<>();
					for(int oo=0;oo<flightlist.size();oo++) {
						tempflightlist.add(flightlist.get(oo).clone());
					}
					FlightEntry<Flight> tempentry=flightlist.get(i).clone();
					if(((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班未分配飞机(Waiting)")) {
						System.out.println("请输入待加入飞机编号，机型号，座位数，机龄(eg N8981,C88,100,2.5)：");
						try {
							temp=(scanner.nextLine()).split(",");
							myflight=new Flight(temp[0],temp[1],Integer.parseInt(temp[2]),Double.parseDouble(temp[3]));
							tempentry.setresource(myflight);
						}catch(ArrayIndexOutOfBoundsException e) {
							 logger.log(Level.SEVERE,"flase:setresource,ArrayIndexOutOfBoundsException->operation again,计划项"+tempentry.getplanningentryname(),e);
							 System.out.println("未用,隔开，请重新执行2操作，用正确格式输入\n");
							 break;
						} catch (NumberFormatException e) {
							e.printStackTrace();
						} catch (LessThanZeroException e) {
							 logger.log(Level.SEVERE,"flase:setresource,LessThanZeroException->operation again,计划项"+tempentry.getplanningentryname(),e);
							 System.out.println("座位数为非正数或机龄为负数，请重新执行2操作，用正确格式输入\n");
							 break;
						}
						state=((FlightState)tempentry.getcurrentstate()).move('a');
						tempentry.setcurrentstate(state);
						System.out.println("计划项当前状态设置成功");
						tempflightlist.set(i, tempentry);
						try {
							if(myapis.checkResourceExclusiveConflict(tempflightlist)) {
								throw new ResourceExclusiveConflictException();
							}
							else {
								flightlist.set(i, tempentry);
								logger.log(Level.INFO,"true:setresource,计划项"+tempentry.getplanningentryname());
								System.out.println("飞机资源设置完成\n");
							}
						}catch(ResourceExclusiveConflictException e) {
							logger.log(Level.SEVERE,"flase:setresource,ResourceExclusiveConflictException->operation again,计划项"+tempentry.getplanningentryname(),e);
							System.out.println("该资源分配后会与已有的其他计划项发生资源独占冲突，请重新分配资源\n");
						}
					}
					else {
						logger.log(Level.INFO,"false:no correct state->operation again,计划项"+tempentry.getplanningentryname());
						System.out.println("当前状态下不能执行设置飞机资源操作\n");
					}
					break;
				case "3":
					logger.log(Level.INFO,"false:changelocation,no changeable location->operation again");
					System.out.println("航班起点和终点位置不可变更");
					System.out.println("\n");
					break;
				case "4":
					System.out.println("请输入你想变更资源的航班计划项的航班号(eg AH3567)：");
					tempname=scanner.nextLine();
					for(i=0;i<flightlist.size();i++) {
						if(flightlist.get(i).getplanningentryname().equals(tempname)) {
							break;
						}
					}
					if(i==flightlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=flightlist.get(i);
					if(((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班已分配飞机(Allocated)")) {
						System.out.println("请输入新的飞机编号，机型号，座位数，机龄(eg B1104,A22,100,2.5)：");
						try {
							temp=(scanner.nextLine()).split(",");
							myflight=new Flight(temp[0],temp[1],Integer.parseInt(temp[2]),Double.parseDouble(temp[3]));
							if(tempentry.changeresource(myflight)) {
								logger.log(Level.INFO,"true:changeresource,计划项"+tempentry.getplanningentryname());
							}
							else {
								logger.log(Level.INFO,"false:changeresource,计划项"+tempentry.getplanningentryname());
								break;
							}
						}catch(ArrayIndexOutOfBoundsException e) {
							 logger.log(Level.SEVERE,"flase:changeresource,ArrayIndexOutOfBoundsException->operation again,计划项"+tempentry.getplanningentryname(),e);
							 System.out.println("未用,隔开，请重新执行4操作，用正确格式输入\n");
							 break;
						} catch (NumberFormatException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						} catch (LessThanZeroException e) {
							 logger.log(Level.SEVERE,"flase:setresource,LessThanZeroException->operation again,计划项"+tempentry.getplanningentryname(),e);
							 System.out.println("座位数为非正数或机龄为负数，请重新执行4操作，用正确格式输入\n");
							 break;
						}
						flightlist.set(i, tempentry);
						System.out.println("\n");
					}
					else {
						logger.log(Level.INFO,"false:no correct state->operation again,计划项"+tempentry.getplanningentryname());
						System.out.println("当前状态下不能执行变更飞机资源操作\n");
					}
					break;
				case "5":
					System.out.println("请输入你想要启动的航班计划项的航班号(eg AH3567)：");
					tempname=scanner.nextLine();
					for(i=0;i<flightlist.size();i++) {
						if(flightlist.get(i).getplanningentryname().equals(tempname)) {
							break;
						}
					}
					if(i==flightlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=flightlist.get(i);
					if(((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班已分配飞机(Allocated)")) {	
						System.out.println("请输入指令：启动");
						yixie=scanner.nextLine();
						if(tempentry.launch(yixie)) {
						logger.log(Level.INFO,"true:launch,计划项"+tempentry.getplanningentryname());
						state=((FlightState)tempentry.getcurrentstate()).move('a');
						tempentry.setcurrentstate(state);
						System.out.println("计划项当前状态设置成功");
						flightlist.set(i, tempentry);
						System.out.println("\n");
						}
						else {
							logger.log(Level.INFO,"false:launch,计划项"+tempentry.getplanningentryname());
							System.out.println("\n");
							break;
						}
					}
					else {
						logger.log(Level.INFO,"false:no correct state->operation again,计划项"+tempentry.getplanningentryname());
						System.out.println("当前状态下不能执行起飞操作\n");
					}
					break;
				case "6":
					System.out.println("请输入你想要结束的航班计划项的航班号(eg AH3567)：");
					tempname=scanner.nextLine();
					for(i=0;i<flightlist.size();i++) {
						if(flightlist.get(i).getplanningentryname().equals(tempname)) {
							break;
						}
					}
					if(i==flightlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=flightlist.get(i);
					if(((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班已起飞(Running)")) {
						System.out.println("请输入指令：结束");
						yixie=scanner.nextLine();
						if(tempentry.finish(yixie)) {
						logger.log(Level.INFO,"true:finish,计划项"+tempentry.getplanningentryname());
						state=((FlightState)tempentry.getcurrentstate()).move('a');
						tempentry.setcurrentstate(state);
						System.out.println("计划项当前状态设置成功");
						flightlist.set(i, tempentry);
						System.out.println("\n");
						}
						else {
							logger.log(Level.INFO,"false:finish,计划项"+tempentry.getplanningentryname());
							System.out.println("\n");
							break;
						}
					}
					else {
						logger.log(Level.INFO,"false:no correct state->operation again,计划项"+tempentry.getplanningentryname());
						System.out.println("当前状态下不能执行降落操作\n");
					}
					break;
				case "7":
					System.out.println("请输入你想要取消的航班计划项的航班号(eg AH3567)：");
					tempname=scanner.nextLine();
					for(i=0;i<flightlist.size();i++) {
						if(flightlist.get(i).getplanningentryname().equals(tempname)) {
							break;
						}
					}
					if(i==flightlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=flightlist.get(i);
					try {
						if(((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班未分配飞机(Waiting)")
								||((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班已分配飞机(Allocated)")) {
							System.out.println("请输入指令：取消");
							yixie=scanner.nextLine();
							if(tempentry.cancel(yixie)) {
							canceltimeone=Calendar.getInstance();
							String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(canceltimeone.getTime()); 
							try {
								canceltimeone.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(str));
								logger.log(Level.INFO,"true:cancel,计划项"+tempentry.getplanningentryname());
							} catch (ParseException e) {
								logger.log(Level.SEVERE,"false:cancel,ParseException->operation again,计划项"+tempentry.getplanningentryname(),e);
								System.out.println("取消时间出错\n");
								break;
							}
							System.out.println("该航班计划项取消的时间为"+str);
							state=((FlightState)tempentry.getcurrentstate()).move('b');
							tempentry.setcurrentstate(state);
							System.out.println("计划项当前状态设置成功");
							flightlist.remove(i);
							System.out.println("\n");
							}
							else {
								System.out.println("\n");
								break;
							}
						}
						else {
							throw new NoCancelStateException();
						}
					}catch(NoCancelStateException e) {
						logger.log(Level.SEVERE,"false:cancel,NoCancelStateException->operation again,计划项"+tempentry.getplanningentryname(),e);
						System.out.println("航班已起飞，无法取消\n");
						break;
					}
				case "8":
					System.out.println("请输入你想要查看的航班计划项的航班号(eg AH3567)：");
					tempname=scanner.nextLine();
					for(i=0;i<flightlist.size();i++) {
						if(flightlist.get(i).getplanningentryname().equals(tempname)) {
							break;
						}
					}
					if(i==flightlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项或者已被取消");
						System.out.println("\n");
						break;
					}
					tempentry=flightlist.get(i);
					System.out.println("该航班计划项的状态为："+((FlightState)tempentry.getcurrentstate()).getflightstate());
					logger.log(Level.INFO,"true:getcurrentstate,计划项"+tempentry.getplanningentryname());
					System.out.println("\n");
					break;
				case "9":
					System.out.println("下面提供五个文件可供读取， 文件名分别为：FlightSchedule_1.txt,FlightSchedule_2.txt,FlightSchedule_3.txt"
							+ "FlightSchedule_4.txt,FlightSchedule_5.txt");
					System.out.println("请输入你想要读取航班计划项的文件名(eg FlightSchedule_1.txt)：");
					tempname=scanner.nextLine();
					BufferedReader thisfile;
					try {
						thisfile = new BufferedReader(new FileReader("src/txt/"+tempname));
					} catch (FileNotFoundException e1) {
						logger.log(Level.SEVERE,"false:findfile,FileNotFoundException->operation again",e1);
						System.out.println("文件不存在，请重新输入文件名\n");
						break;
					}
					i=0;
					int qq=0;
					fileentry="";
					allfileentry=new ArrayList<>();
					Parser tempparser=new Parser();
					tempflightlist=new ArrayList<>();
					boolean flag=true;
					try {
						while((fileoneline=thisfile.readLine())!=null) { //忽略缩进每十三行合成一个字符串
							if(fileoneline.equals("")) {  //忽略缩进
								continue;
							}
							else {
								fileentry=fileentry+fileoneline+"\n"; //记得加上换行符
								if(i==12) {
									allfileentry.add(fileentry);
									i=-1;
									fileentry="";
								}
								i++;
							}
						}
						if(i!=0) {
							throw new ComponentsNumberException();
						}
					} catch (IOException e1) {
						logger.log(Level.SEVERE,"false:readfile,IOException->operation again",e1);
						System.out.println("文件读入出现错误\n");
						break;
					} catch (ComponentsNumberException e) {
						logger.log(Level.SEVERE,"false:addfileentry,ComponentsNumberException->operation again",e);
						System.out.println("存在航班计划项元素定义的分量数目错误，请读入其他文件\n");
						break;
					}
					try {
						thisfile.close();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					for(i=0;i<allfileentry.size();i++) {  //依次检查每个字符串是否符合格式要求
						try {
							tempparser.checkwhethercorrect(allfileentry.get(i));
						} catch (ArrayIndexOutOfBoundsException e) {
							i++;
							logger.log(Level.SEVERE,"false:addfileentry,ComponentsNumberException->operation again",e);
							System.out.println("第"+i+"个航班计划项缺少日期或航班号，请读入其他文件\n");
							flag=false;
							break;
						} catch (DateException e) {
							i++;
							logger.log(Level.SEVERE,"false:addfileentry,DateException->operation again",e);
							System.out.println("第"+i+"个航班计划项航班号在日期前面出现或者日期格式不符合yyyy-MM-dd的要求，请读入其他文件\n");
							flag=false;
							break;
						} catch (FlightNumberException e) {
							i++;
							logger.log(Level.SEVERE,"false:addfileentry,FlightNumberException->operation again",e);
							System.out.println("第"+i+"个航班计划项航班号不符合两位大写字母和2-4位数字构成的要求，请读入其他文件\n");
							flag=false;
							break;
						} catch (FromTimeException e) {
							i++;
							logger.log(Level.SEVERE,"false:addfileentry,FromTimeException->operation again",e);
							System.out.println("第"+i+"个航班计划项出发时间不符合yyyy-MM-dd HH:mm的要求，请读入其他文件\n");
							flag=false;
							break;
						} catch (ToTimeException e) {
							i++;
							logger.log(Level.SEVERE,"false:addfileentry,ToTimeException->operation again",e);
							System.out.println("第"+i+"个航班计划项抵达时间不符合yyyy-MM-dd HH:mm的要求，请读入其他文件\n");
							flag=false;
							break;
						} catch (PlaneIdException e) {
							i++;
							logger.log(Level.SEVERE,"false:addfileentry,PlaneIdException->operation again",e);
							System.out.println("第"+i+"个航班计划项飞机编号不符合第一位为N或B，后面是四位数字的要求，请读入其他文件\n");
							flag=false;
							break;
						} catch (PlaneTypeException e) {
							i++;
							logger.log(Level.SEVERE,"false:addfileentry,PlaneTypeException->operation again",e);
							System.out.println("第"+i+"个航班计划项机型不符合大小写字母或数字构成，不含有空格或其他符号的要求，请读入其他文件\n");
							flag=false;
							break;
						} catch (PlaneSeatsException e) {
							i++;
							logger.log(Level.SEVERE,"false:addfileentry,PlaneSeatsException->operation again",e);
							System.out.println("第"+i+"个航班计划项座位数不符合正整数且范围为[50,600]的要求，请读入其他文件\n");
							flag=false;
							break;
						} catch (PlaneAgeException e) {
							i++;
							logger.log(Level.SEVERE,"false:addfileentry,PlaneAgeException->operation again",e);
							System.out.println("第"+i+"个航班计划项机龄不符合范围为[50,600]，最多为1位小数或无小数的要求，请读入其他文件\n");
							flag=false;
							break;
						}		
					}
					if(flag==false) {
						break;
					}
					else {          //对于符合要求的文件，依次读入航班计划项，并加入航班计划项集合中
						for(i=0;i<allfileentry.size();i++) {
							fileentry=allfileentry.get(i);
							a=new TwoLocationEntryImpl();
							b=new OneDistinguishResourceEntryImpl<Flight>();
							c=new NoBlockableEntryImpl();
							originflight=new FlightEntryFactory().getFlightEntry(a, b, c); //用工厂方法创建对象
							flight=(FlightEntry<Flight>)originflight;
							
							temp=tempparser.getAllinformation("Flight:", fileentry).split(",");  
							
							Calendar readytime= Calendar.getInstance(); 
							try {
								readytime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(temp[0]));
								tempname=temp[1];  //得到航班号
								flight.setplanningentryname(tempname);
								
								from=new FlightTrainLocation(weidu,jingdu,tempparser.getAllinformation("DepartureAirport:",fileentry)); //得到起点
								to=new FlightTrainLocation(weidu,jingdu,tempparser.getAllinformation("ArrivalAirport:",fileentry));  //得到终点
								try {
									flight.setlocations(from, to);
									logger.log(Level.INFO,"true:setlocation,计划项"+tempname);
								} catch (SameLocationException e2) {
									logger.log(Level.SEVERE,"false:setlocation,SameLocationException->operation again,计划项"+tempname,e2);
									System.out.println("存在两个相同飞机场位置");
									flag=false;
									break;
								}
								
								Calendar begintime= Calendar.getInstance(); 
								begintime.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(tempparser.getAllinformation("DepatureTime:",fileentry))); //起飞时间
								Calendar endtime= Calendar.getInstance(); 
								endtime.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(tempparser.getAllinformation("ArrivalTime:",fileentry)));  //抵达时间
								
								Timeslot mytime;
								try {
									mytime = new Timeslot(tempparser.getAllinformation("DepatureTime:",fileentry),tempparser.getAllinformation("ArrivalTime:",fileentry));
									flight.settimeslot(mytime);
									logger.log(Level.INFO,"true:settimeslot,计划项"+tempname);
								} catch (BeginEndTimeException e1) {
									logger.log(Level.SEVERE,"false:settimeslot,BeginEndTimeException->operation again,计划项"+tempname,e1);
									System.out.println("起始时间晚于终止时间，请读入其他文件\n");
									flag=false;
									break;
								}  //得到时间对
								String number=tempparser.getAllinformation("Plane:",fileentry);  //得到飞机的各种信息
								String type=tempparser.getAllinformation("Type:",fileentry);
								int seats= Integer.parseInt(tempparser.getAllinformation("Seats:",fileentry));
								Double age=Double.parseDouble(tempparser.getAllinformation("Age:",fileentry));
								
								myflight=new Flight(number,type,seats,age);
								flight.setresource(myflight);
								
								state = FlightWaitingState.instance;
								state=state.move('a');
								flight.setcurrentstate(state);
								
								//除去所有非法情况
								try {
									if(!((begintime.get(Calendar.YEAR)==(readytime.get(Calendar.YEAR))) 
											&&(begintime.get(Calendar.MONTH)==readytime.get(Calendar.MONTH))&&
											(begintime.get(Calendar.DAY_OF_MONTH)==readytime.get(Calendar.DAY_OF_MONTH)))) {
										throw new OneLineDiferDateException();
									}
								}catch(OneLineDiferDateException e) {
									logger.log(Level.SEVERE,"false:addfileentry,OneLineDiferDateException->operation again,计划项"+tempname,e);
									System.out.println("航班计划项"+tempname+"起飞日期与第一行日期不一致");
									flag=false;
								}	
								try {
									if((endtime.get(Calendar.YEAR)!=begintime.get(Calendar.YEAR))||
											(endtime.get(Calendar.MONTH)!=begintime.get(Calendar.MONTH))||
									       (endtime.get(Calendar.DAY_OF_MONTH)-begintime.get(Calendar.DAY_OF_MONTH)>1)){
										throw new MoreOneDayException();
									}
								}catch(MoreOneDayException e) {
									logger.log(Level.SEVERE,"false:addfileentry,MoreOneDayException->operation again,计划项"+tempname,e);
									System.out.println("航班计划项"+tempname+"降落日期与起飞日期相差超过一天");
									flag=false;
								}
								for(int m=0;m<tempflightlist.size();m++) {
									FlightEntry<Flight> tempflight=tempflightlist.get(m);
									try {
										if(((changeformat(tempflight.getplanningentryname()).equals(changeformat(flight.getplanningentryname()))))){
											if((begintime.get(Calendar.YEAR)==(tempflight.gettimeslot().getbegintime().get(Calendar.YEAR)))
													&&(begintime.get(Calendar.MONTH)==tempflight.gettimeslot().getbegintime().get(Calendar.MONTH))
													&&(begintime.get(Calendar.DAY_OF_MONTH)==tempflight.gettimeslot().getbegintime().get(Calendar.DAY_OF_MONTH))) {
												throw new SameDateFlightNumberException();
											}
											else if(!(tempflight.getfromlocation().getlocationname().equals(from.getlocationname())&&
													tempflight.gettolocation().getlocationname().equals(to.getlocationname())&&
													tempflight.gettimeslot().getbegintime().get(Calendar.HOUR_OF_DAY)==begintime.get(Calendar.HOUR_OF_DAY)&&
													tempflight.gettimeslot().getendtime().get(Calendar.HOUR_OF_DAY)==endtime.get(Calendar.HOUR_OF_DAY)&&
													tempflight.gettimeslot().getbegintime().get(Calendar.MINUTE)==begintime.get(Calendar.MINUTE)&&
													tempflight.gettimeslot().getendtime().get(Calendar.MINUTE)==endtime.get(Calendar.MINUTE))) {
												throw new DifferAirpotFromToTimeException();
											}
										}
										if(tempflight.getresource().getflightnumber().equals(flight.getresource().getflightnumber())) {
											if((!(tempflight.getresource().getflightage()-flight.getresource().getflightage()<0.0000001))||
													(!(tempflight.getresource().getflightallseat()==flight.getresource().getflightallseat()))||
													(!(tempflight.getresource().getflighttype().equals(flight.getresource().getflighttype())))) {
												throw new NoCompleteSamePlane();
											}
										}
									}catch(SameDateFlightNumberException e) {
										i++;
										logger.log(Level.SEVERE,"false:addfileentry,SameDateFlightNumberException->operation again",e);
										System.out.println("第"+i+"个航班计划项"+tempname+"与已存在列表中某计划项航班号和日期相同");
										flag=false;
										break;
									}catch(DifferAirpotFromToTimeException e) {
										i++;
										logger.log(Level.SEVERE,"false:addfileentry,DifferAirpotFromToTimeException->operation again",e);
										System.out.println("第"+i+"个航班计划项"+tempname+"与已存在列表中某计划项航班号相同。但出发机场"
												+ "和降落机场、出发和到达时间中出现了不同");
										flag=false;
										break;
									} catch (NoCompleteSamePlane e) {
										i++;
										logger.log(Level.SEVERE,"false:addfileentry,NoCompleteSamePlane->operation again",e);
										System.out.println("第"+i+"个航班计划项"+tempname+"与已存在列表中某计划项飞机编号相同。但飞机的类型、"
												+ "座位数、机龄中出现了不同");
										flag=false;
										break;
									}
								}
								if(flag==true) {
									tempflightlist.add(flight);
									qq++;
								}
								if(flag==false) {
									System.out.println("请读入其他文件\n");
									break;
								}
							} catch (ParseException e) {
								logger.log(Level.SEVERE,"ParseException->operation again",e);
								System.out.println("时间格式出错，请读入其他文件\n");
								break;
							} catch (LessThanZeroException e1) {
								System.out.println("座位数为非正数或机龄为负数，请读入其他文件\n");
							} 
						}
						//检测资源冲突的代码
						try {
							if(myapis.checkResourceExclusiveConflict(tempflightlist)) {
								throw new ResourceExclusiveConflictException();
							}
						}catch(ResourceExclusiveConflictException e) {
							logger.log(Level.SEVERE,"ResourceExclusiveConflictException->operation again",e);
							System.out.println("存在资源独占冲突，请读取其他文件\n");
							flag=false;
						}
						if(flag==false) {
							logger.log(Level.INFO,"false:addfileentry");
							tempflightlist=new ArrayList<FlightEntry<Flight>>();
						}
						if(flag==true) {
							logger.log(Level.INFO,"true:addfileentry");
							flightlist.addAll(tempflightlist);
							System.out.println("共读入"+qq+"个计划项，所有文件中计划项已录入飞机计划项集合中\n");
						}
					}
					break;
				case "10":
					System.out.println("对于位置独占冲突，你可以选择两种算法来进行判断(输入1使用第一种，输入2使用第二种)，请选择输入(eg 1)");
					tempname=scanner.nextLine();
					System.out.println("所有计划项中位置独占冲突结果如下：");
					if(myapis.checkLocationConflict(flightlist,tempname)) {
						logger.log(Level.INFO,"true:LocationConflict");
					}
					else {
						logger.log(Level.INFO,"false:LocationConflict");
					}
					System.out.println("所有计划项中资源独占冲突结果如下：");
					if(myapis.checkResourceExclusiveConflict(flightlist)) {
						logger.log(Level.INFO,"true:ResourceExclusiveConflict");
					}
					else {
						logger.log(Level.INFO,"false:ResourceExclusiveConflict");
					}
					System.out.println("\n");
					break;
				case "11":
					System.out.println("请输入你想要查看的飞机资源的飞机编号(eg N8981)：");
					tempname=scanner.nextLine();
					for(i=0;i<flightlist.size();i++) {
						if(flightlist.get(i).getresource().getflightnumber().equals(tempname)) {
							break;
						}
					}
					if(i==flightlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项拥有该资源");
						System.out.println();
						break;
					}
					tempentry=flightlist.get(i);
					myapis.findEntryPerResource(tempentry.getresource(), flightlist);
					logger.log(Level.INFO,"findEntryPerResource");
					System.out.println("\n");
					break;
				case "12":
					System.out.println("请输入你想要查看其前序计划项的航班号(eg AH3567)：");
					tempname=scanner.nextLine();
					for(i=0;i<flightlist.size();i++) {
						if(flightlist.get(i).getplanningentryname().equals(tempname)) {
							break;
						}
					}
					if(i==flightlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println();
						break;
					}
					tempentry=flightlist.get(i);
					myapis.findPreEntryPerResource(tempentry.getresource(),tempentry,flightlist);
					logger.log(Level.INFO,"findPreEntryPerResource");
					System.out.println("\n");
					break;
				case "13":
					System.out.println("请输入你想要展示当前时刻的信息版的位置(eg 北京)");
					tempname=scanner.nextLine();
					FlightEntryBoard flightboard=new FlightEntryBoard();	
					flightboard.setairportlocation(tempname);
					flightboard.getsortcomeentry(flightlist);
					flightboard.getsorttoentry(flightlist);
					try {
						flightboard.createFlightEntryBoard();
						logger.log(Level.INFO,"true:FlightEntryBoard");
					} catch (ParseException e) {
						logger.log(Level.SEVERE,"false:FlightEntryBoard,ParseException->operation again",e);
						System.out.println("时间格式出错");
						break;
					}
					flightboard.visualize();	
					System.out.println("\n");
					break;
				case "14":
					logger.log(Level.INFO,"true:get the number of allentrys");
					System.out.println("当前含有航班计划项的个数为："+flightlist.size()+"\n");
					break;
				case "15":
					fileHandler.close();
					try {
						thisfile = new BufferedReader(new FileReader("log/FlightScheduleLog.txt"));
					} catch (FileNotFoundException e1) {
						logger.log(Level.SEVERE,"false:findfile,FileNotFoundException->operation again",e1);
						System.out.println("文件不存在\n");
						break;
					}
					fileoneline=null;
					List<String> alllog=new ArrayList<>();
					String onelog="";
					try {
						while((fileoneline=thisfile.readLine())!=null) { 
							onelog=onelog+fileoneline+"\n"; //记得加上换行符
							if(fileoneline.contains("INFO")||fileoneline.equals("")) {
								alllog.add(onelog);
								onelog="";
							}
						}
					} catch (IOException e1) {
						System.out.println("读入文件异常\n");
					}finally{
						thisfile.close();
					}
					String mychoice;
					int ll;
					System.out.println("你可以选择三种过滤条件，第一种为按操作类型查询，第二种为按计划项名字查询，第三种为按照时间段查询(输入1使用第一种，输入2使用第二种，输入3使用第三种)，请选择输入(eg 1)");
					tempname=scanner.nextLine();
					if(tempname.equals("1")) {
						System.out.println("一共有以下操作类型，请任选一种进行查询：(eg setlocation)");
						System.out.println("setplanningentryname,setlocation,settimeslot,setresource,changelocation,changeresource,launch,finish"
								+ ",cancel,getcurrentstate,addfileentry,LocationConflict,ResourceExclusiveConflict,findEntryPerResource,findPreEntryPerResource,"
								+ "FlightEntryBoard,get the number of allentrys");
						mychoice=scanner.nextLine();
						ll=1;
						System.out.println("该操作的所有日志为：");
						for(String h:alllog) {
							if(h.contains(mychoice)) {
								 System.out.print("日志"+ll+"为："+h+"\n");
								 ll++;
							}
						}
						if(ll==1) {
							System.out.print("没有该操作的日志\n");
						}
					}
					if(tempname.equals("2")) {
						System.out.println("请输入计划项名字：(eg AH3567)");
						mychoice=scanner.nextLine();
						System.out.println("与该计划项有关的所有日志为：");
						ll=1;
						for(String h:alllog) {
							if(h.contains(mychoice)) {
								 System.out.print("日志"+ll+"为："+h+"\n");
								 ll++;
							}
						}
						if(ll==1) {
							System.out.print("没有该计划项的日志\n");
						}
					}
					if(tempname.equals("3")) {
						Pattern pattern1 = Pattern.compile("([A-Z|a-z][A-Z|a-z][A-Z|a-z])\\s(\\d{2}),\\s(\\d{4})\\s(\\d{2}|[1-9]):(\\d{2}):(\\d{2})\\s(PM|AM)");
						Matcher tomatcher;
						String month,day,year,hour,minute,panduan,time;
						System.out.println("请输入你想查找的时间段(用逗号隔开)(eg 2020-06-04 19:00,2020-06-04 19:01)：");
						try {
							temp=(scanner.nextLine()).split(",");
							Timeslot mytime=new Timeslot(temp[0],temp[1]);
							ll=1;
							System.out.println("该时间段的所有日志为：");
							for(String h:alllog) {
								tomatcher=pattern1.matcher(h);
								if(tomatcher.find()) {  //获得所有时间信息
									month=tomatcher.group(1);
									day=tomatcher.group(2);
									year=tomatcher.group(3);
									hour=tomatcher.group(4);
									minute=tomatcher.group(5);
									panduan=tomatcher.group(7);
									switch(month) {   //将月份转化为数字形式
									case "Jan":
										month="01";
										break;
									case "Feb":
										month="02";
										break;
									case "Mar":
										month="03";
										break;
									case "Apr":
										month="04";
										break;
									case "May":
										month="05";
										break;
									case "Jun":
										month="06";
										break;
									case "Jul":
										month="07";
										break;
									case "Aug":
										month="08";
										break;
									case "Sep":
										month="09";
										break;
									case "Oct":
										month="10";
										break;
									case "Nov":
										month="11";
										break;
									case "Dec":
										month="12";
										break;	
									}
									if(panduan.equals("PM")) {  //对于PM的，小时数加12
										hour=Integer.toString(Integer.parseInt(hour)+12);
									}
									if(panduan.equals("AM")&&Integer.parseInt(hour)<10) {  //对于AM的，小时数小于10的，前面加个0
										hour="0"+hour;
									}
									time=year+"-"+month+"-"+day+" "+hour+":"+minute;
									Calendar temptime= Calendar.getInstance(); 
									temptime.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(time));
									if(temptime.compareTo(mytime.getbegintime())>0&&temptime.compareTo(mytime.getendtime())<0) {  
										System.out.print("日志"+ll+"为："+h+"\n");
										ll++;
									}
									
								}
							}
							if(ll==1) {
								System.out.print("没有该时间段的日志\n");
							}
						}catch(ArrayIndexOutOfBoundsException e) {
							 System.out.println("未用,隔开，请重新执行操作，用正确格式输入\n");
							 break;
						}catch(ParseException e) {
							System.out.println("时间不符合yyyy-MM-dd HH:mm的要求，请重新执行操作，用正确格式输入\n");
							break;
						} catch (BeginEndTimeException e) {
							System.out.println("起始时间晚于终止时间，请重新执行操作，用正确格式输入\n");
							break;
						}
					}
					System.out.print("\n");
					break;
				case "16":
					logger.log(Level.INFO,"true:over program");
					fileHandler.close();
					System.out.println("日志已写入且程序已结束");
					System.exit(0);
					break;
				default:
					System.out.println("请输入正确指令\n");
					break;	
				}
				fileHandler.close();
			}
		} catch (SecurityException e3) {
			// TODO 自动生成的 catch 块
			e3.printStackTrace();
		} catch (IOException e3) {
			// TODO 自动生成的 catch 块
			e3.printStackTrace();
		}
	}
	
	/**
	 * 将航班号进行转换用于比较
	 * @param a 待转换航班号
	 * @return 转换后的航班号
	 */
	public static String changeformat(String a) {  //转换航班号进行判断
		if(a.length()==6) {
			return a;
		}
		else if(a.length()==4) {
			return a.substring(0, 2)+"00"+a.substring(2);	
		}
		else{
			return a.substring(0, 2)+"0"+a.substring(2);
		}
	}
}
