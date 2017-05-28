import com.google.gson.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import javafx.scene.image.Image;
import java.util.Calendar;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;


/**
 * Name:    Craig Calvert
 * Date:    5/8/2017
 */

public class MlbModel {

    private JsonElement jse = null;

    // get JSON data from mlb.com and verify if a game has taken place that day
    public boolean getGame(String gameDate, String team) {

        // check if the date is a valid input format
        if (isValidDate(gameDate)) {

            String str[] = gameDate.split("/");

            String month = str[0];
            String day = str[1];
            String year = str[2];

            // check that month is in the correct format of two digits
            if (month.length() == 1) {
                // add "0" month will be formatted correctly
                month = "0" + month;
            }
            // check that day is in the correct format of two digits
            if (day.length() == 1) {
                // add "0" month will be formatted correctly
                day = "0" + day;
            }
            // check that the year is in the correct format of four digits
            if (year.length() == 2) {
                // add "20" so that year will be formatted correctly
                year = "20" + year;
            }
            // check that the year is in the correct format of four digits
            if (year.length() == 1) {
                // add "200" so that year will be formatted correctly
                year = "200" + year;
            }

            // check if year is before 2007, mlb.com's JSON only includes data for 2007 - present
            if (Integer.parseInt(year) < 2007) {
                return false;
            }
            // check if the date entered is for 2017 and the dates following October 1st.
            if (Integer.parseInt(day) > 1 && Integer.parseInt(month) >= 10 && Integer.parseInt(year) == 2017) {
                return false;
            }

            try {
                // construct mlb.com API URL
                URL gameURL = new URL("http://mlb.mlb.com/gdcross/components/game/mlb/year_" + year + "/month_" +
                        month + "/day_" + day + "/master_scoreboard.json");

                // open the URL
                InputStream is = gameURL.openStream(); // throws an IOException
                BufferedReader br = new BufferedReader(new InputStreamReader(is));

                // read the results into a JSON element
                jse = new JsonParser().parse(br);

                // close the connection
                is.close();
                br.close();
            }
            catch (java.io.UnsupportedEncodingException uee) {

            }
            catch (java.io.FileNotFoundException mue) {

            }
            catch (java.net.MalformedURLException mue) {

            }
            catch (java.io.IOException ioe) {

            }
            catch (java.lang.NullPointerException npe) {

            }
            // Check if games are scheduled for the date entered.
            return areGamesScheduled(team);
        }
        else {

            return false;
        }

    } // end of getGame method

    // check the various statuses to see if there is a game scheduled
    public boolean areGamesScheduled(String team) {

        try {
            if (getPlayerInfo(team, "status", "status").equals("") ||
                    getPlayerInfo(team, "status", "status").equals("Preview") ||
                    getPlayerInfo(team, "status", "status").equals("Pre-Game") ||
                    getPlayerInfo(team, "status", "status").equals("Warmup") ||
                    getPlayerInfo(team, "status", "status").equals("In Progress") ||
                    getPlayerInfo(team, "status", "status").equals("Postponed") ||
                    getPlayerInfo(team, "status", "status").equals("Completed Early") ||
                    getPlayerInfo(team, "status", "status").equals("Game Over") ||
                    getPlayerInfo(team, "status", "status").equals("Final")) {
            }
        }
        catch (java.lang.NullPointerException npe) {

            return false;
        }
        catch (java.lang.RuntimeException npe) {

            return false;
        }

        // no errors so there are games scheduled for this date
        return true;
    } // end of areGamesScheduled method

    // acquire game information on the first level of mlb's JSON
    public String getGameInfo(String team, String gameElement) {

        String gameInfo = "";

        // convert JSON element to JSON array
        JsonArray jsa = jse.getAsJsonObject().get("data").getAsJsonObject().get("games").getAsJsonObject().get("game")
                .getAsJsonArray();

        for(int i = 0; i < jsa.size(); i++) {
            if(jsa.get(i).getAsJsonObject().get("home_team_name").getAsString().equals(team) ||
                    jsa.get(i).getAsJsonObject().get("away_team_name").getAsString().equals(team)) {
                gameInfo = jsa.get(i).getAsJsonObject().get(gameElement).getAsString();
            }
        }
        return gameInfo;
    }

    // acquire game information on the second level of mlb's JSON
    public String getPlayerInfo(String team, String level, String gameElement) {

        String playerInfo = "";

        // convert JSON element to JSON array
        JsonArray jsa = jse.getAsJsonObject().get("data").getAsJsonObject().get("games").getAsJsonObject().get("game")
                .getAsJsonArray();

        for(int i = 0; i < jsa.size(); i++) {
            if(jsa.get(i).getAsJsonObject().get("home_team_name").getAsString().equals(team) ||
                    jsa.get(i).getAsJsonObject().get("away_team_name").getAsString().equals(team)) {
                playerInfo = jsa.get(i).getAsJsonObject().get(level).getAsJsonObject().get(gameElement).getAsString();
            }
        }
        return playerInfo;
    }

