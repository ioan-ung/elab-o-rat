package PaooGame.Exceptions;

public enum GameError {
    RESOURCE_NOT_FOUND  ("Resursa grafica nu a putut fi incarcata"),
    AUDIO_LOAD_FAILED   ("Fisier audio nu a putut fi incarcat"),
    MAP_NOT_FOUND       ("Harta nu a putut fi incarcata"),
    SPRITE_NOT_IN_CACHE ("Sprite negasit in cache"),
    DB_INIT_FAILED      ("Initializare baza de date esuata"),
    DB_OPERATION_FAILED ("Operatie SQL esuata");

    public final String message;

    GameError(String message) {
        this.message = message;
    }
}
