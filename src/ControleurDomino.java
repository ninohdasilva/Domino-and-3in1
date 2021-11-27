
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ControleurDomino extends MouseAdapter implements MouseListener, MouseMotionListener {

    private final VueDomino vue;
    private final JeuDomino modele;
    private int x, y;
    private double xinit, yinit;

    public ControleurDomino(VueDomino v) {
        this.vue = v;
        this.modele = this.vue.getModele();

    }

    public void jouerG() throws IOException {
        vue.pack();
        vue.setSize(1000, 500);
        vue.setVisible(true);
        vue.setTitle("Domino");
        vue.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.vue.miseAJour(0);
        while (!this.modele.partieGagnee()) {
            this.vue.getLabelInfo().setText("<html>Au tour de <html>"+this.modele.getlistJ().get(this.modele.gettdj()).getNom()+ "<br/> Glissez-déposez vers le plateau la pièce que vous voulez placer <br/> Si vous ne pouvez pas jouer, cliquez sur le bouton à droite </html>");
        }
        for (int i = 0; i < this.modele.getlistJ().size(); i++) {
            if (this.modele.getlistJ().get(i).aGagne) {
                this.vue.getLabelInfo().setText(this.modele.getlistJ().get(i).getNom() + " a gagné!");
                JOptionPane.showMessageDialog(null,this.modele.getlistJ().get(i).getNom() + " a gagné!","FIN DE PARTIE : ",JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        this.x = e.getX();
        this.y = e.getY();
    }

    public void ajoutePanel(VueDomino.ImageDoPanel idp) {
        //On peut caster car on sait qu'on ne manipulera que des IDP (il n'y a pas autre chose)
        PieceDomino choix = this.vue.iDPToPieceDo(idp);
        if (this.modele.ajouter(choix)) {
            this.modele.getlistJ().get(this.modele.gettdj()).getPieces().remove(choix);
            //this.partieGagnee = this.modele.getlistJ().get(this.tourjoueur).actualiseAGagne();
            //this.partieGagnee = this.modele.partieGagnee();
            //System.out.println(this.modele.partieGagnee());
        } else {
            this.vue.getLabelInfo().setText("Choix invalide ou inexistant pour " + this.modele.getlistJ().get(this.modele.gettdj()).getNom() + ". Ce joueur pioche une carte au hasard dans le talon");
            if (!this.modele.pioche(this.modele.getlistJ().get(this.modele.gettdj()))){
                JOptionPane.showMessageDialog(null,"Plus de pièces dans le talon et aucun joueur n'a posé toutes ses pièces","FIN DE PARTIE : PARTIE NULLE ",JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            }
        }
        //System.out.println("Tour du joueur: " + this.tourjoueur);
        this.modele.inctdj();
        //System.out.println("Tour du joueur: " + this.tourjoueur);
        try {
            this.vue.miseAJour(this.modele.gettdj());
        } catch (IOException ex) {
            Logger.getLogger(ControleurDomino.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        //On peut caster car on sait qu'on ne manipulera que des IDP (il n'y a pas autre chose)
        VueDomino.ImageDoPanel idp = (VueDomino.ImageDoPanel) e.getComponent();
        int np = idp.getNumPanneauContenant();
        if (np == -1) {
            return; //On ne touche plus aux pièces déjà sur le plateau
        }
        this.xinit = e.getComponent().getLocation().getX();
        this.yinit = e.getComponent().getLocation().getY();
        e.getComponent().setLocation((e.getX() + e.getComponent().getX() - this.x), (e.getY() + e.getComponent().getY() - this.y));
        if (e.getYOnScreen() > this.vue.getLabelInfo().getHeight() && e.getYOnScreen() < this.vue.getPane().getHeight() - this.vue.getMDJ().getHeight()) {
            this.ajoutePanel(idp);
        } else {
            e.getComponent().setLocation((int) this.xinit, (int) this.yinit);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {
        //this.vue.getPlateau().add(e.getComponent());
        //this.vue.miseAJourPlateau();
    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {
    }

    @Override
    public void mouseExited(MouseEvent e
    ) {

    }

}
