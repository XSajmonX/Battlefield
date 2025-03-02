/**
 * klasa rycerza elitarnego - posiada tarczę, która zmniejsza otrzymywane obrażenia.
 */
public class Rycerz_elitarny extends Rycerz{
    public Rycerz_elitarny() {
        this.nazwa = "E";
        this.atak=320;
        this.hp = 1300;
        this.tarcza = 50;
        this.leczenie = 30;
        this.maxhp = 1300;
    }
    private int tarcza;
    public int getTarcza()
    {
        return this.tarcza;
    }

}
