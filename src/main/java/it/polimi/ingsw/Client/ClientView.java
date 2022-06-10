package it.polimi.ingsw.Client;

import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Server.Answers.ActionAnswers.StandardActionAnswer;
import it.polimi.ingsw.Server.Answers.SetupAnswers.StandardSetupAnswer;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;

public interface ClientView
{
    LightView MyView = new LightView();

    void run() throws IOException, ClassNotFoundException;
    
    void setupHandler(StandardSetupAnswer answer) throws IOException;

    void messageHandler(StandardActionAnswer answer) throws JsonProcessingException;

    void readMessage() throws IOException, ClassNotFoundException;
}
