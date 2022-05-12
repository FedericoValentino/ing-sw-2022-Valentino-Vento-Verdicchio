package Server.Answers.SetupAnswers;

public class InfoMessage implements StandardSetupAnswer
{
    private String info;

    public InfoMessage(String input)
    {
        this.info = input;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
