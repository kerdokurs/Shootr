package me.kerdo.shootr.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio {
	public static void playSound(String file, float gain) {
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Audio.class.getResource("/audio/" + file));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(gain);
			
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}