package Model;

import java.util.Date;

public class Reservation {
    int id;
    Date check_in_date;
    Date check_out_date;
    Client client;
    Room room;

    public Reservation(int id, Date check_in_date, Date check_out_date, Client client, Room room) {
        this.id = id;
        this.check_in_date = check_in_date;
        this.check_out_date = check_out_date;
        this.client = client;
        this.room = room;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getCheck_in_date() {
        return check_in_date;
    }

    public void setCheck_in_date(Date check_in_date) {
        this.check_in_date = check_in_date;
    }

    public Date getCheck_out_date() {
        return check_out_date;
    }

    public void setCheck_out_date(Date check_out_date) {
        this.check_out_date = check_out_date;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
