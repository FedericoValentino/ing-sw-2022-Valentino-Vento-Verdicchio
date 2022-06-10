package it.polimi.ingsw.Server.Answers.ActionAnswers;

public class RequestCloud extends StandardActionAnswer {


    private String message;

    public RequestCloud(String message) {
        this.message = message;
        super.type = ACTIONANSWERTYPE.CLOUD_REQ;
    }

    public String getMessage() {
        return message;
    }
}
