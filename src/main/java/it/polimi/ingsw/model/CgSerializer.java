package it.polimi.ingsw.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

public class CgSerializer extends StdSerializer<CurrentGameState> {

    public CgSerializer(Class<CurrentGameState> cg) {
        super(cg);
    }

    @Override
    public void serialize(CurrentGameState cgItem, JsonGenerator jgen, SerializerProvider p) throws IOException{
        jgen.writeStartObject();
        jgen.writeObjectField("currentIslands", cgItem.getCurrentIslands());
        jgen.writeObjectField("currentTeams", cgItem.getCurrentTeams());
        jgen.writeObjectField("currentClouds", cgItem.getCurrentClouds());
        jgen.writeObjectField("currentMotherNature", cgItem.getCurrentMotherNature());
        jgen.writeObjectField("currentTurnState", cgItem.getCurrentTurnState());
        jgen.writeNumberField("bankBalance", cgItem.getBankBalance());


        jgen.writeEndObject();
    }
}