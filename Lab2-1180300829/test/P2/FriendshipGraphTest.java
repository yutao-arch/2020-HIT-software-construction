package P2;

import static org.junit.Assert.*;

import org.junit.Test;

public class FriendshipGraphTest {
	FriendshipGraph graph=new FriendshipGraph();
	/*
	 * Testing strategy
	 * 创建对象：创建四个对象即可
	 */
	@Test
	public void addVertextest() {
		Person yu = new Person("Yu");
		Person tao = new Person("Tao");
		Person cai = new Person("Cai");
		Person ji = new Person("Ji");
		graph.addVertex(yu);
		graph.addVertex(tao);
		graph.addVertex(cai);
		graph.addVertex(ji);
		assertTrue(graph.getallprople().vertices().contains(yu));
		assertTrue(graph.getallprople().vertices().contains(tao));
		assertTrue(graph.getallprople().vertices().contains(cai));
		assertTrue(graph.getallprople().vertices().contains(ji));
	}
	/*
	 * Testing strategy
	 * 加入无向图朋友：只需要任取两组点都加入朋友即可
	 */
	@Test
	public void addEdgetest() {
		Person wang = new Person("Wang");
		Person ning = new Person("Ning");
		Person li = new Person("Li");
		Person hai = new Person("Hai");
		graph.addVertex(wang);
		graph.addVertex(ning);
		graph.addVertex(li);
		graph.addVertex(hai);
		graph.addEdge(wang, ning);
		graph.addEdge(ning, wang);
		graph.addEdge(ning, li);
		graph.addEdge(li, ning);
		assertTrue(graph.getallprople().targets(wang).containsKey(ning));
		assertTrue(graph.getallprople().targets(ning).containsKey(wang));
		assertTrue(graph.getallprople().targets(ning).containsKey(li));
		assertTrue(graph.getallprople().targets(li).containsKey(ning));
	}
	
	/*
	 * Testing strategy
	 * 按照两个人之间的距离划分：距离为0，距离为-1，距离为大于1
	 * 按照是否同一个人划分：同一个人之间距离，两个人之间距离
	 * 覆盖每个取值如下：
	 */
	@Test
	public void getDistancetest() {
		Person wo = new Person("Wo");
		Person shi = new Person("Shi");
		Person xue = new Person("Xue");
		Person sheng = new Person("Sheng");
		graph.addVertex(wo);
		graph.addVertex(shi);
		graph.addVertex(xue);
		graph.addVertex(sheng);
		graph.addEdge(wo, shi);
		graph.addEdge(shi, wo);
		graph.addEdge(shi, xue);
		graph.addEdge(xue, shi);
		assertEquals(1, graph.getDistance(wo, shi));
		assertEquals(2, graph.getDistance(wo, xue));
		assertEquals(0, graph.getDistance(sheng, sheng));
		assertEquals(-1, graph.getDistance(wo, sheng));
	}
	
	/*
	 * Testing strategy
	 * 看返回的personGraph是否包含所有图中的元素即可
	 */
	@Test
	public void getallpropletest() {
		Person dan = new Person("Dan");
		Person huang = new Person("Huang");
		graph.addVertex(dan);
		graph.addVertex(huang);
		graph.addEdge(dan, huang);
		assertTrue(graph.getallprople().targets(dan).containsKey(huang));
	}
}
