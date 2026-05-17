package PaooGame.Map;

import PaooGame.GameObjects.Entities.Entity;
import PaooGame.GameObjects.GameObject;
import PaooGame.Levels.LevelManager;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.awt.*;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;


public class TmxParser {
    /// facem asignarea gid index--clase
    /// sincer sa fiu le-am luat mai mult prin incercari sa nimeresc indecsii
    /// f mare discrepanta intre ce arata tiled si ce returneaza
    private static int mapGidToTileIndex(int gid) {
        // Exact matches
        switch (gid) {
            // WALL
            case 1 : return 40;
            case 2 : return 41;
            case 3 : return 42;
            case 4 : return 43;
            case 5 : return 44;
            case 6 : return 45;
            case 7 : return 46;
            case 8 : return 47;
            case 9 : return 48;
            case 10 : return 49;
            case 11 : return 50;

            case 15 : return 51;
            case 16 : return 52;
            case 17 : return 53;
            case 18 : return 54;
            case 19 : return 55;
            case 20 : return 56;
            case 21 : return 57;
            case 22 : return 58;
            case 23 : return 59;
            case 24 : return 60;
            case 25 : return 61;

            case 29 : return 62;
            case 30 : return 63;
            case 31 : return 64;
            case 32 : return 65;
            case 33 : return 66;
            case 34 : return 67;
            case 35 : return 68;
            case 36 : return 69;
            case 37 : return 70;
            case 38 : return 71;
            case 39 : return 72;

            case 43 : return 73;
            case 44 : return 74;
            case 45 : return 75;
            case 46 : return 76;
            case 47 : return 77;
            case 48 : return 78;
            case 49 : return 79;
            case 50 : return 80;
            case 51 : return 81;
            case 52 : return 82;
            case 53 : return 83;
            case 54 : return 84;


            // FLOOR
            case 100 : return 0;
            case 86 : return 1;
            case 99 : return 2;
            case 87 : return 3;
            case 85 : return 4;
            case 116 : return 5;
            case 113 : return 6;

            // DOOR
            case 88 : return 20;
            case 89 : return 21;
            case 117 : return 22;
            case 103 : return 23;
            case 65 : return 24;
            case 79 : return 25;
            case 91 : return 26;
            case 92 : return 27;
            case 106 : return 28;
            case 120 : return 29;


            default : return 0; // GID-uri nemapate → floor
        }
    }

    private static ArrayList<GameObject> parseObjects(Document doc) {
        ArrayList<GameObject> gameObjects = new ArrayList<>();

        // Grab all <objectgroup> tags directly
        NodeList objectGroups = doc.getElementsByTagName("objectgroup");

        for (int i = 0; i < objectGroups.getLength(); i++) {
            Element groupElement = (Element) objectGroups.item(i);

            // Grab all <object> tags inside this specific group
            NodeList objects = groupElement.getElementsByTagName("object");

            for (int j = 0; j < objects.getLength(); j++) {
                Element objElement = (Element) objects.item(j);

                // Extract object
                String type = objElement.getAttribute("type");
                if (type.isEmpty()) type = objElement.getAttribute("class");

                // Extract properties
                NodeList propertiesList = objElement.getElementsByTagName("property");
                Object[] properties = new Object[propertiesList.getLength()];

                for (int p = 0; p < propertiesList.getLength(); p++) {
                    Element propElement = (Element) propertiesList.item(p);

                    // Check if the property is a list, otherwise assume it's an integer
                    if ("list".equals(propElement.getAttribute("type"))) {
                        ArrayList<Integer> listItems = new ArrayList<>();   // Assuming it's a list of integers
                        NodeList items = propElement.getElementsByTagName("item");
                        // Get every item of the list
                        for (int k = 0; k < items.getLength(); k++) {
                            Element itemElement = (Element) items.item(k);
                            listItems.add(Integer.parseInt(itemElement.getAttribute("value")));
                        }
                        properties[p] = listItems;  // Put the list as a property
                    } else properties[p] = Integer.parseInt(propElement.getAttribute("value"));
                }

                // Create and add the object
                GameObject newObject = LevelManager.createObject(
                        type,
                        Integer.parseInt(objElement.getAttribute("x")),
                        Integer.parseInt(objElement.getAttribute("y")),
                        properties
                        );

                if (newObject != null) {
                    gameObjects.add(newObject);
                }
            }
        }
        return gameObjects;
    }

