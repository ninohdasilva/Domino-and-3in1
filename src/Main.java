
import java.io.IOException;
import java.util.Scanner;

public class Main {

    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
        System.out.println("A quel jeu voulez vous jouer ?");
        System.out.println("Tapez 'd'(sans les apostrophes) pour la version graphique du domino ");
        System.out.println("Tapez 'dt' (sans les apostrophes) pour la version textuelle du domino ");
        System.out.println("Tapez 'dgm'(sans les apostrophes) pour la version textuelle du jeu de domino-gomettes ");
        System.out.println("Tapez 'p'(sans les apostrophes) pour la version textuelle du puzzle ");
        System.out.println("Tapez 's' (sans les apostrophes) pour la version textuelle du saboteur");
        String s;
        while (true) {
            try {
                s = sc.nextLine();
                if (s.equals("d") || s.equals("dt") || s.equals("dgm") || s.equals("p") || s.equals("s")) {
                    break;
                } else {
                    System.out.println("Saisie invalide, veuillez entrer les chaines de caractère 'd', 'dt', 'dgm','p' ou 's'");
                }
            } catch (Exception a) {
                System.out.println("Saisie invalide, veuillez entrer les chaines de caractère 'd', 'dt', 'dgm','p' ou 's'");
                sc.next();
            }
        }
        if (s.equals("d")) {
            JeuDomino jD = new JeuDomino();
            jD.initialiser();
            VueDomino vue = new VueDomino(jD);
            ControleurDomino cD = new ControleurDomino(vue);
            cD.jouerG();
        } else if (s.equals("dt")) {
            JeuDomino jD = new JeuDomino();
            jD.jouer();
        } else if (s.equals("dgm")) {
            JeuGo jG = new JeuGo();
            jG.jouer();
        } else if (s.equals("p")) {
            JeuPuz jP = new JeuPuz();
            jP.jouer();
        } else {
            JeuSabo jS = new JeuSabo();
            jS.jouer();
        }

    }

}
