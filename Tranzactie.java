package ro.ase.moneysaver;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
enum Categorie {
    Sanatate, Casa, Cadouri, Educatie, Alimete, Salariu, Cadou, Alte
}

 enum Valuta {
    EUR, RON,DOL, CHF
}

enum MetodaPlata {
    CASH, CARD
}
@Entity(tableName = "tranzactii")
public class Tranzactie implements Serializable {
@PrimaryKey(autoGenerate = true)
private long id;
@ColumnInfo(name="suma")
    private double suma;
@ColumnInfo(name="data")
    private Date data;
@ColumnInfo(name="descriere")
    private String descriere;
@ColumnInfo(name="categorie")
    private Categorie categorie;
@ColumnInfo(name="valuta")
    private Valuta valuta;
@ColumnInfo(name="metodaPlata")
private MetodaPlata metodaPlata;
    public Tranzactie(double suma, Date data, String descriere, Valuta valuta, Categorie categorie, MetodaPlata metodaPlata) {
        this.suma = suma;
        this.data = data;
        this.descriere = descriere;
        this.valuta = valuta;
        this.categorie = categorie;
        this.metodaPlata = metodaPlata;
    }

    public double getSuma() {
        return suma;
    }

    public void setSuma(double suma) {
        this.suma = suma;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public Valuta getValuta() {
        return valuta;
    }

    public void setValuta(Valuta valuta) {
        this.valuta = valuta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public MetodaPlata getMetodaPlata() {
        return metodaPlata;
    }

    public void setMetodaPlata(MetodaPlata metodaPlata) {
        this.metodaPlata = metodaPlata;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "id=" + id +
                ", suma=" + suma +
                ", data=" + data +
                ", descriere='" + descriere + '\'' +
                ", categorie=" + categorie +
                ", valuta=" + valuta +
                ", metodaPlata=" + metodaPlata +
                '}';
    }
}
