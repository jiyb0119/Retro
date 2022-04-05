package snow;

import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import main.Carrier;

public class Dodge extends JFrame implements Runnable, KeyListener{
	private static final long serialVersionUID = 3768019951722129358L;
	/////////////////////////////////////////////////////////////////////////////////////
	Toolkit tk = Toolkit.getDefaultToolkit();
	Image img1 = tk.getImage("imgs/snow/man5.png"); //플레이어 이미지
	Image Img1 = img1.getScaledInstance(50, 50, Image.SCALE_SMOOTH);
	Image back = new ImageIcon("imgs/snow/back5.png").getImage();//배경 이미지
	Image buffImage; Graphics buffg;
    ////////////////////////////////////////////////////////////////////////////////////
	final static int FPS = 30;
	final static double acc = 0.5;       //https://m.blog.naver.com/PostView.naver?isHttpsRedirect=true&blogId=unidus2&logNo=140061181622
	final static double SpdLimit = 10.0; //가속도 
	static int width = 600; 
	static int height = 700;
	int x2 = width/2; // x좌표
	int y2 = 570; //플레이어 y좌표
	double change = 0;
	///////////////////////////////////////////////////////////////////////////////////
	public static Image snowImg = new ImageIcon("imgs/snow/snow4.png").getImage();
	SnowManager SnowManager = new SnowManager();
	/////////////////////////////////////////////////////////////////////////////////////
	Clip clip;
	////////////////////////////////////////////////////////////////////////////////////
	Dodge() {
		new Thread(this).start();
		addKeyListener(this);
		setUI();
		mplay("imgs/snow/snow2.wav");
	}
	protected void Process() {     
      SnowManager.makeSnow();
      SnowManager.move();
	}
	void setUI() { //UI Setting
		setTitle("Dodge");
		setSize(width,height);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	public void paint(Graphics g) {
		buffImage = createImage(width,height);
		buffg = buffImage.getGraphics();
		update(g);
	}
	public void update(Graphics g) {
		drawBack();
		drawPlayer();
		drawString();
		SnowManager.paint(buffg);
		g.drawImage(buffImage,0,0,this);
	}
	void drawPlayer(){
		buffg.drawImage(Img1,x2,y2,this);
	}
	void drawBack() { //배경
		buffg.clearRect(0,0,width,height);
		buffg.drawImage(back, 0, 0, this);
	}
	void drawString() {
		buffg.setFont(new Font("고딕체",Font.BOLD,15));
		buffg.drawString("BGM ON : 1",400,80);
		buffg.drawString("BGM OFF : 2",400,95);
		buffg.setFont(new Font("고딕체",Font.ITALIC,30));
		buffg.drawString("Score : "+ SnowManager.score,400,65);
	}
	boolean start = true;
	public void run() {//Thread
		long lastTime = System.currentTimeMillis();
		long curTime = lastTime;
				while(start) {
					curTime = System.currentTimeMillis();
					if(curTime - lastTime >= (1000.0/FPS)) {
						lastTime = curTime;
						if(Right == false) {
							if(change > 1) { //x
								change -= (acc);
							}
						}
						if(Left == false) {
							if(change < -1) { //x
								change += (acc);
							}
						}
						if(x2+15 < SpdLimit && change < 0) {
							change = 0;
						}
						else if(x2+30 > (width-SpdLimit*2) && change > 0) {
							change = 0;
						}
				x2 += change;
				repaint();
				Process();
				crashProcess();
			} 
		}
	}
	boolean Left = false;
	boolean Right = false;
	public void keyTyped(KeyEvent e){}
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			if(change < SpdLimit) change += acc;
			Right = true;
		}else if(e.getKeyCode()==KeyEvent.VK_LEFT){
			if(change > -SpdLimit) change -= acc;
			Left = true;
		}
		switch (e.getKeyCode()) {
			case KeyEvent.VK_1:
				try {
					clip.stop();
					clip.start();
					clip.loop(100);
					}catch(NullPointerException ne) {}
				break;
			case KeyEvent.VK_2:
				try {
					clip.stop();
					}catch(NullPointerException ne) {}
				break;
		}
	}
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode()==KeyEvent.VK_RIGHT) {
			Right = false;
		}
		if(e.getKeyCode()==KeyEvent.VK_LEFT) {
			Left = false;
		}
	}
	public boolean crashCheck(int object_x1, int object_y1, int object_x2, int object_y2, Image Img1, Image snowImg){
		// 충돌 판정
		boolean crashcheck = false;
		int check_x = Math.abs((object_x1 + Img1.getWidth(null)/2) - (object_x2+snowImg.getWidth(null)/2));
		int base_x = Math.abs(Img1.getWidth(null)/2 + snowImg.getWidth(null)/2);
		int check_y = Math.abs((object_y1 + Img1.getHeight(null)/2) - (object_y2+snowImg.getHeight(null)/2));
		int base_y = Math.abs(Img1.getHeight(null)/2 + snowImg.getHeight(null)/2);
		if( check_x < base_x && check_y < base_y ){
			crashcheck = true;
		}else{
			crashcheck = false;
		}
		return crashcheck;
	}
	public void crashProcess() { //충돌
		for (int i = 0; i < SnowManager.snowList.size(); i++) {
            Snow snow = SnowManager.snowList.get(i);
		if(crashCheck(x2 , y2, snow.pos.x, snow.pos.y, Img1, Dodge.snowImg)){
			//플레이어와 적 충돌
			SnowManager.snowList.remove(i);
			clip.stop();
			gameOver();//눈 맞았을 때
			}
		}
	}
    void gameOver() {
    	setVisible(false);
    	start = false;
		int answer = JOptionPane.showConfirmDialog(null, "SCORE: " + SnowManager.score + "\n다시 시작할까요??", "Game_Over", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null);	
		for (int i = 0; i < SnowManager.snowList.size(); i++) {
            Snow snow = SnowManager.snowList.get(i);
        SnowManager.snowList.removeAll(SnowManager.snowList);
    	if(answer == JOptionPane.YES_OPTION) {
    		clip.loop(100);
    		setVisible(true);
    		SnowManager.score = 0;
    		//setBoard();
    		start = true;
    	}else {
    		clip.stop();
    		start = false;
    		setVisible(false);
    		new Carrier().init();
    		}
    	}
    }
    public void mplay(String fileName) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
			clip = AudioSystem.getClip();
			try {
				clip.stop();
				}catch(NullPointerException ne) {}
			clip.open(ais);
			try {
				clip.start();
				}catch(NullPointerException ne) {}
			clip.loop(100);
		} catch (Exception ex) {
		}
	}
}