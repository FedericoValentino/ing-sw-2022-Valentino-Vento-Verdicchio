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
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import model.boards.token.Col;
import model.boards.token.ColTow;


public class IslandController extends Controller{

    @FXML public Text blueStudents;
    @FXML public Text redStudents;
    @FXML public Text pinkStudents;
    @FXML public Text yellowStudents;
    @FXML public Text greenStudents;
    @FXML public Text towerNumber;
    @FXML public ImageView motherNature;
    @FXML public ImageView noEntry;
    @FXML public AnchorPane islandPane;
    @FXML public Pane ownerShip;

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

    public String getTowerColorPath(ColTow color)
    {
        switch(color)
        {
            case WHITE:
                return "/Client/GUI/Images/Tower/white_tower.png";
            case BLACK:
                return "/Client/GUI/Images/Tower/black_tower.png";
            case GREY:
                return "/Client/GUI/Images/Tower/grey_tower.png";
            default:
                return "";
        }
    }

    public void setup(LightIsland island, int ID, LightMotherNature Mother, int total)
    {
        towerNumber.setVisible(false);
        ownerShip.setVisible(false);
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
        if(island.getOwnership() != null)
        {
            ownerShip.getChildren().clear();
            ImageView ownershipImage = new ImageView(getTowerColorPath(island.getOwnership()));
            ownershipImage.setFitWidth(21);
            ownershipImage.setFitHeight(21);
            ownerShip.getChildren().add(ownershipImage);
            ownerShip.setVisible(true);
            towerNumber.setText(Integer.toString(island.getTowerNumber()));
            towerNumber.setVisible(true);
        }

    }
}
