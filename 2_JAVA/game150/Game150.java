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
	Image img_button = ii3.getImage();				//ImageIcon을 Image로 변환.
	Image changeImg = img_button.getScaledInstance(120, 40, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ii_button = new ImageIcon(changeImg); // start reset 이미지
	
	ImageIcon ii5 = new ImageIcon("imgs/game150/home.png");
	Image img_home = ii5.getImage();				
	Image changeImg3 = img_home.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ii_home = new ImageIcon(changeImg3);	// home label 이미지(이글루)
	
	ImageIcon ii6 = new ImageIcon("imgs/game150/speaker1.png");
	Image img_speaker1 = ii6.getImage();				
	Image changeImg4 = img_speaker1.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ii_speaker1 = new ImageIcon(changeImg4); // speaker1 label 이미지
	
	ImageIcon ii7 = new ImageIcon("imgs/game150/speaker2.png");
	Image img_speaker2 = ii7.getImage();				
	Image changeImg5 = img_speaker2.getScaledInstance(70, 70, java.awt.Image.SCALE_SMOOTH);
	ImageIcon ii_speaker2 = new ImageIcon(changeImg5); // speaker2 label 이미지(mute)
	
	private JLabel lb_title = new JLabel();				// 게임 타이틀 표시 라벨
	private JLabel lb_time = new JLabel();				// 시간 표시 라벨
	private JButton bt_start = new JButton("START");	// 게임시작 버튼
	private JButton bt_reset = new JButton("RESET");	// 게임리셋 버튼
	private JLabel lb_start = new JLabel(ii_button);	// start label
	private JLabel lb_reset = new JLabel(ii_button);	// reset label
	private JLabel lb_home = new JLabel(ii_home);		// 뒤로가기 label
	private JLabel lb_sound = new JLabel(ii_speaker1);	// 뒤로가기 label
	SimpleDateFormat time_format; 						// 시간값 변환하기 위한 포맷
	String show_time;									// 게임 진행 시간 담을 문자열
	long start_time, current_time, actual_time; 		// 게임시작시간, 컴퓨터시간, 실제게임진행시간
	boolean time_run = false;
	Thread th; 
	ImagePanel sc;										// 게임 진행화면 class

	ImageIcon bgIcon = new ImageIcon("imgs/game150/background.gif");	// 배경 gif 이미지
	Image bgImg = bgIcon.getImage();
	JButton bb = new JButton("practice");

	BGM bgm;											// 배경음악 class
	
	MainFrame(){
		System.out.println("생성자 ()");
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
		
		lb_time.setBounds(600, 50, 300, 50); // 프레임 title 텍스트 라벨
		lb_time.setFont(new Font("Default", Font.BOLD, 57));
		panel.add(lb_time);

		bt_start.setBounds(605, 122, 100, 30); // 게임시작 button
		bt_start.setFont(new Font("Default", Font.BOLD, 20));
		bt_start.setBorderPainted(false);
		bt_start.setContentAreaFilled(false);
		bt_start.setFocusPainted(false);
		panel.add(bt_start);
		lb_start.setBounds(595, 120, 120, 40);
		panel.add(lb_start);

		bt_reset.setBounds(730, 122, 105, 30); // 리셋 button
		bt_reset.setFont(new Font("Default", Font.BOLD, 20));
		bt_reset.setBorderPainted(false);
		bt_reset.setContentAreaFilled(false);
		bt_reset.setFocusPainted(false);
		panel.add(bt_reset);
		lb_reset.setBounds(720, 120, 120, 40);	// 리셋 label
		panel.add(lb_reset);

		sc = new ImagePanel(); // 게임화면용패널
		sc.setLayout(null);
		sc.setBounds(150, 50, 430, 430);
		panel.add(sc);
		start();
		this.add(panel);
		
		// ui
		setTitle("1 to 50 Game");
		setSize(900, 640);
		setLocationRelativeTo(null);									// 화면 중앙에 위치
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
		// 프레임내실행시킬기본내용
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);// X버튼 누르면 종료
		this.addMouseListener(this);					// JFrame 마우스 이벤트
		bt_start.addMouseListener(this);
		bt_reset.addMouseListener(this);	
		lb_home.addMouseListener(this);
		lb_sound.addMouseListener(this);
		sc.addMouseListener(this);
		
		th = new Thread(this);
		th.start();	

		time_format = new SimpleDateFormat("HH:mm:ss");	// 시간포맷설정
		lb_time.setText("00:00:00");
		lb_title.setText("1 to 50 Game");

		sc.gameStart(false);							// 게임 대기상태로 세팅
	}
	
	public void run() {
		while (true) {
			try {
				repaint();
				TimeCheck();							// 시간 확인
				Thread.sleep(15);
			} catch (Exception e) {
			}
		}
	}
	
	public void TimeCheck() {
		if (time_run) {
			current_time = System.currentTimeMillis();	// 현재 시간 담기
			actual_time = current_time - start_time;	// 게임 진행 시간 = 현재 시간 - 버튼 누른 시간
			
			sc.countDown((int) actual_time / 1000);		// 카운트다운 표시 용 (초)
			//System.out.println(actual_time);

			if (!sc.game_start && sc.check <= 50) {		// 게임 시작 상태 && 숫자 50이하 일때 시간 갱신
				show_time = time_format.format(actual_time - 32403000);
				lb_time.setText(show_time);
			}
		}
		if (sc.check > 50) {
			sc.ClearTime(lb_time.getText());// 50까지 클릭하면 끝나면 게임클리어 메세지 띄울 준비
		}
	} // end of TimeCheck()
	
	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == bt_start) {					// 게임시작버튼
			if (!time_run && !sc.game_start) {
				BGM stbgm = new BGM("select.wav",0);		// 클릭 시 소리
				start_time = System.currentTimeMillis();	// 시작버튼눌렀을시시간값받기
				sc.rect_select.clear();						// 게임및시간세팅
				time_run = true;
				sc.gameStart(true);
			}
		} else if (e.getSource() == bt_reset) {				// 게임 reset 버튼
			BGM stbgm = new BGM("select.wav",0);// 클릭 시 소리
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
	int sound = 1; // sound 조절 flag
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == lb_home) {
			BGM stbgm = new BGM("select.wav",0);// 클릭 시 소리
			bgm.clip.stop();
			dispose();
			new Carrier().init();
		} else if(e.getSource() == sc) {		// click하면 클릭 위치에 펭귄 나옴
		} else if(e.getSource() == lb_sound) {
			BGM stbgm = new BGM("select.wav",0);// 클릭 시 소리
			if(sound == 1) {
				//System.out.println("mute 이벤트");
				lb_sound.setIcon(ii_speaker2);
				bgm.musicStop();
				++sound;
			} else if(sound == 2) {
				//System.out.println("소리켜기");
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

class ImagePanel extends JPanel implements MouseListener{	// 실제 게임화면 출력 패널
	int count = 3;						// 카운트다운표시용
	int x, y; 							// 좌표값
	int check; 							// 숫자체크용
	String time;						// 클리어시간값표시용
	boolean game_start = false;
	Random ran_num = new Random();		// 랜덤함수
	ImageIcon ii, ii2;					// 숫자 뒤 이미지 넣을 이미지아이콘(1-50)
	Image im, im2;
	Vector rect_select = new Vector();	// 1-50 숫자보관용벡터
	SelectRect sr;						// 숫자보관용객체클래스접근키
	boolean flag = false;
	
	Container cp = new Container();
	JPanel pTest = new JPanel();
	
	ImagePanel() {
		this.addMouseListener(this);	
	}
	
	public void countDown(int count) {	// 게임시간값을받아와카운트다운표시
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

	public void gameStart(boolean game_start) {	// 기본 세팅: 25개 박스, 박스 안에 랜덤난수 입력
		this.game_start = game_start;
		
		if (this.game_start) {	// 25개 사각형 좌표 값
			check = 1;
			for (int i = 0; i < 5; ++i) {
				for (int j = 0; j < 5; ++j) {
					int num = 0;
					int xx = 5 + i * 80;
					int yy = 5 + j * 80;
					
					// 중복X (1-50 난수 발생)
					num = ran_num.nextInt(25) + 1;
					for (int k = 0; k < rect_select.size(); ++k) {
						sr = (SelectRect) rect_select.get(k);
						if (sr.number == num) {
							num = ran_num.nextInt(25) + 1;
							k = -1;
						}
					}
					// 생성 좌표, 난수를 벡터에 저장
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

		// 게임화면사각테두리
		if (game_start) {
			// 카운트 다운 텍스트 그리기
			g.setFont(new Font("Default", Font.BOLD, 50));
			g.drawString("CountDown", 70, 150);
			g.setFont(new Font("Default", Font.BOLD, 100));
			g.drawString("" + count, 170, 250);
		} else if (!game_start && count == 0) {
			for (int i = 0; i < rect_select.size(); ++i) {
				// 벡터에 저장된 숫자 값을 받아 사각형과 숫자그리기
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
					
			// mouse hover: 선택 사각박스 붉은 테두리
			g.setColor(Color.red);
			g.drawRect(x * 80 + 5, y * 80 + 5, 70, 70);
		}
		if (check > 50) {	// 50까지 클릭하면 게임 클리어 // check > 50
			if (soundLoop) {
				BGM vicBgm = new BGM("vic.wav",0);	// 끝나면 소리
				//vicBgm.start();
				soundLoop = false;
			}
			
			int i = 0;
			while(i<60) {	// 게임 클리어 시 화면: while문 -> (reset 누르면 뒷편 깜빡임 문제 해결)	
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
						rect_select.remove(i);		// 1-50 순서대로 숫자 클릭하여 제거

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
							// 숫자가 제거되면 그 자리에 난수를 발생시켜 숫자 추가(26~50)
							sr = new SelectRect(xx, yy, num);
							rect_select.add(sr);		// Vector rect_select: 1-50 숫자보관용벡터
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

class SelectRect {	// (1-50)숫자, 좌표 보관 클래스
	int x, y;
	int number;

	SelectRect(int x, int y, int number) {
		this.x = x;
		this.y = y;
		this.number = number;
	}
}