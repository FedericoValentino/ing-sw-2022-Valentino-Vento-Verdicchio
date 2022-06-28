package it.polimi.ingsw.Client.CLI.Printers;

import it.polimi.ingsw.Client.LightView.LightCards.characters.LightCharacterCard;
import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.cards.assistants.AssistantCard;
import org.fusesource.jansi.AnsiConsole;

import java.util.Arrays;

public class CardPrinters extends PrinterCLI
{
    /** Class constructor; through the parent class constructor, it grants access to the view
     * @param view the LightView coming from the PrinterCLI class
     */
    public CardPrinters(LightView view)
    {
        super(view);
    }


    /** Upon player's command, it prints the player's assistant deck. Of course, each player can see only his deck.
     * @param nickname the nickname of the player requesting to see his deck
     */
    public void showAssistantDeck(String nickname)
    {
        String[] deck = new String[7];
        Arrays.fill(deck, "");

        LightPlayer p = null;
        for (LightTeam team : super.getView().getCurrentTeams())
        {
            for (LightPlayer player : team.getPlayers())
            {
                if(player.getName().equals(nickname))
                {
                    p = player;
                }
            }
        }
        AnsiConsole.out().println(ANSI_GREEN + "[" + p.getLightAssistantDeck().getWizard().name() + "]" + ANSI_RESET + "'s deck");
        for(int i = 0; i < p.getLightAssistantDeck().getDeck().size(); i++)
        {
            deck[0] += "__________ ";
            deck[1] += "|   " + addZero(i) + "   | ";
            deck[2] += "|        | ";
            deck[3] += "|Value:" + addZero(p.getLightAssistantDeck().getDeck().get(i).getValue()) + "| ";
            deck[4] += "|        | ";
            deck[5] += "|Moves:" + addZero(p.getLightAssistantDeck().getDeck().get(i).getMovement()) + "| ";
            deck[6] += "|________| ";
        }

        for(String output : deck)
        {
            AnsiConsole.out().println(output);
        }
    }

    private void printPlayedAssistant(String[] card, boolean currentlyPlayed, String name, AssistantCard assistantCard)
    {
        card[0] += name+ "      ";
        card[1] += "__________     ";
        if(currentlyPlayed)
            card[2] += "|   " + ANSI_GREEN + "CP" + ANSI_RESET + "   |     ";
        else
            card[2] += "|   " + ANSI_RED + "LP" + ANSI_RESET + "   |     ";
        card[3] += "|        |     ";
        card[4] += "|Value:" + addZero(assistantCard.getValue()) + "|     ";
        card[5] += "|        |     ";
        card[6] += "|Moves:" + addZero(assistantCard.getMovement()) + "|     ";
        card[7] += "|________|     ";
    }


    /** Upon player's input, it shows the currently played and last played assistant cards of all players
     */
    public void showPlayedCards()
    {
        String[] currentlyPlayed = new String[8];
        String[] lastPlayed = new String[8];
        Arrays.fill(currentlyPlayed, "");
        Arrays.fill(lastPlayed, "");
        int currentCardCounter = 0;
        int lastCardCounter = 0;

        for(LightTeam t: super.getView().getCurrentTeams())
        {
            for(LightPlayer p: t.getPlayers())
            {
                String name = p.getName();
                String nameTrimmed = nameTrimmer(name);
                if(p.getCurrentAssistantCard() == null)
                {
                    currentCardCounter += 1;
                }
                else
                {
                    printPlayedAssistant(currentlyPlayed, true, nameTrimmed, p.getCurrentAssistantCard());
                }

                if(p.getLastPlayedCard() == null)
                {
                    lastCardCounter += 1;
                }
                else
                {
                    printPlayedAssistant(lastPlayed, false, nameTrimmed, p.getLastPlayedCard());
                }
            }
        }

        AnsiConsole.out().println( ANSI_GREEN + "Currently played" + ANSI_RESET + " assistant cards");

        if(currentCardCounter == super.getView().getCurrentTeams().size() * super.getView().getCurrentTeams().get(0).getPlayers().size())
            AnsiConsole.out().println("Ops! Nothing to show yet!");
        else
        {
            for (int i = 0; i < 8; i++) {
                AnsiConsole.out().println(currentlyPlayed[i]);
            }
        }
        System.out.println();

        AnsiConsole.out().println(ANSI_RED+ "Last played" + ANSI_RESET + " assistant cards");

        if(lastCardCounter == super.getView().getCurrentTeams().size() * super.getView().getCurrentTeams().get(0).getPlayers().size())
            System.out.println("Ops! Nothing to show yet!");
        else
        {
            for (int i = 0; i < 8; i++) {
                AnsiConsole.out().println(lastPlayed[i]);
            }
            if (lastCardCounter == super.getView().getCurrentTeams().size() * super.getView().getCurrentTeams().get(0).getPlayers().size())
                System.out.println("Ops! Nothing to show yet!");
        }

        System.out.println();
    }


