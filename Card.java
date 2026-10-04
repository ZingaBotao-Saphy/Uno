public class Card{
    FACE[] face;
    COLOUR colour;
    int number;
    int drawNumber;
    int destination; // 1 is forward, -1 is backward, 2 is skip

    public String display(){
        return "card";
    }

    public Card(FACE[] face, COLOUR colour) {
        this(face, colour, -1, 0, 1);
    }

    public Card(FACE[] face, COLOUR colour, int number, int drawNumber, int destination) {
        this.face = face;
        this.colour = colour;
        this.number = number;
        this.drawNumber = drawNumber;
        this.destination = destination;
    }
}