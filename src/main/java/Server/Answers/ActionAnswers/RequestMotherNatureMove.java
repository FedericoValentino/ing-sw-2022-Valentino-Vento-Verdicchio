package Server.Answers.ActionAnswers;

public class RequestMotherNatureMove implements StandardActionAnswer
{
    private String message;

    public RequestMotherNatureMove(String message)
    {
        this.message = "You can move Mother Nature up to " + message + " islands";
    }
}