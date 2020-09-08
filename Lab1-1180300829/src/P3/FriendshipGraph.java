package P3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class FriendshipGraph {
	public List<Person> allpeople;  //储存所有的人对象
	public List<String> allname;    //储存所有已经存在的名字
	
	public FriendshipGraph() {   //构造方法
		allpeople=new ArrayList<Person>();
		allname=new ArrayList<String>();
	}
	
	public void addVertex(Person newpeople) {  //增加一个新的人对象
		String name1=newpeople.getmyname();
		if(allname.contains(name1)) {    //若名字已存在，重复
			System.out.println("此名已存在，重复");
			System.exit(0);
		}
		else {
			allname.add(name1);
			allpeople.add(newpeople);
		}
	}
	
	public void addEdge(Person a, Person b) {  //增加a的一个朋友b
		a.addnewfriend(b);
	}
	
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
			List<Person> allfriend=top.gethisfriend();
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
		
		System.out.println("\n第三行Ross换为Rachel后输出结果为:");
		ross = new Person("Rachel");
	}
}
