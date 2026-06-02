package PaooGame.Graphics;

public enum Sprite {
    FLOOR("floor"),
    FLOOR_WIRE_HORIZONTAL("floorWireHorizontal"),
    FLOOR_WIRE_VERTICAL("floorWireVertical"),
    FLOOR_WIRE_SW("floorWireSW"),
    FLOOR_WIRE_SE("floorWireSE"),
    FLOOR_WIRE_NW("floorWireNW"),
    FLOOR_WIRE_NE("floorWireNE"),

    WALL("wall"),

    DOOR_R("doorR"), DOOR_L("doorL"), DOOR_T("doorT"), DOOR_B("doorB"),
    DOOR_NO_KEY_V("doorNoKeyV"), DOOR_NO_KEY_H("doorNoKeyH"),
    DOOR_R_OPEN("doorROpen"), DOOR_L_OPEN("doorLOpen"),
    DOOR_T_OPEN("doorTOpen"), DOOR_B_OPEN("doorBOpen"),

    BOX("box"), CHEESE("cheese"), CAT("cat"),

    BOX_BUTTON_WIRE_TOP("boxButtonWireTop"), BOX_BUTTON_WIRE_LEFT("boxButtonWireLeft"),
    BOX_BUTTON_WIRE_RIGHT("boxButtonWireRight"), BOX_BUTTON_WIRE_BOTTOM("boxButtonWireBottom"),

    TIMED_BUTTON_WIRE_TOP("timedButtonWireTop"), TIMED_BUTTON_WIRE_LEFT("timedButtonWireLeft"),
    TIMED_BUTTON_WIRE_RIGHT("timedButtonWireRight"), TIMED_BUTTON_WIRE_BOTTOM("timedButtonWireBottom"),

    BASIC_BUTTON_WIRE_TOP("basicButtonWireTop"), BASIC_BUTTON_WIRE_LEFT("basicButtonWireLeft"),
    BASIC_BUTTON_WIRE_RIGHT("basicButtonWireRight"), BASIC_BUTTON_WIRE_BOTTOM("basicButtonWireBottom"),

    MOUSE_NORTH("mouseNorth"), MOUSE_EAST("mouseEast"),
    MOUSE_SOUTH("mouseSouth"), MOUSE_WEST("mouseWest"),
    MOUSE_NORTH_EAST("mouseNorthEast"), MOUSE_NORTH_WEST("mouseNorthWest"),
    MOUSE_SOUTH_EAST("mouseSouthEast"), MOUSE_SOUTH_WEST("mouseSouthWest");

    public final String key;

    Sprite(String key) {
        this.key = key;
    }
}
