package PlanningEntryAPIs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import Location.*;
import PlanningEntry.*;
import Resource.*;
import Timeslot.Timeslot;

public class PlanningEntryAPIs<R> {
	
	
	private StrategycheckLocationConflict choice;
	
	    // mutability类
		// Abstraction function:
	    // AF(choice)=检查时间冲突的strategy方法接口
		// Safety from rep exposure:
		// 将choice设置为private
	
	/**
	 * 检测一组计划项之间是否存在位置独占冲突
	 * @param entries 计划项集合
	 * @param a strategy模式的选择参数
	 * @return 是否存在位置独占冲突
	 */
	public boolean checkLocationConflict(List<PlanningEntry> entries,String a) {
		switch(a) {
		case "1":
			choice=new Strategy1checkLocationConflict();
		    return choice.checkLocationConflict(entries);
		case "2":
			choice=new Strategy2checkLocationConflict();
		    return choice.checkLocationConflict(entries);
		default:
		    System.out.println("请输入正确指令");
		    return false;
		}
	}
	
	
	/**
	 * 检测一组计划项之间是否存在资源独占冲突
	 * @param entries 计划项集合
	 * @return 是否存在资源独占冲突
	 */
	public boolean checkResourceExclusiveConflict(List<PlanningEntry> entries) {
		int i,j,k,l;
		String m1,m2;
		boolean flag;
		Timeslot t1,t2;
		List<Timeslot> changt1,changt2;
		if(entries.get(0) instanceof CourseEntry) {
			flag=false;
			List<Teacher> l1=new ArrayList<>();
			List<Teacher> l2=new ArrayList<>();
			Teacher te1,te2;
			List<CourseEntry<Teacher>> courseentries=new ArrayList<>();
			for(i=0;i<entries.size();i++) {
				courseentries.add((CourseEntry<Teacher>)entries.get(i));
			}
			for(i=0;i<courseentries.size()-1;i++) {
				t1=courseentries.get(i).gettimeslot();
				m1=(String) courseentries.get(i).getplanningentryname();
				l1=courseentries.get(i).getresource();
				for(j=i+1;j<courseentries.size();j++) {
					t2=courseentries.get(j).gettimeslot();
					m2=(String) courseentries.get(j).getplanningentryname();
					l2=courseentries.get(j).getresource();
					for(k=0;k<l1.size();k++) {
						te1=l1.get(k);
						for(l=0;l<l2.size();l++) {
							te2=l2.get(l);
							if(te1.equals(te2)) {
								if(!(t1.getendtime().compareTo(t2.getbegintime())<=0||t2.getendtime().compareTo(t1.getbegintime())<=0)) {
									System.out.println("计划项“"+m1+"”与计划项“"+m2+"”存在时间冲突且使用了同一个老师"+"“"+te1.getteachername()+"”");
									flag=true;
									}
								}
							}
						}
					}
			    }
				if(flag) {
					return true;
				}
				else {
					System.out.println("没有计划项在重叠时间使用同一老师");
					return false;
					}	
				}
   

		
		if(entries.get(0) instanceof FlightEntry) {
			flag=false;
			Flight f1,f2;
			List<FlightEntry<Flight>> flightentries=new ArrayList<>();
			for(i=0;i<entries.size();i++) {
				flightentries.add((FlightEntry<Flight>)entries.get(i));
			}
			for(i=0;i<flightentries.size()-1;i++) {
				changt1=flightentries.get(i).gettimeslot();
				m1=(String) flightentries.get(i).getplanningentryname();
				f1=(Flight) flightentries.get(i).getresource();
				for(j=i+1;j<flightentries.size();j++) {
					changt2=flightentries.get(j).gettimeslot();
					m2=(String) flightentries.get(j).getplanningentryname();
					f2=(Flight) flightentries.get(j).getresource();
					if(f1.equals(f2)) {
						if(!(changt1.get(flightentries.size()-1).getendtime().compareTo(changt2.get(0).getbegintime())<=0||
								changt2.get(flightentries.size()-1).getendtime().compareTo(changt1.get(0).getbegintime())<=0)) {
							System.out.println("计划项“"+m1+"”与计划项“"+m2+"”存在时间冲突且使用了同一个飞机"+"“"+f2.getflightnumber()+"”");
							flag=true;
						}
					}
				}
			}
			if(flag) {
				return true;
			}
			else {
				System.out.println("没有冲突资源");
				return false;
			}
		}
		
		if(entries.get(0) instanceof TrainEntry) {
			flag=false;
			List<Carriage> b1=new ArrayList<>();
			List<Carriage> b2=new ArrayList<>();
			List<TrainEntry<Carriage>> trainentries=new ArrayList<>();
			Carriage c1,c2 ;          
			for(i=0;i<entries.size();i++) {
				trainentries.add((TrainEntry<Carriage>)entries.get(i));
			}
			for(i=0;i<trainentries.size()-1;i++) {
					changt1=trainentries.get(i).gettimeslot();
			        b1=trainentries.get(i).getresource();
					m1=(String) trainentries.get(i).getplanningentryname();
					for(j=i+1;j<trainentries.size();j++) {
						changt2=trainentries.get(j).gettimeslot();
						b2=trainentries.get(j).getresource();
						m2=(String) trainentries.get(j).getplanningentryname();
						for(k=0;k<b1.size();k++) {
							c1=b1.get(k);
							for(l=0;l<b2.size();l++) {
								c2=b2.get(l);
								if(c1.equals(c2)) {
									if(!(changt1.get(trainentries.size()-1).getendtime().compareTo(changt2.get(0).getbegintime())<=0||
											changt2.get(trainentries.size()-1).getendtime().compareTo(changt1.get(0).getbegintime())<=0)) { 
										System.out.println("计划项“"+m1+"”与计划项“"+m2+"”存在时间冲突且使用了同一个车厢"+"“"+c2.getcarriagenumber()+"”");
										flag=true;
									}
								}
							}
						}
					}
				}
			if(flag) {
				return true;
			}
			else {
				System.out.println("没有冲突资源");
				return false;
			}
		}
		System.out.println("请输入正确信息");
		return false;
	}
	
