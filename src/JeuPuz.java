import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class JeuPuz extends JeuGo {

    private final PieceGo[][] modele = new PieceGo[4][7];
    private final PieceGo[][] plateau = new PieceGo[4][7]; //cache le champ plateau de la classe mère Jeu (classe mère de JeuGo)
    public static Random rand = new Random();
    public static Scanner sc = new Scanner(System.in);

    public JeuPuz() {
        super();
        //On redispose les pièces de JeuGo au hasard pour former un modèle
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 7; j++) {
                this.modele[i][j] = this.getPiecesJeu().remove(rand.nextInt(this.getPiecesJeu().size()));
            }
        }
    }

    @Override
    public void initialiser() {
        this.getlistJ().add(new JoueurGo("Joueur", 0));
        //On met 8 pièces au hasard dans la liste du joueur pour qu'il puisse commencer à les placer
        for (int k = 0; k < 8; k++) {
            this.getlistJ().get(0).ajouter(this.getTalon().remove(rand.nextInt(this.getTalon().size())));
        }
    }

    @Override
    public void jouer() {
        this.initialiser();
        System.out.println();
        while (!jouerTour()) {
        }
        this.afficher();
        System.out.println("Partie gagnée");
    }

    @Override
    public boolean jouerTour() {
        boolean b = false;
        String action = "";
        while (!b || !this.sontIdentiques()) {
            this.afficher();
            System.out.println("Quelle action voulez-vous effectuer ?");
            if (!this.getlistJ().get(0).getPieces().isEmpty()) {
                System.out.print("Ajouter une pièce de votre liste sur le plateau(Tapez A)  ");
            }
            System.out.print("Retirer une pièce du plateau et la faire revenir dans votre liste (Tapez R)  ");
            while (true) {
                try {
                    action = sc.nextLine();
                    if (action.equals("A") || action.equals("R")) {
                        break;
                    //s'affiche dans tous les cas à partir du second tour..
                    } else {
                        System.out.println("Instruction non comprise, veuillez taper A ou R");
                    }
                } catch (Exception e) {
                    System.out.println("Instruction non comprise, veuillez taper A ou R");
                    sc.next();
                }
            }
            if (action.equals("A")) {
                this.ajouter();
            } else {
                this.retirer();
            }
            b = this.getlistJ().get(0).actualiseAGagne();
            if (this.getlistJ().get(0).getPieces().isEmpty() && !this.sontIdentiques()) {
                System.out.println("Plus de pièces à ajouter. Retirez des pièces mal placées sur le plateau pour continuer à jouer");
            }
        }
        return b;
    }

    @Override
    public void afficher() {
        this.afficheModele();
        this.affichePlateau();
        System.out.print("Pièces du joueur (seulement une partie du total) : ");
        System.out.println(this.getlistJ().get(0));
    }

    //Ici, on n'utilisera pas la méthode ajouter(PieceGo p) car on travaille sur un tableau à 2 dimensions
    //donc pas besoin de la redéfinir
    //Pour l'instant, on part sur l'idée qu'on peut placer une piècce sur n'importe quelle case vide.
    //Réfléchir à la possibilité de n'autoriser que des placements corrects(vrai puzzle)
    public boolean ajouter() {
        PieceGo p;
        int i = 0;
        int j = 0;
        System.out.println("Donnez le numéro de la pièce de votre liste que vous souhaitez placer");
        while (true) {
            try {
                int numPiece = sc.nextInt();
                if (numPiece >= 0 || numPiece < this.getlistJ().get(0).getPieces().size()) {
                    p = this.getlistJ().get(0).getPieces().get(numPiece);
                    System.out.println("Choix enregistré : pièce " + numPiece);
                    break;
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Saisie non valide. Veuillez saisir un numéro dans les limites de votre liste de pièces");
                //sc.next();
            } catch (Exception e){
                System.out.println("Saisie non valide. Veuillez saisir un numéro dans les limites de votre liste de pièces");
                sc.next();
            }
        }
        System.out.println("Donnez le numéro de la ligne sur laquelle vous souhaitez ajouter votre pièce(entre 0 et 3 inclus)");
        while (true) {
            try {
                i = sc.nextInt();
                if (i >= 0 && i < 4) {
                    System.out.println("Cordonnée i : " + i + " valide.");
                    break;
                } else System.out.println("Saisie non valide. Veuillez saisir un numéro entre 0 et 3 inclus");
            } catch (Exception e) {
                System.out.println("Saisie non valide. Veuillez saisir un numéro entre 0 et 3 inclus");
                sc.next();
            }
        }
        System.out.println("Donnez le numéro de la colonne sur laquelle vous souhaitez ajouter votre pièce(entre 0 et 6 inclus)");
        while (true) {
            try {
                j = sc.nextInt();
                if (j >= 0 && j < 7) {
                    System.out.println("Cordonnée j : " + j + " valide.");
                    break;
                } else System.out.println("Saisie non valide. Veuillez saisir un numéro entre 0 et 6 inclus");
            } catch (Exception e) {
                System.out.println("Saisie non valide. Veuillez saisir un numéro entre 0 et 6 inclus");
                sc.next();
            }
        }
        if (this.plateau[i][j] == null) {
            this.plateau[i][j] = p;
            this.getlistJ().get(0).getPieces().remove(p);
            this.pioche(getlistJ().get(0));
            return true;
        }
        System.out.println("Emplacement occupé par une pièce. Retirez-là pour pouvoir en placer une autre ici");
        return false;
    }

    public boolean retirer() {
        int i = 0;
        int j = 0;
        System.out.println("Donnez le numéro de la ligne de laquelle vous souhaitez retirer votre pièce(entre 0 et 3 inclus)");
        while (true) {
            try {
                i = sc.nextInt();
                if (i >= 0 || j < 4) {
                    System.out.println("Cordonnée i : " + i + " valide.");
                    break;
                } else System.out.println("Saisie non valide. Veuillez saisir un numéro entre 0 et 3 inclus");
            } catch (Exception e) {
                System.out.println("Saisie non valide. Veuillez saisir un numéro entre 0 et 3 inclus");
                sc.next();
            }
        }
        System.out.println("Donnez le numéro de la colonne de laquelle vous souhaitez retirer votre pièce(entre 0 et 6 inclus)");
        while (true) {
            try {
                j = sc.nextInt();
                if (j >= 0 || j < 7) {
                    System.out.println("Cordonnée j : " + j + " valide.");
                    break;
                } else System.out.println("Saisie non valide. Veuillez saisir un numéro entre 0 et 6 inclus");
            } catch (Exception e) {
                System.out.println("Saisie non valide. Veuillez saisir un numéro entre 0 et 6 inclus");
                sc.next();
            }
        }
        if (this.plateau[i][j] == null) {
            System.out.println("Pas de pièce à retirer à cet emplacement");
            return false;
        }
        this.getlistJ().get(0).ajouter(this.plateau[i][j]);
        this.plateau[i][j] = null;
        System.out.println("La pièce a bien été retirée de l'emplacement et a été placée dans la liste du joueur");
        return true;
    }

    public boolean sontIdentiques() {
        return Arrays.deepEquals(this.modele, this.plateau);
    }

    public void afficheModele() {
        System.out.println("Modèle :");
        for (int i = 0; i < 4; i++) {
            System.out.print("  ");
            for (int j = 0; j < 7; j++) {
                System.out.print(this.modele[i][j].toString().substring(1, 6).replace(",", " ") + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void affichePlateau() {
        System.out.println("Plateau :");
        System.out.println("---------------------------------------------");
        for (int i = 0; i < 4; i++) {
            System.out.print("| ");
            for (int j = 0; j < 7; j++) {
                if (this.plateau[i][j] == null) {
                    System.out.print("      ");
                } else {
                    System.out.print(this.plateau[i][j].toString().substring(1, 6).replace(",", " ") + " ");
                }
            }
            System.out.println("|");
        }
        System.out.println("---------------------------------------------\n");
    }

}
