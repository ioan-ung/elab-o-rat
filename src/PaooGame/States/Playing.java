package PaooGame.States;

import PaooGame.Camera;
import PaooGame.Entity.Player;
import PaooGame.GameWindow.GameWindow;
import PaooGame.Input.KeyHandler;
import PaooGame.Tiles.Tile;
import java.awt.*;
import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Base64;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class Playing
{
    private static final int    TILE_SIZE = 32;
    private static final String MAP_PATH  = "maps/Tutorial.tmx";

    private int[][] tileMap;
    private int mapWidth;
    private int mapHeight;

    private Camera camera;

    private KeyHandler keyH;
    private Player player;

    //latimea mapei 64x32 lasa imposibil sa incadrez tileuri 32x32 pe canvas 800x600
    //camera momentan trebuie setata manual prin coordonate user
    //MODIFICA --cand faci deplasarea soarecelui
    private float playerX = 1024;
    private float playerY = 512;

//    // Zona stânga-sus
//    playerX = 200;  playerY = 200;
//
//// Zona centru-sus
//    playerX = 700;  playerY = 200;
//
//// Zona dreapta-sus
//    playerX = 1800; playerY = 200;
//
//// Zona stânga-mijloc
//    playerX = 200;  playerY = 500;
//
//// Zona centru
//    playerX = 1024; playerY = 512;
//
//// Zona dreapta-mijloc
//    playerX = 1800; playerY = 500;
//
//// Zona stânga-jos
//    playerX = 200;  playerY = 800;
//
//// Zona centru-jos
//    playerX = 1024; playerY = 800;
//
//// Zona dreapta-jos
//    playerX = 1800; playerY = 800;

    public Playing(GameWindow gameWindow)
    {
        /// Initialize player
        keyH = new KeyHandler();
        player = new Player(gameWindow.GetCanvas(), keyH);

        loadMap();
        /// Camera work
        camera = new Camera(
                gameWindow.GetWndWidth(), gameWindow.GetWndHeight(),
                mapWidth  * TILE_SIZE,
                mapHeight * TILE_SIZE
        );
        camera.centerOn(player.getX(), player.getY());
    }
    private boolean contains(int[] arr, int val)
    {
        for(int x : arr)
            if(x == val) return true;
        return false;
    }

    private int[][] tileMapFloor;   // layerul Floor + Background
    private int[][] tileMapAbove;   // layerul Above (branza, box, soarece)

    private void loadMap()
    {
        try
        {
            File tmxFile = new File(MAP_PATH);
            if(!tmxFile.exists())
            {
                System.out.println("[Playing] EROARE: TMX nu gasit: " + tmxFile.getAbsolutePath());
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder        = factory.newDocumentBuilder();
            Document doc                   = builder.parse(tmxFile);
            doc.getDocumentElement().normalize();

            Element mapElement = (Element) doc.getElementsByTagName("map").item(0);
            mapWidth  = Integer.parseInt(mapElement.getAttribute("width"));
            mapHeight = Integer.parseInt(mapElement.getAttribute("height"));
            System.out.println("[Playing] Harta: " + mapWidth + "x" + mapHeight);

            NodeList layers = doc.getElementsByTagName("layer");
            if(layers.getLength() == 0) { System.out.println("[Playing] EROARE: niciun layer!"); return; }

            tileMapFloor = new int[mapHeight][mapWidth];
            tileMapAbove = new int[mapHeight][mapWidth];

            // Initializam cu -1 (gol)
            for(int[] r : tileMapFloor) java.util.Arrays.fill(r, -1);
            for(int[] r : tileMapAbove) java.util.Arrays.fill(r, -1);

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

                for(int row = 0; row < mapHeight; row++)
                    for(int col = 0; col < mapWidth; col++)
                    {
                        int gid = indices[row * mapWidth + col];
                        if(gid <= 0) continue;

                        int tileIdx = mapGidToTileIndex(gid);

                        // Above = branza, obiecte deasupra playerului
                        if(name.equals("Above"))
                            tileMapAbove[row][col] = tileIdx;
                        else  // Floor + Background
                            tileMapFloor[row][col] = tileIdx;
                    }
            }

            // tileMap ramane pentru compatibilitate cu isSolid()
            tileMap = tileMapFloor;

            System.out.println("[Playing] Harta incarcata cu succes!");
        }
        catch(Exception e)
        {
            System.out.println("[Playing] EROARE la parsarea TMX!");
            e.printStackTrace();
        }
    }

    private void drawLayer(Graphics g, int[][] map, int camX, int camY, int wndWidth, int wndHeight)
    {
        if(map == null) return;

        int startCol = Math.max(0, camX / TILE_SIZE);
        int startRow = Math.max(0, camY / TILE_SIZE);
        int endCol   = Math.min(startCol + wndWidth  / TILE_SIZE + 2, mapWidth);
        int endRow   = Math.min(startRow + wndHeight / TILE_SIZE + 2, mapHeight);

        for(int row = startRow; row < endRow; row++)
        {
            for(int col = startCol; col < endCol; col++)
            {
                int tileIdx = map[row][col];
                if(tileIdx < 0) continue;

                Tile tile = Tile.tiles[tileIdx];
                if(tile == null) continue;

                int drawX = col * TILE_SIZE - camX;
                int drawY = row * TILE_SIZE - camY;

                tile.Draw(g, drawX, drawY);
            }
        }
    }

/// facem asignarea gid index--clase
/// sincer sa fiu le-am luat mai mult prin incercari sa nimeresc indecsii
/// f mare discrepanta intre ce arata tiled si ce returneaza
    private int mapGidToTileIndex(int gid)
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

    public void Draw(Graphics g, int wndWidth, int wndHeight)
    {
        if(tileMapFloor == null) return;

        int camX = camera.getXOffset();
        int camY = camera.getYOffset();

        // 1. Desenam floor + background
        drawLayer(g, tileMapFloor, camX, camY, wndWidth, wndHeight);

        // 2. Desenam playerul aici
        Graphics2D g2 = (Graphics2D)g;
        player.draw(g2);
        g2.dispose();

        // 3. Desenam obiectele de deasupra (branza etc.)
        drawLayer(g, tileMapAbove, camX, camY, wndWidth, wndHeight);
    }


    public void Update()
    {
        player.update();
        camera.centerOn(player.getX(), player.getY());
    }

    public void setPlayerPosition(float x, float y)
    {
        playerX = x;
        playerY = y;
    }

    public Camera getCamera() { return camera; }

}