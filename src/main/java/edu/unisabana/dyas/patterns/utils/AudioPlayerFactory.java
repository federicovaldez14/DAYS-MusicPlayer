package edu.unisabana.dyas.patterns.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Punto único de composición que decide, a partir del {@code audioType} solicitado,
 * qué implementación de {@link AudioPlayer} (adaptador) debe usarse.
 *
 * <p>Esta fábrica es la única clase del proyecto que conoce la existencia de los
 * adaptadores concretos ({@link AdvancedAudioPlayerAdapter}, {@link PremiumAudioPlayerAdapter}).
 * {@code Client} solo conoce el tipo {@link AudioPlayer} devuelto por
 * {@link #getPlayer(String)}.</p>
 *
 * <p><b>Extensibilidad:</b> para agregar un tercer proveedor no es necesario modificar
 * {@code Client} ni ninguno de los adaptadores ya existentes. Basta con:
 * <ol>
 *     <li>Crear su propio adaptador (nueva clase que implemente {@link AudioPlayer}).</li>
 *     <li>Registrar, en el bloque estático de esta fábrica, los nuevos tipos de audio
 *     que ese adaptador resuelve, mediante {@link #register(String, Supplier)}.</li>
 * </ol>
 * Ninguna clase existente (Client, AdvancedAudioPlayerAdapter, PremiumAudioPlayerAdapter)
 * se ve afectada por ese cambio.</p>
 */
public final class AudioPlayerFactory {

    private static final Map<String, Supplier<AudioPlayer>> REGISTRY = new LinkedHashMap<>();

    static {
        register(AdvancedAudioPlayerAdapter.TYPE_MP4, AdvancedAudioPlayerAdapter::new);
        register(AdvancedAudioPlayerAdapter.TYPE_VLC, AdvancedAudioPlayerAdapter::new);
        register(PremiumAudioPlayerAdapter.TYPE_FLAC, PremiumAudioPlayerAdapter::new);
        register(PremiumAudioPlayerAdapter.TYPE_AAC, PremiumAudioPlayerAdapter::new);
    }

    private AudioPlayerFactory() {
        // Clase utilitaria: no instanciable
    }

    /**
     * Registra qué proveedor (fábrica de {@link AudioPlayer}) debe usarse para un
     * tipo de audio dado. Permite incorporar nuevos proveedores sin modificar
     * los adaptadores existentes ni {@code Client}.
     */
    public static void register(String audioType, Supplier<AudioPlayer> supplier) {
        REGISTRY.put(audioType.toLowerCase(), supplier);
    }

    /**
     * Devuelve una instancia de {@link AudioPlayer} capaz de reproducir el
     * {@code audioType} solicitado.
     *
     * @throws IllegalArgumentException si ningún adaptador registrado soporta el formato.
     */
    public static AudioPlayer getPlayer(String audioType) {
        if (audioType == null) {
            throw new IllegalArgumentException("audioType no puede ser null");
        }
        Supplier<AudioPlayer> supplier = REGISTRY.get(audioType.toLowerCase());
        if (supplier == null) {
            throw new IllegalArgumentException("Formato de audio no soportado: " + audioType);
        }
        return supplier.get();
    }
}
