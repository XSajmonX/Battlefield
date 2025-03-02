import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * klasa ryczerz -jednostka podstawowa, która porusza się o jedno pole. Jego atak aktywuje się, gdy pole przed nim stoi przeciwnik.
 */
public class Rycerz {
    public Rycerz() {
        this.nazwa = "R";
        this.hp = 1000;
        this.atak = 400;
        this.movement = 1;
        this.x = 0;
        this.y = 0;
        this.strona = "strona";
        this.leczenie = 20;
        this.maxhp = 1000;
    }

    public String nazwa;
    public int hp;
    protected int atak;
    protected int movement;
    public int x;
    public int y;
    protected String strona;
    protected int leczenie;
    protected int maxhp;
    public int getmaxHP()
    {
        return this.maxhp;
    }
    public int getHP() {
        return this.hp;
    }

    public void setHP(int hp) {
        this.hp = hp;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }
    public String getNazwa() {
        return this.nazwa;
    }
    public int getLeczenie() {
        return this.leczenie;
    }

    /**
     * tura rycerza
     */

    public void tura(Plansza plansza, List red, List blue, Brama bc, Brama bn,PrintWriter zapis,int max_x){

        //Przykładowo: s1 = R00c; t1 = c         nazwa i strona jednostki której jest tura
        String s1 = plansza.getT(this.x, this.y);
        String t1 = s1.substring(4, 5);
        //System.out.println(nazwa + " " + x + " " + y + " " + hp + " " + movement);
        // dla strony czerwonej
        if (t1.equals("c")) {
            //dojście jednostki do krańca planszy (górna krawdź), atak na bramę
            if (y < movement) {
                System.out.println("Walka " + "\u001B[31m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[34m" + "Bramą Niebieską" + "\u001B[0m");
                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4)  + " z " + "Bramą Niebieską");
                bn.hp-=this.atak;
                System.out.println("\u001B[34mHp Bramy Niebieskiej: \u001B[0m"+bn.hp);
                zapis.println("Hp Bramy Niebieskiej: " + bn.hp);
                if(bn.hp<=0)
                {
                    System.out.println("\u001B[34mBrama Niebieska\u001B[0m upada z kretesem\n");
                    zapis.println("Brama niebieska upada z kretesem\n");
                    return;
                }
                System.out.println("----------------------");
                zapis.println("----------------------");
                return;
            }
            //sprawdzenie dla jednostki czerwonej co znajduje się pole przed nim    (s2 i t2)

