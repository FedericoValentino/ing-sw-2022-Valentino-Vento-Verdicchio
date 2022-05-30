package model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import model.CurrentGameState;

import java.io.IOException;

public class cgSerializer extends StdSerializer<CurrentGameState> {

    public cgSerializer() {
        this(null);
    }

    public cgSerializer(Class<CurrentGameState> cg) {
        super(cg);
    }

    @Override
    public void serialize(CurrentGameState cgItem, JsonGenerator jgen, SerializerProvider p) throws IOException, JsonProcessingException {
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