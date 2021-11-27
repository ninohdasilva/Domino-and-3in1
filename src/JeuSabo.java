import java.util.ArrayList;
import java.util.Random;


public class JeuSabo{
	private CarteCh[][] plateau;
	private boolean[][] boolP;
	private ArrayList<JoueurSabo> listJ;
	private ArrayList<CarteSabo> talon;
	static Random r = new Random();
	static int nbTour=0;
	static boolean[] booleanTr={false,false};

	public JeuSabo(){
		this.plateau=new CarteCh[15][15];
		this.boolP=new boolean[15][15];
		for(int i=0;i<this.plateau.length;i++) for(int j=0;j<this.plateau[i].length;j++) this.plateau[i][j]=new CarteCh();
		this.listJ=new ArrayList<JoueurSabo>();
		this.talon=new ArrayList<CarteSabo>();
	}

	public void initialiser(){
		for(int i=0;i<5;i++){
			//les cartes chemains
			this.talon.add(new CarteCh(false,true,true,true));
			this.talon.add(new CarteCh(true,false,false,false));
			this.talon.add(new CarteCh(false,false,true,true));
			this.talon.add(new CarteCh(false,true,false,false));
			this.talon.add(new CarteCh(false,false,true,false));
			this.talon.add(new CarteCh(true,true,true,false));
			this.talon.add(new CarteCh(true,true,true,true));
			this.talon.add(new CarteCh(true,false,true,false));
			this.talon.add(new CarteCh(false,true,false,true));
			//les cartes actions
			if(i<4){
				this.talon.add(new CarteAc("pr"));// pr > pioche reparée
				this.talon.add(new CarteAc("pc"));// pc > pioche cassée
				this.talon.add(new CarteAc("lr"));// le > lanterne réparée
				this.talon.add(new CarteAc("lc"));// lc > lanterne cassée
				this.talon.add(new CarteAc("cr"));// cr > chariot réparée
				this.talon.add(new CarteAc("cc"));// cc > chariot cassée
			}
		}
		//les joueurs
		String[][] t=Demande.demJ();
		for(int i=0;i<t.length;i++ ){
			this.listJ.add(new JoueurSabo(t[i][0],Integer.valueOf(t[i][1])));
		} 
		for(int j=0;j<this.listJ.size()-1;j++){
			for(int i=1;i<this.listJ.size();i++){
				if(this.listJ.get(i).plusGrand(this.listJ.get(i-1))){
					JoueurSabo a=this.listJ.set(i-1,this.listJ.get(i));
					this.listJ.set(i,a);
				}
			}
		}
		for(int i=0;i<5;i++) for(JoueurSabo j : this.listJ) this.pioche(j);
		//on place les cartes
		this.plateau[7][3]=new CarteCh(true,true,true,true);
		ArrayList<CarteCh> a=new ArrayList<CarteCh>();
		a.add(new CarteTresor(true));
		a.add(new CarteTresor(false));
		a.add(new CarteTresor(false));
		this.plateau[5][11]=a.remove(r.nextInt(a.size()));
		this.plateau[7][11]=a.remove(r.nextInt(a.size()));
		this.plateau[9][11]=a.remove(r.nextInt(a.size()));
	}

	public void afficher(){
		System.out.println("Tour numero "+nbTour);
		for(JoueurSabo i : this.listJ) System.out.println(i);
			System.out.println(); System.out.print("  ");
		for(int i=1;i<this.plateau.length-1;i++) System.out.print(i+"  ");
			System.out.println("\n");
		for(int i=1;i<this.plateau.length-1;i++){
			System.out.print(i+"  ");
			for(int j=1;j<this.plateau[i].length-1;j++){
				System.out.print(this.plateau[i][j]+"  ");
			}
			System.out.println("\n");
		}
	}	

