import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player{
    private Game game;

    Scanner scanner = new Scanner(System.in);

    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<Card> availableCards = new ArrayList<>();
    String name;
    int turn; // 0 it is their turn, 1 they're the next, 2 second next...
    boolean unoCall = false;  // ask player for input at the end of each round ENTER is play, "c" is change, UNO is uno

    public Player(String name, Game game){
        this.name = name;
        this.game = game;
    }

    public void getAvailableCards(){
        availableCards.clear();
        for (Card c : cards){
            Card topCard = game.stack.cards.getLast();
            if (c.face.contains(FACE.WILD)
            || c.number == topCard.number 
            || c.colour.equals(topCard.colour) 
            || (c.face.containsAll(topCard.face) && c.drawNumber == topCard.drawNumber && !c.face.contains(FACE.NUMBER))){
                availableCards.add(c);
            }
        }
    }

    public void selectCard(){
        getAvailableCards();
        int availableSize = availableCards.size();

        System.out.println("All cards:");
        for (int i = 0; i < cards.size(); i++) {
            System.out.print(cards.get(i).display() + ", ");
        }

        if (availableSize > 1){
            for (int i = 0; i < availableSize; i++){
                System.out.println(i + ": " + availableCards.get(i).display());
            }

            System.out.println("Which card would you like? (0-" + (availableSize-1) + ")");
            while (true){ 
                try{
                    int desiredCard = scanner.nextInt(); 
                    if (desiredCard >= 0 && desiredCard < availableSize) {
                        placeCard(desiredCard);
                        return;
                    }
                    else{
                        break;
                    }
                } catch (InputMismatchException e){
                    System.out.println("Enter a valid number.(0-" + (availableSize-1) + ")");
                    scanner.next();
                }
            }
        }
        else{
            placeCard(0);
        }
    }

    private void placeCard(int cardIndex){
        Card card = availableCards.get(cardIndex);
        System.out.println("Placing " + card.display());
        scanner.nextLine(); // to clear buffer
        System.out.println("Call: ");
        String call = scanner.nextLine();
        unoCall = validCall(call);
        if (card.face.contains(FACE.WILD)){
            card.colour = chooseColour();
        }

        game.unoSystem(this);
        game.stack.cards.addLast(card);

        scanner.close();
        game.changeTurn(false, this);
    }

    private boolean validCall(String call){
        System.out.println("validating...");
        switch (call.trim().toUpperCase()) {
            case "":
                return false;
            case "C":
                selectCard();
                break;
            case "UNO":
                return true;
            default:
                System.out.println("Insert a valid call: ");
                scanner.nextLine();
                String newCall = scanner.next();
                validCall(newCall);
        }
        return false;
    }

    private COLOUR chooseColour(){
        System.out.println("Choose:\n (r) RED | (g) GREEN | (b) BLUE | y (YELLOW)");
        String letter = scanner.nextLine();
        switch (letter.trim().toLowerCase()){
            case "r":
                return COLOUR.RED;
            case "g":
                return COLOUR.GREEN;
            case "b":
                return COLOUR.BLUE;
            case "y":
                return COLOUR.YELLOW;
            default:
                return chooseColour();
        }
    }
}


// For two players, there is a slight change of rules:

//     Reverse works like Skip
//     Play Skip, and you may immediately play another card
//     If you play a Draw Two or Wild Draw Four card, your opponent has to draw the number of cards required, and then play immediately resumes back on your turn.


// round begin
// 0 1 2 3
// next turn 
// 3 0 1 2
// next turn
// 2 3 0 1
// someone wins
// 1 2   0
