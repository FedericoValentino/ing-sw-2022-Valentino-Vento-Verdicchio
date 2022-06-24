package it.polimi.ingsw.Client.CLI.Printers;

import it.polimi.ingsw.Client.LightView.LightCards.characters.LightCharacterCard;
import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
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
                    currentlyPlayed[0] += nameTrimmed + "      ";
                    currentlyPlayed[1] += "__________     ";
                    currentlyPlayed[2] += "|   " + ANSI_GREEN + "CP" + ANSI_RESET + "   |     ";
                    currentlyPlayed[3] += "|        |     ";
                    currentlyPlayed[4] += "|Value:" + addZero(p.getCurrentAssistantCard().getValue()) + "|     ";
                    currentlyPlayed[5] += "|        |     ";
                    currentlyPlayed[6] += "|Moves:" + addZero(p.getCurrentAssistantCard().getMovement()) + "|     ";
                    currentlyPlayed[7] += "|________|     ";
                }
                if(p.getLastPlayedCard() == null)
                {
                    lastCardCounter += 1;
                }
                else
                {
                    lastPlayed[0] += nameTrimmed + "      ";
                    lastPlayed[1] += "__________     ";
                    lastPlayed[2] += "|   " + ANSI_RED + "LP" + ANSI_RESET + "   |     ";
                    lastPlayed[3] += "|        |     ";
                    lastPlayed[4] += "|Value:" + addZero(p.getLastPlayedCard().getValue()) + "|     ";
                    lastPlayed[5] += "|        |     ";
                    lastPlayed[6] += "|Moves:" + addZero(p.getLastPlayedCard().getMovement()) + "|     ";
                    lastPlayed[7] += "|________|     ";
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


    /** Used by the function "showCharacters", it fills the strings received in input to form the graphical representation of
     * the cards and their content, based on the type of card. Since a lot of cards can be represented in the same way, it isn't
     * necessary to use more than four if branches.
     * @param card the card to generate
     * @param character the string to manipulate
     * @param index the index of the card in its deck
     * @return the manipulated string, containing the drawing of the card
     */
    private String[] printCharacter(LightCharacterCard card, String[] character, int index)
    {

        String[] description = new String[7];
        description = card.getDescription();

        if(card.getName().equals(CharacterName.PRINCESS))
        {
            return printCharacterWithStudents(card, 2, 4, index, character);
        }
        else if(card.getName().equals(CharacterName.PRIEST))
        {
            return printCharacterWithStudents(card, 2, 4, index, character);
        }
        else if(card.getName().equals(CharacterName.JESTER))
        {
            return printCharacterWithStudents(card, 1, 6, index, character);
        }
        else if (card.getName().equals(CharacterName.GRANDMA_HERBS))
        {
            character[0] += ANSI_YELLOW + "____________________";
            character[1] += "|  " + ANSI_GREEN + card.getName() + ANSI_YELLOW + "   |  " + description[0];
            character[2] += "|       " + ANSI_RESET + "ID: " + addZero(index) + ANSI_YELLOW + "     |  " + description[1];
            character[3] += "|                  |  " + description[2];
            character[4] += "| " + ANSI_RESET + "Current Cost: "+ addZero(card.getCurrentCost()) + ANSI_YELLOW + " |  " + description[3];
            character[5] += "|                  |  " + description[4];
            character[6] += "|  " + ANSI_RESET;
            for(int i = 0; i < card.getNoEntry(); i++)
            {
                character[6] += ANSI_RED_BACKGROUND + ANSI_BLACK + "  !" + ANSI_RESET;
            }
            for(int i = 0; i < 4 - card.getNoEntry(); i++)
            {
                character[6] += "  O";
            }
            character[6] += ANSI_YELLOW + "    |  " + description[5];
            character[7] += "|__________________|  " + ANSI_RESET + description[6] + "\n";
        }
        else
        {
            character[0] += ANSI_YELLOW + "____________________";
            character[1] += "|  " + ANSI_GREEN + card.getName() + ANSI_YELLOW;
            for(int i = 0; i < "TRUFFLE_HUNTER".length() - card.getName().toString().length(); i++)
            {
                character[1] += " ";
            }
            character[1] += "  |  " + description[0];
            character[2] += "|       " + ANSI_RESET + "ID: " + addZero(index) + ANSI_YELLOW + "     |  " + description[1];
            character[3] += "|                  |  " + description[2];
            character[4] += "| " + ANSI_RESET + "Current Cost: "+ addZero(card.getCurrentCost()) + ANSI_YELLOW + " |  " + description[3];
            character[5] += "|                  |  " + description[4];
            character[6] += "|                  |  " + description[5];
            character[7] += "|__________________|  " + ANSI_RESET + description[6] + "\n";
        }
        return character;
    }


    /** Upon player's input, it shows the character card in the game, and if they are currently active or not
     */
    public void showCharacters()
    {
        String[] inactiveCharacter = new String[8];
        Arrays.fill(inactiveCharacter, "");
        if(super.getView().getCurrentCharacterDeck() == null)
        {
            System.out.println("You're not playing with expert mode");
            return;
        }
        AnsiConsole.out().println( ANSI_GREEN + "Inactive Cards:" + ANSI_RESET);
        for(int i = 0; i < super.getView().getCurrentCharacterDeck().getLightCharDeck().size(); i++)
        {
            inactiveCharacter = printCharacter(super.getView().getCurrentCharacterDeck().getCard(i), inactiveCharacter, i);
            for(int j = 0; j < 8; j++)
            {
                AnsiConsole.out().println(inactiveCharacter[j]);
            }
            Arrays.fill(inactiveCharacter, "");
        }

        Arrays.fill(inactiveCharacter, "");
        AnsiConsole.out().println(ANSI_RED + "Active Card" + ANSI_RESET);
        for(int i = 0; i < super.getView().getCurrentActiveCharacterCard().getLightActiveDeck().size(); i++)
        {
            inactiveCharacter = printCharacter(super.getView().getCurrentActiveCharacterCard().getLightActiveDeck().get(i), inactiveCharacter, i);
            for(int j = 0; j < 8; j++)
            {
                AnsiConsole.out().println(inactiveCharacter[j]);
            }
        }
    }

    private String[] printCharacterWithStudents(LightCharacterCard card, int spaces, int students, int index, String[] character)
    {
        String[] description = card.getDescription();
        character[0] += ANSI_YELLOW + "____________________";
        character[1] += "|  " + ANSI_GREEN + card.getName() + ANSI_YELLOW + "        |  " + description[0];
        character[2] += "|       " + ANSI_RESET +"ID: " + addZero(index) + ANSI_YELLOW + "     |  " + description[1];
        character[3] += "|                  |  " + description[2];
        character[4] += "| " + ANSI_RESET + "Current Cost: " + addZero(card.getCurrentCost()) + ANSI_YELLOW + " |  " + description[3];
        character[5] += "|                  |  " + description[4];
        character[6] += "|  " + ANSI_RESET + printStudent(card.getStudentList(), spaces);
        for(int i = 0; i < students - card.getStudentList().size(); i++)
        {
            character[6] += "  O";
        }
        character[6] += ANSI_YELLOW + "    |  " + description[5];
        character[7] += "|__________________|  " + ANSI_RESET + description[6] + "\n";
        return character;
    }
}
