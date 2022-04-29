package Server.Answers.ActionAnswers;

public class ErrorMessage implements StandardActionAnswer
{
    private String error;

    public ErrorMessage(String error)
    {
        this.error = error;
    }

    public String getError(){
        return error;
    }
}
