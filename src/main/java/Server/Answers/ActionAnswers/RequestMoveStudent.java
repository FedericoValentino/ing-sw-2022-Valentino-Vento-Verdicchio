package Server.Answers.ActionAnswers;

public class RequestMoveStudent implements StandardActionAnswer
{
    private String message;

    public RequestMoveStudent(String message)
    {
        this.message = "You can still move " + message + " fron the cloud";
    }
}
