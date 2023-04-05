import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.ArrayList; // import the ArrayList class

class MyTest {

	boolean containsCard(Deck d, Card c){
		int i = 0;
		boolean contains = false;

		while(i < d.d.size() && !contains){
			if(d.d.get(i).suit == c.suit && d.d.get(i).value == c.value){
				contains = true;
			}
			i++;
		}

		return contains;
	}

	@Test
	void testCardSuit() {
		Card card = new Card('C', 11);
		assertEquals(card.suit, 'C', "suit isn't matching");
	}

	@Test
	void testCardValue() {
		Card card = new Card('C', 11);
		assertEquals(card.value, 11, "value isn't matching");
	}

	@Test
	void testCardSuit2() {
		Card card = new Card('S', 14);
		assertEquals(card.suit, 'S', "suit isn't matching");
	}

	@Test
	void testCardValue2() {
		Card card = new Card('C', 14);
		assertEquals(card.value, 14, "value isn't matching");
	}





	//10+ Deck and Dealer Tests
	@Test //deck shuffled? check at random places
	void testDeck() {
		Deck deck = new Deck();
		Deck deck2 = new Deck();
		boolean shuffled = false;

		if(deck.d.get(0) != deck2.d.get(0) && deck.d.get(10) != deck2.d.get(10) &&
		   deck.d.get(20) != deck2.d.get(20) && deck.d.get(30) != deck2.d.get(30) &&
		   deck.d.get(40) != deck2.d.get(40) && deck.d.get(50) != deck2.d.get(50)){
			shuffled = true;
		}

		assertEquals(shuffled, true, "we're not fully random");
	}

	@Test //deck shuffled? check at random places
	void testDeck2() {
		Deck deck = new Deck();
		Deck deck2 = new Deck();
		boolean shuffled = false;

		if(deck.d.get(1) != deck2.d.get(1) && deck.d.get(11) != deck2.d.get(11) &&
				deck.d.get(21) != deck2.d.get(21) && deck.d.get(31) != deck2.d.get(31) &&
				deck.d.get(41) != deck2.d.get(41) && deck.d.get(51) != deck2.d.get(51)){
			shuffled = true;
		}

		assertEquals(shuffled, true, "we're not fully random");
	}

	@Test //all cards accounted for?
	void testDeck3() {
		Deck deck = new Deck();

		assertEquals(deck.d.size(), 52, "not enough cards present");
	}

	@Test//----------------------------------------------------------------------------------------------
	void testDeck4() {	//full deck represented?
		Deck deck = new Deck();
		boolean containsAllCards = true;
		ArrayList<Character> suits = new ArrayList<Character>() {
			{
				add('C');
				add('H');
				add('S');
				add('D');
			}
		};
		ArrayList<Integer> values = new ArrayList<Integer>() {
			{
				add(2);
				add(3);
				add(4);
				add(5);
				add(6);
				add(7);
				add(8);
				add(9);
				add(10);
				add(11);
				add(12);
				add(13);
				add(14);
			}
		};

		int missingCOunt = 0;
		for(int i = 0; i < suits.size(); i++){
			for(int j = 0; j < values.size(); j++){
				Card card = new Card(suits.get(i), values.get(j));
				if(containsCard(deck, card) == false){		//contains doesn't work for type card??
					containsAllCards = false;
					missingCOunt++;
				}
			}
		}

		assertEquals(containsAllCards, true, "this deck doesn't contain " + missingCOunt + " cards");
	}

	@Test //no random cards?
	void testDeck5() {
		Deck deck = new Deck();
		assertEquals(containsCard(deck, new Card('Z', 16)), false, "this card SHOULD not exist");
	}

	@Test
	void testNewDeck() {	//newDeck working correctly?
		Deck deck = new Deck();
		Deck deck2 = deck.newDeck();
		boolean shuffled = false;

		if(deck.d.get(0) != deck2.d.get(0) && deck.d.get(10) != deck2.d.get(10) &&
				deck.d.get(20) != deck2.d.get(20) && deck.d.get(30) != deck2.d.get(30) &&
				deck.d.get(40) != deck2.d.get(40) && deck.d.get(50) != deck2.d.get(50)){
			shuffled = true;
		}

		assertEquals(shuffled, true, "we're not fully random");

	}

