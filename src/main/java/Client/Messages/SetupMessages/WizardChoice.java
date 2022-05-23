package Client.Messages.SetupMessages;

import model.boards.token.Wizard;

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
