package Client.LightView;

import com.fasterxml.jackson.annotation.JsonProperty;

public class LightMotherNature
{
    private int idPosition;

    public LightMotherNature(@JsonProperty("position") int idPosition)
    {
        this.idPosition = idPosition;
    }

    public int getIdPosition() {
        return idPosition;
    }
}
