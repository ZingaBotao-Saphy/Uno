
import java.util.ArrayList;

public class Card{
    ArrayList<FACE> face;
    COLOUR colour;
    int number;
    int drawNumber;
    int destination; // 1 is forward, -1 is backward

    public Card(ArrayList<FACE> face, COLOUR colour) {
        this(face, colour, -1, 0, 1);
    }

    public Card(ArrayList<FACE> face, COLOUR colour, int number, int drawNumber, int destination) {
        this.face = face;
        this.colour = colour;
        this.number = number;
        this.drawNumber = drawNumber;
        this.destination = destination;
    }

    public String display(){
        String draw = "";
        String facePart = face.toString();
        if (drawNumber != 0){
            draw = "D: " + drawNumber;
        }
        if (face.contains(FACE.NUMBER)){
            facePart = Integer.toString(number);
        }

        return colour + " " + facePart + " " + draw;
    }
}