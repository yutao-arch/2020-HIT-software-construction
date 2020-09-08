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

import Board.CourseEntryBoard;
import EntryState.*;
import Exception.*;
import Factory.CourseEntryFactory;
import Location.CourseLocation;
import PlanningEntry.*;

import PlanningEntryAPIs.PlanningEntryAPIs;
import Resource.*;
import Timeslot.Timeslot;

public class CourseCalendarApp {

    private static List<CourseEntry<Teacher>> courselist=new ArrayList<>();  //储存所有CourseEntry的集合
    private final static Logger logger = Logger.getLogger(CourseCalendarApp.class.getName());
   	
    /**
     * 菜单
     */
	public static void menu() {
		System.out.println("1.创建一个新的课程计划项并设置信息(为了方便在信息版检测，设置时间时请设置为当天的时间)");
		System.out.println("2.为某个课程计划项分配资源");
		System.out.println("3.变更(删除位置后重新设置)某个课程计划项的位置");
		System.out.println("4.删除某个课程计划项的位置(删除位置后必须重新设置位置然后执行下面的步骤)");	
		System.out.println("5.变更某个课程计划项的资源");
		System.out.println("6.启动某个课程计划项");
		System.out.println("7.结束某个课程计划项");
		System.out.println("8.取消某个课程计划项");
		System.out.println("9.查看某个课程计划项的状态");
		System.out.println("****(注意：执行10,11,12的操作需要为每一个计划项分配资源后执行,否则异常)****");
		System.out.println("10.检测课程计划项中是否存在位置和资源独占冲突");
		System.out.println("11.针对某个老师资源，列出所有使用该资源的课程计划项");
		System.out.println("12.针对某个老师资源，选中含有该资源的某个课程计划项，列出它的前序计划项(输出一个即可)");
		System.out.println("13.选定某个位置，展示当前时刻该位置的信息版");
		System.out.println("14.显示当前含有课程计划项的个数");
		System.out.println("15.按照过滤条件进行日志查询");
		System.out.println("16.写入日志到文件并结束程序");
	}
	
