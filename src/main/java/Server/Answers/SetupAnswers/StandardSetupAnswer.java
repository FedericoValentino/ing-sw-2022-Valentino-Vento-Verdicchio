package Server.Answers.SetupAnswers;

import Server.Answers.ActionAnswers.ACTIONANSWERTYPE;
import Server.Answers.Answer;

public abstract class StandardSetupAnswer implements Answer {
    public SETUPANSWERTYPE type;

    public SETUPANSWERTYPE getType() {
        return type;
    }
}
