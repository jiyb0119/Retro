package snow;
 
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// 눈 관리(움직임 및 그리기 화면에서 없어지기)
public class SnowManager {
	static int score; //점수
    //final을 삭제할경우 변수처리가 가능함...
    //20번에 한번씩 만듬
    public static int SNOW_MAKE_INTERVAL = 10;
    //눈을 생성헀다가 없애야하니까 자동으로 생성 및 삭제를 하기위한 ArrayList를 사용
    public List<Snow> snowList = new ArrayList<Snow>();
    Random rand = new Random(); //랜덤
    int snowMakeInterval = SNOW_MAKE_INTERVAL;
    // 눈만드는 메소드
    public void makeSnow() {
        //초당 5개 생성
        if(snowMakeInterval == SNOW_MAKE_INTERVAL) {
            Snow snow = new Snow(); //눈생성
            snow.pos.y = -snow.pos.height;// 눈이미지 크기에 -를 붙임
            //snow.pos.width/2는 화면에서 안보이는 경우를 대비해 반은 보여주게 설정
            snow.pos.x = rand.nextInt(Dodge.width) - snow.pos.width/2;//0 ~ 399
            snow.speed = rand.nextInt(3) + 3; // 3~5
            //생성된 눈을 리스트에 추가
            snowList.add(snow);
        }
        snowMakeInterval--;
        if(snowMakeInterval<0) 
            snowMakeInterval = SNOW_MAKE_INTERVAL;
    }
    // 눈 움직이기(눈 사이즈만큼 배열 가지고 있음)
    public void move() {
        for (int i = 0; i < snowList.size(); i++) {
            Snow snow = snowList.get(i);
            //(화면 밖으로 벗어난 경우) 땅에 닿았을 때
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