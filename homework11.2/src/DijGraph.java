import java.util.ArrayList;

public class DijGraph {
    private int[][] price = new int[121][121];
    private int[][] trans = new int[121][121];
    private int[][] sat = new int[121][121];
    private int[][] len = new int[121][121];
    private BasicGraph basicGraph;
    
    public DijGraph(BasicGraph g) {
        basicGraph = g;
    }
    
    public void renewGraph(BasicGraph g) {
        basicGraph = g;
        for (int i = 0; i < 121; i++) {
            for (int j = 0; j < 121; j++) {
                price[i][j] = -1;
                trans[i][j] = -1;
                sat[i][j] = -1;
                len[i][j] = -1;
            }
        }
    }
    
    public int getLeastChange(int fromid, int toid) {
        int fromNum = basicGraph.idToNum(fromid);
        int toNum = basicGraph.idToNum(toid);
        /*
        if (trans[fromNum][toNum] == -1) {
            calLeastChange(fromNum);
        }*/
        //return trans[fromNum][toNum] - 1;
        return ShortestDistanceOfTwoPoints.dijkstra(
                basicGraph.getGraphTransList(), fromNum, toNum) - 1;
    }
    
    private void calLeastChange(int beginNum) {
        
        ArrayList list = new ArrayList();
        //先把所有的begin点加进来
        for (int i = 0; i < basicGraph.size(); i++) {
            trans[beginNum][i] = basicGraph.getGraphTrans(beginNum, i);
            trans[i][beginNum] = basicGraph.getGraphTrans(beginNum, i);
            list.add(i);
        }
        // list是还没有计算成功的点
        list.remove(beginNum);
        int i = 0;
        while (!list.isEmpty()) {
            int minDis = -1;
            int minNum = -1;
            //找出距离beginNum最小的点minNum
            for (i = 0; i < list.size(); i++) {
                int temp = min(minDis, trans[beginNum][(int) list.get(i)]);
                if (temp != minDis) {
                    minNum = (int) list.get(i);
                    minDis = temp;
                }
            }
            //加入到已求解的list中
            if (minNum == -1) {
                break;
                //这里可能会有问题：
                //当没有点和当前begin有距离时认为所有联通的点都计算过了
            }
            list.remove(list.indexOf(minNum));
            //nextNum = minNum;
            //更新minNum为起始点的所有剩余点的权值
            for (i = 0; i < list.size(); i++) {
                int tempNum = (int) list.get(i);
                int priceTemp1 = basicGraph.getGraphTrans(beginNum, minNum);
                int priceTemp2 = basicGraph.getGraphTrans(tempNum, minNum);
                if (priceTemp2 > 0) {
                    int temp;
                    if (priceTemp1 != -1) {
                        temp = min(trans[beginNum][tempNum],
                                priceTemp1 + priceTemp2);
                        trans[beginNum][tempNum] = temp;
                        trans[tempNum][beginNum] = temp;
                    }
                }
                
                
            }
        }
    }
    
    public int getLeastPrice(int fromid, int toid) {
        int fromNum = basicGraph.idToNum(fromid);
        int toNum = basicGraph.idToNum(toid);
        /*
        if (price[fromNum][toNum] == -1) {
            calLeastPrice(fromNum);
        }*/
        return ShortestDistanceOfTwoPoints.dijkstra(
                basicGraph.getGraphPriceList(), fromNum, toNum) - 2;
        //return price[fromNum][toNum] - 2;
    }
    
    private void calLeastPrice(int beginNum) {
        ArrayList list = new ArrayList();
        //先把所有的begin点加进来
        for (int i = 0; i < basicGraph.size(); i++) {
            price[beginNum][i] = basicGraph.getGraphPrice(beginNum, i);
            price[i][beginNum] = basicGraph.getGraphPrice(beginNum, i);
            list.add(i);
        }
        // list是还没有计算成功的点
        list.remove(beginNum);
        int i = 0;
        while (!list.isEmpty()) {
            int minDis = -1;
            int minNum = -1;
            //找出距离beginNum最小的点minNum
            for (i = 0; i < list.size(); i++) {
                int temp = min(minDis, price[beginNum][(int) list.get(i)]);
                if (temp != minDis) {
                    minNum = (int) list.get(i);
                    minDis = temp;
                }
            }
            //加入到已求解的list中
            if (minNum == -1) {
                break;
                //这里可能会有问题：
                //当没有点和当前begin有距离时认为所有联通的点都计算过了
            }
            list.remove(list.indexOf(minNum));
            //nextNum = minNum;
            //更新minNum为起始点的所有剩余点的权值
            for (i = 0; i < list.size(); i++) {
                int tempNum = (int) list.get(i);
                int priceTemp1 = basicGraph.getGraphPrice(beginNum, minNum);
                int priceTemp2 = basicGraph.getGraphPrice(tempNum, minNum);
                if (priceTemp2 > 0) {
                    int temp;
                    if (priceTemp1 != -1) {
                        temp = min(price[beginNum][tempNum],
                                priceTemp1 + priceTemp2);
                        price[beginNum][tempNum] = temp;
                        price[tempNum][beginNum] = temp;
                    }
                }
                
                
            }
        }
    }
    
