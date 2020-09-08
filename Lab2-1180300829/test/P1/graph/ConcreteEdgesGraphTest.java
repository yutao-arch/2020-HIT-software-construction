/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph<String>();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    // 测试三个点是否能够toString
    
    // TODO tests for ConcreteEdgesGraph.toString()
    @Test
    public void testConcreteEdgesGraphtoString() {
    	Graph<String> graph =emptyInstance();
    	graph.add("a");
    	graph.add("b");
    	graph.add("c");
    	graph.set("a", "b", 5);
    	graph.set("b", "c", 4);
    	graph.set("a", "c", 3);
    	assertEquals("a->b权重为5\n"+"b->c权重为4\n"+"a->c权重为3\n",graph.toString());
    }
    
    /*
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    // 只需要测试Edge中的每一种方法即可
    
    // TODO tests for operations of Edge
    
    /* Testing strategy
     * 测试终点的返回值即可
     */
    @Test
    public void testGetsource() {
    	Edge<String> one=new Edge<String>("a","b",5);
    	assertEquals("a", one.getsource());
    }
    
    /* Testing strategy
     * 测试源点的返回值即可
     */
    @Test
    public void testGettarget() {
    	Edge<String> one=new Edge<String>("a","b",5);
    	assertEquals("b", one.gettarget());
    }
    
    /* Testing strategy
     * 测试权值的返回值即可
     */
    @Test
    public void testGetweight() {
    	Edge<String> one=new Edge<String>("a","b",5);
    	assertEquals(5, one.getweight());
    }
    
}
