package com.gmail.johnstraub1954.cell_automata.geometry;

import java.util.List;

public interface Neighborhood
{
	public List<Offset> getNeighbors();
	public Offset getSelf();
}
