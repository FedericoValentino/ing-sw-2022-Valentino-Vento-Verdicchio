package it.polimi.ingsw.Server.Answers.SetupAnswers;

import it.polimi.ingsw.Server.Answers.Answer;

public abstract class StandardSetupAnswer implements Answer {
    public SETUPANSWERTYPE type;

    public SETUPANSWERTYPE getType() {
        return type;
    }
}
