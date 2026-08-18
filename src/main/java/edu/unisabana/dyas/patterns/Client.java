package edu.unisabana.dyas.patterns;

import edu.unisabana.dyas.patterns.utils.AudioPlayer;
import edu.unisabana.dyas.patterns.utils.AudioPlayerFactory;

/**
 * Punto de entrada de la aplicación.
 *
 * <p>Client reproduce archivos "mp4", "vlc", "flac" y "aac" usando
 * exclusivamente la interfaz {@link AudioPlayer} (play/stop). No conoce
 * ni referencia en ningún punto a {@code AdvancedAudioPlayer} ni a
 * {@code PremiumAudioPlayer}: la resolución de qué adaptador concreto
 * usar para cada tipo de audio queda delegada a {@link AudioPlayerFactory}.
 */
public class Client {
    public static void main(String[] args) {
        playTrack("mp4", "video.mp4");
        playTrack("vlc", "pelicula.vlc");
        playTrack("flac", "album.flac");
        playTrack("aac", "podcast.aac");
    }

    /**
     * Reproduce y luego detiene un archivo, usando únicamente la interfaz
     * {@link AudioPlayer}. El adaptador concreto (Advanced o Premium) es
     * resuelto internamente por {@link AudioPlayerFactory} según audioType.
     */
    private static void playTrack(String audioType, String fileName) {
        AudioPlayer audioPlayer = AudioPlayerFactory.getPlayer(audioType);
        audioPlayer.play(audioType, fileName);
        audioPlayer.stop();
    }
}
