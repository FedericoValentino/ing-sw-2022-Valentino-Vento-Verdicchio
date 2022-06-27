package it.polimi.ingsw.Server.Answers.ActionAnswers;

import it.polimi.ingsw.Server.Answers.ActionAnswers.ACTIONANSWERTYPE;
import it.polimi.ingsw.Server.Answers.ActionAnswers.StandardActionAnswer;

public class ErrorMessage extends StandardActionAnswer
{


    private ERRORTYPES error;

    /**
     * Class Constructor, this message is used to tell the client about his errors
     */
    public ErrorMessage(ERRORTYPES error)
    {
        this.error = error;
        super.type = ACTIONANSWERTYPE.ERROR;
    }

    public ERRORTYPES getError(){
        return error;
    }
}
