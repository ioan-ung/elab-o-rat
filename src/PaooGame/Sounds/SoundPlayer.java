package PaooGame.Sounds;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundPlayer {
    private Clip clip;
    private final URL[] soundURL = new URL[5];  // Keeps track of sounds
    private final URL ratsURL;                  // Used during win screen

    public SoundPlayer() {
        // Get sound URLs
        soundURL[0] = getClass().getResource("/sound/snd_dimbox.wav");
        soundURL[1] = getClass().getResource("/sound/snd_elecdoor_shut.wav");
        soundURL[2] = getClass().getResource("/sound/snd_credit_s.wav");
        soundURL[3] = getClass().getResource("/sound/snd_bigdoor_open.wav");
        soundURL[4] = getClass().getResource("/sound/snd_bell.wav");
        ratsURL = getClass().getResource("/sound/rats.wav");
    }

    // Used to set which sound to play
    public void setSound(int i) {
        try {
            AudioInputStream ais = i<0 ? AudioSystem.getAudioInputStream(ratsURL) : AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
        } catch (Exception e) {
            System.out.println("Audio file exception!");
        }
    }

    public void play() {
        clip.start();
    }
    public void stop() {
        clip.stop();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
