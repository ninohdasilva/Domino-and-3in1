

public class JeuDomino extends Jeu<PieceDomino, JoueurDomino> {
    public int tdj;
    
    public JeuDomino() {
        super();
        for (int i = 0; i <= 6; i++) {
            for (int j = i; j <= 6; j++) {
                this.getPiecesJeu().add(new PieceDomino(i, j));
            }
        }
        for (PieceDomino tp : this.getPiecesJeu()) {
            this.getTalon().add(tp);
        }
    }

    @Override
    public void initialiser() {
        String[][] t = Demande.demJ();
        for (int i = 0; i < t.length; i++) {
            this.getlistJ().add(new JoueurDomino(t[i][0], Integer.valueOf(t[i][1])));
        }
        for (int j = 0; j < this.getlistJ().size() - 1; j++) {
            for (int i = 1; i < this.getlistJ().size(); i++) {
                if (this.getlistJ().get(i).plusGrand(this.getlistJ().get(i - 1))) {
                    JoueurDomino a = this.getlistJ().set(i - 1, this.getlistJ().get(i));
                    this.getlistJ().set(i, a);
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < this.getlistJ().size(); j++) {
                this.pioche(this.getlistJ().get(j));// et on les fait piocher
            }
        }
        if (this.getlistJ().size() < 3) {
            for (int j = 0; j < this.getlistJ().size(); j++) {
                this.pioche(this.getlistJ().get(j));
            }
        }

    }
    
    //Version que nous comptions utiliser pour l'initialisation graphique. Idéé abandonnée
    
    /*public void initialiserViaTab(String[] tNoms, String[] tAges){
        if(tNoms.length != tAges.length) {System.out.println("Tableaux de taille différente ");return;}
        for(int i=0; i<tNoms.length;i++) {
            if(!tAges[i].matches("^[0-9]$")) {
                this.getlistJ().add(new JoueurDomino(tNoms[i],Integer.parseInt(tAges[i])));
            } else this.getlistJ().add(new JoueurDomino(tNoms[i],0));
        }
        for (int j = 0; j < this.getlistJ().size() - 1; j++) {
            for (int i = 1; i < this.getlistJ().size(); i++) {
                if (this.getlistJ().get(i).plusGrand(this.getlistJ().get(i - 1))) {
                    JoueurDomino a = this.getlistJ().set(i - 1, this.getlistJ().get(i));
                    this.getlistJ().set(i, a);
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < this.getlistJ().size(); j++) {
                this.pioche(this.getlistJ().get(j));// et on les fait piocher
            }
        }
        if (this.getlistJ().size() < 3) {
            for (int j = 0; j < this.getlistJ().size(); j++) {
                this.pioche(this.getlistJ().get(j));
            }
        }
        
    }*/

    @Override
    public boolean ajouter(PieceDomino p) {
        if (p != null) {
            if (this.getPlateau().isEmpty()) {
                this.getPlateau().add(p);
                return true;
            }
            int[] t = p.getNb();
            int[] t2 = this.getPlateau().get(0).getNb();
            if (t[0] == t2[0] || t[1] == t2[0]) {
                if (t[1] != t2[0]) {
                    p = new PieceDomino(t[1], t[0]);
                }
                this.getPlateau().add(0, p);
                return true;
            }
            if (this.getPlateau().size() >= 1) {
                int[] t3 = this.getPlateau().get(this.getPlateau().size() - 1).getNb();
                if (t[0] == t3[1] || t[1] == t3[1]) {
                    if (t[0] != t3[1]) {
                        p = new PieceDomino(t[1], t[0]);
                    }
                    this.getPlateau().add(p);
                    return true;
                }
            }
        }
        return false;
    }
    
    public void inctdj(){
        if(this.tdj < this.getlistJ().size()-1) this.tdj++;
        else tdj = 0;
    }
    public void settdj(int i){this.tdj = i;}
    public int gettdj(){return this.tdj;}
    
    public boolean partieGagnee(){
        for(JoueurDomino j : this.getlistJ()){
            if(j.actualiseAGagne()) return true;
        }
        return false;
    }
    

}
