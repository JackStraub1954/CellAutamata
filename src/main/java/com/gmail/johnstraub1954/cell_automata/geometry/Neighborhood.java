package com.gmail.johnstraub1954.cell_automata.geometry;

import java.util.List;

/**
 * Specifies the behavior needed 
 * for an external class
 * to interact with any type of neighborhood.
 * 
 * @author Jack S.
 */
public interface Neighborhood
{
	/**
	 * Get the list of neighbors of a base cell.
	 * @return the list of neighbors of a base cell
	 */
	public List<Offset> getNeighbors();
	
	/**
	 * Get the base cell of a neighborhood.
	 * 
	 * @return the base cell of a neighborhood
	 */
	public Offset getSelf();
}
