import java.util.ArrayList;
import java.util.InputMismatchException;

public class Player{
    private Game game;

    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<Card> availableCards = new ArrayList<>();
    String name;
    boolean unoCall = false;

    public Player(String name, Game game){
        this.name = name;
        this.game = game;
    }

    private void getAvailableCards(){
        availableCards.clear();
        for (Card c : cards){
            Card topCard = game.stack.getTopCard();
            if (c.face.contains(FACE.WILD)
            || ((c.number != -1) && (c.number == topCard.number))
            || c.colour.equals(topCard.colour) 
            || (c.face.containsAll(topCard.face) && !c.face.contains(FACE.NUMBER))){
                availableCards.add(c);
            }
        }
    }

    public void selectCard(boolean drawOne){
        getAvailableCards();
        int availableSize = availableCards.size();

        System.out.println("All cards (" + cards.size() + "):");
        for (int i = 0; i < cards.size(); i++){
            System.out.print("| " + cards.get(i).display() + " |");
        }
        System.out.println("");

        if (availableSize > 1){
            for (int i = 0; i < availableSize; i++){
                System.out.println(i + ": " + availableCards.get(i).display());
            }

            System.out.println("Which card would you like? (0-" + (availableSize-1) + ")");
            while (true){ 
                try{
                    int desiredCard = game.scanner.nextInt(); 
                    game.scanner.nextLine();
                    if (desiredCard >= 0 && desiredCard < availableSize){
                        placeCard(desiredCard);
                        return;
                    }
                } catch (InputMismatchException e){
                    game.scanner.nextLine();
                }
                System.out.println("Enter a valid number.(0-" + (availableSize-1) + ")");
            }
        }
        else if (availableSize == 1){
            System.out.println("Only one card available: " + availableCards.getFirst().display());
            placeCard(0);
        }
        else if (availableSize == 0 && drawOne){
            System.out.println("No cards available");
            cards.addAll(game.deck.drawCards(1));
            selectCard(false);
        }
        else if (availableSize == 0){
            System.out.println("No cards available");
            game.changeTurn();
        }
    }

    private void placeCard(int cardIndex){
        Card card = availableCards.get(cardIndex);

        if (card.face.contains(FACE.WILD)){
            card.colour = chooseColour();
        }

        unoCall = validCall(); 
        System.out.println("Placing " + card.display());
        game.stack.cards.addLast(card);
        cards.remove(card);
        game.unoSystem(this);
        
        if (cards.isEmpty()){
            game.playerWon();
        }

        game.changeTurn();
    }

    private boolean validCall(){
        System.out.print("Call: ");
        String call = game.scanner.nextLine();
        switch (call.trim().toUpperCase()){
            case "" -> {return false;}
            case "C" -> selectCard(true);
            case "UNO" -> {return true;}
            default -> {
                System.out.println("Insert a valid call.");
                return validCall();
            }
        }
        return false;
    }

    private COLOUR chooseColour(){
        System.out.println("Choose:\n (r) RED | (g) GREEN | (b) BLUE | y (YELLOW)");
        String letter = game.scanner.nextLine();
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
