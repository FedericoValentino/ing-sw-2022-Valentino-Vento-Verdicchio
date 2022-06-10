package it.polimi.ingsw.Server.Answers.ActionAnswers;

import it.polimi.ingsw.Server.Answers.Answer;

public abstract class StandardActionAnswer implements Answer
{
    public ACTIONANSWERTYPE type;

    public ACTIONANSWERTYPE getType() {
        return type;
    }
}
