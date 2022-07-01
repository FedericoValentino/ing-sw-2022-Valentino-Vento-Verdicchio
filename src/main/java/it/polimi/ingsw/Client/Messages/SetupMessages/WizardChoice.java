package it.polimi.ingsw.Client.Messages.SetupMessages;

import it.polimi.ingsw.model.boards.token.enumerations.Wizard;

/**
 * Message used to tell the server which Wizard the player wishes to use
 */
public class WizardChoice extends StandardSetupMessage
{
    private Wizard wizard;

    /**
     * Class Constructor, sets up the wizard choice
     */
    public WizardChoice(Wizard wizard)
    {
        this.wizard = wizard;
        super.type = SETUPMESSAGETYPE.WIZARD_CHOICE;
    }

    public Wizard getWizard() {
        return wizard;
    }
}
