package PlanningEntry;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import Exception.SameResourceException;
import Location.Location;


public class MultipleSortedResourceEntryImpl<R> implements Cloneable,MultipleSortedResourceEntry<R> {

	private List<R> mysource; //资源集合
	
	// mutability类
	// Abstraction function:
	// AF(mytsource)=资源集合
	// Representation invariant:
	// 输入的资源不能为空,资源都不同
	// Safety from rep exposure:
	// 将mysource设置为private
	
	// TODO checkRep
    private void checkRep() {  //保证资源都不同，资源不为空
    	assert mysource!=null;
    	boolean result=true;
		for(int i=0;i<mysource.size()-1;i++) {
			for(int j=i+1;j<mysource.size();j++) {
				if(mysource.get(i).equals(mysource.get(j))) {
					result=false;
					break;
				}
			}
		} 
        assert result==true:"存在重复资源\n";
    }
	
	/**
	 * 设置该资源集合
	 * @param source 资源集合，资源不能相同
	 * @return 是否成功设置该资源集合
	 * @throws SameResourceException 存在相同的资源
	 */
	@Override
	public boolean setresource(List<R> source) throws SameResourceException {
		boolean result=true;
		for(int i=0;i<source.size()-1;i++) {
			for(int j=i+1;j<source.size();j++) {
				if(source.get(i).equals(source.get(j))) {
					result=false;
					break;
				}
			}
		} 
		if(result==false) {      //存在相同的资源抛出异常
			throw new SameResourceException();
		}
		if(mysource==null) {
			 this.mysource=new ArrayList<>(source);
			 System.out.println("资源集合设置成功");
			 checkRep();
			 return true;
		}
		 System.out.println("资源集合只能设置一次");
		return false;
	}
	

	/**
	 * 得到该资源集合
	 * @return 该资源集合
	 */
	@Override
	public List<R> getresource() {
		checkRep();
		return mysource;
	}

	/**
	 * 更改某资源
	 * @param presource 待更改的资源
	 * @param aftersource 更改后的资源
	 * @return 是否成功更改资源
	 */
	@Override
	public boolean changeresource(R presource,R aftersource) {
		checkRep();
		int i;
		for(i=0;i<mysource.size();i++) {
			if(mysource.get(i).equals(presource) ){
				break;
			}
		}
		if(i==mysource.size()) {
			System.out.println("没有待替换资源\n");
			System.out.println();
			return false;
		}
		if(mysource.contains(aftersource)) {
			System.out.println("该资源已经存在\n");
			return false;
		}
		mysource.set(i,aftersource);
		System.out.println("该资源已被替换为指定资源\n");
		return true;	
	}

	/**
	 * 向资源集合里面加入一个资源
	 * @param source 待加入的资源
	 * @param temp 加入资源的位置
	 * @return 是否成功加入资源
	 */
	@Override
	public boolean addresource(R source,int temp) {
		checkRep();
		if(mysource.contains(source)) {
			System.out.println("待加入资源已经存在\n");
			return false;
		}
		else {
			if(temp<mysource.size()) {
				mysource.add(temp, source);
				System.out.println("该资源已经增加在指定位置\n");
				return true;
			}
			else {
				mysource.add(source);
				System.out.println("该资源已经增加在指定位置\n");
				return true;
			}
		}
	}

	/**
	 * 删除资源集合中的某资源
	 * @param source 待删除的资源
	 * @return 是否成功删除资源
	 */
	@Override
	public boolean deleteresource(R source) {
		checkRep();
		boolean flag=false;
		Iterator<R> temp=mysource.iterator();
		while(temp.hasNext()) {
			R mm=temp.next();
			if(mm.equals(source)) {
				temp.remove();
				flag=true;
			}
		}
		if(flag==true) {
			System.out.println("指定资源已被移除\n");
			return true;
		}
		else {
			System.out.println("没有待移除资源\n");
			return false;
		}
	}
	
	@Override
	  public MultipleSortedResourceEntryImpl<R> clone() { 
		MultipleSortedResourceEntryImpl<R> stu = null; 
	    try{ 
	      stu = (MultipleSortedResourceEntryImpl<R>)super.clone(); 
	    }catch(CloneNotSupportedException e) { 
	      e.printStackTrace(); 
	    } 
	    return stu; 
	  } 

}