    // acquire game information on the third level of mlb's JSON
    public String getLineScore(String team, String level, String levelTwo, String gameElement) {

        String playerInfo = "";

        // convert JSON element to JSON array
        JsonArray jsa = jse.getAsJsonObject().get("data").getAsJsonObject().get("games").getAsJsonObject().get("game")
                .getAsJsonArray();

        for(int i = 0; i < jsa.size(); i++) {
            if(jsa.get(i).getAsJsonObject().get("home_team_name").getAsString().equals(team) ||
                    jsa.get(i).getAsJsonObject().get("away_team_name").getAsString().equals(team)) {
                playerInfo = jsa.get(i).getAsJsonObject().get(level).getAsJsonObject().get(levelTwo).getAsJsonObject()
                        .get(gameElement).getAsString();
            }
        }
        return playerInfo;
    }

    // check the team name entered and verify if it's a current mlb baseball team
    // if team is not a current mlb baseball team send an error message
    // if team is a current team find out the game status for the date entered
    public String getGameStatus(String team) {

        String gameStatus = "";

        String[] mlbTeam = {"D-backs", "Braves", "Orioles", "Red Sox", "Cubs", "White Sox", "Reds", "Indians", "Rockies",
                "Tigers", "Astros", "Royals", "Angels", "Dodgers", "Marlins", "Brewers", "Twins", "Mets",
                "Yankees", "Athletics", "Phillies", "Pirates", "Cardinals", "Padres", "Giants", "Mariners",
                "Rays", "Rangers", "Blue Jays", "Nationals"};

        search:
        for (int i = 0; i < 1; i++) {

            for (int j = 0; j < mlbTeam.length; j++) {
                if (mlbTeam[j].equals(team)) {
                    break search;
                }
            }

            team = "No team";
            i++;
        }
        try {
            // indicate if today is an off day for the team selected
            if (getPlayerInfo(team, "status", "status").equals("")) {
                gameStatus = "No game scheduled for the " + uppercaseFirstLetters(team);
            }
        }
        catch (NullPointerException npe) {
            gameStatus = "No game scheduled for the " + uppercaseFirstLetters(team);
        }

        // display error message if there is no match to the team name inputted
        if (team.equals("No team")) {
            gameStatus = "ERROR: Please enter current MLB team name.";
        }
        // indicate if today is an off day for the team selected
        else if (getPlayerInfo(team, "status", "status").equals("")) {
            gameStatus = "No game scheduled for the " + team;
        }
        else {
            gameStatus = getPlayerInfo(team, "status", "status");
        }

        return gameStatus;
    }

    /**
     * based on the status of the game, will display the number of inning of an extra inning game, why a game was postponed,
     * or if the game took place during spring training.
     */
    public String getExtraInnings(String team) {

        String extraInnings;

        // indicate if the game was a spring training game
        if (getGameStatus(team).equals("Final") && getGameInfo(team, "game_type").equals("S")) {
            extraInnings = "Spring Training Game";
        }
        // indicate if the game was an extra inning game
        else if (getGameStatus(team).equals("Final") && Integer.parseInt(getPlayerInfo(team, "status","inning")) > 9) {
            extraInnings = getPlayerInfo(team, "status","inning") + " Innings";
        }
        // indicate if the game was completed early
        else if (getGameStatus(team).equals("Completed Early") && Integer.parseInt(getPlayerInfo(team, "status","inning")) < 9) {
            extraInnings = getPlayerInfo(team, "status","inning") + " Innings" + " (" + getPlayerInfo(team, "status", "reason") + ")";
        }
        // indicate if the game was postponed
        else if (getGameStatus(team).equals("Postponed")) {
            extraInnings = getPlayerInfo(team, "status","reason");
        }
        else {
            extraInnings = "";
        }

        return extraInnings;
    }

    // method to display the "@" symbol for visiting team at home team display
    public String getAtGame (String team) {

        String atGame = getGameStatus(team);

        if (team.equals("") || atGame.equals("No game scheduled for the " + team) || atGame.equals("ERROR: Please enter " +
                "current MLB team name.")) {
            atGame = "";
        }
        else {
            atGame = "@";
        }

        return atGame;
    }

    // get the home team's name
    public String getHomeName(String team) {

        return getGameInfo(team, "home_team_name");
    }

    // get the home team's city
    public String getHomeCity(String team) {

        String homeCity = getGameInfo(team, "home_team_city");

        // for chicago teams change city name to Chicago
        if (homeCity.equals("Chi Cubs") || homeCity.equals("Chi White Sox")) {
            homeCity = "Chicago";
        }
        // for los angeles teams change city name to Los Angeles
        if (homeCity.equals("LA Angels") || homeCity.equals("LA Dodgers")) {
            homeCity = "Los Angeles";
        }
        // for new york teams change city name to New York
        if (homeCity.equals("NY Mets") || homeCity.equals("NY Yankees")) {
            homeCity = "New York";
        }
        return homeCity;
    }

    // get the home team's # of wins on a given date
    public String getHomeWin(String team) {

        return getGameInfo(team, "home_win");
    }

    // get the home team's # of losses on a given date
    public String getHomeLoss(String team) {

        return getGameInfo(team, "home_loss");
    }

