//Idéé de début graphique abandonnée, produit des fenetres nulles

/*import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import static java.awt.GridBagConstraints.*;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MenuPrincipal extends JFrame {

    JButton dominoGraphique;
    JButton dominoTextuel;
    JButton dominoGoTextuel;
    JButton puzzleTextuel;
    JButton saboteurTextuel;
    JPanel panneauBoutons;
    private final Container pane;

    public MenuPrincipal() throws IOException {
        this.panneauBoutons = new JPanel();
        this.dominoGraphique = new JButton("Domino (version graphique)");
        this.dominoGraphique.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                try {
                    JOptionPane.showMessageDialog(null, "Choix enregistré : ");
                    MenuPrincipal.this.setVisible(false);
                    MenuPrincipal.this.dispose();
                    secondeFenetre secfen = new secondeFenetre(); //Tentative de tout faire graphiquement mais fenetre vide. Idée abandonnée
                    secfen.pack();                                  //Expliquer dans le rapport. Voir à la ligne ...
                    secfen.setVisible(true);
                    secfen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    JeuDomino jD = new JeuDomino();
                    jD.initialiser();
                    VueDomino vue = new VueDomino(jD);
                    ControleurDomino cD = new ControleurDomino(vue);
                    cD.jouerG();
                } catch (IOException ex) {
                    Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        this.dominoTextuel = new JButton("Domino (version textuelle)");
        this.dominoGoTextuel = new JButton("Domino-Gommettes (version textuelle)");
        this.puzzleTextuel = new JButton("Puzzle (version textuelle)");
        this.saboteurTextuel = new JButton("Saboteur (version textuelle)");
        this.pane = this.getContentPane();
        this.panneauBoutons.add(this.dominoGraphique);
        this.panneauBoutons.add(this.dominoTextuel);
        this.panneauBoutons.add(this.dominoGoTextuel);
        this.panneauBoutons.add(this.puzzleTextuel);
        this.panneauBoutons.add(this.puzzleTextuel);
        this.panneauBoutons.add(this.saboteurTextuel);
        this.pane.setLayout(new FlowLayout());
        this.panneauBoutons.setLayout(new GridLayout(5, 1));
        this.pane.add(new JPanel().add(new JLabel("A quel jeu voulez vous jouer ?")));
        this.pane.add(this.panneauBoutons);
        this.setTitle("Menu Principal");
        this.setPreferredSize(new Dimension(300, 250));
    }

    class secondeFenetre extends JFrame implements ActionListener { //Idée abandonnée

        //private JTextField nbJoueurs;
        Container pane;
        private JComboBox jbc;
        private JPanel infos;
        private String[] nbjoueurs;
        private JTextField[] tNoms;
        private JTextField[] tAges;
        private JButton valider;

        public secondeFenetre() {
            this.infos = new JPanel(new GridBagLayout());
            this.valider = new JButton("Valider saisie");
            this.nbjoueurs = new String[]{"2", "3", "4"};
            this.pane = this.getContentPane();
            this.jbc = new JComboBox(nbjoueurs);
            this.jbc.setSelectedIndex(0);
            this.jbc.addActionListener(this);
            this.pane.setLayout(new GridLayout(5, 2));
            this.pane.add(new JLabel("<html> Choisissez le nombre de joueurs souhaité <br/> "
                    + "Donnez à chaque joueur un nom et un age (entrez un entier entre guillemets pour les ages dans la 2e ligne) <br/>"
                    + "Le domino se lancera lorsque tous les champs de nom seront différents de 'nom'<br/> "
                    + "Toute valeur d'age non valide sera interprétée comme 0 <html>"));
            this.pane.add(this.jbc);
            this.pane.add(this.infos);
            this.pane.add(this.valider);
            this.setTitle("Initialisation de la vue graphique du Domino");
            this.setPreferredSize(new Dimension(600, 400));
        }

        public boolean estCompletee() {
            for (JTextField j : tNoms) {
                if (j.getText().equals("nom")) {
                    return false;
                }
            }
            for (JTextField jA : tAges) {
                if (!jA.getText().matches("^[0-9]$")) {
                    return false;
                }
            }
            return true;
        }

        public void addInfosJoueurs(int i) {
            this.infos.removeAll();
            this.tNoms = new JTextField[i];
            this.tAges = new JTextField[i];
            for (int k = 0; k < i; k++) {
                this.tNoms[k] = new JTextField("nom");
                this.tAges[k] = new JTextField("age");
                //this.tNoms[k].setSize(200,24);
                JLabel jl = new JLabel("Joueur " + k);
                JPanel panj = new JPanel(new GridLayout(3, 4));
                panj.add(jl);
                panj.add(this.tNoms[k]);
                panj.add(this.tAges[k]);
                this.infos.add(panj);
            }
            this.infos.revalidate();
            this.infos.repaint();
            this.valider.addActionListener(event -> {
                if (this.estCompletee()) {
                    String[] inJ = new String[this.tNoms.length];
                    for (JTextField t : tNoms) {
                        System.out.println(t.getText());
                    }
                    String[] inA = new String[this.tAges.length];
                    for (JTextField t : tAges) {
                        System.out.println(t.getText());
                    }
                    for(int k=0; k<inJ.length;k++) {
                        inJ[k] = this.tNoms[k].getText();
                        inA[k] = this.tAges[k].getText();
                    }
                    this.setVisible(false);
                    this.dispose();
                    JeuDomino jD = new JeuDomino();
                    jD.initialiserViaTab(inJ, inA);
                    VueDomino vue;
                    try {
                        vue = new VueDomino(jD);
                    } catch (IOException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                        vue = null;
                    }
                    ControleurDomino cD = new ControleurDomino(vue);
                    try {
                        cD.jouerG();
                    } catch (IOException ex) {
                        Logger.getLogger(MenuPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("Impossible de valider saisie");
                }
            });
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox) e.getSource();
            String nb = (String) cb.getSelectedItem();
            if (nb.equals("2")) {
                this.addInfosJoueurs(2);
            }
            if (nb.equals("3")) {
                this.addInfosJoueurs(3);
            }
            if (nb.equals("4")) {
                this.addInfosJoueurs(4);
            }

        }

}
}*/
