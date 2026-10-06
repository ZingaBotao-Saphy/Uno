import java.util.ArrayList;
import java.util.Random;

public class Stack{
    Random random = new Random();

    ArrayList<Card> cards = new ArrayList<>();
    
    public ArrayList<Card> shuffle(ArrayList<Card> sortedCards){
        ArrayList<Card> shuffledCards = new ArrayList<>();

        while (!sortedCards.isEmpty()){
            int index = random.nextInt(sortedCards.size());
            if (sortedCards.get(index).face.contains(FACE.WILD)){
                sortedCards.get(index).colour = COLOUR.BLACK;
            }
            shuffledCards.add(sortedCards.remove(index));
        }
        return shuffledCards;
    }
}