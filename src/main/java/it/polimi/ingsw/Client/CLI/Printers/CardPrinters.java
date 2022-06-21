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
    public CardPrinters(LightView view)
    {
        super(view);
    }

    public void showAssistantDeck(String nickname)
    {
        String[] deck = new String[7];
        Arrays.fill(deck, "");

        LightPlayer p = null;
        for (LightTeam team : super.getView().getCurrentTeams())
        {
            for (LightPlayer player : team.getPlayers())
            {
                if(player.getNome().equals(nickname))
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
                String name = p.getNome();
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


    private String[] printCharacter(LightCharacterCard card, String[] character, int index)
    {

        String[] description = new String[7];
        description = card.getDescription();

        if(card.getName().equals(CharacterName.PRINCESS))
        {
            character[0] += ANSI_YELLOW + "____________________";
            character[1] += "|  " + ANSI_GREEN + card.getName() + ANSI_YELLOW + "        |  " + description[0];
            character[2] += "|       " + ANSI_RESET +"ID: " + addZero(index) + ANSI_YELLOW + "     |  " + description[1];
            character[3] += "|                  |  " + description[2];
            character[4] += "| " + ANSI_RESET + "Current Cost: " + addZero(card.getCurrentCost()) + ANSI_YELLOW + " |  " + description[3];
            character[5] += "|                  |  " + description[4];
            character[6] += "|  " + ANSI_RESET + printStudent(card.getStudentList(), 2);
            for(int i = 0; i < 4 - card.getStudentList().size(); i++)
            {
                character[6] += "  O";
            }
            character[6] += ANSI_YELLOW + "    |  " + description[5];
            character[7] += "|__________________|  " + ANSI_RESET + description[6] + "\n";
        }
        else if(card.getName().equals(CharacterName.PRIEST))
        {
            character[0] += ANSI_YELLOW + "____________________";
            character[1] += "|  " + ANSI_GREEN + card.getName() + ANSI_YELLOW + "          |  " + description[0];
            character[2] += "|       " + ANSI_RESET + "ID: " + addZero(index) + ANSI_YELLOW + "     |  " + description[1];
            character[3] += "|                  |  " + description[2];
            character[4] += "| " + ANSI_RESET + "Current Cost: " + addZero(card.getCurrentCost()) + ANSI_YELLOW + " |  " + description[3];
            character[5] += "|                  |  " + description[4];
            character[6] += "|  " + ANSI_RESET + printStudent(card.getStudentList(), 2);
            for(int i = 0; i < 4 - card.getStudentList().size(); i++)
            {
                character[6] += "  O";
            }
            character[6] += ANSI_YELLOW + "    |  " + description[5];
            character[7] += "|__________________|  " +  description[6] + ANSI_RESET + "\n";
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
}
