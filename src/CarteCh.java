public class CarteCh extends CarteSabo{

	private boolean[] c;
	private CarteCh[] d;

	public CarteCh(boolean a,boolean b,boolean c,boolean d){
		this.c=new boolean[4];
		this.c[0]=a;this.c[1]=b;this.c[2]=c;this.c[3]=d;
		this.d=new CarteCh[4];
	}

	public CarteCh(){
		this(false,false,false,false);
	}

	public boolean ajouter(int a,CarteCh ch){
		if(a>-1 && a<4 && this.c[a] && this.d[a]==null){	
			if(a==0 && ch.c[2]){this.d[a]=ch;ch.d[2]=this;return true;}
			if(a==1 && ch.c[3]){this.d[a]=ch;ch.d[3]=this;return true;}
			if(a==2 && ch.c[0]){this.d[a]=ch;ch.d[0]=this;return true;}
			if(a==3 && ch.c[1]){this.d[a]=ch;ch.d[1]=this;return true;}	
		}
		return false;
	}

	public void tourner(){//on tourne la carte
		for(int i=0;i<2;i++){
			boolean b1=this.c[1],c1=this.c[2],d1=this.c[3];
			this.c[1]=this.c[0];
			this.c[0]=d1;
			this.c[2]=b1;
			this.c[3]=c1;
		}
	}

	public boolean[] aideMJAP(){
		boolean[] t=new boolean[4];
		for(int i=0;i<4;i++){
			t[i]=(this.c[i] && (this.d[i]==null));
		}
		return t;
	}

	public String toString(){
		String s="";
		if(this.c[0]){
			s="-";
			if(this.c[1]){
				s+="'";
				if(this.c[3]) s="-|";
			} else {
				if(this.c[3]) s+=",";
			}
			if(c[2]) s+="-";
		} else{
			if(this.c[1]){
				s="'";
				if(this.c[3]) s="|";
			} else {
				if(this.c[3]) s=",";
			}
			if(this.c[2]) s+="-";
		}
		if(s.equals("")) s=".";
		return s;
	}

	

}