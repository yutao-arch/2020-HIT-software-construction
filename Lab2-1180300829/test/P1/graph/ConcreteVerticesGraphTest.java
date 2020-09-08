/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<String>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    // 测试三个点是否能够toString
    
    // TODO tests for ConcreteVerticesGraph.toString()
    @Test
    public void testConcreteEdgesGraphtoString() {
    	Graph<String> graph =emptyInstance();
    	graph.add("a");
    	graph.add("b");
    	graph.add("c");
    	graph.set("a", "b", 5);
    	graph.set("b", "c", 4);
    	graph.set("c", "a", 3);
    	assertEquals("点a，指向它的点和边的权值为：{c=3}，它指向的点和边的权值为：{b=5}\n"+"点b，指向它的点和边的权值为：{a=5}，它指向的点和边的权值为：{c=4}\n"+"点c，指向它的点和边的权值为：{b=4}，它指向的点和边的权值为：{a=3}\n",graph.toString());
    } 
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    // 只需要测试Vertex中的每一种方法即可
    
    // TODO tests for operations of Vertex
    
    /* Testing strategy
     * 测试点的返回值即可
     */
    @Test
    public void testGetmark() {
    	Vertex<String> vertex = new Vertex<String>("a");
		assertEquals("a", vertex.getmark());
	}
    
    /* Testing strategy
     * 测试点的所有源点和边返回值即可
     */
    @Test
	public void testGetsource() {
    	Vertex<String> vertex = new Vertex<String>("a");
		vertex.addsource("b", 5);
		vertex.addsource("c", 3);
    	Map<String,Integer> map=new HashMap<String,Integer>();
    	map.put("b",5);
    	map.put("c",3);
    	assertEquals(map, vertex.getsource());
    }
    
    /* Testing strategy
     * 测试点的所有终点和边返回值即可
     * 覆盖每个取值如下：
     */
    @Test
	public void testGettarget() {
    	Vertex<String> vertex = new Vertex<String>("a");
		vertex.addtarget("b", 5);
		vertex.addtarget("c", 3);
    	Map<String,Integer> map=new HashMap<String,Integer>();
    	map.put("b",5);
    	map.put("c",3);
    	assertEquals(map, vertex.gettarget());
    }
    
    /* Testing strategy
     * 按照加入的点划分：点已经存在，点不存在
     * 按照加入的权值划分：权值为0，权值大于0，权值小于0
     * 覆盖每个取值如下：
     */
    @Test
	public void testAddsource() {
    	Vertex<String> vertex = new Vertex<String>("a");
    	assertEquals(0, vertex.addsource("b", 5));
    	assertEquals(0, vertex.addsource("c", 3));
    	assertEquals(5, vertex.addsource("b", 4));
    	assertEquals(4, vertex.addsource("b", 0));
    	assertEquals(-1, vertex.addsource("b", -1));
    }
    
    /* Testing strategy
     * 按照加入的点划分：点已经存在，点不存在
     * 按照加入的权值划分：权值为0，权值大于0，权值小于0
     */
    @Test
	public void testAddtarget() {
    	Vertex<String> vertex = new Vertex<String>("a");
    	assertEquals(0, vertex.addtarget("b", 5));
    	assertEquals(0, vertex.addtarget("c", 3));
    	assertEquals(5, vertex.addtarget("b", 4));
    	assertEquals(4, vertex.addtarget("b", 0));
    	assertEquals(-1, vertex.addtarget("b", -1));
    }
    
    /* Testing strategy
     * 按照移除的点划分：点已经存在，点不存在
     * 覆盖每个取值如下：
     */
    @Test
	public void testRemovesource() {
    	Vertex<String> vertex = new Vertex<String>("a");
    	vertex.addsource("b", 5);
    	vertex.addsource("c", 3);
    	assertEquals(0, vertex.removesource("d"));
    	assertEquals(5, vertex.removesource("b"));
    }
    
    /* Testing strategy
     * 按照移除的点划分：点已经存在，点不存在
     * 覆盖每个取值如下：
     */
    @Test
	public void testRemovetarget() {
    	Vertex<String> vertex = new Vertex<String>("a");
    	vertex.addtarget("b", 5);
    	vertex.addtarget("c", 3);
    	assertEquals(0, vertex.removetarget("d"));
    	assertEquals(5, vertex.removetarget("b"));
    }
}
