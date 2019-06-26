import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class UserFace {
    private JFrame frame;
    private JPanel jp;
    private int x = 0;
    private int y = 0;
    private int i = 0;
    private GameMap map;
    private ArrayList<JButton> buttonList = new ArrayList<>();
    
    public UserFace(GameMap map) {
        this.map = map;
        x = map.amountX;
        y = map.amountY;
        frame = new JFrame("扫雷");
        jp = new JPanel();
    }
    
    public void create() {
        setWinSize();
        buttonJob();
    }
    
    private void buttonJob(){
        frame.remove(jp);
        jp = new JPanel();
        jp.setLayout(new GridLayout(y, x, 1, 1));
        int buttonAmount = x * y;
        buttonList.clear();
        for (i = 0; i < buttonAmount; i++) {
            JButton btn = new JButton();
            int posx = i % x;
            int posy = i / x;
            //System.out.println(posx);
            if (!map.gameMineMap[posx][posy].isCovered()) {
                if (map.mineMap[posx][posy].ispMine()) {
                    System.out.println("GameOver");
                } else {
                    if (map.mineMap[posx][posy].getSurroundMine() == 0) {
                        btn.setEnabled(false);
                    } else {
                        btn.setEnabled(false);
                        btn.setText(String.valueOf(map.mineMap[posx][posy].getSurroundMine()));
                    }
                }
            }
            Dimension preferredSize = new Dimension(100, 100);
            btn.setPreferredSize(preferredSize);
            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    click(btn);
                }
            });
            jp.add(btn);
            buttonList.add(btn);
        }
        System.out.println(buttonList.size());
        frame.add(jp);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void setWinSize() {
        int width = x * 100;
        int height = y * 100;
        frame.setSize(width, height);
    }
    
    private void click(JButton btn) {
        int j = buttonList.indexOf(btn);
        System.out.println(j);
        int posx = j % x;
        //System.out.println(x);
        int posy = j / x;
        map.clickPoint(posx, posy);
        System.out.println(map);
        System.out.println("click");
        buttonJob();
    }
}