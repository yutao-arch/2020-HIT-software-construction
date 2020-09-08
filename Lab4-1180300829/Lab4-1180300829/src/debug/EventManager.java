package debug;

import java.util.TreeMap;

/**
 * 
 * 实现一个EventManager类来管理个人日程，通过该类的一个方法
 * 
 * book(int day, int start, int end)
 * 
 * 来添加新事件
 * 
 * 待添加的新事件发生在day，这是一个整数，表示一年里的第day天
 * 
 * start表示事件的起始时间，为该day天的第start小时
 * 
 * end表示该事件的结束时间，为该day天的第end小时。
 * 
 * 例如：
 * book(1,8,10)表示添加一个在1月1日（第1天）的8点开始，10点结束的事件。
 * book(1, 0, 1)表示在第1天的0:00-1:00的事件  
 * book(1, 22,24)表示在第1天的22:00-24:00的事件
 * 
 * 事件的长度单位是小时，不需要考虑分钟。
 * 
 * 约束条件：1<=day<=365（无需考虑闰年之类的问题），0<=start<end<=24。

 * “k-重叠”是指：有k个事件的时间范围在某个时间段内存在交集，即这k个事件在某个小时内都已经启动且尚未结束。
 * book(…)方法的返回值是：当本次调用结束后的最大k值。
 * 
 * 例如：
 * 
 * EventManager.book(1, 10, 20); 	// returns 1
 * EventManager.book(1, 1, 7); 		// returns 1
 * EventManager.book(1, 10, 22); 	// returns 2
 * EventManager.book(1, 5, 15); 	// returns 3
 * EventManager.book(1, 5, 12); 	// returns 4
 * EventManager.book(1, 7, 10); 	// returns 4
 * 
 * 请对以下代码进行调试和修改，使其完整、正确、健壮的完成上述需求，但不要改变该代码的内在逻辑。
 *
 * 
 */
public class EventManager {

	static TreeMap<Integer, Integer> temp = new TreeMap<>();  //未对Map引用参数化，加上<>

	
	/**
	 * @param day one of the day in a year
	 * @param start start time of the event to be added, should be in [0, 24)
	 * @param end   end time of the event to be added, should be in (0, 24]
	 * @return 		the max number of concurrent events in the same hour on one day
	 */
	public static int book(int day, int start, int end) { 

		assert day>=1&&day<=365;    //对前置条件进行检查
		assert start>=0&&start<24;  
		assert end>=0&&end<24;
		start=start+(day-1)*24;  //原因：未使用day对每天区分。修改：得到每天的开始时间
		end=end+(day-1)*24;      //原因：未使用day对每天区分。修改：得到每天的结束时间
		//前缀和思想，对于开始时间每遇到一个值+1，结束时间每遇到一个值-1
		if(temp.containsKey(start)&&temp.containsKey(end)) {  //若两个时间本身就存在，直接取得原时间值，开始时间值+1，结束时间值-1
			temp.put(start, temp.get(start)+1);
			temp.put(end, temp.get(end)-1);
		}
		else if(temp.containsKey(start)&&!temp.containsKey(end)) { //若开始时间值存在，结束时间值不存在，直接取得原时间，开始时间值+1，结束时间值直接为-1
			temp.put(start, temp.get(start)+1);
			temp.put(end, -1);
		}
		else if(!temp.containsKey(start)&&temp.containsKey(end)) { //若结束时间值存在，开始时间值不存在，直接取得原时间，开始时间值直接为+1，结束时间值-1
			temp.put(start, 1);
			temp.put(end, temp.get(end)-1);
		}
		else {     //若两个时间值本身不存在，开始时间值直接为+1，结束时间值直接为-1
			temp.put(start, 1);
			temp.put(end, -1);
		}

		int active = 0, ans = 0;
		for (int d : temp.values()) {
			active += d;
			if (active >= ans)
				ans = active;
		}
		return ans;
	}
}