	@Test
	void testNewDeck2() {	//newDeck working correctly and full random
		Deck deck = new Deck();
		Deck deck2 = deck.newDeck();
		boolean shuffled = false;

		if(deck.d.get(2) != deck2.d.get(2) && deck.d.get(12) != deck2.d.get(12) &&
				deck.d.get(22) != deck2.d.get(22) && deck.d.get(32) != deck2.d.get(32) &&
				deck.d.get(42) != deck2.d.get(42) && deck.d.get(49) != deck2.d.get(49)){
			shuffled = true;
		}

		assertEquals(shuffled, true, "we're not fully random");

	}

		@Test//------------------------------------------------------------------------------------------------
		void testNewDeck3() {	//newDeck working correctly and has all cards?
			Deck deck2 = new Deck();
			Deck deck = deck2.newDeck();
			boolean containsAllCards = true;
			ArrayList<Character> suits = new ArrayList<Character>() {
				{
					add('C');
					add('H');
					add('S');
					add('D');
				}
			};
			ArrayList<Integer> values = new ArrayList<Integer>() {
				{
					add(2);
					add(3);
					add(4);
					add(5);
					add(6);
					add(7);
					add(8);
					add(9);
					add(10);
					add(11);
					add(12);
					add(13);
					add(14);
				}
			};

			char missingChar = ',';
			int missingValue = -1;
			int missingCount = 0;
			for(int i = 0; i < suits.size(); i++){
				for(int j = 0; j < values.size(); j++){
					Card card = new Card(suits.get(i), values.get(j));
					if(!containsCard(deck, card)){
						containsAllCards = false;
						missingChar = card.suit;
						missingValue = card.value;
						missingCount++;
					}
				}
			}

			assertEquals(containsAllCards, true, "this deck doesn't contain " + missingCount + " cards");
		}

	@Test
	void testNewDeck4() {
		Deck deck = new Deck();
		Deck deck2 = deck.newDeck();

		assertEquals(deck2.d.size(), 52, "size isn't correct");
	}

	@Test //no random cards?
	void testNewDeck5() {
		Deck deck = new Deck();
		Deck deck2 = deck.newDeck();
		assertEquals(containsCard(deck2, new Card('C', 1)), false, "this card SHOULD not exist");
	}



	@Test
	void testDealerDealersHand() {
		Dealer dealer = new Dealer();

		assertEquals(dealer.theDeck.d.size(), 49, "not removing cards from the deck");
	}

	@Test
	void testDealerDealersHand2() {
		Dealer dealer = new Dealer();
		ArrayList<Card> playerHand = dealer.dealHand();

		assertEquals(dealer.theDeck.d.size(), 46, "not removing cards from the deck properly");
	}

	@Test
	void testDealerDealersHand3() {
		Dealer dealer = new Dealer();
		ArrayList<Card> playerHand = dealer.dealHand();

		assertEquals(playerHand.size(), 3, "not adding right number of cards to a hand");
	}

	@Test
	void testDealerDealersHand4() {
		Dealer dealer = new Dealer();
		ArrayList<Card> playerHand = dealer.dealHand();

		assertEquals(playerHand.size(), dealer.dealersHand.size(), "not adding right number of cards to either hand");
	}



	@Test
	void testPlayer() {
		Player p1 = new Player();

		assertEquals(p1.anteBet, 0, "ante bet not initialized correctly");
	}

	@Test
	void testPlayer2() {
		Player p1 = new Player();

		assertEquals(p1.playBet, 0, "play bet not initialized correctly");
	}

	@Test
	void testPlayer3() {
		Player p1 = new Player();

		assertEquals(p1.pairPlusBet, 0, "pair plus bet not initialized correctly");
	}

	@Test
	void testPlayer4() {
		Player p1 = new Player();

		assertEquals(p1.totalWinnings, 0, "total winnings not initialized correctly");
	}

	@Test
	void testPlayer5() {
		Player p1 = new Player();

		assertEquals(p1.hand.size(), 0, "player's hand not initialized correctly");
	}


	//20 ThreeCardLogic test cases
	@Test
	void testThreeCardLogicEvalHand() {
		ArrayList<Card> dummyHand = new ArrayList<Card>() {
			{
				add(new Card('C', 6));
				add(new Card('C', 7));
				add(new Card('C', 5));
			}
		};

		assertEquals(ThreeCardLogic.evalHand(dummyHand), 1, "straight flush not recognized");
	}

	@Test
	void testThreeCardLogicEvalHand2() {
		ArrayList<Card> dummyHand = new ArrayList<Card>() {
			{
				add(new Card('C', 12));
				add(new Card('H', 12));
				add(new Card('S', 12));
			}
		};

		assertEquals(ThreeCardLogic.evalHand(dummyHand), 2, "three of a kind not recognized");
	}

