import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * Name:    Craig Calvert
 * Date:    5/8/2017
 */

public class MlbController implements Initializable {

    @FXML
    private Button btnGetGame;

    @FXML
    private TextField gameDate;

    @FXML
    private TextField teamName;

    @FXML
    private Label gameStatus;

    @FXML
    private Label extraInnings;

    @FXML
    private Label homeTeam;

    @FXML
    private Label homeRecord;

    @FXML
    private Label atGame;

    @FXML
    private Label awayTeam;

    @FXML
    private Label awayRecord;

    @FXML
    private Label homeR;

    @FXML
    private Label awayR;

    @FXML
    private Label homeLine;

    @FXML
    private Label awayLine;

    @FXML
    private Label inning;

    @FXML
    private Label pbp;

    @FXML
    private Label winPitch;

    @FXML
    private ImageView winPitchPic;

    @FXML
    private Label losePitch;

    @FXML
    private ImageView losePitchPic;

    @FXML
    private Label savePitch;

    @FXML
    private ImageView savePitchPic;

    @FXML
    private Label venueLocation;

    @FXML
    private ImageView homeLogo;

    @FXML
    private ImageView awayLogo;

    @FXML
    private void handleButtonAction(ActionEvent e) {
        // Create object to access the MlbModel
        MlbModel game = new MlbModel();

        // Has the get game button been pressed?
        if (e.getSource() == btnGetGame) {
            String gameday = gameDate.getText();
            String team = MlbModel.uppercaseFirstLetters(teamName.getText().trim().toLowerCase());

            // check if the Diamondbacks have been entered, needs to be changed to D-backs to work with MLB.com's JSON
            if (team.equals("Diamondbacks")) {
                team = "D-backs";
            }

            if (game.getGame(gameday, team)) {
                gameStatus.setText(game.getGameStatus(team));
                extraInnings.setText(game.getExtraInnings(team));
                homeTeam.setText(game.getHomeCity(team) + " " + game.getHomeName(team));
                homeRecord.setText(game.getHomeRecord(team));
                atGame.setText(game.getAtGame(team));
                awayTeam.setText(game.getAwayCity(team) + " " + game.getAwayName(team));
                awayRecord.setText(game.getAwayRecord(team));
                homeR.setText(game.getHomeR(team));
                awayR.setText(game.getAwayR(team));
                homeLine.setText(game.getHomeLine(team));
                awayLine.setText(game.getAwayLine(team));
                homeLogo.setImage(game.getHomeLogo(team));
                awayLogo.setImage(game.getAwayLogo(team));
                inning.setText(game.getInning(team));
                pbp.setText(game.getPbp(team));
                winPitch.setText(game.getWinPitchName(team) + "\n" + game.getWinPitchRecord(team));
                winPitchPic.setImage(game.getWinPitchPic(team));
                losePitch.setText(game.getLosePitchName(team) + "\n" + game.getLosePitchRecord(team));
                losePitchPic.setImage(game.getLosePitchPic(team));
                savePitch.setText(game.getSavePitchName(team) + "\n" + game.getSavePitchSave(team));
                savePitchPic.setImage(game.getSavePitchPic(team));
                venueLocation.setText(game.getVenue(team) + game.getLocation(team));
            }
            else {
                if (!MlbModel.isValidDate(gameday)) {
                    gameStatus.setText("ERROR: Invalid date, please try again.");
                }
                else {
                    gameStatus.setText("No Major League Baseball games scheduled on this date.");
                }
                extraInnings.setText("");
                homeTeam.setText("");
                homeRecord.setText("");
                atGame.setText("");
                awayTeam.setText("");
                awayRecord.setText("");
                homeR.setText("");
                awayR.setText("");
                homeLine.setText("");
                awayLine.setText("");
                homeLogo.setImage(new Image("noImage.png"));
                awayLogo.setImage(new Image("noImage.png"));
                inning.setText("");
                pbp.setText("");
                winPitch.setText("");
                winPitchPic.setImage(new Image("noImage.png"));
                losePitch.setText("");
                losePitchPic.setImage(new Image("noImage.png"));
                savePitch.setText("");
                savePitchPic.setImage(new Image("noImage.png"));
                venueLocation.setText("");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

} // end MlbController class
