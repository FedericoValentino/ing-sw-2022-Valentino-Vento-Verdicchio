package it.polimi.ingsw.Client.Messages.SetupMessages;

import it.polimi.ingsw.model.boards.token.enumerations.Wizard;

public class WizardChoice extends StandardSetupMessage
{
    private Wizard wizard;

    public WizardChoice(Wizard w)
    {
        this.wizard = w;
        super.type = SETUPMESSAGETYPE.WIZARD_CHOICE;
    }

    public Wizard getWizard() {
        return wizard;
    }
}