    public int getMostSat(int fromid, int toid) {
        int fromNum = basicGraph.idToNum(fromid);
        int toNum = basicGraph.idToNum(toid);
        /*
        if (sat[fromNum][toNum] == -1) {
            calMostSat(fromNum);
        }*/
        return ShortestDistanceOfTwoPoints.dijkstra(
                basicGraph.getGraphSatList(), fromNum, toNum) - 32;
        //return sat[fromNum][toNum] - 32;
    }
    
    private void calMostSat(int beginNum) {
        ArrayList list = new ArrayList();
        //先把所有的begin点加进来
        for (int i = 0; i < basicGraph.size(); i++) {
            sat[beginNum][i] = basicGraph.getGraphSat(beginNum, i);
            sat[i][beginNum] = basicGraph.getGraphSat(beginNum, i);
            list.add(i);
        }
        // list是还没有计算成功的点
        list.remove(beginNum);
        int i = 0;
        while (!list.isEmpty()) {
            int minDis = -1;
            int minNum = -1;
            //找出距离beginNum最小的点minNum
            for (i = 0; i < list.size(); i++) {
                int temp = min(minDis, sat[beginNum][(int) list.get(i)]);
                if (temp != minDis) {
                    minNum = (int) list.get(i);
                    minDis = temp;
                }
            }
            //加入到已求解的list中
            if (minNum == -1) {
                break;
                //这里可能会有问题：
                //当没有点和当前begin有距离时认为所有联通的点都计算过了
            }
            list.remove(list.indexOf(minNum));
            //nextNum = minNum;
            //更新minNum为起始点的所有剩余点的权值
            for (i = 0; i < list.size(); i++) {
                int tempNum = (int) list.get(i);
                int priceTemp1 = basicGraph.getGraphSat(beginNum, minNum);
                int priceTemp2 = basicGraph.getGraphSat(tempNum, minNum);
                if (priceTemp2 > 0) {
                    int temp;
                    if (priceTemp1 != -1) {
                        temp = min(sat[beginNum][tempNum],
                                priceTemp1 + priceTemp2);
                        sat[beginNum][tempNum] = temp;
                        sat[tempNum][beginNum] = temp;
                    }
                }
                
                
            }
        }
    }
    
    public int getShortest(int fromid, int toid) {
        int fromNum = basicGraph.idToNum(fromid);
        int toNum = basicGraph.idToNum(toid);/*
        if (len[fromNum][toNum] == -1) {
            calShortest(fromNum);
        }
        return len[fromNum][toNum];*/
        return ShortestDistanceOfTwoPoints.dijkstra(
                basicGraph.getGraphLenList(), fromNum, toNum);
    }
    
    private void calShortest(int beginNum) {
        ArrayList list = new ArrayList();
        //先把所有的begin点加进来
        for (int i = 0; i < basicGraph.size(); i++) {
            len[beginNum][i] = basicGraph.getGraphLen(beginNum, i);
            len[i][beginNum] = basicGraph.getGraphLen(beginNum, i);
            list.add(i);
        }
        // list是还没有计算成功的点
        list.remove(beginNum);
        int i = 0;
        while (!list.isEmpty()) {
            int minDis = -1;
            int minNum = -1;
            //找出距离beginNum最小的点minNum
            for (i = 0; i < list.size(); i++) {
                int temp = min(minDis, len[beginNum][(int) list.get(i)]);
                if (temp != minDis) {
                    minNum = (int) list.get(i);
                    minDis = temp;
                }
            }
            //加入到已求解的list中
            if (minNum == -1) {
                break;
                //这里可能会有问题：
                //当没有点和当前begin有距离时认为所有联通的点都计算过了
            }
            list.remove(list.indexOf(minNum));
            //nextNum = minNum;
            //更新minNum为起始点的所有剩余点的权值
            for (i = 0; i < list.size(); i++) {
                int tempNum = (int) list.get(i);
                int priceTemp1 = basicGraph.getGraphLen(beginNum, minNum);
                int priceTemp2 = basicGraph.getGraphLen(tempNum, minNum);
                if (priceTemp2 > 0) {
                    int temp;
                    if (priceTemp1 != -1) {
                        temp = min(len[beginNum][tempNum],
                                priceTemp1 + priceTemp2);
                        len[beginNum][tempNum] = temp;
                        len[tempNum][beginNum] = temp;
                    }
                }
                
                
            }
        }
    }
    
    
    //用于选出ab中最小的（-1认为是无穷大）
    private int min(int a, int b) {
        if (a == -1 && b >= 0) {
            return b;
        }
        if (b == -1 && a >= 0) {
            return a;
        }
        if (a < b) {
            return a;
        }
        return b;
    }
    
}
//用于选出所有对图的更改中结果最小的（-1认为是无穷大）

    

