package Model;

public class Employe {
    private int id;
    private String name;
    private String tele;
    private String poste;
    private double salaire;

    public Employe(int id, String name, String tele, String poste, double salaire) {
        this.id = id;
        this.name = name;
        this.tele = tele;
        this.poste = poste;
        this.salaire = salaire;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getTele() {
        return tele;
    }

    public String getPoste() {
        return poste;
    }

    public double getSalaire() {
        return salaire;
    }

    @Override
    public String toString() {
        return "le nom est "+getName() ;
    }
}