    public static GameMap getMap(String mapPath)
    {
        GameMap map = new GameMap();    // The map that will get returned
        Document doc;
        try (InputStream is = TmxParser.class.getResourceAsStream(mapPath)) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder        = factory.newDocumentBuilder();
            // TMX Input Stream
            if (is == null) {
                System.out.println("[Playing] EROARE: TMX nu gasit: " + mapPath);
                return null;
            }
            doc = builder.parse(is);
            doc.getDocumentElement().normalize();
        } catch (Exception e) {
            System.out.println("[Playing] EROARE la parsarea TMX!");
            return null;
        }
        Element mapElement = (Element) doc.getElementsByTagName("map").item(0);
        map.mapWidth  = Integer.parseInt(mapElement.getAttribute("width"));
        map.mapHeight = Integer.parseInt(mapElement.getAttribute("height"));
        System.out.print("[Playing] Map: " + map.mapWidth + "x" + map.mapHeight + " | Layers: ");

        NodeList layers = doc.getElementsByTagName("layer");
        if(layers.getLength() == 0) { System.out.println("[Playing] EROARE: niciun layer!"); return null; }

        map.tileMap = new int[map.mapHeight][map.mapWidth];
        //map.tileMapAbove = new int[map.mapHeight][map.mapWidth]; /// Currently not using layer Above

        // Initializam cu -1 (gol)
        for(int[] r : map.tileMap) Arrays.fill(r, -1);
        //for(int[] r : map.tileMapAbove) Arrays.fill(r, -1); /// Currently not using layer Above

        String encoding = "";
        for(int l = 0; l < layers.getLength(); l++)
        {
            Element layer   = (Element) layers.item(l);
            String  name    = layer.getAttribute("name");

            if(name.equals("Above")) continue;  /// Currently not using layer Above

            Element dataEl  = (Element) layer.getElementsByTagName("data").item(0);
            encoding = dataEl.getAttribute("encoding");
            String rawData  = dataEl.getTextContent().trim();

            System.out.print(name + " ");

            int[] indices = decodeLayerData(encoding, rawData);
            if (indices == null) return null;

            for(int row = 0; row < map.mapHeight; row++)
                for(int col = 0; col < map.mapWidth; col++)
                {
                    int gid = indices[row * map.mapWidth + col];
                    if(gid <= 0) continue;

                    int tileIdx = mapGidToTileIndex(gid);

                    /// Currently not using layer Above
//                    if(name.equals("Above")) // Above
//                        map.tileMapAbove[row][col] = tileIdx;
//                    else  // Floor + Background
                        map.tileMap[row][col] = tileIdx;
                }
        }
        map.gameObjects = parseObjects(doc);
        map.gameEntities = getEntities(map.gameObjects);
        System.out.println("Objects | Encoding: " + encoding);
        return map;
    }

    private static ArrayList<Entity> getEntities(ArrayList<GameObject> gameObjects) {
        ArrayList<Entity> entities = new ArrayList<>();
        for (GameObject obj : gameObjects) {
            if (obj instanceof Entity)  entities.add((Entity) obj);
        }
        return entities;
    }

    private static int[] decodeLayerData(String encoding, String rawData) {
        if (encoding.equals("csv")) {
            String[] values = rawData.split(",");
            int[] indices = new int[values.length];
            for (int i = 0; i < values.length; i++) {
                indices[i] = Integer.parseInt(values[i].trim());
            }
            return indices;
        }
        else if (encoding.equals("base64")) {
            byte[] decoded = Base64.getDecoder().decode(rawData);
            ByteBuffer buf = ByteBuffer.wrap(decoded).order(ByteOrder.LITTLE_ENDIAN);
            int[] indices = new int[decoded.length / 4];
            for (int i = 0; i < indices.length; i++) {
                indices[i] = buf.getInt() & 0x1FFFFFFF;
            }
            return indices;
        }

        System.out.println("[Playing] Encoding necunoscut: " + encoding + " — sarit");
        return null;
    }
}
