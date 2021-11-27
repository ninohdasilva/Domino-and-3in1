
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

public class VueDomino extends JFrame {

    private final Container pane;
    private final JLabel labelInfo;
    private final JPanel panneauInfoControle;
    private final JPanel plateau;
    private final JButton boutonPioche;
    private final JeuDomino modele;
    private final JPanel[] mainDesJoueurs;
    private final JPanel mDJ;

    public VueDomino(JeuDomino jD) throws IOException {
        this.pane = this.getContentPane();
        this.plateau = new JPanel();
        //this.plateau.setBackground(c1);
        this.modele = jD;
        this.mainDesJoueurs = new JPanel[modele.getlistJ().size()];
        for (int i = 0; i < this.mainDesJoueurs.length; i++) {
            mainDesJoueurs[i] = new JPanel();
        }
        this.mDJ = new JPanel(new GridLayout(1, this.mainDesJoueurs.length));
        for (int i = 0; i < this.mainDesJoueurs.length; i++) {
            TitledBorder border = new TitledBorder(this.modele.getlistJ().get(i).getNom());
            border.setTitleJustification(TitledBorder.CENTER);
            this.mainDesJoueurs[i].setBorder(border);
            for (PieceDomino p : this.modele.getlistJ().get(i).getPieces()) {
                ImageDoPanel idp = pieceDoToIDP(p);
                idp.setNumPanneauContenant(i);
                this.mainDesJoueurs[i].add(idp);
            }
            JScrollPane sP = new JScrollPane(this.mainDesJoueurs[i], ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            mDJ.add(sP);
        }
        JScrollPane pS = new JScrollPane(this.plateau, ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        this.mDJ.setPreferredSize(new Dimension(this.pane.getWidth(), 150));
        this.labelInfo = new JLabel();
        this.labelInfo.setVerticalAlignment(JLabel.CENTER);
        this.labelInfo.setHorizontalAlignment(JLabel.CENTER);
        this.panneauInfoControle = new JPanel();
        this.panneauInfoControle.setPreferredSize(new Dimension(this.pane.getWidth(), 150));
        this.panneauInfoControle.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        this.panneauInfoControle.add(this.labelInfo);
        this.boutonPioche = new JButton("Pioche (passe le tour de ce joueur)");
        this.boutonPioche.addActionListener(event -> {
            if (!this.modele.pioche(this.modele.getlistJ().get(this.modele.gettdj()))) {
                JOptionPane.showMessageDialog(null, "Plus de pièces dans le talon et aucun joueur n'a posé toutes ses pièces", "FIN DE PARTIE : PARTIE NULLE ", JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
            this.labelInfo.setText(this.modele.getlistJ().get(this.modele.gettdj()).getNom() + " a pioché une pièce au hasard dans le talon.\n");
            this.modele.inctdj();
            this.labelInfo.setText(this.labelInfo.getText() + "Au tour de " + this.modele.getlistJ().get(this.modele.gettdj()).getNom());
            try {
                this.miseAJour(this.modele.gettdj());
            } catch (IOException ex) {
                Logger.getLogger(VueDomino.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        this.panneauInfoControle.add(boutonPioche);
        this.pane.add(this.panneauInfoControle, BorderLayout.NORTH);
        this.pane.add(pS, BorderLayout.CENTER);
        this.pane.add(this.mDJ, BorderLayout.SOUTH);
        this.setSize(1000, 500);
        this.setVisible(true);
        this.setTitle("Domino");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    public Container getPane() {
        return this.pane;
    }

    public JPanel getPlateau() {
        return this.plateau;
    }

    public JLabel getLabelInfo() {
        return this.labelInfo;
    }

    public JPanel getMDJ() {
        return this.mDJ;
    }

    public JeuDomino getModele() {
        return this.modele;
    }

    public void miseAJour(int i) throws IOException {
        this.plateau.removeAll();
        for (JPanel pan : this.mainDesJoueurs) {
            pan.removeAll();
        }
        this.rafraichirPlateau();
        this.rafraichirMainDesJoueurs(i);
        this.pane.repaint();

    }

    public void rafraichirPlateau() throws IOException {
        for (PieceDomino piece : this.modele.getPlateau()) {
            this.plateau.add(pieceDoToIDPPlateau(piece));
        }
        this.plateau.revalidate();
        this.plateau.repaint();
    }

    public void rafraichirMainDesJoueurs(int i) throws IOException {
        for (PieceDomino piece : this.modele.getlistJ().get(i).getPieces()) {
            ImageDoPanel idp = pieceDoToIDP(piece);
            idp.setNumPanneauContenant(i);
            this.mainDesJoueurs[i].add(idp);

        }
        this.mainDesJoueurs[i].revalidate();
        this.mainDesJoueurs[i].repaint();
    }

    //pour éviter "Exporting non-public type through public API" Netbeans propose entre autres de passer la méthode en private. A voir
    //Version pour les mains des joueurs (tout vertical);
    public ImageDoPanel pieceDoToIDP(PieceDomino p) throws IOException {
        ImageDoPanel idp;
        String path;
        int[] t = p.getNb();
        path = "../img/croppedresized/vertical/domino" + String.valueOf(t[0]) + String.valueOf(t[1]) + "cropped.png";
        idp = new ImageDoPanel(path, p);
        return idp;
    }

    public ImageDoPanel pieceDoToIDPPlateau(PieceDomino p) throws IOException {
        ImageDoPanel idp;
        String path;
        int[] t = p.getNb();
        if (t[0] == t[1]) {
            path = "../img/croppedresized/vertical/domino" + String.valueOf(t[0]) + String.valueOf(t[1]) + "cropped.png";
            idp = new ImageDoPanel(path, p);
        } else {
            path = "../img/croppedresized/horizontal/domino" + String.valueOf(t[0]) + String.valueOf(t[1]) + "croppedhor.png";
            idp = new ImageDoPanel(path, p);
        }
        idp.setNumPanneauContenant(-1);
        return idp;
    }

    public PieceDomino iDPToPieceDo(ImageDoPanel idp) {
        return idp.getPieceDomino();
    }

    class ImageDoPanel extends JPanel {

        private final BufferedImage im;
        private final File f;
        private final PieceDomino p;
        private int numPanneauContenant; //-1 pour le plateau, O pour le joueur 1 etc..

        public ImageDoPanel(String path, PieceDomino piece) throws IOException {
            this.f = new File(path);
            this.im = ImageIO.read(f);
            this.p = piece;
            addMouseListener(new ControleurDomino(VueDomino.this));
            addMouseMotionListener(new ControleurDomino(VueDomino.this));
            this.setPreferredSize(new Dimension(im.getWidth(), im.getHeight()));
        }

        public void setNumPanneauContenant(int i) {
            numPanneauContenant = i;
        }

        public int getNumPanneauContenant() {
            return numPanneauContenant;
        }

        public String getTitrePanneauContenant(int i) {
            if (i >= 0) {
                JPanel jp = VueDomino.this.mainDesJoueurs[i];
                return ((TitledBorder) jp.getBorder()).getTitle();
            }
            return "";
        }

        public File getFile() {
            return this.f;
        }

        public PieceDomino getPieceDomino() {
            return this.p;
        }

        public BufferedImage getImage() {
            return this.im;
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.drawImage(im, 0, 0, this);
        }
    }

}
