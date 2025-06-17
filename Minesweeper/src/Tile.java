/**
 * Abstract class
 */
public class Tile {
    private TileTypes type;

    public Tile(TileTypes type) {
        this.type = type;
    }
    
    // lambda function to determine if the current tile is a mine
    public boolean IsMine() { return this.type == TileTypes.MINE; }
}