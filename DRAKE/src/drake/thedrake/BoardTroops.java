package drake.thedrake;


import java.util.*;

/**
 * se stará o jednotky nacházející se na hracím plánu. Stav hry si udržuje instanci této třídy pro každého hráče.
 * Tedy jedna instance této třídy drží jednotky pouze jedné strany (modré nebo oranžové).
 */
public class BoardTroops {
    private final PlayingSide playingSide;
    private final Map<BoardPos, TroopTile> troopMap;
    private final TilePos leaderPosition;
    private final int guards;

    public BoardTroops(PlayingSide playingSide) {
        this.playingSide = playingSide;
        this.troopMap = Collections.emptyMap();
        this.leaderPosition = TilePos.OFF_BOARD;
        this.guards = 0;
    }

    public BoardTroops(
            PlayingSide playingSide,
            Map<BoardPos, TroopTile> troopMap,
            TilePos leaderPosition,
            int guards) {
        this.playingSide = playingSide;
        this.troopMap = troopMap;
        this.leaderPosition = leaderPosition;
        this.guards = guards;
    }

    public Optional<TroopTile> at(TilePos pos) {

        for (Map.Entry<BoardPos, TroopTile> entry : troopMap.entrySet()) {
            if (entry.getKey().i() == pos.i() && entry.getKey().j() == pos.j()) {
                return Optional.of(entry.getValue());
            }
        }

        return Optional.empty();
    }

    public PlayingSide playingSide() {
        return playingSide;
    }

    public TilePos leaderPosition() {
        return leaderPosition;
    }

    public int guards() {
        return guards;
    }

    public boolean isLeaderPlaced() {
        return leaderPosition != TilePos.OFF_BOARD;
    }

    public boolean isPlacingGuards() {
        return isLeaderPlaced() && (guards < 2);
    }

    public Set<BoardPos> troopPositions() {
        Set<BoardPos> boardPos = new HashSet<>();

        for (Map.Entry<BoardPos, TroopTile> entry : troopMap.entrySet()) {
            if (entry.getValue().hasTroop()) {
                boardPos.add(entry.getKey());
            }
        }

        return boardPos;
    }

    public BoardTroops placeTroop(Troop troop, BoardPos target) {
        if (troopPositions().contains(target)) {
            throw new IllegalArgumentException("");
        }
        Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
        newTroops.put(target, new TroopTile(troop, playingSide, TroopFace.AVERS));
        return new BoardTroops(playingSide, newTroops, isLeaderPlaced() ? leaderPosition : target, isPlacingGuards() ? guards + 1 : guards);
    }

    public BoardTroops troopStep(BoardPos origin, BoardPos target) {
        if (!isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }
        if (isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }
//===================================================================================
//===========================================  NEW!  ================================
        if(troopMap.containsKey(target)){
            throw new IllegalArgumentException();
        }
//===========================================  NEW!  ================================
//===================================================================================

        if (!at(origin).isPresent())
            throw new IllegalArgumentException();

//===================================================================================
//===========================================  NEW!  ================================
        if(troopMap.containsKey(origin)){

            Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
            newTroops.put(target, troopMap.get(origin).flipped());
            newTroops.remove(origin);

            return new BoardTroops(playingSide, newTroops, target, guards);

        }
//===========================================  NEW!  ================================
//===================================================================================

        return null;
    }

    public BoardTroops troopFlip(BoardPos origin) {
        if (!isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }

        if (isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }

        if (!at(origin).isPresent())
            throw new IllegalArgumentException();

        Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
        TroopTile tile = newTroops.remove(origin);
        newTroops.put(origin, tile.flipped());

        return new BoardTroops(playingSide(), newTroops, leaderPosition, guards);
    }

    public BoardTroops removeTroop(BoardPos target) {
        if (!isLeaderPlaced()) {
            throw new IllegalStateException(
                    "Cannot move troops before the leader is placed.");
        }

        if (isPlacingGuards()) {
            throw new IllegalStateException(
                    "Cannot move troops before guards are placed.");
        }


        for (Map.Entry<BoardPos, TroopTile> entry : troopMap.entrySet()) {
            if (entry.getKey().i() == target.i() && entry.getKey().j() == target.j()) {
                Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
                newTroops.remove(entry.getKey());
                return new BoardTroops(playingSide, newTroops, TilePos.OFF_BOARD, guards);
            }
        }

//===================================================================================
//===========================================  NEW!  ================================
        if(troopMap.containsKey(target)){
            Map<BoardPos, TroopTile> newTroops = new HashMap<>(troopMap);
            newTroops.remove(troopMap.get(target));
            return new BoardTroops(playingSide, newTroops, TilePos.OFF_BOARD, guards);
        }
//===========================================  NEW!  ================================
//===================================================================================

        throw new IllegalArgumentException();
    }

}
