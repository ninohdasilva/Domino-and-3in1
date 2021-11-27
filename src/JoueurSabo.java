import java.util.ArrayList;

public class JoueurSabo{
	private String nom;
	private int age;
	private ArrayList<CarteCh> c;
	private ArrayList<CarteAc> a;
	private ArrayList<CarteAc> malus;

	public JoueurSabo(String nom,int a){
		this.nom=nom;this.age=a;
		this.c=new ArrayList<CarteCh>();
		this.a=new ArrayList<CarteAc>();
		this.malus=new ArrayList<CarteAc>();
	}
 
 	public void ajouter(CarteSabo c){
 		if(c instanceof CarteCh) this.c.add((CarteCh)c);
		else if(c instanceof CarteAc) this.a.add((CarteAc)c);
	}

	public void m(CarteAc act){
		boolean b=false; int i=0;
		String s=act.toString();
		if(s.charAt(1)=='c'){
			this.malus.add(act);
		} else{
			while(!b && i<this.malus.size()){
				if((this.malus.get(i).toString()).equals(act.toString().charAt(0)+"c")) {
					this.malus.remove(i); 
					b=true;
				}
				i++;
			}
		}
	}

	public CarteSabo joue(int i,int j,int k){
		CarteSabo p=null;
		if(i!=-1){
			if(i==0){
				if(this.malus.isEmpty()){
					if(k>-1 && k<c.size()) p=this.c.remove(k);
					else return this.joue(-1,-1,-1);
				}
				else{ 
					System.out.println("Penalite! Vous avez une carte malus, vous ne pouvez pas placer de carte chemin.");
					return this.joue(-1,-1,-1);
				}
			} else {
				if(k>-1 && k<a.size()) p=this.a.remove(k);
				else return this.joue(-1,-1,-1);
			}	
		} else {
			if(j==0 && k>-1 && k<c.size()) this.c.remove(k);
			if(j==1 && k>-1 && k<a.size()) this.a.remove(k);
			if(j==-1 || k<0 || k>=a.size() || k>=c.size()){//si le gars joue mal on le fait jeter aleatoirement une carte
				int al=(int)(Math.random()*2);//la formule c'est nbAleatoire=Min+(int)(Math.random()*((Max - Min)+1));
				if(al==0){
					if(this.c.isEmpty()) al=1;
					else this.c.remove((int)(Math.random()*this.c.size()));
				}
				if(al==1) {
					if(this.a.isEmpty()) this.c.remove((int)(Math.random()*this.c.size()));
					else this.a.remove((int)(Math.random()*this.a.size()));
				}
			}
		}
		return p;
	}

	public String getNom(){
		return this.nom;
	}

	public int[] getSize(){
		int[] t={this.c.size(),this.a.size()};
		return t;
	}

	public String toString(){
		String s=this.nom+"\n0) ";
		for(int i=0;i<this.c.size();i++){
			s+=this.c.get(i).toString()+" ";
		}
		s+=" 1) ";
		for(int i=0;i<this.a.size();i++){
			s+=this.a.get(i).toString()+" ";
		}
		s+=" 2) ";
		for(int i=0;i<this.malus.size();i++){
			s+=this.malus.get(i).toString()+" ";
		}
		return s;
	}

	public boolean plusGrand(JoueurSabo j){
		return this.age>j.age;
	}
}