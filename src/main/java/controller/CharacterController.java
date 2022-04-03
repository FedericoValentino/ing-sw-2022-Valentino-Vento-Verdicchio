package controller;

import model.CurrentGameState;
import model.Player;
import model.boards.token.Col;
import model.boards.token.Student;
import model.cards.*;

import java.util.ArrayList;

public class CharacterController
{
    private CharacterCard pickedCard;


    public void pickCard(CurrentGameState game, int position, Player player)        //Questa funzione prende la carta dal CharDeck e la mette
    {                                                                               //nell'active deck, oltre a memorizzarla (sol temporanea)
                                                                                    // inoltre, ne updata il costo e updata il bank balance e il player balance
        pickedCard = game.getCurrentCharacterDeck().drawCard(position);
        game.getCurrentActiveCharacterCard().add(pickedCard);
        game.updateBankBalance(player);
    }

    public void effect(Priest card, CurrentGameState game, int studentPosition, int chosenIsland)
    {
        Student student = card.getStudent(studentPosition);
        game.getCurrentIslands().placeToken(student, chosenIsland);
        card.updateStudents(game.getCurrentPouch());
        deckManagement(card, game);
    }

    public void effect(Princess card, CurrentGameState game, int studentPosition, int team, String currentPlayer)
    {
        Col color = card.getStudent(studentPosition).getColor();
        int index = 0;
        for(int i=0; i< game.getCurrentTeams().get(team).getPlayers().size(); i++)
        {
            if(currentPlayer.equals(game.getCurrentTeams().get(team).getPlayers().get(i).getNome()))
                index = i;
        }
        game.getCurrentTeams().get(team).getPlayers().get(index).getSchool().placeInDiningRoom(color);
        card.updateStudents(game.getCurrentPouch());
        deckManagement(card, game);
    }

    public void effect(Herald card, CurrentGameState game, int island)
    {
        game.getCurrentIslands().getIslands().get(island).updateTeamInfluence(game.getCurrentTeams());
        game.getCurrentIslands().getIslands().get(island).calculateOwnership();
        deckManagement(card, game);
    }

    public void effect(Postman card, CurrentGameState game, String currentPlayer, int team)
    {
        int index = 0;
        for(int i=0; i< game.getCurrentTeams().get(team).getPlayers().size(); i++)
        {
            if(currentPlayer.equals(game.getCurrentTeams().get(team).getPlayers().get(i).getNome()))
                index = i;
        }
        game.getCurrentTeams().get(team).getPlayers().get(index).updateMaxMotherMovement();
        deckManagement(card, game);
    }

    public void effect(GrandmaWeed card, CurrentGameState game, int island)
    {
        game.getCurrentIslands().getIslands().get(island).updateNoEntry();
        card.updateNoEntry(-1);
        deckManagement(card, game);
    }

    public void effect(Centaur card, CurrentGameState game, int island)
    {
        game.getCurrentIslands().getIslands().get(island).towerNumber = 0;
        deckManagement(card, game);
    }

    public void effect(TruffleHunter card, CurrentGameState game, Col color, int island)
    {
        int cont = 0;
        for(int i=0; i<game.getCurrentIslands().getIslands().get(island).currentStudents.size(); i++)
        {
            if(game.getCurrentIslands().getIslands().get(island).currentStudents.get(i).getColor() == color)
            {
                game.getCurrentIslands().getIslands().get(island).currentStudents.remove(i);
                cont++;
            }
        }
        game.getCurrentIslands().getIslands().get(island).updateTeamInfluence(game.getCurrentTeams());
        game.getCurrentIslands().getIslands().get(island).calculateOwnership();
        for(int i=0; i<cont; i++)
        {
            game.getCurrentIslands().getIslands().get(island).currentStudents.add(new Student(color));
        }
        deckManagement(card, game);
    }



    private void deckManagement(CharacterCard card, CurrentGameState game)
    {
        for(int i=0; i<game.getCurrentActiveCharacterCard().size(); i++)
        {
            if(card.getIdCard() == game.getCurrentActiveCharacterCard().get(i).getIdCard())
                game.getCurrentActiveCharacterCard().remove(i);
        }
        game.getCurrentCharacterDeck().getDeck().add(card);
    }

    public CharacterCard getPickedCard()
    {
        return pickedCard;
    }
}
