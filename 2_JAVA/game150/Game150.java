package game150;
import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.*;

import main.Carrier;
//main class
public class Game150 {
	public Game150() {
		
		new MainFrame();
	}
	public static void main(String[] args) {
		new Game150();
	}
}
class MainFrame extends JFrame implements MouseListener, Runnable {
	ImageIcon ii3 = new ImageIcon("imgs/game150/button.png");
	Image img_button = ii3.getImage();				//ImageIcon�� Image�� ��ȯ.
	Image changeImg = img_button.getScaledInstance(120, 40, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ii_button = new ImageIcon(changeImg); // start reset �̹���
	
	ImageIcon ii5 = new ImageIcon("imgs/game150/home.png");
	Image img_home = ii5.getImage();				
	Image changeImg3 = img_home.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ii_home = new ImageIcon(changeImg3);	// home label �̹���(�̱۷�)
	
	ImageIcon ii6 = new ImageIcon("imgs/game150/speaker1.png");
	Image img_speaker1 = ii6.getImage();				
	Image changeImg4 = img_speaker1.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ii_speaker1 = new ImageIcon(changeImg4); // speaker1 label �̹���
	
	ImageIcon ii7 = new ImageIcon("imgs/game150/speaker2.png");
	Image img_speaker2 = ii7.getImage();				
	Image changeImg5 = img_speaker2.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ii_speaker2 = new ImageIcon(changeImg5); // speaker2 label �̹���(mute)
	
	private JLabel lb_title = new JLabel();				// ���� Ÿ��Ʋ ǥ�� ��
	private JLabel lb_time = new JLabel();				// �ð� ǥ�� ��
	private JButton bt_start = new JButton("START");	// ���ӽ��� ��ư
	private JButton bt_reset = new JButton("RESET");	// ���Ӹ��� ��ư
	private JLabel lb_start = new JLabel(ii_button);	// start label
	private JLabel lb_reset = new JLabel(ii_button);	// reset label
	private JLabel lb_home = new JLabel(ii_home);		// �ڷΰ��� label
	private JLabel lb_sound = new JLabel(ii_speaker1);	// �ڷΰ��� label
	SimpleDateFormat time_format; 						// �ð��� ��ȯ�ϱ� ���� ����
	String show_time;									// ���� ���� �ð� ���� ���ڿ�
	long start_time, current_time, actual_time; 		// ���ӽ��۽ð�, ��ǻ�ͽð�, ������������ð�
	boolean time_run = false;
	Thread th; 
	ImagePanel sc;										// ���� ����ȭ�� class

	ImageIcon bgIcon = new ImageIcon("imgs/game150/background.gif");	// ��� gif �̹���
	Image bgImg = bgIcon.getImage();
	JButton bb = new JButton("practice");

	BGM bgm;											// ������� class
	
	MainFrame(){
		System.out.println("������ ()");
		init();
		bgm = new BGM();
	}
	void init() {
		MyPanel panel = new MyPanel();
		panel.setLayout(null);
		
		lb_home.setBounds(35, 50, 70, 70);
		lb_home.setFont(new Font("Default", Font.BOLD, 15));
		panel.add(lb_home);
		
		lb_sound.setBounds(35, 130, 70, 70);
		lb_sound.setFont(new Font("Default", Font.BOLD, 15));
		panel.add(lb_sound);
		
		lb_time.setBounds(600, 50, 300, 50); // ������ title �ؽ�Ʈ ��
		lb_time.setFont(new Font("Default", Font.BOLD, 57));
		panel.add(lb_time);

		bt_start.setBounds(605, 122, 100, 30); // ���ӽ��� button
		bt_start.setFont(new Font("Default", Font.BOLD, 20));
		bt_start.setBorderPainted(false);
		bt_start.setContentAreaFilled(false);
		bt_start.setFocusPainted(false);
		panel.add(bt_start);
		lb_start.setBounds(595, 120, 120, 40);
		panel.add(lb_start);

		bt_reset.setBounds(730, 122, 105, 30); // ���� button
		bt_reset.setFont(new Font("Default", Font.BOLD, 20));
		bt_reset.setBorderPainted(false);
		bt_reset.setContentAreaFilled(false);
		bt_reset.setFocusPainted(false);
		panel.add(bt_reset);
		lb_reset.setBounds(720, 120, 120, 40);	// ���� label
		panel.add(lb_reset);

		sc = new ImagePanel(); // ����ȭ����г�
		sc.setLayout(null);
		sc.setBounds(150, 50, 430, 430);
		panel.add(sc);
		start();
		this.add(panel);
		
		// ui
		setTitle("1 to 50 Game");
		setSize(900, 640);
		setLocationRelativeTo(null);									// ȭ�� �߾ӿ� ��ġ
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	class MyPanel extends JPanel{
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(bgImg, 0, 0, getWidth(), getHeight(), this);
			repaint();
		}
	} // end of InnerClass MyPanel

	public void start() {
		// �����ӳ������ų�⺻����
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// X��ư ������ ����
		this.addMouseListener(this);					// JFrame ���콺 �̺�Ʈ
		bt_start.addMouseListener(this);
		bt_reset.addMouseListener(this);	
		lb_home.addMouseListener(this);
		lb_sound.addMouseListener(this);
		sc.addMouseListener(this);
		
		th = new Thread(this);
		th.start();	

		time_format = new SimpleDateFormat("HH:mm:ss");	// �ð����˼���
		lb_time.setText("00:00:00");
		lb_title.setText("1 to 50 Game");

		sc.gameStart(false);							// ���� �����·� ����
	}
	
	public void run() {
		while (true) {
			try {
				repaint();
				TimeCheck();							// �ð� Ȯ��
				Thread.sleep(15);
			} catch (Exception e) {
			}
		}
	}
	
	public void TimeCheck() {
		if (time_run) {
			current_time = System.currentTimeMillis();	// ���� �ð� ���
			actual_time = current_time - start_time;	// ���� ���� �ð� = ���� �ð� - ��ư ���� �ð�
			
			sc.countDown((int) actual_time / 1000);		// ī��Ʈ�ٿ� ǥ�� �� (��)
			//System.out.println(actual_time);

			if (!sc.game_start && sc.check <= 50) {		// ���� ���� ���� && ���� 50���� �϶� �ð� ����
				show_time = time_format.format(actual_time - 32403000);
				lb_time.setText(show_time);
			}
		}
		if (sc.check > 50) {
			sc.ClearTime(lb_time.getText());// 50���� Ŭ���ϸ� ������ ����Ŭ���� �޼��� ��� �غ�
		}
	} // end of TimeCheck()
	
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == bt_start) {					// ���ӽ��۹�ư
			if (!time_run && !sc.game_start) {
				BGM stbgm = new BGM("select.wav",0);		// Ŭ�� �� �Ҹ�
				start_time = System.currentTimeMillis();	// ���۹�ư�������ýð����ޱ�
				sc.rect_select.clear();						// ���ӹ׽ð�����
				time_run = true;
				sc.gameStart(true);
			}
		} else if (e.getSource() == bt_reset) {				// ���� reset ��ư
			BGM stbgm = new BGM("select.wav",0);// Ŭ�� �� �Ҹ�
			start_time = 0;
			lb_time.setText("00:00:00");
			sc.rect_select.clear();
			sc.countDown(0);
			time_run = false;
			sc.gameStart(false);
			sc.check = 0;
			repaint();
		}
	}
	int sound = 1; // sound ���� flag
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == lb_home) {
			BGM stbgm = new BGM("select.wav",0);// Ŭ�� �� �Ҹ�
			bgm.clip.stop();
			dispose();
			new Carrier().init();
		} else if(e.getSource() == sc) {		// click�ϸ� Ŭ�� ��ġ�� ��� ����
		} else if(e.getSource() == lb_sound) {
			BGM stbgm = new BGM("select.wav",0);// Ŭ�� �� �Ҹ�
			if(sound == 1) {
				//System.out.println("mute �̺�Ʈ");
				lb_sound.setIcon(ii_speaker2);
				bgm.musicStop();
				++sound;
			} else if(sound == 2) {
				//System.out.println("�Ҹ��ѱ�");
				lb_sound.setIcon(ii_speaker1);
				bgm.musicStart();
				--sound;
			}
		}
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {	// hover sound
		if ((e.getSource()==bt_start)||(e.getSource()==bt_reset)||(e.getSource()==lb_home)||(e.getSource()==lb_sound)) {
			BGM hoverBgm = new BGM("fsound.wav",0);
		}
		
	}
	public void mouseExited(MouseEvent e) {}
}

