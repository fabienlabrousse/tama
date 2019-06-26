package tama;
/**
 * Modélise un tamagoshi.
 * Un tamagoshi est un être virtuel muni d'un coeur et d'un estomac.
 * Bien entretenu, un tamagoshi vivra longtemps et en plein forme.
 * Un tamagoshi a un certain niveau d'énergie qui ne cesse de diminuer au
 *  fil du temps qui passe.
 * Pour empêcher que votre tamagoshi ne meure prématurément, vous devez le
 *  nourrir, ce qui fera remonter son niveau d'énergie.
 * Attention toutefois car les tamagoshis sont tellement gourmands qu'ils
 *  peuvent manger jusqu'à s'en faire péter le bide (et mourir ainsi
 *  prématurément).
 */
public interface ITamagoshi {
    // ATTRIBUTS
    /**
     * Quantité de nourriture minimale apportée par un plat.
     */
    int MIN = 2;
    /**
     * Quantité de nourriture maximale apportée par un plat.
     */
    int MAX = 5;

    // REQUETES
    /**
     * Décrit ce tamagoshi à l'aide d'une courte chaîne de caractères.
     */
    String describe();
    /**
     * Indique si le tamagoshi est bien vivant, c'est-à-dire si son coeur bat.
     */
    boolean isAlive();
    /**
     * Indique si le tamagoshi est affamé, c'est-à-dire si son stock d'énergie
     *  est vide.
     */
    boolean isStarved();
    /**
     * Indique si le tamagoshi a atteint sa durée de vie maximale.
     */
    boolean isVenerable();
    /**
     * Indique si l'estomac du tamagoshi a explosé.
     */
    boolean stomachHasExploded();
    /**
     * Indique si l'estomac du tamagoshi fonctionne.
     */
    boolean stomachIsWorking();

    // COMMANDES
    /**
     * Nourrit le tamagoshi.
     * @pre
     *     isAlive() && stomachIsWorking()
     *     mealNb > 0
     * @post
     *     La quantité de nourriture présente dans l'estomac du tamagoshi
     *      a augmenté de sum(i:[0..mealNb[, alea(MIN, MAX))
     *     Si l'estomac a explosé, le coeur du tamagoshi s'est arrêté de battre
     */
    void feed(int mealNb);
    /**
     * Fait vivre ce tamagoshi pendant un tour de jeu.
     * @pre
     *     isAlive()
     * @post
     *     Si l'estomac fonctionnait, il a vécu un tour de jeu
     *     Puis le coeur a vécu un tour de jeu
     */
    void spendLife();
}
