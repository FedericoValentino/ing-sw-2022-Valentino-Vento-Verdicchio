package it.polimi.ingsw.model;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Class used to serialize the parts of the model we want to send to the view through a json file
 */
public class CgSerializer extends StdSerializer<CurrentGameState> {

    public CgSerializer()
    {
        this(null);
    }

    /**
     * Class constructor, uses the parent class constructor to bind itself to the current game state
     * @param currentGame an instance of the current game state
     */
    public CgSerializer(Class<CurrentGameState> currentGame) {
        super(currentGame);
    }


    /**
     * Method serialize creates and fills the json file with serialized model objects
     * @param currentGame the current game
     * @param generator the json Generator
     * @param p
     * @throws IOException
     */
    @Override
    public void serialize(CurrentGameState currentGame, JsonGenerator generator, SerializerProvider p) throws IOException{
        generator.writeStartObject();
        generator.writeObjectField("currentIslands", currentGame.getCurrentIslands());
        generator.writeObjectField("currentTeams", currentGame.getCurrentTeams());
        generator.writeObjectField("currentClouds", currentGame.getCurrentClouds());
        generator.writeObjectField("currentMotherNature", currentGame.getCurrentMotherNature());
        generator.writeObjectField("currentTurnState", currentGame.getCurrentTurnState());
        generator.writeNumberField("bankBalance", currentGame.getBankBalance());


        generator.writeEndObject();
    }
}