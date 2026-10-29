import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game{
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();
    ArrayList<Player> players = new ArrayList<>();
    ArrayList<Player> winners = new ArrayList<>();
    Deck deck;
    Stack stack;
    int turn = 0;
    int direction = 1;

    public Game() {
        stack = new Stack();       
        deck = new Deck(this);
    }

    private Player getCurrentPlayer(){
        if (!players.isEmpty()){
            return players.get(turn);
        }
        else{
            return null;
        }
    }

    private void insertPlayers(){
        // some code to determine how many players there are and their names

        ArrayList<String> names = new ArrayList<>();
        names.add("Paulo");
        names.add("Saphy");
        names.add("Shaun");
        names.add("Wenjie");
        int i = 0;
        while (!names.isEmpty()){
            int currentIndex = random.nextInt(names.size());
            String name = names.get(currentIndex);
            Player player = new Player(name, this);
            players.add(player);
            names.remove(name);
            System.out.println("Player: " + players.get(i).name + " Turn: " + i);
            i++;
        }
    }

    public void startGame(){
        deck.createDeck();
        insertPlayers();
        deck.dealCards();
        System.out.println("First player is: " + getCurrentPlayer().name);
        getCurrentPlayer().selectCard(true);
    }

    private void EndGame(){
        scanner.close();
        System.out.println("Game over!");
        for (int i = 0; i < winners.size(); i++){
            System.out.println(i + ":" + winners.get(i).name);
        }
    }

    public void changeTurn(){
        int skip = 0;
        Card topCard = stack.getTopCard();

        if (topCard.face.contains(FACE.REVERSE)){
            direction = -(direction);
        }
        else if (topCard.face.contains(FACE.SKIP)){
            skip = 1;
        }

        turn = turn + direction + (skip * direction);

        if (turn < 0){
            turn = players.size() + turn;
        }
        else if (turn >= players.size()){
            turn = turn - players.size();
        }

        Player currentPlayer = getCurrentPlayer();
        System.out.println("Player: " + currentPlayer.name + "\n");

        if (topCard.face.contains(FACE.DRAW)){
            currentPlayer.cards.addAll(deck.drawCards(topCard.drawNumber));
        }
        currentPlayer.selectCard(true);
    }

   // need to figure out how this works with draw card
    public void unoSystem(Player player){
        if (player.cards.size() == 1){ 
            if (player.unoCall){
                System.out.println(player.name + " has UNO card left.");
            }
            else{
                System.out.println(player.name + " didn't call UNO.");
                player.cards.addAll(deck.drawCards(2));
                changeTurn();
            }  
        }
        else if (player.unoCall && player.cards.size() != 1){
            System.out.println("You don't have one card...");
        }

  
    }

    public void playerWon(){
        if (winners.isEmpty()){
            System.out.println(getCurrentPlayer().name + " won!");
        }
        else{
            System.out.println(getCurrentPlayer().name + " finished!");
        }
        winners.add(getCurrentPlayer());
        if (players.size() > 1){
            players.remove(turn);
            turn = turn + direction;
            changeTurn();
        }
        else{
            EndGame();
        }

    }
}

// len = 6
// 0 1 2 3 4 5 index

// no skip: skipIndex = 0, skip = 1
// with skip: skipIndex = 1, skip = 2
// direction -1
// (0)first gets (5 - skipIndex)last turn. 
// 4 3 2 1 0 5
// 5 4 3 2 1 0
// 0 1 2 3 4 5

// get turn of index + direction * skip

// direction 1
// (5)last gets (0 + skipIndex)first turn.
// 4 3 2 1 0 5
// 3 2 1 0 5 4
// 2 1 0 5 4 3


// skip, direction multiplied (in order)

// (0)first becomes (4)second last turn.
// 0 1 2 3 4 5
// 4 5 0 1 2 3

// get turn of index + -2(direction) GLOBAL

// (5)last becomes (1)second first turn.
// 4 5 0 1 2 3
// 0 1 2 3 4 5


// reverse
// 0 1 2 3 4 5
// 5 4 3 2 1 0
// two pointers with storage

// player won
// remove player with turn 0, every turn + index + direction // too hard im doing --turn * skip
// 0 1 2 3 4 5
// n 0 1 2 3 4

// 3 4 5 0 1 2
// 2 3 4 n 0 1

// 0 1 2 3 4 5
// n 2 3 4 1 0

// skip 
// 4 5 0 1 2 3
// 0 1 n 2 3 4

// 4 5 0 1 2 3
// 2 3 n 4 0 1