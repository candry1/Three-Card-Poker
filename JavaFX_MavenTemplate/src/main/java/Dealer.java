import java.util.ArrayList; // import the ArrayList class

public class Dealer {
    public Deck theDeck;
    public ArrayList<Card> dealersHand;

    public Dealer(){
        this.theDeck = new Deck();
        this.dealersHand = dealHand();
    }

    public ArrayList<Card> dealHand(){
        ArrayList<Card> hand = new ArrayList<Card>();

        if(this.theDeck.d.size() <= 34) {
            this.theDeck = new Deck();
        }

        hand.add(this.theDeck.d.remove(0));
        hand.add(this.theDeck.d.remove(1));
        hand.add(this.theDeck.d.remove(2));

        return hand;
    }
}