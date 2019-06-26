package tama;

/**
 * Modélise un coeur de tamagoshi.
 * Un coeur est un organe de durée de vie maximale définie lors de sa création.
 * À chaque tour de jeu, le coeur puise une certaine quantité d'énergie, dans
 *  son stock d'énergie, pour vivre.
 * S'il n'y a pas assez d'énergie dans le stock d'énergie, la durée de vie
 *  effective du coeur diminue, de sorte qu'il risque de s'arrêter de battre
 *  avant que son âge ait atteint la durée de vie maximale prévue.
 * Enfin, il peut se produire un accident dans la vie d'un coeur : il peut
 *  s'arrèter de battre prématurément.
 * @inv <pre>
 *     describe() != null
 *     lifeTime() > 0
 *     maxLifeTime() >= lifeTime() >= age() >= 0
 *     energyStock() != null
 *     (age() == lifeTime()) ==> !isBeating() </pre>
 * @cons <pre>
 *     $DESC$
 *         Un coeur qui bat.
 *         La durée de vie maximale de ce coeur est fixée par le paramètre max.
 *         La réserve d'énergie est fixée par le paramètre nrj.
 *     $ARGS$ int max, IEnergyStock nrj
 *     $PRE$
 *         max > 0
 *         nrj != null
 *     $POST$
 *         age() == 0
 *         maxLifeTime() == max
 *         lifeTime() == maxLifeTime()
 *         energyStock() == nrj
 *         isBeating() </pre>
 */
public interface IHeart {
    // ATTRIBUTS
    /**
     * Quantité d'énergie absorbée par un coeur lors d'un tour de jeu.
     */
    int NRJ = 3;
    
    // REQUETES
    /**
     * L'âge courant de ce coeur.
     */
    int age();
    /**
     * Décrit ce coeur à l'aide d'une courte chaîne de caractères, du genre
     *  "heart: x/y/z/v" où x représente l'âge, y la durée de vie effective, z
     *  la durée de vie maximale de ce coeur, et v l'indication "vivant" ou
     *  "mort".
     */
    String describe();
    /**
     * La durée de vie effective de ce coeur.
     * Cette valeur, initialement égale à la durée de vie maximale, va diminuer
     *  si le coeur manque d'énergie pour vivre.
     */
    int lifeTime();
    /**
     * Le réservoir d'énergie auquel ce coeur est rattaché.
     */
    IEnergyStock energyStock();
    /**
     * La durée de vie maximale de ce coeur.
     */
    int maxLifeTime();
    /**
     * Indique si ce coeur bat.
     */
    boolean isBeating();

    // COMMANDES
    /**
     * Fait vivre ce coeur un tour de jeu.
     * Si le coeur atteint son âge limite, il s'arrête de battre.
     * @pre <pre>
     *     isBeating() </pre>
     * @post <pre>
     *     Soit level ::= old energyStock().crtLevel()
     *     Soit delta ::= old maxLifeTime() - lifeTime()
     *     age() == old age() + 1
     *     !isBeating() == (age() == lifeTime())
     *     level < NRJ
     *         ==> energyStock().crtLevel() == 0
     *             lifeTime() == max(old lifeTime() - (NRJ - level), age())
     *     NRJ <= level < NRJ + delta
     *         ==> energyStock().crtLevel() == 0
     *             lifeTime() == old lifeTime() + level - NRJ
     *     NRJ + delta <= level
     *         ==> energyStock().crtLevel() == level - NRJ - delta
     *             lifeTime() == maxLifeTime() </pre>
     */
    void spendLife();
    /**
     * Arrête les battements de ce coeur.
     * @post <pre>
     *     !isBeating() </pre>
     */
    void stopBeating();
}
