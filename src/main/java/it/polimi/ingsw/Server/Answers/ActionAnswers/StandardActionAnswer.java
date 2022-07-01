package it.polimi.ingsw.Server.Answers.ActionAnswers;

import it.polimi.ingsw.Server.Answers.Answer;

/**
 * Abstract class from which the ActionAnswers inherit the parameter type and its getter
 */
public abstract class StandardActionAnswer implements Answer
{
    public ACTIONANSWERTYPE type;

    public ACTIONANSWERTYPE getType() {
        return type;
    }
}
