import java.util.Scanner;

public class Game extends Thread{
    private GameMap map;
    
    @Override
    public void run() {
        gameStart();
        UserFace userFace = new UserFace(map);
        userFace.create();
        //inputStart();
    }
    
    private void gameStart() {
        map = new GameMap();
        map.mapInit();
        System.out.println(map);
    }
    
    private void inputStart(){
        int x = 0;
        int y = 0;
        Scanner sc = new Scanner(System.in);
        System.out.println("输入m进行排雷，输入c进行点击：");
        String in = sc.nextLine();
        while (!in.equals("quit")) {
            if (in.equals("m")) {
                System.out.print("输入x坐标：");
                in = sc.nextLine();
                x = Integer.parseInt(in);
                in = sc.nextLine();
                System.out.print("输入y坐标：");
                y = Integer.parseInt(in);
                guessMine(x, y);
            } else {
                System.out.print("输入x：");
                in = sc.nextLine();
                x = Integer.parseInt(in);
                System.out.print("输入y：");
                in = sc.nextLine();
                y = Integer.parseInt(in);
                clickPoint(x, y);
            }
            in = sc.nextLine();
        }
        System.out.println("Game Quit!");
    }
    
    private void clickPoint(int x, int y) {
        if (!map.clickPoint(x, y)) {
            System.out.println("Game Over!");
            map.uncoverAll();
            System.out.println(map);
            System.exit(0);
        } else {
            System.out.println(map);
        }
    }
    
    private void guessMine(int x, int y) {
        int amount = 0;
        if (map.isMine(x, y)) {
            System.out.println(map);
            amount++;
        } else {
            System.out.println("Wrong Guess!");
            System.out.println(map);
        }
        if (amount == map.amountMine) {
            System.out.println("Good Job!");
            System.exit(0);
        }
    }
}
