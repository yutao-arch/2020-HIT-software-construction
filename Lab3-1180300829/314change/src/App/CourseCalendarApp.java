package App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import Board.CourseEntryBoard;
import EntryState.*;
import Factory.CourseEntryFactory;
import Location.CourseLocation;
import PlanningEntry.*;

import PlanningEntryAPIs.PlanningEntryAPIs;
import Resource.*;
import Timeslot.Timeslot;

public class CourseCalendarApp {

    private static List<CourseEntry<Teacher>> courselist=new ArrayList<>();  //储存所有CourseEntry的集合
   	
    /**
     * 菜单
     */
	public static void menu() {
		System.out.println("1.创建一个新的课程计划项并设置信息(为了方便在信息版检测，设置时间时请设置为当天的时间)");
		System.out.println("2.为某个课程计划项分配资源");
		System.out.println("3.变更(删除位置后重新设置)某个课程计划项的位置");
		System.out.println("4.删除某个课程计划项的位置(删除位置后必须重新设置位置然后执行下面的步骤)");	
		System.out.println("5.变更某个课程计划项的资源");
		System.out.println("6.增加某个课程计划项的资源");
		System.out.println("7.删除某个课程计划项的资源");
		System.out.println("8.启动某个课程计划项");
		System.out.println("9.结束某个课程计划项");
		System.out.println("10.取消某个课程计划项");
		System.out.println("11.查看某个课程计划项的状态");
		System.out.println("****(注意：执行12,13,14的操作需要为每一个计划项分配资源后执行,否则异常)****");
		System.out.println("12.检测课程计划项中是否存在位置和资源独占冲突");
		System.out.println("13.针对某个老师资源，列出所有使用该资源的课程计划项");
		System.out.println("14.针对某个老师资源，选中含有该资源的某个课程计划项，列出它的前序计划项(输出一个即可)");
		System.out.println("15.选定某个位置，展示当前时刻该位置的信息版");
		System.out.println("16.显示当前含有课程计划项的个数");
	}
	
