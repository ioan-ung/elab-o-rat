package PaooGame.Exceptions;

public class GameLogicException extends RuntimeException implements GameException {

    private final GameError error;

    public GameLogicException(GameError error, String detail) {
        super(error.message + ": " + detail);
        this.error = error;
    }

    @Override
    public GameError getError() { return error; }

    public static class SpriteNotFoundException extends GameLogicException {
        public SpriteNotFoundException(String spriteName) {
            super(GameError.SPRITE_NOT_IN_CACHE, spriteName);
        }
    }
}
