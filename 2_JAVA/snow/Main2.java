package snow;

import javax.swing.JOptionPane;

import main.Carrier;
import snow.Dodge;

public class Main2 {

	public Main2() {
	int answer2 = JOptionPane.showConfirmDialog(null,  "               <START> \n �¿� ����Ű ����ؼ� �� ���ϱ�", "GAME_START", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null);
	if(answer2 == JOptionPane.YES_OPTION) {
		new Dodge();
	}else {
		new Carrier().init();
		}
	}
	public static void main(String[] args) {
		new Main2();
	}
}
