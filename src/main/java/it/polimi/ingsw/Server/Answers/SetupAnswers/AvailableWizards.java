package it.polimi.ingsw.Server.Answers.SetupAnswers;

import it.polimi.ingsw.model.boards.token.enumerations.Wizard;

import java.util.ArrayList;

public class AvailableWizards extends StandardSetupAnswer {
    private ArrayList<Wizard> available;

    /**
     * Class Constructor, this message is used to tell the clients the available wizards
     */
    public AvailableWizards(ArrayList<Wizard> available)
    {
        this.available = available;
        super.type = SETUPANSWERTYPE.WIZARDS;
    }
    public ArrayList<Wizard> getAvailable() {
        return available;
    }
}
