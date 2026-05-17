package PaooGame.Algorithms;

import PaooGame.Levels.Level;
import PaooGame.Tiles.DoorTile;
import PaooGame.Tiles.Tile;
import PaooGame.Tiles.WallTile;

import java.util.*;

// clasa pentru pathfinding folosind BFS
// BFS = Breadth First Search - cauta cel mai scurt drum pe o harta de tile-uri
public class Pathfinder {

    // returneaza o lista de tile-uri [col, row] de la start la goal
    // daca nu exista drum returneaza lista goala
    public static List<int[]> bfs(int startCol, int startRow, int goalCol, int goalRow) {
        int[][] tileMap = Level.map.tileMap;
        int mapH = Level.map.mapHeight;
        int mapW = Level.map.mapWidth;

        // verificam ca start si goal sa fie in harta
        if (!inBounds(startCol, startRow, mapW, mapH) || !inBounds(goalCol, goalRow, mapW, mapH))
            return new ArrayList<>();

        // codificam fiecare tile ca un singur numar: row * mapW + col
        // parent retine de unde am ajuns la tile-ul curent
        int start = encode(startCol, startRow, mapW);
        int goal  = encode(goalCol,  goalRow,  mapW);
        Map<Integer, Integer> parent = new HashMap<>();
        parent.put(start, -1); // start nu are parinte

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);

        // cele 4 directii: sus, jos, stanga, dreapta
        int[][] dirs = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        while (!queue.isEmpty()) {
            int cur = queue.poll();

            // am ajuns la destinatie
            if (cur == goal)
                return reconstructPath(parent, start, goal, mapW);

            int col = cur % mapW, row = cur / mapW;

            // exploram vecinii
            for (int[] d : dirs) {
                int nc = col + d[0], nr = row + d[1];
                int next = encode(nc, nr, mapW);

                // sarim peste tile-urile invalide / deja vizitate / blocate
                if (!inBounds(nc, nr, mapW, mapH) || parent.containsKey(next) || isBlocked(tileMap, nr, nc))
                    continue;

                parent.put(next, cur); // retinem de unde am venit
                queue.add(next);
            }
        }

        return new ArrayList<>(); // nu s-a gasit drum
    }

    // codifica (col, row) intr-un singur int
    private static int encode(int col, int row, int mapW) {
        return row * mapW + col;
    }

    // un tile e blocat daca e perete sau usa inchisa
    private static boolean isBlocked(int[][] tileMap, int row, int col) {
        int idx = tileMap[row][col];
        if (idx < 0) return true;
        Tile t = Tile.tiles[idx];
        return t == null || t instanceof WallTile || t instanceof DoorTile;
    }

    // verifica daca coordonatele sunt in limitele hartii
    private static boolean inBounds(int col, int row, int mapW, int mapH) {
        return col >= 0 && col < mapW && row >= 0 && row < mapH;
    }

    // reconstruieste drumul mergand inapoi prin parent de la goal la start
    private static List<int[]> reconstructPath(Map<Integer, Integer> parent, int start, int goal, int mapW) {
        List<int[]> result = new ArrayList<>();
        int cur = goal;

        while (cur != start) {
            result.add(0, new int[]{cur % mapW, cur / mapW}); // adaugam la inceput ca sa fie in ordine corecta
            cur = parent.get(cur);
        }

        return result;
    }
}