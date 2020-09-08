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

import Board.TrainEntryBoard;
import EntryState.*;
import Exception.*;
import Factory.TrainEntryFactory;
import Location.*;
import PlanningEntry.BlockableEntryImpl;
import PlanningEntry.MultipleLacationEntryImpl;
import PlanningEntry.MultipleSortedResourceEntryImpl;
import PlanningEntry.PlanningEntry;
import PlanningEntry.TrainEntry;
import PlanningEntryAPIs.PlanningEntryAPIs;

import Resource.Carriage;
import Timeslot.Timeslot;

public class TrainScheduleApp {

    private static List<TrainEntry<Carriage>> trainlist=new ArrayList<>(); //储存所有TrainEntry的集合
    private final static Logger logger = Logger.getLogger(TrainScheduleApp.class.getName());
	
	 /**
     * 菜单
     */
	public static void menu() {
		System.out.println("1.创建一个新的高铁计划项并设置信息(为了方便在信息版检测，设置时间时请设置为当前时间一小时前后的时间)");
		System.out.println("2.为某个高铁计划项分配资源");
		System.out.println("3.变更某个高铁计划项的位置");
		System.out.println("4.变更某个高铁计划项的资源");
		System.out.println("5.增加某个高铁计划项的资源");
		System.out.println("6.删除某个高铁计划项的资源");
		System.out.println("7.(重新)启动某个高铁计划项");
		System.out.println("8.阻塞某个高铁计划项");
		System.out.println("9.结束某个高铁计划项");
		System.out.println("10.取消某个高铁计划项");
		System.out.println("11.查看某个高铁计划项的状态");
		System.out.println("****(注意：执行12,13,14的操作需要为每一个计划项分配资源后执行,否则异常)****");
		System.out.println("12.检测高铁计划项中是否存在位置和资源独占冲突");
		System.out.println("13.针对某个车厢资源，列出所有使用该资源的高铁计划项");
		System.out.println("14.针对某个车厢资源，选中含有该资源的某个高铁计划项，列出它的前序计划项(输出一个即可)");
		System.out.println("15.选定某个位置，展示当前时刻该位置的信息版");
		System.out.println("16.显示当前含有高铁计划项的个数");
		System.out.println("17.按照过滤条件进行日志查询");
		System.out.println("18.写入日志并结束程序");
	}
	
