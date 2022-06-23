package it.polimi.ingsw.Client.GUI.Controllers.BoardControllers;

import it.polimi.ingsw.Client.GUI.Controllers.Controller;
import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.LightView.LightBoards.LightIsland;
import it.polimi.ingsw.Client.LightView.LightToken.LightMotherNature;
import it.polimi.ingsw.Client.Messages.ActionMessages.MoveMN;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import it.polimi.ingsw.model.boards.token.Student;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import it.polimi.ingsw.model.boards.token.enumerations.Col;
import it.polimi.ingsw.model.boards.token.enumerations.ColTow;

import java.util.ArrayList;


public class IslandController extends Controller {

    @FXML private ImageView stud_green;
    @FXML private ImageView stud_blue;
    @FXML private ImageView stud_red;
    @FXML private ImageView stud_yellow;
    @FXML private ImageView stud_pink;
    @FXML private ImageView islandImage;
    @FXML private Text blueStudents;
    @FXML private Text redStudents;
    @FXML private Text pinkStudents;
    @FXML private Text yellowStudents;
    @FXML private Text greenStudents;
    @FXML private Text towerNumber;
    @FXML private ImageView motherNature;
    @FXML private ImageView noEntry;
    @FXML private AnchorPane islandPane;
    @FXML private Pane ownerShip;

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

        studentPlacing(island);

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

        islandImage.setImage(new Image("/Client/GUI/Images/Islands/island"+Integer.toString(ID%3)+".png"));
    }


    void studentPlacing(LightIsland island)
    {

        long counterTemp=0;

        counterTemp= island.getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.BLUE).count();
        if(counterTemp>0)
            blueStudents.setText(Long.toString(counterTemp));
       else
        {
            blueStudents.setText("");
            stud_blue.setVisible(false);
        }

        counterTemp=island.getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.RED).count();
        if(counterTemp>0)
            redStudents.setText(Long.toString(counterTemp));
        else
        {
            redStudents.setVisible(false);
            stud_red.setVisible(false);
        }

        counterTemp=island.getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.PINK).count();
        if(counterTemp>0)
            pinkStudents.setText(Long.toString(counterTemp));
        else
        {
            pinkStudents.setVisible(false);
            stud_pink.setVisible(false);
        }

        counterTemp=island.getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.YELLOW).count();
        if(counterTemp>0)
            yellowStudents.setText(Long.toString(counterTemp));
        else
        {
            yellowStudents.setVisible(false);
            stud_yellow.setVisible(false);
        }

        counterTemp=island.getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.GREEN).count();
        if(counterTemp>0)
            greenStudents.setText(Long.toString(counterTemp));
        else
        {
            greenStudents.setVisible(false);
            stud_green.setVisible(false);
        }
    }
}
