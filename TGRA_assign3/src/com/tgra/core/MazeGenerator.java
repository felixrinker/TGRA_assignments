package com.tgra.core;


import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Arrays;

import com.tgra.model.Cell;
 
/*
 * recursive backtracking algorithm
 * shamelessly borrowed from the ruby at
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 * Recursive backtracker
 * 
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
	private static final Random randomGenerator = new Random();
 
	public MazeGenerator(int mazeHeight, int mazeLength) {
		
		this.mazeHeight	= mazeHeight;
		this.mazeLength = mazeLength;
		
		this.cells = new Cell[mazeHeight][mazeLength ];
	
		generateMaze(this.mazeHeight, this.mazeLength);
	}
	
	
	public void generatePath() {
		
		ArrayList<Cell> visitN = new ArrayList<Cell>();
		
		for (int x = 0; x < mazeHeight; x++) {
			
			for (int y = 1; y < mazeLength; y++) {
				
				if(this.cells[x][y].isSouthWall()) {
					//System.out.print("+---");
					
					
					ArrayList<Cell> foundN	= findNeighbours(x, y);
					//System.out.println("found N: "+foundN);
					
					// sort out visited neighbors
					ArrayList<Cell> unknownN = new ArrayList<Cell>();
					for(Cell n : foundN) {
						if(!visitN.contains(n)) unknownN.add(n);
					}
					
					// choose one random from remaining
					int index = this.randomGenerator.nextInt(unknownN.size());
					Cell randomN = unknownN.get(index);		
							
					System.out.println("Random N: "+randomN);
					
					
				}
			}
			System.out.println("+");
			
			if(x < (mazeHeight-1)) {
				for (int j = 0; j < this.mazeLength; j++) {
			
				
				if(this.cells[x][j].isWestWall()) {
					System.out.print("|   ");
				}
				
			}
			}
			System.out.println(" ");
		}
		
	}
	
	private ArrayList<Cell> findNeighbours(int x, int y) {
		
		ArrayList<Cell> neighCells = new ArrayList<Cell>();
		
		if(x >= 0 && x< this.mazeHeight && y >= 1 && y< this.mazeLength) {
			
			if(y > 1) neighCells.add(cells[x][y-1]);
			if(y < this.mazeLength-1) neighCells.add(cells[x][y+1]);
			
			if(x > 0) neighCells.add(cells[x-1][y]);
			if(x < this.mazeHeight-1) neighCells.add(cells[x+1][y]);
		}
		
		return neighCells;
	}
	
	private void generateMaze(int mazeWidth, int  mazeLength) {
		
		for (int i = 0; i < mazeWidth; i++) {
			
			for (int j = 0; j < mazeLength; j++) {
				
				this.cells[i][j] = new Cell();
			}
		}
		
	}
 
	public void display() {
		for (int i = 0; i < this.mazeHeight; i++) {
			// draw the north edge
			for (int j = 0; j < this.mazeLength; j++) {
				
				if(this.cells[i][j].isSouthWall()) {
					System.out.print("---+");
				}
			}
			System.out.println(" ");
			
			for (int j = 0; j < this.mazeLength; j++) {
				
				if(this.cells[i][j].isWestWall()) {
					System.out.print("   |");
				}
				
			}
			
			System.out.println(" ");
		}
	}
 
	
 
	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}
 
	
 
	public static void main(String[] args) {
		
		MazeGenerator maze = new MazeGenerator(5, 5);
		maze.display();
		System.out.println("aa");
		
		maze.generatePath();
	}
 
}