class ImagePanel extends JPanel implements MouseListener{	// ���� ����ȭ�� ��� �г�
	int count = 3;						// ī��Ʈ�ٿ�ǥ�ÿ�
	int x, y; 							// ��ǥ��
	int check; 							// ����üũ��
	String time;						// Ŭ����ð���ǥ�ÿ�
	boolean game_start = false;
	Random ran_num = new Random();		// �����Լ�
	ImageIcon ii, ii2;					// ���� �� �̹��� ���� �̹���������(1-50)
	Image im, im2;
	Vector rect_select = new Vector();	// 1-50 ���ں����뺤��
	SelectRect sr;						// ���ں����밴üŬ��������Ű
	boolean flag = false;
	
	Container cp = new Container();
	JPanel pTest = new JPanel();
	
	ImagePanel() {
		this.addMouseListener(this);	
	}
	
	public void countDown(int count) {	// ���ӽð������޾ƿ�ī��Ʈ�ٿ�ǥ��
		switch (count) {
		case 0:
			this.count = 3;
			break;
		case 1:
			this.count = 2;
			break;
		case 2:
			this.count = 1;
			break;
		case 3:
			this.count = 0;
			game_start = false;
			break;
		}
	}

	public void gameStart(boolean game_start) {	// �⺻ ����: 25�� �ڽ�, �ڽ� �ȿ� �������� �Է�
		this.game_start = game_start;
		
		if (this.game_start) {	// 25�� �簢�� ��ǥ ��
			check = 1;
			for (int i = 0; i < 5; ++i) {
				for (int j = 0; j < 5; ++j) {
					int num = 0;
					int xx = 5 + i * 80;
					int yy = 5 + j * 80;
					
					// �ߺ�X (1-50 ���� �߻�)
					num = ran_num.nextInt(25) + 1;
					for (int k = 0; k < rect_select.size(); ++k) {
						sr = (SelectRect) rect_select.get(k);
						if (sr.number == num) {
							num = ran_num.nextInt(25) + 1;
							k = -1;
						}
					}
					// ���� ��ǥ, ������ ���Ϳ� ����
					sr = new SelectRect(xx, yy, num);
					rect_select.add(sr);
				}
			}
		}
	}
	
