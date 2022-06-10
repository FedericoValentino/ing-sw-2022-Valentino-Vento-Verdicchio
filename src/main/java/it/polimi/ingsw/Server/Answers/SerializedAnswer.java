package it.polimi.ingsw.Server.Answers;


import it.polimi.ingsw.Server.Answers.ActionAnswers.StandardActionAnswer;
import it.polimi.ingsw.Server.Answers.SetupAnswers.StandardSetupAnswer;

import java.io.Serializable;

public class SerializedAnswer implements Serializable
{
    private static final long serialVersionUID = 7526472295622776147L;
    private StandardSetupAnswer command;
    private StandardActionAnswer action;

    public SerializedAnswer(StandardActionAnswer m)
    {
        this.command = null;
        this.action = m;
    }

    public SerializedAnswer(StandardSetupAnswer m)
    {
        this.command = m;
        this.action = null;
    }

    public StandardSetupAnswer getCommand() {
        return command;
    }

    public StandardActionAnswer getAction() {
        return action;
    }
}
