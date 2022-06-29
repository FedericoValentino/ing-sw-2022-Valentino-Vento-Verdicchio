package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Server.Answers.ActionAnswers.StandardActionAnswer;
import it.polimi.ingsw.Server.Answers.SetupAnswers.StandardSetupAnswer;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface ClientView
{
    LightView MyView = new LightView();

    void run();
    
    void setupHandler(StandardSetupAnswer answer);

    void messageHandler(StandardActionAnswer answer);

    void readMessage();

    default void checkUsername(String nickname)
    {
        nickname.replace(" ", "_");
    }
}
