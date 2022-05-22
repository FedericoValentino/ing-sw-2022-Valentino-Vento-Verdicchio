package Server.Answers.ActionAnswers;

public class ErrorMessage extends StandardActionAnswer
{


    private String error;

    public ErrorMessage(String error)
    {
        this.error = error;
        super.type = ACTIONANSWERTYPE.ERROR;
    }

    public String getError(){
        return error;
    }
}
