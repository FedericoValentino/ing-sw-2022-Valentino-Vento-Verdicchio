package Client;

import Client.LightView.LightView;
import Server.Answers.ActionAnswers.StandardActionAnswer;
import Server.Answers.SetupAnswers.StandardSetupAnswer;
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