	/**
	 * 得到一组计划项中使用某一资源的所有计划项集合
	 * @param r 待查找的资源
	 * @param entries 计划项集合
	 * @return 使用该资源的所有计划项集合
	 */
	public List<PlanningEntry> findEntryPerResource(R r,List<PlanningEntry> entries) {
		String l1;
		int i,k;
		if(entries.get(0) instanceof CourseEntry) {
			List<CourseEntry<Teacher>> courseentries=new ArrayList<>();
			List<PlanningEntry> fanhuientries=new ArrayList<>();
			Teacher myteacher=(Teacher) r;
			for(i=0;i<entries.size();i++) {
				courseentries.add((CourseEntry<Teacher>)entries.get(i));
			}
			//Collections.sort(courseentries);
			for(i=0;i<courseentries.size();i++) {
				for(k=0;k<courseentries.get(i).getresource().size();k++) {
					if(courseentries.get(i).getresource().get(k).getteachername().equals(myteacher.getteachername())) {
						l1=(String)courseentries.get(i).getplanningentryname();
						System.out.println("计划项“"+l1+"”使用了该资源");
						fanhuientries.add(courseentries.get(i));
					}
				}
			}
			return fanhuientries;
		}
		if(entries.get(0) instanceof FlightEntry) {
			List<FlightEntry<Flight>> flightentries=new ArrayList<>();
			List<PlanningEntry> fanhuientries=new ArrayList<>();
			Flight myflight=(Flight) r;
			for(i=0;i<entries.size();i++) {
				flightentries.add((FlightEntry<Flight>)entries.get(i));
			}
			//Collections.sort(flightentries);
			for(i=0;i<flightentries.size();i++) { 
				if(flightentries.get(i).getresource().getflightnumber().equals(myflight.getflightnumber())) {
					l1=(String)flightentries.get(i).getplanningentryname();
					System.out.println("计划项“"+l1+"”使用了该资源");
					fanhuientries.add(flightentries.get(i));
				}
			}	
			return fanhuientries;
		}
		if(entries.get(0) instanceof TrainEntry) {
			List<TrainEntry<Carriage>> trainentries=new ArrayList<>();
			List<PlanningEntry> fanhuientries=new ArrayList<>();
			Carriage mycarriage=(Carriage) r;
			for(i=0;i<entries.size();i++) {
				trainentries.add((TrainEntry<Carriage>)entries.get(i));
			}
			//Collections.sort(trainentries);
			for(i=0;i<trainentries.size();i++) { 
				for(k=0;k<trainentries.get(i).getresource().size();k++) {
						if(trainentries.get(i).getresource().get(k).getcarriagenumber().equals(mycarriage.getcarriagenumber())) {
							l1=(String)trainentries.get(i).getplanningentryname();
							System.out.println("计划项“"+l1+"”使用了该资源");
							fanhuientries.add(trainentries.get(i));
						
					}
				}
			}
			return fanhuientries;	
		}
		System.out.println("请输入正确信息");
		   return null;
	}
	
