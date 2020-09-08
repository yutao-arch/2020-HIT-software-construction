package App;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import Board.FlightEntryBoard;
import EntryState.*;
import Factory.FlightEntryFactory;
import Location.*;
import Parser.Parser;
import PlanningEntry.*;
import PlanningEntryAPIs.PlanningEntryAPIs;
import Resource.Flight;
import Timeslot.*;

public class FlightScheduleApp {

	private static List<FlightEntry<Flight>> flightlist=new ArrayList<>(); //储存所有FlightEntry的集合
	
	
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
	}
	
	/**
	 * 
	 * @param args
	 * @throws ParseException 时间格式不为yyyy-MM-dd HH:mm
	 * @throws IOException 文件读入出问题
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParseException, IOException {
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
		
		while(true) {
			String[] temp;
			String yixie;
			menu();
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
				System.out.println("请输入起点站名称(eg 北京)：");
				String tempname;
				tempname=scanner.nextLine();
				FlightTrainLocation from=new FlightTrainLocation(weidu,jingdu,tempname);
				System.out.println("请输入终点站名称(eg 武汉)：");
				tempname=scanner.nextLine();
				FlightTrainLocation to=new FlightTrainLocation(weidu,jingdu,tempname);
				if(flight.setlocations(from, to)) {
					System.out.println("位置设置成功");
				}
				System.out.println("请输入起飞和降落时间(用逗号隔开)(eg 2020-01-01 12:00,2020-01-01 14:00)：");
				try {
					temp=(scanner.nextLine()).split(",");
					Timeslot mytime=new Timeslot(temp[0],temp[1]);
					if(flight.settimeslot(mytime)) {
						System.out.println("一个时间对设置成功");
					}
				}catch(ArrayIndexOutOfBoundsException e) {
					 System.out.println("未用,隔开，请重新执行1操作，用正确格式输入\n");
					 //e.printStackTrace();
					 break;
				}
				//temp=(scanner.nextLine()).split(",");
				//Timeslot mytime=new Timeslot(temp[0],temp[1]);
				//if(flight.settimeslot(mytime)) {
				//	System.out.println("一个时间对设置成功");
				//}
				System.out.println("请输入航班号(eg AH3567)：");
				String flightname=scanner.nextLine();
				flight.setplanningentryname(flightname);
			    System.out.println("计划项名字设置成功");
				state = FlightWaitingState.instance;
				flight.setcurrentstate(state);
				System.out.println("计划项当前状态设置成功");
				System.out.println("信息设置完成\n");
				flightlist.add(flight);
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
					System.out.println("没有该计划项");
					System.out.println();
					break;
				}
				FlightEntry<Flight> tempentry=flightlist.get(i);
				if(((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班未分配飞机(Waiting)")) {
					System.out.println("请输入待加入飞机编号，机型号，座位数，机龄(eg N8981,C88,100,2.5)：");
					try {
						temp=(scanner.nextLine()).split(",");
						myflight=new Flight(temp[0],temp[1],Integer.parseInt(temp[2]),Double.parseDouble(temp[3]));
						if(tempentry.setresource(myflight)) {
							 System.out.println("航班设置成功");
						}
					}catch(ArrayIndexOutOfBoundsException e) {
						 System.out.println("未用,隔开，请重新执行2操作，用正确格式输入\n");
						 //e.printStackTrace();
						 break;
					}
					//temp=(scanner.nextLine()).split(",");
					//myflight=new Flight(temp[0],temp[1],Integer.parseInt(temp[2]),Double.parseDouble(temp[3]));
					//if(tempentry.setresource(myflight)) {
					//	 System.out.println("航班设置成功");
					//}
					state=((FlightState)tempentry.getcurrentstate()).move('a');
					tempentry.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					flightlist.set(i, tempentry);
					System.out.println("飞机资源设置完成\n");
				}
				else {
					System.out.println("当前状态下不能执行设置飞机资源操作\n");
				}
				break;
			case "3":
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
						tempentry.changeresource(myflight);
					}catch(ArrayIndexOutOfBoundsException e) {
						 System.out.println("未用,隔开，请重新执行4操作，用正确格式输入\n");
						 //e.printStackTrace();
						 break;
					}
					//temp=(scanner.nextLine()).split(",");
					//myflight=new Flight(temp[0],temp[1],Integer.parseInt(temp[2]),Double.parseDouble(temp[3]));
					//tempentry.changeresource(myflight);
					flightlist.set(i, tempentry);
					System.out.println("\n");
				}
				else {
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
					System.out.println("没有该计划项");
					System.out.println("\n");
					break;
				}
				tempentry=flightlist.get(i);
				if(((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班已分配飞机(Allocated)")) {	
					System.out.println("请输入指令：启动");
					yixie=scanner.nextLine();
					if(tempentry.launch(yixie)) {
					state=((FlightState)tempentry.getcurrentstate()).move('a');
					tempentry.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					flightlist.set(i, tempentry);
					System.out.println("\n");
					}
					else {
						System.out.println("\n");
						break;
					}
				}
				else {
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
					System.out.println("没有该计划项");
					System.out.println("\n");
					break;
				}
				tempentry=flightlist.get(i);
				if(((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班已起飞(Running)")) {
					System.out.println("请输入指令：结束");
					yixie=scanner.nextLine();
					if(tempentry.finish(yixie)) {
					state=((FlightState)tempentry.getcurrentstate()).move('a');
					tempentry.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					flightlist.set(i, tempentry);
					System.out.println("\n");
					}
					else {
						System.out.println("\n");
						break;
					}
				}
				else {
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
					System.out.println("没有该计划项");
					System.out.println("\n");
					break;
				}
				tempentry=flightlist.get(i);
				if(((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班未分配飞机(Waiting)")
						||((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班已分配飞机(Allocated)")) {
					System.out.println("请输入指令：取消");
					yixie=scanner.nextLine();
					if(tempentry.cancel(yixie)) {
					canceltimeone=Calendar.getInstance();
					String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(canceltimeone.getTime()); 
					canceltimeone.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(str));
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
					System.out.println("航班已起飞，无法取消\n");
				}
				break;
			case "8":
				System.out.println("请输入你想要查看的航班计划项的航班号(eg AH3567)：");
				tempname=scanner.nextLine();
				for(i=0;i<flightlist.size();i++) {
					if(flightlist.get(i).getplanningentryname().equals(tempname)) {
						break;
					}
				}
				if(i==flightlist.size()) {
					System.out.println("没有该计划项或者已被取消");
					System.out.println();
					break;
				}
				tempentry=flightlist.get(i);
				System.out.println("该航班计划项的状态为："+((FlightState)tempentry.getcurrentstate()).getflightstate());
				System.out.println("\n");
				break;
			case "9":
				System.out.println("下面提供五个文件可供读取，文件名分别为：FlightSchedule_1.txt,FlightSchedule_2.txt,FlightSchedule_3.txt"
						+ "FlightSchedule_4.txt,FlightSchedule_5.txt");
				System.out.println("请输入你想要读取航班计划项的文件名(eg FlightSchedule_1.txt)：");
				tempname=scanner.nextLine();
				BufferedReader thisfile=new BufferedReader(new FileReader("src/txt/"+tempname));
				i=0;
				int qq=0;
				fileentry="";
				allfileentry=new ArrayList<>();
				Parser tempparser=new Parser();
				List<FlightEntry<Flight>> tempflightlist=new ArrayList<>();
				boolean flag=true;
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
				for(i=0;i<allfileentry.size();i++) {  //依次检查每个字符串是否符合格式要求
					flag=tempparser.checkwhethercorrect(allfileentry.get(i));
					if(flag==false) {
						break;
					}
				}
				if(flag==false) {
					System.out.println("文件中的航班信息字符串不符合要求,请读入其他文件\n");
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
						readytime.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(temp[0])); //得到计划起飞日期
						
						tempname=temp[1];  //得到航班号
						flight.setplanningentryname(tempname);
						
						from=new FlightTrainLocation(weidu,jingdu,tempparser.getAllinformation("DepartureAirport:",fileentry)); //得到起点
						to=new FlightTrainLocation(weidu,jingdu,tempparser.getAllinformation("ArrivalAirport:",fileentry));  //得到终点
						flight.setlocations(from, to);
						
						Calendar begintime= Calendar.getInstance(); 
						begintime.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(tempparser.getAllinformation("DepatureTime:",fileentry))); //起飞时间
						Calendar endtime= Calendar.getInstance(); 
						endtime.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(tempparser.getAllinformation("ArrivalTime:",fileentry)));  //抵达时间
						
						Timeslot mytime=new Timeslot(tempparser.getAllinformation("DepatureTime:",fileentry),tempparser.getAllinformation("ArrivalTime:",fileentry));  //得到时间对
						flight.settimeslot(mytime);
						
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
						if(!((begintime.get(Calendar.YEAR)==(readytime.get(Calendar.YEAR))) 
								&&(begintime.get(Calendar.MONTH)==readytime.get(Calendar.MONTH))&&
								(begintime.get(Calendar.DAY_OF_MONTH)==readytime.get(Calendar.DAY_OF_MONTH)))) {
							System.out.println("航班计划项"+tempname+"起飞日期与第一行日期不一致");
							flag=false;
						}
				     	if((((endtime.getTime().getTime()-begintime.getTime().getTime())/1000/60)>1440)) {
							System.out.println("航班计划项"+tempname+"降落日期与起飞日期相差超过一天");
							flag=false;
						}
						for(int m=0;m<tempflightlist.size();m++) {
							FlightEntry<Flight> tempflight=tempflightlist.get(m);
							if(((changeformat(tempflight.getplanningentryname()).equals(changeformat(flight.getplanningentryname()))))){
									if((begintime.get(Calendar.YEAR)==(tempflight.gettimeslot().getbegintime().get(Calendar.YEAR)))
											&&(begintime.get(Calendar.MONTH)==tempflight.gettimeslot().getbegintime().get(Calendar.MONTH))
											&&(begintime.get(Calendar.DAY_OF_MONTH)==tempflight.gettimeslot().getbegintime().get(Calendar.DAY_OF_MONTH))) {
										System.out.println("第"+i+"个航班计划项"+tempname+"与已存在列表中某计划项航班号和日期相同");
										flag=false;
										break;
									}
									else if(!(tempflight.getfromlocation().getlocationname().equals(from.getlocationname())&&
											tempflight.gettolocation().getlocationname().equals(to.getlocationname())&&
											tempflight.gettimeslot().getbegintime().get(Calendar.HOUR_OF_DAY)==begintime.get(Calendar.HOUR_OF_DAY)&&
											tempflight.gettimeslot().getendtime().get(Calendar.HOUR_OF_DAY)==endtime.get(Calendar.HOUR_OF_DAY)&&
											tempflight.gettimeslot().getbegintime().get(Calendar.MINUTE)==begintime.get(Calendar.MINUTE)&&
											tempflight.gettimeslot().getendtime().get(Calendar.MINUTE)==endtime.get(Calendar.MINUTE))) {
										System.out.println("第"+i+"个航班计划项"+tempname+"与已存在列表中某计划项航班号相同。但出发机场"
												+ "和降落机场、出发和到达时间中出现了不同");
										flag=false;
										break;
									}
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
					}
					if(flag==false) {
						tempflightlist=new ArrayList<FlightEntry<Flight>>();
					}
					if(flag==true) {
						flightlist.addAll(tempflightlist);
						System.out.println("共读入"+qq+"个计划项，所有文件中计划项已录入飞机计划项集合中\n");
					}
					thisfile.close();
				}
				break;
			case "10":
				System.out.println("对于位置独占冲突，你可以选择两种算法来进行判断(输入1使用第一种，输入2使用第二种)，请选择输入(eg 1)");
				tempname=scanner.nextLine();
				System.out.println("所有计划项中位置独占冲突结果如下：");
				myapis.checkLocationConflict(flightlist,tempname);
				System.out.println("所有计划项中资源独占冲突结果如下：");
				myapis.checkResourceExclusiveConflict(flightlist);
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
					System.out.println("没有该计划项拥有该资源");
					System.out.println();
					break;
				}
				tempentry=flightlist.get(i);
				myapis.findEntryPerResource(tempentry.getresource(), flightlist);
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
					System.out.println("没有该计划项");
					System.out.println();
					break;
				}
				tempentry=flightlist.get(i);
				myapis.findPreEntryPerResource(tempentry.getresource(),tempentry,flightlist);
				System.out.println("\n");
				break;
			case "13":
				System.out.println("请输入你想要展示当前时刻的信息版的位置(eg 北京)");
				tempname=scanner.nextLine();
				FlightEntryBoard flightboard=new FlightEntryBoard();	
				flightboard.setairportlocation(tempname);
				flightboard.getsortcomeentry(flightlist);
				flightboard.getsorttoentry(flightlist);
				flightboard.createFlightEntryBoard();
				flightboard.visualize();	
				System.out.println("\n");
				break;
			case "14":
				System.out.println("当前含有航班计划项的个数为："+flightlist.size()+"\n");
				break;
			default:
				System.out.println("请输入正确指令\n");
				break;	
				
			}
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
