package be.hcbgsystem.awareness.providers;

import be.hcbgsystem.core.models.awareness.AwarenessMessage;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioAwarenessProvider implements AwarenessProvider {
    @Override
    public void provideAwareness(AwarenessMessage message) {
        File audioFile = new File(getClass().getClassLoader().getResource("audio-awareness.wav").getFile());
        AudioInputStream audioInputStream = null;
        try {
            audioInputStream = AudioSystem.getAudioInputStream(audioFile.getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        (new AudioAwarenessProvider()).provideAwareness(null);
    }
}
