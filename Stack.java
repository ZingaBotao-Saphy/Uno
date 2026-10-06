import java.util.ArrayList;
import java.util.Random;

public class Stack{
    Random random = new Random();

    ArrayList<Card> cards = new ArrayList<>();
    
    public ArrayList<Card> shuffle(ArrayList<Card> sortedCards){
        ArrayList<Card> shuffledCards = new ArrayList<>();
        System.out.println("shuffling...");

        // TODO: does this remove from the actual array?
        while (!sortedCards.isEmpty()){
            int index = random.nextInt(sortedCards.size());
            shuffledCards.add(sortedCards.remove(index));
        }
        System.out.println("shuffled!");
        return shuffledCards;
    }
}