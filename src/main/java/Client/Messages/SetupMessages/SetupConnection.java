package Client.Messages.SetupMessages;

public class SetupConnection implements StandardSetupMessage
{
    private String nickname;
    private int team;

    public SetupConnection(String nickname, int team) {
        this.nickname = nickname;
        this.team = team;
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
