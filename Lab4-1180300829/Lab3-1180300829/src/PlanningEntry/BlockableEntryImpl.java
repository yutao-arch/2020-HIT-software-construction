package PlanningEntry;

import java.util.ArrayList;
import java.util.List;

import Exception.ConflictTimeException;
import Timeslot.Timeslot;

public class BlockableEntryImpl implements Cloneable,BlockableEntry {

	private List<Timeslot> alltime; //一组时间对
	
	// mutability类
	// Abstraction function:
	// AF(mytrain)=一组时间对
	// Representation invariant:
	// 输入的时间对集合不能为空，每个站的抵达时间早于出发时间
	// Safety from rep exposure:
	// 将alltime设置为private
	
	// TODO checkRep
    private void checkRep() {  //保证每个站抵达时间早于出发时间
    	assert alltime!=null;
    	boolean result=true;
    	for(int i=0;i<alltime.size()-1;i++) {
			if(alltime.get(i).getendtime().compareTo(alltime.get(i+1).getbegintime())>0) {
				result=false;
			}
		}
        assert result==true:"某个站抵达时间晚于出发时间\n";
    }
	
	/**
	 * 判断是否可阻塞
	 * @return 是否可阻塞
	 */
	@Override
	public boolean whetherblockable() {
		System.out.println("可阻塞");
		return true;
	}

	 /**
     * 设置这一组时间对
     * @param alltime 一组时间对，每个站抵达时间早于出发时间
     * @return 是否成功设置这一组时间对
	 * @throws ConflictTimeException 某个站抵达时间晚于出发时间
     */
	@Override
	public boolean settimeslot(List<Timeslot> time) throws ConflictTimeException {
		for(int i=0;i<time.size()-1;i++) {
			if(time.get(i).getendtime().compareTo(time.get(i+1).getbegintime())>0) {
				throw new ConflictTimeException();
			}
		}
		if(alltime==null) {
			alltime=new ArrayList<>();
			this.alltime=time;
			System.out.println("一组时间对设置成功");
			checkRep();
			return true;
		}
		System.out.println("时间只能设置一次");
		return false;
	}

	
	/**
	 * 得到这一组时间对
	 * @return 这一组时间对
	 */
	@Override
	public List<Timeslot> gettimeslot() {
		checkRep();
		return alltime;
	}
	
	@Override
	  public BlockableEntryImpl clone() { 
		BlockableEntryImpl stu = null; 
	    try{ 
	      stu = (BlockableEntryImpl)super.clone(); 
	    }catch(CloneNotSupportedException e) { 
	      e.printStackTrace(); 
	    } 
	    return stu; 
	  } 
}
