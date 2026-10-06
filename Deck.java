
import java.util.ArrayList;
import java.util.Random;

public class Deck{
    private Game game;

    Random random = new Random();

    ArrayList<Card> cards = new ArrayList<>();
    //ask somewhere fo start cards
    int START_CARDS = 7;
    
    public Deck(Game game){
        this.game = game;
    }

    public void createDeck(){
        ArrayList<Card> sortedCards = new ArrayList<>();
        
        int number = -1;
        int destination = 1;
        int drawNumber = 0;

        int MAX_CARDS = 108;
        int COLOUR_MAX = 19;
        int SPECIAL_MAX = 8;
        int SPECIAL_COLOUR_MAX = 2;
        int WILD_DRAW_MAX = 4;

        // Add Coloured Cards
        for (int c = 0; c < COLOUR.values().length - 1; c++){ 

            number = 0;
            // Add Number Cards
            for (int i = 0; i <= COLOUR_MAX; i++){ 
                ArrayList<FACE> faces = new ArrayList<>();
                faces.add(FACE.NUMBER);
                Card card = new Card(faces, COLOUR.values()[c], number, drawNumber, destination); 
                sortedCards.add(card);
                number++;
                if (number > 9){
                    number = 0;
                }
            }
            number = -1; //actual default

            // Add Special Cards
            for (int i = 0; i < SPECIAL_COLOUR_MAX; i++){ //-2 to exclude black
                for (int s = 1; s < FACE.values().length - 2; s++){

                    switch (FACE.values()[s]) {
                        case REVERSE:
                            destination = -1;
                            break;

                        case DRAW:
                            drawNumber = 2;
                            number = 2;
                            break;

                        default:
                            number = -1;
                            drawNumber = 0;
                            destination = 1;
                            break;
                    }

                    // if (FACE.values()[s].equals(FACE.REVERSE)){
                    //     destination = -1;
                    // }
                    // else if (FACE.values()[s].equals(FACE.DRAW)){
                    //     drawNumber = 2;
                    //     number = 2;
                    // }
                    // else{
                    //     number = -1;
                    //     drawNumber = 0;
                    //     destination = 1;
                    // }

                    ArrayList<FACE> faces = new ArrayList<>();
                    faces.add(FACE.values()[s]);
                    Card card = new Card(faces, COLOUR.values()[c], number, drawNumber, destination);
                    sortedCards.add(card);
                }
            }
            
        }
        
        // Add Wild Cards
        for (int i = 0; i < SPECIAL_MAX; i++){
            ArrayList<FACE> faces = new ArrayList<>();
            faces.add(FACE.WILD);
            Card card = new Card(faces, COLOUR.BLACK, number, drawNumber, destination);
            sortedCards.add(card);
        }

        // Add Wild Draw Cards
        for (int i = 0; i < WILD_DRAW_MAX; i++){
            ArrayList<FACE> faces = new ArrayList<>();
            faces.add(FACE.WILD);
            faces.add(FACE.DRAW);
            Card card = new Card(faces, COLOUR.BLACK, number, 4, destination);
            sortedCards.add(card);
        }

        // Shuffle Deck
        cards = game.stack.shuffle(sortedCards);
    }

    public void dealCards(){
        for (int i = 0; i <= START_CARDS; i++){
            for (Player p : game.players){
                p.cards.add(cards.removeLast());
            }
        }
        Card topCard = cards.removeLast();
        game.stack.cards.add(topCard);
        System.out.println("TopCard: " + topCard.display());
    }

    public ArrayList<Card> drawCards(int drawNumber){
        ArrayList<Card> drawnCards = new ArrayList<>();

        if (drawNumber > cards.size()){
            cards = game.stack.shuffle(game.stack.cards);
        }

        for (int i = 0; i < drawNumber; i++){
            drawnCards.add(cards.removeLast());
        }

        return drawnCards;
    }

}