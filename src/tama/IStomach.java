package tama;
/**
 * Modélise un estomac de tamagoshi.
 * Un estomac est un organe que l'on peut remplir de nourriture, pour qu'il
 *  la digère, produisant ainsi de l'énergie qu'il emmagasine dans le réservoir
 *  d'énergie du corps auquel il est rattaché. 
 * La taille d'un estomac ainsi que le stock d'énergie qui lui est associé
 *  sont déterminés une fois pour toutes à sa création.
 * Chaque unité de nourriture absorbée par l'estomac produit
 *  <code>NRJ_GAIN</code> unités d'énergie, et l'estomac peut digérer à chaque
 *  tour de jeu une quantité de nourriture égale à <code>QTY_PER_TURN</code>.
 * Durant un tour de jeu, l'estomac commence par absorber une unité de
 *  nourriture pour pouvoir fonctionner puis convertit donc
 *  <code>QTY_PER_TURN - 1</code> unités de nourriture en énergie ; ensuite il
 *  stocke l'énergie ainsi produite dans le réservoir d'énergie.
 * Si la nourriture est en quantité suffisante, l'estomac va donc produire de
 *  l'énergie, mais s'il y a peu de nourriture, l'estomac produira peu, voire
 *  pas du tout, d'énergie.
 * À la limite, s'il n'y a pas de nourriture dans l'estomac, celui-ci va
 *  consommer de l'énergie sans en produire.
 * Pire ! S'il n'y a plus d'énergie dans le réservoir, l'estomac ne pourra pas
 *  en consommer et il ne pourra plus fonctionner, vouant le corps auquel il
 *  est intégré à une mort toute proche.
 * Enfin, si un estomac contient plus de nourriture qu'il ne peut en absorber
 *  il explose.
 * @inv <pre>
 *     describe() != null
 *     energyStock() != null
 *     foodQuantity() >= 0
 *     size() > 0
 *     isEmpty() == (foodQuantity() == 0)
 *     isExploded() == (foodQuantity() > size())
 *     isExploded() ==> !isWorking() </pre>
 * @cons <pre>
 *     $DESC$
 *         Un estomac vide de capacité c.
 *         La réserve d'énergie est donnée par stock
 *     $ARGS$ int c, IEnergyStock stock
 *     $PRE$
 *         c > 0
 *         stock != null
 *     $POST$
 *         size() == c
 *         energyStock() == stock
 *         foodQuantity() == 0
 *         isWorking() </pre>
 */
public interface IStomach {
    // ATTRIBUTS
    /**
     * Quantité d'énergie produite par la digestion d'une unité de nourriture.
     */
    int NRJ_GAIN = 2;
    /**
     * Quantité maximale de nourriture convertie en énergie par tour de jeu.
     */
    int QTY_PER_TURN = 4;

    // REQUETES
    /**
     * Décrit cet estomac à l'aide d'une courte chaîne de caractères, du genre
     *  "stomach: x/y/w" où x représente la quantité de nourriture, y la taille,
     *  et w l'indication "fonctionnel" ou "détruit".
     */
    String describe();
    /**
     * Le stock d'énergie associé à cet estomac.
     */
    IEnergyStock energyStock();
    /**
     * La quantité de nourriture que contient cet estomac.
     */
    int foodQuantity();
    /**
     * Indique si cet estomac est vide.
     */
    boolean isEmpty();
    /**
     * Indique si cet estomac a explosé.
     */
    boolean isExploded();
    /**
     * Indique si cet estomac fonctionne.
     */
    boolean isWorking();
    /**
     * La capacité de cet estomac.
     */
    int size();

    // COMMANDES
    /**
     * Remplit cet estomac de qty unités de nourriture.
     * @pre <pre>
     *     qty > 0
     *     isWorking() </pre>
     * @post <pre>
     *     foodQuantity() == old foodQuantity() + qty </pre>
     */
    void fill(int qty);
    /**
     * Fait vivre cet estomac pour un tour de jeu.
     * @pre <pre>
     *     isWorking() </pre>
     * @post <pre>
     *     Soit x ::= old foodQuantity()
     *     Soit y ::= old energyStock().crtLevel()
     *     x >= QTY_PER_TURN
     *         ==> foodQuantity() == x - QTY_PER_TURN
     *             energyStock().crtLevel() == y + (QTY_PER_TURN - 1) * NRJ_GAIN
     *     0 < x < QTY_PER_TURN
     *         ==> foodQuantity() == 0
     *             energyStock().crtLevel() == y + (x - 1) * NRJ_GAIN
     *     x == 0
     *         ==> energyStock().crtLevel() == max(0, y - NRJ_GAIN)
     *             isWorking() == (y >= NRJ_GAIN) </pre>
     */
    void spendLife();
}
