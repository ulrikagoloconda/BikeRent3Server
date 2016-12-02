package Model;

import java.util.ArrayList;

/**
 * Created by Goloconda on 2016-12-02.
 */
public class Bikes {
    //Lite tillfällig klass, vi kan behöva bygga ut denna för att anpassa till det vi behöver
    ArrayList<Bike> bikes;

    public Bikes(){
        bikes = new ArrayList<>();
    }

    public ArrayList<Bike> getBikes() {
        return bikes;
    }

    public void setBikes(ArrayList<Bike> bikes) {
        this.bikes = bikes;
    }
}
