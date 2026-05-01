package PaooGame.Map;

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
        // Check ranges first for walls (since switches don't do >= well)
        if ((gid >= 1 && gid <= 11) || (gid >= 15 && gid <= 23) ||
                (gid >= 29 && gid <= 39) || (gid >= 43 && gid <= 54)) {
            return 10; // WALL
        }

        // Exact matches
        switch (gid) {
            // FLOOR
            case 100 : return 0;
            case 86 : return 1;
            case 99 : return 2;
            case 87 : return 3;
            case 85 : return 4;
            case 116 : return 5;
            case 113 : return 6;

            // PERM BUTTON
            case 73 : return 30;
            case 58 : return 31;
            case 57 : return 32;
            case 59 : return 33;

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

            // ENTITIES / ITEMS
            case 122 : return 50; // cheese
            case 121 : return 51; // box

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
                String[] properties = new String[propertiesList.getLength()];

                for (int p = 0; p < propertiesList.getLength(); p++) {
                    Element propElement = (Element) propertiesList.item(p);
                    String propValue = propElement.getAttribute("value");
                    properties[p] = propValue;
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
        Document doc = null;
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
            e.printStackTrace();
            return null;
        }
        Element mapElement = (Element) doc.getElementsByTagName("map").item(0);
        map.mapWidth  = Integer.parseInt(mapElement.getAttribute("width"));
        map.mapHeight = Integer.parseInt(mapElement.getAttribute("height"));
        System.out.println("[Playing] Harta: " + map.mapWidth + "x" + map.mapHeight);

        NodeList layers = doc.getElementsByTagName("layer");
        if(layers.getLength() == 0) { System.out.println("[Playing] EROARE: niciun layer!"); return null; }

        map.tileMap = new int[map.mapHeight][map.mapWidth];
        map.tileMapAbove = new int[map.mapHeight][map.mapWidth];

        // Initializam cu -1 (gol)
        for(int[] r : map.tileMap) Arrays.fill(r, -1);
        for(int[] r : map.tileMapAbove) Arrays.fill(r, -1);

        for(int l = 0; l < layers.getLength(); l++)
        {
            Element layer   = (Element) layers.item(l);
            String  name    = layer.getAttribute("name");
            Element dataEl  = (Element) layer.getElementsByTagName("data").item(0);
            String encoding = dataEl.getAttribute("encoding");
            String rawData  = dataEl.getTextContent().trim();

            System.out.println("[Playing] Layer: " + name + " | encoding: " + encoding);

            int[] indices = decodeLayerData(encoding, rawData);
            if (indices == null) return null;

            for(int row = 0; row < map.mapHeight; row++)
                for(int col = 0; col < map.mapWidth; col++)
                {
                    int gid = indices[row * map.mapWidth + col];
                    if(gid <= 0) continue;

                    int tileIdx = mapGidToTileIndex(gid);

                    // Above = branza, obiecte deasupra playerului
                    if(name.equals("Above"))
                        map.tileMapAbove[row][col] = tileIdx;
                    else  // Floor + Background
                        map.tileMap[row][col] = tileIdx;
                }
        }
        map.gameObjects = parseObjects(doc).toArray(new GameObject[0]);
        System.out.println("[Playing] Harta incarcata cu succes!");
        return map;
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
