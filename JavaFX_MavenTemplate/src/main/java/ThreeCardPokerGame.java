import javafx.animation.FadeTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyCode;
import java.util.HashMap;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Iterator;


public class ThreeCardPokerGame extends Application {	//--------------------------------------------------------------
	// data members
	public static Player playerOne;
	public static Player playerTwo;
	public static Dealer theDealer;

	int prefWidthOfBetTextFields = 250;
	int picDimensions = 50;
	int playerOneCashAmount = 0;
	int playerTwoCashAmount = 0;
	int dealerOrP1 = 0;
	int dealerOrP2 = 0;
	boolean p1Play = false;
	boolean p2Play = false;
	boolean isBeginning = true;
	String colorScheme = "-fx-background-color: #2f4f4a;";
	String winner = "";
	String winner2 = "";

	TextField player1StartAmount, player1AnteBet, player1PairPlusWager, player1CashBox, player1CashBox2,
			  player2StartAmount, player2AnteBet, player2PairPlusWager, player2CashBox, player2CashBox2;
	MenuBar menuBar,
			menuBar2;
	Button dealerPicButton, p1PicButton, p2PicButton,
		   dealerPicButton2, p1PicButton2, p2PicButton2,
		   p1Card1Visual, p1Card2Visual, p1Card3Visual,
		   p2Card1Visual, p2Card2Visual, p2Card3Visual,
		   dCard1Visual, dCard2Visual, dCard3Visual,
		   p1PlayButton, p1FoldButton, p2PlayButton, p2FoldButton,
		   p1Wins, p2Wins, dealerWins, nooneWins,
	       playAgain, exitGame;
	Image playerTwoPic, dealerPic, playerOnePic,
		  playerTwoPic2, dealerPic2, playerOnePic2;
	ImageView dPV, pOPV, pTPV,
			  dPV2, pOPV2, pTPV2;

	ListView<String> displayQueueItems;
	HashMap<String, Scene> sceneMap;
	GenericQueue<String> myQueue;
	ObservableList<String> storeQueueItemsinListView;

//	PauseTransition pause = new PauseTransition(Duration.seconds(2));


	public static void main(String[] args) {	//----------------------------------------------------------------------
		playerOne = new Player();
		playerTwo = new Player();
		theDealer = new Dealer();

		playerOne.hand = theDealer.dealHand();
		playerTwo.hand = theDealer.dealHand();

		launch(args);
	}

