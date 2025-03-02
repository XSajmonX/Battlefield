import java.io.PrintWriter;
import java.util.List;
/**
 * klasa jezdzca - od rycerza różni się możliwością poruszania. Gdy w przypadku dwóch wolnych pól przed nim nie ma żadnej jednostki, jeździec porusza się o dwa pola. Dotyczy to także sytuacji, gdy bezpośrednio przed jeźdźcem znajduje się sojusznik, a dwa pola przed jeźdźcem jest wolne pole.
 */
public class Jezdziec extends Rycerz{
    public Jezdziec() {
        this.nazwa = "J";
        this.hp = 1100;
        this.movement =2;
        this.atak=480;
        this.leczenie = 25;
        this.maxhp = 1100;
    }

    /**
     *  tura Jeźdźca
     */
   public void tura(Plansza plansza, List red, List blue, Brama bc, Brama bn, PrintWriter zapis, int max_x){

//Przykładowo: s1 = J00c t1 = c         nazwa i strona jednostki której jest tura
        String s1 = plansza.getT(this.x,this.y);
        String t1 = s1.substring(4,5);
// dla strony czerwonej
       boolean dev = false;
       if(dev) System.out.println(x+" "+y +" " + s1+" "+hp);
        if(t1.equals("c")) {
//dojście jednostki do krańca planszy (górna krawdź), atak na bramę
            if (y < movement) {
                if(y==1 && plansza.getT(x, y-1)==" *** "){
                    plansza.setT(this.x, this.y - 1, s1);
                    plansza.setT(this.x, this.y, " *** ");
                    this.y = y - 1;
                    return;
                }
                System.out.println("Walka " + "\u001B[31m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[34m" + "Bramą niebieską" + "\u001B[0m");
                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z Bramą Niebieską");
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
/*
s1 = nazwa jednostki, której jest tura ; t1 = c
s2 = pole o 2 przed Jeźdźcem ; t2 = c lub n
s3 = pole bezpośrednio przed Jeźdźcem ; t3 = c lub n
 */
            String s2 = plansza.getT(this.x, this.y - movement);
            String t2 = s2.substring(4, 5);
            String s3 = plansza.getT(this.x, this.y - movement + 1);
            String t3 = s3.substring(4, 5);

// przed jeźdźcem pole puste lub sojusznik, pole odległość 2 puste --> porusza się o 2 pola
            if (s2 == " *** " && (t3 == t1 || s3 == " *** ")) {
                plansza.setT(this.x, this.y - movement, s1);
                plansza.setT(this.x, this.y, " *** ");
                this.y = y - movement;
                //System.out.println(x+" "+y +" " + s1);
// przed jeźdźcem jest przeciwnik --> atak
            } else if (!t1.equals(t3) && s3!=" *** ") {
                System.out.println("Walka " + "\u001B[31m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[34m" + plansza.getT(this.x, this.y - 1).substring(0, 4) + "\u001B[0m");
                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z " + plansza.getT(this.x, this.y - 1).substring(0, 4));
                atak(blue, plansza, x, y-1,zapis);
//przed jeźdźcem jest pusto
            } else if (s3 == " *** ") {
                plansza.setT(this.x, this.y - movement + 1, s1);
                plansza.setT(this.x, this.y, " *** ");
                this.y = y - movement + 1;
            }
            else if (plansza.getT(this.x, this.y - 1) == " *** ") {//if żeby koń podążał za przyjazną jednostką
                plansza.setT(this.x, this.y - 1, s1);
                plansza.setT(this.x, this.y, " *** ");
                this.y = y - 1;
            }
            //widząc nieporuszającą się jednostkę z przodu rycerza, rycerz stara się poruszyć na bok
            else if(t1.equals(t3))
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

        else  if(t1.equals("n")) {

                // atak na bramę
            if(this.y>=plansza.getY()-movement)
            {
                if(y==plansza.getY()-2 && plansza.getT(x, y+1)==" *** "){
                    plansza.setT(this.x, this.y + 1, s1);
                    plansza.setT(this.x, this.y, " *** ");
                    this.y = y + 1;
                    return;
                }
                System.out.println("Walka " + "\u001B[34m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[31m" + "Bramą Czrewoną"+"\u001B[0m");
                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z Bramą Czrewoną");
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
            String s2 = plansza.getT(this.x,this.y+movement);
            String t2 = s2.substring(4,5);
            String s3 = plansza.getT(this.x,this.y+movement-1);
            String t3 = s3.substring(4,5);
            if(s2 == " *** " && ( t3 == t1 || s3==" *** " ) ){
                plansza.setT(this.x,this.y+movement, s1);
                plansza.setT(this.x,this.y, " *** ");
                this.y = y+movement;
            }else if(!t1.equals(t3) && s2!=" *** " && s3!=" *** ") {
                System.out.println("Walka " + "\u001B[34m" + plansza.getT(this.x, this.y).substring(0, 4) + "\u001B[0m" + " z " + "\u001B[31m" + plansza.getT(this.x, this.y + 1).substring(0, 4) + "\u001B[0m");
                zapis.println("Walka " + plansza.getT(this.x, this.y).substring(0, 4) + " z " + plansza.getT(this.x, this.y + 1).substring(0, 4));
                //walka
                atak(red, plansza, x, y + 1,zapis);
            }
            else if(s3 == " *** ")
            {
                plansza.setT(this.x,this.y+movement-1, s1);
                plansza.setT(this.x,this.y, " *** ");
                this.y = y+movement-1;
            }
            else if (plansza.getT(this.x, this.y + 1) == " *** ") {//if żeby koń podążał za przyjazną jednostką
                plansza.setT(this.x, this.y + 1, s1);
                plansza.setT(this.x, this.y, " *** ");
                this.y = y + 1;
            }
            //widząc nieporuszającą się jednostkę z przodu jeźdźcy, jeździec stara się poruszyć na bok
            else if(t1.equals(t3))
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

        }

    }
}
