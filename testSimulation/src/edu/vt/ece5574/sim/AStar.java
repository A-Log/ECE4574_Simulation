package edu.vt.ece5574.sim;
import java.util.*;
import sim.field.grid.*;
import sim.util.Int2D;


/* Author: Tyler Olson
 * Description: File containing the implementation of
 * the A* algortihm.
 * Usage:  To use the algorithm call the findPath function
 * and pass in the proper parameters.  The function will
 * return a Stack<Int2D> that will contain the path from
 * the start to destination.
 *
 */

public class AStar
{
    public static final int DIAGONAL_COST = 14;
    public static final int V_H_COST = 10;

    static class Cell
    {
        int heuristicCost = 0; //Heuristic cost
        int finalCost = 0; //G+H
        int i, j;
        Cell parent;

        Cell(int i, int j)
        {
            this.i = i;
            this.j = j;
        }

        @Override
        public String toString()
        {
            return "["+this.i+", "+this.j+"]";
        }
    }

    //Blocked cells are just null Cell values in grid
    static Cell [][] grid = new Cell[5][5];

    static PriorityQueue<Cell> open;

    static boolean closed[][];
    static int startI, startJ;
    static int endI, endJ;

    public static void setBlocked(int i, int j)
    {
        grid[i][j] = null;
    }

    public static void setStartCell(int i, int j)
    {
        startI = i;
        startJ = j;
    }

    public static void setEndCell(int i, int j)
    {
        endI = i;
        endJ = j;
    }

    static void checkAndUpdateCost(Cell current, Cell t, int cost)
    {
        if(t == null || closed[t.i][t.j])return;
        int t_final_cost = t.heuristicCost+cost;

        boolean inOpen = open.contains(t);
        if(!inOpen || t_final_cost<t.finalCost)
        {
            t.finalCost = t_final_cost;
            t.parent = current;
            if(!inOpen)open.add(t);
        }
    }

    @SuppressWarnings("javadoc")
    public static void AStarm()
    {
        //add the start location to open list.
        open.add(grid[startI][startJ]);

        Cell current;

        while(true)
        {
            current = open.poll();
            if(current==null)break;
            closed[current.i][current.j]=true;

            if(current.equals(grid[endI][endJ]))
            {
                return;
            }

            Cell t;
            if(current.i-1>=0)
            {
                t = grid[current.i-1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);

                if(current.j-1>=0)
                {
                    t = grid[current.i-1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
                }

                if(current.j+1<grid[0].length)
                {
                    t = grid[current.i-1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
                }
            }

            if(current.j-1>=0)
            {
                t = grid[current.i][current.j-1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);
            }

            if(current.j+1<grid[0].length)
            {
                t = grid[current.i][current.j+1];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);
            }

            if(current.i+1<grid.length)
            {
                t = grid[current.i+1][current.j];
                checkAndUpdateCost(current, t, current.finalCost+V_H_COST);

                if(current.j-1>=0)
                {
                    t = grid[current.i+1][current.j-1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
                }

                if(current.j+1<grid[0].length)
                {
                   t = grid[current.i+1][current.j+1];
                    checkAndUpdateCost(current, t, current.finalCost+DIAGONAL_COST);
                }
            }
        }
    }

    /*
    Params :
    si, sj = agents start location x and y coordinates, respectively
    ei, ej = agents end location x and y coordinates, respectively
    IntGrid2D obstacles = the tilemap.
    */
    public static Stack<Int2D> findPath(int si, int sj, int ei, int ej, IntGrid2D obstacles)
    {
    	   Stack<Int2D> FinalPath = new Stack<Int2D>();
    	   int x,y;
    	   x = obstacles.getWidth();
    	   y = obstacles.getHeight();
           grid = new Cell[x][y];
           closed = new boolean[x][y];
           open = new PriorityQueue<>((Object o1, Object o2) -> {
                Cell c1 = (Cell)o1;
                Cell c2 = (Cell)o2;

                return c1.finalCost<c2.finalCost?-1:
                        c1.finalCost>c2.finalCost?1:0;
            });
           //Set start position and end positions
           setStartCell(si, sj);
           setEndCell(ei, ej);

           for(int i=0; i<x; i++)
           {
              for(int j=0; j<y; j++)
              {
                  grid[i][j] = new Cell(i, j);
                  grid[i][j].heuristicCost = Math.abs(i-endI)+Math.abs(j-endJ);
              }
           }
           grid[si][sj].finalCost = 0;
           /*
             Set blocked cells. Simply set the cell values to null
             for blocked cells.
           */
           for(int i=0; i<obstacles.getWidth(); i++)
           {
        	   for(int j=0; j<obstacles.getHeight(); j++)
        	   {
        		   if(obstacles.field[i][j] == 1) // 1 on the tilemap means the tile is wall
        			   setBlocked(i,j);			  // Doors, and walkable tiles will all be included in the path calculation
        	   }
           }

           AStarm();
           if(closed[endI][endJ])
           {
                Cell current = grid[endI][endJ];
                while(current.parent!=null)
                {
                	Int2D curr = new Int2D(current.i, current.j);
                	FinalPath.push(curr);
                    current = current.parent;
                }
                return FinalPath;
           }
           else
           {
        	    System.out.println("No possible path");
       			return null;
           }
    }



    /*  Method below shows how to use the aStar algorithm, and shows how
     *  the path will be sent back.  This will have to be commented out when
     *  the code is integrated with the Simulation code.
     *
     */
    /************************************************/
//    public static void main(String[] args) throws Exception
//    {
//    	int[][] something  = {{0,0,1,0,1},
//		    				  {0,0,0,0,0},
//					    	  {1,0,0,1,0},
//					    	  {0,0,0,0,0},
//					    	  {1,1,0,0,0}};
//    	IntGrid2D testGrid = new IntGrid2D(something);
//    	Stack<Int2D> testPath = findPath(0, 0, 4, 3, testGrid);
//
//    	System.out.println("stack: " + testPath);
//    	Int2D a = testPath.pop();
//    	System.out.println("First: " + a);
//    }
    /************************************************/
}
