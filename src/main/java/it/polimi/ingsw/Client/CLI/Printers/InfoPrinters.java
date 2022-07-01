package it.polimi.ingsw.Client.CLI.Printers;

import it.polimi.ingsw.Client.LightView.LightBoards.LightIsland;
import it.polimi.ingsw.Client.LightView.LightTeams.LightPlayer;
import it.polimi.ingsw.Client.LightView.LightTeams.LightTeam;
import it.polimi.ingsw.Client.LightView.LightUtilities.Utilities;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import org.fusesource.jansi.AnsiConsole;

import java.util.ArrayList;
/**
 * Class InfoPrinters hosts all the methods necessary to print to screen the various info obtained from the view
 */
public class InfoPrinters extends PrinterCLI
{

    /**
     * Class constructor; through the parent class constructor, it grants access to the view
     * @param view the LightView coming from the PrinterCLI class
     */
    public InfoPrinters(LightView view)
    {
        super(view);
    }


    /**
     * Upon player's input, it shows a short list of information about the selected player and its team, such as controlled
     * islands, number of towers left, and other useful information
     * @param name the name of the player to show
     */
    public void showPlayer(String name)
    {
        LightPlayer player = Utilities.findPlayerByName(getView(), name);
        if(player == null)
            AnsiConsole.out().println( ANSI_RED_BACKGROUND + ANSI_BLACK + "Sorry, player not found. Are you sure the spelling was correct?" + ANSI_RESET);
        LightTeam team = Utilities.getPlayerTeam(getView(), name);
        if(team == null)
            AnsiConsole.out().println( ANSI_RED_BACKGROUND + ANSI_BLACK + "Sorry, player not found. Are you sure the spelling was correct?" + ANSI_RESET);
        AnsiConsole.out().println(ANSI_CYAN +  name + ANSI_RESET + "'s information:");
        AnsiConsole.out().println(team.getColor() + " Team");
        if(team.getControlledIslands() != 0) {
            if (team.getControlledIslands() == 1)
                AnsiConsole.out().println(ANSI_CYAN + name + ANSI_RESET + " and their team control " + team.getControlledIslands() + ANSI_GREEN + " island" + ANSI_RESET + ", (" + ANSI_YELLOW + controlledIslands(team) + ANSI_RESET + ")");
            else
                AnsiConsole.out().println(ANSI_CYAN + name + ANSI_RESET + " and their team control " + team.getControlledIslands() + ANSI_GREEN + " islands" + ANSI_RESET + ", (" + ANSI_YELLOW + controlledIslands(team) + ANSI_RESET + ")");
        }
        else
            AnsiConsole.out().println(ANSI_CYAN + name + ANSI_RESET + " and their team control no " + ANSI_GREEN + "islands" + ANSI_RESET + " yet");

        if(team.getControlledProfessors().size() != 0)
            AnsiConsole.out().println(ANSI_CYAN + name + ANSI_RESET + " and their team control these " + ANSI_RED + "professors" + ANSI_RESET + ":" + team.getControlledProfessors());
        else
            AnsiConsole.out().println(ANSI_CYAN + name + ANSI_RESET + " and their team control no " + ANSI_RED + "professors" + ANSI_RESET + " at the moment");

        if(player.isTowerOwner())
        {
            if(player.getSchool().getTowerCount() < 3) {
                if (player.getSchool().getTowerCount() == 1)
                    AnsiConsole.out().println(ANSI_CYAN + name + ANSI_RESET + " and their team have " + player.getSchool().getTowerCount() + ANSI_BLUE + " tower" + ANSI_RESET + " left. They are getting close!");
                else
                    AnsiConsole.out().println(ANSI_CYAN + name + ANSI_RESET + " and their team have " + player.getSchool().getTowerCount() + ANSI_BLUE + " towers" + ANSI_RESET + " left. They are getting close!");
            }
            else{
                if(player.getSchool().getTowerCount() == 1)
                    AnsiConsole.out().println(ANSI_CYAN +  name + ANSI_RESET + " and their team have " + player.getSchool().getTowerCount() + ANSI_BLUE + " tower" + ANSI_RESET + " left");
                else
                    AnsiConsole.out().println(ANSI_CYAN + name + ANSI_RESET + " and their team have " + player.getSchool().getTowerCount() + ANSI_BLUE + " towers" + ANSI_RESET + " left");
            }

        }
        if(player.getMaxMotherMovement() == 0)
            AnsiConsole.out().println(ANSI_CYAN + name + ANSI_RESET + " hasn't played an assistant card yet!");
        else
            AnsiConsole.out().println(ANSI_CYAN + name + ANSI_RESET + " can move " + ANSI_GREEN + "Mother Nature" + ANSI_RESET + " up to " + player.getMaxMotherMovement() + " spaces");
        System.out.println();
    }