	/**
	 * 
	 * @param args
	 * @throws ParseException 时间格式不为yyyy-MM-dd HH:mm
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParseException {
		OneLocationEntryImpl a;
		MultipleSortedResourceEntryImpl<Teacher> b;
		NoBlockableEntryImpl c;
		String choice,weidu="北纬40度",jingdu="东经112度";
		Scanner scanner=new Scanner(System.in);
		CourseState state;
		List<Teacher> allteacher;
		Teacher myteacher;
		Calendar canceltimeone;
		PlanningEntryAPIs myapis=new PlanningEntryAPIs<>();
		System.out.println("最开始请执行第一步");
		
		while(true) {
			String[] temp;
			String yixie;
			menu();
			System.out.println("请输入你的操作：");
			choice=scanner.nextLine();
			int j;
			switch(choice) {
			case "1":
				a=new OneLocationEntryImpl();
				b=new MultipleSortedResourceEntryImpl<Teacher>();
				c=new NoBlockableEntryImpl();
				PlanningEntry origincourse=new CourseEntryFactory().getCourseEntry(a, b, c); //用工厂方法创建对象
				CourseEntry<Teacher> course=(CourseEntry<Teacher>)origincourse;
				System.out.println("此课程计划项已经创建完成,下面输入一些信息完成初始状态的建立：");
				System.out.println("请输入教室名称(eg 正心楼32)：");
				String tempname;
				tempname=scanner.nextLine();
				CourseLocation from=new CourseLocation(weidu,jingdu,tempname);
				course.setlocations(from);
				System.out.println("请输入课程的上课和下课时间(用逗号隔开)(eg 2020-01-01 15:45,2020-01-01 17:30)：");
				try {
					temp=(scanner.nextLine()).split(",");
					Timeslot mytime=new Timeslot(temp[0],temp[1]);
					course.settimeslot(mytime);
				}catch(ArrayIndexOutOfBoundsException e) {
					 System.out.println("未用,隔开，请重新执行1操作，用正确格式输入\n");
					 //e.printStackTrace();
					 break;
				}
				//temp=(scanner.nextLine()).split(",");
				//Timeslot mytime=new Timeslot(temp[0],temp[1]);
				//course.settimeslot(mytime);
				System.out.println("请输入课程名(eg 软件构造)：");
				String coursename=scanner.nextLine();
				course.setplanningentryname(coursename);
				 System.out.println("计划项名字设置成功");
				state = CourseWaitingState.instance;
				course.setcurrentstate(state);
				System.out.println("信息设置完成\n");
				courselist.add(course);
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
					System.out.println("没有该计划项");
					System.out.println();
					break;
				}
				CourseEntry<Teacher> tempentry=courselist.get(i);
				allteacher=new ArrayList<>();
				boolean gg=false;
				if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程未分配老师(Waiting)")) {
					System.out.println("请按照次序依次输入待加入老师的身份证号，姓名，性别，职称，输入“结束”停止输入(eg 422823199812254452,余涛,男,讲师)：");
					while(!(tempname=scanner.nextLine()).equals("结束")) {
						try {
							temp=tempname.split(",");
							myteacher=new Teacher(temp[0],temp[1],temp[2],temp[3]);
							allteacher.add(myteacher);
						}catch(ArrayIndexOutOfBoundsException e) {
							 System.out.println("未用,隔开，请重新执行2操作，用正确格式输入\n");
							 gg=true;
							 break;
						}
					}
					if(gg==true) {
						break;
					}
					
					tempentry.setresource(allteacher);
					state=((CourseState)tempentry.getcurrentstate()).move('a');
					tempentry.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					courselist.set(i, tempentry);
					System.out.println("\n");
				}
				else {
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
					System.out.println("没有该计划项");
					System.out.println("\n");
					break;
				}
				tempentry=courselist.get(i);
				if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程未分配老师(Waiting)")
						||((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程已分配老师(Allocated)")) {
					System.out.println("请输入你想变更(重新设置)位置教室的名称(eg 致知楼15)：");
					tempname=scanner.nextLine();
					from=new CourseLocation(weidu,jingdu,tempname);
					tempentry.changelocations(from);
					courselist.set(i, tempentry);
					System.out.println("\n");
				}
				else {
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
					tempentry.deletelocations(from);
					courselist.set(i, tempentry);
					System.out.println("\n");
				}
				else {
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
					System.out.println("没有该计划项");
					System.out.println();
					break;
				}
				tempentry=courselist.get(i);
				allteacher=tempentry.getresource();
				if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程已分配老师(Allocated)")) {
					System.out.println("请输入你需要变更的老师的姓名(eg 余涛)：");
					tempname=scanner.nextLine();
					for(j=0;j<allteacher.size();j++) {
						if(allteacher.get(j).getteachername().equals(tempname)) {
							break;
						}
					}
					if(j==allteacher.size()) {
						System.out.println("没有该老师");
						System.out.println("\n");
						break;
					}
					Teacher tempcarriage=allteacher.get(j);
					System.out.println("请输入变更后的老师的身份证号，姓名，性别，职称，输入“结束”停止输入(eg 422823199812251134,王宁,男,讲师)：");
					try {
						temp=(scanner.nextLine()).split(",");
						myteacher=new Teacher(temp[0],temp[1],temp[2],temp[3]);
					}catch(ArrayIndexOutOfBoundsException e) {
						 System.out.println("未用,隔开，请重新执行4操作，用正确格式输入\n");
						 //e.printStackTrace();
						 break;
					}
					tempentry.changeresource(tempcarriage,myteacher);
					courselist.set(i, tempentry);
					System.out.println("\n");
				}
				else {
					System.out.println("当前状态下不能执行变更资源操作\n");
				}
				break;
			case "6":
				System.out.println("请输入你想增加资源的课程名称(eg 软件构造)：");
				tempname=scanner.nextLine();
				for(i=0;i<courselist.size();i++) {
					if(courselist.get(i).getplanningentryname().equals(tempname) ){
						break;
					}
				}
				if(i==courselist.size()) {
					System.out.println("没有该计划项");
					System.out.println("\n");
					break;
				}
				tempentry=courselist.get(i);
				allteacher=tempentry.getresource();
				if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程已分配老师(Allocated)")) {
					System.out.println("请输入增加的老师的身份证号，姓名，性别，职称，输入“结束”停止输入(eg 422823199812251134,王宁,男,讲师)：");
					try {
						temp=(scanner.nextLine()).split(",");
						myteacher=new Teacher(temp[0],temp[1],temp[2],temp[3]);
					}catch(ArrayIndexOutOfBoundsException e) {
						 System.out.println("未用,隔开，请重新执行5操作，用正确格式输入\n");
						 //e.printStackTrace();
						 break;
					}
					System.out.println("请输入增加的老师在一组老师中的位置(第一个老师默认位置为1)(eg 3)：");
					tempname=scanner.nextLine();
					int weizhi=Integer.parseInt(tempname);
					if(weizhi>tempentry.getresource().size()+1||weizhi<1) {
						System.out.println("增加的老师位置不合法\n");
						break;
					}
					tempentry.addresource(myteacher,weizhi-1);
					courselist.set(i, tempentry);
				}
				else {
					System.out.println("当前状态下不能执行增加资源操作\n");
				}
				break;
			case "7":
				System.out.println("请输入你想删除资源的课程名称(eg 软件构造)：");
				tempname=scanner.nextLine();
				for(i=0;i<courselist.size();i++) {
					if(courselist.get(i).getplanningentryname().equals(tempname) ){
						break;
					}
				}
				if(i==courselist.size()) {
					System.out.println("没有该计划项");
					System.out.println("\n");
					break;
				}
				tempentry=courselist.get(i);
				allteacher=tempentry.getresource();
				if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程已分配老师(Allocated)")) {
					System.out.println("请输入你需要删除的老师的姓名(eg 余涛)：");
					tempname=scanner.nextLine();
					for(j=0;j<allteacher.size();j++) {
						if(allteacher.get(j).getteachername().equals(tempname)) {
							break;
						}
					}
					if(j==allteacher.size()) {
						System.out.println("没有该老师");
						System.out.println("\n");
						break;
					}
					Teacher tempcarriage=allteacher.get(j);
					tempentry.deleteresource(tempcarriage);
					courselist.set(i, tempentry);
				}
				else {
					System.out.println("当前状态下不能执行删除资源操作\n");
				}
				break;
			case "8":
				System.out.println("请输入你想要启动的课程计划项的课程名称(eg 软件构造)：");
				tempname=scanner.nextLine();
				for(i=0;i<courselist.size();i++) {
					if(courselist.get(i).getplanningentryname().equals(tempname)) {
						break;
					}
				}
				if(i==courselist.size()) {
					System.out.println("没有该计划项");
					System.out.println("\n");
					break;
				}
				tempentry=courselist.get(i);
				if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程已分配老师(Allocated)")) {	
					System.out.println("请输入指令：启动");
					yixie=scanner.nextLine();
					if(tempentry.launch(yixie)) {
					state=((CourseState)tempentry.getcurrentstate()).move('a');
					tempentry.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					courselist.set(i, tempentry);
					System.out.println("\n");
					}
					else {
						System.out.println("\n");
						break;
					}
				}
				else {
					System.out.println("当前状态下不能执行上课操作\n");
				}
				break;
			case "9":
				System.out.println("请输入你想要结束的课程计划项的课程名称(eg 软件构造)：");
				tempname=scanner.nextLine();
				for(i=0;i<courselist.size();i++) {
					if(courselist.get(i).getplanningentryname().equals(tempname)) {
						break;
					}
				}
				if(i==courselist.size()) {
					System.out.println("没有该计划项");
					System.out.println("\n");
					break;
				}
				tempentry=courselist.get(i);
				if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程已开始上课(Running)")) {
					System.out.println("请输入指令：结束");
					yixie=scanner.nextLine();
					if(tempentry.finish(yixie)) {
					state=((CourseState)tempentry.getcurrentstate()).move('a');
					tempentry.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					courselist.set(i, tempentry);
					System.out.println("\n");
					}
					else {
						System.out.println("\n");
						break;
					}
				}
				else {
					System.out.println("当前状态下不能执行下课操作\n");
				}
				break;
			case "10":
				System.out.println("请输入你想要取消的课程计划项的课程名称(eg 软件构造)：");
				tempname=scanner.nextLine();
				for(i=0;i<courselist.size();i++) {
					if(courselist.get(i).getplanningentryname().equals(tempname)) {
						break;
					}
				}
				if(i==courselist.size()) {
					System.out.println("没有该计划项");
					System.out.println("\n");
					break;
				}
				tempentry=courselist.get(i);
				if(((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程未分配老师(Waiting)")
						||((CourseState)tempentry.getcurrentstate()).getcoursestate().equals("课程已分配老师(Allocated)")) {
					System.out.println("请输入指令：取消");
					yixie=scanner.nextLine();	
					if(tempentry.cancel(yixie)) {
					canceltimeone=Calendar.getInstance();
					String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(canceltimeone.getTime()); 
					canceltimeone.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(str));
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
					System.out.println("课程已上课，无法取消\n");
				}
				break;
			case "11":
				System.out.println("请输入你想要查看的课程计划项的课程名称(eg 软件构造)：");
				tempname=scanner.nextLine();
				for(i=0;i<courselist.size();i++) {
					if(courselist.get(i).getplanningentryname().equals(tempname)) {
						break;
					}
				}
				if(i==courselist.size()) {
					System.out.println("没有该计划项或者已被取消");
					System.out.println();
					break;
				}
				tempentry=courselist.get(i);
				System.out.println("该课程计划项的状态为："+((CourseState)tempentry.getcurrentstate()).getcoursestate());
				System.out.println("\n");
				break;
			case "12":
				System.out.println("对于位置独占冲突，你可以选择两种算法来进行判断(输入1使用第一种，输入2使用第二种)，请选择输入(eg 1)");
				tempname=scanner.nextLine();
				System.out.println("所有计划项中位置独占冲突结果如下：");
				myapis.checkLocationConflict(courselist,tempname);
				System.out.println("所有计划项中资源独占冲突结果如下：");
				myapis.checkResourceExclusiveConflict(courselist);
				System.out.println("\n");
				break;
			case "13":
				System.out.println("请输入你想要查看的老师的身份证号，姓名，性别，职称，输入“结束”停止输入(eg 422823199812254452,余涛,男,讲师)：");
            	try {
            		temp=(scanner.nextLine()).split(",");
    				myteacher=new Teacher(temp[0],temp[1],temp[2],temp[3]);
				}catch(ArrayIndexOutOfBoundsException e) {
					 System.out.println("未用,隔开，请重新执行13操作，用正确格式输入\n");
					 //e.printStackTrace();
					 break;
				}
 				myapis.findEntryPerResource(myteacher, courselist);
 				System.out.println("\n");
            	break;
			case "14":
				System.out.println("请输入你想要查看其前序计划项的课程名称(eg 软件构造)：");
            	tempname=scanner.nextLine();
  				for(i=0;i<courselist.size();i++) {
 					if(courselist.get(i).getplanningentryname().equals(tempname) ){
 						break;
 					}
 				}
 				if(i==courselist.size()) {
 					System.out.println("没有该计划项");
 					System.out.println("\n");
 					break;
 				}
 				tempentry=courselist.get(i);
 				System.out.println("请输入需要查看前序计划项的该课程计划项中拥有的老师资源的姓名(eg 王宁)：");
 				tempname=scanner.nextLine();
 				for(j=0;j<tempentry.getresource().size();j++) {
 					if(tempentry.getresource().get(j).getteachername().equals(tempname)) {
 						break;
 					}
 				}
 				myteacher=tempentry.getresource().get(j);
                myapis.findPreEntryPerResource(myteacher,tempentry,courselist);	
                System.out.println("\n");	
				break;
			case "15":
				System.out.println("请输入你想要展示当前时刻的信息版的位置(eg 正心楼32)");
				tempname=scanner.nextLine();
				CourseEntryBoard flightboard=new CourseEntryBoard();
				flightboard.setclassroomlocation(tempname);
				flightboard.getsortallentry(courselist);
				flightboard.createCourseEntryBoard();
				flightboard.visualize();
				System.out.println("\n");
				break;
			case "16":
				 System.out.println("当前含有课程计划项的个数为："+courselist.size()+"\n");
	 			break;
			default:
				System.out.println("请输入正确指令\n");
				break;		
			}
		}
		
		
	}
}