    // get the home team's won/loss record on a given date
    public String getHomeRecord(String team) {

        String homeRecord = "";

        // if a team name has not been entered or an incorrect has been entered
        if (team.equals("") || getGameStatus(team).equals("ERROR: Please enter current MLB team name.")) {
            homeRecord = "";
        }
        // preview status, only provides team record one day after the current date
        else if (getGameStatus(team).equals("Preview")) {
            try {
                if (compareDates(getGameInfo(team, "original_date"), 1)) {
                    homeRecord = "(" + getHomeWin(team) + " - " + getHomeLoss(team) + ")";
                }
                else {
                    homeRecord = "";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // if the game status is pre-game, warmup, in progress, postponed, completed early, game over, or final
        else if (getPlayerInfo(team, "status", "status").equals("Pre-Game") ||
                getPlayerInfo(team, "status", "status").equals("Warmup") ||
                getPlayerInfo(team, "status", "status").equals("In Progress") ||
                getPlayerInfo(team, "status", "status").equals("Postponed") ||
                getPlayerInfo(team, "status", "status").equals("Completed Early") ||
                getPlayerInfo(team, "status", "status").equals("Game Over") ||
                getPlayerInfo(team, "status", "status").equals("Final")) {

            homeRecord = "(" + getHomeWin(team) + " - " + getHomeLoss(team) + ")";
        }
        // if today is an off day
        else {
            homeRecord = " ";
        }

        return homeRecord;
    }

    // the number of runs the home team scored on a given date
    public String getHomeR(String team) {

        String homeR;
        // if the game status is preview or postponed
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Postponed")) {
            homeR = "";
        }
        else {
            homeR = getLineScore(team, "linescore","r", "home");
        }

        return homeR;
    }

    // the number of hits the home team had on a given date
    public String getHomeH(String team) {

        String homeH;
        // if the game status is preview or postponed
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Postponed")) {
            homeH = "";
        }
        else {
            homeH = getLineScore(team, "linescore","h", "home");
        }

        return homeH;
    }

    // the number of errors the home team committed on a given date
    public String getHomeE(String team) {

        String homeE;
        // if the game status is preview or postponed
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Postponed")) {
            homeE = "";
        }
        else {
            homeE = getLineScore(team, "linescore","e", "home");
        }

        return homeE;
    }

    // format the hits and errors by the home team to be displayed below the number of runs
    public String getHomeLine(String team) {

        String homeLine;
        // if the game status is preview or postponed
        if (team.equals("") || getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Postponed") ||
                getGameStatus(team).equals("No game scheduled for the " + uppercaseFirstLetters(team)) ||
                getGameStatus(team).equals("ERROR: Please enter current MLB team name.")) {
            homeLine = "";
        }
        else {
            homeLine = "H  E\n" + getHomeH(team) + "  " + getHomeE(team);
        }

        return homeLine;
    }

    // the number of runs the away team scored on a given date
    public String getAwayR(String team) {

        String awayR;
        // if the game status is preview or postponed
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Postponed")) {
            awayR = "";
        }
        else {
            awayR = getLineScore(team, "linescore","r", "away");
        }

