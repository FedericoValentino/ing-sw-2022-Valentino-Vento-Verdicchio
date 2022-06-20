package it.polimi.ingsw.Client.GUI.Controllers;

import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Client.Messages.SetupMessages.TeamChoice;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import it.polimi.ingsw.model.boards.token.ColTow;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;


public class TeamController extends Controller
{
    @FXML private HBox AvailableTeams;

    public String getTeamColorPath(int i)
    {
        switch(ColTow.values()[i])
        {
            case WHITE:
                return "/Client/GUI/Images/Tower/white_tower_for_choice.png";
            case BLACK:
                return "/Client/GUI/Images/Tower/black_tower_for_choice.png";
            case GREY:
                return "/Client/GUI/Images/Tower/grey_tower_for_choice.png";
            default:
                return "";
        }
    }


    public void init(int[] availableTeams)
    {
        this.AvailableTeams.getChildren().clear();
        this.AvailableTeams.setSpacing(10);
        for(int i = 0; i < 3; i++)
        {
            int finalI = i;
            if(availableTeams[finalI] > 0)
            {
                Pane Team = new Pane();
                ImageView teamColor = new ImageView(getTeamColorPath(finalI));
                teamColor.setFitHeight(200);
                teamColor.setFitWidth(135);
                Team.getChildren().clear();

                Text text=new Text(String.valueOf(ColTow.values()[i]));

                VBox vBox1=new VBox();
                vBox1.getChildren().add(teamColor);
                vBox1.alignmentProperty().set(Pos.CENTER);
                vBox1.getChildren().add(text);

                HBox hBox=new HBox(vBox1);
                hBox.setPadding(new Insets(25,0,0,0));
                Team.getChildren().add(hBox);
                Team.setOnMouseClicked((MouseEvent) -> GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new TeamChoice(finalI))));
                AvailableTeams.getChildren().add(Team);
            }
        }
        AvailableTeams.setSpacing(50);
        AvailableTeams.alignmentProperty().set(Pos.CENTER);
    }
}
