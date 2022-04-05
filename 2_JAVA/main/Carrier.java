package main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.TimerTask;
import java.util.Timer;
import java.util.Random;

import game150.Game150;
import snow.Main2;


@SuppressWarnings("serial")
public class Carrier extends JFrame implements ActionListener {
	Container cp;
	ImageIcon backImg,to50,snow,title,fire;
	
	JButton snowBt;
	JButton to50Bt;
	JPanel p;
	JLabel lamain,lastar,lasnow,lato50,latitle,lafire;
	Random random;
	boolean rt;
	boolean rf;
	boolean r;
	boolean music;
	
	Clip clip;
	
	public void init() {
		cp = getContentPane();
		p = new JPanel();
		p.setLayout(new BorderLayout());
		
		backImg = new ImageIcon(("imgs/main/main.png"));
		snow = new ImageIcon(("imgs/main/그림1.png"));
		to50 = new ImageIcon(("imgs/main/test1.gif"));
		title = new ImageIcon(("imgs/main/Title.png"));
		//fire = new ImageIcon(("C:\\Users\\Kosmo_02\\Desktop\\eclipse\\workspace\\test\\src\\main\\img\\test.png"));
		
		lafire =new JLabel(fire);
		//fireBt = new JButton(fire);
	/*	lasnow = new JLabel();
		lasnow.setText("눈피하기");
		lasnow.setBounds(285, 50, 550, 500);
		
		//lato50.setText("1 TO 50");*/
		MouseListener mousein = new MouseAdapter() {
			public void mouseEntered(MouseEvent e){
				Object obj = e.getSource();
	
				if(obj == snowBt) {
					//snowBt.setAction((Action)lafire);
					//Play("C:\\Users\\Kosmo_02\\Desktop\\eclipse\\workspace\\Game\\src\\main\\sound\\마리오동전.wav", false);

				}
				if(obj == to50Bt) {
					//Play("C:\\Users\\Kosmo_02\\Desktop\\eclipse\\workspace\\Game\\src\\main\\sound\\마리오동전.wav", false);
				}
			}
		};
		/*MouseListener mouseout = new MouseAdapter() {
			public void mouseExited(MouseEvent e){
				Object obj = e.getSource();
				if(obj == snowBt) {
				}
				if(obj == to50Bt) {
					
				}
			}
		};*/
		snowBt = new JButton(snow);
		snowBt.setBorderPainted(false);
		snowBt.setFocusPainted(false);
		snowBt.setContentAreaFilled(false);
		snowBt.addMouseListener(mousein);
		//snowBt.addMouseListener(mouseout);
		snowBt.addActionListener(this);
		snowBt.setBounds(170, 220, 300, 267);
		
		to50Bt = new JButton(to50);
		to50Bt.setBorderPainted(false);
		to50Bt.setFocusPainted(false);
		to50Bt.setContentAreaFilled(false);
		to50Bt.addActionListener(this);
		to50Bt.setBounds(720, 235, 300, 267);
		
		latitle = new JLabel(title);
		latitle.setBounds(290,50,594,179);
		
		/*Font f = new Font("굴림",Font.BOLD,23);*/
		
		lamain = new JLabel(backImg);
		
		Random();
		
		/*cp.add(lato50);cp.add(lasnow);*/
		cp.add(latitle);cp.add(to50Bt);cp.add(snowBt);cp.add(lamain);
		//setLayout(null);
		Timer time = new Timer();
		TimerTask t= new TimerTask(){
			public void run() {
				
				if(r) {
					music = true;
					Play("imgs/main/main3.wav", true);
				}else{
					music = true;
					Play("imgs/main/main.wav", true);
					}	
			}
		};
		time.schedule(t, 0);
		setUI();
		}		
	
	public void Random(){
		Random random =new Random();
		r = random.nextBoolean();
		System.out.print(r);
		}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object obj = e.getSource();
		if(obj == snowBt){
			music = false;
			try {
			Thread.sleep(100);
			}catch(Exception e1){}
			music = true;
			Play("imgs/main/티모웃음.wav", false);
			try {
				Thread.sleep(10);
				}catch(Exception e1){}
			music = false;
			dispose();
			new Main2();
			
		}
		if(obj == to50Bt) {
			music = false;
			try {
				Thread.sleep(100);
				}catch(Exception e1){}
			music = true;
			Play("imgs/main/티모웃음.wav", false);
			try {
				Thread.sleep(10);
				}catch(Exception e1){}
			music = false;
			dispose();
			new Game150();
		}
	}
	
	/*public void paint(Graphics g) {
		g.drawImage(backImg.getImage(), 0,0, null);
	}*/
	
	  public void Play(String fileName, boolean Loop)
	    {
	        try
	        {	
	            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileName));
	            clip = AudioSystem.getClip();  
	            clip.open(ais);
	            if(music==true) 
	            	clip.start();
	            if(Loop)
	            	clip.loop(10);
	            while(music) {
	            	try {
						Thread.sleep(10);
					}catch(Exception e){}
		            	if(Loop == false) {
			            	for(int i=0;i<110;i++) {
				            	try {
									Thread.sleep(10);
								}catch(Exception e){}
			            	}
			            	break;
		            	}
		            	
	            }
	            clip.stop();
	        }
	        catch (Exception ex){ex.printStackTrace();}
	    }
	  
	void setUI() {
		setTitle("Retrogames");
		setSize(1175, 697);
		//pack();
		//setLocation(200, 100);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	

	
	
	
	public static void main(String[] args) {
		new Carrier().init();

	}

}
