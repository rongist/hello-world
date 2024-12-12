package Game;

import java.util.Random;

public class MapTool {
    public static int[][] createMap() {
        int[][] map = new int[10][10];
        Random rand = new Random();
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                map[i][j] = rand.nextInt(9) + 1;
            }
        }
        return map;
    }

    public static int[][] removed(int[][] map, int pi, int pj, int ci, int cj) {
        if (map[pi][pj] == map[ci][cj] && (pj != cj || pi != ci)) {
            System.out.println("消除：map[" + ci + "][" + cj + "]，map[" + pi + "][" + pj + "]");
            map[pi][pj] = 0;
            map[ci][cj] = 0;
        }
        return map;
    }
}