	/**
	 * 提取面向特定资源的一个前序计划项
	 * @param r 资源
	 * @param e 含有该资源的某个计划项
	 * @param entries  计划项集合
	 * @return 该计划项含有r的某个前序计划项，没有返回null
	 */
	public PlanningEntry findPreEntryPerResource(R r,PlanningEntry e,List<PlanningEntry> entries) {
		String l1,l2;
		int i,k,l;
		if(entries.get(0) instanceof CourseEntry) {
			List<CourseEntry<Teacher>> courseentries=new ArrayList<>();
			CourseEntry<Teacher> eentries=(CourseEntry<Teacher>)e;
			for(i=0;i<entries.size();i++) {
				courseentries.add((CourseEntry<Teacher>)entries.get(i));
			}
			Teacher myteacher=(Teacher) r;
			Collections.sort(courseentries);
			int temp=courseentries.indexOf(eentries);
			for(i=0;i<temp;i++) { //找出e的所有前序项
				for(k=0;k<courseentries.get(i).getresource().size();k++) {
						if(courseentries.get(i).getresource().get(k).equals(r)) {
							l1=(String)courseentries.get(i).getplanningentryname();
							l2=(String)eentries.getplanningentryname();
							System.out.println("前序计划项“"+l1+"”与计划项“"+l2+"”共享了资源该");
							return courseentries.get(i);
						}
					}
			}
			System.out.println("没有前序计划项共享资源");
			return null;
		}
		if(entries.get(0) instanceof FlightEntry) {
			List<FlightEntry<Flight>> flightentries=new ArrayList<>();
			FlightEntry<Flight> eentries=(FlightEntry<Flight>)e;
			for(i=0;i<entries.size();i++) {
				flightentries.add((FlightEntry<Flight>)entries.get(i));
			}
			Collections.sort(flightentries);
			int temp=flightentries.indexOf(eentries);
			for(i=0;i<temp;i++) { //找出e的所有前序项
				if(flightentries.get(i).getresource().equals(eentries.getresource())) {
					l1=(String)flightentries.get(i).getplanningentryname();
					l2=(String)eentries.getplanningentryname();
					System.out.println("前序计划项“"+l1+"”与计划项“"+l2+"”共享了资源"+"“"+eentries.getresource().getflightnumber()+"”");
					return flightentries.get(i);
				}
			}	
			System.out.println("没有前序计划项共享资源");
			return null;
		}
		if(entries.get(0) instanceof TrainEntry) {
			List<TrainEntry<Carriage>> trainentries=new ArrayList<>();
			TrainEntry<Carriage> eentries=(TrainEntry<Carriage>)e;
			for(i=0;i<entries.size();i++) {
				trainentries.add((TrainEntry<Carriage>)entries.get(i));
			}
			Carriage mycarriage=(Carriage) r;
			Collections.sort(trainentries);
			int temp=trainentries.indexOf(eentries);
			for(i=0;i<temp;i++) { //找出e的所有前序项
				for(k=0;k<trainentries.get(i).getresource().size();k++) {
						if(trainentries.get(i).getresource().get(k).equals(mycarriage)) {
							l1=(String)trainentries.get(i).getplanningentryname();
							l2=(String)eentries.getplanningentryname();
							System.out.println("前序计划项“"+l1+"”与计划项“"+l2+"”共享了该资源");
							return trainentries.get(i);
						}
				}
			}
			System.out.println("没有前序计划项共享资源");
			return null;
		}
		System.out.println("请输入正确信息");
		return null;
	}
}
