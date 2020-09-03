package miaoyu.helper;
import java.util.*;

/**
 * This class represents Route for VRPCC 
 * 
 * @author Miao Yu
 * @since May 3, 2017
 *
 */

public class Route {
	public ArrayList<Integer> route;
	public double cost;
	public double reduceCost;
	public double sol;
	public int vehicleIndex;
	public boolean inPool;
	public boolean removed = false;
	public int lastActive = -1;
	/*
	 * constructor from a list of integer
	 */
	public Route(ArrayList<Integer> lst){
		route = new ArrayList<Integer>(8);
		for(int customer : lst){
			route.add(customer);
		}
		inPool = false;
		lastActive = -1;
	}

	public Route(){
		route = new ArrayList<>();
	}
	/*
	 * update the cost of the route
	 */
	public void updateCost(double[][] dist){
		int current = 0;
		this.cost = 0;
		int i, next;
		for(i = 0; i < route.size(); i++){
			next = route.get(i);
			cost += dist[current][next];
			current = next;
		}
		cost += dist[current][0];
	}

	public void updateCost(int root, double[][] dist){
		int current = root;
		this.cost = 0;
		int i, next;
		for(i = 0; i < route.size(); i++){
			next = route.get(i);
			cost += dist[current][next];
			current = next;
		}
		cost += dist[current][root];
	}

	/*
	 * update reduceCost of the route
	 */
	public void updateReduceCost(double[][] dist){
		int current = 0;
		int i, next;
		for(i = 0; i < route.size(); i++){
			next = route.get(i);
			reduceCost += dist[current][next];
			current = next;
		}
		reduceCost += dist[current][0];
	}


	/*
	 * check if route contains edge (i,j)
	 */
	public boolean contains(int i, int j){
		if(i == 0 && (j == route.get(0) || j == route.get(route.size() - 1)))
			return true;
		if(j == 0 && (i == route.get(route.size()-1)|| i == route.get(0)))
			return true;

		int index = route.indexOf(i);

		if(index == -1)
			return false;
		if(index < route.size() - 1){
			if(route.get(index + 1 ) == j)
				return true;
		}

		for(int temp = index + 1; temp < route.size() - 1; temp++){
			if(route.get(temp) == i){
				if(index < route.size() - 1){
					if(route.get(index + 1 ) == j)
						return true;
				}
			}
		}

		index = route.indexOf(j);
		if(index == -1)
			return false;
		if(index < route.size() - 1){
			if(route.get(index + 1 ) == i)
				return true;
		}
		for(int temp = index + 1; temp < route.size() - 1; temp++){
			if(route.get(temp) == j){
				if(index < route.size() - 1){
					if(route.get(index + 1 ) == i)
						return true;
				}
			}
		}
		
		return false;
	}

	public boolean contains(int i){
		return this.route.contains(i);
	}

	@Override
	public boolean equals(Object other) {
		if (!(other instanceof Route)) {
			return false;
		}

		Route that = (Route) other;

		// Custom equality check here.
		return this.route.equals(that.route)
				&& this.vehicleIndex==that.vehicleIndex;
	}

	@Override
	public int hashCode() {
		int hashCode = 1;

		hashCode = hashCode * 37 + this.route.hashCode();
		hashCode = hashCode * 37 + this.vehicleIndex;
		return hashCode;
	}

}
