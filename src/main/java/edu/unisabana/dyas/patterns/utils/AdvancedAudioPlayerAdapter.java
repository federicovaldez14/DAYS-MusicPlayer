package edu.unisabana.dyas.patterns.utils;

/**
 * Adaptador (patrón Adapter - modalidad "de objeto", vía composición) que permite
 * usar {@link AdvancedAudioPlayer} (API de terceros, no modificable) a través de la
 * interfaz estándar {@link AudioPlayer} que el resto de la aplicación conoce.
 *
 * <p>{@link AdvancedAudioPlayer} sabe reproducir únicamente los formatos
 * {@code "mp4"} y {@code "vlc"}, cada uno con su propio método
 * ({@code playMp4}/{@code playVlc}). Este adaptador traduce la llamada única
 * {@link AudioPlayer#play(String, String)} hacia el método específico correspondiente,
 * y guarda una referencia interna al adaptado para poder delegar correctamente
 * {@link AudioPlayer#stop()}.</p>
 *
 * <p>Se optó por un adaptador dedicado (uno por proveedor) en lugar de un único
 * adaptador "combinado" para: (1) respetar el Principio de Responsabilidad Única
 * (cada adaptador conoce la API de un solo proveedor externo), (2) cumplir el
 * Principio Abierto/Cerrado — un tercer proveedor se incorpora agregando una nueva
 * clase adaptadora, sin tocar esta ni la de Premium — y (3) evitar acoplar entre sí
 * dos APIs de terceros que no tienen relación alguna.</p>
 */
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
