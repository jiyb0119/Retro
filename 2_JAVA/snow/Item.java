package snow;
 
import java.awt.Graphics;


//눈 위치, 크기, 행위(움직임), 그리기
public abstract class Item {
    //행위속성
    public abstract boolean move();
    //Graphics g 
    public abstract void paint(Graphics g);
}
