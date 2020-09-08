package P2;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import P1.graph.ConcreteEdgesGraph;


public class FriendshipGraph {

	private final ConcreteEdgesGraph<Person> personGraph;
	
	// Abstraction function:
    // AF(personFraph)=Social NetWork
    // Representation invariant:
    // 没有重复的Person
    // Safety from rep exposure:
    // 将personGraph设置为private
    // 由于personGraph是inmutable，所以在返回时不需要进行defensive copies
	
	/**
	 * 初始化构造方法
	 */
	public FriendshipGraph() {
		personGraph = new ConcreteEdgesGraph<>();
	}
	
	/**
	 * 在图中增加新人
	 * @param newperson 待加入的新Person
	 */
	public void addVertex(Person newperson) {
		personGraph.add(newperson);
	}

	/**
	 * 为某个人增加朋友
	 * @param a 这个人
	 * @param b 增加的朋友
	 */
	public void addEdge(Person a, Person b) {
		personGraph.set(a, b, 1);
	}
	
	/**
	 * 返回ConcreteVerticesGraph图
	 * @return ConcreteVerticesGraph图
	 */
	public ConcreteEdgesGraph<Person> getallprople() {
		return personGraph;
	}

	/**
	 * 得到两个人之间的最短距离
	 * @param c1 第一个人
	 * @param c2 第二个人
	 * @return 两个人之间的最短距离
	 */
	public int getDistance(Person c1, Person c2) {  //得到两个人之间的距离
		if(c1==c2) {
			return 0;
		}
		Map<Person,Integer> theway=new HashMap<>();
		Queue<Person> myqueue=new LinkedList<>();
		myqueue.offer(c1);
		theway.put(c1,0);
		int distance;
		while(!myqueue.isEmpty()) {  //只要队列不空
			Person top=myqueue.poll();
			distance=theway.get(top);
			Map<Person, Integer> friend=personGraph.targets(top);
			Set<Person> allfriend=friend.keySet();
			for(Person m:allfriend) {
				if(!theway.containsKey(m)) {
					theway.put(m,distance+1);  //将某人的朋友全部标记为加一存入
					myqueue.offer(m);
					if(m==c2) {
						return theway.get(c2);
					}
				}
			}
		}
		return -1;
	}
	
	public static void main(String args[]) {
		System.out.println("原版实验报告所所对应图输出结果为:");
		FriendshipGraph graph = new FriendshipGraph();
		Person rachel = new Person("Rachel");
		Person ross = new Person("Ross");
		Person ben = new Person("Ben");
		Person kramer = new Person("Kramer");
		graph.addVertex(rachel);
		graph.addVertex(ross);
		graph.addVertex(ben);
		graph.addVertex(kramer);
		graph.addEdge(rachel, ross);
		graph.addEdge(ross, rachel);
		graph.addEdge(ross, ben);
		graph.addEdge(ben, ross);
		System.out.println("(rachel, ross)之间距离为"+graph.getDistance(rachel, ross));
		// should print 1
		System.out.println("(rachel, ben)之间距离为"+graph.getDistance(rachel, ben));
		// should print 2
		System.out.println("(rachel, rachel)之间距离为"+graph.getDistance(rachel, rachel));
		// should print 0
		System.out.println("(rachel, kramer)之间距离为"+graph.getDistance(rachel, kramer));
		// should print -1
	}
}
