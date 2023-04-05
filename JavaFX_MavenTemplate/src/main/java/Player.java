import java.util.ArrayList; // import the ArrayList class

public class Player {
    public ArrayList<Card> hand;
    public int anteBet;
    public int playBet;
    public int pairPlusBet;
    public int totalWinnings;

    public Player(){
        this.hand = new ArrayList<Card>();
        this.anteBet = 0;
        this.playBet = 0;
        this.pairPlusBet = 0;
        this.totalWinnings = 0;
    }
}