package snow;
 
import java.awt.Graphics;
import java.awt.Rectangle;

import snow.Dodge;
//눈 자신 한 개를 표현
public class Snow extends Item {
	public Rectangle pos = new Rectangle();
    public Snow() {
   //눈이미지를 가져와서 네모(rectangle)의 가로 값에 저장
   //눈이미지를 가져와서 네모(rectangle)의 세로 값에 저장    
    	pos.width  = Dodge.snowImg.getWidth(null);
        pos.height = Dodge.snowImg.getHeight(null);
    }
    public int speed;//이동속도
    //눈이 움직이는 메소드
    @Override
    public boolean move() { 
        //이동한 결과
        //pos.t = pos.y + speed;
        pos.y += speed + 5;
        //점수 달성했을 때 속도 증가
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
        //게임판 높이보다 눈의 높이가 작은지 (눈이 화면 안에 있는지)
        return (pos.y  <= Dodge.height - 100); // 눈이 땅에 닿았을 때 SnowManager에서 삭제
    } 
    //눈을 그리는 메서드(자기 자신이 자신을 그림)
    @Override
    public void paint(Graphics g) {
        g.drawImage(Dodge.snowImg,pos.x,pos.y,null);
    }
} 