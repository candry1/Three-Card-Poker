import java.util.ArrayList; // import the ArrayList class
import java.lang.Math;

public class Deck extends ArrayList<Card>{
    public ArrayList<Card> d;

    public Deck(){
        d = new ArrayList<Card>();

        for(int i = 0; i < 52; i++) {
            //create a card
            int suitRandom = (int) Math.floor(Math.random() * 4);
            int value = (int) (Math.floor(Math.random() * 13) + 2);
            char suit = '/';

            if(suitRandom == 0){ suit = 'C';
            } else if(suitRandom == 1){ suit = 'D';
            } else if(suitRandom == 2){ suit = 'H';
            } else if(suitRandom == 3){ suit = 'S';
            }
            Card card = new Card(suit, value);

            //check if card is in deck or not
            int j = 0;
            boolean contains = false;

            while(j < d.size() && contains != true){
                if(d.get(j).suit == card.suit && d.get(j).value == card.value){
                    contains = true;
                }
                j++;
            }

            while(contains == true){
                suitRandom = (int) Math.floor(Math.random() * 4);
                value =  (int) (Math.floor(Math.random() * 13) + 2);

                if(suitRandom == 0){ suit = 'C';
                } else if(suitRandom == 1){ suit = 'D';
                } else if(suitRandom == 2){ suit = 'H';
                } else if(suitRandom == 3){ suit = 'S';
                }

                card = new Card(suit, value);


                j = 0;
                contains = false;
                while(j < d.size() && contains == false){
                    if(d.get(j).suit == card.suit && d.get(j).value == card.value){
                        contains = true;
                    }
                    j++;
                }
            }
            d.add(card);
        }
    }

    public Deck newDeck(){
        Deck newDeck = new Deck();
        return newDeck;
    }
}