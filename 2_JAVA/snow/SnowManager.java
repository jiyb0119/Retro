package snow;
 
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// �� ����(������ �� �׸��� ȭ�鿡�� ��������)
public class SnowManager {
	static int score; //����
    //final�� �����Ұ�� ����ó���� ������...
    //20���� �ѹ��� ����
    public static int SNOW_MAKE_INTERVAL = 10;
    //���� �������ٰ� ���־��ϴϱ� �ڵ����� ���� �� ������ �ϱ����� ArrayList�� ���
    public List<Snow> snowList = new ArrayList<Snow>();
    Random rand = new Random(); //����
    int snowMakeInterval = SNOW_MAKE_INTERVAL;
    // ������� �޼ҵ�
    public void makeSnow() {
        //�ʴ� 5�� ����
        if(snowMakeInterval == SNOW_MAKE_INTERVAL) {
            Snow snow = new Snow(); //������
            snow.pos.y = -snow.pos.height;// ���̹��� ũ�⿡ -�� ����
            //snow.pos.width/2�� ȭ�鿡�� �Ⱥ��̴� ��츦 ����� ���� �����ְ� ����
            snow.pos.x = rand.nextInt(Dodge.width) - snow.pos.width/2;//0 ~ 399
            snow.speed = rand.nextInt(3) + 3; // 3~5
            //������ ���� ����Ʈ�� �߰�
            snowList.add(snow);
        }
        snowMakeInterval--;
        if(snowMakeInterval<0) 
            snowMakeInterval = SNOW_MAKE_INTERVAL;
    }
    // �� �����̱�(�� �����ŭ �迭 ������ ����)
    public void move() {
        for (int i = 0; i < snowList.size(); i++) {
            Snow snow = snowList.get(i);
            //(ȭ�� ������ ��� ���) ���� ����� ��
            if(snow.move() == false) { //Snow.boolean move
            	snowList.remove(i);
            	score += 10;
            	System.out.println(score);
            }
        }
    }
    public void paint(Graphics g) {
        for (int i = 0; i < snowList.size(); i++) {
            Snow snow = snowList.get(i);
            snow.paint(g);
        } 
    }
}