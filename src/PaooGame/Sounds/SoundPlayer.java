package PaooGame.Sounds;

import PaooGame.Exceptions.AssetException.AudioException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.BooleanControl;
import java.net.URL;

public class SoundPlayer {
    private Clip clip;
    private final URL[] soundURL = new URL[6];  // Keeps track of sounds
    private final URL ratsURL;                  // Used during win screen
    private final URL miceLiving, miceVenus;    // Background music

    private static final float SFX_VOLUME = -20.0f;
    private static final float MUSIC_VOLUME = -10.0f;

    private boolean isMuted = false;

    public SoundPlayer() {
        // Get sound URLs
        soundURL[0] = getClass().getResource("/sound/snd_dimbox.wav");
        soundURL[1] = getClass().getResource("/sound/snd_elecdoor_shut.wav");
        soundURL[2] = getClass().getResource("/sound/snd_credit_s.wav");
        soundURL[3] = getClass().getResource("/sound/snd_bigdoor_open.wav");
        soundURL[4] = getClass().getResource("/sound/snd_bell.wav");
        soundURL[5] = getClass().getResource("/sound/mus_myemeow.wav");
        miceLiving = getClass().getResource("/sound/05 - Living Mice.wav");
        miceVenus = getClass().getResource("/sound/11 - Mice on Venus.wav");
        ratsURL = getClass().getResource("/sound/rats.wav");
    }

    // Used to set which sound to play
    public void setSound(int i) {
        try {
            AudioInputStream ais;
            switch (i) {
                case -1: ais = AudioSystem.getAudioInputStream(miceLiving); break;
                case -2: ais = AudioSystem.getAudioInputStream(miceVenus); break;
                case -3: ais = AudioSystem.getAudioInputStream(ratsURL); break;
                default: ais = AudioSystem.getAudioInputStream(soundURL[i]);
            }
            clip = AudioSystem.getClip();
            clip.open(ais);
            // Sets volume
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            if (i < 0) {
                gainControl.setValue(MUSIC_VOLUME);
                BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
                muteControl.setValue(isMuted);
            }
            else gainControl.setValue(SFX_VOLUME);
        } catch (Exception e) {
            throw new AudioException("sound index " + i);
        }
    }

    public void setMuted(boolean mute) {
        this.isMuted = mute;
        if (clip == null) return;

        BooleanControl muteControl = (BooleanControl) clip.getControl(BooleanControl.Type.MUTE);
        muteControl.setValue(isMuted);
    }

    public void play() {
        clip.start();
    }
    public void stop() {
        if (clip == null) return;
        clip.stop();
        clip.close();
    }
    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public boolean isMuted() {
        return isMuted;
    }}
