package Client.Messages.SetupMessages;

public class SetupConnection implements StandardSetupMessage
{
    private String nickname;
    private int team;

    public String getNickname() {
        return nickname;
    }
    public void setTeam(int team) {
        this.team = team;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getTeam() {
        return team;
    }
}