	@Test
	void testThreeCardLogicEvalHand3() {
		ArrayList<Card> dummyHand = new ArrayList<Card>() {
			{
				add(new Card('D', 3));
				add(new Card('C', 4));
				add(new Card('D', 5));
			}
		};

		assertEquals(ThreeCardLogic.evalHand(dummyHand), 3, "straight not recognized");
	}

	@Test
	void testThreeCardLogicEvalHand4() {
		ArrayList<Card> dummyHand = new ArrayList<Card>() {
			{
				add(new Card('D', 13));
				add(new Card('D', 5));
				add(new Card('D', 9));
			}
		};

		assertEquals(ThreeCardLogic.evalHand(dummyHand), 4, "flush not recognized");
	}

	@Test
	void testThreeCardLogicEvalHand5() {
		ArrayList<Card> dummyHand = new ArrayList<Card>() {
			{
				add(new Card('H', 13));
				add(new Card('C', 13));
				add(new Card('D', 2));
			}
		};

		assertEquals(ThreeCardLogic.evalHand(dummyHand), 5, "pair not recognized");
	}

	@Test
	void testThreeCardLogicEvalPPWinnings() {
		ArrayList<Card> dummyHand = new ArrayList<Card>() {
			{
				add(new Card('C', 6));
				add(new Card('C', 7));
				add(new Card('C', 5));
			}
		};

		assertEquals(ThreeCardLogic.evalPPWinnings(dummyHand, 25), 40*25, "PPWinnings not calculated properly");
	}

	@Test
	void testThreeCardLogicEvalPPWinnings2() {
		ArrayList<Card> dummyHand = new ArrayList<Card>() {
			{
				add(new Card('C', 12));
				add(new Card('H', 12));
				add(new Card('S', 12));
			}
		};

		assertEquals(ThreeCardLogic.evalPPWinnings(dummyHand, 22), 30*22, "PPWinnings not calculated properly");
	}

	@Test
	void testThreeCardLogicEvalPPWinnings3() {
		ArrayList<Card> dummyHand = new ArrayList<Card>() {
			{
				add(new Card('D', 3));
				add(new Card('C', 4));
				add(new Card('D', 5));
			}
		};

		assertEquals(ThreeCardLogic.evalPPWinnings(dummyHand, 13), 6*13, "PPWinnings not calculated properly");
	}

	@Test
	void testThreeCardLogicEvalPPWinnings4() {
		ArrayList<Card> dummyHand = new ArrayList<Card>() {
			{
				add(new Card('D', 13));
				add(new Card('D', 5));
				add(new Card('D', 9));
			}
		};

		assertEquals(ThreeCardLogic.evalPPWinnings(dummyHand, 5), 3*5, "PPWinnings not calculated properly");
	}

	@Test
	void testThreeCardLogicEvalPPWinnings5() {
		ArrayList<Card> dummyHand = new ArrayList<Card>() {
			{
				add(new Card('H', 13));
				add(new Card('C', 13));
				add(new Card('D', 2));
			}
		};

		assertEquals(ThreeCardLogic.evalPPWinnings(dummyHand, 6), 6, "PPWinnings not calculated properly");
	}

	@Test
	void testThreeCardLogicCompareHand() {
		ArrayList<Card> dealerHand = new ArrayList<Card>() {
			{
				add(new Card('H', 13));
				add(new Card('C', 13));
				add(new Card('D', 2));
			}
		};
		ArrayList<Card> p1Hand = new ArrayList<Card>() {
			{
				add(new Card('C', 12));
				add(new Card('H', 12));
				add(new Card('S', 12));
			}
		};

		assertEquals(ThreeCardLogic.compareHands(dealerHand, p1Hand), 2, "winner not calculated properly");
	}

	@Test
	void testThreeCardLogicCompareHand2() {
		ArrayList<Card> dealerHand = new ArrayList<Card>() {
			{
				add(new Card('C', 12));
				add(new Card('H', 12));
				add(new Card('S', 12));
			}
		};
		ArrayList<Card> p1Hand = new ArrayList<Card>() {
			{

				add(new Card('H', 13));
				add(new Card('C', 13));
				add(new Card('D', 2));
			}
		};

		assertEquals(ThreeCardLogic.compareHands(dealerHand, p1Hand), 1, "winner not calculated properly");
	}

