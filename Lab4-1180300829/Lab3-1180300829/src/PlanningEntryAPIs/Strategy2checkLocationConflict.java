package PlanningEntryAPIs;

import java.util.ArrayList;
import java.util.List;
import PlanningEntry.CourseEntry;
import PlanningEntry.FlightEntry;
import PlanningEntry.PlanningEntry;
import PlanningEntry.TrainEntry;
import Resource.Teacher;
import Timeslot.Timeslot;

public class Strategy2checkLocationConflict implements StrategycheckLocationConflict{

	/**
	 * 检测一组计划项之间是否存在位置独占冲突
	 * @param entries 计划项集合
	 * @return 是否存在位置独占冲突
	 */
	@Override
	public boolean checkLocationConflict(List<PlanningEntry> entries) {
		int i,j,k;
		Timeslot b1,b2;
		String l1,m1,m2;
		int zongshu=0;  
		boolean flag;
		if(entries.get(0) instanceof CourseEntry) {
			flag=false;
			List<CourseEntry<Teacher>> tempentries=new ArrayList<>();    //临时的list
			List<CourseEntry<Teacher>> oneentries=new ArrayList<>();     //向下转型后的list
			List<List<CourseEntry<Teacher>>> alllist=new ArrayList<>();  //储存一堆具有相同地址的Entry的list的list
			List<String> locationentries=new ArrayList<>();     //储存所有地址的list
	        String templocation;
			for(i=0;i<entries.size();i++) {          //向下转型得到CourseEntry新的list
				oneentries.add((CourseEntry<Teacher>)entries.get(i));
			}
			for(i=0;i<oneentries.size();i++) {      //得到储存所有地址的list
				l1=oneentries.get(i).getlocations().getlocationname();
				if(!locationentries.contains(l1)) {
					locationentries.add(l1);
				}
			}
			while(zongshu<oneentries.size()) {      //按照得知等价类划分所有CourseEntry，得到储存储存一堆具有相同地址的Entry的list的list
				for(i=0;i<locationentries.size();i++) {
					templocation=locationentries.get(i);
					tempentries=new ArrayList<>();
					for(j=0;j<oneentries.size();j++) {
						if(oneentries.get(j).getlocations().getlocationname().equals(templocation)) {
							tempentries.add(oneentries.get(j));  //临时得到具有相同地址的Entry的list
							zongshu++;
						}
					}
					alllist.add(tempentries);	//将该临时的list加入储存等价类的list中
				}	
			}	
			for(i=0;i<alllist.size();i++) {
				tempentries=new ArrayList<>(alllist.get(i));  
				if(tempentries.size()>1) {   //对该等价类list中的每一个长度超过一的list进行是否时间冲突判断
					for(j=0;j<tempentries.size()-1;j++) {
						b1=tempentries.get(j).gettimeslot();
						m1=(String) tempentries.get(j).getplanningentryname();
						for(k=j+1;k<tempentries.size();k++) {
							b2=tempentries.get(k).gettimeslot();
							m2=(String) tempentries.get(k).getplanningentryname();
							if(!(b1.getendtime().compareTo(b2.getbegintime())<=0||b2.getendtime().compareTo(b1.getbegintime())<=0)) {
								System.out.println("计划项“"+m1+"”与计划项“"+m2+"”存在时间冲突且使用了同一间教室"+"“"+tempentries.get(k).getlocations().getlocationname()+"”");
								flag=true;
							}
						}
					}
				}
			}
			if(flag==true) {
				return true;
			}
			else {
				System.out.println("没有计划项在重叠时间使用同一教室");
				return false;
			}
		}
		if(entries.get(0) instanceof FlightEntry) {
			System.out.println("机场位置可共享，不存在冲突");
			return false;
		}
		if(entries.get(0) instanceof TrainEntry) {
			System.out.println("高铁站位置可共享，不存在冲突");
			return false;
		}
	   System.out.println("请输入正确信息");
	   return false;
	}

}
