package drake.thedrake;

/**
 * představující hrací plán.
 */
public final class Board {
	private final int dimension;
	private final BoardTile [][] boardTile;

	public Board(int dimension) {
		this.dimension = dimension;
		boardTile = new BoardTile[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			for (int j = 0; j < dimension; j++) {
				boardTile[i][j] = BoardTile.EMPTY;
			}
		}
	}

	public Board(int dimension, BoardTile[][] boardTile) {
		this.dimension = dimension;
		this.boardTile = boardTile;
	}

	public int dimension() {
		return dimension;
	} 
	
	public BoardTile at(BoardPos pos) {
		return boardTile[pos.i()][pos.j()];
	}
		
	public Board withTiles(TileAt ...ats) {
		BoardTile [][] tile = new BoardTile[dimension][dimension];
		for (int i = 0; i < dimension; i++) {
			tile[i] = boardTile[i].clone();
		}
		for (TileAt cur : ats ) {
			tile[cur.pos.i()][cur.pos.j()] = cur.tile;
		}
		return new Board(dimension, tile);
	}
	
	public PositionFactory positionFactory() {
		return new PositionFactory(dimension);
	}

    /**
     * Je to pomůcka, pomocí které říkáme na jaké pozici je jaká dlaždice,
     * protože dlaždice si samy svoji pozici nepamatují.
     * Toto se nám hodí pro metodu withTiles viz níže.
     */
	public static class TileAt {
		public final BoardPos pos;
		public final BoardTile tile;
		
		public TileAt(BoardPos pos, BoardTile tile) {
			this.pos = pos;
			this.tile = tile;
		}
	}

}

