import javax.swing.*;
import java.awt.*;

public class MainClass {
    private static void createAndShowGUI() {
        // 确保一个漂亮的外观风格
        //JFrame.setDefaultLookAndFeelDecorated(true);
        
        // 创建及设置窗口
    
        Toolkit kit = Toolkit.getDefaultToolkit();
        Dimension screenSize = kit.getScreenSize();
        int screenHeight = (int) screenSize.getHeight();
        int screenWidth = (int) screenSize.getWidth();
        
        
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setSize(screenWidth/4,screenHeight/4);
        frame.setLocationByPlatform(true);
        //frame.setBounds(300, 100, 1400, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        JPanel jp = new JPanel();
        jp.setBackground(Color.GRAY);
        Font font = new Font("宋体",Font.BOLD,60);
        
        // 添加 "Hello World" 标签
        JLabel label = new JLabel("Hello World");
        label.setFont(font);
        jp.add(label);
        
        frame.getContentPane().add(jp);
        
        // 显示窗口
        //frame.pack();
        frame.setVisible(true);
    }
    
    public static void main(String[] args) {
        // 显示应用 GUI
        createAndShowGUI();
    }
}
