package com.tgra.core;


import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;
import java.util.Arrays;
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
	private static final Random randomGenerator = new Random();
 
	public MazeGenerator(int mazeHeight, int mazeLength) {
		
		this.mazeHeight	= mazeHeight;
		this.mazeLength = mazeLength;
		
		this.cells = new Cell[mazeHeight][mazeLength ];
	
		generateMaze(this.mazeHeight, this.mazeLength);
	}
	
	/**
	 * 
	 */
	public void generatePath() {
		
		ArrayList<Cell> visitN = new ArrayList<Cell>();
		ArrayList<Cell> unvisitN = new ArrayList<Cell>();
		
		Stack<Cell> stack = new Stack<Cell>();
		for (int x = 0; x < mazeHeight-1; x++) {
			for (int y = 1; y < mazeLength; y++) {
				unvisitN.add(cells[x][y]);
			}
		}
		
		Cell currentCell = cells[0][1];
		// make currentCell visit
		unvisitN.remove(currentCell);
		visitN.add(currentCell);
		stack.add(currentCell);
		currentCell.setSouthWall(false);
		while(!unvisitN.isEmpty()) {
			
			int curX = currentCell.getPosX();
			int curY = currentCell.getPosY();
			
			ArrayList<Cell> foundN	= findNeighbours(curX, curY);
			//System.out.println("found N: "+foundN);
			
			// sort out visited neighbors
			ArrayList<Cell> unknownN = new ArrayList<Cell>();
			for(Cell n : foundN) {
				if(!visitN.contains(n)) unknownN.add(n);
			}
			if(!unknownN.isEmpty()) {	
				// choose one random from remaining
				int index = randomGenerator.nextInt(unknownN.size());
				Cell randomN = unknownN.get(index);		
						
				//System.out.println("Random N: "+randomN.getPosX()+" Y: "+randomN.getPosY());
				
				
				modifyWall(currentCell, randomN);
				
				// push randomN to visit stack and remove from unvisit
				visitN.add(randomN);
				unvisitN.remove(randomN);
				
				stack.add(randomN);
				
				// make random new current cell
				currentCell = randomN;
				
			}else {
				//if(unvisitN.isEmpty()) System.out.println("X: "+currentCell.getPosX()+" Y: "+currentCell.getPosY());
				//if(!unvisitN.isEmpty()) currentCell = unvisitN.get(0);
				
				currentCell = stack.pop();
			}	
			//this.display();
		}
		
		
		this.display();
		System.out.println("X: "+currentCell.getPosX()+" Y: "+currentCell.getPosY());
		/*for(Cell c: visitN) {
			System.out.println("X: "+c.getPosX()+" Y: "+c.getPosY());
		}*/
	}
	
	private void createExit(Cell cell) {
		
		int x = cell.getPosX();
		int y = cell.getPosY();
		
		
	}
	
	private void modifyWall(Cell currentCell, Cell neighCell) {
		
		int curX	= currentCell.getPosX();
		int curY	= currentCell.getPosY();
		int neX		= neighCell.getPosX();
		int neY		= neighCell.getPosY();
		
		if(neX == curX) {	
			if(neY > curY) this.cells[curX][curY].setWestWall(false);
			if(neY < curY) this.cells[curX][neY].setWestWall(false);
		}
		
		if(neY == curY) {
			if(neX > curX) this.cells[neX][curY].setSouthWall(false);
			if(neX < curX) this.cells[curX][curY].setSouthWall(false);
		}
	}
	
	private ArrayList<Cell> findNeighbours(int x, int y) {
		
		ArrayList<Cell> neighCells = new ArrayList<Cell>();
		
		if(x >= 0 && x < this.mazeHeight-1 && y >= 1 && y< this.mazeLength) {
			
			if(y > 1) neighCells.add(cells[x][y-1]);
			if(y < this.mazeLength-1) neighCells.add(cells[x][y+1]);
			
			if(x > 0) neighCells.add(cells[x-1][y]);
			if(x < this.mazeHeight-2) neighCells.add(cells[x+1][y]);
		}
		
		return neighCells;
	}
	
	private void generateMaze(int mazeWidth, int  mazeLength) {
		
		for (int x = 0; x < mazeWidth; x++) {
			
			for (int y = 0; y < mazeLength; y++) {
				
				this.cells[x][y] = new Cell(x,y);
			}
		}
		
		//this.cells[0][1].setSouthWall(false);
	}
 
	public void display() {
		for (int i = 0; i < this.mazeHeight; i++) {
			// draw the north edge
			for (int j = 0; j < this.mazeLength; j++) {
				
				if(this.cells[i][j].isSouthWall()) {
					System.out.print("---+");
				}else{
					System.out.print("   +");
				}
			}
			System.out.println(" ");
			
			for (int j = 0; j < this.mazeLength; j++) {
				
				if(this.cells[i][j].isWestWall()) {
					System.out.print("   |");
				}else {
					System.out.print("    ");
				}
				
			}
			
			System.out.println(" ");
		}
	}
 
	public static void main(String[] args) {
		
		MazeGenerator maze = new MazeGenerator(20, 30);
		maze.display();
		System.out.println("aa");
		
		maze.generatePath();
	}
 
}