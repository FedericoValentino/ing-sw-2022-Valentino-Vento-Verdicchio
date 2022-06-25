package it.polimi.ingsw.Client.Messages.SetupMessages;

import static it.polimi.ingsw.Client.Messages.SetupMessages.SETUPMESSAGETYPE.*;

public class Pong extends StandardSetupMessage {

    public Pong ()
    {
        super.type = PONG;
    }
}
