package Client;

import Server.Answers.ActionAnswers.ViewMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.Player;
import model.Team;
import model.boards.Cloud;
import model.boards.Islands;
import model.boards.token.Col;
import model.boards.token.ColTow;
import model.boards.token.MotherNature;
import model.boards.token.Student;
import model.cards.CharacterCard;
import model.cards.CharacterDeck;

import java.util.ArrayList;


public class LightView
{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private ArrayList<CharacterCard> currentActiveCharacterCard;
    private CharacterDeck currentCharacterDeck;
    private Islands currentIslands;
    private ArrayList<Team> currentTeams;
    private MotherNature currentMotherNature;
    private Cloud[] currentClouds;
    private int bankBalance;

    public LightView()
    {

    }

    public void parse(ViewMessage view) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = view.getJsonView();
        LightView lv;
        lv = objectMapper.readValue(json, LightView.class);
        lv.currentCharacterDeck = view.getCurrentCharacterDeck();
        lv.currentActiveCharacterCard = view.getCurrentActiveCharacterCard();
        updateLightView(lv);
    }

    public void updateLightView(LightView newView)
    {
        this.currentIslands = newView.currentIslands;
        this.currentTeams = newView.currentTeams;
        this.currentMotherNature = newView.currentMotherNature;
        this.currentClouds = newView.currentClouds;
        this.currentCharacterDeck = newView.currentCharacterDeck;
        this.currentActiveCharacterCard = newView.currentActiveCharacterCard;

        System.out.println("Total Islands Groups = " + newView.currentIslands.getTotalGroups());
    }

    public String addZero(int X)
    {
        if(X < 10)
            return "0" + Integer.toString(X);
        else
            return Integer.toString(X);
    }

    public String convertTo3Char(ColTow towerColor)
    {
        if(towerColor != null)
        {
            switch(towerColor)
            {
                case GREY:
                    return "GRY";

                case WHITE:
                    return "WHT";

                case BLACK:
                    return "BLK";

                default:
                    return "   ";
            }
        }
        return "   ";
    }

    public void showIsland(int id)
    {
        String MN;
        if(currentIslands.getIslands().get(id).getMotherNature())
            MN = "X";
        else
            MN = "O";
        int StudentNumber;

        if(id != -1)
        {
            System.out.println("____________________");
            System.out.println("|   Island n: " + addZero(id) +"   |");
            System.out.println("|       MN: " + MN + "      |");
            StudentNumber = (int)currentIslands.getIslands().get(id).currentStudents.stream().filter(Student -> Student.getColor() == Col.GREEN).count();
            System.out.println("| " + ANSI_GREEN + ":D " + ANSI_RESET + addZero(StudentNumber) + "            |");
            StudentNumber = (int)currentIslands.getIslands().get(id).currentStudents.stream().filter(Student -> Student.getColor() == Col.RED).count();
            System.out.println("| " + ANSI_RED + ":D " + ANSI_RESET + addZero(StudentNumber) + "    " + convertTo3Char(currentIslands.getIslands().get(id).getOwnership())+ "     |");
            StudentNumber = (int)currentIslands.getIslands().get(id).currentStudents.stream().filter(Student -> Student.getColor() == Col.YELLOW).count();
            System.out.println("| " + ANSI_YELLOW + ":D " + ANSI_RESET + addZero(StudentNumber) + "            |");
            StudentNumber = (int)currentIslands.getIslands().get(id).currentStudents.stream().filter(Student -> Student.getColor() == Col.PINK).count();
            System.out.println("| " + ANSI_PURPLE + ":D " + ANSI_RESET + addZero(StudentNumber) + "   TOW " + addZero(currentIslands.getIslands().get(id).getTowerNumber()) + "   |");
            StudentNumber = (int)currentIslands.getIslands().get(id).currentStudents.stream().filter(Student -> Student.getColor() == Col.BLUE).count();
            System.out.println("| " + ANSI_BLUE + ":D " + ANSI_RESET + addZero(StudentNumber) + "            |");
            System.out.println("|__________________|");
        }

    }
    private String printEntrance(String nome)
    {
        String output = "";
        int entranceCount = 0;
        for(Team team: currentTeams)
        {
            for(Player player: team.getPlayers())
            {
                if(player.getNome().equals(nome))
                {
                    entranceCount = player.getSchool().getEntrance().size();
                    for(Student s: player.getSchool().getEntrance())
                    {
                        switch(s.getColor())
                        {
                            case GREEN:
                                output += ANSI_GREEN + "  X";
                                break;
                            case RED:
                                output += ANSI_RED + "  X";
                                break;
                            case YELLOW:
                                output += ANSI_YELLOW + "  X";
                                break;
                            case PINK:
                                output += ANSI_PURPLE + "  X";
                                break;
                            case BLUE:
                                output += ANSI_BLUE + "  X";
                                break;
                        }
                    }
                }
            }
        }
        for(int i = 0; i < 9 - entranceCount; i++)
        {
            output += ANSI_RESET + "  O";
        }
        return output + ANSI_RESET;
    }

    private String printDinnerTable(String nome, int dinnerPosition)
    {
        String output = "";
        String color = "";
        switch (dinnerPosition)
        {
            case 0:
                color += ANSI_GREEN;
                break;
            case 1:
                color += ANSI_RED;
                break;
            case 2:
                color += ANSI_YELLOW;
                break;
            case 3:
                color += ANSI_PURPLE;
                break;
            case 4:
                color += ANSI_BLUE;
                break;
        }
        output += color;
        for(Team team: currentTeams)
        {
            for(Player player: team.getPlayers())
            {
                if(player.getNome().equals(nome))
                {
                    for(int i = 0; i < 10; i++)
                    {
                        if(i < player.getSchool().getDiningRoom()[dinnerPosition])
                        {
                            output += " X";
                        }
                        else if(i == 2 || i == 5 || i == 8)
                        {
                            output += ANSI_WHITE + " C" + color;
                        }
                        else
                        {
                            output += ANSI_RESET + " O" + color;
                        }
                    }
                }
            }
        }
        return  output + ANSI_RESET;
    }

    private String printHasProf(String nome, int tablePosition)
    {
        String output = "";
        String color = "";
        switch (tablePosition)
        {
            case 0:
                color += ANSI_GREEN;
                break;
            case 1:
                color += ANSI_RED;
                break;
            case 2:
                color += ANSI_YELLOW;
                break;
            case 3:
                color += ANSI_PURPLE;
                break;
            case 4:
                color += ANSI_BLUE;
                break;
        }
        for(Team team: currentTeams)
        {
            for(Player player: team.getPlayers())
            {
                if(player.getNome().equals(nome))
                {
                    if(player.getSchool().getProfessorTable()[tablePosition])
                        output = color + "P";
                    else
                        output = "O";
                }
            }
        }
        return output + ANSI_RESET;
    }

    public void showSchool(String name)
    {
        if(!name.equals("-1"))
        {
            Player p = null;
            for (Team team : currentTeams) {
                for (Player player : team.getPlayers()) {
                    if (player.getNome().equals(name)) {
                        p = player;
                    }
                }
            }
            System.out.println(name);
            System.out.println("____________________________________________");
            System.out.println("|     0  1  2  3  4  5  6  7  8            |");
            System.out.println("| E:" + printEntrance(name) + ANSI_RESET + "            |");
            System.out.println("|__________________________________________|");
            System.out.println("|                                          |");
            System.out.println("| G:" + printDinnerTable(name, 0) + "  || " + printHasProf(name, 0) + "             |");
            System.out.println("| R:" + printDinnerTable(name, 1) + "  || " + printHasProf(name, 1) + "             |");
            System.out.println("| Y:" + printDinnerTable(name, 2) + "  || " + printHasProf(name, 2) + "    TOW " + addZero(p.getSchool().getTowerCount()) + "   |");
            System.out.println("| P:" + printDinnerTable(name, 3) + "  || " + printHasProf(name, 3) + "             |");
            System.out.println("| B:" + printDinnerTable(name, 4) + "  || " + printHasProf(name, 4) + "             |");
            System.out.println("|__________________________________________|");
        }
    }

    public void showCloud(int id)
    {

    }

    public void showAssistant(int id)
    {

    }

    public void showCharacter(int id)
    {

    }

    public void showPlayer(int id)
    {

    }



    public void setCurrentIslands(Islands currentIslands) {
        this.currentIslands = currentIslands;
    }

    public void setCurrentTeams(ArrayList<Team> currentTeams) {
        this.currentTeams = currentTeams;
    }

    public void setCurrentMotherNature(MotherNature currentMotherNature) {
        this.currentMotherNature = currentMotherNature;
    }

    public void setCurrentClouds(Cloud[] currentClouds) {
        this.currentClouds = currentClouds;
    }

    public void setBankBalance(int bankBalance) {
        this.bankBalance = bankBalance;
    }
}
