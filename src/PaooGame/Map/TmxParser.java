package PaooGame.Map;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Base64;


public class TmxParser {
    /// facem asignarea gid index--clase
    /// sincer sa fiu le-am luat mai mult prin incercari sa nimeresc indecsii
    /// f mare discrepanta intre ce arata tiled si ce returneaza
    private static int mapGidToTileIndex(int gid)
    {
        // FLOOR
        if(gid == 100) return 0;   // floor
        if(gid == 86)  return 1;   // floorWireHorizontal
        if(gid == 99)  return 2;   // floorWireVertical
        if(gid == 87)  return 3;   // floorWireSW
        if(gid == 85)  return 4;   // floorWireSE
        if(gid == 116) return 5;  // floorWireNW
        if(gid == 113) return 6;  // floorWireNE


        // PERM BUTTON
        if(gid == 73) return 30;   // boxButtonWireTop
        if(gid == 58) return 31;   // boxButtonWireLeft
        if(gid == 57) return 32;   // boxButtonWireRight
        if(gid == 59) return 33;   // boxButtonWireBottom

        // TIMED BUTTON
        if(gid == 77) return 34;   // timedButtonWireTop
        if(gid == 62) return 35;   // timedButtonWireLeft
        if(gid == 61) return 36;   // timedButtonWireRight
        if(gid == 63) return 37;   // timedButtonWireBottom

        if(gid == 101) return 40;  // basicButtonWireTop
        if(gid == 114) return 41;  // basicButtonWireLeft
        if(gid == 115) return 42;  // basicButtonWireRight
        if(gid == 102) return 43;  // basicButtonWireBottom

        if(gid == 122) return 50;  // cheese
        if(gid == 121) return 51;  // box
        if(gid == 94)  return 52;  // ratT
        if(gid == 108) return 53;  // ratB
        if(gid == 93)  return 54;  // ratL
        if(gid == 107) return 55;  // ratR


        // WALL
        if(gid >= 1  && gid <= 11) return 10;
        if(gid >= 15 && gid <= 23) return 10;
        if(gid >= 29 && gid <= 39) return 10;
        if(gid >= 43 && gid <= 54) return 10;

        // DOOR
        if(gid == 88)  return 20;  // doorL
        if(gid == 89)  return 21;  // doorR
        if(gid == 117) return 22;  // doorB
        if(gid == 103) return 23;  // doorT
        if(gid == 65) return 24;  // doorNoKeyV
        if(gid == 79) return 25;  // doorNoKeyH

        // GID-uri nemapate → floor
        return 0;
    }


    public static GameMap loadMap(String mapPath)
    {
        try
        {
            File tmxFile = new File(mapPath);
            if(!tmxFile.exists())
            {
                System.out.println("[Playing] EROARE: TMX nu gasit: " + tmxFile.getAbsolutePath());
                return null;
            }
            GameMap map = new GameMap();
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder        = factory.newDocumentBuilder();
            Document doc                   = builder.parse(tmxFile);
            doc.getDocumentElement().normalize();

            Element mapElement = (Element) doc.getElementsByTagName("map").item(0);
            map.mapWidth  = Integer.parseInt(mapElement.getAttribute("width"));
            map.mapHeight = Integer.parseInt(mapElement.getAttribute("height"));
            System.out.println("[Playing] Harta: " + map.mapWidth + "x" + map.mapHeight);

            NodeList layers = doc.getElementsByTagName("layer");
            if(layers.getLength() == 0) { System.out.println("[Playing] EROARE: niciun layer!"); return null; }

            map.tileMapFloor = new int[map.mapHeight][map.mapWidth];
            map.tileMapAbove = new int[map.mapHeight][map.mapWidth];

            // Initializam cu -1 (gol)
            for(int[] r : map.tileMapFloor) java.util.Arrays.fill(r, -1);
            for(int[] r : map.tileMapAbove) java.util.Arrays.fill(r, -1);

            for(int l = 0; l < layers.getLength(); l++)
            {
                Element layer   = (Element) layers.item(l);
                String  name    = layer.getAttribute("name");
                Element dataEl  = (Element) layer.getElementsByTagName("data").item(0);
                String encoding = dataEl.getAttribute("encoding");
                String rawData  = dataEl.getTextContent().trim();

                System.out.println("[Playing] Layer: " + name + " | encoding: " + encoding);

                int[] indices;

                if(encoding.equals("csv"))
                {
                    String[] values = rawData.split(",");
                    indices = new int[values.length];
                    for(int i = 0; i < values.length; i++)
                        indices[i] = Integer.parseInt(values[i].trim());
                }
                else if(encoding.equals("base64"))
                {
                    byte[] decoded = Base64.getDecoder().decode(rawData);
                    ByteBuffer buf = ByteBuffer.wrap(decoded).order(ByteOrder.LITTLE_ENDIAN);
                    indices        = new int[decoded.length / 4];
                    for(int i = 0; i < indices.length; i++)
                        indices[i] = buf.getInt() & 0x1FFFFFFF;
                }
                else
                {
                    System.out.println("[Playing] Encoding necunoscut: " + encoding + " — sarit");
                    continue;
                }

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
                            map.tileMapFloor[row][col] = tileIdx;
                    }
            }

//            // tileMap ramane pentru compatibilitate cu isSolid()
//            map.tileMap = map.tileMapFloor;

            System.out.println("[Playing] Harta incarcata cu succes!");

            return map;
        }
        catch(Exception e)
        {
            System.out.println("[Playing] EROARE la parsarea TMX!");
            e.printStackTrace();
        }
        return null;
    }
}
