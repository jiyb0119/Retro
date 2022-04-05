package snow;
 
import java.awt.Graphics;
import java.awt.Rectangle;

import snow.Dodge;
//�� �ڽ� �� ���� ǥ��
public class Snow extends Item {
	public Rectangle pos = new Rectangle();
    public Snow() {
   //���̹����� �����ͼ� �׸�(rectangle)�� ���� ���� ����
   //���̹����� �����ͼ� �׸�(rectangle)�� ���� ���� ����    
    	pos.width  = Dodge.snowImg.getWidth(null);
        pos.height = Dodge.snowImg.getHeight(null);
    }
    public int speed;//�̵��ӵ�
    //���� �����̴� �޼ҵ�
    @Override
    public boolean move() { 
        //�̵��� ���
        //pos.t = pos.y + speed;
        pos.y += speed + 5;
        //���� �޼����� �� �ӵ� ����
        if(SnowManager.score >= 200) {
        	pos.y += 2 ;
        }
        if(SnowManager.score >= 400) {
        	pos.y += 2 ;
        }
        if(SnowManager.score >= 600) {
        	pos.y += 2 ;
        }
        if(SnowManager.score >= 800) {
        	pos.y += 2 ;
        }
        if(SnowManager.score >= 1000) {
        	pos.y += 2 ;
        }
        if(SnowManager.score > 1500) {
        	pos.y += 2 ;
        }
        //������ ���̺��� ���� ���̰� ������ (���� ȭ�� �ȿ� �ִ���)
        return (pos.y  <= Dodge.height - 100); // ���� ���� ����� �� SnowManager���� ����
    } 
    //���� �׸��� �޼���(�ڱ� �ڽ��� �ڽ��� �׸�)
    @Override
    public void paint(Graphics g) {
        g.drawImage(Dodge.snowImg,pos.x,pos.y,null);
    }
} 