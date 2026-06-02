package PaooGame.Exceptions;

import java.sql.SQLException;

public class DatabaseException extends RuntimeException implements GameException {

    private final GameError error;

    public DatabaseException(GameError error) {
        super(error.message);
        this.error = error;
    }

    public DatabaseException(GameError error, SQLException cause) {
        super(error.message, cause);
        this.error = error;
    }

    @Override
    public GameError getError() { return error; }
}