	ImageIcon iiClear = new ImageIcon("imgs/game150/clear.gif");
	Image clear = iiClear.getImage();
	Thread th = new Thread();
	
	boolean soundLoop = true;
	public void paint(Graphics g) {
		//g.drawRect(0, 0, 420, 420);
		ImageIcon bCube = new ImageIcon("imgs/game150/bCube.png");
		Image rectBack = bCube.getImage();
		g.drawImage(rectBack, 0, 0, 420, 420, this);

		// ����ȭ��簢�׵θ�
		if (game_start) {
			// ī��Ʈ �ٿ� �ؽ�Ʈ �׸���
			g.setFont(new Font("Default", Font.BOLD, 50));
			g.drawString("CountDown", 70, 150);
			g.setFont(new Font("Default", Font.BOLD, 100));
			g.drawString("" + count, 170, 250);
		} else if (!game_start && count == 0) {
			for (int i = 0; i < rect_select.size(); ++i) {
				// ���Ϳ� ����� ���� ���� �޾� �簢���� ���ڱ׸���
				if((sr.number%6) == 0) {
					ii = new ImageIcon("imgs/game150/cube2.png");
					im = ii.getImage();
					g.drawImage(im, sr.x - 7, sr.y, 90, 70, this);
					g.setFont(new Font("Default", Font.BOLD, 30));
					g.drawString("" + sr.number, sr.x + 22, sr.y + 45);
				} else if((sr.number%2) == 0) {
					ii = new ImageIcon("imgs/game150/cube.png");
					im = ii.getImage();
					g.drawImage(im, sr.x - 7, sr.y, 90, 70, this);
					g.setFont(new Font("Default", Font.BOLD, 30));
					g.drawString("" + sr.number, sr.x + 22, sr.y + 45);
				} else{
					ii = new ImageIcon("imgs/game150/cube1.png");
					im = ii.getImage();
					g.drawImage(im, sr.x - 7, sr.y, 90, 70, this);
					g.setFont(new Font("Default", Font.BOLD, 30));
					g.drawString("" + sr.number, sr.x + 22, sr.y + 45);
				}
				
				sr = (SelectRect) rect_select.get(i);	
			}
					
			// mouse hover: ���� �簢�ڽ� ���� �׵θ�
			g.setColor(Color.red);
			g.drawRect(x * 80 + 5, y * 80 + 5, 70, 70);
		}
		if (check > 50) {	// 50���� Ŭ���ϸ� ���� Ŭ���� // check > 50
			if (soundLoop) {
				BGM vicBgm = new BGM("vic.wav",0);	// ������ �Ҹ�
				//vicBgm.start();
				soundLoop = false;
			}
			
			int i = 0;
			while(i<60) {	// ���� Ŭ���� �� ȭ��: while�� -> (reset ������ ���� ������ ���� �ذ�)	
				g.drawImage(clear, -25, -70, 460, 400, this);
				g.setColor(Color.red);
				g.setFont(new Font("Default", Font.BOLD, 40));
				g.drawString(" " + time, 90, 300);
				i++;
				//System.out.println(i);
			}
		}
	} // end of paint()

