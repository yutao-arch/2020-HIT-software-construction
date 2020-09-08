package PlanningEntry;

import java.util.List;

import Exception.SameResourceException;



public interface MultipleSortedResourceEntry<R> {

	/**
	 * 设置该资源集合
	 * @param source 资源集合
	 * @return 是否成功设置该资源集合
	 * @throws SameResourceException 存在相同的资源
	 */
	public boolean setresource(List<R> source) throws SameResourceException;
	
	/**
	 * 得到该资源集合
	 * @return 该资源集合
	 */
	public List<R> getresource();
	
	/**
	 * 更改某资源
	 * @param presource 待更改的资源
	 * @param aftersource 更改后的资源
	 * @return 是否成功更改资源
	 */
	public boolean changeresource(R presource,R aftersource);
	
	/**
	 * 向资源集合里面加入一个资源
	 * @param source 待加入的资源
	 * @param temp 加入资源的位置
	 * @return 是否成功加入资源
	 */
	public boolean addresource(R source,int temp);
	
	/**
	 * 删除资源集合中的某资源
	 * @param source 待删除的资源
	 * @return 是否成功删除资源
	 */
	public boolean deleteresource(R source);

	
}