        return awayR;
    }

    // the number of hits the away team had on a given date
    public String getAwayH(String team) {

        String awayH;
        // if the game status is preview or postponed
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Postponed")) {
            awayH = "";
        }
        else {
            awayH = getLineScore(team, "linescore","h", "away");
        }

        return awayH;
    }

    // the number of errors the away team committed on a given date
    public String getAwayE(String team) {

        String awayE;
        // if the game status is preview or postponed
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Postponed")) {
            awayE = "";
        }
        else {
            awayE = getLineScore(team, "linescore","e", "away");
        }

        return awayE;
    }

    // format the hits and errors by the away team to be displayed below the number of runs
    public String getAwayLine(String team) {

        String awayLine;
        // if the game status is preview or postponed
        if (team.equals("") || getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Postponed") ||
                getGameStatus(team).equals("No game scheduled for the " + uppercaseFirstLetters(team)) ||
                getGameStatus(team).equals("ERROR: Please enter current MLB team name.")) {
            awayLine = "";
        }
        else {
            awayLine = "H  E\n" + getAwayH(team) + "  " + getAwayE(team);
        }

        return awayLine;
    }

    // get the away team's name
    public String getAwayName(String team) {

        return getGameInfo(team, "away_team_name");
    }

    // get the away team's city
    public String getAwayCity(String team) {

        String awayCity = getGameInfo(team, "away_team_city");

        // for chicago teams change city name to chicago
        if (awayCity.equals("Chi Cubs") || awayCity.equals("Chi White Sox")) {
            awayCity = "Chicago";
        }
        // for los angeles teams change city name to Los Angeles
        if (awayCity.equals("LA Angels") || awayCity.equals("LA Dodgers")) {
            awayCity = "Los Angeles";
        }
        // for new york teams change city name to new york
        if (awayCity.equals("NY Mets") || awayCity.equals("NY Yankees")) {
            awayCity = "New York";
        }

        return awayCity;
    }

    // get the away team's # of wins on a given date
    public String getAwayWin(String team) {

        return getGameInfo(team, "away_win");
    }

    // get the away team's # of losses on a given date
    public String getAwayLoss(String team) {

        return getGameInfo(team, "away_loss");
    }

    // get the away team's won/loss record on a given date
    public String getAwayRecord(String team) {

        String awayRecord = "";

        // if a team name has not been entered or an incorrect team name has been entered
        if (team.equals("") || getGameStatus(team).equals("ERROR: Please enter current MLB team name.")) {
            awayRecord = "";
        }
        // preview status, only provides team record one day after the current date
        else if (getGameStatus(team).equals("Preview")) {
            try {
                if (compareDates(getGameInfo(team, "original_date"), 1)) {
                    awayRecord = "(" + getAwayWin(team) + " - " + getAwayLoss(team) + ")";
                }
                else {
                    awayRecord = "";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // if the game status is pre-game, warmup, in progress, postponed, completed early, game over, or final
        else if (getPlayerInfo(team, "status", "status").equals("Pre-Game") ||
                getPlayerInfo(team, "status", "status").equals("Warmup") ||
                getPlayerInfo(team, "status", "status").equals("In Progress") ||
                getPlayerInfo(team, "status", "status").equals("Postponed") ||
                getPlayerInfo(team, "status", "status").equals("Completed Early") ||
                getPlayerInfo(team, "status", "status").equals("Game Over") ||
                getPlayerInfo(team, "status", "status").equals("Final")) {

            awayRecord = "(" + getAwayWin(team) + " - " + getAwayLoss(team) + ")";
        }
        // if today is an off day
        else {
            awayRecord = " ";
        }

        return awayRecord;
    }

    // if a game's status is preview, pre-game, or warmup return the tv/radio covering the game
    // if the game's status is in progress return the status of the inning (top/middle/end of # inning)
    public String getInning (String team) {

        String inning = "";

        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")) {
            String time = getGameInfo(team, "time");
            String ampm = getGameInfo(team, "ampm");
            String timeZone = getGameInfo(team, "time_zone");

            inning = time + " " + ampm + " " + timeZone;
        }
        if (getGameStatus(team).equals("In Progress")) {
            String topBottom = getPlayerInfo(team, "status","inning_state");
            String inn = getPlayerInfo(team, "status","inning");

            inning = topBottom + " " + inn;
        }

        return inning;
    }

    // method to get game play-by-play while the game status is "in-progress"
    // for future games where status is "preview" method displays tv/radio stations carrying the game
    public String getPbp (String team) {

        String pbp;
        String awayTv = "TBD";
        String awayRadio = "TBD";
        String homeTv = "TBD";
        String homeRadio = "TBD";

        // while the game is in progress display the last play in the game
        if (getGameStatus(team).equals("In Progress")) {
            pbp = "LAST PLAY: " + getPlayerInfo(team, "pbp","last");
        }
        // prior to the game display broadcast information for both teams
        else if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")) {

            try {
                if (!getLineScore(team, "broadcast","away", "tv").equals("")) {
                    awayTv = getLineScore(team, "broadcast","away", "tv");
                }
            }
            catch (RuntimeException npe) {

            }
            try {
                if (!getLineScore(team, "broadcast","away", "radio").equals("")) {
                    awayRadio = getLineScore(team, "broadcast","away", "radio");
                }
            }
            catch (RuntimeException npe) {

            }
            try {
                if (!getLineScore(team, "broadcast","home", "tv").equals("")) {
                    homeTv = getLineScore(team, "broadcast","home", "tv");
                }
            }
            catch (RuntimeException npe) {

            }
            try {
                if (!getLineScore(team, "broadcast","home", "radio").equals("")) {
                    homeRadio = getLineScore(team, "broadcast","home", "radio");
                }
            }
            catch (RuntimeException npe) {

            }

            pbp = getAwayCity(team) + " TV: " + awayTv + ", Radio: " + awayRadio + "\n" + getHomeCity(team) + " TV: " + homeTv + ", Radio: " + homeRadio;
        }
        else {
            pbp = "";
        }
        return pbp;
    }

    // player information methods --------------------------------------------------------------------------------------

    // for a game status of preview, pre-game, or warmup displays the away team's scheduled pitcher's last name
    // for a game in progress displays the current batter's last name
    // for a completed game displays the winning pitcher's last name
    public String getWinPitchLast(String team) {

        String winPitchLast = "";

        // if the game status is in preview, pre-game, or warmup
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")) {
            winPitchLast = getPlayerInfo(team, "away_probable_pitcher","last");
        }
        // if the game is in progress get the current batter
        if (getGameStatus(team).equals("In Progress")) {
            winPitchLast = getPlayerInfo(team, "batter","last");
        }
        // if the game status is game over or final
        if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            winPitchLast = getPlayerInfo(team, "winning_pitcher","last");
        }

        return winPitchLast;
    }

    // for a game status of preview, pre-game, or warmup displays the away team's scheduled pitcher's first name
    // for a game in progress displays the current batter's first name
    // for a completed game displays the winning pitcher's first name
    public String getWinPitchFirst(String team) {

        String winPitchFirst = "";

        // if the game status is in preview, pre-game, or warmup
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")) {
            winPitchFirst = getPlayerInfo(team, "away_probable_pitcher","first");
        }
        // if the game is in progress get the current batter
        if (getGameStatus(team).equals("In Progress")) {
            winPitchFirst = "At Bat\n" + getPlayerInfo(team, "batter","first");
        }
        // if the game is a final
        if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            winPitchFirst = "WP: " + getPlayerInfo(team, "winning_pitcher","first");
        }

        return winPitchFirst;
    }

    public String getWinPitchName (String team) {
        return getWinPitchFirst(team) + " " + getWinPitchLast(team);
    }

    public String getWinPitchWins(String team) {

        String winPitchWins = "";
        // if the game status is in preview, pre-game, or warmup
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")) {
            winPitchWins = getPlayerInfo(team, "away_probable_pitcher","wins");
        }
        // if the game is in progress get the current batter
        if (getGameStatus(team).equals("In Progress")) {
            winPitchWins = getPlayerInfo(team, "batter", "h");
        }
        // if the game is a final
        if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            winPitchWins = getPlayerInfo(team, "winning_pitcher","wins");
        }

        return winPitchWins;
    }

    public String getWinPitchLosses(String team) {

        String winPitchLosses = "";
        // if the game status is in preview, pre-game, or warmup
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")){
            winPitchLosses = getPlayerInfo(team, "away_probable_pitcher","losses");
        }
        // if the game is in progress get the current batter
        if (getGameStatus(team).equals("In Progress")) {
            winPitchLosses = getPlayerInfo(team, "batter", "ab");
        }
        // if the game is a final
        if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            winPitchLosses = getPlayerInfo(team, "winning_pitcher","losses");
        }

        return winPitchLosses;
    }

    public String getWinPitchEra(String team) {

        String winPitchEra = "";
        // if the game status is in preview, pre-game, or warmup
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")) {
            winPitchEra = getPlayerInfo(team, "away_probable_pitcher","era") + " ERA";
        }
        // if the game is in progress get the current batter's batting average
        if (getGameStatus(team).equals("In Progress")) {
            winPitchEra = getPlayerInfo(team, "batter", "avg");
        }
        // if the game is a final
        if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            winPitchEra = getPlayerInfo(team, "winning_pitcher","era");
        }

        return winPitchEra;
    }

    public String getWinPitchRecord (String team) {

        String winPitchRecord = "(" + getWinPitchWins(team) + "-" + getWinPitchLosses(team) + ", " + getWinPitchEra(team) + ")";
        // if today is an off day for the team selected
        if(winPitchRecord.equals("(-, )")) {
            winPitchRecord = "";
        }
        // for games with a status of preview, pre-game, and warmup, get the pitcher's pitching stats
        // check if the date entered is after 3 days from the present date, if so scheduled pitcher data is not available
        else if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")) {
            try {
                if (compareDates(getGameInfo(team, "original_date"), 3)) {
                    winPitchRecord = getPlayerInfo(team, "away_probable_pitcher","throwinghand") + " #" +
                            getPlayerInfo(team, "away_probable_pitcher","number") + "\n" +
                            getWinPitchWins(team) + "-" + getWinPitchLosses(team) + ", " + getWinPitchEra(team);
                }
                else {
                    winPitchRecord = "";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        else if (getGameStatus(team).equals("In Progress")) {
            winPitchRecord = getWinPitchWins(team) + "-" + getWinPitchLosses(team) + ", " + getWinPitchEra(team) + " AVG";
        }
        else if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            winPitchRecord = "(" + getWinPitchWins(team) + "-" + getWinPitchLosses(team) + ", " + getWinPitchEra(team) + " ERA)";
        }

        return winPitchRecord;
    }

    // for a game status of preview, pre-game, or warmup displays the home team's scheduled pitcher's last name
    // for a game in progress displays the current pitcher's last name
    // for a completed game displays the losing pitcher's last name
    public String getLosePitchLast(String team) {

        String losePitchLast = "";
        // if the game is in preview
        if (getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")) {
            losePitchLast = getPlayerInfo(team, "home_probable_pitcher","last");
        }
        // if the game is in progress get the current pitcher
        if (getGameStatus(team).equals("In Progress")) {
            losePitchLast = getPlayerInfo(team, "pitcher","last");
        }
        // if the game is a final
        if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            losePitchLast = getPlayerInfo(team, "losing_pitcher","last");
        }

        return losePitchLast;
    }

    // for a game status of preview, pre-game, or warmup displays the home team's scheduled pitcher's first name
    // for a game in progress displays the current pitcher's first name
    // for a completed game displays the losing pitcher's first name
    public String getLosePitchFirst(String team) {

        String losePitchFirst = "";
        // if the game is in preview
        if (getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")) {
            losePitchFirst = getPlayerInfo(team, "home_probable_pitcher","first");
        }
        // if the game is in progress get the current batter
        if (getGameStatus(team).equals("In Progress")) {
            losePitchFirst = "P: " + getPlayerInfo(team, "pitcher","first");
        }
        // if the game is a final
        if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            losePitchFirst = "LP: " + getPlayerInfo(team, "losing_pitcher","first");
        }

        return losePitchFirst;
    }

    public String getLosePitchName(String team) {

        String losePitchName = "";

        // for games with a status of preview, pre-game, and warmup, get the scheduled pitcher
        // check if the date entered is after 3 days from the present date, if so scheduled pitcher data is not available
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")) {
            try {
                if (compareDates(getGameInfo(team, "original_date"), 3)) {
                    losePitchName = "Scheduled pitchers";
                }
                else {
                    losePitchName = "";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // if the game is in progress display the current balls, strikes, and outs
        else if (getGameStatus(team).equals("In Progress")) {
            String b = getPlayerInfo(team, "status","b");
            String s = getPlayerInfo(team, "status","s");
            String o = getPlayerInfo(team, "status","o");

            losePitchName = b + "-" + s + ", " + o + " Out";
        }
        // when the game is a final return the losing pitchers full name
        else {
            losePitchName = getLosePitchFirst(team) + " " + getLosePitchLast(team);
        }
        return losePitchName;
    }

    public String getLosePitchWins(String team) {

        String losePitchWins = "";
        // if the game is in progress get the current batter
        if (getGameStatus(team).equals("In Progress")) {
            losePitchWins = getPlayerInfo(team, "pitcher", "ip");
        }
        // if the game is a final get the number of wins for the pitcher
        if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            losePitchWins = getPlayerInfo(team, "losing_pitcher","wins");
        }

        return losePitchWins;
    }

    public String getLosePitchLosses(String team) {

        String losePitchLosses = "";
        // if the game is in progress get the current batter
        if (getGameStatus(team).equals("In Progress")) {
            losePitchLosses = getPlayerInfo(team, "pitcher", "er");
        }
        // if the game is a final get the number of losses for the pitcher
        if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            losePitchLosses = getPlayerInfo(team, "losing_pitcher","losses");
        }

        return losePitchLosses;
    }

    public String getLosePitchEra(String team) {

        String losePitchEra = "";

        // if the game is in progress get the current pitcher's era
        if (getGameStatus(team).equals("In Progress")) {
            losePitchEra = getPlayerInfo(team, "pitcher", "era");
        }
        // if the game is a final get the losing pitchers era
        if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            losePitchEra = getPlayerInfo(team, "losing_pitcher","era");
        }

        return losePitchEra;
    }

    public String getLosePitchRecord(String team) {

        String losePitchRecord = "(" + getLosePitchWins(team) + "-" + getLosePitchLosses(team) + ", " + getLosePitchEra(team) + ")";
        // if today is an off day for the team selected or the game is in progress
        if (getGameStatus(team).equals("In Progress") || losePitchRecord.equals("(-, )")) {
            losePitchRecord = "";
        }
        // once the game has been completed return the losing pitchers wins, losses, and era
        else if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            losePitchRecord = "(" + getLosePitchWins(team) + "-" + getLosePitchLosses(team) + ", " + getLosePitchEra(team) + " ERA)";
        }

        return losePitchRecord;
    }

    // for a game status of preview, pre-game, or warmup displays the home team's scheduled pitcher's last name
    // for a game in progress displays the current pitcher's last name
    // for a completed game where a save is recorded display the pitcher's last name
    public String getSavePitchLast(String team) {

        String savePitchLast = "";

        // if the game status is in preview, pre-game, or warmup
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")) {
            try {
                if (compareDates(getGameInfo(team, "original_date"), 3)) {
                    savePitchLast = getPlayerInfo(team, "home_probable_pitcher","last");
                }
                else {
                    savePitchLast = "";
                }
            }   catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // if the game is in progress get the current pitcher
        if (getGameStatus(team).equals("In Progress")) {
            savePitchLast = getPlayerInfo(team, "pitcher","last");
        }
        if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            savePitchLast = getPlayerInfo(team, "save_pitcher","last");
        }

        return savePitchLast;
    }

    // for a game status of preview, pre-game, or warmup displays the home team's scheduled pitcher's first name
    // for a game in progress displays the current pitcher's first name
    // for a completed game where a save is recorded display the pitcher's first name
    public String getSavePitchFirst(String team) {

        String savePitchFirst = "";

        // check if the date entered is after 3 days from the present date, if so scheduled pitcher data is not available
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")) {
            try {
                if (compareDates(getGameInfo(team, "original_date"), 3)) {
                    savePitchFirst = getPlayerInfo(team, "home_probable_pitcher", "first");
                } else {
                    savePitchFirst = "";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        if (getGameStatus(team).equals("In Progress")) {
            savePitchFirst = "Pitching\n" + getPlayerInfo(team, "pitcher","first");
        }
        // if a game is played checked to see if a save has been recorded
        if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final")  || getGameStatus(team).equals("Completed Early")) {

            // if a save has not been recorded
            if (getPlayerInfo(team, "save_pitcher","saves").equals("0")) {
                savePitchFirst = "SV: None";
            }
            // if a save has been recorded
            else {
                savePitchFirst = "SV: " + getPlayerInfo(team, "save_pitcher","first");
            }
        }

        return savePitchFirst;
    }

    public String getSavePitchName(String team) {

        return getSavePitchFirst(team) + " " + getSavePitchLast(team);
    }

    // for a game status of preview, pre-game, or warmup displays the home team's probable pitcher's stats
    // for a game in progress displays the current pitcher's game stats (ip, er, and era)
    // for a completed game where a save is recorded display the # of saves the pitcher has for the season
    public String getSavePitchSave(String team) {

        String savePitchSave = "";

        // if the game status is in preview, pre-game, or warmup
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")) {
            try {
                if (compareDates(getGameInfo(team, "original_date"), 3)) {
                    String throwinghand = getPlayerInfo(team, "home_probable_pitcher","throwinghand");
                    String number = " #" + getPlayerInfo(team, "home_probable_pitcher","number") + "\n";
                    String wins = getPlayerInfo(team, "home_probable_pitcher","wins");
                    String losses = "-" + getPlayerInfo(team, "home_probable_pitcher","losses");
                    String era = ", " + getPlayerInfo(team, "home_probable_pitcher","era") + " ERA";

                    savePitchSave = throwinghand + number + wins + losses + era;
                }
                else {
                    savePitchSave = "";
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // if the game is in progress get the current on-deck batter
        if (getGameStatus(team).equals("In Progress")) {
            String ip = getPlayerInfo(team, "pitcher","ip") + " IP";
            String er = getPlayerInfo(team, "pitcher","er") + " ER";
            String era = getPlayerInfo(team, "pitcher","era") + " ERA";

            savePitchSave = ip + ", " + er + ", " + era;
        }
        // if game is a final and a save has taken place
        if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            savePitchSave = "(" + getPlayerInfo(team, "save_pitcher", "saves") + ")";
        }
        // if today is an off day or a save has not been recorded in the game
        if (savePitchSave.equals("(0)") || savePitchSave.equals("()")) {
            savePitchSave = " ";
        }

        return savePitchSave;
    }

    // returns the name of the stadium the game is taking place in
    public String getVenue(String team) {

        return getGameInfo(team, "venue");
    }

    // returns the city and state the game is taking place in
    public String getLocation(String team) {

        String location;
        String year = jse.getAsJsonObject().get("data").getAsJsonObject().get("games").getAsJsonObject(). get("year").getAsString();

        // location is not part of mlb.com's JSON values prior to 2015
        if (Integer.valueOf(year) < 2015 || getGameStatus(team).equals("No game scheduled for the " + uppercaseFirstLetters(team))
                || getGameStatus(team).equals("ERROR: Please enter current MLB team name.") || team.equals("")) {
            location = "";
        }
        else {
            location = ", " + getGameInfo(team, "location");
        }

        return location;
    }

    // methods to get graphics------------------------------------------------------------------------------------------

    // retuns the home team's logo
    public Image getHomeLogo(String team) {

        String homeLogo = getGameInfo(team,"home_name_abbrev");

        // for the chicago white sox logo to be retrieved
        if (homeLogo.equals("CWS")) {
            homeLogo = "CHW";
        }
        // for the washington nationals logo to be retrieved
        if (homeLogo.equals("WSH")) {
            homeLogo = "WAS";
        }

        homeLogo = "http://sports.cbsimg.net/images/mlb/logos/100x100/" + homeLogo + ".png";

        return new Image(homeLogo);
    }

    // returns the away team's logo
    public Image getAwayLogo(String team) {

        String awayLogo = getGameInfo(team,"away_name_abbrev");

        // for the chicago white sox logo to be retrieved
        if (awayLogo.equals("CWS")) {
            awayLogo = "CHW";
        }
        // for the washington nationals logo to be retrieved
        if (awayLogo.equals("WSH")) {
            awayLogo = "WAS";
        }

        awayLogo = "http://sports.cbsimg.net/images/mlb/logos/100x100/" + awayLogo + ".png";

        return new Image(awayLogo);
    }

    // for a game status of preview, pre-game, or warmup displays the away team's probable pitcher's picture
    // for a game in progress displays the current batter's picture
    // for a completed game where a save is recorded display the winning pitchers picture
    public Image getWinPitchPic(String team) {

        String winPitchPic = "";

        // if the game status is in preview, pre-game, or warmup
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")){
            try {
                if (compareDates(getGameInfo(team, "original_date"), 3)) {
                    winPitchPic = "http://mlb.mlb.com/images/players/assets/68_" + getPlayerInfo(team, "away_probable_pitcher","id") + ".png";
                }
                else {
                    winPitchPic = "noImage.png";
                }
            }   catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // if the game is in progress get the current batter
        else if (getGameStatus(team).equals("In Progress")) {
            winPitchPic = "http://mlb.mlb.com/images/players/assets/68_" + getPlayerInfo(team, "batter","id") + ".png";
        }
        else if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            winPitchPic = "http://mlb.mlb.com/images/players/assets/68_" + getPlayerInfo(team, "winning_pitcher","id") + ".png";
        }
        else {
            winPitchPic = "noImage.png";
        }

        return new Image(winPitchPic);
    }

    // for a game in progress displays the status of the baseball diamond (bases empty, runner on first, etc.)
    // for a completed game display the losing pitchers picture
    public Image getLosePitchPic(String team) {

        String losePitchPic = "";
        // if the game is in progress show if there are any runners on base
        if (getGameStatus(team).equals("In Progress")) {
            if (getPlayerInfo(team, "runners_on_base","status").equals("0")) {
                losePitchPic = "basesEmpty.png";
            }
            else if (getPlayerInfo(team, "runners_on_base","status").equals("1")) {
                losePitchPic = "firstOnly.png";
            }
            else if (getPlayerInfo(team, "runners_on_base","status").equals("2")) {
                losePitchPic = "secondOnly.png";
            }
            else if (getPlayerInfo(team, "runners_on_base","status").equals("3")) {
                losePitchPic = "thirdOnly.png";
            }
            else if (getPlayerInfo(team, "runners_on_base","status").equals("4")) {
                losePitchPic = "firstSecond.png";
            }
            else if (getPlayerInfo(team, "runners_on_base","status").equals("5")) {
                losePitchPic = "firstThird.png";
            }
            else if (getPlayerInfo(team, "runners_on_base","status").equals("6")) {
                losePitchPic = "secondThird.png";
            }
            else if (getPlayerInfo(team, "runners_on_base","status").equals("7")) {
                losePitchPic = "basesLoaded.png";
            }
        }
        else if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final") || getGameStatus(team).equals("Completed Early")) {
            losePitchPic = "http://mlb.mlb.com/images/players/assets/68_" + getPlayerInfo(team, "losing_pitcher","id") + ".png";
        }
        else {
            losePitchPic = "noImage.png";
        }

        return new Image(losePitchPic);
    }

    // for a game status of preview, pre-game, or warmup displays the home team's probable pitcher's picture
    // for a game in progress displays the current pitcher's picture
    // for a completed game where a save is recorded display the pitchers picture
    // if a save has not been recorded for a complete game, a picture of a silhouetted player is returned
    public Image getSavePitchPic(String team) {

        String savePitchPic = "";

        // if the game status is in preview, pre-game, or warmup get the home pitcher's picture
        if (getGameStatus(team).equals("Preview") || getGameStatus(team).equals("Pre-Game") || getGameStatus(team).equals("Warmup")){
            try {
                if (compareDates(getGameInfo(team, "original_date"), 3)) {
                    savePitchPic = "http://mlb.mlb.com/images/players/assets/68_" + getPlayerInfo(team, "home_probable_pitcher","id") + ".png";
                }
                else {
                    savePitchPic = "noImage.png";
                }
            }   catch (ParseException e) {
                e.printStackTrace();
            }
        }
        // if the game is in progress get the current pitcher's picture
        else if (getGameStatus(team).equals("In Progress")) {
            savePitchPic = "http://mlb.mlb.com/images/players/assets/68_" + getPlayerInfo(team, "pitcher","id") + ".png";
        }
        // if a game is played checked to see if a save has been recorded
        else if (getGameStatus(team).equals("Game Over") || getGameStatus(team).equals("Final")  || getGameStatus(team).equals("Completed Early")) {

            // if a save has not been recorded
            if (getPlayerInfo(team, "save_pitcher","saves").equals("0")) {
                savePitchPic = "headshot.png";
            }
            // if a save has been recorded
            else {
                savePitchPic = "http://mlb.mlb.com/images/players/assets/68_" + getPlayerInfo(team, "save_pitcher","id") + ".png";
            }
        }
        else {
            savePitchPic = "noImage.png";
        }

        return new Image(savePitchPic);
    }

    // method to capitalize the first letters of each word in a string
    // used to guarantee that the words in the team name being entered are capitalized
    public static String uppercaseFirstLetters(String str) {
        boolean prevWasWhiteSp = true;
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (Character.isLetter(chars[i])) {
                if (prevWasWhiteSp) {
                    chars[i] = Character.toUpperCase(chars[i]);
                }
                prevWasWhiteSp = false;
            } else {
                prevWasWhiteSp = Character.isWhitespace(chars[i]);
            }
        }
        return new String(chars);
    }

    // method to validate the input date
    public static boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("mm/DD/yyyy");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim());
        }
        catch (ParseException pe) {

            return false;
        }

        return true;
    }

    // compares the current date to an inputted date
    public static boolean compareDates(String date, Integer days) throws ParseException {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

        // create calendar and get today's date
        Calendar todayDate = Calendar.getInstance();
        todayDate.setTime(new Date());

        // add days to today's calendar date
        todayDate.add(Calendar.DATE, days);

        // break up the month, day, and year into individual values
        String year = String.valueOf(todayDate.get(Calendar.YEAR));
        String month = String.valueOf(todayDate.get(Calendar.MONTH)+1);
        String day = String.valueOf(todayDate.get(Calendar.DATE));

        String compareDate = year + "/" + month + "/" + day;

        Date date1 = sdf.parse(date);
        Date date2 = sdf.parse(compareDate);

        if (date1.compareTo(date2) == 0) {
            return  true;
        }
        else if (date1.compareTo(date2) < 0) {
            return true;
        }
        else {
            return false;
        }

    }
}
