package sergioapps.pronosticapp.adaptador;

/**
 * Created by drago on 15/02/2018.
 */

public class Atributos{
    private String dia;
    private String temp;

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getTemp() {
        return temp;
    }

    public void setTemp(String temp) {
        this.temp = temp;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    private int image;

    public Atributos(String dia,String temp,int image){
        this.dia = dia;
        this.temp =temp;
        this.image=image;
    }


}
