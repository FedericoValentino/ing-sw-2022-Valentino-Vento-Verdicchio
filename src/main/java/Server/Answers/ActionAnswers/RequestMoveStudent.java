package Server.Answers.ActionAnswers;

public class RequestMoveStudent extends StandardActionAnswer
{


    private String message;

    public RequestMoveStudent(String message)
    {
        super.type = ACTIONANSWERTYPE.STUD_REQ;
        this.message = "You can still move " + message + " fron the cloud";
    }
}
