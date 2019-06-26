public class GameMap extends Map {
    protected GamePoint[][] gameMineMap;
    
    public GameMap(int x, int y, int amount) {
        super(x, y, amount);
        gameMineMap = new GamePoint[x][y];
    }
    
    public GameMap() {
        super();
        gameMineMap = new GamePoint[20][10];
    }
    
    public boolean clickPoint(int x, int y) {
        if(!gameMineMap[x][y].isCovered()){
            return true;
        }
        if (gameMineMap[x][y].ispMine()) {
            return false;
        } else {
            gameMineMap[x][y].uncovered();
            if (gameMineMap[x][y].getSurroundMine() == 0) {
                try {
                    clickPoint(x, y + 1);
                } catch (Exception e) {
                    return true;
                }
                try {
                    clickPoint(x + 1, y);
                } catch (Exception e) {
                    return true;
                }
                try {
                    clickPoint(x - 1, y);
                } catch (Exception e) {
                    return true;
                }
                try {
                    clickPoint(x, y - 1);
                } catch (Exception e) {
                    return true;
                }
                try {
                    clickPoint(x + 1, y + 1);
                } catch (Exception e) {
                    return true;
                }
                try {
                    clickPoint(x + 1, y - 1);
                } catch (Exception e) {
                    return true;
                }
                try {
                    clickPoint(x - 1, y + 1);
                } catch (Exception e) {
                    return true;
                }
                try {
                    clickPoint(x - 1, y - 1);
                } catch (Exception e) {
                    return true;
                }
            }
            else{
                return true;
            }
        }
        return true;
    }
    
    public void uncoverAll(){
        int i = 0;
        int j = 0;
        for(i=0;i<amountX;i++){
            for(j=0;j<amountY;j++){
                gameMineMap[i][j].uncovered();
            }
        }
    }
    
    public boolean isMine(int x, int y){
        if(gameMineMap[x][y].ispMine()){
            gameMineMap[x][y].uncovered();
        }
        return gameMineMap[x][y].ispMine();
    }
    
    @Override
    public void mapInit() {
        super.mapInit();
        System.out.println(super.toString());
        int i = 0;
        int j = 0;
        for (i = 0; i < amountX; i++) {
            for (j = 0; j < amountY; j++) {
                gameMineMap[i][j] = new GamePoint(mineMap[i][j]);
            }
        }
    }
    
    @Override
    public String toString() {
        int i = 0;
        int j = 0;
        String out = "";
        for (i = 0; i < amountY; i++) {
            for (j = 0; j < amountX; j++) {
                out = out + gameMineMap[j][i].toString() + " ";
            }
            out = out + "|\n";
        }
        for (j = 0; j < amountX; j++) {
            out = out + "--";
        }
        return out;
    }
}
