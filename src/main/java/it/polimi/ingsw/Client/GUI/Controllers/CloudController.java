package it.polimi.ingsw.Client.GUI.Controllers;
//
import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.LightView.LightCloud;
import it.polimi.ingsw.Client.LightView.LightTurnState;
import it.polimi.ingsw.Client.Messages.ActionMessages.ChooseCloud;
import it.polimi.ingsw.Client.Messages.ActionMessages.DrawFromPouch;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.Observer.ObserverLightView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import it.polimi.ingsw.model.boards.token.GamePhase;
import it.polimi.ingsw.model.boards.token.Student;



public class CloudController extends Controller implements ObserverLightView
{
    private LightTurnState turn;

    @FXML private GridPane clouds;
    @FXML private GridPane cloud0;
    @FXML private GridPane cloud1;
    @FXML private GridPane cloud2;
    @FXML private GridPane cloud3;


    public void CloudSelection(MouseEvent event)
    {
        if(turn.getGamePhase() == GamePhase.PLANNING)
        {
            GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new DrawFromPouch(Integer.parseInt(((StackPane)event.getSource()).getId().replace("stack_", "")))));
        }
        else if(turn.getGamePhase() == GamePhase.ACTION)
        {
            int cloudIndex = Integer.parseInt(((StackPane)event.getSource()).getId().replace("stack_", ""));
            GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new ChooseCloud(cloudIndex)));
        }
    }

    public void setup(LightCloud[] cloudsArray, LightTurnState turnState)
    {
        turn = turnState;
        int temp = cloudsArray.length;
        for(int i = 0; i < 2 && temp > 0; i++)
        {
            for(int j = 0; j < 2 && temp > 0; j++)
            {
                StackPane pane = (StackPane) getCellFromGridPane(clouds, j, i);
                pane.setVisible(true);
                pane.setOnMouseClicked(this::CloudSelection);
                temp--;
            }
        }
        for(LightCloud c: cloudsArray)
        {
            c.addObserverLight(this);
        }
    }


    public Node getCellFromGridPane(GridPane matrix, int column, int row)
    {
        for(Node N : matrix.getChildren())
        {
            int rowN = GridPane.getRowIndex(N);
            int columnN = GridPane.getColumnIndex(N);
            if(rowN == row && columnN == column)
            {
                return N;
            }
        }
        return null;
    }

    public String getRightColorPath(Student s)
    {
        switch(s.getColor())
        {
            case GREEN:
                return "/Client/GUI/Images/Student/student_green.png";
            case YELLOW:
                return "/Client/GUI/Images/Student/student_yellow.png";
            case RED:
                return "/Client/GUI/Images/Student/student_red.png";
            case BLUE:
                return "/Client/GUI/Images/Student/student_blue.png";
            case PINK:
                return "/Client/GUI/Images/Student/student_pink.png";
            default:
                return "";
        }
    }

    public void fill(GridPane fillable, LightCloud filler)
    {
        int temp = filler.getStudents().size();
        for(int i = 0; i < 2; i++)
        {
            for(int j = 0; j < 2; j++)
            {
                Pane cell = (Pane)getCellFromGridPane(fillable, j, i);
                cell.setVisible(false);
            }
        }
        for(int i = 0; i < 2 && temp > 0; i++)
        {
            for(int j = 0; j < 2 && temp > 0; j++)
            {
                Pane cell = (Pane)getCellFromGridPane(fillable, j, i);
                cell.setVisible(true);
                cell.getChildren().clear();
                cell.getChildren().add(new ImageView(getRightColorPath(filler.getStudents().get(temp-1))));
                temp--;
            }
        }
    }

    @Override
    public void update(Object o)
    {
        Platform.runLater(()->{
            LightCloud cloud = (LightCloud) o;
            switch(cloud.getCloudID())
            {
                case 0:
                    fill(cloud0, cloud);
                    break;
                case 1:
                    fill(cloud1, cloud);
                    break;
                case 2:
                    fill(cloud2, cloud);
                    break;
                case 3:
                    fill(cloud3, cloud);
                    break;
                default:
                    break;
            }
        });


    }
}
