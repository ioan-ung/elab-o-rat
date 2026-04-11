package PaooGame.States;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

/*! \class Playing
    \brief Incarca harta TMX din Tiled si spritesheet-ul asociat.
           Deseneaza tile-urile pe ecran folosind o matrice de indici.
 */
public class Playing
{
    /*! Dimensiunea unui tile in pixeli (in spritesheet si pe harta) */
    private static final int TILE_SIZE = 16;

    /*! Calea catre fisierul TMX exportat din Tiled */
    private static final String MAP_PATH        = "materials/map.tmx";

    /*! Calea catre spritesheet-ul de tile-uri */
    private static final String SPRITESHEET_PATH = "materials/spritesheet.png";

    /*! Matricea de indici de tile-uri incarcata din TMX */
    private int[][] tileMap;

    /*! Latimea hartii in tile-uri */
    private int mapWidth;

    /*! Inaltimea hartii in tile-uri */
    private int mapHeight;

    /*! Spritesheet-ul incarcat in memorie */
    private BufferedImage spritesheet;

    /*! Array de tile-uri decupate din spritesheet */
    private BufferedImage[] tiles;

    /*! Numarul de coloane din spritesheet */
    private int spritesheetCols;

    public Playing()
    {
        loadSpritesheet();
        loadMap();
    }

    /*! \fn private void loadSpritesheet()
        \brief Incarca spritesheet-ul si decupeaza fiecare tile intr-un array.
     */
    private void loadSpritesheet()
    {
        try
        {
            spritesheet = ImageIO.read(new File(SPRITESHEET_PATH));
            System.out.println("[Playing] Spritesheet incarcat: "
                + spritesheet.getWidth() + "x" + spritesheet.getHeight());

            spritesheetCols = spritesheet.getWidth()  / TILE_SIZE;
            int spritesheetRows = spritesheet.getHeight() / TILE_SIZE;
            int totalTiles = spritesheetCols * spritesheetRows;

            tiles = new BufferedImage[totalTiles];

            for(int row = 0; row < spritesheetRows; row++)
            {
                for(int col = 0; col < spritesheetCols; col++)
                {
                    int idx = row * spritesheetCols + col;
                    tiles[idx] = spritesheet.getSubimage(
                        col * TILE_SIZE,
                        row * TILE_SIZE,
                        TILE_SIZE,
                        TILE_SIZE
                    );
                }
            }

            System.out.println("[Playing] Tile-uri decupate: " + totalTiles
                + " (" + spritesheetCols + " col x " + spritesheetRows + " rows)");
        }
        catch(IOException e)
        {
            System.out.println("[Playing] EROARE: spritesheet-ul nu a fost gasit!");
            e.printStackTrace();
        }
    }

    /*! \fn private void loadMap()
        \brief Parseaza fisierul TMX (XML) si extrage matricea de tile-uri
               din primul layer de tip <layer>.
     */
    private void loadMap()
    {
        try
        {
            File tmxFile = new File(MAP_PATH);
            if(!tmxFile.exists())
            {
                System.out.println("[Playing] EROARE: fisierul TMX nu a fost gasit: "
                    + tmxFile.getAbsolutePath());
                return;
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(tmxFile);
            doc.getDocumentElement().normalize();

            // --- Citim dimensiunile hartii din tag-ul <map> ---
            Element mapElement = (Element) doc.getElementsByTagName("map").item(0);
            mapWidth  = Integer.parseInt(mapElement.getAttribute("width"));
            mapHeight = Integer.parseInt(mapElement.getAttribute("height"));
            System.out.println("[Playing] Dimensiuni harta: " + mapWidth + "x" + mapHeight + " tile-uri");

            // --- Citim primul layer <layer> ---
            NodeList layers = doc.getElementsByTagName("layer");
            if(layers.getLength() == 0)
            {
                System.out.println("[Playing] EROARE: niciun layer gasit in TMX!");
                return;
            }

            Element layer = (Element) layers.item(0);
            System.out.println("[Playing] Layer incarcat: " + layer.getAttribute("name"));

            // --- Citim tag-ul <data> care contine CSV-ul cu indici ---
            Element dataElement = (Element) layer.getElementsByTagName("data").item(0);
            String encoding = dataElement.getAttribute("encoding");

            if(!encoding.equals("csv"))
            {
                System.out.println("[Playing] ATENTIE: encoding-ul nu e CSV! ("
                    + encoding + "). Seteaza encoding=csv in Tiled.");
                return;
            }

            String csvData = dataElement.getTextContent().trim();
            String[] values = csvData.split(",");

            // --- Populam matricea tileMap ---
            tileMap = new int[mapHeight][mapWidth];
            for(int row = 0; row < mapHeight; row++)
            {
                for(int col = 0; col < mapWidth; col++)
                {
                    int linearIdx = row * mapWidth + col;
                    // Tiled foloseste indici 1-based; 0 = tile gol
                    int tileId = Integer.parseInt(values[linearIdx].trim());
                    tileMap[row][col] = tileId - 1; // convertim la 0-based pentru array-ul tiles[]
                }
            }

            System.out.println("[Playing] Matricea TMX incarcata cu succes!");
        }
        catch(Exception e)
        {
            System.out.println("[Playing] EROARE la parsarea TMX!");
            e.printStackTrace();
        }
    }

    /*! \fn public void Draw(Graphics g, int wndWidth, int wndHeight)
        \brief Deseneaza harta tile cu tile pe ecran.
               Scaleaza fiecare tile la dimensiunea ferestrei.
     */
    public void Draw(Graphics g, int wndWidth, int wndHeight)
    {
        if(tileMap == null || tiles == null) return;

        // Dimensiunea unui tile scalata la fereastra
        int tileW = wndWidth  / mapWidth;
        int tileH = wndHeight / mapHeight;

        for(int row = 0; row < mapHeight; row++)
        {
            for(int col = 0; col < mapWidth; col++)
            {
                int tileId = tileMap[row][col];

                // tileId < 0 = tile gol (era 0 in TMX)
                if(tileId < 0 || tileId >= tiles.length) continue;

                int drawX = col * tileW;
                int drawY = row * tileH;

                g.drawImage(tiles[tileId], drawX, drawY, tileW, tileH, null);
            }
        }
    }

    /*! \fn public void Update()
        \brief Metoda de update - deocamdata goala, pentru logica viitoare.
     */
    public void Update()
    {
        // TODO: logica joc (miscare player, coliziuni etc.)
    }

    // --- Getteri utili ---

    public int[][] getTileMap()   { return tileMap;   }
    public int     getMapWidth()  { return mapWidth;   }
    public int     getMapHeight() { return mapHeight;  }
    public int     getTileSize()  { return TILE_SIZE;  }
    public BufferedImage[] getTiles() { return tiles;  }
}
