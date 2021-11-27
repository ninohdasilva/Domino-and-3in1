public class JeuGo extends Jeu<PieceGo, JoueurGo> {

    public JeuGo() {
        super();
        String[] tab = {"j", "v", "b", "o"};
        int a = 0;
        for (int i = 6; i >= 0; i--) {
            for (int j = i; j >= 0; j--) {
                this.getPiecesJeu().add(new PieceGo(i, j, tab[a++], tab[a++])); //Ne sort pas de tab car sa longueur est paire
                if (a == tab.length) {
                    a = 0;
                }
            }
        }
        for (PieceGo tp : this.getPiecesJeu()) {
            this.getTalon().add(tp);
        }
    }

    @Override
    public void initialiser() {
        String[][] t = Demande.demJ();
        for (int i = 0; i < t.length; i++) {
            this.getlistJ().add(new JoueurGo(t[i][0], Integer.valueOf(t[i][1])));
        }
        for (int j = 0; j < this.getlistJ().size() - 1; j++) {
            for (int i = 1; i < this.getlistJ().size(); i++) {
                if (this.getlistJ().get(i).plusGrand(this.getlistJ().get(i - 1))) {
                    JoueurGo a = this.getlistJ().set(i - 1, this.getlistJ().get(i));
                    this.getlistJ().set(i, a);
                }
            }
        }
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < this.getlistJ().size(); j++) {
                this.pioche(this.getlistJ().get(j));// et on les fais piocher
            }
        }
        if (this.getlistJ().size() < 3) {
            for (int j = 0; j < this.getlistJ().size(); j++) {
                this.pioche(this.getlistJ().get(j));
            }
        }

    }

    @Override
    public boolean ajouter(PieceGo p) {
        if (p != null) {
            if (this.getPlateau().size() == 0) {
                this.getPlateau().add(p);
                return true;
            }
            int[] t = p.getNb();
            String[] t0 = p.getC();
            int[] t2 = this.getPlateau().get(0).getNb();
            String[] t20 = this.getPlateau().get(0).getC();
            if (t[1] == t2[0] || t[0] == t2[0]) {
                if (t[1] != t2[0]) {
                    p = new PieceGo(t[1], t[0], t0[1], t0[0]);
                }
                this.getPlateau().add(0, p);
                return true;
            }
            if (t0[1].equals(t20[0]) || t0[0].equals(t20[0])) {
                if (!t0[1].equals(t20[0])) {
                    p = new PieceGo(t[1], t[0], t0[1], t0[0]);
                }
                this.getPlateau().add(0, p);
                return true;
            }
            if (this.getPlateau().size() >= 1) {
                int[] t3 = this.getPlateau().get(this.getPlateau().size() - 1).getNb();
                String[] t30 = this.getPlateau().get(this.getPlateau().size() - 1).getC();
                if (t[0] == t3[1] || t[1] == t3[1]) {
                    if (t[0] != t3[1]) {
                        p = new PieceGo(t[1], t[0], t0[1], t0[0]);
                    }
                    this.getPlateau().add(p);
                    return true;
                }
                if (t0[0].equals(t30[1]) || t0[1].equals(t30[1])) {
                    if (!t0[0].equals(t30[1])) {
                        p = new PieceGo(t[1], t[0], t0[1], t0[0]);
                    }
                    this.getPlateau().add(0, p);
                    return true;
                }
            }
        }
        return false;
    }

}
