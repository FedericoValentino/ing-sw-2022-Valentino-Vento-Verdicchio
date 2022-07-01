package it.polimi.ingsw.Server.Answers.SetupAnswers;

import it.polimi.ingsw.Server.Answers.Answer;

/**
 * Abstract class from which the SetupAnswers inherit the parameter type and its getter
 */
public abstract class StandardSetupAnswer implements Answer {
    public SETUPANSWERTYPE type;

    public SETUPANSWERTYPE getType() {
        return type;
    }
}
