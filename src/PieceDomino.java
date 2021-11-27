public class PieceDomino implements Piece {

    private final int e1;
    private final int e2;

    public PieceDomino(int e1, int e2) {
        if ((0 <= e1 && e1 <= 6) && (0 <= e2 && e2 <= 6)) {
            this.e1 = e1;
            this.e2 = e2;
        } else {
            this.e1 = -1;
            this.e2 = -1;
        }
    }

    @Override
    public String toString() {
        return "[" + e1 + "," + e2 + "] ";
    }

    @Override
    public int[] getNb() {
        int[] t = {this.e1, this.e2};
        return t;
    }
}
