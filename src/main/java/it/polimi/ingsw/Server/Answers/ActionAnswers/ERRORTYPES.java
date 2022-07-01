package it.polimi.ingsw.Server.Answers.ActionAnswers;

/**
 * Enumeration listing the types of errors: will be useful to the CLIENT in generating client side error messages to display
 */
public enum ERRORTYPES
{
    WRONG_INPUT,
    WRONG_PHASE,
    WRONG_TURN,
    CLOUD_ERROR,
    CARD_ERROR,
    MOTHER_ERROR,
    STUD_MOVE_ERROR,
    EMPTY_POUCH,
    LAST_TURN
}
