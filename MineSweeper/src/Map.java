import java.util.Random;

public class Map {
    protected int amountX = 0;
    protected int amountY = 0;
    protected int amountMine = 0;
    protected Point[][] mineMap;
    
    public Map(int x, int y, int amount) {
        amountX = x;
        amountY = y;
        amountMine = amount;
        mineMap = new Point[x][y];
    }
    
    public Map() {
        amountX = 20;
        amountY = 10;
        amountMine = 10;
        mineMap = new Point[20][10];
    }
    
    public void mapInit() {
        int i = 0;
        int j = 0;
        for (i = 0; i < amountX; i++) {
            for (j = 0; j < amountY; j++) {
                mineMap[i][j] = new Point();
            }
        }
        randomMine();
        countMap();
        
    }
    
    private void randomMine() {
        int x = 0;
        int y = 0;
        Random ranX = new Random();
        Random ranY = new Random();
        for (int i = 0; i < amountMine; i++) {
            x = ranX.nextInt(amountX);
            y = ranY.nextInt(amountY);
            if (!mineMap[x][y].ispMine()) {
                mineMap[x][y].setpStatus();
            } else {
                i--;
            }
        }
    }
    
    private void countMap() {
        int i = 0;
        int j = 0;
        int count = 0;
        for (i = 0; i < amountX; i++) {
            for (j = 0; j < amountY; j++) {
                count = countPoint(i - 1, j - 1) + countPoint(i - 1, j)
                        + countPoint(i - 1, j + 1) + countPoint(i, j - 1)
                        + countPoint(i, j + 1) + countPoint(i + 1, j - 1)
                        + countPoint(i + 1, j) + countPoint(i + 1, j + 1);
                mineMap[i][j].setSurroundMine(count);
            }
        }
    }
    
    private int countPoint(int i, int j) {
        try {
            if (mineMap[i][j].ispMine()) {
                return 1;
            } else {
                return 0;
            }
        } catch (Exception e) {
            return 0;
        }
    }
    
    @Override
    public String toString() {
        int i = 0;
        int j = 0;
        String out = "";
        for (i = 0; i < amountY; i++) {
            for (j = 0; j < amountX; j++) {
                out = out + mineMap[j][i].toString() + " ";
            }
            out = out + "|\n";
        }
        for (j = 0; j < amountX; j++) {
            out = out +  "--";
        }
        return out;
    }
}
