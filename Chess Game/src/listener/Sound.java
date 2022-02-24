package listener;

import values.Parameter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;
import java.net.URL;

public class Sound {

    private AudioInputStream audioInputStream;
    private Clip clip;

    private URL moveSound,captureSound;

    public Sound(){
        moveSound = Parameter.getPath('m');
        captureSound = Parameter.getPath('c');
    }

    public Clip play(boolean isCapture){
        URL sound = isCapture ?captureSound  : moveSound;
        try{
            audioInputStream = AudioSystem.getAudioInputStream(sound);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            if (clip.isRunning())
                stop();

            // Rewind clip to beginning
            clip.setFramePosition(0);

            // Play clip
            clip.start();
        }catch (Exception e){
            e.printStackTrace();
        }
        return clip;
    }

    public void stop(){
        if(clip == null){
            return;
        }
        clip.stop();
    }

}
