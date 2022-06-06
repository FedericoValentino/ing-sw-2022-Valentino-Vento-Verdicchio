package Client.LightView;

import Observer.Observable;
import com.fasterxml.jackson.annotation.JsonProperty;

public class LightMotherNature extends Observable
{
    private int idPosition;

    public LightMotherNature(@JsonProperty("position") int idPosition)
    {
        this.idPosition = idPosition;
    }

    public void updateMother(LightMotherNature light)
    {
        this.idPosition = light.getIdPosition();
        System.out.println("Updated LightMN");
        notifyLight(this);
    }

    public int getIdPosition() {
        return idPosition;
    }
}