	public void ajouter(CarteCh c){// l'erreur est entre ici ...
		this.mJAP();
		int[] t={};
		if(c!=null){
			do{
				t=Demande.demCoord();
			}while(!(t[0]>0 && t[0]<this.plateau.length-1 && t[1]>0 && t[1]<this.plateau[0].length-1 && boolP[t[0]][t[1]]));
			
			if(this.plateau[t[0]][t[1]-1]!=null){
				if(this.plateau[t[0]][t[1]-1].ajouter(2,c)){
					this.plateau[t[0]][t[1]]=c;
					booleanTr[0]=this.plateau[t[0]][t[1]-1] instanceof CarteTresor; 
					if(booleanTr[0]){
						booleanTr[1]=((CarteTresor)(this.plateau[t[0]][t[1]-1])).getTresor();
						if(!booleanTr[1]){
							this.plateau[t[0]][t[1]-1]=new CarteCh(true,true,true,true);
						}
					}
				} else {
					c.tourner();
					if(this.plateau[t[0]][t[1]-1].ajouter(2,c)){
						this.plateau[t[0]][t[1]]=c;
						booleanTr[0]=this.plateau[t[0]][t[1]-1] instanceof CarteTresor;
						if(booleanTr[0]){
							booleanTr[1]=((CarteTresor)(this.plateau[t[0]][t[1]-1])).getTresor();
							if(!booleanTr[1]){
								this.plateau[t[0]][t[1]-1]=new CarteCh(true,true,true,true);
							}
						}
					}
				}
			}
			if(this.plateau[t[0]-1][t[1]]!=null){
				if(this.plateau[t[0]-1][t[1]].ajouter(3,c)){
					this.plateau[t[0]][t[1]]=c;
					booleanTr[0]=this.plateau[t[0]-1][t[1]] instanceof CarteTresor;
					if(booleanTr[0]){
						booleanTr[1]=((CarteTresor)(this.plateau[t[0]-1][t[1]])).getTresor();
						if(!booleanTr[1]){
							this.plateau[t[0]-1][t[1]]=new CarteCh(true,true,true,true);
						}
					}
				} 
				else {
					c.tourner();
					if(this.plateau[t[0]-1][t[1]].ajouter(3,c)){
						this.plateau[t[0]][t[1]]=c;
						booleanTr[0]=this.plateau[t[0]-1][t[1]] instanceof CarteTresor; 
						if(booleanTr[0]){
							booleanTr[1]=((CarteTresor)(this.plateau[t[0]-1][t[1]])).getTresor();
							if(!booleanTr[1]){
								this.plateau[t[0]-1][t[1]]=new CarteCh(true,true,true,true);
							}
						}
					}
				}
			}
			if(this.plateau[t[0]][t[1]+1]!=null){
				if(this.plateau[t[0]][t[1]+1].ajouter(0,c)){
					this.plateau[t[0]][t[1]]=c;
					booleanTr[0]=this.plateau[t[0]][t[1]+1] instanceof CarteTresor; 
					if(booleanTr[0]){
						booleanTr[1]=((CarteTresor)(this.plateau[t[0]][t[1]+1])).getTresor();
						if(!booleanTr[1]){
							this.plateau[t[0]][t[1]+1]=new CarteCh(true,true,true,true);
						}
					}
				}
				else {
					c.tourner();
					if(this.plateau[t[0]][t[1]+1].ajouter(0,c)){
						this.plateau[t[0]][t[1]]=c;
						booleanTr[0]=this.plateau[t[0]][t[1]+1] instanceof CarteTresor;
						if(booleanTr[0]){
							booleanTr[1]=((CarteTresor)(this.plateau[t[0]][t[1]+1])).getTresor();
							if(!booleanTr[1]){
								this.plateau[t[0]][t[1]+1]=new CarteCh(true,true,true,true);
							}
						} 
					}
				}
			}
			if(this.plateau[t[0]+1][t[1]]!=null){ 
				if(this.plateau[t[0]+1][t[1]].ajouter(1,c)){
					this.plateau[t[0]][t[1]]=c;
					booleanTr[0]=this.plateau[t[0]+1][t[1]] instanceof CarteTresor;
					if(booleanTr[0]){
						booleanTr[1]=((CarteTresor)(this.plateau[t[0]+1][t[1]])).getTresor();
						if(!booleanTr[1]){
							this.plateau[t[0]+1][t[1]]=new CarteCh(true,true,true,true);
						}
					}
				}
				else {
					c.tourner();
					if(this.plateau[t[0]][t[1]+1].ajouter(1,c)){
						this.plateau[t[0]][t[1]]=c;
						booleanTr[0]=this.plateau[t[0]+1][t[1]] instanceof CarteTresor;
						if(booleanTr[0]){
							booleanTr[1]=((CarteTresor)(this.plateau[t[0]+1][t[1]])).getTresor();
							if(!booleanTr[1]){
								this.plateau[t[0]+1][t[1]]=new CarteCh(true,true,true,true);
							}
						}
					}
				}
			} 
		}
	} // ... là

	public void mJAP(){// mise a jour boolP
		for(int i=0;i<this.plateau.length;i++){
			for(int j=0;j<this.plateau[i].length;j++){
				if(this.plateau[i][j]!=null && !(this.plateau[i][j] instanceof CarteTresor)){
					boolean[] b=this.plateau[i][j].aideMJAP();
					if(b[0]) this.boolP[i][j-1]=true;
					if(b[1]) this.boolP[i-1][j]=true;
					if(b[2]) this.boolP[i][j+1]=true;
					if(b[3]) this.boolP[i+1][j]=true;
				}
			}
		}
	}

	public void pioche(JoueurSabo jouDo){
        if(this.talon.size()!=0) jouDo.ajouter(this.talon.remove(r.nextInt(this.talon.size())));
    }

    public boolean jouerTour(){
    	for(JoueurSabo j : this.listJ){//une boucle while serait meilleur
    		this.afficher();
    		int[] t=Demande.demPS(j.getNom(),j.getSize());
    		CarteSabo cs=j.joue(t[0],t[1],t[2]);
    		if(cs instanceof CarteCh){
    			this.ajouter((CarteCh)cs);
    			if(booleanTr[0] && booleanTr[1]) {System.out.println(j.getNom()+" a revele une carte tresor et gagne ! Felicitaions !"); return true;}
    			else if(booleanTr[0]) System.out.println(j.getNom()+" a revele une carte tresor mais il n'y a pas d'or. La partie continue");
    		} else if(cs instanceof CarteAc){
    			String[] tab=new String[this.listJ.size()];
    			for(int i=0;i<tab.length;i++) tab[i]=this.listJ.get(i).getNom();
    			this.listJ.get(Demande.malus(tab)).m((CarteAc)cs);
    		}
    		this.pioche(j);
    	}
    	return false;
    }

    public void jouer(){
    	this.initialiser();
    	boolean b=false;
    	do{
    		nbTour++;
    		if(nbTour==30){
    			System.out.println("------------  Dernier tour -----------");
    		}
    		b=this.jouerTour();
    	} while(nbTour<30 && !b);
    	if(nbTour==30 && !b) System.out.println("----------- Match null -----------");
    	System.out.println("------------   Fin de la partie  -----------");
    }


}