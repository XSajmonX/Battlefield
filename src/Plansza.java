import java.io.PrintWriter;
import java.security.PublicKey;
import java.util.List;
import java.util.Random;

public class Plansza {

    Plansza(){
        this.x=10;
        this.y=10;
        T = new String[x][y];
        this.Plansza_domyślna();
    }

    /**
     *   stworzenie planszy o wymiarach podanych przez użytkownika
     */

    Plansza(int x, int y){
        this.x=x;
        this.y=y;
        T = new String[x][y];
        this.Plansza_domyślna();
    }

    private int x, y;
    private String[][] T;
   public String getT(int x,int y){
        return this.T[x][y];
    }
    public void setT(int x,int y, String nazwa) {
        T[x][y] = nazwa;
    }
    public int getX()
    {
        return this.x;
    }
    public int getY()
    {
        return this.y;
    }

    /**
     * Wypełnienie planszy (*** - puste pole)
     */
    private void Plansza_domyślna(){
        for(int j=0;j<y;j++){
            for(int i=0;i<x;i++){
                T[i][j] = " *** ";
            }
        }
    }

    /**
     *  Wyświetla aktualne położenie jednostek uwzględnia kolor strony
     */
    public void Plansza_wyświetl(){
        String s;
        for(int j=0;j<y;j++){
            for(int i=0;i<x;i++) {
                s = T[i][j];
                if (s.substring(4).equals("n"))
                {
                    System.out.print("\u001B[34m" + s.substring(0, 4) + "\u001B[0m");
                }
                else if(s.substring(4).equals("c")) System.out.print("\u001B[31m"+s.substring(0, 4)+"\u001B[0m");
                else System.out.print(s.substring(0, 4));
            }
            System.out.println();
        }
    }

    /**
     *  Wyświetla w zapisie do pliku planszę z zerowej rundy
     * @param x pole graniczne
     * @param bn brama niebieska
     * @param bc brama czerwona
     * @param zapis zapis do pliku
     */
    public void plansza_zapis(int x, Brama bn,Brama bc,PrintWriter zapis) {
        for(int i=0;i<x;i++)
        {
            zapis.print("----");
        }
        zapis.print("-\n");

        for(int i=0;i<((x*2)-7);i++)
        {
            zapis.print(" ");
        }
        zapis.println("Brama Niebieska");
        for(int i=0;i<((x*2)-9);i++)
        {
            zapis.print(" ");
        }
        zapis.print("HP: "+bn.hp+" "+" Atak: "+bn.atak+"\n");
        for(int i=0;i<x;i++)
        {
            zapis.print("----");
        }
        zapis.println();
        String s;
        for (int j = 0; j < y; j++) {
            for (int i = 0; i < x; i++) {
                s = T[i][j];
                if (s.substring(4).equals("n")) {
                    zapis.print(s.substring(0, 4));
                }
                else if (s.substring(4).equals("c"))
                {
                     zapis.print(s.substring(0, 4));
                }
                else zapis.print(s.substring(0, 4));
            }
            zapis.println();
        }
        for(int i=0;i<x;i++)
        {
            zapis.print("----");
        }
        zapis.print("-\n");

        for(int i=0;i<((x*2)-7);i++)
        {
            zapis.print(" ");
        }
        zapis.print("Brama Czerwona\n");
        for(int i=0;i<((x*2)-9);i++)
        {
            zapis.print(" ");
        }
        zapis.println("HP: "+bc.hp+" "+" Atak: "+bc.atak);
        for(int i=0;i<x;i++)
        {
            zapis.print("----");
        }
        zapis.print("-\n");
    }

/**
 funkcja nadaje każdej jednostce niepowtarzalne współrzędne i wpisuje nazwę jednostki do tablicy o wylosowanych współrzędnych
 dodając niewidzalną na planszystronę (n - niebieska, c -czerwona). Najpierw wykonuje dla strony niebieskiej potem dla czerwonej
 Strona czerwona znajduje się na dolnej części planszy, niebieska na górnej
 */
    public void wspłrzędne(List listaC , List listaN){
        Random random = new Random();
        int k=0;
        for(int i=0;i<listaN.size();i++) {

            do{
                ((Rycerz)listaN.get(i)).x = random.nextInt(x);
                (((Rycerz)listaN.get(i)).y) = random.nextInt(y/2-1);
            }while(T[((Rycerz)listaN.get(i)).x][((Rycerz)listaN.get(i)).y]!=" *** ") ;
            k++;
            T[((Rycerz)listaN.get(i)).x][((Rycerz)listaN.get(i)).y]=" "+((Rycerz)listaN.get(i)).nazwa+"n";
        }
        for(int i=0;i<listaC.size();i++) {

            do{
                ((Rycerz)listaC.get(i)).x = random.nextInt(x);
                (((Rycerz)listaC.get(i)).y) = y-1-random.nextInt(y/2-1);
            }while(T[((Rycerz)listaC.get(i)).x][((Rycerz)listaC.get(i)).y]!=" *** ") ;
            k++;
            T[((Rycerz)listaC.get(i)).x][((Rycerz)listaC.get(i)).y]=" "+((Rycerz)listaC.get(i)).nazwa+"c";
        }

    }

}