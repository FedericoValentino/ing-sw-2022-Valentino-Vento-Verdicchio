package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Server.Answers.ActionAnswers.StandardActionAnswer;
import it.polimi.ingsw.Server.Answers.SetupAnswers.StandardSetupAnswer;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

/**
 * Interface containing common methods between CLI and GUI
 */
public interface ClientView
{
    LightView MyView = new LightView();

    void run();
    
    void setupHandler(StandardSetupAnswer answer);

    void messageHandler(StandardActionAnswer answer);

    void readMessage();

    /**
     * Default method that replaces the spaces in a username with underscores
     * @param nickname
     */
    default void checkUsername(String nickname)
    {
        nickname.replace(" ", "_");
    }
}
