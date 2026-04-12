package PaooGame.States;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Base64;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

public class Playing
{
    private static final int    TILE_SIZE = 32;
    private static final String MAP_PATH  = "maps/Tutorial_Prototype.tmx";
    private static final String SHEET_PATH = "maps/SpreetSheet.png";

    private int[][] tileMap;
    private int mapWidth;
    private int mapHeight;
    private BufferedImage[] tiles;
    private int spritesheetCols;

    public Playing()
    {
        loadSpritesheet();
        loadMap();
    }

    private void loadSpritesheet()
    {
        try
        {
            BufferedImage sheet = ImageIO.read(new File(SHEET_PATH));
            spritesheetCols     = sheet.getWidth() / TILE_SIZE;
            int rows            = sheet.getHeight() / TILE_SIZE;
            tiles               = new BufferedImage[spritesheetCols * rows];

            for(int r = 0; r < rows; r++)
                for(int c = 0; c < spritesheetCols; c++)
                    tiles[r * spritesheetCols + c] = sheet.getSubimage(
                            c * TILE_SIZE, r * TILE_SIZE, TILE_SIZE, TILE_SIZE);

            System.out.println("[Playing] Spritesheet: " + tiles.length + " tile-uri");
        }
        catch(IOException e)
        {
            System.out.println("[Playing] EROARE: spritesheet nu gasit!");
            e.printStackTrace();
        }
    }

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

            Element mapEl = (Element) doc.getElementsByTagName("map").item(0);
            mapWidth      = Integer.parseInt(mapEl.getAttribute("width"));
            mapHeight     = Integer.parseInt(mapEl.getAttribute("height"));
            System.out.println("[Playing] Harta: " + mapWidth + "x" + mapHeight);

            NodeList layers = doc.getElementsByTagName("layer");
            if(layers.getLength() == 0) { System.out.println("[Playing] EROARE: niciun layer!"); return; }

            Element layer   = (Element) layers.item(0);
            Element dataEl  = (Element) layer.getElementsByTagName("data").item(0);
            String encoding = dataEl.getAttribute("encoding");
            String rawData  = dataEl.getTextContent().trim();

            int[] indices;

            if(encoding.equals("csv"))
            {
                String[] values = rawData.split(",");
                indices = new int[values.length];
                for(int i = 0; i < values.length; i++)
                    indices[i] = Integer.parseInt(values[i].trim()) & 0x1FFFFFFF;
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
                System.out.println("[Playing] Encoding necunoscut: " + encoding);
                return;
            }

            tileMap = new int[mapHeight][mapWidth];
            for(int row = 0; row < mapHeight; row++)
                for(int col = 0; col < mapWidth; col++)
                    tileMap[row][col] = indices[row * mapWidth + col];

            System.out.println("[Playing] Harta incarcata cu succes!");
        }
        catch(Exception e)
        {
            System.out.println("[Playing] EROARE la parsarea TMX!");
            e.printStackTrace();
        }
    }

    public void Draw(Graphics g, int wndWidth, int wndHeight)
    {
        if(tileMap == null || tiles == null) return;

        int tileW = wndWidth  / mapWidth;
        int tileH = wndHeight / mapHeight;

        for(int row = 0; row < mapHeight; row++)
        {
            for(int col = 0; col < mapWidth; col++)
            {
                int gid = tileMap[row][col];
                if(gid <= 0) continue;

                int idx = gid - 1;  // GID 1-based → index 0-based
                if(idx >= tiles.length) continue;

                g.drawImage(tiles[idx], col * tileW, row * tileH, tileW, tileH, null);
            }
        }
    }

    public void Update() {}

    public int[][] getTileMap()   { return tileMap;  }
    public int     getMapWidth()  { return mapWidth; }
    public int     getMapHeight() { return mapHeight;}
    public int     getTileSize()  { return TILE_SIZE;}
}