import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
public class JTextFieldDemo
{
    public static void main(String[] agrs)
    {
        JFrame frame=new JFrame("Java文本框组件示例");    //创建Frame窗口
        JPanel jp=new JPanel();    //创建面板
        JTextField txtfield1=new JTextField();    //创建文本框
        txtfield1.setText("普通文本框");    //设置文本框的内容
        JTextField txtfield2=new JTextField(28);
        txtfield2.setFont(new Font("楷体",Font.BOLD,36));    //修改字体样式
        txtfield2.setText("指定长度和字体的文本框");
        JTextField txtfield3=new JTextField(30);
        txtfield3.setText("居中对齐");
        txtfield3.setHorizontalAlignment(JTextField.CENTER);    //居中对齐
        jp.add(txtfield1);
        jp.add(txtfield2);
        jp.add(txtfield3);
        frame.add(jp);
        frame.setBounds(300,200,400,100);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}