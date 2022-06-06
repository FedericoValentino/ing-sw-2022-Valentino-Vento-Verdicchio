package Client.GUI.Controllers;

import Client.GUI.GuiMainStarter;
import Client.LightView.LightIsland;
import Client.LightView.LightMotherNature;
import Client.Messages.ActionMessages.MoveMN;
import Client.Messages.SerializedMessage;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import model.boards.token.Col;



public class IslandController extends Controller{

    @FXML public Text blueStudents;
    @FXML public Text redStudents;
    @FXML public Text pinkStudents;
    @FXML public Text yellowStudents;
    @FXML public Text greenStudents;
    @FXML public ImageView motherNature;
    @FXML public ImageView noEntry;
    @FXML public AnchorPane islandPane;

    private int islandID;
    private LightMotherNature MN;
    private int totalIslands;

    public void onClick(MouseEvent event)
    {
        int amount;
        if(MN.getIdPosition() > islandID)
        {
            amount = islandID + totalIslands - MN.getIdPosition();
        }
        else
        {
            amount = islandID - MN.getIdPosition();
        }

        GuiMainStarter.getClientGUI().getServerConnection().sendMessage(new SerializedMessage(new MoveMN(amount)));
    }

    public void setup(LightIsland island, int ID, LightMotherNature Mother, int total)
    {
        this.MN = Mother;
        islandID = ID;
        this.totalIslands = total;
        blueStudents.setText(Long.toString(island.getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.BLUE).count()));
        redStudents.setText(Long.toString(island.getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.RED).count()));
        pinkStudents.setText(Long.toString(island.getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.PINK).count()));
        yellowStudents.setText(Long.toString(island.getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.YELLOW).count()));
        greenStudents.setText(Long.toString(island.getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.GREEN).count()));
        motherNature.setVisible(island.isMotherNature());
        noEntry.setVisible(island.isNoEntry());
        islandPane.setOnMouseClicked(this::onClick);
    }

    public void randomImage()
    {

    }
}