	/**
	 * 
	 * @param args
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args)  {
		OneLocationEntryImpl a;
		OneDistinguishResourceEntryImpl<Teacher> b;
		NoBlockableEntryImpl c;
		String choice,weidu="北纬40度",jingdu="东经112度";
		Scanner scanner=new Scanner(System.in);
		CourseState state;
		Teacher myteacher;
		Calendar canceltimeone;
		PlanningEntryAPIs myapis=new PlanningEntryAPIs<>();
		System.out.println("最开始请执行第一步");
		
		File file=new File("log/CourseCalendarLog.txt");
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
				fileHandler = new FileHandler("log/CourseCalendarLog.txt", true);
				fileHandler.setFormatter(new SimpleFormatter());
				logger.addHandler(fileHandler);	
				logger.setUseParentHandlers(false);
				System.out.println("请输入你的操作：");
				choice=scanner.nextLine();
				switch(choice) {
				case "1":
					a=new OneLocationEntryImpl();
					b=new OneDistinguishResourceEntryImpl<Teacher>();
					c=new NoBlockableEntryImpl();
					PlanningEntry origincourse=new CourseEntryFactory().getCourseEntry(a, b, c); //用工厂方法创建对象
					CourseEntry<Teacher> course=(CourseEntry<Teacher>)origincourse;
					System.out.println("此课程计划项已经创建完成,下面输入一些信息完成初始状态的建立：");
					System.out.println("请输入课程名(eg 软件构造)：");
					String coursename=scanner.nextLine();
					course.setplanningentryname(coursename);
					logger.log(Level.INFO,"true:setplanningentryname");
					System.out.println("计划项名字设置成功");
					System.out.println("请输入教室名称(eg 正心楼32)：");
					String tempname;
					tempname=scanner.nextLine();
					CourseLocation from=new CourseLocation(weidu,jingdu,tempname);
					if(course.setlocations(from)) {
						logger.log(Level.INFO,"true:setlocation,计划项"+coursename);
					}
					else {
						logger.log(Level.INFO,"false:setlocation,计划项"+coursename);
					}
					System.out.println("请输入课程的上课和下课时间(用逗号隔开)(eg 2020-01-01 15:45,2020-01-01 17:30)：");
					try {
						temp=(scanner.nextLine()).split(",");
						Timeslot mytime=new Timeslot(temp[0],temp[1]);
						if(course.settimeslot(mytime)) {
							logger.log(Level.INFO,"true:settimeslot,计划项"+coursename);
						}
					}catch(ArrayIndexOutOfBoundsException e) {
						 logger.log(Level.SEVERE,"false:settimeslot,ArrayIndexOutOfBoundsException->operation again,计划项"+coursename,e);
						 System.out.println("未用,隔开，请重新执行1操作，用正确格式输入\n");
						 break;
					} catch (ParseException e) {
						logger.log(Level.SEVERE,"false:settimeslot,ParseException->operation again,计划项"+coursename,e);
						System.out.println("时间不符合yyyy-MM-dd HH:mm的要求，请重新执行1操作，用正确格式输入\n");
						break;
					} catch (BeginEndTimeException e) {
						logger.log(Level.SEVERE,"false:settimeslot,BeginEndTimeException->operation again,计划项"+coursename,e);
						System.out.println("起始时间晚于终止时间，请重新执行1操作，用正确格式输入\n");
						break;
					}
					state = CourseWaitingState.instance;
					course.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					List<CourseEntry<Teacher>> tempcourselist=new ArrayList<>();
					for(int oo=0;oo<courselist.size();oo++) {
						tempcourselist.add(courselist.get(oo).clone());
					}
					tempcourselist.add(course.clone());
					try {
						if(myapis.checkLocationConflict(tempcourselist,"1")) {
							throw new LocationConflictException();
						}
					}catch(LocationConflictException e) {
						logger.log(Level.SEVERE,"false:setlocation,LocationConflictException->operation again,计划项"+coursename,e);
						System.out.println("该位置分配后会与已有的其他计划项发生位置独占冲突，请重新执行1操作\n");
						break;
					}
					System.out.println("信息设置完成");
					courselist.add(course);
					System.out.println("\n");
					break;
				case "2":
					System.out.println("请输入你想加入资源的的课程名(eg 软件构造)：");
					tempname=scanner.nextLine();
					int i;
					for(i=0;i<courselist.size();i++) {
						if(courselist.get(i).getplanningentryname().equals(tempname) ){
							break;
						}
					}
					if(i==courselist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println();
						break;
					}
					tempcourselist=new ArrayList<>();
					for(int oo=0;oo<courselist.size();oo++) {
						tempcourselist.add(courselist.get(oo).clone());
					}
					CourseEntry<Teacher> tempentry=tempcourselist.get(i).clone();
					if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程未分配老师(Waiting)")) {
						System.out.println("请输入待加入老师的身份证号，姓名，性别，职称(eg 422823199812254452,余涛,男,讲师)：");
						try {
							temp=(scanner.nextLine()).split(",");
							myteacher=new Teacher(temp[0],temp[1],temp[2],temp[3]);
							tempentry.setresource(myteacher);
						}catch(ArrayIndexOutOfBoundsException e) {
							 logger.log(Level.SEVERE,"false:setresource,ArrayIndexOutOfBoundsException->operation again,计划项"+tempentry.getplanningentryname(),e);
							 System.out.println("未用,隔开，请重新执行2操作，用正确格式输入\n");
							 break;
						}	
						state=((CourseState)tempentry.getcurrentstate()).move('a');
						tempentry.setcurrentstate(state);
						System.out.println("计划项当前状态设置成功");
						tempcourselist.set(i, tempentry);
						try {
							if(myapis.checkResourceExclusiveConflict(tempcourselist)) {
								throw new ResourceExclusiveConflictException();
							}
							else {
								courselist.set(i, tempentry);
								logger.log(Level.INFO,"true:setresource,计划项"+tempentry.getplanningentryname());
								System.out.println("老师资源设置完成\n");
							}
						}catch(ResourceExclusiveConflictException e) {
							logger.log(Level.SEVERE,"false:setresource,ResourceExclusiveConflictException->operation again,计划项"+tempentry.getplanningentryname(),e);
							System.out.println("该资源分配后会与已有的其他计划项发生资源独占冲突，请重新分配资源\n");
						}
					}
					else {
						logger.log(Level.INFO,"false:no correct state->operation again,计划项"+tempentry.getplanningentryname());
						System.out.println("当前状态下不能执行设置老师资源操作\n");
					}
					break;
				case "3":
					System.out.println("请输入你想变更(重新设置)位置的课程名称(eg 软件构造)：");
					tempname=scanner.nextLine();
					for(i=0;i<courselist.size();i++) {
						if(courselist.get(i).getplanningentryname().equals(tempname) ){
							break;
						}
					}
					if(i==courselist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempcourselist=new ArrayList<>();
					for(int oo=0;oo<courselist.size();oo++) {
						tempcourselist.add(courselist.get(oo).clone());
					}
	                tempentry=tempcourselist.get(i).clone();
					if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程未分配老师(Waiting)")
							||((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程已分配老师(Allocated)")) {
						System.out.println("请输入变更后(重新设置的)位置教室的名称(eg 致知楼15)：");
						tempname=scanner.nextLine();
						from=new CourseLocation(weidu,jingdu,tempname);
						tempentry.changelocations(from);
						tempcourselist.set(i, tempentry);
						try {
							if(myapis.checkLocationConflict(tempcourselist,"1")) {
								throw new LocationConflictException();
							}
							else {
								logger.log(Level.INFO,"true:changelocation,计划项"+tempentry.getplanningentryname());
								courselist.set(i, tempentry);
								System.out.println("\n");
							}
						}catch(LocationConflictException e) {
							logger.log(Level.SEVERE,"false:changelocations,LocationConflictException->operation again,计划项"+tempentry.getplanningentryname(),e);
							System.out.println("该位置变更后会与已有的其他计划项发生位置独占冲突，请重新分配位置\n");
						}
					}
					else {
						logger.log(Level.INFO,"false:no correct state->operation again,计划项"+tempentry.getplanningentryname());
						System.out.println("当前状态下不能执行变更(重新设置)位置操作\n");
					}
					break;
				case "4":
					System.out.println("请输入你想删除位置的课程名称(eg 软件构造)：");
					tempname=scanner.nextLine();
					for(i=0;i<courselist.size();i++) {
						if(courselist.get(i).getplanningentryname().equals(tempname) ){
							break;
						}
					}
					if(i==courselist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=courselist.get(i);
					if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程未分配老师(Waiting)")
							||((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程已分配老师(Allocated)")) {
						System.out.println("请输入你想删除位置教室的名称(eg 致知楼15)：");
						tempname=scanner.nextLine();
						from=new CourseLocation(weidu,jingdu,tempname);
						try {
							for(int kk=0;kk<courselist.size();kk++) {
								if(courselist.get(kk).getlocations().equals(from)&&
										!((CourseState)courselist.get(kk).getcurrentstate()).getcoursestate().equals("课程已下课(Ended)")) {
									throw new NoendedLocationException();
								}
							}
						}catch(NoendedLocationException e) {
							logger.log(Level.SEVERE,"false:deletelocation,NoendedLocationException->operation again,计划项"+tempentry.getplanningentryname(),e);
							System.out.println("有尚未结束的计划项在该位置执行，无法删除该位置\n");
							break;
						}
						if(tempentry.deletelocations(from)) {
							logger.log(Level.INFO,"true:deletelocation,计划项"+tempentry.getplanningentryname());
						}
						else {
							logger.log(Level.INFO,"false:deletelocation,计划项"+tempentry.getplanningentryname());
							break;
						}
						courselist.set(i, tempentry);
						System.out.println("\n");
					}
					else {
						logger.log(Level.INFO,"false:no correct state->operation again,计划项"+tempentry.getplanningentryname());
						System.out.println("当前状态下不能执行删除位置操作\n");
					}
					break;
				case "5":
					System.out.println("请输入你想变更资源的课程名称(eg 软件构造)：");
					tempname=scanner.nextLine();
					for(i=0;i<courselist.size();i++) {
						if(courselist.get(i).getplanningentryname().equals(tempname)) {
							break;
						}
					}
					if(i==courselist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println();
						break;
					}
					tempentry=courselist.get(i);
					if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程已分配老师(Allocated)")) {
						System.out.println("请输入新老师的身份证号，姓名，性别，职称(eg 422823199812257777,王宁,男,讲师)：");
						try {
							temp=(scanner.nextLine()).split(",");
							myteacher=new Teacher(temp[0],temp[1],temp[2],temp[3]);
							if(tempentry.changeresource(myteacher)) {
								logger.log(Level.INFO,"true:changeresource,计划项"+tempentry.getplanningentryname());
							}
							else {
								logger.log(Level.INFO,"false:changeresource,计划项"+tempentry.getplanningentryname());
								break;
							}
						}catch(ArrayIndexOutOfBoundsException e) {
							 logger.log(Level.SEVERE,"false:changeresource,ArrayIndexOutOfBoundsException->operation again,计划项"+tempentry.getplanningentryname(),e);
							 System.out.println("未用,隔开，请重新执行5操作，用正确格式输入\n");
							 //e.printStackTrace();
							 break;
						}	
						courselist.set(i, tempentry);
						System.out.println("\n");
					}
					else {
						logger.log(Level.INFO,"false:no correct state->operation again,计划项"+tempentry.getplanningentryname());
						System.out.println("当前状态下不能执行变更资源操作\n");
					}
					break;
				case "6":
					System.out.println("请输入你想要启动的课程计划项的课程名称(eg 软件构造)：");
					tempname=scanner.nextLine();
					for(i=0;i<courselist.size();i++) {
						if(courselist.get(i).getplanningentryname().equals(tempname)) {
							break;
						}
					}
					if(i==courselist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=courselist.get(i);
					if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程已分配老师(Allocated)")) {	
						System.out.println("请输入指令：启动");
						yixie=scanner.nextLine();
						if(tempentry.launch(yixie)) {
						logger.log(Level.INFO,"true:launch,计划项"+tempentry.getplanningentryname());
						state=((CourseState)tempentry.getcurrentstate()).move('a');
						tempentry.setcurrentstate(state);
						System.out.println("计划项当前状态设置成功");
						courselist.set(i, tempentry);
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
						System.out.println("当前状态下不能执行上课操作\n");
					}
					break;
				case "7":
					System.out.println("请输入你想要结束的课程计划项的课程名称(eg 软件构造)：");
					tempname=scanner.nextLine();
					for(i=0;i<courselist.size();i++) {
						if(courselist.get(i).getplanningentryname().equals(tempname)) {
							break;
						}
					}
					if(i==courselist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=courselist.get(i);
					if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程已开始上课(Running)")) {
						System.out.println("请输入指令：结束");
						yixie=scanner.nextLine();
						if(tempentry.finish(yixie)) {
						logger.log(Level.INFO,"true:finish,计划项"+tempentry.getplanningentryname());
						state=((CourseState)tempentry.getcurrentstate()).move('a');
						tempentry.setcurrentstate(state);
						System.out.println("计划项当前状态设置成功");
						courselist.set(i, tempentry);
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
						System.out.println("当前状态下不能执行下课操作\n");
					}
					break;
				case "8":
					System.out.println("请输入你想要取消的课程计划项的课程名称(eg 软件构造)：");
					tempname=scanner.nextLine();
					for(i=0;i<courselist.size();i++) {
						if(courselist.get(i).getplanningentryname().equals(tempname)) {
							break;
						}
					}
					if(i==courselist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=courselist.get(i);
					try {
						if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程未分配老师(Waiting)")
								||((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程已分配老师(Allocated)")) {
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
							System.out.println("该课程计划项取消的时间为"+str);
							state=((CourseState)tempentry.getcurrentstate()).move('b');
							tempentry.setcurrentstate(state);
							System.out.println("计划项当前状态设置成功");
							courselist.remove(i);
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
						System.out.println("课程已上课，无法取消\n");
						break;
					}

				case "9":
					System.out.println("请输入你想要查看的课程计划项的课程名称(eg 软件构造)：");
					tempname=scanner.nextLine();
					for(i=0;i<courselist.size();i++) {
						if(courselist.get(i).getplanningentryname().equals(tempname)) {
							break;
						}
					}
					if(i==courselist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项或者已被取消");
						System.out.println();
						break;
					}
					tempentry=courselist.get(i);
					System.out.println("该课程计划项的状态为："+((CourseState)tempentry.getcurrentstate()).getcoursestate());
					logger.log(Level.INFO,"true:getcurrentstate,计划项"+tempentry.getplanningentryname());
					System.out.println("\n");
					break;
				case "10":
					System.out.println("对于位置独占冲突，你可以选择两种算法来进行判断(输入1使用第一种，输入2使用第二种)，请选择输入(eg 1)");
					tempname=scanner.nextLine();
					System.out.println("所有计划项中位置独占冲突结果如下：");
					if(myapis.checkLocationConflict(courselist,tempname)) {
						logger.log(Level.INFO,"true:LocationConflict");
					}
					else {
						logger.log(Level.INFO,"false:LocationConflict");
					}
					System.out.println("所有计划项中资源独占冲突结果如下：");
					if(myapis.checkResourceExclusiveConflict(courselist)) {
						logger.log(Level.INFO,"true:ResourceExclusiveConflict");
					}
					else {
						logger.log(Level.INFO,"false:ResourceExclusiveConflict");
					}
					System.out.println("\n");
					break;
				case "11":
					System.out.println("请输入你想要查看的老师资源的姓名(eg 余涛)：");
					tempname=scanner.nextLine();
					for(i=0;i<courselist.size();i++) {
						if(courselist.get(i).getresource().getteachername().equals(tempname)) {
							break;
						}
					}
					if(i==courselist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项拥有该资源");
						System.out.println("\n");
						break;
					}
					tempentry=courselist.get(i);
					myapis.findEntryPerResource(tempentry.getresource(), courselist);
					logger.log(Level.INFO,"findEntryPerResource");
					System.out.println("\n");
					break;
				case "12":
					System.out.println("请输入你想要查看其前序计划项的课程名称(eg 软件构造)：");
					tempname=scanner.nextLine();
					for(i=0;i<courselist.size();i++) {
						if(courselist.get(i).getplanningentryname().equals(tempname)) {
							break;
						}
					}
					if(i==courselist.size()) {
						logger.log(Level.INFO,"false:can not find this entry->operation again");
						System.out.println("没有该计划项");
						System.out.println("\n");
						break;
					}
					tempentry=courselist.get(i);
					myapis.findPreEntryPerResource(tempentry.getresource(),tempentry,courselist);
					logger.log(Level.INFO,"findPreEntryPerResource");
					System.out.println("\n");
					break;
				case "13":
					System.out.println("请输入你想要展示当前时刻的信息版的位置(eg 正心楼32)");
					tempname=scanner.nextLine();
					CourseEntryBoard flightboard=new CourseEntryBoard();
					flightboard.setclassroomlocation(tempname);
					flightboard.getsortallentry(courselist);
					try {
						flightboard.createCourseEntryBoard();
						logger.log(Level.INFO,"true:CourseEntryBoard");
					} catch (ParseException e) {
						logger.log(Level.SEVERE,"false:CourseEntryBoard,ParseException->operation again",e);
						System.out.println("时间格式出错");
						break;
					}
					flightboard.visualize();
					System.out.println("\n");
					break;
				case "14":
					logger.log(Level.INFO,"true:get the number of allentrys");
					 System.out.println("当前含有课程计划项的个数为："+courselist.size()+"\n");
		 			break;
				case "15":
					fileHandler.close();
					BufferedReader thisfile;
					String fileoneline;
					try {
						thisfile = new BufferedReader(new FileReader("log/CourseCalendarLog.txt"));
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
						System.out.println("setplanningentryname,setlocation,settimeslot,setresource,changelocation,deletelocation,changeresource,launch,finish"
								+ ",cancel,getcurrentstate,LocationConflict,ResourceExclusiveConflict,findEntryPerResource,findPreEntryPerResource,"
								+ "CourseEntryBoard,get the number of allentrys");
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
						System.out.println("请输入计划项名字：(eg 软件构造)");
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
			
		}catch (SecurityException e3) {
			// TODO 自动生成的 catch 块
			e3.printStackTrace();
		} catch (IOException e1) {
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		} 		
	}
}
