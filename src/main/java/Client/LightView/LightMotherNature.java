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
        if(light.equals(this))
        {
            return;
        }
        else
        {
            this.idPosition = light.getIdPosition();
            notifyLight(this);
        }
    }

    public int getIdPosition() {
        return idPosition;
    }
}
