package game150;

import java.io.*;
import java.net.URL;

import javax.sound.sampled.*;

public class BGM {
	File bgm;
	Clip clip;

	BGM() {
		AudioInputStream stream;
		AudioFormat format;
		DataLine.Info info;
		bgm = new File("sounds\\bgm.wav");

		try {
			stream = AudioSystem.getAudioInputStream(bgm);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
			clip.loop(Clip.LOOP_CONTINUOUSLY);
		} catch (Exception e) {
			System.out.println("err : " + e);
		}
	}
	
	BGM(String name, int i) { // hover
		AudioInputStream stream;
		AudioFormat format;
		DataLine.Info info;
		bgm = new File("sounds\\" + name);
		try {
			stream = AudioSystem.getAudioInputStream(bgm);
			format = stream.getFormat();
			info = new DataLine.Info(Clip.class, format);
			clip = (Clip) AudioSystem.getLine(info);
			clip.open(stream);
			clip.start();
			clip.loop(i);
		} catch (Exception e) {
			System.out.println("err : " + e);
		}
	}

	void musicStop() {
		if (clip != null) {
			clip.stop();
		}
	}

	void musicStart() {
		if (clip != null) {
			clip.start();
		}
	}

	public static void main(String[] args) {
		new BGM();
	}
}
