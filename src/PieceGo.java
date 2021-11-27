public class PieceGo implements Piece {

    private final int e1;
    private final int e2;
    private final String couleur1;
    private final String couleur2;

    public PieceGo(int e1, int e2, String c1, String c2) {
        if ((0 <= e1 && e1 <= 6) && (0 <= e2 && e2 <= 6)) {
            this.e1 = e1;
            this.e2 = e2;
        } else {
            this.e1 = -1;
            this.e2 = -1;
        }
        this.couleur1 = c1;
        this.couleur2 = c2;
    }

    @Override
    public String toString() {
        String s = "[";
        switch (this.e1) {
            case 0:
                s += "*" + this.couleur1 + ",";
                break;

            case 1:
                s += "&" + this.couleur1 + ",";
                break;

            case 2:
                s += "#" + this.couleur1 + ",";
                break;

            case 3:
                s += "@" + this.couleur1 + ",";
                break;

            case 4:
                s += "$" + this.couleur1 + ",";
                break;

            case 5:
                s += "µ" + this.couleur1 + ",";
                break;

            case 6:
                s += "§" + this.couleur1 + ",";
                break;
        }
        switch (this.e2) {
            case 0:
                s += "*" + this.couleur2 + "]";
                break;

            case 1:
                s += "&" + this.couleur2 + "]";
                break;

            case 2:
                s += "#" + this.couleur2 + "]";
                break;

            case 3:
                s += "@" + this.couleur2 + "]";
                break;

            case 4:
                s += "$" + this.couleur2 + "]";
                break;

            case 5:
                s += "µ" + this.couleur2 + "]";
                break;

            case 6:
                s += "§" + this.couleur2 + "]";
                break;
        }
        return s;
    }

    public String[] getC() {
        String[] t = {this.couleur1, this.couleur2};
        return t;
    }

    @Override
    public int[] getNb() {
        int[] t = {this.e1, this.e2};
        return t;
    }

}
