package Server.Answers.SetupAnswers;

import model.boards.token.Wizard;

import java.util.ArrayList;

public class AvailableWizards
{
    private ArrayList<Wizard> available;

    public AvailableWizards(ArrayList<Wizard> available)
    {
        this.available = available;
    }
    public ArrayList<Wizard> getAvailable() {
        return available;
    }
}
