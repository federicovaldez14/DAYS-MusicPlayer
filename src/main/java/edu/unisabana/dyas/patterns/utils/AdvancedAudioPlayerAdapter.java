package edu.unisabana.dyas.patterns.utils;

public class AdvancedAudioPlayerAdapter implements AudioPlayer {

    /** Formatos soportados por este adaptador. */
    public static final String TYPE_MP4 = "mp4";
    public static final String TYPE_VLC = "vlc";

    private final AdvancedAudioPlayer advancedAudioPlayer;

    public AdvancedAudioPlayerAdapter() {
        this.advancedAudioPlayer = new AdvancedAudioPlayer();
    }

    @Override
    public void play(String audioType, String fileName) {
        if (audioType == null) {
            throw new IllegalArgumentException("audioType no puede ser null");
        }
        switch (audioType.toLowerCase()) {
            case TYPE_MP4:
                advancedAudioPlayer.playMp4(fileName);
                break;
            case TYPE_VLC:
                advancedAudioPlayer.playVlc(fileName);
                break;
            default:
                throw new IllegalArgumentException(
                        "AdvancedAudioPlayerAdapter no soporta el formato: " + audioType);
        }
    }

    @Override
    public void stop() {
        advancedAudioPlayer.stop();
    }
}
