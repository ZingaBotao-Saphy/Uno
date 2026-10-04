
import java.util.InputMismatchException;
import java.util.Scanner;

public class Player{
    private int id;
    int turn; // 0 it is their turn, 1 they're the next, 2 second next...
    Card[] cards;
    //boolean unoCall = false;  // idk how to implement

    public void selectCard(){
        if (cards.length > 1){
            for (int i = 0; i < cards.length; i++) {
                System.out.println(i + ": " + cards[i].display());
            }

            System.out.println("Which card would you like? (0-" + cards.length + ")");
            Scanner scanner = new Scanner(System.in);
            while (true) { 
                try{
                    int desiredCard = scanner.nextInt(); 
                    if (desiredCard >= 0 && desiredCard < cards.length) {
                        placeCard(desiredCard);
                    }
                    else{
                        break;
                    }
                } catch (InputMismatchException e){
                    System.out.println("Enter a number.");
                    scanner.next();
                }
                
            }
            scanner.close();
        }
        else{
            placeCard(0);
        }
    }

    public void placeCard(int cardIndex){
        Card card = cards[cardIndex];


        changeTurn();
    }

    public void changeTurn(){
        if (turn == 0){
            turn = cards.length - 1;
        }
        else{
            turn++;
        }
    }
}


// round begin
// 0 1 2 3
// next turn 
// 3 0 1 2
// next turn
// 2 3 0 1
