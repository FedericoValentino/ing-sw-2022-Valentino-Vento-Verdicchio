package Server.Answers.ActionAnswers;

public class ViewMessage implements StandardActionAnswer
{
    private String jsonView;

    public ViewMessage(String jsonView) {
        this.jsonView = jsonView;
    }

    public String getJsonView() {
        return jsonView;
    }
}
