package Model;


public class Room {
    int id;
    int nombre_lits;
    Double price;
    int room_number;
    String Status;

    public Room(int id, int nombre_lits, Double price, int room_number, String status) {
        this.id = id;
        this.nombre_lits = nombre_lits;
        this.price = price;
        this.room_number = room_number;
        Status = status;
    }
    public  Room(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNombre_lits() {
        return nombre_lits;
    }

    public void setNombre_lits(int nombre_lits) {
        this.nombre_lits = nombre_lits;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public int getRoom_number() {
        return room_number;
    }

    public void setRoom_number(int room_number) {
        this.room_number = room_number;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
