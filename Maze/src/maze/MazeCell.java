package maze;
import java.awt.*;
   /**
    * MazeCell objects represent a cell for use in 2-dimentional maze 
    * Each MazeCell may be traversable (e.g. CellType.EMPTY), or 
    * non-traversable (e.g. CellType.WALL).  One traversable cell must be the 
    * "start" of the maze (CellType.TRAIL_HEAD).  At least one traversable cell 
    * must be an "exit" from the maze (CellType.EXIT).
    * Maze cells are labeled by table row and column, with origin (0,0) being
    * the upper-left corner. 
    * @author doom 2008
    */
public class MazeCell {
   public enum CellType { EMPTY, WALL, TRAIL, TRAIL_HEAD, EXIT };
   public final char TRAIL = '*';
   public final char WALL = '#';
   public final char EMPTY = '.';
   public final char START = 's';
   public final char EXIT = 'x';
   public static final int size = 15;
   private CellType cellType ; 
   private int row;
   private int column;

   /**
    * Creates an empty cell for use in an n by m rectangular maze. 
    * @param row position in the overall maze for this maze cell
    * @param column position in the overall maze for this maze cell
    */
   public MazeCell(int row, int column) {
      this.column = column;
      this.row = row;
      setType(CellType.EMPTY);
   } // end constructor
   
   public MazeCell (MazeCell original) {
      this.row = original.getRow();
      this.column = original.getColumn();
      setType(original.getType());
   } // end copy constructor
   
   /**
    * Set the maze cell's cellType (wall, empty, etc) by setting it from
    * options in MazeCell.CellType
    * @param The maze cell's cellType (e.g. CellType.EMPTY, CellType.WALL, etc).
    */
   public void setType(CellType type) {
      this.cellType = type;
   } // end method setType

   /**
    * Set the maze cell's cellType (wall, empty, etc) by using a one
    * character text symbol.  Useful for creating a maze from a text format.
    * @param 'x' (wall), ' ' (empty), 'e' (exit), or 's' (start)
    */
   public void setType(char typeChar) {
      switch (typeChar) {
         case EMPTY:
            setType(CellType.EMPTY);
            break;
         case WALL:
            setType(CellType.WALL);
            break;
         case START:
            setType(CellType.TRAIL_HEAD);
            break;
         case EXIT:
            setType(CellType.EXIT);
            break;
         default:
            System.out.println("Error creating Maze");
            System.exit(1);
      } // end switch
      // System.out.println(cellType);
   } // end method setType

   /**
    * @return The cellType of mazecell (CellType.WALL, CellType.EXIT, etc).
    */
   public CellType getType() {
      return cellType;
   } // end method getType

   /**
    * @return The one character symbol used to represent the maze
    * cell in text representation (e.g. # for Wall, . for empty, etc.)
    */
   public char getTypeChar() {
      char symbol = '&';
      switch (cellType) {
         case EMPTY: return EMPTY;
         case WALL: return WALL;
         case TRAIL_HEAD: return START;
         case TRAIL: return TRAIL;
         case EXIT: return EXIT;
      }
      return symbol;
   } // end method getTypeChar
   
   /**
    * @return The appropriate display color for this maze
    *  cell (by cellType: Wall, Empty, etc.).  Returns PINK if
    *  cell is of non-standard cellType, likely due to an error 
    *  elsewhere.
    */
   public Color getColor() {
      Color color = Color.PINK;
      if (cellType == CellType.EMPTY) {
         color = Color.WHITE;
      }
      if (cellType == CellType.WALL) {
         color = Color.BLACK;
      }
      if (cellType == CellType.TRAIL) {
         color = Color.LIGHT_GRAY;
      }
      if (cellType == CellType.TRAIL_HEAD) {
         color = Color.BLUE;
      }
      if (cellType == CellType.EXIT) {
         color = Color.RED;
      }
      return color;
   } // end method getColor

   /**
    * @return This maze cell's horizontal display position on the canvas
    */
   public int getX() {
      return column * size;
   } // end method getX

   /**
    * @return This maze cell's vertical display position on the canvas
    */
   public int getY() {
      return row * size;
   } // end method getY
   
   /**
    * @return This maze cell's row position in the overall maze
    */
   public int getRow() {
      return row;
   } // end method getRow
   
   /**
    * @return This maze cell's column position in the overall maze
    */
   public int getColumn() {
      return column;
   } // end method getColumn
   
} // end class MazeCell
