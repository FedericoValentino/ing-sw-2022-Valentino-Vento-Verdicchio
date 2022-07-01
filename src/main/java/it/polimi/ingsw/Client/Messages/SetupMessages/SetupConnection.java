package it.polimi.ingsw.Client.Messages.SetupMessages;

/**
 * Message containing the player's nickname
 */
public class SetupConnection extends StandardSetupMessage
{
    private String nickname;

    /**
     * Class Constructor, this message is used to tell the server our nickname after the connection has been established
     */
    public SetupConnection(String nickname) {
        this.nickname = nickname;
        super.type = SETUPMESSAGETYPE.CONNECTION_SETUP;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
