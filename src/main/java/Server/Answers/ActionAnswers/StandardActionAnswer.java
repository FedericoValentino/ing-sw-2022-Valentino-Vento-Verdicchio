package Server.Answers.ActionAnswers;

import Server.Answers.Answer;

public abstract class StandardActionAnswer implements Answer
{
    public ACTIONANSWERTYPE type;

    public ACTIONANSWERTYPE getType() {
        return type;
    }
}
