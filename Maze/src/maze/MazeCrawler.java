package maze;

import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import javax.swing.JOptionPane;

/**
 * Class that contains game logic for a 2-d maze using framework made in Maze.java
 * @author Spencer Bryant
 */
public class MazeCrawler extends Maze{
    public enum Compass {NORTH, SOUTH, EAST, WEST};
    public Compass direction;
private boolean win = false;
private boolean noLeft = false;
private boolean noRight = false;
private boolean noUp = false;
private boolean noDown = false;
private int count = 0;
public MazeCell currentCell;
    MazeCrawler(int a, int b){
        super(a,b);
    }

    /**
     * if the player has already attempted to move in each directionFrom, but is
     * unable to, declares there are no moves left to be made
     * @return true only if all directions have been attempted from the same
     * spot on the maze
     */
    public boolean isNoMove(){

        if (getTrailHead().getColumn() != 0 && getTrailHead().getRow() != 0 &&
            getTrailHead().getRow() != this.getRows() - 1 &&
            getTrailHead().getColumn() != this.getColumns() - 1 && count == 0){
            if ((isValidMove(getCell(getTrailHead().getRow() - 1,
                            getTrailHead().getColumn())) ||
                isValidMove(getCell(getTrailHead().getRow() + 1,
                            getTrailHead().getColumn())) ||
                isValidMove(getCell(getTrailHead().getRow(),
                            getTrailHead().getColumn() + 1)) ||
                isValidMove(getCell(getTrailHead().getRow(),
                            getTrailHead().getColumn() - 1)))){
                return false;
            } else
                return true;
        } else if (noUp && noDown && noLeft && noRight)
            return true;
        else return false;
    }

    /**
     * reports that a win condition has been achieved
     * @return true only if target cell to move into is an exit cell
     */
    public boolean isWin(){
        
        return win;
    }

    /**
     * determines if the move attempted is valid (not a wall or boundary,
     * or part of trail)
     * @param targetCell the cell being tested for validity
     * @return true if the cell is empty
     */
    public boolean isValidMove(MazeCell targetCell){
        //is it a wall?
        if (targetCell.getTypeChar() == targetCell.WALL){
            //y, return false
            return false;
        } else {
            //n, is it the tail?
            if (targetCell.getTypeChar() == targetCell.TRAIL){
                //y, return false
                return false;
            }
        }
        //n, return true
        return true;
    }

    /**
     * turns the target cell into a Trail_head cell
     * @param cell target cell
     * @return original cell with new cell type
     */
    public MazeCell makeIntoHead(MazeCell cell){
        cell.setType(MazeCell.CellType.TRAIL_HEAD);
        repaint();
        return cell;
    }

    /**
     * turns old trail head into part of the trail after a move
     * @return cell after the transformation
     */
    public MazeCell headIntoTrail(){
        MazeCell head = getTrailHead();
        head.setType(MazeCell.CellType.TRAIL);
        return head;
    }

    /**
     * changes "stuck" conditions to false(after a move has been made)
     */
//    public void reset(){
//        noUp = false;
//        noDown = false;
//        noLeft = false;
//        noRight = false;
//    }

    /**
     * determines if the target cell is an exit cell
     * @param target cell being moved into
     * @return true if the cell is an exit cell
     */
    public boolean isExit(MazeCell target){
        if (target.getType() == MazeCell.CellType.EXIT)
            return true;
        else return false;
    }

    /**
     * Moves the trailHead north one space if the cell above is valid.
     * If the cell is an exit, triggers win condition, also changes target cell
     * into the trail head and the old trailhead into trail
     */
    public void moveNorth(){
        if(getTrailHead().getRow() != 0){
            //look at cell north of trail head (row - 1)
            MazeCell targetCell = getCell(getTrailHead().getRow() - 1,
                                          getTrailHead().getColumn());
            //check if valid move
            if (isValidMove(targetCell)){
                noUp = false;
                //is it an exit?
                if (isExit(targetCell)){
                    //y, isWin() = true
                    win = true;
                    return;
                }
                //n, continue
                
                //y, move
                setCell(targetCell);
                setCell(headIntoTrail());
                setCell(makeIntoHead(targetCell));
                repaint();
                return;
            }
        }
        noUp = true;
    }

