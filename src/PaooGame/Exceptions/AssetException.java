package PaooGame.Exceptions;

import java.io.IOException;

public class AssetException extends RuntimeException implements GameException {

    private final GameError error;

    public AssetException(GameError error, String detail) {
        super(error.message + ": " + detail);
        this.error = error;
    }

    public AssetException(GameError error, String detail, Throwable cause) {
        super(error.message + ": " + detail, cause);
        this.error = error;
    }

    @Override
    public GameError getError() { return error; }

    public static class ResourceLoadException extends AssetException {
        public ResourceLoadException(String path) {
            super(GameError.RESOURCE_NOT_FOUND, path);
        }

        public ResourceLoadException(String path, IOException cause) {
            super(GameError.RESOURCE_NOT_FOUND, path, cause);
        }
    }

    public static class AudioException extends AssetException {
        public AudioException(String file) {
            super(GameError.AUDIO_LOAD_FAILED, file);
        }

        public AudioException(String file, IOException cause) {
            super(GameError.AUDIO_LOAD_FAILED, file, cause);
        }
    }

    public static class MapLoadException extends AssetException {
        public MapLoadException(String path) {
            super(GameError.MAP_NOT_FOUND, path);
        }

        public MapLoadException(String path, IOException cause) {
            super(GameError.MAP_NOT_FOUND, path, cause);
        }
    }
}