    /**
     * Calls the aforementioned "showPlayer" function to show information on all the players, to grant a  strategic
     * ensemble view on the game
     */
    public void showPlayers()
    {
        for(LightTeam team: super.getView().getCurrentTeams())
        {
            for(LightPlayer player: team.getPlayers())
            {
                showPlayer(player.getName());
            }
        }
    }

    /**
     * Prints to screen, along with the MainBoard, the player's coins and the current BankBalance
     * @param playerName the player's nickname
     */
    public void printEconomy(String playerName)
    {
        LightPlayer player = Utilities.findPlayerByName(super.getView(), playerName);
        AnsiConsole.out().println("Your " + ANSI_YELLOW + "coins " + ANSI_RESET + ": " + player.getCoinAmount());
        AnsiConsole.out().println("Bank Balance: " + super.getView().getBankBalance());
    }


    /**
     * Upon player's input, it shows a brief but sufficiently detailed description of the main board and of all the commands
     * a player can use
     */
    public void showHelp()
    {
        AnsiConsole.out().println("Welcome to Eryantis! The game automatically displays on your screen the " + ANSI_GREEN + "Game Board" + ANSI_RESET + ", which comprises:");
        AnsiConsole.out().println(ANSI_YELLOW + ">" + ANSI_RESET + " The " + ANSI_CYAN + "Clouds" + ANSI_RESET + " and the students they contain");
        AnsiConsole.out().println(ANSI_YELLOW + ">" + ANSI_RESET + " The " + ANSI_YELLOW + "Island" + ANSI_RESET + " tiles, on which you can see the island id, the contained students, the number of towers, the team that eventually controls the island and whether mother nature is currently present");
        AnsiConsole.out().println("  If Mother nature is on the Island, you will se an X next to the MN field; you will see an O in the other case");
        AnsiConsole.out().println(ANSI_YELLOW + ">" + ANSI_RESET + " The players' " + ANSI_RED + "Schools" + ANSI_RESET + ", on which you can see the students in the entrance and in the dining room, the controlled professors and the remaining towers");
        AnsiConsole.out().println();
        AnsiConsole.out().println("COMMANDS");
        AnsiConsole.out().println("To give a command simply type the command you want to give and then press the " + ANSI_GREEN + "[Enter]" + ANSI_RESET + " key on your keyboard.");
        AnsiConsole.out().println("Here follows a list of the accepted commands.");
        AnsiConsole.out().println();
        AnsiConsole.out().println(ANSI_GREEN + "Action" + ANSI_RESET + " commands");
        AnsiConsole.out().println(ANSI_YELLOW + ">" + ANSI_RESET + " move");
        AnsiConsole.out().println(ANSI_RED + "  >>" + ANSI_RESET + " student");
        AnsiConsole.out().println(ANSI_PURPLE + "     >>" + ANSI_RESET + " toisland " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t\t moves a student from your entrance to the desired island");
        AnsiConsole.out().println(ANSI_PURPLE + "     >>" + ANSI_RESET + " todining\t\t\t moves a student from your entrance to your dining room");
        AnsiConsole.out().println(ANSI_RED + "  >>" + ANSI_RESET + " mothernature " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t\t where X is an integer number, moves mother nature of the desired number of spaces");
        AnsiConsole.out().println(ANSI_YELLOW + ">" + ANSI_RESET + " draw assistantcard " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t lets you play the assistant card at the X index in your deck, where X is an integer number");
        AnsiConsole.out().println(ANSI_YELLOW + ">" + ANSI_RESET + " refill " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t\t\t\t lets you refill the selected cloud (must be empty) of students: X is an integer and identifies the cloud to refill");
        AnsiConsole.out().println(ANSI_YELLOW + ">" + ANSI_RESET + " refillfrom " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t\t\t use the cloud specified by the X integer to refill your entrance of students");
        AnsiConsole.out().println(ANSI_YELLOW + ">" + ANSI_RESET + " play " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t\t\t\t\t choose the character card specified by the index (the X integer) and pay its cost");
        AnsiConsole.out().println(ANSI_YELLOW + ">" + ANSI_RESET + " activate\t\t\t\t\t activate the effect of the character card you have previously played");
        AnsiConsole.out().println(ANSI_YELLOW + ">" + ANSI_RESET + " endturn\t\t\t\t\t write this to end your turn");
        AnsiConsole.out().println();
        AnsiConsole.out().println(ANSI_CYAN + "Info" + ANSI_RESET + " commands");
        AnsiConsole.out().println(ANSI_YELLOW + ">" + ANSI_RESET + " show");
        AnsiConsole.out().println(ANSI_RED + "  >>" + ANSI_RESET + " island " + ANSI_CYAN + "[X]" + ANSI_RESET + "\t\t\t\t displays the selected island");
        AnsiConsole.out().println(ANSI_RED + "  >>" + ANSI_RESET + " islands\t\t\t\t displays all islands");
        AnsiConsole.out().println(ANSI_RED + "  >>" + ANSI_RESET + " school " + ANSI_CYAN + "[playerName]" + ANSI_RESET + "\t displays the specified player's school");
        AnsiConsole.out().println(ANSI_RED + "  >>" + ANSI_RESET + " schools\t\t\t\t displays all schools");
        AnsiConsole.out().println(ANSI_RED + "  >>" + ANSI_RESET + " clouds\t\t\t\t\t displays all clouds");
        AnsiConsole.out().println(ANSI_RED + "  >>" + ANSI_RESET + " assistants\t\t\t\t displays your assistant deck");
        AnsiConsole.out().println(ANSI_RED + "  >>" + ANSI_RESET + " playedcards\t\t\t displays the assistant cards currently active and the last assistant cards played");
        AnsiConsole.out().println(ANSI_RED + "  >>" + ANSI_RESET + " characters\t\t\t\t shows active and inactive character cards");
        AnsiConsole.out().println(ANSI_RED + "  >>" + ANSI_RESET + " player " + ANSI_CYAN + "[playerName]" + ANSI_RESET + "\t displays the desired player's game info");
        AnsiConsole.out().println(ANSI_RED + "  >>" + ANSI_RESET + " players\t\t\t\t shows all players' game info");
    }


    /**
     * Used by the "showPlayer" function, it returns a list of the islands controlled by the specified team
     * @param team the desired team
     * @return the list of the islands controlled by the team
     */
    private ArrayList<String> controlledIslands(LightTeam team)
    {
        ArrayList<String> islands = new ArrayList<>();
        for(LightIsland island : super.getView().getCurrentIslands().getIslands())
        {
            if(island.getOwnership() != null)
                if(island.getOwnership().equals(team.getColor()))
                    islands.add(addZero(island.getIslandId()));
        }
        return islands;
    }

    /**
     * Prints the current Turn number
     */
    public void printTurn(){
        AnsiConsole.out().println( ANSI_GREEN + "Turn " + ANSI_RESET + super.getView().getCurrentTurnState().getTurn());
    }

    /**
     * given a dining room, forms an Arraylist of colors to return to the caller
     * @param diningRoom the given dining room
     * @return a list of colors
     */
    public ArrayList<Col> printAvailableColors(int[] diningRoom)
    {
        StringBuilder colors = new StringBuilder();
        ArrayList<Col> available = new ArrayList<>();
        for(int i = 0; i < 5; i++)
        {
            if(diningRoom[i] > 0)
            {
                colors.append(Col.values()[i]).append(" ");
                available.add(Col.values()[i]);
            }
        }
        System.out.println(colors);
        //TODO are we sure we are returning the right thing?
        return available;
    }
}
