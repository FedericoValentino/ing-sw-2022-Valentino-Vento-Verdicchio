package Server.Answers.ActionAnswers;

public class RequestCloud implements StandardActionAnswer
{
    private String message;

    public RequestCloud(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
