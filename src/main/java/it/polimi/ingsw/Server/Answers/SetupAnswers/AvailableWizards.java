package it.polimi.ingsw.Server.Answers.SetupAnswers;

import it.polimi.ingsw.model.boards.token.enumerations.Wizard;

import java.util.ArrayList;

/**
 * This Message is used to tell the clients the available wizards
 */
public class AvailableWizards extends StandardSetupAnswer {
    private ArrayList<Wizard> available;

    /**
     * Class Constructor.
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
