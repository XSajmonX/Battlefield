import java.io.*;
import java.util.*;

/**
 * tu wykonuje się pętla symulacji
 */
public class Main {
    public static void main(String[] args) throws IOException {
        List blue = new ArrayList<>();
        List red = new ArrayList<>();
        File plik = new File("WynikBitwy.txt");
        FileWriter filewriter = new FileWriter(plik);
        PrintWriter zapis = new PrintWriter(filewriter);
        Brama bc =new Brama();
        Brama bn =new Brama();
        statystyki_jednostek();
        dane_od_użytkownika(bc,bn,zapis);
        int x,y;
        Scanner scanner = new Scanner(System.in);
        //podanie rozmiaru planszy od użytkownika
        System.out.println("Podaj rozmiar planszy x na y:");
        zapis.println("\nRozmiar planszy:");
        do {
            System.out.print("x (min:5, max:50): ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= 5 && input <= 50) {
                    x = input;
                    zapis.println("x: "+x);
                    break;
                } else {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
                }
            } else {
                System.out.println("Nieprawidłowy format. Spróbuj ponownie.");
                scanner.next(); // Pomija nieprawidłowe dane wejściowe
            }
        } while (true);
        int max_x = x;

        do {
            System.out.print("y (min:10, max:50): ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= 10 && input <= 50) {
                    y = input;
                    zapis.println("y: "+y);
                    break;
                } else {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
                }
            } else {
                System.out.println("Nieprawidłowy format. Spróbuj ponownie.");
                scanner.next(); // Pomija nieprawidłowe dane wejściowe
            }
        } while (true);


        Plansza plansza = new Plansza(x,y);
        //tablica:          {R,Ł,J,E,K,A};
        //int[] T1 = new int[]{5,0,10,0,0,0};
        //int[] T2 = new int[]{5,0,10,0,0,0};
        int T1[] = new int[6];
        int T2[] = new int[6];

        System.out.println("\nPodaj ilość jednostek dla \u001B[31mDrużyny Czerwonej: \u001B[0m");
        zapis.println("\nIlość jednostek Drużyny Czerwonej: ");
        jednostki(T2,zapis,x,y);
        System.out.println("\nPodaj ilość jednostek dla \u001B[34mDrużyny Niebieskiej: \u001B[0m");
        zapis.println("\nIlość jednostek Drużyny Nibieskiej: ");
        jednostki(T1,zapis,x,y);
        dodanie_do_listy(red,T1,"czerwona");
        dodanie_do_listy(blue,T2,"niebieska");
        plansza.wspłrzędne(red, blue);

        int runda = 0;
        wyswietalnie_niebieskiej_bramy(x,bn);
        plansza.Plansza_wyświetl();
        wyswietlanie_czerwonej_bramy(x,bc);
        zapis.println("Plansza w zerowej rundzie:\n");
        plansza.plansza_zapis(x,bn,bc,zapis);

        /**
        pętla symulacji
        Pierwsi zaczynają czerwoni wykonują swoją turę (Przemieszczenie się lub atak i uleczenie się)
        obecnie wyświetla stan planszy co rundę, pokazuje waliki jakie występują
         */

        while(true) {
            System.out.print("\u000C");
            runda++;
            zapis.println("\nRunda: " + runda);
            System.out.println("--------------------");
            zapis.println("--------------------");
            System.out.println("\nRunda: "+runda+"\n");
            for (Object l1 : red) {
                ((Rycerz) l1).tura(plansza, red, blue, bc, bn, zapis,max_x);
                if (bn.hp <= 0){
                    break;
                }
            }
            if (bn.hp <= 0){
                break;
            }

            for(Object l2 : blue){
                ((Rycerz)l2).tura(plansza,red,blue,bc,bn,zapis,max_x);
                if(bc.hp<=0) {
                    break;
                }
            }
            if(bc.hp<=0) {
                break;
            }
            for(int i=0;i<x;i++) {
                bc.atak(blue, plansza,i, y-1);
            }
            for(int i=0;i<x;i++) {
                bn.atak(red, plansza,i, 0);
            }
            if(blue.size()==0 && red.size()==0){
                remis(x,zapis);
                break;
            }

            //System.out.println("\nRunda: "+runda+"\n");
            wyswietalnie_niebieskiej_bramy(x,bn);
            plansza.Plansza_wyświetl();
            wyswietlanie_czerwonej_bramy(x,bc);
        }
        if(bc.hp<=0) {
            wygrana_niebiescy(x,zapis);
        }
        else if(bn.hp<=0) {
            wygrana_czerwoni(x,zapis);
        }
        //ilość jednostek pozostałych na polu bitwy na koniec wszystkich rund
        System.out.println("\u001B[31m"+"Pozostali Czerwoni: "+"\u001B[0m"+red.size());
        zapis.println("Pozostali Czerwoni: "+red.size());
        System.out.println("\u001B[34m"+"Pozostali Niebiescy: "+"\u001B[0m"+blue.size());
        zapis.println("Pozostali Niebiescy: "+blue.size());

        zapis.close();
        scanner.close();
    }

    /**
    Funkcja dodanie_do_listy() ma zadanie dodać do listy konkretną ilość jednostek jaką podał użytkownik
    struktura listy:
    Rycerz      Łucznik     Jeździec    Rycerz_elitarny     Kusznik        Armata
    Funkcja dodaje nr każdej jednostce np. R00, R01, Ł10
     */
    public static void dodanie_do_listy(List lista,int[] T,String strona){
        for (int i=0;i<T[0];i++){
            lista.add(new Rycerz());
            int ind = i;
            String id = String.valueOf(ind);
            if(i<10)
            (((Rycerz)lista.get(i)).nazwa)+="0"+id;
            else (((Rycerz)lista.get(i)).nazwa)+=id;
        }
        int ind=0;
        for (int i=T[0];i<T[0]+T[1];i++){
            lista.add(new Lucznik());
            String id = String.valueOf(ind);
            if(ind<10) (((Lucznik)lista.get(i)).nazwa)+="0"+id;
            else (((Lucznik)lista.get(i)).nazwa)+=id;
            ind++;
        }
        ind=0;
        for (int i=T[0]+T[1];i<T[0]+T[1]+T[2];i++){
            lista.add(new Jezdziec());
            String id = String.valueOf(ind);
            if(ind<10) (((Jezdziec)lista.get(i)).nazwa)+="0"+id;
            else (((Jezdziec)lista.get(i)).nazwa)+=id;
            ind++;
        }
        ind=0;
        for (int i=T[0]+T[1]+T[2];i<T[0]+T[1]+T[2]+T[3];i++){
            lista.add(new Rycerz_elitarny());
            String id = String.valueOf(ind);
            if(ind<10) (((Rycerz_elitarny)lista.get(i)).nazwa)+="0"+id;
            else (((Rycerz_elitarny)lista.get(i)).nazwa)+=id;
            ind++;
        }
        ind=0;
        for (int i=T[0]+T[1]+T[2]+T[3];i<T[0]+T[1]+T[2]+T[3]+T[4];i++){
            lista.add(new Kusznik());
            String id = String.valueOf(ind);
            if(ind<10)(((Kusznik)lista.get(i)).nazwa)+="0"+id;
            else (((Kusznik)lista.get(i)).nazwa)+=id;
            ind++;
        }
        ind=0;
        for (int i=T[0]+T[1]+T[2]+T[3]+T[4];i<T[0]+T[1]+T[2]+T[3]+T[4]+T[5];i++){
            lista.add(new Armata());
            String id = String.valueOf(ind);
            if(ind<10) (((Armata)lista.get(i)).nazwa)+="0"+id;
            else (((Armata)lista.get(i)).nazwa)+=id;
            ind++;
        }

            for (int i = 0; i < lista.size(); i++) {
                if(strona.equals("czerwona")) {
                (((Rycerz) lista.get(i)).strona) = "czerwona";
                }else (((Rycerz)lista.get(i)).strona) ="niebieska";
            }
    }
    public static void dane_od_użytkownika(Brama b1,Brama b2,PrintWriter zapis){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Podaj parametry \u001B[31mBramy Czerwonej: \u001B[0m");
        zapis.println("Parametry Bramy Czerwonej: ");
        b1.strona = "czerwona";
        do {
            System.out.print("Podaj hp (max 40000): ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input > 0 && input <= 40000) {
                    b1.hp = input;
                    zapis.println("Hp: "+b1.hp);
                    break;
                } else {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
                }
            } else {
                System.out.println("Nieprawidłowy format. Spróbuj ponownie.");
                scanner.next(); // Pomija nieprawidłowe dane wejściowe
            }
        } while (true);

        do {
            System.out.print("Podaj Atak: ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= 0) {
                    b1.atak = input;
                    zapis.println("Atak: "+b1.atak);
                    break;
                } else {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
                }
            } else {
                System.out.println("Nieprawidłowy format. Spróbuj ponownie.");
                scanner.next(); // Pomija nieprawidłowe dane wejściowe
            }
        } while (true);

        System.out.println("Podaj parametry \u001B[34mBramy Niebieskiej: \u001B[0m");
        zapis.println("\nParametry Bramy Niebieskiej: ");
        b2.strona = "niebieska";
        do {
            System.out.print("Podaj hp (max 40000): ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input > 0 && input <= 40000) {
                    b2.hp = input;
                    zapis.println("Hp: "+b2.hp);
                    break;
                } else {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
                }
            } else {
                System.out.println("Nieprawidłowy format. Spróbuj ponownie.");
                scanner.next(); // Pomija nieprawidłowe dane wejściowe
            }
        } while (true);

        do {
            System.out.print("Podaj Atak: ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= 0) {
                    b2.atak = input;
                    zapis.println("Atak: "+b2.atak);
                    break;
                } else {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
                }
            } else {
                System.out.println("Nieprawidłowy format. Spróbuj ponownie.");
                scanner.next(); // Pomija nieprawidłowe dane wejściowe
            }
        } while (true);
    }

    /**
     * poprawne wpisywanie jednostek
     * @param T2 tablica jednostek
     * @param zapis zapis do pliku
     * @param x
     * @param y
     */
    public static void jednostki(int T2[],PrintWriter zapis, int x, int y)
    {
        //maksymalna ilość jednostek jest obliczana na podstawie wielkości planszy podanej przez użytkownika i jest niewiększa od 100
        int max_y=(y-2)/2;
        int max1=x*max_y;
        Scanner scanner = new Scanner(System.in);
        //Skanuje wartość podaną od użytkownika i ignoruje wartości ujemne oraz które nie są intami
        do {
            System.out.print("Podaj ilość Rycerzy (max " + Math.min(max1,100) + "): ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= 0 && input <= Math.min(max1,100)) {
                    T2[0] = input;
                    max1 -= T2[0];
                    zapis.println("Ilość Rycerzy: " + T2[0]);
                    break;
                }
                else {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
                }
            } else {
                System.out.println("Nieprawidłowy format. Spróbuj ponownie.");
                scanner.next(); // Pomija nieprawidłowe dane wejściowe
            }
        } while (true);
        //kod jest powtarzany analogicznie dla pozostałych jednostek
        do {
            System.out.print("Podaj ilość Łuczników (max " + Math.min(max1,100) + "): ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= 0 && input <= Math.min(max1,100)) {
                    T2[1] = input;
                    max1 -= T2[1];
                    zapis.println("Ilość Łuczników: " + T2[1]);
                    break;
                }
                else {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie."); //pomija dane ujemne
                }
            } else {
                System.out.println("Nieprawidłowy format. Spróbuj ponownie.");
                scanner.next(); // Pomija nieprawidłowe dane wejściowe
            }
        } while (true);
        do {
            System.out.print("Podaj ilość Jeźdźców (max " + Math.min(max1,100) + "): ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= 0 && input <= Math.min(max1,100)) {
                    T2[2] = input;
                    max1 -= T2[2];
                    zapis.println("Ilość Jeźdźców: " + T2[2]);
                    break;
                }
                else {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
                }
            } else {
                System.out.println("Nieprawidłowy format. Spróbuj ponownie.");
                scanner.next(); // Pomija nieprawidłowe dane wejściowe
            }
        } while (true);
        do {
            System.out.print("Podaj ilość Rycerzy Elitarnych (max " + Math.min(max1,100) + "): ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= 0 && input <= Math.min(max1,100)) {
                    T2[3] = input;
                    max1 -= T2[3];
                    zapis.println("Ilość Rycerzy Elitarnych: " + T2[3]);
                    break;
                }
                else {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
                }
            } else {
                System.out.println("Nieprawidłowy format. Spróbuj ponownie.");
                scanner.next(); // Pomija nieprawidłowe dane wejściowe
            }
        } while (true);
        do {
            System.out.print("Podaj ilość Kuszników (max " + Math.min(max1,100) + "): ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= 0 && input <= Math.min(max1,100)) {
                    T2[4] = input;
                    max1 -= T2[4];
                    zapis.println("Ilość Kuszników: " + T2[4]);
                    break;
                }
                else {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
                }
            } else {
                System.out.println("Nieprawidłowy format. Spróbuj ponownie.");
                scanner.next(); // Pomija nieprawidłowe dane wejściowe
            }
        } while (true);
        do {
            System.out.print("Podaj ilość Armat (max " + Math.min(max1,100) + "): ");
            if (scanner.hasNextInt()) {
                int input = scanner.nextInt();
                if (input >= 0 && input <= Math.min(max1,100)) {
                    T2[5] = input;
                    max1 -= T2[5];
                    zapis.println("Ilość Armat: " + T2[5]);
                    break;
                }
                else {
                    System.out.println("Nieprawidłowa wartość. Spróbuj ponownie.");
                }
            } else {
                System.out.println("Nieprawidłowy format. Spróbuj ponownie.");
                scanner.next(); // Pomija nieprawidłowe dane wejściowe
            }
        } while (true);
    }

    /**
     * Funkcja wyświetla bramę niebieską dostosowaną do wielkości planszy
     * @param x
     * @param bn
     */
    public static void wyswietalnie_niebieskiej_bramy(int x, Brama bn)
    {
        for(int i=0;i<x;i++)
        {
            System.out.print("\u001B[34m----");
        }
        System.out.println("-\u001B[0m");

        for(int i=0;i<((x*2)-7);i++)
        {
            System.out.print(" ");
        }
        System.out.print("\u001B[34m"+"Brama Niebieska\n");
        for(int i=0;i<((x*2)-9);i++)
        {
            System.out.print(" ");
        }
        System.out.println("HP: "+bn.hp+" "+" Atak: "+bn.atak +"\u001B[0m");
        for(int i=0;i<x;i++)
        {
            System.out.print("\u001B[34m----");
        }
        System.out.println("-\u001B[0m");
    }

    /**
     * Funkcja wyświetla bramę czerwoną dostosowaną do wielkości planszy
     * @param x
     * @param bc brama czerwona
     */
    public static void wyswietlanie_czerwonej_bramy(int x, Brama bc)
    {
        for(int i=0;i<x;i++)
        {
            System.out.print("\u001B[31m----");
        }
        System.out.println("-\u001B[0m");

        for(int i=0;i<((x*2)-7);i++)
        {
            System.out.print(" ");
        }
        System.out.print("\u001B[31m"+"Brama Czerwona\n");
        for(int i=0;i<((x*2)-9);i++)
        {
            System.out.print(" ");
        }
        System.out.println("HP: "+bc.hp+" "+" Atak: "+bc.atak +"\u001B[0m");
        for(int i=0;i<x;i++)
        {
            System.out.print("\u001B[31m----");
        }
        System.out.println("-\u001B[0m");
    }

    /**
     * funkcja wyświetla wiadomość o wygranej niebieskich dostosowaną do wielkości planszy
     */

    public static void wygrana_niebiescy(int x,PrintWriter zapis)
    {
        for(int i=0;i<x;i++)
        {
            System.out.print("\u001B[34m----");
        }
        System.out.println("-\u001B[0m");
        for(int i=0;i<((x*2)-13);i++)
        {
            System.out.print(" ");
        }
        System.out.println("\u001B[34mStrona niebieska wygrała!!!\u001B[0m");
        zapis.println("\n---------------------------");
        zapis.println("Strona niebieska wygrała!!!");
        zapis.println("---------------------------");
        for(int i=0;i<x;i++)
        {
            System.out.print("\u001B[34m----");
        }
        System.out.println("-\u001B[0m");
    }

    /**
     * Funkcja wyświetla wiadomość o wygranej czerwonych dostosowaną do wielkości planszy
     * @param x
     * @param zapis zapis do pliku
     */
    public static void wygrana_czerwoni(int x,PrintWriter zapis)
    {
        for(int i=0;i<x;i++)
        {
            System.out.print("\u001B[31m----");
        }
        System.out.println("-\u001B[0m");
        for(int i=0;i<((x*2)-13);i++)
        {
            System.out.print(" ");
        }
        System.out.println("\u001B[31mStrona czerwona wygrała!!!\u001B[0m");
        zapis.println("\n--------------------------");
        zapis.println("Strona czerwona wygrała!!!");
        zapis.println("--------------------------");
        for(int i=0;i<x;i++)
        {
            System.out.print("\u001B[31m----");
        }
        System.out.println("-\u001B[0m");
    }

    /**
     * Funkcja wyświetla wiadomość o remisie dostosowaną do wielkości planszy
     * @param x
     * @param zapis
     */
    public static void remis(int x,PrintWriter zapis) {
        for (int i = 0; i < x; i++) {
            System.out.print("----");
        }
        System.out.println("-");
        for (int i = 0; i < ((x * 2) - 11); i++) {
            System.out.print(" ");
        }
        System.out.println("Remis nikt nie wygrał");
        zapis.println("\n-----------------------");
        zapis.println(" Remis nikt nie wygrał");
        zapis.println("-----------------------");
        for (int i = 0; i < x; i++) {
            System.out.print("----");
            zapis.print("----");
        }
        System.out.println("-");
        zapis.print("-\n");
    }

    /**
     * wyopisuje statystyki jednostek np. hp, atak przed podaniem parametrów przez użytkownika
     */
    public static void statystyki_jednostek(){
        Rycerz R = new Rycerz();
        Lucznik Ł = new Lucznik();
        Jezdziec J = new Jezdziec();
        Rycerz_elitarny Re =new Rycerz_elitarny();
        Kusznik K = new Kusznik();
        Armata A = new Armata();
        System.out.println("Statystyki jednostek: ");
        System.out.println("Statystyki\t\t"+"HP  \t"+"DMG\t\t"+"HEAL\t"+"MOVE\t"+"RANGE");
        System.out.println("Rycerz\t\t\t"+R.hp+" \t"+R.atak+" \t"+R.leczenie+" \t\t"+R.movement);
        System.out.println("Łucznik\t\t\t"+Ł.hp+" \t"+Ł.atak+" \t"+Ł.leczenie+" \t\t"+Ł.movement+" \t\t"+Ł.zasięg);
        System.out.println("Jeździec\t\t"+J.hp+" \t"+J.atak+" \t"+J.leczenie+" \t\t"+J.movement);
        System.out.println("Rycerz_Elitarny\t"+Re.hp+" \t"+Re.atak+" \t"+Re.leczenie+" \t\t"+Re.movement);
        System.out.println("Kusznik\t\t\t"+K.hp+" \t"+K.atak+" \t"+K.leczenie+" \t\t"+K.movement+" \t\t"+A.zasięg);
        System.out.println("Armata\t\t\t"+A.hp+" \t"+A.atak+" \t"+A.leczenie+" \t\t"+A.movement+" \t\t"+A.zasięg);
    }
}



