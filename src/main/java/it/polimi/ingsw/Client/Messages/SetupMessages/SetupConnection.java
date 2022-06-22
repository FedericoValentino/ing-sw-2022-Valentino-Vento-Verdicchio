package it.polimi.ingsw.Client.Messages.SetupMessages;

public class SetupConnection extends StandardSetupMessage
{
    private String nickname;
    private int team;

    /**
     * Class Constructor, this message is used to tell the server our nickname after the connection has been established
     * @param nickname
     */
    public SetupConnection(String nickname) {
        this.nickname = nickname;
        super.type = SETUPMESSAGETYPE.CONNECTION_SETUP;
    }

    public String getNickname() {
        return nickname;
    }

    public int getTeam() {
        return team;
    }

    public void setTeam(int team) {
        this.team = team;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


}
