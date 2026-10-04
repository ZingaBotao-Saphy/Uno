
import java.util.ArrayList;
import java.util.Random;

public class Deck{
    ArrayList<Card> cards = new ArrayList<>();
    
    public void createDeck(){
        ArrayList<Card> sortedCards = new ArrayList<>();
        Random random = new Random();
        int number = 0;
        int destination = 1;
        int drawNumber = 0;

        int MAX_CARDS = 112;
        int COLOUR_MAX = 19;
        int SPECIAL_MAX = 8;
        int SPECIAL_COLOUR_MAX = 2;
        int WILD_DRAW_MAX = 4;

        // Add Number Cards
        for (int i = 0; i <= COLOUR_MAX; i++) {
            for (int c = 0; c < COLOUR.values().length - 2; c++){ //-2 to exclude black
                FACE[] faces = {FACE.NUMBER};
                Card card = new Card(faces, COLOUR.values()[c], number, drawNumber, destination); 
                sortedCards.add(card);
                System.out.println("iteration: " + number);
                number++;
                if (number > 9){
                    number = 0;
                }
            }
        }
        number = -1; //actual default

        // Add Special Cards
        for (int i = 0; i < SPECIAL_COLOUR_MAX; i++) {
            for (int c = 0; c < COLOUR.values().length - 2; c++){ //-2 to exclude black
                for (int s = 1; s < FACE.values().length - 2; s++) {
                    if (FACE.values()[s].equals(FACE.REVERSE)){
                        destination = -1;
                    }
                    else if (FACE.values()[s].equals(FACE.DRAW)){
                        drawNumber = 2;
                        number = 2;
                    }
                    else{
                        number = -1;
                        drawNumber = 0;
                        destination = 1;
                    }
                    FACE[] faces = {FACE.values()[s]};
                    Card card = new Card(faces, COLOUR.values()[c], number, drawNumber, destination);
                    sortedCards.add(card);
                }
                
            }
        }

        // Add Wild Cards
        for (int i = 0; i < SPECIAL_MAX; i++) {
            FACE[] faces = {FACE.WILD};
            Card card = new Card(faces, COLOUR.BLACK, number, drawNumber, destination);
            sortedCards.add(card);
        }

        // Add Wild Draw Cards
        for (int i = 0; i < WILD_DRAW_MAX; i++) {
            FACE[] faces = {FACE.WILD, FACE.DRAW};
            Card card = new Card(faces, COLOUR.BLACK, number, 4, destination);
            sortedCards.add(card);
        }

        // Shuffle Deck
        for (int i = 0; i < MAX_CARDS; i++) {
            int index = random.nextInt(sortedCards.size());
            cards.add(sortedCards.get(index));
            sortedCards.remove(index);
        }

    }

    public Card takeCard(){
        Card card = cards.getLast();
        cards.removeLast();
        return card;
    }
}