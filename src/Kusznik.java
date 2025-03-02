import java.io.PrintWriter;
import java.util.List;
/**
 * klasa kusznika - Po oddaniu strzału Kusznik musi przeładować swoją broń, co sprawia, że nie może się poruszać ani atakować.
 */
public class Kusznik extends Lucznik {
    Kusznik(){
        this.nazwa = "K";
        this.hp = 700;
        this.atak=640;
        this.zasięg = 4;
        this.przeładuj=1;
        this.przeładujL=0;
        this.leczenie = 25;
        this.maxhp = 700;

    }
    protected int przeładuj;
    protected int przeładujL;

    /**
     * tura kusznika
     */
   public void tura(Plansza plansza, List red, List blue, Brama bc, Brama bn, PrintWriter zapis, int max_x) {
        // kusznik po oddaniu strzału przeładowuje przez następną rundę (nie może się poruszać i strzelać)
        if(przeładujL<przeładuj)
        {
            przeładujL++;
            return;
        }

//Przykładowo: s1 = Ł00c; t1 = c         nazwa i strona jednostki której jest tura
        String s1 = plansza.getT(this.x, this.y);
        String t1 = s1.substring(4, 5);
        String s2,t2,s3,t3;
        // dla strony czerwonej
        if (t1.equals("c")) {
            //dojście jednostki do krańca planszy (górna krawdź), atak na bramę
            if (y < zasięg) {
                System.out.println("Walka " + "\u001B[31m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[34m" + "Brama Niebieską" + "\u001B[0m");
                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z " + "Bramą Niebieską");
                bn.hp-=this.atak;
                przeładujL=0;
                System.out.println("\u001B[34mHp Bramy Niebieskiej: \u001B[0m"+bn.hp);
                zapis.println("Hp Bramy Niebieskiej: " + bn.hp);
                if(bn.hp<=0)
                {
                    System.out.println("\u001B[34mBrama Niebieska\u001B[0m upada z kretesem\n");
                    zapis.println("Brama Niebieska upada z kretesem\n");
                    return;
                }
                System.out.println("----------------------");
                zapis.println("----------------------");
                return;
            }
            s3 = plansza.getT(x,y-1);
            t3 = s3.substring(4,5);
            //sprawdza czy w zasięgu łucznika znajduje się przeciwnik
            for (int i = y-1; i >= y-zasięg; i--) {
                s2 = plansza.getT(x, i);
                t2 = s2.substring(4, 5);
                if(t2.equals("n")){
                    System.out.println("Walka " + "\u001B[31m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[34m" + plansza.getT(this.x, i).substring(0, 4) + "\u001B[0m");
                    zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z " + plansza.getT(this.x, i).substring(0, 4));
                    atak(blue,plansza,x,i,zapis);//atak
                    przeładujL=0;
                    break;
                }
            }
            //jeżeli w zasięgu nie ma przeciwnika i pole przed łucznikiem jest puste, może on się poruszyć
            if(!t3.equals("n") && s3.equals(" *** "))
            {
                plansza.setT(this.x, this.y - movement, s1);
                plansza.setT(this.x, this.y, " *** ");
                this.y = y - movement;
            }
            //widząc nieporuszającą się jednostkę z przodu łucznika, łucznik stara się poruszyć na bok
            else if(t1.equals(t3))
            {
                if(x!=0)
                {
                    String lewo = plansza.getT(this.x-1, this.y);
                    if(lewo == " *** ") {
                        //System.out.println(plansza.getT(this.x, this.y).substring(0, 4) + " porusza się w lewo");
                        plansza.setT(this.x - 1, this.y, s1);
                        plansza.setT(this.x, this.y, " *** ");
                        this.x = x - 1;
                    }
                }
                else if(x!=(max_x-1))
                {
                    String prawo = plansza.getT(this.x+1, this.y);
                    if(prawo == " *** ") {
                        //System.out.println(plansza.getT(this.x, this.y).substring(0, 4) + " porusza się w prawo");
                        plansza.setT(this.x + 1, this.y, s1);
                        plansza.setT(this.x, this.y, " *** ");
                        this.x = x + 1;
                    }
                }
            }

        } else if (t1.equals("n")){
            //dojście jednostki do krańca planszy (dolna krawdź), atak na bramę
            if (y > plansza.getY() -1 - zasięg) {
                System.out.println("Walka " + "\u001B[34m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[31m" + "Bramą Czrewoną"+"\u001B[0m");
                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z " + "Bramą Czerwoną");
                bc.hp-=this.atak;
                przeładujL=0;
                System.out.println("\u001B[31mHp Bramy Czerwonej: \u001B[0m"+bc.hp);
                zapis.println("Hp Bramy Czerwonej: " + bc.hp);
                if(bc.hp<=0)
                {
                    System.out.println("\u001B[31mBrama Czerwona\u001B[0m upada z kretesem\n");
                    zapis.println("Brama Czerwona upada z kretesem\n");
                    return;
                }
                zapis.println("----------------------");
                System.out.println("----------------------");
                return;
            }
            s3 = plansza.getT(x,y+1);
            t3 = s3.substring(4,5);
            //sprawdza czy w zasięgu łucznika znajduje się przeciwnik
            for (int i = y+1; i <= y+zasięg; i++) {
                s2 = plansza.getT(x, i);
                t2 = s2.substring(4, 5);
                if(t2.equals("c")){

                    System.out.println("Walka " + "\u001B[34m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[31m" + plansza.getT(this.x, i).substring(0, 4) + "\u001B[0m");
                    zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z " + plansza.getT(this.x, i).substring(0, 4));
                    atak(red,plansza,x,i,zapis);//atak
                    przeładujL=0;
                    break;
                }
            }
            //jeżeli w zasięgu nie ma przeciwnika i pole przed łucznikiem jest puste, może on się poruszyć
            if(!t3.equals("c") && s3.equals(" *** "))
            {
                plansza.setT(this.x, this.y + movement, s1);
                plansza.setT(this.x, this.y, " *** ");
                this.y = y + movement;
            }
            //widząc nieporuszającą się jednostkę z przodu rycerza, rycerz stara się poruszyć na bok
            else if(t1.equals(t3))
            {
                if(x!=0)
                {
                    String lewo = plansza.getT(this.x-1, this.y);
                    if(lewo == " *** ") {
                        //System.out.println(plansza.getT(this.x, this.y).substring(0, 4) + " porusza się w lewo");
                        plansza.setT(this.x - 1, this.y, s1);
                        plansza.setT(this.x, this.y, " *** ");
                        this.x = x - 1;
                    }
                }
                else if(x!=(max_x-1))
                {
                    String prawo = plansza.getT(this.x+1, this.y);
                    if(prawo == " *** ") {
                        //System.out.println(plansza.getT(this.x, this.y).substring(0, 4) + " porusza się w prawo");
                        plansza.setT(this.x + 1, this.y, s1);
                        plansza.setT(this.x, this.y, " *** ");
                        this.x = x + 1;
                    }
                }
            }
        }
    }
}
