package it.polimi.ingsw.Client.Messages;

import java.io.Serializable;
/**
 * Message interface, extending serializable. Its existence is justified by the desire to Unify StandardActionMessage and
 * StandardSetupMessage under a unique designation as messages (so it's for enhanced legibility and hierarchy)
 */
public interface Message extends Serializable {
}