            String s2 = plansza.getT(this.x, this.y - movement);
            String t2 = s2.substring(4, 5);
            String t3 = s2.substring(1,2);
            // dla pustego pola, poruszenie się w przód
            if (plansza.getT(this.x, this.y - movement) == " *** ") {
                plansza.setT(this.x, this.y - movement, s1);
                plansza.setT(this.x, this.y, " *** ");
                this.y = y - movement;
                //System.out.println(x+" "+y +" " + s1);
            // walka gdy t1 = c i t2 = n
            } else if (!t1.equals(t2)) {
                System.out.println("Walka " + "\u001B[31m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[34m" + plansza.getT(this.x, this.y - movement).substring(0, 4) + "\u001B[0m");
                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z " +  plansza.getT(this.x, this.y - movement).substring(0, 4));
                atak(blue,plansza,x, y-movement,zapis);  //walka
            }
            //widząc nieporuszającą się jednostkę z przodu rycerza, rycerz stara się poruszyć na bok
            else if(t1.equals(t2))
            {
                if(x!=0)
                {
                    String lewo = plansza.getT(this.x-1, this.y);
                    if(lewo == " *** ") {
                        //System.out.println(plansza.getT(this.x, this.y).substring(1, 5)+" porusza się w lewo");
                        plansza.setT(this.x - 1, this.y, s1);
                        plansza.setT(this.x, this.y, " *** ");
                        this.x = x - 1;
                    }
                }
                else if(x!=(max_x-1))
                {
                    String prawo = plansza.getT(this.x+1, this.y);
                    if(prawo == " *** ") {
                        //System.out.println(plansza.getT(this.x, this.y).substring(1, 5) + " porusza się w prawo");
                        plansza.setT(this.x + 1, this.y, s1);
                        plansza.setT(this.x, this.y, " *** ");
                        this.x = x + 1;
                    }
                }
            }
        // Dla strony niebieskiej ( kolejność ta sama dostosowana dla strony niebieskij)
        } else if (t1.equals("n")) {
            //dojście jednostki do krańca planszy (dolna krawdź), atak na bramę
            if (this.y >= plansza.getY() - movement) {
                System.out.println("Walka " + "\u001B[34m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[31m" + "Brama Czrewoną"+"\u001B[0m");
                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z Bramą Czerwoną");
                bc.hp-=this.atak;
                System.out.println("\u001B[31mHp Bramy Czerwonej: \u001B[0m"+bc.hp);
                zapis.println("Hp Bramy Czerwonej: " + bc.hp);
                if(bc.hp<=0)
                {
                    System.out.println("\u001B[31mBrama czerwona\u001B[0m upada z kretesem\n");
                    zapis.println("Brama Czerwona upada z kretesem\n");
                    return;
                }
                zapis.println("----------------------");
                System.out.println("----------------------");
                return;
            }
            //sprawdzenie dla jednostki niebieskiej co znajduje się pole przed nim    (s2 i t2)
            String s2 = plansza.getT(this.x, this.y + movement);
            String t2 = s2.substring(4, 5);
            String t3 = s2.substring(0,1);
            // dla pustego pola, poruszenie się w przód
            if (plansza.getT(this.x, this.y + movement) == " *** ") {
                plansza.setT(this.x, this.y + movement, s1);
                plansza.setT(this.x, this.y, " *** ");
                this.y = y + movement;
                // walka gdy t1 = n i t2 = c
            } else if (!t1.equals(t2)) {
                System.out.println("Walka " + "\u001B[34m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[31m" + plansza.getT(this.x, this.y + 1).substring(0, 4) + "\u001B[0m");
                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z " + plansza.getT(this.x, this.y + 1).substring(0, 4));
                atak(red,plansza,x, y+movement,zapis);  //walka
            }
            //widząc nieporuszającą się jednostkę z przodu rycerza, rycerz stara się poruszyć na bok
            else if(t1.equals(t2))
            {
                if(x!=0)
                {
                    String lewo = plansza.getT(this.x-1, this.y);
                    if(lewo == " *** ") {
                        //System.out.println(plansza.getT(this.x, this.y).substring(1, 5) + " porusza się w lewo");
                        plansza.setT(this.x - 1, this.y, s1);
                        plansza.setT(this.x, this.y, " *** ");
                        this.x = x - 1;
                    }
                }
                else if(x!=(max_x-1))
                {
                    String prawo = plansza.getT(this.x+1, this.y);
                    if(prawo == " *** ") {
                        //System.out.println(plansza.getT(this.x, this.y).substring(1, 5) + " porusza się w prawo");
                        plansza.setT(this.x + 1, this.y, s1);
                        plansza.setT(this.x, this.y, " *** ");
                        this.x = x + 1;
                    }
                }
            }
        }
    }

    /**
     * atak rycerza
     * @param opp lista przeciwnika
     * @param plansza plansza
     * @param x2 współrzędne przeciwnika
     * @param y2 współrzędne przeciwnika
     */
   protected void atak(List opp, Plansza plansza, int x2, int y2,PrintWriter zapis){
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
                        System.out.println("\u001B[34m" + "Hp niebieskiego: \u001B[0m" + e.getHP());
                        zapis.println("Hp niebieskiego: " + e.getHP());
                    }else
                    {
                        System.out.println("\u001B[31m" + "Hp czerwonego: \u001B[0m" + e.getHP());
                        zapis.println("Hp czerwonego: " + e.getHP());
                    }

                    if (e.getHP() <= 0) {
                        if(strona.equals("czerwona")) System.out.print("\u001B[34m");
                        else System.out.print("\u001B[31m");
                        System.out.println(e.getNazwa()+"\u001B[0m"+" umiera z kretesem");
                        zapis.println(e.getNazwa()+" umiera z kretesem");
                        System.out.println("----------------------");
                        zapis.println("----------------------");
                        opp.remove(opp.indexOf(l1)); // usunięcie przeciwnika z listy
                        plansza.setT(x2, y2, " *** ");
                        break;
                    }
                    //leczeczenie się gdy rycerz elitarny przetrwał zadany mu atak
                    else if(e.getHP()>0 && e.getHP()!=e.getmaxHP())
                    {
                        e.setHP(e.getHP()+e.getLeczenie());
                        //sprawdzanie czy po leczeniu rycerz elitarny ma większe hp niż jego max
                        if(e.getHP()>e.getmaxHP())
                        {
                            e.setHP(e.getmaxHP());
                        }
                    }
                    System.out.println("----------------------");
                    zapis.println("----------------------");
                }
                else
                {
                    r.setHP(r.getHP() - this.atak); //atak dla innych niż elitarny
                    if(strona.equals("czerwona")) {
                        System.out.println("\u001B[34m" + "Hp niebieskiego: \u001B[0m" + r.getHP());
                        zapis.println("Hp niebieskiego: "+ r.getHP());
                    }else {
                        System.out.println("\u001B[31m" + "Hp czerwonego: \u001B[0m" + r.getHP());
                        zapis.println("Hp czerwonego: " + r.getHP());
                    }
                    if (r.getHP() <= 0) {
                        if(strona.equals("czerwona")) System.out.print("\u001B[34m");
                        else System.out.print("\u001B[31m");
                        System.out.println(r.getNazwa()+"\u001B[0m"+" umiera z kretesem");
                        zapis.println(r.getNazwa()+" umiera z kretesem");
                        System.out.println("----------------------");
                        zapis.println("----------------------");
                        opp.remove(opp.indexOf(l1)); //usunięcie przeciwnika z listy
                        plansza.setT(x2, y2, " *** ");
                        break;
                    }
                    //leczeczenie się gdy rycerz przetrwał zadany mu atak
                    else if(r.getHP()>0 && r.getHP()!=r.getmaxHP())
                    {
                        r.setHP(r.getHP()+r.getLeczenie());
                        //sprawdzanie czy po leczeniu rycerz ma większe hp niż jego max
                        if(r.getHP()>r.getmaxHP())
                        {
                            r.setHP(r.getmaxHP());
                        }
                    }
                    System.out.println("----------------------");
                    zapis.println("----------------------");
                }
            }
        }
    }
}
