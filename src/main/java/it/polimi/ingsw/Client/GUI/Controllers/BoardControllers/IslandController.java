package it.polimi.ingsw.Client.GUI.Controllers.BoardControllers;
//
import it.polimi.ingsw.Client.GUI.Controllers.Controller;
import it.polimi.ingsw.Client.GUI.GUIUtilities;
import it.polimi.ingsw.Client.GUI.GuiMainStarter;
import it.polimi.ingsw.Client.LightView.LightBoards.LightIsland;
import it.polimi.ingsw.Client.LightView.LightToken.LightMotherNature;
import it.polimi.ingsw.Client.LightView.LightView;
import it.polimi.ingsw.Client.Messages.ActionMessages.MoveMN;
import it.polimi.ingsw.Client.Messages.SerializedMessage;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import it.polimi.ingsw.model.boards.token.enumerations.Col;

/**
 * GUI controller responsible for the graphical representation and the functions associated with the single Island
 */
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
    @FXML private Text islandIDText;

    private int islandID;
    private LightView view;
    private int totalIslands;

    /**
     * This method calculates the amount of movement for mother nature and sends the appropriate message to the server
     * @param event is the click on an island
     */
    public void onClick(MouseEvent event)
    {
        LightMotherNature MN = view.getCurrentMotherNature();
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


    /**
     * This method fills the island with students, no entry tokens, mother nature and the towers if present
     *
     * @param island contains all the information the island need to be filled with
     * @param ID is the island ID
     * @param view is the LightView. It is saved in the controller so that the onClick
     *               function can work out the movement amount from mother nature
     * @param total is the total number of islands
     * @param angle is a parameter used to place in the right spot the noEntry token and the island ID
     */
    public void setup(LightIsland island, int ID, LightView view, int total, double angle)
    {
        towerNumber.setVisible(false);
        ownerShip.setVisible(false);
        this.view = view;
        islandID = ID;
        this.totalIslands = total;

        studentPlacing(island);

        motherNature.setVisible(island.isMotherNature());
        noEntry.setVisible(island.isNoEntry());
        noEntry.setLayoutX(100 + 90 * Math.cos(angle));
        noEntry.setLayoutY(100 - 90 * Math.sin(angle));
        islandIDText.setLayoutX(100 + 90 * Math.cos(angle + Math.PI));
        islandIDText.setLayoutY(100 - 90 * Math.sin(angle + Math.PI));
        islandIDText.setText(String.valueOf(islandID));

        islandPane.setOnMouseClicked(this::onClick);
        if(island.getOwnership() != null)
        {
            ownerShip.getChildren().clear();
            ImageView ownershipImage = new ImageView(GUIUtilities.getSchoolColorPath(island.getOwnership()));
            ownershipImage.setFitWidth(21);
            ownershipImage.setFitHeight(21);
            ownerShip.getChildren().add(ownershipImage);
            ownerShip.setVisible(true);
            towerNumber.setText(Integer.toString(island.getTowerNumber()));
            towerNumber.setVisible(true);
        }

        islandImage.setImage(new Image("/Client/GUI/Images/Islands/island"+ ID % 3 +".png"));
    }


    /**
     * This method place the student into the island only if there are, showing the pane with the color of that student
     * and the number of student of the same color in it (so if the number of a student of some color is
     * 0 it doesn't show the pane and the text)
     *
     * @param island is used because it contains the student to place into the island
     */
    void studentPlacing(LightIsland island)
    {

        long counterTemp= island.getCurrentStudents().stream().filter(Student -> Student.getColor() == Col.BLUE).count();
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
