public class Linky {
    public int ocisleni;
    public String typ;
    public String konecnaZastavka1;
    public String konecnaZastavka2;
    public String alternetivniZastavka;
    public int delkaJizdy;
    public int pocetZastavek;
    public String nejcastejsiAutobus;

    public Linky(int ocisleni, String typ, String konecnaZastavka1, String konecnaZastavka2, String alternativniZastavka, int delkaJizdy, int pocetZastavek, String nejcastejsiAutobus) {
        this.ocisleni = ocisleni;
        this.typ = typ;
        this.konecnaZastavka1 = konecnaZastavka1;
        this.konecnaZastavka2 = konecnaZastavka2;
        this.alternetivniZastavka = alternativniZastavka;
        this.delkaJizdy = delkaJizdy;
        this.pocetZastavek = pocetZastavek;
        this.nejcastejsiAutobus = nejcastejsiAutobus;
    }

    public int getDelkaJizdy() {
        return delkaJizdy;
    }

    public int getOcisleni() {
        return ocisleni;
    }

     public String getTyp() {
        return typ;
    }

    public String getAlternetivniZastavka() {
        return alternetivniZastavka;
    }

    public void setAlternetivniZastavka(String alternetivniZastavka) {
        this.alternetivniZastavka = alternetivniZastavka;
    }

    public String getKonecnaZastavka1() {
        return konecnaZastavka1;
    }

     public String getKonecnaZastavka2() {
        return konecnaZastavka2;
    }

     public int getPocetZastavek() {
        return pocetZastavek;
    }

     public String getNejcastejsiAutobus() {
        return nejcastejsiAutobus;
    }

    public void setDelkaJizdy(int delkaJizdy) {
        this.delkaJizdy = delkaJizdy;
    }

    public void setKonecnaZastavka1(String konecnaZastavka1) {
        this.konecnaZastavka1 = konecnaZastavka1;
    }

    public void setKonecnaZastavka2(String konecnaZastavka2) {
        this.konecnaZastavka2 = konecnaZastavka2;
    }

    public void setNejcastejsiAutobus(String nejcastejsiAutobus) {
        this.nejcastejsiAutobus = nejcastejsiAutobus;
    }

    public void setOcisleni(int ocisleni) {
        this.ocisleni = ocisleni;
    }

    public void setPocetZastavek(int pocetZastavek) {
        this.pocetZastavek = pocetZastavek;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }
}
