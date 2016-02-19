package com.routing.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.routing.util.ObjectUtil;
import com.thoughtworks.xstream.XStream;

/**
 * graph representation for the 
 * @author ramkumarsundarajan
 *
 */
public class Graph {
	
	private static final String ROUTE_SPLITTER = "-";
	
	/**
	 * list of nodes/vertex in the graph
	 */
	private List<TownNode> nodeList;
	
	/**
	 * list of edge/routes in the graph
	 */
	private List<RouteEdge> edgeList;
	
	/**
	 * starting point of the node to start from. 
	 */
	private TownNode rootNode;
	
	
	public Graph(){
		/* initialize empty node and edge list*/
		nodeList = new ArrayList<TownNode>();
		edgeList = new ArrayList<RouteEdge>();
	}
	
	/**
	 * adds edge/route to the graph.
	 * assumes the sample edge format => AB6
	 * Node is one char length is assumed.
	 * so in AB6 , there is node A, node B, and distance between is 6.
	 * @param edge
	 * @return
	 */
	public void addEdge(String edge) {
		
		String startName = ObjectUtil.charToString(edge.charAt(0));
		String endName = ObjectUtil.charToString(edge.charAt(1));
		
		/* see if it is already available */
		TownNode startNode = getTownByName(startName);
		TownNode endNode = getTownByName(endName);
		
		if(startNode == null)
			startNode = new TownNode(startName);
		
		if(endNode == null)
			endNode = new TownNode(endName);
		
		
		int distance = ObjectUtil.charToInt(edge.charAt(2));
		
		RouteEdge routeEdge = new RouteEdge(startNode, endNode, distance);
		
		/* adding the incoming and outgoing edges/routes */
		startNode.addIORoute(routeEdge);
		endNode.addIORoute(routeEdge);
		
		/* add it to the graph node list */
		this.addNode(startNode);
		this.addNode(endNode);
		
		/* add it to the graph's route list */
		this.addRoute(routeEdge);
	}
	
	
	
	
	/**
	 * start traversing from this node which is passed to this method.
	 * using adjacency lists => BFS 
	 * @param townnode is set as the root node for this traversal.
	 * @param townNode
	 */	
	public void traverseRoutes(TownNode townNode) {
		
		/* reset visits */
		clearNodeVisits();
		
		this.setRootNode(townNode);
		LinkedList<TownNode> nodeList = new LinkedList<TownNode>();
		nodeList.add(townNode);
		
		/*flagging this node off */
		townNode.setVisited(true);
		
		/**
		 * this loop will run in O(|RouteEdge|)
		 * runs for all the edges.
		 */
		while(nodeList.isEmpty() == false){
			
			/* remove from queue / linkedlist impl of queue */
			nodeList.removeFirst();
			/* not sure of passing this value for parent depth */
			townNode.calculateLength(this, townNode);
			/* go and visit all the neighbouring nodes */
			for(RouteEdge edge: townNode.getOutgoingNodeList()){
				
				/*node in the route's destination*/
				TownNode adjacentNode = edge.getTargetNode();
				/*if node is not already visited from here only then do something */
				if(!adjacentNode.isVisited()){
					
					adjacentNode.setVisited(true);
					
					/*do something here */
					adjacentNode.calculateLength(this, adjacentNode);
					/*add this node for look up next */
					nodeList.add(townNode);
				}
			}
		}
	}       
	
	 
	/**
	 * can I maintain the node list in a hashmap for quick retrieval ??
	 * find the town by Name from graph.
	 * returns null if no node of the given name is found.
	 * @param name
	 * @return
	 */
	public TownNode getTownByName(String name){
		if(this.nodeList!=null && !this.nodeList.isEmpty()){
			for(TownNode node:this.nodeList){
				if(node.getName().equalsIgnoreCase(name)){
					return node;
				}
			}
		}
		return null;
	}
	
	/**
	 * clears the node visit done in the previous traversals.
	 * @return
	 */
	private boolean clearNodeVisits(){
		
		for(TownNode node : this.nodeList){
			node.setVisited(false);
		}
		return true;
	}
	
	
	/**
	 * route => A-B-C
	 * assuming input is given in this format.
	 * @param route
	 */
	public String getDistance(String route){
		String[] townNames = route.split(ROUTE_SPLITTER);
		TownNode[] townNodeArray = new TownNode[townNames.length];
		/*order will be maintained */
		for(int i=0;i<townNames.length; i++){
			townNodeArray[i] = this.getTownByName(townNames[i]);
		}
		int distance = findDistance(townNodeArray);
		return (distance == 0) ? "no route exists": String.valueOf(distance);
		
		
	}
	
	/**
	 * A-B-C
	 * @param graph
	 * @param townNode
	 * @param nodeArray
	 */
	private int findDistance(TownNode[] nodeArray) {
		
		clearNodeVisits();
		
		int depth = 0, distance = 0;
		
		TownNode sourceNode = nodeArray[depth];
		TownNode destinationNode = nodeArray[nodeArray.length - 1];
		
		LinkedList<TownNode> nodeList = new LinkedList<TownNode>();
		nodeList.add(sourceNode);
		
		/* this flag indicates if the route exists or not */
		boolean routeFlag = false;
		
		/**
		 * this loop will run in O(|RouteEdge|)
		 * runs for all the edges.
		 */
		while(nodeList.isEmpty() == false){

			/* remove from queue / linkedlist impl of queue */
			sourceNode = nodeList.removeFirst();
			
			/* increamenting it to the next in the array parameter */
			depth = depth + 1;
			
			/* reset the flag */
			routeFlag = false;
			/* go and visit all the neighbouring nodes */
			for(RouteEdge edge: sourceNode.getOutgoingNodeList()){

				/*node in the route's destination*/
				TownNode adjacentNode = edge.getTargetNode();
				
				if(adjacentNode.equals(nodeArray[depth])) {
					routeFlag = true;
					distance = distance + edge.getDistance();
					if(!nodeArray[depth].equals(destinationNode)){
						nodeList.add(nodeArray[depth]);
					}
				}
			} // edge loop 
			
			if(!routeFlag) {
				return 0;
			}
			
		} // while loop 
		
		return distance;
	}       
	
	
	
	
	
	/**
	 * adds the passed node to the graph.
	 * 
	 * @param node
	 */
	private void addNode(TownNode node){
		if(this.nodeList!=null){
			//TODO: need to override the equals in node.
			if(!this.nodeList.contains(node)){
				this.nodeList.add(node);
			}
		}
	}
	
	/**
	 * add this edge/route to the graph's routelist
	 * checks if this route already in the graph before adding the route.
	 * @param routeEdge
	 */
	private void addRoute(RouteEdge routeEdge){
		if(this.edgeList!=null){
			if(!this.edgeList.contains(routeEdge)) {
				this.edgeList.add(routeEdge);
			}
		}
	}

	public List<TownNode> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<TownNode> nodeList) {
		this.nodeList = nodeList;
	}

	public List<RouteEdge> getEdgeList() {
		return edgeList;
	}

	public void setEdgeList(List<RouteEdge> edgeList) {
		this.edgeList = edgeList;
	}

	public TownNode getRootNode() {
		return rootNode;
	}

	public void setRootNode(TownNode rootNode) {
		this.rootNode = rootNode;
	}
	
	

}
