package com.routing.model;

import java.util.ArrayList;
import java.util.List;

/**
 * represents the town/node/vertex
 * @author ramkumarsundarajan
 *
 */
public class TownNode {
	
	private String name;
	
	private List<RouteEdge> outgoingRouteList;
	
	private List<RouteEdge> incomingRouteList;
	
	private boolean visited;
	
	protected int depth;
	
	
	
	/**
	 * name => name of the node/vertex
	 * isStartNode => boolean true if this is the starting point of the edge/route.
	 * @param name
	 * @param isStartNode
	 */
	public TownNode(String name){
		this.name = name;
		this.visited = false;
		this.incomingRouteList = new ArrayList<RouteEdge>();
		this.outgoingRouteList = new ArrayList<RouteEdge>();
	}
	
	/**
	 * adds to node's incoming and outgoing route list.
	 * @param routeEdge
	 * @return
	 */
	public boolean addIORoute(RouteEdge routeEdge){
		
		if(routeEdge.getSourceNode().equals(this)){
			this.outgoingRouteList.add(routeEdge);
		} else {
			this.incomingRouteList.add(routeEdge);
		}
		return true;
	}
	
	public void calculateLength(Graph graph,TownNode node){
		if(graph.getRootNode().equals(this)){
			//this is the parent. set depth as 0
			this.depth = 0;
		} else {
			//add depth
			this.depth = node.depth + 1;
		}
	}
	
	/**
	 * prints the IO list
	 */
	public void printOutIOList() {
		System.out.println("prinitng out list ");
		for(RouteEdge routeEdge :this.outgoingRouteList) {
			
			System.out.println("source => "+routeEdge.getSourceNode().getName()+" target => "+routeEdge.getTargetNode().getName()
					+"distance =>"+routeEdge.getDistance());
		}
		
		System.out.println("prinitng In list ");
		for(RouteEdge routeEdge :this.incomingRouteList) {
			
			System.out.println("source => "+routeEdge.getSourceNode().getName()+" target => "+routeEdge.getTargetNode().getName()
					+"distance =>"+routeEdge.getDistance());
		}
	}
	
	
	
	
	public List<RouteEdge> getOutgoingNodeList() {
		return outgoingRouteList;
	}


	public void setOutgoingNodeList(List<RouteEdge> outgoingNodeList) {
		this.outgoingRouteList = outgoingNodeList;
	}


	public List<RouteEdge> getIncomingNodeList() {
		return incomingRouteList;
	}

	public void setIncomingNodeList(List<RouteEdge> incomingNodeList) {
		this.incomingRouteList = incomingNodeList;
	}


	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	

}
