package Client.GUI.Controllers;
//
import Client.GUI.GuiMainStarter;
import Client.LightView.LightCloud;
import Client.LightView.LightTurnState;
import Client.Messages.ActionMessages.ChooseCloud;
import Client.Messages.ActionMessages.DrawFromPouch;
import Client.Messages.SerializedMessage;
import Observer.ObserverLightView;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.boards.token.GamePhase;
import model.boards.token.Student;



public class CloudController extends Controller implements ObserverLightView
{
    LightTurnState turn;
    private int playerNumber;

    public GridPane clouds;
    public GridPane cloud0;
    public GridPane cloud1;
    public GridPane cloud2;
    public GridPane cloud3;


    public void CloudSelection(MouseEvent event)
    {
        if(turn.getGamePhase() == GamePhase.PLANNING)
        {
            GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new DrawFromPouch(Integer.parseInt(((StackPane)event.getSource()).getId().replace("stack_", "")))));
        }
        else if(turn.getGamePhase() == GamePhase.ACTION)
        {
            for(Node cell : ((GridPane)event.getSource()).getChildren())
            {
                cell.setVisible(false);
            }
            GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new ChooseCloud(Integer.parseInt(((StackPane)event.getSource()).getId().replace("stack_", "")))));
        }
    }

    public void setup(LightCloud[] cloudsArray, LightTurnState turnState)
    {
        turn = turnState;
        playerNumber = cloudsArray.length;
        int temp = playerNumber;
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
