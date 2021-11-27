
import java.util.ArrayList;
import java.util.Random;

public abstract class Jeu<P extends Piece, J extends Joueur> {

    private final ArrayList<P> piecesJeu;
    private final ArrayList<P> talon;
    private final ArrayList<J> listJ;
    private final ArrayList<P> plateau;
    public static Random r = new Random();

    public Jeu() {
        this.talon = new ArrayList<>();
        this.listJ = new ArrayList<>();
        this.plateau = new ArrayList<>();
        this.piecesJeu = new ArrayList<>();
    }

    public ArrayList<P> getTalon() {
        return this.talon;
    }

    public ArrayList<P> getPlateau() {
        return this.plateau;
    } 	 //Accesseurs pour listes renvoient les vraies listes et non la copie

    public ArrayList<J> getlistJ() {
        return this.listJ;
    }		 //car on a besoin de les modifier dans les classes filles

    public ArrayList<P> getPiecesJeu() {
        return this.piecesJeu;
    }

    public void jouer() {
        this.initialiser();
        System.out.println();
        while (!jouerTour()) {
        }
        for (int i = 0; i < this.listJ.size(); i++) {
            if (this.listJ.get(i).aGagne) {
                System.out.println(this.listJ.get(i).getNom() + " a gagné!");
                return;
            }
        }
        System.out.println("Aucun joueur n'a posé toutes ses pièces");
        if (this.gagnantAuxPoints() == null) {
            System.out.println("Égalité aux points entre au moins 2 joueurs. Partie nulle");
        } else {
            System.out.println(this.gagnantAuxPoints().getNom() + " a le plus petit total de points. " + this.gagnantAuxPoints().getNom() + " est gagnant aux points");
        }
    }

    @SuppressWarnings("unchecked")
    public boolean jouerTour() {
        int i = 0;
        boolean b = false;
        int dernierTour = 0;
        do {
            this.afficher();
            int pieceChoisie = Demande.demP(this.listJ.get(i).getNom());
            P p = (P) this.listJ.get(i).joue(pieceChoisie);             //On caste ici car Java ne considère pas P comme une Piece (malgré le extends au début de la classe) et n'accepte pas la conversion
            if (p != null && this.ajouter(p)) {
                this.listJ.get(i).getPieces().remove(pieceChoisie);
                b = this.listJ.get(i).actualiseAGagne();
            } else {
                if (this.pioche(this.listJ.get(i))) {
                    System.out.println("Choix non valide ou inexistant. " + this.listJ.get(i).getNom() + " pioche une pièce au hasard dans le talon");
                } else if (dernierTour > 0) {
                    return true; //talon vide et toutes les pièces n'ont pas été placées. Fin de partie
                } else {
                    System.out.println("Dernier tour. Si aucun joueur n'a posé toutes ses pièces après ce tour, on déterminera le vainqueur aux points");
                    dernierTour++;
                }
            }
            i++;
        } while (i < this.listJ.size() && !b);
        return b;
    }

    public abstract boolean ajouter(P p);

    public abstract void initialiser();

    public void afficher() {
        System.out.println();
        for (J i : this.listJ) {
            System.out.println(i);
        }
        System.out.println();
        System.out.print("Plateau : ");
        for (P p : this.plateau) {
            System.out.print(p);
        }
        System.out.print("\n\n");
    }

    public int[] pointsDesJoueurs() {
        int[] pdj = new int[this.getlistJ().size()];
        for (int i = 0; i < this.getlistJ().size(); i++) {
            pdj[i] = this.getlistJ().get(i).getPoints();
        }
        return pdj;
    }

    public J gagnantAuxPoints() {
        int min = this.pointsDesJoueurs()[0];
        int posDuMin = 0;
        for (int i = 1; i < this.pointsDesJoueurs().length; i++) {
            if (this.pointsDesJoueurs()[i] == min) {
                return null;
            }
            if (this.pointsDesJoueurs()[i] < min) {
                min = this.pointsDesJoueurs()[i];
                posDuMin = i;
            }
        }
        return this.getlistJ().get(posDuMin);
    }

    @SuppressWarnings("unchecked") //voir avec José pour expliquer le souci
    public boolean pioche(J joueur) {
        if (this.talon.size() != 0) {
            joueur.ajouter(this.talon.remove(r.nextInt(this.talon.size())));
            return true;
        } else {
            System.out.println("Plus de pièces dans le talon");
            return false;
        }
    }
    
}
