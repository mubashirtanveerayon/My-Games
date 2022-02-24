package listener;

import values.Parameter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


public class Sound {

    private AudioInputStream audioInputStream;
    private Clip clip;

    private String moveSoundPath,captureSoundPath;

    public Sound(){
        moveSoundPath = Parameter.getPath('m');
        captureSoundPath = Parameter.getPath('c');
    }

    public Clip play(boolean isCapture){
        String soundPath = isCapture ?captureSoundPath  : moveSoundPath;
        try{
            audioInputStream = AudioSystem.getAudioInputStream(new File(soundPath));
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