    /**
     * Moves the trailHead south one space if the cell above is valid.
     * If the cell is an exit, triggers win condition, also changes target cell
     * into the trail head and the old trailhead into trail
     */
    public void moveSouth(){
        if (getTrailHead().getRow() != this.getRows() - 1){
            //look at cell south of trail head (row + 1)
            MazeCell targetCell = getCell(getTrailHead().getRow() + 1,
                                          getTrailHead().getColumn());
            //check if valid move
            if (isValidMove(targetCell)){
                noDown = false;
                //is it an exit?
                if (isExit(targetCell)){
                    //y, isWin() = true
                    win = true;
                    return;
                }
                //n, continue
                //y, move
                setCell(targetCell);
                setCell(headIntoTrail());
                setCell(makeIntoHead(targetCell));
                repaint();
                return;
            }
        }
        noDown = true;
    }

    /**
     * Moves the trailHead east one space if the cell above is valid.
     * If the cell is an exit, triggers win condition, also changes target cell
     * into the trail head and the old trailhead into trail
     */
    public void moveEast(){
        if (getTrailHead().getColumn() != this.getColumns() - 1){
            //look at cell south of trail head (column + 1)
            MazeCell targetCell = getCell(getTrailHead().getRow(),
                                          getTrailHead().getColumn() + 1);
            //check if valid move
            if (isValidMove(targetCell)){
                noRight = false;
                //is it an exit?
                if (isExit(targetCell)){
                    //y, isWin() = true
                    win = true;
                    return;
                }
                //n, continue
                //y, move
                setCell(targetCell);
                setCell(headIntoTrail());
                setCell(makeIntoHead(targetCell));
                repaint();
                return;
            }
        }
        //n, do nothing or give message
        noRight = true;
    }

    /**
     * Moves the trailHead west one space if the cell above is valid.
     * If the cell is an exit, triggers win condition, also changes target cell
     * into the trail head and the old trailhead into trail
     */
    public void moveWest(){
        if (getTrailHead().getColumn() != 0){
            //look at cell south of trail head (column + 1)
            MazeCell targetCell = getCell(getTrailHead().getRow(),
                                          getTrailHead().getColumn() - 1);
            //check if valid move
            if (isValidMove(targetCell)){
                noLeft = false;
                //is it an exit?
                if (isExit(targetCell)){
                    //y, isWin() = true
                    win = true;
                    return;
                }
                //n, continue
                //y, move
                setCell(targetCell);
                setCell(headIntoTrail());
                setCell(makeIntoHead(targetCell));
                repaint();
                return;
            }
        }
         //n, do nothing or give message
        noLeft = true;
    }

    public void solveMaze(){
        while (!win){
            GUI.pause();
            repaint();
            currentCell = getTrailHead();
        traverse(currentCell, null);
        repaint();
        }

    }
    
    public void unmark(MazeCell previousCell){
        previousCell.setType(MazeCell.CellType.EMPTY);
        setCell(previousCell);
        this.paintComponent(this.getGraphics());
        
    }
    
    public void traverse(MazeCell previousCell, Compass directionFrom){
        GUI.pause();
        count ++;
        this.paintComponent(this.getGraphics());
        currentCell = getTrailHead();
        //base case 1
        //exit is found
        if (win){
            count--;
            return;
        }

        //try north
        moveNorth();
        if (!noUp){
        directionFrom = Compass.SOUTH;
        traverse(currentCell, directionFrom);
        }
        //try east
        moveEast();
        if (!noRight){
        directionFrom = Compass.WEST;
        traverse(currentCell, directionFrom);
        }
        //try south
        moveWest();
        if (!noLeft){
        directionFrom = Compass.EAST;
        traverse(currentCell, directionFrom);
        }
        //try west
        moveSouth();
        if (!noDown){
        directionFrom = Compass.NORTH;
        traverse(currentCell, directionFrom);
        }
        //base case 2
        //all routes exhausted
        //backtrack
        if (noUp && noDown && noLeft && noRight){
            unmark(previousCell);
            if (directionFrom == Compass.NORTH)
                moveNorth();
            if (directionFrom == Compass.SOUTH)
                moveSouth();
            if (directionFrom == Compass.EAST)
                moveEast();
            if (directionFrom == Compass.WEST)
                moveWest();
            count--;
            return;
        }
    }

    /**
     * makes a file and prints a text representation of the cells in the maze
     * at the current moment (key can be found in MazeCell.java)
     * @param filename the name of the file
     */
    public void saveToFile(String filename){
        File file = new File(filename);
        PrintWriter writer = null;
        try{
        writer = new PrintWriter(file);
        } catch (FileNotFoundException f){
            System.out.println(f.getMessage());
        }

        for (int row = 0; row < this.getRows(); row++) {
            for (int column = 0; column < this.getColumns(); column++) {
                writer.print(getCell(row, column).getTypeChar());
            } // end for each column
            writer.print("\n");

        } // end for each row
        writer.close();
        JOptionPane.showMessageDialog(null, "Saved to " + filename + "!");
    }
}