	public void ClearTime(String time) {
		this.time = time;
	}
	public void mouseClicked(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		x = e.getX() / 80;
		y = e.getY() / 80;
		if (count == 0) {
			for (int i = 0; i < rect_select.size(); ++i) {
				sr = (SelectRect) rect_select.get(i);
				if (x == sr.x / 80 && y == sr.y / 80) {
					int xx = sr.x;
					int yy = sr.y;
					if (sr.number - check == 0) {
						check++;
						BGM stbgm = new BGM("cracksound.wav",0);
						rect_select.remove(i);		// 1-50 ������� ���� Ŭ���Ͽ� ����

						if (check < 27) {
							int num = 0;
							num = ran_num.nextInt(25) + 26;

							for (int k = 0; k < rect_select.size(); ++k) {
								sr = (SelectRect) rect_select.get(k);
								if (sr.number == num) {
									num = ran_num.nextInt(25) + 26;
									k = -1;
								}
							}
							// ���ڰ� ���ŵǸ� �� �ڸ��� ������ �߻����� ���� �߰�(26~50)
							sr = new SelectRect(xx, yy, num);
							rect_select.add(sr);		// Vector rect_select: 1-50 ���ں����뺤��
						}
					}
				}
			}
		}
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
}

class SelectRect {	// (1-50)����, ��ǥ ���� Ŭ����
	int x, y;
	int number;

	SelectRect(int x, int y, int number) {
		this.x = x;
		this.y = y;
		this.number = number;
	}
}