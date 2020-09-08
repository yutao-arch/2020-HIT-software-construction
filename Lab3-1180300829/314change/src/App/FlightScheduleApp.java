package App;


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
		System.out.println("5.(重新)启动某个航班计划项");
		System.out.println("6.结束某个航班计划项");
		System.out.println("7.取消某个航班计划项");
		System.out.println("8.查看某个航班计划项的状态");
		System.out.println("9.阻塞某个航班计划项");
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
		MultipleLacationEntryImpl a;
		OneDistinguishResourceEntryImpl<Flight> b;
		BlockableEntryImpl c;
		String choice,weidu="北纬40度",jingdu="东经112度";
		Scanner scanner=new Scanner(System.in);
		FlightState state;
		Flight myflight;
		Calendar canceltimeone;
		PlanningEntryAPIs myapis=new PlanningEntryAPIs<>();
		String fileentry,fileoneline;
		int blockflag;
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
				a=new MultipleLacationEntryImpl();
				b=new OneDistinguishResourceEntryImpl<Flight>();
				c=new BlockableEntryImpl();
				PlanningEntry originflight=new FlightEntryFactory().getFlightEntry(a, b, c); //用工厂方法创建对象
				FlightEntry<Flight> flight=(FlightEntry<Flight>)originflight;
				System.out.println("此航班计划项已经创建完成,下面输入一些信息完成初始状态的建立：");
				List<Location> alllocation=new ArrayList<>();
				int ll=0;
				System.out.println("请依次输入该行程所有机场名称，只能输入三个(每输入一个按一下回车)(eg 北京)：");
				String tempname;
				while(ll<3) {
					tempname=scanner.nextLine();
					FlightTrainLocation from=new FlightTrainLocation(weidu,jingdu,tempname);
					alllocation.add(from);
					ll++;
				}
                flight.setlocations(alllocation);
                List<Timeslot> alltime=new ArrayList<>();
                boolean hh=false;
				System.out.println("请依次输入该行程所有时间段(时间段数应该比飞机场数少一)(用逗号隔开，每输入一对按一下回车)(eg 2020-01-01 12:00,2020-01-01 14:00)：");
				for(int pp=0;pp<ll-1;pp++) {
					try {
						tempname=scanner.nextLine();
						temp=tempname.split(",");
						Timeslot mytime=new Timeslot(temp[0],temp[1]);
						alltime.add(mytime);
					}catch(ArrayIndexOutOfBoundsException e) {
						 System.out.println("未用,隔开，请重新执行1操作，用正确格式输入\n");
						 hh=true;
						 break;
					}
				}
				if(hh==true) {
					break;
				}
				flight.settimeslot(alltime);
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
						 break;
					}
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
						 break;
					}
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
				if(((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班已分配飞机(Allocated)")||
						((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班中途阻塞(Blocked)")) {	
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
						||((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班已分配飞机(Allocated)")||
						((FlightState)tempentry.getcurrentstate()).getflightstate().equals("航班中途阻塞(Blocked)")) {
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
				System.out.println("请输入你想要阻塞的航班计划项的航班号(eg AH3567)：");
				tempname=scanner.nextLine();
				for(i=0;i<flightlist.size();i++) {
					if(flightlist.get(i).getplanningentryname().equals(tempname) ){
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
					System.out.println("请输入你需要阻塞的飞机场位置：(eg 武汉)");
					yixie=scanner.nextLine();
					blockflag=tempentry.trainblock(yixie);
					if(blockflag==-1) {
						break;
					}
					state=((FlightState)tempentry.getcurrentstate()).move('b');
					tempentry.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					flightlist.set(i, tempentry);
					System.out.println("\n");
				}
				else {
					System.out.println("当前状态下不能执行阻塞高铁操作\n");
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
