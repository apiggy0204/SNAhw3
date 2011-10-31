package sna.model;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

import sna.model.helper.MyLink;
import sna.model.helper.MyNode;
import sna.model.helper.ReadGraph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;

public abstract class Model {

	protected UndirectedGraph<MyNode,MyLink> graph = new UndirectedSparseGraph<MyNode,MyLink>(); 	
	protected Map<Integer, MyNode> map = new TreeMap<Integer, MyNode>();
	
	public Map<Integer, MyNode> getMap() {
		return map;
	}

	// get Initial Graph
	public void getGraph(String s)throws IOException{
		ReadGraph reader = new ReadGraph(s);
		ArrayList<Integer[]> edges = reader.getEdges();
		for (Integer[] temp: edges){
			MyNode node0 = map.get(temp[0]);
			if(node0 == null){ 
				node0 = new MyNode(temp[0]);
				map.put(temp[0], node0);
			}
			MyNode node1 = map.get(temp[1]);
			if(node1 == null){ 
				node1 = new MyNode(temp[1]);
				map.put(temp[1], node1);
			}
			graph.addEdge(new MyLink(), node0, node1);
			//System.out.println(temp[0]+"   "+temp[1]);
		}
	}
	
	
	public void init(String graphFileName, String revealFileName)throws IOException{
		getGraph(graphFileName);
	}

	public void output(String outFileName)throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(outFileName));
		if (writer!=null){
			writer.close();
		}
	}
	
	// default constructor
	public Model(){
		
	}
	// copy constructor
	public Model(Model model){
		graph=new UndirectedSparseGraph<MyNode,MyLink>();
		map=new TreeMap<Integer,MyNode>();
		for (MyLink link:model.graph.getEdges()){
			MyNode node1=map.get(model.graph.getSource(link).getId());
			MyNode node2=map.get(model.graph.getDest(link).getId());
			if (node1==null){
				node1=new MyNode(model.graph.getSource(link));
				map.put(model.graph.getSource(link).getId(), node1);
			}
			if (node2==null){
				node2=new MyNode(model.graph.getDest(link));
				map.put(model.graph.getDest(link).getId(), node2);
			}
			graph.addEdge(new MyLink(link.getWeight()), node1, node2);
		}
		
	}
	

	public UndirectedGraph<MyNode,MyLink> getGraph() {
		return graph;
	}

	public void setGraph(UndirectedGraph<MyNode,MyLink> graph) {
		this.graph = graph;
	}
}
	