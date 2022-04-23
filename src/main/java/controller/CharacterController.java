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
        int gain = pickedCard.getCurrentCost() - 1;
        game.updateBankBalance(player, gain);
    }

    public static void effect(Priest card, CurrentGameState game, int studentPosition, int chosenIsland)           //Prende lo studente dalla carta di tipo priest
    {                                                                                                                 //Prende l'isola giusta da game e usa place token per piazzarlo
                                                                                                                    //UpdateStudents pesca dal pouch e rimette uno studente sulla carta
        game.getCurrentIslands().placeToken(card.getStudent(studentPosition), chosenIsland);                                         //Deckmgmt elimina la vecchia priest dalle active e piazza questa updatata nel charDeck
        card.updateStudents(game.getCurrentPouch());
        deckManagement(card, game);
    }

    public static void effect(Princess card, CurrentGameState game, int studentPosition, String currentPlayer)       //Prende student dalla carta e ne salva il colore
    {
        Col color = card.getStudent(studentPosition).getColor();
        int teamIndex = 0;
        int playerIndex = 0;
        for(int i=0; i<game.getCurrentTeams().size(); i++)
        {
            for(int j=0; j< game.getCurrentTeams().get(i).getPlayers().size(); j++)
            {
                if(currentPlayer.equals(game.getCurrentTeams().get(i).getPlayers().get(j).getNome()))
                    playerIndex = j;
                teamIndex = i;
            }
        }
        game.getCurrentTeams().get(teamIndex).getPlayers().get(playerIndex).getSchool().placeInDiningRoom(color);         //Prendo la sucola del currentPlayer e ci piazzo il colore
        card.updateStudents(game.getCurrentPouch());                                                                      //Updato studenti su carta e solita deckmgmt
        deckManagement(card, game);
    }

    public static void effect(Herald card, CurrentGameState game, int island)                                          //Banalmente risolve subito l'isola selezionata
    {
        game.getCurrentIslands().getIslands().get(island).updateTeamInfluence(game.getCurrentTeams());
        game.getCurrentIslands().getIslands().get(island).calculateOwnership();
        deckManagement(card, game);
    }

    public static void effect(Postman card, CurrentGameState game, String currentPlayer)                     //Cerca CurrentPlayer come prima e updata il maxmotherMovement con +2(model)
    {
        int teamIndex = 0;
        int playerIndex = 0;
        for(int i=0; i<game.getCurrentTeams().size(); i++)
        {
            for(int j=0; j< game.getCurrentTeams().get(i).getPlayers().size(); j++)
            {
                if(currentPlayer.equals(game.getCurrentTeams().get(i).getPlayers().get(j).getNome()))
                    playerIndex = j;
                teamIndex = i;
            }
        }
        game.getCurrentTeams().get(teamIndex).getPlayers().get(playerIndex).updateMaxMotherMovement(2);
        deckManagement(card, game);
    }

    public static void effect(GrandmaWeed card, CurrentGameState game, int island)         //Cerca isola e mette a true il NoEntry (vedi metodo model)
    {
        game.getCurrentIslands().getIslands().get(island).updateNoEntry();
        card.updateNoEntry(-1);                                                //Updata i NoEntry sulla carta
        deckManagement(card, game);
    }

    public static void effect(Centaur card, CurrentGameState game, int island)               //Va sull'isola e toglie le torri in funzione del calcolo influenza
    {                                                                                  //le riaggiorna alla fine
        int temp = game.getCurrentIslands().getIslands().get(island).towerNumber;
        game.getCurrentIslands().getIslands().get(island).towerNumber = 0;
        game.getCurrentIslands().getIslands().get(island).updateTeamInfluence(game.getCurrentTeams());
        game.getCurrentIslands().getIslands().get(island).calculateOwnership();
        game.getCurrentIslands().getIslands().get(island).towerNumber = temp;
        deckManagement(card, game);
    }

    public static void effect(TruffleHunter card, CurrentGameState game, Col color, int island)                //Complessa. Va sull'isola e rimuove dal currentStudents di questa
    {                                                                                                           //gli studenti del colore desiderato. Si salva un contatore per sapere quanti ne toglie
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

    public static void effect(Knight card, CurrentGameState game, int island, int team)                                //Chiama Update teams influence per calcolare l'influenza
    {                                                                                                           //chiama l'altro metodo (overloading) per aumentare di 2
        game.getCurrentIslands().getIslands().get(island).updateTeamInfluence(game.getCurrentTeams());          //l'influenza del team scelto
        game.getCurrentIslands().getIslands().get(island).updateTeamInfluence(2, team);                   //calcola ownership
        game.getCurrentIslands().getIslands().get(island).calculateOwnership();                                 //rimette a posto l'influenza
        game.getCurrentIslands().getIslands().get(island).updateTeamInfluence(-2, team);
        deckManagement(card, game);
    }





    public static void deckManagement(CharacterCard card, CurrentGameState game)                                  //Toglie la carta usata non aggiornata dall'active e
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