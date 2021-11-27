import java.util.ArrayList;

public abstract class Joueur<P extends Piece> { //Joueur est une classe abstraite pour qu'on ne puisse pas l'instancier 

    private final String nom;
    private final int age;
    private final ArrayList<P> pieces;
    public boolean aGagne = false;
    

    public Joueur(String n, int a) {
        this.pieces = new ArrayList<>();
        this.nom = n;
        this.age = a;
    }

    public String getNom() {
        return this.nom;
    }

    public int getAge() {
        return this.age;
    }

    public ArrayList<P> getPieces() {
        return this.pieces;
    } //car on veut pouvoir modifier cette liste depuis jouerTour() dans la classe Jeu 

    public int getPoints() {
        int nbp = 0;
        for (P p : this.pieces) {
            nbp += p.getNb()[0] + p.getNb()[1];
        }
        return nbp;
    }

    public void ajouter(P p) {
        this.pieces.add(p);
    }

    public P joue(int i) {
        P p = null;
        if (i > -1 && i < this.pieces.size()) {
            p = this.pieces.get(i);
        }
        return p;
    }
    
    public P joue(P p){
        if (this.pieces.contains(p)) return p;
        else return null;
    }

    public boolean actualiseAGagne() {
        this.aGagne = (this.pieces.isEmpty());
        return this.aGagne;
    }

    @Override
    public String toString() {
        String s = this.nom + " ";
        for (int i = 0; i < this.pieces.size(); i++) {
            s += this.pieces.get(i).toString();
        }
        return s;
    }

    public boolean plusGrand(Joueur j) {
        return this.age > j.age;
    }

}