	@Test
	void testThreeCardLogicCompareHand3() {
		ArrayList<Card> dealerHand = new ArrayList<Card>() {
			{
				add(new Card('C', 12));
				add(new Card('H', 12));
				add(new Card('S', 12));
			}
		};
		ArrayList<Card> p1Hand = new ArrayList<Card>() {
			{

				add(new Card('C', 12));
				add(new Card('H', 12));
				add(new Card('S', 12));
			}
		};

		assertEquals(ThreeCardLogic.compareHands(dealerHand, p1Hand), 0, "winner not calculated properly");
	}

	@Test
	void testThreeCardLogicCompareHand4() {
		ArrayList<Card> dealerHand = new ArrayList<Card>() {
			{
				add(new Card('C', 14));
				add(new Card('H', 13));
				add(new Card('S', 12));
			}
		};
		ArrayList<Card> p1Hand = new ArrayList<Card>() {
			{

				add(new Card('C', 12));
				add(new Card('H', 12));
				add(new Card('S', 12));
			}
		};

		assertEquals(ThreeCardLogic.compareHands(dealerHand, p1Hand), 2, "winner not calculated properly");
	}

	@Test
	void testThreeCardLogicCompareHand5() {
		ArrayList<Card> dealerHand = new ArrayList<Card>() {
			{
				add(new Card('C', 14));
				add(new Card('H', 13));
				add(new Card('S', 12));
			}
		};
		ArrayList<Card> p1Hand = new ArrayList<Card>() {
			{

				add(new Card('C', 12));
				add(new Card('C', 12));
				add(new Card('C', 12));
			}
		};

		assertEquals(ThreeCardLogic.compareHands(dealerHand, p1Hand), 2, "winner not calculated properly");
	}

	@Test
	void testThreeCardLogicCompareHand6() {
		ArrayList<Card> dealerHand = new ArrayList<Card>() {
			{
				add(new Card('C', 14));
				add(new Card('C', 13));
				add(new Card('C', 12));
			}
		};
		ArrayList<Card> p1Hand = new ArrayList<Card>() {
			{

				add(new Card('C', 12));
				add(new Card('C', 12));
				add(new Card('C', 12));
			}
		};

		assertEquals(ThreeCardLogic.compareHands(dealerHand, p1Hand), 1, "winner not calculated properly");
	}

	@Test
	void testThreeCardLogicCompareHand7() {
		ArrayList<Card> dealerHand = new ArrayList<Card>() {
			{
				add(new Card('C', 12));
				add(new Card('C', 12));
				add(new Card('C', 12));
			}
		};
		ArrayList<Card> p1Hand = new ArrayList<Card>() {
			{
				add(new Card('C', 7));
				add(new Card('S', 2));
				add(new Card('S', 2));
			}
		};

		assertEquals(ThreeCardLogic.compareHands(dealerHand, p1Hand), 1, "winner not calculated properly");
	}

	@Test
	void testThreeCardLogicCompareHand8() {
		ArrayList<Card> dealerHand = new ArrayList<Card>() {
			{
				add(new Card('C', 2));
				add(new Card('S', 4));
				add(new Card('C', 10));
			}
		};
		ArrayList<Card> p1Hand = new ArrayList<Card>() {
			{
				add(new Card('C', 7));
				add(new Card('S', 10));
				add(new Card('S', 2));
			}
		};

		assertEquals(ThreeCardLogic.compareHands(dealerHand, p1Hand), 0, "winner not calculated properly");
	}

	@Test
	void testThreeCardLogicCompareHand9() {
		ArrayList<Card> dealerHand = new ArrayList<Card>() {
			{
				add(new Card('S', 14));
				add(new Card('H', 4));
				add(new Card('C', 7));
			}
		};
		ArrayList<Card> p1Hand = new ArrayList<Card>() {
			{
				add(new Card('C', 7));
				add(new Card('D', 12));
				add(new Card('S', 3));
			}
		};

		assertEquals(ThreeCardLogic.compareHands(dealerHand, p1Hand), 0, "winner not calculated properly");
	}

	@Test
	void testThreeCardLogicCompareHand10() {
		ArrayList<Card> dealerHand = new ArrayList<Card>() {
			{
				add(new Card('H', 2));
				add(new Card('S', 3));
				add(new Card('C', 10));
			}
		};
		ArrayList<Card> p1Hand = new ArrayList<Card>() {
			{
				add(new Card('C', 7));
				add(new Card('S', 8));
				add(new Card('D', 2));
			}
		};

		assertEquals(ThreeCardLogic.compareHands(dealerHand, p1Hand), 0, "winner not calculated properly");
	}

}