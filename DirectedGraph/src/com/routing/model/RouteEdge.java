package com.routing.model;

/**
 * represents edge/path/route
 * @author ramkumarsundarajan
 *
 */
public class RouteEdge {
	
	/**
	 * weight of the edge
	 */
	private int distance;
	
	/**
	 * edge start point
	 */
	private TownNode sourceNode;
	
	/**
	 * edge end point
	 */
	private TownNode targetNode;
	

	/**
	 * start node/vertex, end node/vertex, distance/weight of the edge.
	 * @param start
	 * @param end
	 * @param distance
	 */
	public RouteEdge(TownNode start, TownNode end, int distance){
		this.sourceNode = start;
		this.targetNode = end;
		this.distance = distance;
	}
	

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public TownNode getSourceNode() {
		return sourceNode;
	}

	public void setSourceNode(TownNode sourceNode) {
		this.sourceNode = sourceNode;
	}

	public TownNode getTargetNode() {
		return targetNode;
	}

	public void setTargetNode(TownNode targetNode) {
		this.targetNode = targetNode;
	}



}