	/**
	 * 
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		MultipleLacationEntryImpl a;
		MultipleSortedResourceEntryImpl<Carriage> b;
		BlockableEntryImpl c;
		String choice,weidu="北纬40度",jingdu="东经112度";
		Scanner scanner=new Scanner(System.in);
		TrainState state;
		Carriage mycarriage;
		List<Carriage> allcarriage=new ArrayList<>();
		Calendar canceltimeone;
		PlanningEntryAPIs myapis=new PlanningEntryAPIs<>();
		int blockflag;
		String[] temp;
		String yixie;
		System.out.println("最开始请执行第一步");
		
		File file=new File("log/TrainScheduleLog.txt");
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
				menu();
				Locale.setDefault(new Locale("en", "EN"));
				logger.setLevel(Level.INFO);
				FileHandler fileHandler;	
				fileHandler = new FileHandler("log/TrainScheduleLog.txt", true);
				fileHandler.setFormatter(new SimpleFormatter());
				logger.addHandler(fileHandler);	
				logger.setUseParentHandlers(false);
				System.out.println("请输入你的操作：");
				choice=scanner.nextLine();
				switch(choice) {
				case "1":
					a=new MultipleLacationEntryImpl();
					b=new MultipleSortedResourceEntryImpl<Carriage>();
					c=new BlockableEntryImpl();
					PlanningEntry origintrain=new TrainEntryFactory().getTrainEntry(a, b, c); //用工厂方法创建对象
					TrainEntry<Carriage> train=(TrainEntry<Carriage>)origintrain;
					System.out.println("此高铁计划项已经创建完成,下面输入一些信息完成初始状态的建立：");
					System.out.println("请输入车次号(eg G1020)：");
					String trainname=scanner.nextLine();
					train.setplanningentryname(trainname);
					logger.log(Level.INFO,"true:setplanningentryname,计划项"+trainname);
					System.out.println("计划项名字设置成功");
					List<Location> alllocation=new ArrayList<>();
					int ll=0;
					System.out.println("请依次输入该行程所有高铁站名称，输入“结束”停止输入(每输入一个按一下回车)(eg 北京)：");
					String tempname;
					while(!(tempname=scanner.nextLine()).equals("结束")) {
						FlightTrainLocation from=new FlightTrainLocation(weidu,jingdu,tempname);
						alllocation.add(from);
						ll++;
					}
	                try {
	                	if(train.setlocations(alllocation)){
	                		logger.log(Level.INFO,"true:setlocation,计划项"+trainname);
	                	}
					} catch (SameLocationException e1) {
						logger.log(Level.SEVERE,"flase:setlocation,SameLocationException->operation again,计划项"+trainname,e1);
						System.out.println("存在重复位置，请重新执行1操作，用正确格式输入\n");
						break;
					}
	                List<Timeslot> alltime=new ArrayList<>();
	                boolean hh=false;
					System.out.println("请依次输入该行程所有时间段(时间段数应该比高铁站数少一)(用逗号隔开，每输入一对按一下回车)(eg 2020-01-01 12:00,2020-01-01 14:00)：");
					for(int pp=0;pp<ll-1;pp++) {
						try {
							tempname=scanner.nextLine();
							temp=tempname.split(",");
							Timeslot mytime=new Timeslot(temp[0],temp[1]);
							alltime.add(mytime);
						}catch(ArrayIndexOutOfBoundsException e) {
							 logger.log(Level.SEVERE,"flase:settimeslot,ArrayIndexOutOfBoundsException->operation again,计划项"+trainname,e);
							 System.out.println("未用,隔开，请重新执行1操作，用正确格式输入\n");
							 hh=true;
							 break;
						} catch (ParseException e) {
							logger.log(Level.SEVERE,"flase:settimeslot,ParseException->operation again,计划项"+trainname,e);
							System.out.println("时间不符合yyyy-MM-dd HH:mm的要求，请重新执行1操作，用正确格式输入\n");
							break;
						} catch (BeginEndTimeException e) {
							logger.log(Level.SEVERE,"flase:settimeslot,BeginEndTimeException->operation again,计划项"+trainname,e);
							System.out.println("某个时间段起始时间晚于终止时间，请重新执行1操作，用正确格式输入\n");
							hh=true;
							break;
						}	
					}
					if(hh==true) {
						break;
					}
					try {
						if(train.settimeslot(alltime)) {
							logger.log(Level.INFO,"true:settimeslot,计划项"+trainname);
						}
					} catch (ConflictTimeException e2) {
						logger.log(Level.SEVERE,"flase:settimeslot,ConflictTimeException->operation again,计划项"+trainname,e2);
						System.out.println("某个站抵达时间晚于出发时间，请重新执行1操作，用正确格式输入\n");
						break;
					}
					state = TrainWaitingState.instance;
					train.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					System.out.println("信息设置完成");
					trainlist.add(train);
					System.out.println("\n");
					break;
				case "2":
					System.out.println("请输入你想加入资源的高铁计划项的车次号(eg G1020)：");
					tempname=scanner.nextLine();
					int i;
					for(i=0;i<trainlist.size();i++) {
						if(trainlist.get(i).getplanningentryname().equals(tempname) ){
							break;
						}
					}
					if(i==trainlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					List<TrainEntry<Carriage>> temptrainlist=new ArrayList<>();
					for(int oo=0;oo<trainlist.size();oo++) {
						temptrainlist.add(trainlist.get(oo).clone());
					}
					TrainEntry<Carriage> tempentry=trainlist.get(i).clone();
					boolean gg=false;
					allcarriage=new ArrayList<>();
					if(((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁未分配车厢(Waiting)")) {
						System.out.println("请按照次序依次输入一组车厢的编号(不同车厢不同)，类型，定员数，出厂年份，输入“结束”停止输入(用逗号隔开，每输入一个车厢按一下回车)(eg A01,一等座,100,2012)：");
						while(!(tempname=scanner.nextLine()).equals("结束")) {
							try {
								temp=tempname.split(",");
								mycarriage=new Carriage(temp[0],temp[1],Integer.parseInt(temp[2]),temp[3]);
								allcarriage.add(mycarriage);
							}catch(ArrayIndexOutOfBoundsException e) {
								 logger.log(Level.SEVERE,"flase:setresource,ArrayIndexOutOfBoundsException->operation again,计划项"+tempentry.getplanningentryname(),e);
								 System.out.println("未用,隔开，请重新执行2操作，用正确格式输入\n");
								 gg=true;
								 break;
							} catch (NumberFormatException e) {
								// TODO 自动生成的 catch 块
								e.printStackTrace();
							} catch (LessThanZeroException e) {
								logger.log(Level.SEVERE,"flase:setresource,LessThanZeroException->operation again,计划项"+tempentry.getplanningentryname(),e);
								 System.out.println("定员数为非正数，请重新执行2操作，用正确格式输入\n");
								 gg=true;
								 break;
							}
						}
						if(gg==true) {
							break;
						}
						try {
							tempentry.setresource(allcarriage);
						} catch (SameResourceException e1) {
							 logger.log(Level.SEVERE,"flase:setresource,SameResourceException->operation again,计划项"+tempentry.getplanningentryname(),e1);
							 System.out.println("存在相同的车厢，请重新执行2操作，用正确格式输入\n");
							 break;
						}
						state=((TrainState)tempentry.getcurrentstate()).move('a');
						tempentry.setcurrentstate(state);
						System.out.println("计划项当前状态设置成功");
						temptrainlist.set(i, tempentry);
						try {
							if(myapis.checkResourceExclusiveConflict(temptrainlist)) {
								throw new ResourceExclusiveConflictException();
							}
							else {
								trainlist.set(i, tempentry);
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
					System.out.println("高铁计划项位置不可变更");
					System.out.println();
					break;
				case "4":	
					System.out.println("请输入你想变更资源的高铁计划项的车次号(eg G1020)：");
					tempname=scanner.nextLine();
					for(i=0;i<trainlist.size();i++) {
						if(trainlist.get(i).getplanningentryname().equals(tempname) ){
							break;
						}
					}
					if(i==trainlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=trainlist.get(i);
					allcarriage=tempentry.getresource();
					int j;
					if(((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁已分配一组车厢(Allocated)")) {
						System.out.println("请输入你需要变更的车厢的编号(eg A01)：");
						tempname=scanner.nextLine();
						for(j=0;j<allcarriage.size();j++) {
							if(allcarriage.get(j).getcarriagenumber().equals(tempname)) {
								break;
							}
						}
						if(j==allcarriage.size()) {
							logger.log(Level.INFO,"false:can not find this carriage->operation again");
							System.out.println("没有该车厢");
							System.out.println("\n");
							break;
						}
						Carriage tempcarriage=allcarriage.get(j);
						System.out.println("请输入变更后的车厢的编号，类型，定员数，出厂年份(用逗号隔开)(eg NB02,二等座,100,2011)：");
						try {
							temp=(scanner.nextLine()).split(",");
							mycarriage=new Carriage(temp[0],temp[1],Integer.parseInt(temp[2]),temp[3]);
							if(tempentry.changeresource(tempcarriage,mycarriage)) {
								logger.log(Level.INFO,"true:changeresource,计划项"+tempentry.getplanningentryname());
							}
							else {
								logger.log(Level.INFO,"false:changeresource,计划项"+tempentry.getplanningentryname());
								break;
							}
							trainlist.set(i, tempentry);
						}catch(ArrayIndexOutOfBoundsException e) {
							 logger.log(Level.SEVERE,"flase:changeresource,ArrayIndexOutOfBoundsException->operation again,计划项"+tempentry.getplanningentryname(),e);
							 System.out.println("未用,隔开，请重新执行4操作，用正确格式输入\n");
							 break;
						} catch (NumberFormatException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						} catch (LessThanZeroException e) {
							 logger.log(Level.SEVERE,"flase:changeresource,LessThanZeroException->operation again,计划项"+tempentry.getplanningentryname(),e);
							 System.out.println("定员数为非正数，请重新执行4操作，用正确格式输入\n");
							 break;
						}
					}
					else {
						logger.log(Level.INFO,"false:no correct state->operation again,计划项"+tempentry.getplanningentryname());
						System.out.println("当前状态下不能执行变更车厢操作\n");
					}
					break;
				case "5":
					System.out.println("请输入你想增加资源的高铁计划项的车次号(eg G1020)：");
					tempname=scanner.nextLine();
					for(i=0;i<trainlist.size();i++) {
						if(trainlist.get(i).getplanningentryname().equals(tempname) ){
							break;
						}
					}
					if(i==trainlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=trainlist.get(i);
					allcarriage=tempentry.getresource();
					if(((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁已分配一组车厢(Allocated)")) {
						System.out.println("请输入增加的车厢的编号，类型，定员数，出厂年份(用逗号隔开)(eg AS02,二等座,100,2011)：");
						try {
							temp=(scanner.nextLine()).split(",");
							mycarriage=new Carriage(temp[0],temp[1],Integer.parseInt(temp[2]),temp[3]);
							System.out.println("请输入增加的车厢在一组车厢中的位置(第一节车厢默认位置为1)(eg 3)：");
							tempname=scanner.nextLine();
							int weizhi=Integer.parseInt(tempname);
							if(weizhi>tempentry.getresource().size()+1||weizhi<1) {
								logger.log(Level.INFO,"false:addresource,计划项"+tempentry.getplanningentryname());
								System.out.println("增加的车厢位置不合法\n");
								break;
							}
							if(tempentry.addresource(mycarriage,weizhi-1)) {
								logger.log(Level.INFO,"true:addresource,计划项"+tempentry.getplanningentryname());
							}
							else {
								logger.log(Level.INFO,"false:addresource,计划项"+tempentry.getplanningentryname());
							}
							trainlist.set(i, tempentry);
						}catch(ArrayIndexOutOfBoundsException e) {
							 logger.log(Level.SEVERE,"flase:addresource,ArrayIndexOutOfBoundsException->operation again,计划项"+tempentry.getplanningentryname(),e);
							 System.out.println("未用,隔开，请重新执行5操作，用正确格式输入\n");
							 break;
						} catch (NumberFormatException e) {
							// TODO 自动生成的 catch 块
							e.printStackTrace();
						} catch (LessThanZeroException e) {
							 logger.log(Level.SEVERE,"flase:addresource,LessThanZeroException->operation again,计划项"+tempentry.getplanningentryname(),e);
							 System.out.println("定员数为非正数，请重新执行5操作，用正确格式输入\n");
							 break;
						}
					}
					else {
						logger.log(Level.INFO,"false:no correct state->operation again,计划项"+tempentry.getplanningentryname());
						System.out.println("当前状态下不能执行增加资源操作\n");
					}
					break;
				case "6":
					System.out.println("请输入你想删除资源的高铁计划项的车次号(eg G1020)：");
					tempname=scanner.nextLine();
					for(i=0;i<trainlist.size();i++) {
						if(trainlist.get(i).getplanningentryname().equals(tempname) ){
							break;
						}
					}
					if(i==trainlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=trainlist.get(i);
					allcarriage=tempentry.getresource();
					if(((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁已分配一组车厢(Allocated)")) {
						System.out.println("请输入你需要删除的车厢的编号(eg A01)：");
						tempname=scanner.nextLine();
						for(j=0;j<allcarriage.size();j++) {
							if(allcarriage.get(j).getcarriagenumber().equals(tempname)) {
								break;
							}
						}
						if(j==allcarriage.size()) {
							logger.log(Level.INFO,"false:can not find this carriage->operation again");
							System.out.println("没有该车厢");
							System.out.println("\n");
							break;
						}
						Carriage tempcarriage=allcarriage.get(j);
						try {
							for(int kk=0;kk<trainlist.size();kk++) {
								if(trainlist.get(kk).getresource().contains(tempcarriage)&&
										!((TrainState)trainlist.get(kk).getcurrentstate()).gettrainstate().equals("高铁已抵达终点站(Ended)")) {
									throw new NoendedCarriageException();
								}
							}
						}catch(NoendedCarriageException e) {
							logger.log(Level.SEVERE,"deleteresource,NoendedCarriageException->operation again,计划项"+tempentry.getplanningentryname(),e);
							System.out.println("有尚未结束的计划项正在占用该资源，无法删除该资源\n");
							break;
						}
						if(tempentry.deleteresource(tempcarriage)) {
							logger.log(Level.INFO,"true:deleteresource,计划项"+tempentry.getplanningentryname());
						}
						else {
							logger.log(Level.INFO,"false:deleteresource,计划项"+tempentry.getplanningentryname());
						}
						trainlist.set(i, tempentry);
					}
					else {
						logger.log(Level.INFO,"false:no correct state->operation again,计划项"+tempentry.getplanningentryname());
						System.out.println("当前状态下不能执行删除资源操作\n");
					}
					break;
				case "7":
					System.out.println("请输入你想要启动的高铁计划项的车次号(eg G1020)：");
					tempname=scanner.nextLine();
					for(i=0;i<trainlist.size();i++) {
						if(trainlist.get(i).getplanningentryname().equals(tempname) ){
							break;
						}
					}
					if(i==trainlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=trainlist.get(i);
					if(((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁已分配一组车厢(Allocated)")||
							((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁中途阻塞(Blocked)")) {	
						System.out.println("请输入指令：启动");
						yixie=scanner.nextLine();
						if(tempentry.launch(yixie)) {
						logger.log(Level.INFO,"true:launch,计划项"+tempentry.getplanningentryname());
						state=((TrainState)tempentry.getcurrentstate()).move('a');
						tempentry.setcurrentstate(state);
						System.out.println("计划项当前状态设置成功");
						trainlist.set(i, tempentry);
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
						System.out.println("当前状态下不能执行启动高铁操作\n");
					}
					break;
				case "8":
					System.out.println("请输入你想要阻塞的高铁计划项的车次号(eg G1020)：");
					tempname=scanner.nextLine();
					for(i=0;i<trainlist.size();i++) {
						if(trainlist.get(i).getplanningentryname().equals(tempname) ){
							break;
						}
					}
					if(i==trainlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=trainlist.get(i);
					if(((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁已从起始站发车(Running)")) {	
						System.out.println("请输入你需要阻塞的高铁站位置：(eg 武汉)");
						yixie=scanner.nextLine();
						blockflag=tempentry.trainblock(yixie);
						if(blockflag==-1) {
							logger.log(Level.INFO,"false:trainblock,计划项"+tempentry.getplanningentryname());
							break;
						}
						logger.log(Level.INFO,"true:trainblock,计划项"+tempentry.getplanningentryname());
						state=((TrainState)tempentry.getcurrentstate()).move('b');
						tempentry.setcurrentstate(state);
						System.out.println("计划项当前状态设置成功");
						trainlist.set(i, tempentry);
						System.out.println("\n");
					}
					else {
						logger.log(Level.INFO,"false:no correct state->operation again,计划项"+tempentry.getplanningentryname());
						System.out.println("当前状态下不能执行阻塞高铁操作\n");
					}
					break;
				case "9":
					System.out.println("请输入你想要结束的高铁计划项的车次号(eg G1020)：");
					tempname=scanner.nextLine();
					for(i=0;i<trainlist.size();i++) {
						if(trainlist.get(i).getplanningentryname().equals(tempname) ){
							break;
						}
					}
					if(i==trainlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=trainlist.get(i);
						if(((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁已从起始站发车(Running)")) {	
							System.out.println("请输入指令：结束");
							yixie=scanner.nextLine();
							if(tempentry.finish(yixie)) {
							logger.log(Level.INFO,"true:finish,计划项"+tempentry.getplanningentryname());
							state=((TrainState)tempentry.getcurrentstate()).move('a');
							tempentry.setcurrentstate(state);
							System.out.println("计划项当前状态设置成功");
							trainlist.set(i, tempentry);
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
							System.out.println("当前状态下不能执行结束高铁操作\n");
							break;
						}	
					break;
				case "10":
					System.out.println("请输入你想要取消的高铁计划项的车次号(eg G1020)：");
					tempname=scanner.nextLine();
					for(i=0;i<trainlist.size();i++) {
						if(trainlist.get(i).getplanningentryname().equals(tempname) ){
							break;
						}
					}
					if(i==trainlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=trainlist.get(i);
					try {
						if(((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁已分配一组车厢(Allocated)")||
								((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁中途阻塞(Blocked)")||
								((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁未分配车厢(Waiting)")) {	
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
									state=((TrainState)tempentry.getcurrentstate()).move('b');
									tempentry.setcurrentstate(state);
									System.out.println("计划项当前状态设置成功");
									trainlist.remove(i);
									System.out.println("\n");
									}
						}
						else {
							throw new NoCancelStateException();
						}	
					}catch(NoCancelStateException e) {
						logger.log(Level.SEVERE,"false:cancel,NoCancelStateException->operation again,计划项"+tempentry.getplanningentryname(),e);
						System.out.println("当前状态下不能执行取消高铁操作\n");
						break;
					}
					break;
	             case "11":
	            	System.out.println("请输入你想要查看的高铁计划项的车次号(eg G1020)：");
	 				tempname=scanner.nextLine();
	 				for(i=0;i<trainlist.size();i++) {
						if(trainlist.get(i).getplanningentryname().equals(tempname) ){
							break;
						}
					}
					if(i==trainlist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项或者已被取消");
						System.out.println("\n");
						break;
					}
					tempentry=trainlist.get(i);
	 				System.out.println("该高铁计划项的状态为："+((TrainState)tempentry.getcurrentstate()).gettrainstate());
	 				logger.log(Level.INFO,"true:getcurrentstate,计划项"+tempentry.getplanningentryname());
	 				System.out.println("\n");
					break;
	             case "12":
	            	System.out.println("对于位置独占冲突，你可以选择两种算法来进行判断(输入1使用第一种，输入2使用第二种)，请选择输入(eg 1)");
	    			tempname=scanner.nextLine();
	            	System.out.println("所有计划项中位置独占冲突结果如下：");
	    			if(myapis.checkLocationConflict(trainlist,tempname)) {
	    				logger.log(Level.INFO,"true:LocationConflict");
	    			}
	    			else {
	    				logger.log(Level.INFO,"false:LocationConflict");
	    			}
	    			System.out.println("所有计划项中资源独占冲突结果如下：");
	    			if(myapis.checkResourceExclusiveConflict(trainlist)) {
	    				logger.log(Level.INFO,"true:ResourceExclusiveConflict");
	    			}
	    			else {
	    				logger.log(Level.INFO,"false:ResourceExclusiveConflict");
	    			}
	    			System.out.println("\n");
	            	 break;
	             case "13":
	            	System.out.println("请输入你想要查看的车厢资源的车厢的编号，类型，定员数，出厂年份(用逗号隔开)(eg A01,二等座,100,2011)：");
	            	try {
	            		temp=(scanner.nextLine()).split(",");
	    				mycarriage=new Carriage(temp[0],temp[1],Integer.parseInt(temp[2]),temp[3]);
	    				myapis.findEntryPerResource(mycarriage, trainlist);
		 				logger.log(Level.INFO,"findEntryPerResource");
					}catch(ArrayIndexOutOfBoundsException e) {
						logger.log(Level.SEVERE,"flase:setresource,ArrayIndexOutOfBoundsException->operation again");
						 System.out.println("未用,隔开，请重新执行13操作，用正确格式输入\n");
						 break;
					} catch (NumberFormatException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					} catch (LessThanZeroException e) {
						logger.log(Level.SEVERE,"flase:setresource,LessThanZeroException->operation again");
						 System.out.println("定员数为非正数，请重新执行13操作，用正确格式输入\n");
						 break;
					}
	 				System.out.println("\n");
	            	break;
	             case "14":
	            	System.out.println("请输入你想要查看其前序计划项的车次号(eg G1020)：");
	            	tempname=scanner.nextLine();
	  				for(i=0;i<trainlist.size();i++) {
	 					if(trainlist.get(i).getplanningentryname().equals(tempname) ){
	 						break;
	 					}
	 				}
	 				if(i==trainlist.size()) {
	 					logger.log(Level.INFO,"false:can not find this entry->operation again");
	 					System.out.println("没有该计划项");
	 					System.out.println("\n");
	 					break;
	 				}
	 				tempentry=trainlist.get(i);
	 				System.out.println("请输入需要查看前序计划项的该高铁计划项中拥有的车厢资源的编号(eg A01)：");
	 				tempname=scanner.nextLine();
	 				for(j=0;j<tempentry.getresource().size();j++) {
	 					if(tempentry.getresource().get(j).getcarriagenumber().equals(tempname)) {
	 						break;
	 					}
	 				}
	 				if(j==tempentry.getresource().size()) {
	 					logger.log(Level.INFO,"false:can not find this carriage->operation again");
	 					System.out.println("没有该车厢资源");
	 					System.out.println("\n");
	 					break;
	 				}
	 				mycarriage=tempentry.getresource().get(j);
	                myapis.findPreEntryPerResource(mycarriage,tempentry,trainlist);	
	                logger.log(Level.INFO,"findPreEntryPerResource");
	                System.out.println("\n");	
	            	break;
	             case "15":
	            	System.out.println("请输入你想要展示当前时刻的信息版的位置(eg 北京)");
	    			tempname=scanner.nextLine();
	    			TrainEntryBoard trainboard=new TrainEntryBoard();	
	    			trainboard.setrailwaylocation(tempname);
	    			trainboard.getsortcomeentry(trainlist);
	    			trainboard.getsorttoentry(trainlist);
	    			try {
						trainboard.createTrainEntryBoard();
						logger.log(Level.INFO,"true:TrainEntryBoard");
					} catch (ParseException e) {
						logger.log(Level.SEVERE,"false:FlightEntryBoard,ParseException->operation again",e);
						System.out.println("时间格式出错");
						break;
					}
	    			trainboard.visualize();	
	    			System.out.println("\n");
	            	 break;
	             case "16":
	            	logger.log(Level.INFO,"true:get the number of allentrys");
	                System.out.println("当前含有高铁计划项的个数为："+trainlist.size()+"\n");
	 				break;
	             case "17":
	            	 fileHandler.close();
						BufferedReader thisfile;
						String fileoneline;
						try {
							thisfile = new BufferedReader(new FileReader("log/TrainScheduleLog.txt"));
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
						int lll;
						System.out.println("你可以选择三种过滤条件，第一种为按操作类型查询，第二种为按计划项名字查询，第三种为按照时间段查询(输入1使用第一种，输入2使用第二种，输入3使用第三种)，请选择输入(eg 1)");
						tempname=scanner.nextLine();
						if(tempname.equals("1")) {
							System.out.println("一共有以下操作类型，请任选一种进行查询：(eg setlocation)");
							System.out.println("setplanningentryname,setlocation,settimeslot,setresource,changelocation,changeresource,addresource,deleteresource,launch,trainblock,finish"
									+ ",cancel,getcurrentstate,LocationConflict,ResourceExclusiveConflict,findEntryPerResource,findPreEntryPerResource,"
									+ "TrainEntryBoard,get the number of allentrys");
							mychoice=scanner.nextLine();
							lll=1;
							System.out.println("该操作的所有日志为：");
							for(String h:alllog) {
								if(h.contains(mychoice)) {
									 System.out.print("日志"+lll+"为："+h+"\n");
									 lll++;
								}
							}
							if(lll==1) {
								System.out.print("没有该操作的日志\n");
							}
						}
						if(tempname.equals("2")) {
							System.out.println("请输入计划项名字：(eg G1020)");
							mychoice=scanner.nextLine();
							System.out.println("与该计划项有关的所有日志为：");
							lll=1;
							for(String h:alllog) {
								if(h.contains(mychoice)) {
									 System.out.print("日志"+lll+"为："+h+"\n");
									 lll++;
								}
							}
							if(lll==1) {
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
								lll=1;
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
											System.out.print("日志"+lll+"为："+h+"\n");
											lll++;
										}
										
									}
								}
								if(lll==1) {
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
	         	 case "18":
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
		}catch (SecurityException e3) {
			e3.printStackTrace();
		} catch (IOException e3) {
			e3.printStackTrace();
		}
	}
}
