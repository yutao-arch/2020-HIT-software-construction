/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P1.graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    // AF(vertices)=图中的点
    // AF(edges)=图中的边
    // Representation invariant:
    // edges长度是大于0的实数，有起始的节点
    // vertex点必须包含在vertices中
    // 两个点之间最多只有一条边
    // Safety from rep exposure:
    // 将vertices和edges设置为private
    // 由于vertices和edges是mutable，所以在返回他们时进行了defensive copies
    
    // TODO constructor
    public ConcreteEdgesGraph() {	//构造方法
	}
    // TODO checkRep
    private void checkRep() {
        for (Edge<L> edge : edges) {
            assert vertices.contains(edge.getsource());
            assert vertices.contains(edge.gettarget());
            assert edge.getweight()>0;
        }
    }
    
    /**
     * Add a vertex to this graph.
     * 
     * @param vertex label for the new vertex
     * @return true if this graph did not already include a vertex with the
     *         given label; otherwise false (and this graph is not modified)
     */
    @Override public boolean add(L vertex) {
    	if(vertices.contains(vertex)) {
    		System.out.println("待加入点已经存在\n");
    		return false;
    	}
    	checkRep();
    	return vertices.add(vertex);
       // throw new RuntimeException("not implemented");
    }
    
    /**
     * Add, change, or remove a weighted directed edge in this graph.
     * If weight is nonzero, add an edge or update the weight of that edge;
     * vertices with the given labels are added to the graph if they do not
     * already exist.
     * If weight is zero, remove the edge if it exists (the graph is not
     * otherwise modified).
     * 
     * @param source label of the source vertex
     * @param target label of the target vertex
     * @param weight nonnegative weight of the edge
     * @return the previous weight of the edge, or zero if there was no such
     *         edge
     */
    @Override public int set(L source, L target, int weight) {
    	int fanhui=0;
    	if(weight<0) {
    		System.out.println("权值不能为负");
    		return -1;
    	}
    	else {
    		if(weight>0) {
    			Iterator<Edge<L>> it=edges.iterator();
    			while(it.hasNext()) {  //当新边已经存在时，除去此边
    				Edge<L> a=it.next();
    				if(a.getsource().equals(source) && a.gettarget().equals(target)) {
    					fanhui=a.getweight();
    					it.remove();
    					break;
    				}
    			}
    			vertices.add(source);  //添加新边
    			vertices.add(target);
    			Edge<L> ok=new Edge<L>(source,target,weight);
    			edges.add(ok);
    		}
    		if(weight==0) {  //新边权值为0且已经存在时除去此边
    			Iterator<Edge<L>> him=edges.iterator();
    			while(him.hasNext()) {
    				Edge<L> m=him.next();
    				if (m.getsource().equals(source) && m.gettarget().equals(target)) {
    					him.remove();
    					fanhui=m.getweight();
    					break;
    			}
    		}
    	}
    	}
    	checkRep();
    	return fanhui;	
        //throw new RuntimeException("not implemented");
    }
    
    /**
     * Remove a vertex from this graph; any edges to or from the vertex are
     * also removed.
     * 
     * @param vertex label of the vertex to remove
     * @return true if this graph included a vertex with the given label;
     *         otherwise false (and this graph is not modified)
     */
    @Override public boolean remove(L vertex) {
    	if(vertices.contains(vertex)) {  //如果该点已经存在，除去该点和与该点连接的边
    		vertices.remove(vertex);
    		Iterator<Edge<L>> ll=edges.iterator();
    		while(ll.hasNext()) {
    			Edge<L> b=ll.next();
    			if(b.getsource().equals(vertex)||b.gettarget().equals(vertex)) {
    				ll.remove();
    			}
    		}
    	}
    	else {  //如果该点不存在返回false
    		return false; 
    	}
    	checkRep();
    	return true;
       // throw new RuntimeException("not implemented");   
    }
    
    /**
     * Get all the vertices in this graph.
     * 
     * @return the set of labels of vertices in this graph
     */
    @Override public Set<L> vertices() {
    	return new HashSet<L>(vertices);
        //throw new RuntimeException("not implemented");
    }
    
    /**
     * Get the source vertices with directed edges to a target vertex and the
     * weights of those edges.
     * 
     * @param target a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from that vertex to target, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         the key to target
     */
    @Override public Map<L, Integer> sources(L target) {
        Map<L,Integer> yutao=new HashMap<>();
        for(Edge<L> c:edges) {
        	if(c.gettarget().equals(target)) {   
        		yutao.put(c.getsource(),c.getweight());  //加入初始点和权值
        	}
        }
        checkRep();
        return yutao;
    	//throw new RuntimeException("not implemented");
    }
    
    /**
     * Get the target vertices with directed edges from a source vertex and the
     * weights of those edges.
     * 
     * @param source a label
     * @return a map where the key set is the set of labels of vertices such
     *         that this graph includes an edge from source to that vertex, and
     *         the value for each key is the (nonzero) weight of the edge from
     *         source to the key
     */
    @Override public Map<L, Integer> targets(L source) {
    	 Map<L,Integer> yutao=new HashMap<>();
         for(Edge<L> c:edges) {
         	if(c.getsource().equals(source)) {   
         		yutao.put(c.gettarget(),c.getweight());  //加入初始点和权值
         	}
         }
         checkRep();
         return yutao;
    	//throw new RuntimeException("not implemented");
    }
    
    // TODO toString()
    /**
     * @return 返回整个图连成的字符串
     */
    @Override public String toString() {
    	String mm = "";
		for (Edge<L> p : edges) {
			mm = mm + p.toString();
		}
		return mm;
    }
    
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge<L> {
    
    // TODO fields
	private final L source,target;  //边的两个点
	private final int weight; //边的权值
    
    // Abstraction function:
	// AF(source) = 边的起点
    // AF(target) = 边的终点
    // AF(weight) = 边的权值
    // Representation invariant:
    // weight >=0 source和target不为空
    // Safety from rep exposure:
    // 将source,target,weight设置为private
    
    // TODO constructor
    
	/**
	 * 初始化构造方法，初始化新边的两个点和边的权值
	 * @param source2  新边的一个点
	 * @param target2  新边的另外一个点
	 * @param wei  新边的权值
	 */
    public Edge(L source2,L target2,int wei) {
    	source=source2;
    	target=target2;
    	weight=wei;
    	checkRep();
    }
    // TODO checkRep
	
    /**
     * 检查表示不变性,边不为空且权值大于等于0
     */
    public void checkRep(){
    	assert source!=null;
    	assert target!=null;
    	assert weight>=0;
    }
    
    /**
     * 
     * @return 边的一个点source
     */
    public L getsource() {
    	return source;
    }
    
    /**
     * 
     * @return 边的另外点target
     */
    public L gettarget() {
    	return target;
    }
    
    /**
	 * 
	 * @return 返回边的weight
	 */
	public int getweight() {
		checkRep();
		return weight;
	}
	
	// TODO toString()
    /**
     * 返回一条边的字符串
     */
	@Override
	public String toString() {
		return source.toString() + "->" + target.toString() + "权重为" + weight + '\n';
	}
	

}
