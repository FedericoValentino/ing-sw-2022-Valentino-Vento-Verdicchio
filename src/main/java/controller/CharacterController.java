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

    public void effect(Priest card, CurrentGameState game, int studentPosition, int chosenIsland)           //Prende lo studente dalla carta di tipo priest
    {                                                                                                       //Prende l'isola giusta da game e usa place token per piazzarlo
        Student student = card.getStudent(studentPosition);                                                 //UpdateStudents pesca dal pouch e rimette uno studente sulla carta
        game.getCurrentIslands().placeToken(student, chosenIsland);                                         //Deckmgmt elimina la vecchia priest dalle active e piazza questa updatata nel charDeck
        card.updateStudents(game.getCurrentPouch());
        deckManagement(card, game);
    }

    public void effect(Princess card, CurrentGameState game, int studentPosition, int team, String currentPlayer)       //Prende student dalla carta e ne salva il colore
    {
        Col color = card.getStudent(studentPosition).getColor();
        int index = 0;                                                                                      //Cerca a che indice di Teams sta il currentPlayer
        for(int i=0; i< game.getCurrentTeams().get(team).getPlayers().size(); i++)
        {
            if(currentPlayer.equals(game.getCurrentTeams().get(team).getPlayers().get(i).getNome()))
                index = i;
        }
        game.getCurrentTeams().get(team).getPlayers().get(index).getSchool().placeInDiningRoom(color);         //Prendo la sucola del currentPlayer e ci piazzo il colore
        card.updateStudents(game.getCurrentPouch());                                                           //Updato studenti su carta e solita deckmgmt
        deckManagement(card, game);
    }

    public void effect(Herald card, CurrentGameState game, int island)                                          //Banalmente risolve subito l'isola selezionata
    {
        game.getCurrentIslands().getIslands().get(island).updateTeamInfluence(game.getCurrentTeams());
        game.getCurrentIslands().getIslands().get(island).calculateOwnership();
        deckManagement(card, game);
    }

    public void effect(Postman card, CurrentGameState game, String currentPlayer, int team)                     //Cerca CurrentPlayer come prima e updata il maxmotherMovement con +2(model)
    {
        int index = 0;
        for(int i=0; i< game.getCurrentTeams().get(team).getPlayers().size(); i++)
        {
            if(currentPlayer.equals(game.getCurrentTeams().get(team).getPlayers().get(i).getNome()))
                index = i;
        }
        game.getCurrentTeams().get(team).getPlayers().get(index).updateMaxMotherMovement(2);
        deckManagement(card, game);
    }

    public void effect(GrandmaWeed card, CurrentGameState game, int island)         //Cerca isola e mette a true il NoEntry (vedi metodo model)
    {
        game.getCurrentIslands().getIslands().get(island).updateNoEntry();
        card.updateNoEntry(-1);                                                //Updata i NoEntry sulla carta
        deckManagement(card, game);
    }

    public void effect(Centaur card, CurrentGameState game, int island)               //Va sull'isola e toglie le torri in funzione del calcolo influenza
    {
        game.getCurrentIslands().getIslands().get(island).towerNumber = 0;
        deckManagement(card, game);
    }

    public void effect(TruffleHunter card, CurrentGameState game, Col color, int island)        //Complessa. Va sull'isola e rimuove dal currentStudents di questa
    {                                                                                           //gli studenti del colore desiderato. Si salva un contatore per sapere quanti ne toglie
        int cont = 0;
        for(int i=0; i<game.getCurrentIslands().getIslands().get(island).currentStudents.size(); i++)
        {
            if(game.getCurrentIslands().getIslands().get(island).currentStudents.get(i).getColor() == color)
            {
                game.getCurrentIslands().getIslands().get(island).currentStudents.remove(i);
                cont++;
            }
        }
        game.getCurrentIslands().getIslands().get(island).updateTeamInfluence(game.getCurrentTeams());          //Calcola influenza con nuova roba
        game.getCurrentIslands().getIslands().get(island).calculateOwnership();
        for(int i=0; i<cont; i++)
        {
            game.getCurrentIslands().getIslands().get(island).currentStudents.add(new Student(color));          //Riempie di nuovo gli studenti nell'isola creandone
        }                                                                                                       //di nuovi quanti ne indica il contatore
        deckManagement(card, game);
    }



    private void deckManagement(CharacterCard card, CurrentGameState game)                                  //Toglie la carta usata non aggiornata dall'active e
    {                                                                                                       //mette la copia aggiornata nel charcterCard (inactive)
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
