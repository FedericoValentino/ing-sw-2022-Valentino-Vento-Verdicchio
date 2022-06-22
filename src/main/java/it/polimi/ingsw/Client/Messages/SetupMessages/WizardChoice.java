package it.polimi.ingsw.Client.Messages.SetupMessages;

import it.polimi.ingsw.model.boards.token.enumerations.Wizard;

public class WizardChoice extends StandardSetupMessage
{
    private Wizard wizard;

    /**
     * Class Constructor, this message is used to tell the server about the player's wizard choice
     * @param w
     */
    public WizardChoice(Wizard w)
    {
        this.wizard = w;
        super.type = SETUPMESSAGETYPE.WIZARD_CHOICE;
    }

    public Wizard getWizard() {
        return wizard;
    }
}
