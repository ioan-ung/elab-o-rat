package PaooGame.Tiles;

import PaooGame.Graphics.Assets;

/*! \class public class CheeseTile extends Tile
    \brief Abstractizeaza notiunea de dala de tip branza.
           Branza poate fi colectata de player — nu e solida.
           Odata colectata, devine invizibila (collected = true).
 */
public class CheeseTile extends Tile
{
    /*! Starea branzei: true = colectata, false = disponibila */
    private boolean collected;

    /*! \fn public CheeseTile(int id)
        \brief Constructorul de initializare al clasei.
               Branza porneste necolectata.

        \param id Id-ul dalei util in desenarea hartii.
     */
    public CheeseTile(int id)
    {
        super(Assets.cheese, id);
        collected = false;
    }

    /*! \fn public boolean IsSolid()
        \brief Branza nu este solida — playerul poate trece peste ea.
     */
    @Override
    public boolean IsSolid()
    {
        return false;
    }

    /*! \fn public void collect()
        \brief Marcheaza branza ca fiind colectata.
     */
    public void collect()
    {
        collected = true;
    }

    /*! \fn public boolean isCollected()
        \brief Returneaza daca branza a fost colectata.
     */
    public boolean isCollected()
    {
        return collected;
    }
}