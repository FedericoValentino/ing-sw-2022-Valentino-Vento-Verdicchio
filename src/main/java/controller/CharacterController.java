package controller;

import model.CurrentGameState;
import model.Player;
import model.boards.token.Col;
import model.boards.token.Student;
import model.cards.CharacterCard;
import model.cards.Priest;
import model.cards.Princess;

import java.util.ArrayList;

public class CharacterController
{
    private CharacterCard pickedCard;


    public void pickCard(CurrentGameState game, int position, Player player)        //Questa funzione prende la carta dal CharDeck e la mette
    {                                                                               //nell'active deck, oltre a memorizzarla (sol temporanea)
        CharacterCard card;                                                         //inoltre, ne updata il costo e updata il bank balance e il player balance
        card = game.getCurrentCharacterDeck().drawCard(position);
        pickedCard = card;
        game.getCurrentActiveCharacterCard().add(card);
        game.updateBankBalance(player);
    }

    public void effect(Priest card, CurrentGameState game, int studentPosition, int chosenIsland)
    {
        Student student = card.getStudent(studentPosition);
        game.getCurrentIslands().placeToken(student, chosenIsland);
    }

    public void effect(Princess card, CurrentGameState game, int studentPosition, int team, String currentPlayer)
    {
        Col color = card.getStudent(studentPosition).getColor();
        ArrayList<Player> players = game.getCurrentTeams().get(team).getPlayers();
        int index;
        for(int i=0; i< players.size(); i++)
        {
            if(currentPlayer == players.get(i).getNome())
            {
                index = i;
            }
        }

    }

}
