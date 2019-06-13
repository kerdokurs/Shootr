package me.kerdo.shootr.audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Audio {
  // TODO: NOT WORKING
  public static Clip loadSound(final String file) {
    try {
      final AudioInputStream ais = AudioSystem.getAudioInputStream(Audio.class.getResource("/audio/" + file));
      final Clip clip = AudioSystem.getClip();
      clip.open(ais);

      return clip;
    } catch (Exception e) {
      e.printStackTrace();
    }

    return null;
  }

  public static void playSound(final Clip clip, final float gain) {
    final FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    gainControl.setValue(gain);

    clip.start();
  }
}