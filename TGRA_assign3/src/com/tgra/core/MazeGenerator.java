package com.tgra.core;


import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import com.tgra.model.Cell;
 
/*

The depth-first search algorithm of maze generation is frequently implemented using backtracking:
Start with all walls in the maze intact

Make the initial cell the current cell and mark it as visited
	While there are unvisited cells
		If the current cell has any neighbours which have not been visited
			Choose randomly one of the unvisited neighbours
			Push the chosen cell to the stack
			Remove the wall between the current cell and the chosen cell
			Make the chosen cell the current cell and mark it as visited
		Else
			Pop a cell from the stack
			Make it the current cell
Repeat this until the stack is empty.
 */
public class MazeGenerator {
	
	private int mazeHeight;
	private int mazeLength;
	private Cell[][] cells;
	private ArrayList<Cell> visitN;
	private ArrayList<Cell> unvisitN;
	private Stack<Cell> traceStack;
	private static final Random randomGenerator = new Random();
 
	public MazeGenerator(int mazeHeight, int mazeLength) {
		
		this.mazeHeight	= mazeHeight;
		this.mazeLength = mazeLength;
		
		this.cells = new Cell[mazeHeight][mazeLength ];
	
		generateCellsWithWalls();
	}
	
	/**
	 * 
	 */
	public void generatePath() {
		
		// create needed caches
		visitN		= new ArrayList<Cell>();
		unvisitN	= new ArrayList<Cell>();
		traceStack	= new Stack<Cell>();
		
		// mark all cells als unvisited
		for (int x = 0; x < mazeHeight-1; x++) {
			for (int y = 1; y < mazeLength; y++) {
				unvisitN.add(cells[x][y]);
			}
		}
		// make first cell visit
		Cell currentCell = this.updateSearchCaches(cells[0][1]);
		currentCell.setSouthWall(false);
		
		while(!unvisitN.isEmpty()) {
			
			// find all existing neighbors
			ArrayList<Cell> foundN	= findNeighbours(currentCell);
			
			// sort out visited neighbors
			ArrayList<Cell> unknownN = new ArrayList<Cell>();
			for(Cell n : foundN) {
				if(!visitN.contains(n)) unknownN.add(n);
			}
			
			if(!unknownN.isEmpty()) {	
				// choose one random from remaining
				int index = randomGenerator.nextInt(unknownN.size());
				Cell randomN = unknownN.get(index);		
				// remove connecting wall
				modifyWall(currentCell, randomN);	
				// make random new current cell
				currentCell = this.updateSearchCaches(randomN);
				
			}else {
				currentCell = traceStack.pop();
			}	
		}
	}
	
	/**
	 * Draws the generated maze
	 * primary for debugging
	 */
	public void display() {
		for (int i = 0; i < this.mazeHeight; i++) {
			for (int j = 0; j < this.mazeLength; j++) {	
				System.out.print( this.cells[i][j].isSouthWall() ? "---+" : "   +");
			}
			
			System.out.println(" ");
			
			for (int j = 0; j < this.mazeLength; j++) {
				System.out.print( this.cells[i][j].isWestWall() ? "   |" : "    ");
			}
			
			System.out.println(" ");
		}
	}
	
/******************************************** PRIVATE METHODS ************************************************/	
	
	/**
	 * Updates the unvisit / visit cache
	 * also push the cell on the trace stack
	 * 
	 * @param newCell the new cell to cache
	 * 
	 * @return the new cell
	 */
	private Cell updateSearchCaches(Cell newCell) {
		unvisitN.remove(newCell);
		visitN.add(newCell);
		traceStack.add(newCell);
		
		return newCell;
	}
	
	/**
	 * Generates a cell array with the given sizes
	 * the generated cells have all walls set.
	 */
	private void generateCellsWithWalls() {
			
		for (int x = 0; x < mazeHeight; x++) {	
			for (int y = 0; y < mazeLength; y++) {
					
				this.cells[x][y] = new Cell(x,y);
			}
		}
	}
	
	/**
	 * Modify the wall of the cell
	 * to connect it with the neighbor cell
	 * 
	 * @param currentCell the current cell
	 * @param neighCell the neighbor cell
	 */
	private void modifyWall(Cell currentCell, Cell neighCell) {
		
		int cuX	= currentCell.getPosX();
		int cuY	= currentCell.getPosY();
		int neX	= neighCell.getPosX();
		int neY	= neighCell.getPosY();
		
		if(neX == cuX) {	
			if(neY > cuY) this.cells[cuX][cuY].setWestWall(false);
			if(neY < cuY) this.cells[cuX][neY].setWestWall(false);
		}
		
		if(neY == cuY) {
			if(neX > cuX) this.cells[neX][cuY].setSouthWall(false);
			if(neX < cuX) this.cells[cuX][cuY].setSouthWall(false);
		}
	}
	
	/**
	 * Finds all neighbor cell 
	 * of the given cell
	 * 
	 * @param cell the cell to find the neighbors for
	 * 
	 * @return a ArrayList either containing the neighbors or empty
	 */
	private ArrayList<Cell> findNeighbours(Cell cell) {
		
		int x = cell.getPosX();
		int y = cell.getPosY(); 
		
		ArrayList<Cell> neighCells = new ArrayList<Cell>();
		
		if(x >= 0 && x < this.mazeHeight-1 && y >= 1 && y< this.mazeLength) {
			
			if(y > 1) neighCells.add(cells[x][y-1]);
			if(y < this.mazeLength-1) neighCells.add(cells[x][y+1]);
			
			if(x > 0) neighCells.add(cells[x-1][y]);
			if(x < this.mazeHeight-2) neighCells.add(cells[x+1][y]);
		}
		
		return neighCells;
	}
	
 
	
 
	public static void main(String[] args) {
		
		MazeGenerator maze = new MazeGenerator(20, 30);
		maze.display();
		
		System.out.println("\n\n\n");
		
		maze.generatePath();
		
		maze.display();
	}
 
}