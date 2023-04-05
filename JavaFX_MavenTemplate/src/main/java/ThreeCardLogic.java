import java.util.ArrayList; // import the ArrayList class

public class ThreeCardLogic {
    public static int evalHand(ArrayList<Card> hand){
        int firstValue = hand.get(0).value;
        char firstSuit = hand.get(0).suit;
        int pairCount = 0;
        boolean threeOfAKind = true;
        boolean flush = true;
        boolean straight = true;

        for(int i = 1; i < hand.size(); i++){
            if(hand.get(i).value != firstValue){
                threeOfAKind = false;
                if(hand.get(i).value != firstValue + 2 &&
                   hand.get(i).value != firstValue + 1 &&
                   hand.get(i).value != firstValue - 2 &&
                   hand.get(i).value != firstValue - 1){
                    straight = false;
                }
            } else{
                pairCount++;
            }

            if(hand.get(i).suit != firstSuit){
                flush = false;
            }
        }

        if(hand.get(0) == hand.get(1) || hand.get(1) == hand.get(2) || hand.get(0) == hand.get(2)){
            pairCount = 1;
        }

        if(straight && flush & pairCount == 0){
            System.out.println("  straight flush found!");
            return 1;
        } else if(threeOfAKind){
            System.out.println("  three of a kind found!");
            return 2;
        } else if(straight && pairCount == 0){
            System.out.println("  straight found!");
            return 3;
        } else if(flush){
            System.out.println("  flush found!");
            return 4;
        } else if(pairCount >= 1){
            System.out.println("  pair found!: " + pairCount);
            return 5;
        } else{
            System.out.println("  nothing found");
            return 0;
        }
    }

    public static int evalPPWinnings(ArrayList<Card> hand, int bet){
        int winningCombo = evalHand(hand);
        if(winningCombo != 0){
            if(winningCombo == 1){ return 40 * bet;
            } else if(winningCombo == 2){ return 30 * bet;
            } else if(winningCombo == 3){ return 6 * bet;
            } else if(winningCombo == 4){ return 3 * bet;
            } else { return bet;
            }
        } else{
            return 0;
        }
    }

    public static int compareHands(ArrayList<Card> dealer, ArrayList<Card> player){
        System.out.println("dealer:");
        int winningComboDealer = evalHand(dealer);
        System.out.println(winningComboDealer);
        System.out.println("player:");
        int winningComboPlayer = evalHand(player);
        System.out.println(winningComboPlayer);

        //if on eof our players in 0, and the other isn't that one wins!!
        if(winningComboPlayer == 0 && winningComboDealer != 0){     //if one is 0 and the other isn't the other wins
            return 1;
        } else if(winningComboDealer == 0 && winningComboPlayer != 0){     //if one is 0 and the other isn't the other wins
            return 2;
        } else if(winningComboDealer < winningComboPlayer){    //both have a value != 0; dealer wins
            return 1;
        } else if(winningComboDealer > winningComboPlayer){     //both have a value != 0; player wins
            return 2;
        } else{ //both == 0 ONLY
            return 0;
        }
    }
}