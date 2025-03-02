import java.io.PrintWriter;
import java.util.List;
/**
 * klasa armata - atak zadaje obrażenia centralne oraz obrażenia wokół centrum równe połowie ataku centralnego. Armata też posiada przeładowanie trwające 4 rundy
 */
public class Armata extends Kusznik{
    Armata(){
        this.nazwa = "A";
        this.hp = 500;
        this.atak=1000;
        this.zasięg = 4;
        this.przeładuj=4;
        this.leczenie = 15;
        this.maxhp = 500;
        this.przeładujL=0;
    }
    /**
     * tura armaty
     */
    public void tura(Plansza plansza, List red, List blue, Brama bc, Brama bn, PrintWriter zapis, int max_x) {
        // armata po oddaniu strzału przeładowuje przez następne rundy (nie może się poruszać i strzelać)
        if(przeładujL<przeładuj)
        {
            przeładujL++;
            return;
        }
        //sprawdza czy atak zostanie przeprowadzony
        //System.out.println(nazwa + " " + x + " " + y + " " + hp + " " + movement);
        String s1 = plansza.getT(this.x, this.y);
        String t1 = s1.substring(4, 5);
        String s4, t4;
        //turę dzedziczy po kuszniku
        //Przykładowo: s1 = A00c; t1 = c         nazwa i strona jednostki której jest tura
        String s2,t2,s3;
        // dla strony czerwonej
        if (t1.equals("c")) {
            //dojście jednostki do krańca planszy (górna krawdź), atak na bramę
            if (y < zasięg) {
                System.out.println("Walka " + "\u001B[31m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[34m" + "Bramą Niebieską" + "\u001B[0m");
                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z Bramą Niebieską");
                bn.hp-=this.atak;
                przeładujL=0;
                return;
            }
            //sprawdza czy w zasięgu armaty znajduje się przeciwnik
            for (int i = y-1; i >= y-zasięg; i--) {
                s2 = plansza.getT(x, i);
                t2 = s2.substring(4, 5);
                if(t2.equals("n")){
                    atak(blue,plansza,x,i,zapis);//atak
                    przeładujL=0;
                    break;
                }
            }
            //jeżeli w zasięgu nie ma przeciwnika i pole przed armatą jest puste, może ona się poruszyć
            s3 = plansza.getT(x,y-1);
            String t3 = s3.substring(4,5);
            if(!t3.equals("n") && s3.equals(" *** "))
            {
                plansza.setT(this.x, this.y - movement, s1);
                plansza.setT(this.x, this.y, " *** ");
                this.y = y - movement;
            }
            //widząc nieporuszającą się jednostkę z przodu rycerza, rycerz stara się poruszyć na bok
            else if(t1.equals(t3))
            {
                if(x!=0)
                {
                    String lewo = plansza.getT(this.x-1, this.y);
                    if(lewo == " *** ") {
                        //System.out.println(plansza.getT(this.x, this.y).substring(0, 4)+" porusza się w lewo");
                        plansza.setT(this.x - 1, this.y, s1);
                        plansza.setT(this.x, this.y, " *** ");
                        this.x = x - 1;
                    }
                }
                else if(x!=(max_x-1))
                {
                    String prawo = plansza.getT(this.x+1, this.y);
                    if(prawo == " *** ") {
                        System.out.println(plansza.getT(this.x, this.y).substring(0, 4) + " porusza się w prawo");
                        plansza.setT(this.x + 1, this.y, s1);
                        plansza.setT(this.x, this.y, " *** ");
                        this.x = x + 1;
                    }
                }
            }
        // dla strony niebieskiej
        } else if (t1.equals("n")){
            //dojście jednostki do krańca planszy (dolna krawdź), atak na bramę
            if (y > plansza.getY() -1 - zasięg) {
                System.out.println("Walka " + "\u001B[34m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[31m" + "Bramą Czrewoną"+"\u001B[0m");
                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z " + "Bramą Czrewoną");
                bc.hp-=this.atak;
                przeładujL=0;
                return;
            }
            //sprawdza czy w zasięgu armaty znajduje się przeciwnik
            for (int i = y+1; i <= y+zasięg; i++) {
                s2 = plansza.getT(x, i);
                t2 = s2.substring(4, 5);
                if(t2.equals("c")){
                    atak(red,plansza,x,i,zapis);//atak
                    przeładujL=0;
                    break;
                }
            }
            //jeżeli w zasięgu nie ma przeciwnika i pole przed armatą jest puste, może ona się poruszyć
            s3 = plansza.getT(x,y+1);
            String t3 = s3.substring(4,5);
            if(!t3.equals("c") && s3.equals(" *** "))
            {
                plansza.setT(this.x, this.y + movement, s1);
                plansza.setT(this.x, this.y, " *** ");
                this.y = y + movement;
            }
            //widząc nieporuszającą się jednostkę z przodu armaty, armata stara się poruszyć na bok
            else if(t1.equals(t3))
            {
                if(x!=0)
                {
                    String lewo = plansza.getT(this.x-1, this.y);
                    if(lewo == " *** ") {
                        //System.out.println(plansza.getT(this.x, this.y).substring(0, 4)+" porusza się w lewo");
                        plansza.setT(this.x - 1, this.y, s1);
                        plansza.setT(this.x, this.y, " *** ");
                        this.x = x - 1;
                    }
                }
                else if(x!=(max_x-1))
                {
                    String prawo = plansza.getT(this.x+1, this.y);
                    if(prawo == " *** ") {
                        System.out.println(plansza.getT(this.x, this.y).substring(0, 4) + " porusza się w prawo");
                        plansza.setT(this.x + 1, this.y, s1);
                        plansza.setT(this.x, this.y, " *** ");
                        this.x = x + 1;
                    }
                }
            }
        }

    }
    /**
     * atak armaty
     * @param opp lista przeciwnika
     * @param plansza plansza
     * @param x2 współrzędne przeciwnika
     * @param y2 współrzędne przeciwnika
     */
    protected void atak(List opp, Plansza plansza, int x2, int y2,PrintWriter zapis) {
        //wyszukanie przeciwnika opp - lista przeciwnika
        for(int i=-1;i<=1;i++) {
            for (int j = -1; j <= 1; j++) {
                for (Object l1 : opp) {
                    Rycerz r = Rycerz.class.cast(l1);
                    if (r.getX() == x2+i && r.getY() == y2+j) {

                        if (r.getNazwa().substring(0, 1).equals("E"))//sprawdzanie czy przeciwnik jest elitarny
                        {
                            //odejmowanie życia przeciwnikowi i przeciwnikom wokół niego
                            Rycerz_elitarny e = Rycerz_elitarny.class.cast(l1);
                            e.setHP(e.getHP() - (int)(((i==0 && j==0) ? 1 : 1.0/2)*this.atak) + e.getTarcza());
                            //wyświetlanie hp przeciwnika i czy zginą
                            if (strona.equals("czerwona")) {
                                System.out.println("Walka " + "\u001B[31m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[34m" + plansza.getT(x2+i, y2+j).substring(0, 4) + "\u001B[0m");
                                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z " + plansza.getT(x2+i, y2+j).substring(0, 4));
                                System.out.println("\u001B[34m" + "Hp niebieskiego: " + e.getHP() + "\u001B[0m");
                                zapis.println("Hp niebieskiego: " + e.getHP());
                            } else{
                                System.out.println("Walka " + "\u001B[34m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[31m" + plansza.getT(x2+i, y2+j).substring(0, 4) + "\u001B[0m");
                                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4)+ " z "+ plansza.getT(x2+i, y2+j).substring(0, 4));
                                System.out.println("\u001B[31m" + "Hp czerwonego: " + e.getHP() + "\u001B[0m");
                                zapis.println("Hp czerwonego: " + e.getHP());
                            }
                            if (e.getHP() <= 0) {
                                if (strona.equals("czerwona")) System.out.print("\u001B[34m");
                                else System.out.print("\u001B[31m");
                                System.out.println(e.getNazwa() + "\u001B[0m" + " umiera z kretesem");
                                zapis.println(e.getNazwa() + " umiera z kretesem");
                                System.out.println("----------------------");
                                zapis.println("----------------------");
                                opp.remove(opp.indexOf(l1)); // usunięcie przeciwnika z listy
                                plansza.setT(x2+i, y2+j, " *** ");
                                break;
                            }
                            System.out.println("----------------------");
                            zapis.println("----------------------");
                        } else {
                            r.setHP(r.getHP() - (int)(((i==0 && j==0) ? 1 : 1.0/2)*this.atak)); //atak dla innych niż elitarny
                            if (strona.equals("czerwona")) {
                                System.out.println("Walka " + "\u001B[31m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[34m" + plansza.getT(x2+i, y2+j).substring(0, 4) + "\u001B[0m");
                                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z " + plansza.getT(x2+i, y2+j).substring(0, 4));
                                System.out.println("\u001B[34m" + "Hp niebieskiego: " + r.getHP() + "\u001B[0m");
                                zapis.println("Hp niebieskiego: " + r.getHP());
                            } else{
                                System.out.println("Walka " + "\u001B[34m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[31m" + plansza.getT(x2+i, y2+j).substring(0, 4) + "\u001B[0m");
                                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z " + plansza.getT(x2+i, y2+j).substring(0, 4));
                                System.out.println("\u001B[31m" + "Hp czerwonego: " + r.getHP() + "\u001B[0m");
                                zapis.println("Hp czerwonego: " + r.getHP());
                            }
                            if (r.getHP() <= 0) {
                                if (strona.equals("czerwona")) System.out.print("\u001B[34m");
                                else System.out.print("\u001B[31m");
                                System.out.println(r.getNazwa() + "\u001B[0m" + " umiera z kretesem");
                                zapis.println(r.getNazwa() + " umiera z kretesem");
                                System.out.println("----------------------");
                                zapis.println("----------------------");
                                opp.remove(opp.indexOf(l1)); //usunięcie przeciwnika z listy
                                plansza.setT(x2+i, y2+j, " *** ");
                                break;
                            }
                            System.out.println("----------------------");
                            zapis.println("----------------------");
                        }

                    }
                }

            }
        }


    }
}
