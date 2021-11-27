import java.util.Scanner;

public class Demande{
	private static String nom="";
	private static int nbErreur=0;
	private static int c=0;
	public static Scanner sc = new Scanner(System.in);

	public static String[][] demJ(){
		System.out.println("Nombre de joueurs?(2,3 ou 4)");
		while(true){
			try{
				c=sc.nextInt();
				if (c==2 || c==3 || c==4) break;
				else System.out.println("Saisie invalide, veuillez entrer le chiffre 2, 3 ou 4");
			} catch(Exception a) {System.out.println("Saisie invalide, veuillez entrer le chiffre 2, 3 ou 4");sc.next();}//mdr t un douilleur
		}
		String[][] t=new String[c][2];//voir et gerer exceptions
		//2e Scanner ici car sinon l'affichage pour le nom et l'âge s'enchaîne sans laisser le temps de taper 
		Scanner sc2=new Scanner(System.in);int i=0;
		while(i<t.length){
			System.out.println("Donnez un nom pour le joueur " + (i+1));
			t[i][0]=sc2.nextLine();
			System.out.println("Donnez son âge");
			while(true){
				try{
					c=sc.nextInt();
					if(c>0) break;
					else System.out.println("Saisie invalide, veuillez entrer un entier strictement supérieur à 0");
				} catch(Exception a) {System.out.println("Saisie invalide, veuillez entrer un entier strictement supérieur à 0");sc.next();}
			}
			t[i][1]=String.valueOf(c);
			i++;
		}
		return t;
	}

	public static int demP(String g){
		if(g.equals(nom)) nbErreur++;
		else nbErreur=0;
		System.out.println("Au tour de "+g);
		System.out.println("Donnez un numéro de piece (tapez -1 si vous ne pouvez pas jouer)");
		try{
			c=sc.nextInt();
		} catch(Exception a){
			nom=g;
			if(nbErreur!=2) return demP(g);
			else return -1;
		}
		return c;
	}

	public static int[] demCoord(){
		int[] t={-1,-1};
		Scanner sc=new Scanner(System.in);
		System.out.println("donner un numero de ligne :");
		do{
			try{
				t[0]=sc.nextInt();
			} catch(Exception a){System.out.println("donner un numero de ligne correct :");}
		} while(t[0]<0 || t[0]>12);
		System.out.println("donner un numero de colone :");
		do{
			try{
				t[1]=sc.nextInt();
			} catch(Exception a){System.out.println("donner un numero de ligne correct :");}
		} while(t[1]<0 || t[1]>12);
		return t;
	}

	public static int[] demPS(String g,int[] tab){
		int[] t={-1,-1,-1};
		System.out.println("Au tour de "+g);
		System.out.println("Vous voulez : 0)placer une carte chemin 1)jouer une carte action -1)jeter une carte");
		nbErreur=0;
		while(nbErreur<3){
			try{
				c=sc.nextInt();
				if(c==0 || c==1 || c==-1) break;
				else{
					nbErreur++;
					System.out.print("Saisie invalide");
					if(nbErreur<3){
						System.out.print(", veuillez entrer -1, 0 ou 1");   
						if(nbErreur==2) System.out.print(" (c'est votre dernière chance)");
					}
					System.out.println();
				} 
			} catch(Exception a) {
				nbErreur++;
					System.out.print("Saisie invalide");
					if(nbErreur<3){
						System.out.print(", veuillez entrer -1, 0 ou 1");   
						if(nbErreur==2) System.out.print(" (c'est votre dernière chance)");
					}
					System.out.println();
				sc.next();
			}
		}
		if(nbErreur!=3)	t[0]=c;
		if(c==-1){
			System.out.println("Vous voulez jeter : 0)une carte chemin 1)une carte action");
			nbErreur=0;
			while(nbErreur<3){
				try{
					c=sc.nextInt();
					if(c==0 || c==1) break;
					else{
						nbErreur++;
						System.out.print("Saisie invalide");
						if(nbErreur<3){
							System.out.print(", veuillez entrer 0 ou 1");   
							if(nbErreur==2) System.out.print(" (c'est votre dernière chance)");
						}
						System.out.println();	
					} 
				} catch(Exception a) {
					nbErreur++;
					System.out.print("Saisie invalide");
					if(nbErreur<3){
						System.out.print(", veuillez entrer 0 ou 1");   
						if(nbErreur==2) System.out.print(" (c'est votre dernière chance)");
					}
					System.out.println();
					sc.next();
				}
			}
		}
		if(nbErreur!=3){
			t[1]=c;
			System.out.println("veuillez choisir une carte :");
			nbErreur=0;
			while(nbErreur<3){
				try{
					c=sc.nextInt();
					if(t[0]==0){
						if(c>-1 && c<tab[0]) break;
						else{
							nbErreur++;
							System.out.print("Saisie invalide");
							if(nbErreur<3){
								System.out.print(", veuillez entrer un nombre compris entre 0 et "+(tab[0]-1));   
								if(nbErreur==2) System.out.print(" (c'est votre dernière chance)");
							}
							System.out.println();
						}
					} 
					if(t[0]==1){
						if(c>-1 && c<tab[1]) break;
						else{
							nbErreur++;
							System.out.print("Saisie invalide");
							if(nbErreur<3){
								System.out.print(", veuillez entrer un nombre compris entre 0 et "+(tab[1]-1));   
								if(nbErreur==2) System.out.print(" (c'est votre dernière chance)");
							}
							System.out.println();
						}
					} 
					if(t[0]==-1){
						if(t[1]==0){
							if(c>-1 && c<tab[0]) break;
							else{
								nbErreur++;
								System.out.print("Saisie invalide");
								if(nbErreur<3){
									System.out.print(", veuillez entrer un nombre compris entre 0 et "+(tab[0]-1));   
									if(nbErreur==2) System.out.print(" (c'est votre dernière chance)");
								}
								System.out.println();
							}
						} 
						if(t[1]==1){
							if(c>-1 && c<tab[1]) break;
							else{
								nbErreur++;
								System.out.print("Saisie invalide");
								if(nbErreur<3){
									System.out.print(", veuillez entrer un nombre compris entre 0 et "+(tab[1]-1));   
									if(nbErreur==2) System.out.print(" (c'est votre dernière chance)");
								}
								System.out.println();
							}
						}
					} 
				} catch(Exception a) {
					nbErreur++;
							System.out.print("Saisie invalide");
							if(nbErreur<3){
								System.out.print(", veuillez entrer un nombre correct");   
								if(nbErreur==2) System.out.print(" (c'est votre dernière chance)");
							}
							System.out.println();
					sc.next();
				}
			} 
		}
		if(nbErreur!=3) t[2]=c;
		return t;
	}

	public static int malus(String[] t){
		int i=0;
		System.out.println("Vers qui voulez-vous placer cette carte?");
		for(String j : t){ System.out.print(i+")"+j+" ");i++;}
		System.out.println();
		Scanner sc=new Scanner(System.in);
		try{
			i=sc.nextInt();
		} catch(Exception a){
			System.out.println("veuillez saisir un nombre compris entre 0 et "+(t.length-1));
			malus(t);	
		}
		if(i>-1 && i<t.length) return i;
		else {
			System.out.println("veuillez saisir un nombre compris entre 0 et "+(t.length-1));
			return malus(t);
		}
	} 

}
