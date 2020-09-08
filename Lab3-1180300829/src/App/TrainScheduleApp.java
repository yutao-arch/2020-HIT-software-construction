package App;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import Board.TrainEntryBoard;
import EntryState.*;
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
	}
	
	/**
	 * 
	 * @param args
	 * @throws ParseException 时间格式不为yyyy-MM-dd HH:mm
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws ParseException {
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
		
		while(true) {
			menu();
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
				List<Location> alllocation=new ArrayList<>();
				int ll=0;
				System.out.println("请依次输入该行程所有高铁站名称，输入“结束”停止输入(每输入一个按一下回车)(eg 北京)：");
				String tempname;
				while(!(tempname=scanner.nextLine()).equals("结束")) {
					FlightTrainLocation from=new FlightTrainLocation(weidu,jingdu,tempname);
					alllocation.add(from);
					ll++;
				}
                train.setlocations(alllocation);
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
						 System.out.println("未用,隔开，请重新执行1操作，用正确格式输入\n");
						 hh=true;
						 //e.printStackTrace();
						 break;
					}
					//tempname=scanner.nextLine();
					//temp=tempname.split(",");
					//Timeslot mytime=new Timeslot(temp[0],temp[1]);
					//alltime.add(mytime);
				}
				if(hh==true) {
					break;
				}
				train.settimeslot(alltime);
				System.out.println("请输入车次号(eg G1020)：");
				String trainname=scanner.nextLine();
				train.setplanningentryname(trainname);
				System.out.println("计划项名字设置成功");
				state = TrainWaitingState.instance;
				train.setcurrentstate(state);
				System.out.println("计划项当前状态设置成功");
				System.out.println("信息设置完成\n");
				trainlist.add(train);
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
					System.out.println("没有该计划项");
					System.out.println("\n");
					break;
				}
				TrainEntry<Carriage> tempentry=trainlist.get(i);
				allcarriage=new ArrayList<>();
				boolean gg=false;
				if(((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁未分配车厢(Waiting)")) {
					System.out.println("请按照次序依次输入一组车厢的编号(不同车厢不同)，类型，定员数，出厂年份，输入“结束”停止输入(用逗号隔开，每输入一个车厢按一下回车)(eg A01,一等座,100,2012)：");
					while(!(tempname=scanner.nextLine()).equals("结束")) {
						try {
							temp=tempname.split(",");
							mycarriage=new Carriage(temp[0],temp[1],Integer.parseInt(temp[2]),temp[3]);
							allcarriage.add(mycarriage);
						}catch(ArrayIndexOutOfBoundsException e) {
							 System.out.println("未用,隔开，请重新执行2操作，用正确格式输入\n");
							 //e.printStackTrace();
							 gg=true;
							 break;
						}
						//temp=tempname.split(",");
						//mycarriage=new Carriage(temp[0],temp[1],Integer.parseInt(temp[2]),temp[3]);
						//allcarriage.add(mycarriage);
					}
					if(gg==true) {
						break;
					}
					tempentry.setresource(allcarriage);
					state=((TrainState)tempentry.getcurrentstate()).move('a');
					tempentry.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					trainlist.set(i, tempentry);
					System.out.println("\n");
				}
				else {
					System.out.println("当前状态下不能执行设置飞机资源操作\n");
				}
				break;
			case "3":	
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
						System.out.println("没有该车厢");
						System.out.println("\n");
						break;
					}
					Carriage tempcarriage=allcarriage.get(j);
					System.out.println("请输入变更后的车厢的编号，类型，定员数，出厂年份(用逗号隔开)(eg NB02,二等座,100,2011)：");
					try {
						temp=(scanner.nextLine()).split(",");
						mycarriage=new Carriage(temp[0],temp[1],Integer.parseInt(temp[2]),temp[3]);
					}catch(ArrayIndexOutOfBoundsException e) {
						 System.out.println("未用,隔开，请重新执行4操作，用正确格式输入\n");
						 //e.printStackTrace();
						 break;
					}
					//temp=(scanner.nextLine()).split(",");
					//mycarriage=new Carriage(temp[0],temp[1],Integer.parseInt(temp[2]),temp[3]);
					tempentry.changeresource(tempcarriage,mycarriage);
					trainlist.set(i, tempentry);
				}
				else {
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
					}catch(ArrayIndexOutOfBoundsException e) {
						 System.out.println("未用,隔开，请重新执行5操作，用正确格式输入\n");
						 //e.printStackTrace();
						 break;
					}
					//temp=(scanner.nextLine()).split(",");
					//mycarriage=new Carriage(temp[0],temp[1],Integer.parseInt(temp[2]),temp[3]);
					System.out.println("请输入增加的车厢在一组车厢中的位置(第一节车厢默认位置为1)(eg 3)：");
					tempname=scanner.nextLine();
					int weizhi=Integer.parseInt(tempname);
					if(weizhi>tempentry.getresource().size()+1||weizhi<1) {
						System.out.println("增加的车厢位置不合法\n");
						break;
					}
					tempentry.addresource(mycarriage,weizhi-1);
					trainlist.set(i, tempentry);
				}
				else {
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
						System.out.println("没有该车厢");
						System.out.println("\n");
						break;
					}
					Carriage tempcarriage=allcarriage.get(j);
					tempentry.deleteresource(tempcarriage);
					trainlist.set(i, tempentry);
				}
				else {
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
					state=((TrainState)tempentry.getcurrentstate()).move('a');
					tempentry.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					trainlist.set(i, tempentry);
					System.out.println("\n");
					}
					else {
						System.out.println("\n");
						break;
					}
				}
				else {
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
						break;
					}
					state=((TrainState)tempentry.getcurrentstate()).move('b');
					tempentry.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					trainlist.set(i, tempentry);
					System.out.println("\n");
				}
				else {
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
					System.out.println("没有该计划项");
					System.out.println("\n");
					break;
				}
				tempentry=trainlist.get(i);
				if(((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁已从起始站发车(Running)")) {	
					System.out.println("请输入指令：结束");
					yixie=scanner.nextLine();
					if(tempentry.finish(yixie)) {
					state=((TrainState)tempentry.getcurrentstate()).move('a');
					tempentry.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					trainlist.set(i, tempentry);
					System.out.println("\n");
					}
					else {
						System.out.println("\n");
						break;
					}
				}
				else {
					System.out.println("当前状态下不能执行结束高铁操作\n");
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
					System.out.println("没有该计划项");
					System.out.println("\n");
					break;
				}
				tempentry=trainlist.get(i);
				if(((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁已分配一组车厢(Allocated)")||
				((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁中途阻塞(Blocked)")||
				((TrainState)tempentry.getcurrentstate()).gettrainstate().equals("高铁未分配车厢(Waiting)")) {	
					System.out.println("请输入指令：取消");
					yixie=scanner.nextLine();	
					if(tempentry.cancel(yixie)) {
					canceltimeone=Calendar.getInstance();
					String str = (new SimpleDateFormat("yyyy-MM-dd HH:mm")).format(canceltimeone.getTime()); 
					canceltimeone.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(str));
					System.out.println("该航班计划项取消的时间为"+str);
					state=((TrainState)tempentry.getcurrentstate()).move('b');
					tempentry.setcurrentstate(state);
					System.out.println("计划项当前状态设置成功");
					trainlist.remove(i);
					System.out.println("\n");
					}
					else {
						System.out.println("\n");
						break;
					}
				}
				else {
					System.out.println("当前状态下不能执行取消高铁操作\n");
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
					System.out.println("没有该计划项或者已被取消");
					System.out.println("\n");
					break;
				}
				tempentry=trainlist.get(i);
 				System.out.println("该高铁计划项的状态为："+((TrainState)tempentry.getcurrentstate()).gettrainstate());
 				System.out.println("\n");
				break;
             case "12":
            	System.out.println("对于位置独占冲突，你可以选择两种算法来进行判断(输入1使用第一种，输入2使用第二种)，请选择输入(eg 1)");
    			tempname=scanner.nextLine();
            	System.out.println("所有计划项中位置独占冲突结果如下：");
    			myapis.checkLocationConflict(trainlist,tempname);
    			System.out.println("所有计划项中资源独占冲突结果如下：");
    			myapis.checkResourceExclusiveConflict(trainlist);
    			System.out.println();
            	 break;
             case "13":
            	System.out.println("请输入你想要查看的车厢资源的车厢的编号，类型，定员数，出厂年份(用逗号隔开)(eg A01,二等座,100,2011)：");
            	try {
            		temp=(scanner.nextLine()).split(",");
    				mycarriage=new Carriage(temp[0],temp[1],Integer.parseInt(temp[2]),temp[3]);
				}catch(ArrayIndexOutOfBoundsException e) {
					 System.out.println("未用,隔开，请重新执行13操作，用正确格式输入\n");
					 //e.printStackTrace();
					 break;
				}
            	//temp=(scanner.nextLine()).split(",");
				//mycarriage=new Carriage(temp[0],temp[1],Integer.parseInt(temp[2]),temp[3]);
 				myapis.findEntryPerResource(mycarriage, trainlist);
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
 				mycarriage=tempentry.getresource().get(j);
                myapis.findPreEntryPerResource(mycarriage,tempentry,trainlist);	
                System.out.println("\n");	
            	break;
             case "15":
            	System.out.println("请输入你想要展示当前时刻的信息版的位置(eg 北京)");
    			tempname=scanner.nextLine();
    			TrainEntryBoard trainboard=new TrainEntryBoard();	
    			trainboard.setrailwaylocation(tempname);
    			trainboard.getsortcomeentry(trainlist);
    			trainboard.getsorttoentry(trainlist);
    			trainboard.createTrainEntryBoard();
    			trainboard.visualize();	
    			System.out.println("\n");
            	 break;
             case "16":
                System.out.println("当前含有高铁计划项的个数为："+trainlist.size()+"\n");
 				break;
             default:
 				System.out.println("请输入正确指令\n");
 				break;		
			}

			
		}
	}
}
