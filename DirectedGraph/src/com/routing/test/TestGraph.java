package com.routing.test;

import com.routing.model.Graph;
import com.routing.model.TownNode;

public class TestGraph {

	public static void main(String[] args) {

		Graph graph = new Graph();
		graph.addEdge("AB5");
		graph.addEdge("BC4");
		graph.addEdge("CD8");
		graph.addEdge("DC8");
		graph.addEdge("DE6");
		graph.addEdge("AD5");
		graph.addEdge("CE2");
		graph.addEdge("EB3");
		graph.addEdge("AE7");
		
//		TownNode nodeE = graph.getTownByName("A");
//		nodeE.printOutIOList();
//		nodeE = graph.getTownByName("B");
//		nodeE.printOutIOList();
//		nodeE = graph.getTownByName("C");
//		nodeE.printOutIOList();
//		nodeE = graph.getTownByName("D");
//		nodeE.printOutIOList();
//		nodeE = graph.getTownByName("E");
//		nodeE.printOutIOList();
	
		
		String distance = graph.getDistance("A-B-C");
		
		System.out.println("distance of A-B-C  =>"+distance);
		
		distance = graph.getDistance("A-D");
		
		System.out.println("distance of A-D =>"+distance);
		
		distance = graph.getDistance("A-D-C");
		
		System.out.println("distance of A-D-C =>"+distance);
		
		distance = graph.getDistance("A-E-B-C-D");
		
		System.out.println("distance of A-E-B-C-D =>"+distance);
		
		distance = graph.getDistance("A-E-D");
		
		System.out.println("distance of A-E-D =>"+distance);
		
	}

}