	public void printListView(ListView<String> displayQueueItems, ObservableList<String> storeQueueItemsinListView, GenericQueue<String> myQueue){
		displayQueueItems.getItems().removeAll(storeQueueItemsinListView);
		storeQueueItemsinListView.clear();
		Iterator<String> i = myQueue.createIterator();
		while(i.hasNext()) {
			storeQueueItemsinListView.add(i.next());
		}
		displayQueueItems.setItems(storeQueueItemsinListView);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {	//------------------------------------------------------
		primaryStage.setTitle("Three Card Poker - Charlotte Andry");

		//allows us to switch screens
		sceneMap = new HashMap<String,Scene>();
		sceneMap.put("mainScreen", createMainScene(primaryStage, isBeginning));
		sceneMap.put("gamePlay", createGameScene(primaryStage));

		primaryStage.setScene(sceneMap.get("mainScreen"));
		primaryStage.show();
	}

	public Scene createMainScene(Stage primaryStage, boolean isBeginning){	//--------------------------------------------------------------
		if(isBeginning == false){
			player2AnteBet.clear();
			player1AnteBet.clear();
			player2PairPlusWager.clear();
			player1PairPlusWager.clear();
		}
		//creates textbox to place start $$ amount
		player1StartAmount = new TextField();
		player1StartAmount.setPromptText("1) Enter a starting $$ amount  + \"Enter\"");//CONTINUE
		player1StartAmount.setPrefWidth(prefWidthOfBetTextFields);
		player2StartAmount = new TextField();
		player2StartAmount.setEditable(false);
		player2StartAmount.setPrefWidth(prefWidthOfBetTextFields);


		//creates textbox to place ante bets
		player1AnteBet = new TextField();
		player1AnteBet.setPrefWidth(prefWidthOfBetTextFields);
		player1AnteBet.setEditable(false);
		player2AnteBet = new TextField();
		player2AnteBet.setPrefWidth(prefWidthOfBetTextFields);
		player2AnteBet.setEditable(false);

		//creates tectboxes to place pair plus wage
		player1PairPlusWager = new TextField();
		player1PairPlusWager.setPrefWidth(prefWidthOfBetTextFields);
		player1PairPlusWager.setEditable(false);
		player2PairPlusWager = new TextField();
		player2PairPlusWager.setPrefWidth(prefWidthOfBetTextFields);
		player2PairPlusWager.setEditable(false);

		//initializes cashbxes
		player1CashBox = new TextField("$");
		player1CashBox.setPrefWidth(prefWidthOfBetTextFields);
		player2CashBox = new TextField("$");
		player2CashBox.setPrefWidth(prefWidthOfBetTextFields);
		player1CashBox.setEditable(false);
		player2CashBox.setEditable(false);

		//creates "Options" menubar
		menuBar = new MenuBar();
		Menu optionsMenu = new Menu("Options");
		MenuItem optionsMenuExit = new MenuItem("Exit");
		//setOnAction
		MenuItem optionsMenuFreshStart = new MenuItem("Fresh Start");
		//setOnAction
		MenuItem optionsMenuNewLook = new MenuItem("New Look");
		optionsMenu.getItems().addAll(optionsMenuExit, optionsMenuFreshStart, optionsMenuNewLook);
		menuBar.getMenus().add(optionsMenu);

		//creates image for dealer
		dealerPic = new Image("Dealer.png");
		dPV = new ImageView(dealerPic);
		dPV.setFitHeight(picDimensions);
		dPV.setFitWidth(picDimensions);
		dealerPicButton = new Button();
		dealerPicButton.setDisable(true);
		dealerPicButton.setGraphic(dPV);
		dPV.setPreserveRatio(true);

		//creates image for playerOne
		playerOnePic = new Image("playerOne.png");
		pOPV = new ImageView(playerOnePic);
		pOPV.setFitHeight(picDimensions);
		pOPV.setFitWidth(picDimensions);
		pOPV.setPreserveRatio(true);
		p1PicButton = new Button();
		p1PicButton.setGraphic(pOPV);
		p1PicButton.setDisable(true);

		//creates image for playerTwo
		playerTwoPic = new Image("playerTwo.png");
		pTPV = new ImageView(playerTwoPic);
		pTPV.setFitHeight(picDimensions);
		pTPV.setFitWidth(picDimensions);
		pTPV.setPreserveRatio(true);
		p2PicButton = new Button();
		p2PicButton.setGraphic(pTPV);
		p2PicButton.setDisable(true);

		//grabbing start amount for P1
		if(isBeginning == true) {
			player1StartAmount.setOnKeyPressed(e -> {
				if (e.getCode().equals(KeyCode.ENTER)) {
					player2StartAmount.setPromptText("1) Enter a starting $$ amount + \"Enter\"");
					player2StartAmount.setEditable(true);
					player1StartAmount.setEditable(false);
					player1CashBox.setText("$" + player1StartAmount.getText());
					playerOneCashAmount = Integer.parseInt(player1StartAmount.getText());
					System.out.println("1st: " + playerOneCashAmount);
				}
			});

			//grabbing start amount for P2
			player2StartAmount.setOnKeyPressed(e -> {
				if (e.getCode().equals(KeyCode.ENTER)) {
					player1AnteBet.setPromptText("2) Enter an ante bet($5-$25)  + \"Enter\"");
					player1AnteBet.setEditable(true);
					player2StartAmount.setEditable(false);
					player2CashBox.setText("$" + player2StartAmount.getText());
					playerTwoCashAmount = Integer.parseInt(player2StartAmount.getText());
				}
			});
		}

		if(isBeginning == false){
			player1AnteBet.setPromptText("2) Enter an ante bet($5-$25)  + \"Enter\"");
			player1AnteBet.setEditable(true);
		}

		//grabbing ante bet for P1
		player1AnteBet.setOnKeyPressed(e -> {if(e.getCode().equals(KeyCode.ENTER)) {
			if (Integer.parseInt(player1AnteBet.getText()) > 25 || Integer.parseInt(player1AnteBet.getText()) < 5) {
				player1AnteBet.setText("Please choose between $5-$25");
			} else {
				player2AnteBet.setPromptText("2) Enter an ante bet($5-$25)  + \"Enter\"");
				player2AnteBet.setEditable(true);
				player1AnteBet.setEditable(false);
				playerOne.anteBet = Integer.parseInt(player1AnteBet.getText());
			}
		}
		});

		//grabbing ante bet for P2
		player2AnteBet.setOnKeyPressed(e -> { if (e.getCode().equals(KeyCode.ENTER)) {
			if (Integer.parseInt(player2AnteBet.getText()) > 25 || Integer.parseInt(player2AnteBet.getText()) < 5) {
				player2AnteBet.setText("Please choose between $5-$25");
			} else{
				player1PairPlusWager.setPromptText("3) Enter a  pair plus wager($5-$25, or $0) + \"Enter\"");
				player1PairPlusWager.setEditable(true);
				player2AnteBet.setEditable(false);
					playerTwo.anteBet = Integer.parseInt(player2AnteBet.getText());
			}
		}
		});

		//grabbing pair plus bet for P1
		player1PairPlusWager.setOnKeyPressed(e -> {if(e.getCode().equals(KeyCode.ENTER)){
			if (Integer.parseInt(player1PairPlusWager.getText()) > 25 || (Integer.parseInt(player1PairPlusWager.getText()) < 5 && Integer.parseInt(player1PairPlusWager.getText()) != 0)) {
				player1PairPlusWager.setText("Please choose between $5-$25 or $0");
			}else {
				player1PairPlusWager.setEditable(false);
				player2PairPlusWager.setEditable(true);
				player2PairPlusWager.setPromptText("3) Enter a  pair plus wager($5-$25, or $0) + \"Enter\"");
				playerOne.pairPlusBet = Integer.parseInt(player1PairPlusWager.getText());
			}
		}
		});

		//grabbing pair plus bet for P2 AND switches scenes
		player2PairPlusWager.setOnKeyPressed(e -> {if(e.getCode().equals(KeyCode.ENTER)) {
			if (Integer.parseInt(player2PairPlusWager.getText()) > 25 || (Integer.parseInt(player2PairPlusWager.getText()) < 5 && Integer.parseInt(player2PairPlusWager.getText()) != 0)) {
				player2PairPlusWager.setText("Please choose between $5-$25 or $0");
			} else {
				player2PairPlusWager.setEditable(false);
				playerTwo.pairPlusBet = Integer.parseInt(player2PairPlusWager.getText());
				primaryStage.setScene(sceneMap.get("gamePlay"));
			}
		}
		});

		//groups menu bar
		VBox VBmenu = new VBox(menuBar);
		VBmenu.setSpacing(picDimensions);

		//groups p1 text fields
		VBox VBplayer1Fields;
		VBox VBplayer2Fields;
		if(isBeginning == true){
			VBplayer1Fields = new VBox(player1StartAmount, player1AnteBet, player1PairPlusWager);
			VBplayer2Fields = new VBox(player2StartAmount, player2AnteBet, player2PairPlusWager);
		} else{
			VBplayer1Fields = new VBox(player1AnteBet, player1PairPlusWager);
			VBplayer2Fields = new VBox(player2AnteBet, player2PairPlusWager);

		}

		//groups cashboxes together
		HBox HBplayerCashBoxes = new HBox(player1CashBox, player2CashBox);	//add list box in the middle of these 2
		HBplayerCashBoxes.setSpacing(1200 - (prefWidthOfBetTextFields/2));

		//groups p1 icon and text fields together
		HBox HBPlayerOnePicAndTextFields = new HBox(p1PicButton, VBplayer1Fields);
		HBPlayerOnePicAndTextFields.setSpacing(picDimensions);

		//groups p2 icon and text fields together
		HBox HBPlayerTwoPicAndTextFields = new HBox(VBplayer2Fields, p2PicButton);
		HBPlayerTwoPicAndTextFields.setSpacing(picDimensions);


		//creates Board Pane and sets top and bottom sections
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(VBmenu);
		borderPane.setBottom(HBplayerCashBoxes);

		//sets center section
		borderPane.setCenter(dealerPicButton);
		borderPane.setMargin(dealerPicButton, new Insets(0,0,400,0));

		//sets left section
		borderPane.setLeft(HBPlayerOnePicAndTextFields);
		borderPane.setMargin(HBPlayerOnePicAndTextFields, new Insets(300,40,40,100));

		//sets right section
		borderPane.setRight(HBPlayerTwoPicAndTextFields);
		borderPane.setMargin(HBPlayerTwoPicAndTextFields, new Insets(300,100,40,40));

		optionsMenuFreshStart.setOnAction(e-> {
//					primaryStage.setScene(sceneMap.get("mainScreen"));
					//reset boiii
				}
		);

		optionsMenuExit.setOnAction(e-> {
					p1PicButton.setVisible(false);
					p2PicButton.setVisible(false);
					dealerPicButton.setVisible(false);
					if(isBeginning == true){
						player1StartAmount.setVisible(false);
						player2StartAmount.setVisible(false);
					}
					player1AnteBet.setVisible(false);
					player2AnteBet.setVisible(false);
					player1PairPlusWager.setVisible(false);
					player2PairPlusWager.setVisible(false);
					player1CashBox.setVisible(false);
					player2CashBox.setVisible(false);
				}
		);

		optionsMenuNewLook.setOnAction(e-> {
					colorScheme = "-fx-background-color: #" + (int) Math.floor(Math.random() * 9) + "f" + (int) Math.floor(Math.random() * 9) + "f" + (int) Math.floor(Math.random() * 9) + "f;";
					borderPane.setStyle(colorScheme);
				}
		);

		borderPane.setStyle(colorScheme);
		return new Scene(borderPane, 1200, 600);
	}

	public Scene createGameScene(Stage primaryStage){		//------------------------------------------------------------------------------
		//creates "Options" menubar
		menuBar2 = new MenuBar();
		Menu optionsMenu2 = new Menu("Options");
		MenuItem optionsMenuExit2 = new MenuItem("Exit");
		//setOnAction
		MenuItem optionsMenuFreshStart2 = new MenuItem("Fresh Start");
		//setOnAction
		MenuItem optionsMenuNewLook2 = new MenuItem("New Look");
		optionsMenu2.getItems().addAll(optionsMenuExit2, optionsMenuFreshStart2, optionsMenuNewLook2);
		menuBar2.getMenus().add(optionsMenu2);

		//creates image for dealer
		dealerPic2 = new Image("Dealer.png");
		dPV2 = new ImageView(dealerPic2);
		dPV2.setFitHeight(picDimensions);
		dPV2.setFitWidth(picDimensions);
		dealerPicButton2 = new Button();
		dealerPicButton2.setDisable(true);
		dealerPicButton2.setGraphic(dPV2);
		dPV2.setPreserveRatio(true);
		dealerWins = new Button("Dealer\nWins!");
		dealerWins.setVisible(false);
		dealerWins.setPrefHeight(75);
		dealerWins.setPrefWidth(75);

		//creates image for playerOne
		playerOnePic2 = new Image("playerOne.png");
		pOPV2 = new ImageView(playerOnePic2);
		pOPV2.setFitHeight(picDimensions);
		pOPV2.setFitWidth(picDimensions);
		pOPV2.setPreserveRatio(true);
		p1PicButton2 = new Button();
		p1PicButton2.setGraphic(pOPV2);
		p1PlayButton = new Button("Play?");
		p1PlayButton.setPrefHeight(50);
		p1PlayButton.setPrefWidth(50);
		p1FoldButton = new Button("Fold?");
		p1FoldButton.setPrefHeight(50);
		p1FoldButton.setPrefWidth(50);
		p1Wins = new Button("PlayerOne\nWins!");
		p1Wins.setVisible(false);
		p1Wins.setPrefHeight(75);
		p1Wins.setPrefWidth(75);

		//creates image for playerTwo
		playerTwoPic2 = new Image("playerTwo.png");
		pTPV2 = new ImageView(playerTwoPic2);
		pTPV2.setFitHeight(picDimensions);
		pTPV2.setFitWidth(picDimensions);
		pTPV2.setPreserveRatio(true);
		p2PicButton2 = new Button();
		p2PicButton2.setGraphic(pTPV2);
		p2PlayButton = new Button("Play?");
		p2PlayButton.setPrefHeight(50);
		p2PlayButton.setPrefWidth(50);
		p2FoldButton = new Button("Fold?");
		p2PlayButton.setVisible(false);
		p2FoldButton.setVisible(false);
		p2FoldButton.setPrefHeight(50);
		p2FoldButton.setPrefWidth(50);
		p2Wins = new Button("PlayerTwo\nWins!");
		p2Wins.setVisible(false);
		p2Wins.setPrefHeight(100);
		p2Wins.setPrefWidth(100);

		nooneWins = new Button("Noone\nWins...");
		nooneWins.setPrefHeight(75);
		nooneWins.setPrefWidth(75);
		nooneWins.setVisible(false);

		playAgain = new Button("Play\nAgain?");
		playAgain.setPrefHeight(100);
		playAgain.setPrefWidth(75);
		playAgain.setVisible(false);

		exitGame = new Button("Exit\nGame");
		exitGame.setPrefHeight(100);
		exitGame.setPrefWidth(75);
		exitGame.setVisible(false);

		//creates cashboxes
//		System.out.println("2nd:" + playerOneCashAmount);
		player1CashBox2 = new TextField("$" + playerOneCashAmount);
		player1CashBox2.setPrefWidth(prefWidthOfBetTextFields);
		player2CashBox2 = new TextField("$" + playerTwoCashAmount);
		player2CashBox2.setPrefWidth(prefWidthOfBetTextFields);
		player1CashBox2.setEditable(false);
		player2CashBox2.setEditable(false);

		//creates image for playerOne's first card
		Image p1Card1 = new Image(playerOne.hand.get(0).suit + "" + playerOne.hand.get(0).value + ".png");
		ImageView p1C1V = new ImageView(p1Card1);
		p1C1V.setFitHeight(picDimensions*1.5);
		p1C1V.setFitWidth(picDimensions*1.5);
		p1Card1Visual = new Button();
		p1Card1Visual.setGraphic(p1C1V);
		p1C1V.setPreserveRatio(true);

		//creates image for playerOne's second card
		Image p1Card2 = new Image((char)playerOne.hand.get(1).suit + "" + playerOne.hand.get(1).value + ".png");
		ImageView p1C2V = new ImageView(p1Card2);
		p1C2V.setFitHeight(picDimensions*1.5);
		p1C2V.setFitWidth(picDimensions*1.5);
		p1Card2Visual = new Button();
		p1Card2Visual.setGraphic(p1C2V);
		p1C2V.setPreserveRatio(true);

		//creates image for playerOne's third card
		Image p1Card3 = new Image((char)playerOne.hand.get(2).suit + "" + playerOne.hand.get(2).value + ".png");
		ImageView p1C3V = new ImageView(p1Card3);
		p1C3V.setFitHeight(picDimensions*1.5);
		p1C3V.setFitWidth(picDimensions*1.5);
		p1Card3Visual = new Button();
		p1Card3Visual.setGraphic(p1C3V);
		p1C3V.setPreserveRatio(true);

		//groups together all the cards vertically
		VBox player1Cards = new VBox(p1Card1Visual, p1Card2Visual, p1Card3Visual);

		//creates image for playerTwo's first card
		Image p2Card1 = new Image((char)playerTwo.hand.get(0).suit + "" + playerTwo.hand.get(0).value + ".png");
		ImageView p2C1V = new ImageView(p2Card1);
		p2C1V.setFitHeight(picDimensions*1.5);
		p2C1V.setFitWidth(picDimensions*1.5);
		p2Card1Visual = new Button();
		p2Card1Visual.setGraphic(p2C1V);
		p2C1V.setPreserveRatio(true);

		//creates image for playerTwo's second card
		Image p2Card2 = new Image((char)playerTwo.hand.get(1).suit + "" + playerTwo.hand.get(1).value + ".png");
		ImageView p2C2V = new ImageView(p2Card2);
		p2C2V.setFitHeight(picDimensions*1.5);
		p2C2V.setFitWidth(picDimensions*1.5);
		p2Card2Visual = new Button();
		p2Card2Visual.setGraphic(p2C2V);
		p2C2V.setPreserveRatio(true);

		//creates image for playerTwo's third card
		Image p2Card3 = new Image((char)playerTwo.hand.get(2).suit + "" + playerTwo.hand.get(2).value + ".png");
		ImageView p2C3V = new ImageView(p2Card3);
		p2C3V.setFitHeight(picDimensions*1.5);
		p2C3V.setFitWidth(picDimensions*1.5);
		p2Card3Visual = new Button();
		p2Card3Visual.setGraphic(p2C3V);
		p2C3V.setPreserveRatio(true);

		//groups all of playerTwo's cards vertically
		VBox player2Cards = new VBox(p2Card1Visual, p2Card2Visual, p2Card3Visual);

		//creates image for the dealers's first card
		Image dCard1 = new Image("cardBackSide.png");
		ImageView dC1V = new ImageView(dCard1);
		dC1V.setFitHeight(picDimensions);
		dC1V.setFitWidth(picDimensions);
		dCard1Visual = new Button();
		dCard1Visual.setDisable(true);
		dCard1Visual.setGraphic(dC1V);
		dC1V.setPreserveRatio(true);

		//creates image for the dealers's second card
		Image dCard2 = new Image("cardBackSide.png");
		ImageView dC2V = new ImageView(dCard2);
		dC2V.setFitHeight(picDimensions);
		dC2V.setFitWidth(picDimensions);
		dCard2Visual = new Button();
		dCard2Visual.setDisable(true);
		dCard2Visual.setGraphic(dC2V);
		dC2V.setPreserveRatio(true);

		//creates image for the dealers's third card
		Image dCard3 = new Image("cardBackSide.png");
		ImageView dC3V = new ImageView(dCard3);
		dC3V.setFitHeight(picDimensions);
		dC3V.setFitWidth(picDimensions);
		dCard3Visual = new Button();
		dCard3Visual.setDisable(true);
		dCard3Visual.setGraphic(dC3V);
		dC3V.setPreserveRatio(true);

		myQueue = new GenericQueue<String>("Starting the game...");
		displayQueueItems = new ListView<String>();
		displayQueueItems.setPrefWidth(600);
		displayQueueItems.setPrefHeight(200);
		displayQueueItems.setStyle("-fx-font-size: 12;"+"-fx-border-size: 20;"+"-fx-border-color: purple;");
		storeQueueItemsinListView = FXCollections.observableArrayList();

		//groups the dealer's cards together horizontally
		HBox dealersCards = new HBox(dCard1Visual, dCard2Visual, dCard3Visual);


		//groups menu bar
		VBox VBmenu = new VBox(menuBar2);
		VBmenu.setSpacing(picDimensions);

		//groups cashboxes
		HBox HBplayerCashBoxes = new HBox(player1CashBox2, displayQueueItems, player2CashBox2);
		//add list box in the middle of these 2
		HBplayerCashBoxes.setSpacing((1200 - 600) / 3);

		//groups playerOne's icon & play/fold buttons
		VBox VBPlayerOnePlayAndFold = new VBox(p1PlayButton, p1FoldButton);
		VBPlayerOnePlayAndFold.setSpacing(picDimensions * 2);

		//groups playerOne's icon & play/fold buttons WITH the cards
		HBox HBPlayerOne = new HBox(p1PicButton2, player1Cards, VBPlayerOnePlayAndFold);
		HBPlayerOne.setSpacing(picDimensions);

		//groups playerTwo's icon & play/fold buttons
		VBox VBPlayerTwoPlayAndFold = new VBox(p2PlayButton, p2FoldButton);
		VBPlayerTwoPlayAndFold.setSpacing(picDimensions);

		//groups playerTwo's icon & play/fold buttons WITH the cards
		HBox HBPlayerTwo = new HBox(VBPlayerTwoPlayAndFold, player2Cards, p2PicButton2);
		HBPlayerTwo.setSpacing(picDimensions);

		//groups the dealer's icon and cards
		HBox HBWinnerButtons = new HBox(p1Wins, p2Wins, dealerWins, nooneWins);
		HBox HBPlayAgainOrExit = new HBox(playAgain, exitGame);
		VBox VBDealerPicAndCards = new VBox(dealerPicButton2, dealersCards, HBWinnerButtons, HBPlayAgainOrExit);
		VBDealerPicAndCards.setSpacing(picDimensions);

		//creates boarder pane and sets top and bottom sections
		BorderPane borderPane = new BorderPane();
		borderPane.setTop(VBmenu);
		borderPane.setBottom(HBplayerCashBoxes);

		//sets center section
		borderPane.setCenter(VBDealerPicAndCards);
		borderPane.setMargin(VBDealerPicAndCards, new Insets(0,0,100,100));

		//sets left section
		borderPane.setLeft(HBPlayerOne);
		borderPane.setMargin(HBPlayerOne, new Insets(200,0,0,40));

		//sets right section
		borderPane.setRight(HBPlayerTwo);
		borderPane.setMargin(HBPlayerTwo, new Insets(200,75,0,40));


		p1PlayButton.setOnAction(e-> {
				p1PlayButton.setVisible(false);
				p1FoldButton.setVisible(false);
				p2PlayButton.setVisible(true);
				p2FoldButton.setVisible(true);
				p1Play = true;
				playerOne.playBet = playerOne.anteBet;
				myQueue.enqueue("P1: play $" + playerOne.playBet);
				printListView(displayQueueItems, storeQueueItemsinListView, myQueue);
			}
		);

		p1FoldButton.setOnAction(e-> {
				p1PlayButton.setVisible(false);
				p1FoldButton.setVisible(false);
				p2PlayButton.setVisible(true);
				p2FoldButton.setVisible(true);
				p1Card1Visual.setDisable(true);
				p1Card2Visual.setDisable(true);
				p1Card3Visual.setDisable(true);
				p1PicButton2.setDisable(true);
				myQueue.enqueue("P1: fold");
				playerOneCashAmount -= playerOne.playBet;
				printListView(displayQueueItems, storeQueueItemsinListView, myQueue);
			}
		);

		p2PlayButton.setOnAction(e-> {
				p2PlayButton.setVisible(false);
				p2FoldButton.setVisible(false);
				dealerPicButton2.setDisable(false);
				dCard1Visual.setDisable(false);
				dCard2Visual.setDisable(false);
				dCard3Visual.setDisable(false);
				dC1V.setImage(new Image((char)theDealer.dealersHand.get(0).suit + "" + theDealer.dealersHand.get(0).value + ".png"));
				dC2V.setImage(new Image((char)theDealer.dealersHand.get(1).suit + "" + theDealer.dealersHand.get(1).value + ".png"));
				dC3V.setImage(new Image((char)theDealer.dealersHand.get(2).suit + "" + theDealer.dealersHand.get(2).value + ".png"));
				p2Play = true;
				myQueue.enqueue("P2: play $" + playerOne.playBet);
				playerTwo.playBet = playerTwo.anteBet;
				System.out.println("Player1: ");
				if(ThreeCardLogic.evalPPWinnings(playerOne.hand, playerOne.pairPlusBet) == 0){
					playerOneCashAmount -= playerOne.pairPlusBet + playerOne.playBet;
					myQueue.enqueue("P1: lost $" + playerOne.playBet + playerOne.playBet);
				} else{
					playerOneCashAmount += ThreeCardLogic.evalPPWinnings(playerOne.hand, playerOne.pairPlusBet) + playerOne.playBet;
					myQueue.enqueue("P1: won $" + playerOne.playBet + playerOne.playBet);
				}

				if(ThreeCardLogic.evalPPWinnings(playerTwo.hand, playerTwo.pairPlusBet) == 0){
					playerTwoCashAmount -=playerTwo.pairPlusBet+ playerTwo.playBet;
					myQueue.enqueue("P2: lost $" + playerTwo.playBet+ playerTwo.playBet);
				} else{
					playerTwoCashAmount += ThreeCardLogic.evalPPWinnings(playerTwo.hand, playerTwo.pairPlusBet)+ playerTwo.playBet;
					myQueue.enqueue("P2: won $" + playerTwo.playBet+ playerTwo.playBet);
				}

				if(p1Play == true){
					dealerOrP1 = ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand);
					if(dealerOrP1 == 1){	//dealer wins
						System.out.println(" Dealer wins");
						winner = "d";
					} else if(dealerOrP1 == 2){		//p1 wins
						System.out.println(" Player1 wins");
						winner = "p1";
					} else{
						System.out.println(" Noone wins");
						winner = "n";
					}
				}
				System.out.println("Player2: ");
				dealerOrP2 = ThreeCardLogic.compareHands(theDealer.dealersHand, playerTwo.hand);
				if(dealerOrP2 == 1){	//dealer wins
					System.out.println(" Dealer wins!");
					winner2 = "d";
				} else if(dealerOrP2 == 2){		//dealer wins
					System.out.println(" Player2 wins!");
					winner2 = "p2";
				} else{
					System.out.println(" Noone wins");
					winner2 = "n";
				}
				//who won?

				if(winner == "d"){
					dealerWins.setVisible(true);
					myQueue.enqueue("Between P1 and Dealer: Dealer Wins!");
				} else if(winner == "p1"){
					p1Wins.setVisible(true);
					myQueue.enqueue("Between P1 and Dealer: P1 Wins!");
				}

				if(winner == "n"){
					nooneWins.setVisible(true);
					myQueue.enqueue("Between P1 and Dealer: noone won...");
				}
				if(winner2 == "n"){
					nooneWins.setVisible(true);
					myQueue.enqueue("Between P2 and Dealer: noone won...");
				}

				if(winner2 == "p2"){
					p2Wins.setVisible(true);
					myQueue.enqueue("Between P2 and Dealer: P2 Wins!");
				} else if(winner2 == "d"){
					dealerWins.setVisible(true);
					myQueue.enqueue("Between P2 and Dealer: Dealer Wins!");
				}

				playAgain.setVisible(true);
				exitGame.setVisible(true);
				printListView(displayQueueItems, storeQueueItemsinListView, myQueue);
			}
		);

		p2FoldButton.setOnAction(e-> {
				p2PlayButton.setVisible(false);
				p2FoldButton.setVisible(false);
				dealerPicButton2.setDisable(false);
				dCard1Visual.setDisable(false);
				dCard2Visual.setDisable(false);
				dCard3Visual.setDisable(false);
				dC1V.setImage(new Image((char)theDealer.dealersHand.get(0).suit + "" + theDealer.dealersHand.get(0).value + ".png"));
				dC2V.setImage(new Image((char)theDealer.dealersHand.get(1).suit + "" + theDealer.dealersHand.get(1).value + ".png"));
				dC3V.setImage(new Image((char)theDealer.dealersHand.get(2).suit + "" + theDealer.dealersHand.get(2).value + ".png"));
				myQueue.enqueue("P2: fold");
				p2Card1Visual.setDisable(true);
				p2Card2Visual.setDisable(true);
				p2Card3Visual.setDisable(true);
				p2PicButton2.setDisable(true);
				playerTwoCashAmount -= playerTwo.playBet;
				if(ThreeCardLogic.evalPPWinnings(playerTwo.hand, playerTwo.pairPlusBet) == 0){
					playerTwoCashAmount -=playerTwo.pairPlusBet;
					myQueue.enqueue("P2: lost $" + playerTwo.playBet);
				} else{
					playerTwoCashAmount += ThreeCardLogic.evalPPWinnings(playerTwo.hand, playerTwo.pairPlusBet);
					myQueue.enqueue("P2: won $" + playerTwo.playBet);
				}

				if(p1Play == true){
					dealerOrP1 = ThreeCardLogic.compareHands(theDealer.dealersHand, playerOne.hand);
					if(dealerOrP1 == 1){
						System.out.println("Dealer wins!");
						winner = "d";

					} else if(dealerOrP1 == 2){
						System.out.println("Player wins1");
						winner = "p1";
					} else{
						System.out.println("Between P1 and Dealer: noone won...");
						winner = "n";
					}
				} else{
					System.out.println("Dealer wins!");
					winner = "d";
				}
				//who won?
				if(winner == "d"){
					dealerWins.setVisible(true);
					myQueue.enqueue("Between P1 and Dealer: Dealer Wins!");
				}

				if(winner == "p1"){
					p1Wins.setVisible(true);
					myQueue.enqueue("Between P1 and Dealer: P1 Wins!");
				}

				if(winner == "n"){
					nooneWins.setVisible(true);
					myQueue.enqueue("Between P1 and Dealer: noone won...");
				}
				if(winner2 == "n"){
					nooneWins.setVisible(true);
					myQueue.enqueue("Between P2 and Dealer: noone won...");
				}
				playAgain.setVisible(true);
				exitGame.setVisible(true);
				printListView(displayQueueItems, storeQueueItemsinListView, myQueue);
			}
		);

		optionsMenuFreshStart2.setOnAction(e-> {
//						primaryStage.setScene(sceneMap.get("mainScreen"));
//				reset boiii
			 }
		);

		optionsMenuExit2.setOnAction(e-> {
				p1PicButton2.setVisible(false);
				p2PicButton2.setVisible(false);
				dealerPicButton2.setVisible(false);
				p1Card1Visual.setVisible(false);
				p1Card2Visual.setVisible(false);
				p1Card3Visual.setVisible(false);
				p2Card1Visual.setVisible(false);
				p2Card2Visual.setVisible(false);
				p2Card3Visual.setVisible(false);
				dCard1Visual.setVisible(false);
				dCard2Visual.setVisible(false);
				dCard3Visual.setVisible(false);
				player1CashBox2.setVisible(false);
				player2CashBox2.setVisible(false);
				playAgain.setVisible(false);
				displayQueueItems.setVisible(false);
				exitGame.setVisible(false);
				p2Wins.setVisible(false);
				nooneWins.setVisible(false);
				dealerWins.setVisible(false);
				p1Wins.setVisible(false);
				p1PlayButton.setVisible(false);
				p2PlayButton.setVisible(false);
				p1FoldButton.setVisible(false);
				p2FoldButton.setVisible(false);
			}
		);

		optionsMenuNewLook2.setOnAction(e-> {
				colorScheme = "-fx-background-color: #" + (int) Math.floor(Math.random() * 9) + "f" + (int) Math.floor(Math.random() * 9) + "f" + (int) Math.floor(Math.random() * 9) + "f;";
				borderPane.setStyle(colorScheme);
			}
		);

		playAgain.setOnAction(e-> {
				//go to main, but take away start amount textfield and ante?
				isBeginning = false;
				primaryStage.setScene(sceneMap.get("mainScreen"));	//not working

			}
		);

		exitGame.setOnAction(e-> {
				p1PicButton2.setVisible(false);
				p2PicButton2.setVisible(false);
				dealerPicButton2.setVisible(false);
				p1Card1Visual.setVisible(false);
				p1Card2Visual.setVisible(false);
				p1Card3Visual.setVisible(false);
				p2Card1Visual.setVisible(false);
				p2Card2Visual.setVisible(false);
				p2Card3Visual.setVisible(false);
				dCard1Visual.setVisible(false);
				dCard2Visual.setVisible(false);
				dCard3Visual.setVisible(false);
				player1CashBox2.setVisible(false);
				player2CashBox2.setVisible(false);
				playAgain.setVisible(false);
				displayQueueItems.setVisible(false);
				exitGame.setVisible(false);
				p2Wins.setVisible(false);
				nooneWins.setVisible(false);
				dealerWins.setVisible(false);
				p1Wins.setVisible(false);
				p1PlayButton.setVisible(false);
				p2PlayButton.setVisible(false);
				p1FoldButton.setVisible(false);
				p2FoldButton.setVisible(false);
			}
		);


		borderPane.setStyle(colorScheme);
		return new Scene(borderPane, 1200, 600);
	}

}
