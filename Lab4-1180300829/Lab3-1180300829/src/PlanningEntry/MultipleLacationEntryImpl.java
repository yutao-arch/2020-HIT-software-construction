package PlanningEntry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import Exception.SameLocationException;
import Location.*;

public class MultipleLacationEntryImpl implements Cloneable,MultipleLocationEntry {

	private List<Location> locations; //一组位置
	
	// mutability类
	// Abstraction function:
	// AF(mytrain)=一组位置
	// Representation invariant:
	// 所有位置应该不同
	// Safety from rep exposure:
	// 将locations设置为private
	
	// TODO checkRep
    private void checkRep() {  //保证位置都不同,一组位置不为空
    	assert locations!=null;
    	boolean result=true;
		for(int i=0;i<locations.size()-1;i++) {
			for(int j=i+1;j<locations.size();j++) {
				if(locations.get(i).equals(locations.get(j))) {
					result=false;
					break;
				}
			}
		} 
        assert result==true:"存在重复位置\n";
    }
	
	/**
	 * 设置一组位置
	 * @param locations 待设置的一组位置，不能存在相同位置
	 * @return 是否成功设置这一组位置
	 * @throws SameLocationException 存在相同位置
	 */
	@Override
	public boolean setlocations(List<Location> mylocations) throws SameLocationException {
		boolean result=true;
		for(int i=0;i<mylocations.size()-1;i++) {
			for(int j=i+1;j<mylocations.size();j++) {
				if(mylocations.get(i).equals(mylocations.get(j))) {
					result=false;
					break;
				}
			}
		} 
		if(result==false) {   //存在相同的位置抛出异常
			throw new SameLocationException();
		}
		if(locations==null) {
			this.locations=new ArrayList<>(mylocations);
			System.out.println("位置设置成功");
			checkRep();
			return true;
		}
		System.out.println("位置只能设置一次");
		return false;
	}

	/**
	 * 更改这一组位置
	 * @param mylocations 更改后的位置
	 * @return 是都成功更改该位置
	 */
	@Override
	public boolean changelocations(List<Location> mylocations) {
		checkRep();
		System.out.println("位置不可更改");
		return false;
	}

	/**
	 * 得到这一组位置
	 * @return 这一组该位置
	 */
	@Override
	public List<Location> getlocations() {
		checkRep();
		return this.locations;
	}
	
	@Override
	  public MultipleLacationEntryImpl clone() { 
		MultipleLacationEntryImpl stu = null; 
	    try{ 
	      stu = (MultipleLacationEntryImpl)super.clone(); 
	    }catch(CloneNotSupportedException e) { 
	      e.printStackTrace(); 
	    } 
	    return stu; 
	  } 
}
