package it.polimi.ingsw.Client.LightView.LightCards.characters;

import it.polimi.ingsw.model.boards.token.enumerations.CharacterName;
import it.polimi.ingsw.model.boards.token.Student;

import java.util.ArrayList;

public class LightCharacterCard
{
    private CharacterName name;
    private int baseCost;
    private int uses;
    private int currentCost;
    private int noEntry;
    private ArrayList<Student> studentList = new ArrayList<>();
    private String[] description;
    private LightCharacterType type;

    public LightCharacterCard(CharacterName name, int baseCost, int uses, int currentCost, int noEntry, ArrayList<Student> studentList, String[] description, LightCharacterType type)
    {
        this.name = name;
        this.baseCost = baseCost;
        this.uses = uses;
        this.currentCost = currentCost;
        this.noEntry = noEntry;
        this.studentList = studentList;
        this.description = description;
        this.type = type;
    }

    public CharacterName getName() {
        return name;
    }

    public int getBaseCost() {
        return baseCost;
    }

    public int getUses() {
        return uses;
    }

    public int getCurrentCost() {
        return currentCost;
    }

    public int getNoEntry() {
        return noEntry;
    }

    public ArrayList<Student> getStudentList() {
        return studentList;
    }

    public String[] getDescription() {
        return description;
    }

    public LightCharacterType getType() {
        return type;
    }
}
