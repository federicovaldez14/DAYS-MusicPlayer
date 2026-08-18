package edu.unisabana.dyas.patterns.utils;

/**
 * Adaptador (patrón Adapter) que permite usar {@link PremiumAudioPlayer}
 * (API de terceros, no modificable) a través de la interfaz estándar
 * {@link AudioPlayer}.
 *
 * <p>{@link PremiumAudioPlayer} reproduce cualquier códec (incluidos
 * {@code "flac"} y {@code "aac"}) mediante un único método
 * {@code playAudio(codec, path, volumePercent)}, pero exige siempre un
 * volumen explícito (0-100). Como {@link AudioPlayer#play(String, String)}
 * no recibe volumen, este adaptador define y aplica un <b>volumen por
 * defecto documentado</b>:</p>
 *
 * <pre>DEFAULT_VOLUME_PERCENT = {@value #DEFAULT_VOLUME_PERCENT} (70%)</pre>
 *
 * <p>Se eligió 70% como un nivel de escucha cómodo y seguro para el oído
 * (ampliamente recomendado como límite prudente de volumen), evitando tanto
 * un volumen inaudible (muy bajo) como uno potencialmente dañino o que sature
 * el audio (100%). Si la aplicación necesitara volúmenes distintos por
 * pista, se podría extender el constructor de este adaptador para recibirlo,
 * sin afectar a {@code Client} ni al resto del diseño.</p>
 */
public class PremiumAudioPlayerAdapter implements AudioPlayer {

    /** Formatos soportados por este adaptador. */
    public static final String TYPE_FLAC = "flac";
    public static final String TYPE_AAC = "aac";

    /**
     * Volumen por defecto (0-100) usado cuando {@link AudioPlayer#play(String, String)}
     * no permite especificar uno explícitamente, ya que {@link PremiumAudioPlayer}
     * exige siempre un volumen. Documentado como 70%: nivel de escucha
     * moderado y seguro.
     */
    public static final int DEFAULT_VOLUME_PERCENT = 70;

    private final PremiumAudioPlayer premiumAudioPlayer;

    public PremiumAudioPlayerAdapter() {
        this.premiumAudioPlayer = new PremiumAudioPlayer();
    }

    @Override
    public void play(String audioType, String fileName) {
        if (audioType == null) {
            throw new IllegalArgumentException("audioType no puede ser null");
        }
        String codec = audioType.toLowerCase();
        if (!codec.equals(TYPE_FLAC) && !codec.equals(TYPE_AAC)) {
            // PremiumAudioPlayer soporta "cualquier códec"; se restringe aquí
            // solo a los tipos que este ejercicio declara resolver con este adaptador.
            throw new IllegalArgumentException(
                    "PremiumAudioPlayerAdapter no soporta el formato: " + audioType);
        }
        premiumAudioPlayer.playAudio(codec, fileName, DEFAULT_VOLUME_PERCENT);
    }

    @Override
    public void stop() {
        premiumAudioPlayer.halt();
    }
}
