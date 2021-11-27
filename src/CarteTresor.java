public class CarteTresor extends CarteCh {
	private boolean tresor;

	public CarteTresor(boolean e){
		super(true,true,true,true);
		this.tresor=e;
	}

	public boolean getTresor(){
		return this.tresor;
	}

}