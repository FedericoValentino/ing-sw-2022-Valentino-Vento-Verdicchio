package it.polimi.ingsw.Client.LightView.LightCards.characters;

/**
 * Enumeration containing the type of inputs the Characters need:
 * - None requires no extra input from the player, aside from activating the card
 * - Integer_1 requires the choice of one or more integers (normally indexes) regarding a single list
 * - Integer_2 requires the choice of two lists of integers (normally indexes) regarding two lists
 * - Color requires the choice of a student color
 */
public enum LightCharacterType 
{
    NONE,
    INTEGER_1,
    INTEGER_2,
    COLOR
}
