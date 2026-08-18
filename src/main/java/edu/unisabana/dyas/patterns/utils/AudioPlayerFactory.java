package edu.unisabana.dyas.patterns.utils;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Supplier;


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
