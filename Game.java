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
        Player currentPlayer = getCurrentPlayer();

        System.out.println("Turn changes from: " + currentPlayer.name);

        if (topCard.face.contains(FACE.REVERSE)){
            direction = -(direction);
        }
        else if (topCard.face.contains(FACE.SKIP)){
            skip = 1;
        }

        System.out.println(direction);
        turn = turn + direction + (skip * direction);

        if (turn < 0){
            System.out.println("too small" + turn);
            turn = players.size() + turn;
            System.out.println(turn);
        }
        else if (turn >= players.size()){
            System.out.println("too big");
            turn = turn - players.size();
            System.out.println(turn);
        }

        Player nextPlayer = getCurrentPlayer();
        System.out.println("Turn changes to: " + nextPlayer.name);

        if (topCard.face.contains(FACE.DRAW)){
            nextPlayer.cards.addAll(deck.drawCards(topCard.drawNumber));
        }
        nextPlayer.selectCard(true);
    }

    /*
    public void changeTurn(boolean playerWon, Player getCurrentPlayer()){
        ArrayList<Integer> turns = new ArrayList<>();
        Card getTopCard() = stack.cards.getLast();
        Player nextPlayer = new Player("Default", this);
        int direction = getTopCard().direction;
        int skipIndex = 0;
        int skip = 1;
        int pos = 0;
        int neg = 0;
        
        System.out.println("Top card: " + getTopCard().display());
        
        if (getTopCard().face.contains(FACE.SKIP)){
            skipIndex = 1;
            skip = 2;
        }


        if (!getTopCard().face.contains(FACE.REVERSE)){
            if (playerWon){
                players.remove(getCurrentPlayer());
                for (Player p : players){
                    p.turn = direction * skip;
                }
            }
            else{
                System.out.println("direction: " + direction);
                if (direction == -1){
                    Player firstPlayeri = players.getFirst();
                    firstPlayeri.turn = players.get(players.size()-1 - skipIndex).turn;
                    if (firstPlayeri.turn == 0){
                        nextPlayer = firstPlayeri;
                    }
                    pos = 1;
                }
                else if (direction == 1){
                    Player lastPlayer = players.getLast();
                    lastPlayer.turn = players.get(0 + skipIndex).turn;
                    if (lastPlayer.turn == 0){
                        nextPlayer = lastPlayer;
                    }                   
                    neg = -1;
                }
                for (int i = 0 + pos; i < players.size() + neg; i++){
                    System.out.println("i: " + i + "dest: " + direction + "skip: " + skip);
                    players.get(i).turn += direction * skip;
                    if (players.get(i).turn == 0){
                        nextPlayer = players.get(i);
                    }
                    else if (players.get(i).turn == players.size() - 1 && skip == 2){
                        System.out.println(players.get(i).name + " got skipped");
                    }
                }
            }
        }

        // Reverse works
        else{
            for (Player p : players){
                if (playerWon){
                    players.remove(getCurrentPlayer());
                }
                turns.add(p.turn * 10);
            }
            List<Integer> reversedTurns = turns.reversed();
            for (int i = 0; i < players.size(); i++){
                System.out.println(reversedTurns);
                players.get(i).turn = reversedTurns.get(i) / 10;
                if (players.get(i).turn == 0){
                    nextPlayer = players.get(i);
                }
            }
            System.out.println("It's rewind time!");
        }


        
        if (getTopCard().face.contains(FACE.DRAW)){
            deck.drawCards(getTopCard().drawNumber);
            System.out.println(nextPlayer.name + " drew " + getTopCard().drawNumber + " cards");
        }

        for (int i = 0; i < players.size(); i++){
            if (players.get(i).turn < 0){
                players.get(i).turn = players.size() - players.get(i).turn;
            }
            else if (players.get(i).turn >= players.size()){
                System.out.println(players.get(i).name + players.get(i).turn + "TOO BIG");
                players.get(i).turn = players.get(i).turn - players.size();
            }
            System.out.println(players.get(i).name + " Turn: " + players.get(i).turn);
            
        }
        System.out.println("Turn: " + nextPlayer.name);
        nextPlayer.selectCard(true);
    }
    */

   // need to figure out how this works with draw card
    public void unoSystem(Player player){
        if (player.cards.isEmpty() && !stack.getTopCard().face.contains(FACE.DRAW)){
            if (player.unoCall){
                playerWon();
            }
            else{
                player.cards.add(deck.cards.removeLast());
                player.cards.add(deck.cards.removeLast());
                changeTurn();
            }
        }        
    }

    public void playerWon(){
        if (winners.isEmpty()){
            System.out.println(getCurrentPlayer().name + " won!");
        }
        else{
            System.out.println(getCurrentPlayer().name + " finished!");
        }
        players.remove(turn);
        changeTurn();
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