    /** Upon player's input, it shows the character card in the game, and if they are currently active or not
     */
    public void showCharacters()
    {
        String[] inactiveCharacter = new String[8];
        String[] activeCharacter = new String[8];
        Arrays.fill(inactiveCharacter, "");
        if(super.getView().getCurrentCharacterDeck() == null)
        {
            System.out.println("You're not playing with expert mode");
            return;
        }
        AnsiConsole.out().println( ANSI_GREEN + "Inactive Cards:" + ANSI_RESET);
        for(int i = 0; i < super.getView().getCurrentCharacterDeck().getLightCharDeck().size(); i++)
        {
            printCharacter(super.getView().getCurrentCharacterDeck().getCard(i), inactiveCharacter, i);
            for(int j = 0; j < 8; j++)
            {
                AnsiConsole.out().println(inactiveCharacter[j]);
            }
            Arrays.fill(inactiveCharacter, "");
        }

        Arrays.fill(activeCharacter, "");
        AnsiConsole.out().println(ANSI_RED + "Active Card" + ANSI_RESET);
        for(int i = 0; i < super.getView().getCurrentActiveCharacterCard().getLightActiveDeck().size(); i++)
        {
            printCharacter(super.getView().getCurrentActiveCharacterCard().getLightActiveDeck().get(i), activeCharacter, i);
            for(int j = 0; j < 8; j++)
            {
                AnsiConsole.out().println(activeCharacter[j]);
            }
        }
    }

    /** Used by the function "showCharacters", it fills the strings received in input to form the graphical representation of
     * the cards and their content, based on the type of card. Since a lot of cards can be represented in the same way, it isn't
     * necessary to use more than four if branches.
     * @param card the card to generate
     * @param character the string to manipulate
     * @param index the index of the card in its deck
     */
    private void printCharacter(LightCharacterCard card, String[] character, int index)
    {
        String[] description = card.getDescription();
        character[0] = ANSI_YELLOW + "____________________";
        character[1] += "|  " + ANSI_GREEN + card.getName() + ANSI_YELLOW;
        for(int i = 0; i < "TRUFFLE_HUNTER".length() - card.getName().toString().length(); i++)
        {
            character[1] += " ";
        }
        character[1] += "  |  " + description[0];
        character[2] = "|       " + ANSI_RESET +"ID: " + addZero(index) + ANSI_YELLOW + "     |  " + description[1];
        character[3] = "|                  |  " + description[2];
        character[4] = "| " + ANSI_RESET + "Current Cost: " + addZero(card.getCurrentCost()) + ANSI_YELLOW + " |  " + description[3];
        character[5] = "|                  |  " + description[4];
        character[6] = characterDifferentiate(card);
        character[7] = "|__________________|  " +  description[6] + ANSI_RESET + "\n";
    }

    /** Auxiliary method that adapts a critical line of the character representation according to certain character types.
     * For example, some characters will need to represent a list of students, some nothing at all.
     * @param card the card used to give the correct representation
     * @return the string that will correspond to character[6] into the character string array
     */
    private String characterDifferentiate(LightCharacterCard card)
    {
        StringBuilder line = new StringBuilder();
        String[] description;
        description = card.getDescription();

        if(card.getName().equals(CharacterName.PRINCESS) || card.getName().equals(CharacterName.PRIEST))
        {
            line.append("|  " + ANSI_RESET).append(printStudent(card.getStudentList(), 2));
            for(int i = 0; i < 4 - card.getStudentList().size(); i++)
            {
                line.append("  O");
            }
            line.append(ANSI_YELLOW + "    |  ").append(description[5]);
        }
        else if(card.getName().equals(CharacterName.JESTER))
        {
            line.append("|  " + ANSI_RESET).append(printStudent(card.getStudentList(), 1));
            for(int i = 0; i < 6 - card.getStudentList().size(); i++)
            {
                line.append("  O");
            }
            line.append(ANSI_YELLOW + "    |  ").append(description[5]);
        }
        else if (card.getName().equals(CharacterName.GRANDMA_HERBS))
        {
            line.append("|  " + ANSI_RESET);
            for(int i = 0; i < card.getNoEntry(); i++)
            {
                line.append(ANSI_RED_BACKGROUND + ANSI_BLACK + "  !" + ANSI_RESET);
            }
            for(int i = 0; i < 4 - card.getNoEntry(); i++)
            {
                line.append("  O");
            }
            line.append(ANSI_YELLOW + "    |  ").append(description[5]);
        }
        else
        {
            return "|                  |  " + description[5];
        }
        return line.toString();
    }
}
