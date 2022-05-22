package Server.Answers.ActionAnswers;

public class RequestMotherNatureMove extends StandardActionAnswer
{


    private String message;

    public RequestMotherNatureMove(String message)
    {
        super.type = ACTIONANSWERTYPE.MN_REQ;
        this.message = "You can move Mother Nature up to " + message + " islands";
    }
}