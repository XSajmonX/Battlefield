import java.util.List;
import java.util.Scanner;
/**
 * klasa bramy
 */
public class Brama extends Rycerz{
    public Brama(){
        this.hp=1000;
        this.atak = 100;
    }
    public Brama(int hp,int atak, String strona){
        this.strona=strona;
        this.hp=hp;
        this.atak = atak;
    }
    public void hpSet(int hp)
    {
        this.hp=hp;
    }

    /**
     * atak bramy
     * @param opp lista przeciwnika
     * @param plansza plansza
     * @param x2 współrzędne przeciwnika
     * @param y2 współrzędne przeciwnika
     */
    void atak(List opp, Plansza plansza, int x2, int y2){
        //wyszukanie przeciwnika opp - lista przeciwnika
        for (Object l1 : opp) {
            Rycerz r = Rycerz.class.cast(l1);
            if (r.getX() == x2 && r.getY() == y2) {

                if(r.getNazwa().substring(0, 1).equals("E"))//sprawdzanie czy przeciwnik jest elitarny
                {
                    Rycerz_elitarny e = Rycerz_elitarny.class.cast(l1);
                    e.setHP(e.getHP() - this.atak + e.getTarcza());
                    //wyświetlanie hp przeciwnika i czy zginą
                    if(strona.equals("czerwona")) {
                        System.out.println("\u001B[31mBrama czerwona \u001B[0m"+"atakuje " +"\u001B[34m"+e.getNazwa()+ "\nHp niebieskiego: \u001B[0m" + e.getHP());
                    }else if(strona.equals("niebieska"))
                    {
                        System.out.println("\u001B[34mBrama niebieska \u001B[0m"+"atakuje " +"\u001B[31m"+e.getNazwa()+ "\nHp czerwonego: \u001B[0m" + e.getHP());
                    }
                    if (e.getHP() <= 0) {
                        if(strona.equals("czerwona")) System.out.print("\u001B[34m");
                        else System.out.print("\u001B[31m");
                        System.out.println(e.getNazwa()+"\u001B[0m"+" umiera z kretesem");
                        System.out.println("----------------------");
                        opp.remove(opp.indexOf(l1)); // usunięcie przeciwnika z listy
                        plansza.setT(x2, y2, " *** ");
                        break;
                    }
                    System.out.println("----------------------");
                }
                else
                {
                    r.setHP(r.getHP() - this.atak); //atak dla innych niż elitarny
                    if(strona.equals("czerwona")) {
                        System.out.println("\u001B[31mBrama czerwona \u001B[0m"+"atakuje " +"\u001B[34m"+r.getNazwa()+ "\nHp niebieskiego: \u001B[0m" + r.getHP());
                    }
                    else {
                        System.out.println("\u001B[34mBrama niebieska \u001B[0m"+"atakuje " +"\u001B[31m"+r.getNazwa()+ "\nHp czerwonego: \u001B[0m" + r.getHP());
                    }
                    if (r.getHP() <= 0) {
                        if(strona.equals("czerwona")) System.out.print("\u001B[34m");
                        else System.out.print("\u001B[31m");
                        System.out.println(r.getNazwa()+"\u001B[0m"+" umiera z kretesem");
                        System.out.println("----------------------");
                        opp.remove(opp.indexOf(l1)); //usunięcie przeciwnika z listy
                        plansza.setT(x2, y2, " *** ");
                        break;
                    }
                    System.out.println("----------------------");
                }

            }
        }



    }

}


