import static org.junit.Assert.*;
import org.junit.Test;

/**
 * Name:    Craig Calvert
 * Date:    5/8/2017
 */
public class MlbModelTest {

    @Test
    public void testGetGame1() {
        MlbModel game = new MlbModel();
        assertEquals(true, game.getGame("04/29/2017", "Yankees"));
    }

    @Test
    public void testGetGame2() {
        MlbModel game = new MlbModel();
        assertEquals(true, game.getGame("5/1/2017", "Yankees"));
    }

    @Test
    public void testGetGame3() {
        MlbModel game = new MlbModel();
        assertEquals(false, game.getGame("1/1/2017", "Yankees"));
    }

    @Test
    public void testGetGame4() {
        MlbModel game = new MlbModel();
        assertEquals(false, game.getGame("38fklci@da8jli$k", "Yankees"));
    }

    @Test
    public void testGetGame5() {
        MlbModel game = new MlbModel();
        assertEquals(false, game.getGame("2/20/2020", "Yankees"));
    }

    @Test
    public void testAreGamesScheduled1() {
        MlbModel game = new MlbModel();
        game.getGame("5/17/2017", "Athletics");
        assertEquals(true, game.areGamesScheduled("Athletics"));
    }

    @Test
    public void testAreGamesScheduled2() {
        MlbModel game = new MlbModel();
        game.getGame("9/17/2017", "Mariners");
        assertEquals(true, game.areGamesScheduled("Mariners"));
    }

    @Test
    public void testAreGamesScheduled3() {
        MlbModel game = new MlbModel();
        game.getGame("11/18/2016", "Giants");
        assertEquals(false, game.areGamesScheduled("Giants"));
    }

    @Test
    public void testGetGameStatus1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("Final", game.getGameStatus("Yankees"));
    }

    @Test
    public void testGetGameStatus2() {
        MlbModel game = new MlbModel();
        game.getGame("05/11/2017", "Athletics");
        assertEquals("No game scheduled for the Athletics", game.getGameStatus("Athletics"));
    }

    @Test
    public void testGetGameStatus3() {
        MlbModel game = new MlbModel();
        game.getGame("05/13/2010", "Nationals");
        assertEquals("Completed Early", game.getGameStatus("Nationals"));
    }

    @Test
    public void testGetGameStatus4() {
        MlbModel game = new MlbModel();
        game.getGame("05/11/2017", "Nationals");
        assertEquals("Postponed", game.getGameStatus("Nationals"));
    }

    @Test
    public void testGetGameStatus5() {
        MlbModel game = new MlbModel();
        game.getGame("05/19/2017", "");
        assertEquals("ERROR: Please enter current MLB team name.", game.getGameStatus(""));
    }

    @Test
    public void testGetGameStatus6() {
        MlbModel game = new MlbModel();
        game.getGame("05/19/2017", "Senators");
        assertEquals("ERROR: Please enter current MLB team name.", game.getGameStatus(""));
    }

    @Test
    public void testGetExtraInnings1() {
        MlbModel game = new MlbModel();
        game.getGame("07/06/2008", "Tigers");
        assertEquals("15 Innings", game.getExtraInnings("Tigers"));
    }

    @Test
    public void testGetExtraInnings2() {
        MlbModel game = new MlbModel();
        game.getGame("05/13/2010", "Nationals");
        assertEquals("8 Innings (Rain)", game.getExtraInnings("Nationals"));
    }

    @Test
    public void testGetExtraInnings3() {
        MlbModel game = new MlbModel();
        game.getGame("05/11/2017", "Nationals");
        assertEquals("Rain", game.getExtraInnings("Nationals"));
    }

    @Test
    public void testGetAtGame1() {
        MlbModel game = new MlbModel();
        game.getGame("05/11/2017", "");
        assertEquals("", game.getAtGame(""));
    }

    @Test
    public void testGetAtGame2() {
        MlbModel game = new MlbModel();
        game.getGame("08/19/2011", "Orioles");
        assertEquals("@", game.getAtGame("Orioles"));
    }

    @Test
    public void testGetHomeTeamName1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("Yankees", game.getHomeName("Yankees"));
    }

    @Test
    public void testGetHomeTeamName2() {
        MlbModel game = new MlbModel();
        game.getGame("08/09/2017", "Angels");
        assertEquals("Angels", game.getHomeName("Angels"));
    }

    @Test
    public void testGetHomeTeamCity1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("New York", game.getHomeCity("Yankees"));
    }

    @Test
    public void testGetHomeTeamCity2() {
        MlbModel game = new MlbModel();
        game.getGame("08/09/2017", "Angels");
        assertEquals("Los Angeles", game.getHomeCity("Angels"));
    }

    @Test
    public void testGetHomeTeamW1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("15", game.getHomeWin("Yankees"));
    }

    @Test
    public void testGetHomeTeamW2() {
        MlbModel game = new MlbModel();
        game.getGame("09/08/2017", "Blue Jays");
        assertEquals("0", game.getHomeWin("Blue Jays"));
    }

    @Test
    public void testGetHomeTeamL1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("7", game.getHomeLoss("Yankees"));
    }

    @Test
    public void testGetHomeTeamL2() {
        MlbModel game = new MlbModel();
        game.getGame("09/08/2017", "Blue Jays");
        assertEquals("0", game.getHomeLoss("Blue Jays"));
    }

    @Test
    public void testGetHomeRecord1() {
        MlbModel game = new MlbModel();
        game.getGame("04/19/2017", "Red Sox");
        assertEquals("(3 - 11)", game.getHomeRecord("Red Sox"));
    }

    @Test
    public void testGetHomeRecord2() {
        MlbModel game = new MlbModel();
        game.getGame("07/08/2017", "Twins");
        assertEquals("", game.getHomeRecord("Twins"));
    }

    @Test
    public void testGetHomeR1() {
        MlbModel game = new MlbModel();
        game.getGame("08/05/2016", "D-backs");
        assertEquals("3", game.getHomeR("D-backs"));
    }

    @Test
    public void testGetHomeR2() {
        MlbModel game = new MlbModel();
        game.getGame("09/02/2017", "Indians");
        assertEquals("", game.getHomeR("Indians"));
    }

    @Test
    public void testGetHomeH1() {
        MlbModel game = new MlbModel();
        game.getGame("08/05/2016", "D-backs");
        assertEquals("7", game.getHomeH("D-backs"));
    }

    @Test
    public void testGetHomeH2() {
        MlbModel game = new MlbModel();
        game.getGame("09/02/2017", "Indians");
        assertEquals("", game.getHomeH("Indians"));
    }

    @Test
    public void testGetHomeE1() {
        MlbModel game = new MlbModel();
        game.getGame("08/05/2016", "D-backs");
        assertEquals("2", game.getHomeE("D-backs"));
    }

    @Test
    public void testGetHomeE2() {
        MlbModel game = new MlbModel();
        game.getGame("09/02/2017", "Indians");
        assertEquals("", game.getHomeE("Indians"));
    }

    @Test
    public void testGetHomeLine1() {
        MlbModel game = new MlbModel();
        game.getGame("08/05/2016", "D-backs");
        assertEquals("H  E\n7  2", game.getHomeLine("D-backs"));
    }

    @Test
    public void testGetHomeLine2() {
        MlbModel game = new MlbModel();
        game.getGame("09/02/2017", "Indians");
        assertEquals("", game.getHomeLine("Indians"));
    }

    @Test
    public void testGetAwayTeamName1() {
        MlbModel game = new MlbModel();
        game.getGame("09/12/2013", "Athletics");
        assertEquals("Athletics", game.getAwayName("Athletics"));
    }

    @Test
    public void testGetAwayTeamName2() {
        MlbModel game = new MlbModel();
        game.getGame("08/31/2017", "Rangers");
        assertEquals("Rangers", game.getAwayName("Rangers"));
    }

    @Test
    public void testGetAwayTeamCity1() {
        MlbModel game = new MlbModel();
        game.getGame("09/12/2013", "Athletics");
        assertEquals("Oakland", game.getAwayCity("Athletics"));
    }

    @Test
    public void testGetAwayTeamCity2() {
        MlbModel game = new MlbModel();
        game.getGame("08/31/2017", "Rangers");
        assertEquals("Texas", game.getAwayCity("Rangers"));
    }

    @Test
    public void testGetAwayTeamW1() {
        MlbModel game = new MlbModel();
        game.getGame("07/25/2008", "Rays");
        assertEquals("60", game.getAwayWin("Rays"));
    }

    @Test
    public void testGetAwayTeamW2() {
        MlbModel game = new MlbModel();
        game.getGame("09/07/2017", "Marlins");
        assertEquals("0", game.getAwayWin("Marlins"));
    }

    @Test
    public void testGetAwayTeamL1() {
        MlbModel game = new MlbModel();
        game.getGame("07/25/2008", "Rays");
        assertEquals("42", game.getAwayLoss("Rays"));
    }

    @Test
    public void testGetAwayTeamL2() {
        MlbModel game = new MlbModel();
        game.getGame("09/07/2017", "Marlins");
        assertEquals("0", game.getAwayLoss("Marlins"));
    }

    @Test
    public void testGetAwayRecord1() {
        MlbModel game = new MlbModel();
        game.getGame("07/25/2008", "Rays");
        assertEquals("(60 - 42)", game.getAwayRecord("Rays"));
    }

    @Test
    public void testGetAwayRecord2() {
        MlbModel game = new MlbModel();
        game.getGame("09/07/2017", "Marlins");
        assertEquals("", game.getAwayRecord("Marlins"));
    }

    @Test
    public void testGetAwayR1() {
        MlbModel game = new MlbModel();
        game.getGame("05/21/2011", "Braves");
        assertEquals("5", game.getAwayR("Braves"));
    }

    @Test
    public void testGetAwayR2() {
        MlbModel game = new MlbModel();
        game.getGame("10/01/2017", "Rockies");
        assertEquals("", game.getAwayR("Rockies"));
    }

    @Test
    public void testGetAwayH1() {
        MlbModel game = new MlbModel();
        game.getGame("05/21/2011", "Braves");
        assertEquals("17", game.getAwayH("Braves"));
    }

    @Test
    public void testGetAwayH2() {
        MlbModel game = new MlbModel();
        game.getGame("10/01/2017", "Rockies");
        assertEquals("", game.getAwayH("Rockies"));
    }

    @Test
    public void testGetAwayE1() {
        MlbModel game = new MlbModel();
        game.getGame("05/21/2011", "Braves");
        assertEquals("0", game.getAwayE("Braves"));
    }

    @Test
    public void testGetAwayE2() {
        MlbModel game = new MlbModel();
        game.getGame("10/01/2017", "Rockies");
        assertEquals("", game.getAwayE("Rockies"));
    }

    @Test
    public void testGetAwayLine1() {
        MlbModel game = new MlbModel();
        game.getGame("05/21/2011", "Braves");
        assertEquals("H  E\n17  0", game.getAwayLine("Braves"));
    }

    @Test
    public void testGetAwayLine2() {
        MlbModel game = new MlbModel();
        game.getGame("10/01/2017", "Rockies");
        assertEquals("", game.getAwayLine("Rockies"));
    }

    @Test
    public void testGetInning1() {
        MlbModel game = new MlbModel();
        game.getGame("08/03/2017", "Royals");
        assertEquals("8:15 PM ET", game.getInning("Royals"));
    }

    @Test
    public void testGetInning2() {
        MlbModel game = new MlbModel();
        game.getGame("05/19/2007", "Cardinals");
        assertEquals("", game.getInning("Cardinals"));
    }

    @Test
    public void testGetPbp1() {
        MlbModel game = new MlbModel();
        game.getGame("09/24/2017", "Dodgers");
        assertEquals("San Francisco TV: NBCSBA, Radio: KNBR 680, KXZM 93.7\n" +
                "Los Angeles TV: TBD, Radio: TBD", game.getPbp("Dodgers"));
    }

    @Test
    public void testGetPbp2() {
        MlbModel game = new MlbModel();
        game.getGame("08/11/2009", "Phillies");
        assertEquals("", game.getPbp("Phillies"));
    }

    @Test
    public void testGetWinPitchLast1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("Pineda", game.getWinPitchLast("Yankees"));
    }

    @Test
    public void testGetWinPitchLast2() {
        MlbModel game = new MlbModel();
        game.getGame("09/30/2017", "White Sox");
        assertEquals("", game.getWinPitchLast("White Sox"));
    }

    @Test
    public void testGetWinPitchFirst1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("WP: Michael", game.getWinPitchFirst("Yankees"));
    }

    @Test
    public void testGetWinPitchFirst2() {
        MlbModel game = new MlbModel();
        game.getGame("09/30/2017", "White Sox");
        assertEquals("", game.getWinPitchFirst("White Sox"));
    }

    @Test
    public void testGetWinPitchName1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("WP: Michael Pineda", game.getWinPitchName("Yankees"));
    }

    @Test
    public void testGetWinPitchName2() {
        MlbModel game = new MlbModel();
        game.getGame("09/30/2017", "White Sox");
        assertEquals(" ", game.getWinPitchName("White Sox"));
    }

    @Test
    public void testGetWinPitchWins1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("3", game.getWinPitchWins("Yankees"));
    }

    @Test
    public void testGetWinPitchWins2() {
        MlbModel game = new MlbModel();
        game.getGame("07/19/2017", "Reds");
        assertEquals("", game.getWinPitchWins("Reds"));
    }

    @Test
    public void testGetWinPitchLosses1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("1", game.getWinPitchLosses("Yankees"));
    }

    @Test
    public void testGetWinPitchLosses2() {
        MlbModel game = new MlbModel();
        game.getGame("07/19/2017", "Reds");
        assertEquals("", game.getWinPitchLosses("Reds"));
    }

    @Test
    public void testGetWinPitchEra1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("3.14", game.getWinPitchEra("Yankees"));
    }

    @Test
    public void testGetWinPitchEra2() {
        MlbModel game = new MlbModel();
        game.getGame("07/19/2017", "Reds");
        assertEquals(" ERA", game.getWinPitchEra("Reds"));
    }

    @Test
    public void testGetWinPitchRecord1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("(3-1, 3.14 ERA)", game.getWinPitchRecord("Yankees"));
    }

    @Test
    public void testGetWinPitchRecord2() {
        MlbModel game = new MlbModel();
        game.getGame("07/19/2017", "Reds");
        assertEquals("", game.getWinPitchRecord("Reds"));
    }

    @Test
    public void testGetLosePitchLast1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("Jimenez", game.getLosePitchLast("Yankees"));
    }

    @Test
    public void testGetLosePitchLast2() {
        MlbModel game = new MlbModel();
        game.getGame("08/16/2017", "Astros");
        assertEquals("", game.getLosePitchLast("Astros"));
    }

    @Test
    public void testGetLosePitchFirst1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("LP: Ubaldo", game.getLosePitchFirst("Yankees"));
    }

    @Test
    public void testGetLosePitchFirst2() {
        MlbModel game = new MlbModel();
        game.getGame("08/16/2017", "Astros");
        assertEquals("", game.getLosePitchFirst("Astros"));
    }

    @Test
    public void testGetLosePitchName1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("LP: Ubaldo Jimenez", game.getLosePitchName("Yankees"));
    }

    @Test
    public void testGetLosePitchName2() {
        MlbModel game = new MlbModel();
        game.getGame("08/16/2017", "Astros");
        assertEquals("", game.getLosePitchName("Astros"));
    }

    @Test
    public void testGetLosePitchWins1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("1", game.getLosePitchWins("Yankees"));
    }

    @Test
    public void testGetLosePitchWins2() {
        MlbModel game = new MlbModel();
        game.getGame("07/27/2017", "Mets");
        assertEquals("", game.getLosePitchWins("Mets"));
    }

    @Test
    public void testGetLosePitchLosses1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("1", game.getLosePitchLosses("Yankees"));
    }

    @Test
    public void testGetLosePitchLosses2() {
        MlbModel game = new MlbModel();
        game.getGame("07/27/2017", "Mets");
        assertEquals("", game.getLosePitchLosses("Mets"));
    }

    @Test
    public void testGetLosePitchEra1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("7.43", game.getLosePitchEra("Yankees"));
    }

    @Test
    public void testGetLosePitchEra2() {
        MlbModel game = new MlbModel();
        game.getGame("07/27/2017", "Mets");
        assertEquals("", game.getLosePitchEra("Mets"));
    }

    @Test
    public void testGetLosePitchRecord1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("(1-1, 7.43 ERA)", game.getLosePitchRecord("Yankees"));
    }

    @Test
    public void testGetLosePitchRecord2() {
        MlbModel game = new MlbModel();
        game.getGame("07/27/2017", "Mets");
        assertEquals("", game.getLosePitchRecord("Mets"));
    }

    @Test
    public void testGetSavePitchLast1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Nationals");
        assertEquals("Familia", game.getSavePitchLast("Nationals"));
    }

    @Test
    public void testGetSavePitchLast2() {
        MlbModel game = new MlbModel();
        game.getGame("09/01/2017", "Cubs");
        assertEquals("", game.getSavePitchLast("Cubs"));
    }

    @Test
    public void testGetSavePitchFirst1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Nationals");
        assertEquals("SV: Jeurys", game.getSavePitchFirst("Nationals"));
    }

    @Test
    public void testGetSavePitchFirst2() {
        MlbModel game = new MlbModel();
        game.getGame("09/01/2017", "Cubs");
        assertEquals("", game.getSavePitchFirst("Cubs"));
    }

    @Test
    public void testGetSavePitchName1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Nationals");
        assertEquals("SV: Jeurys Familia", game.getSavePitchName("Nationals"));
    }

    @Test
    public void testGetSavePitchName2() {
        MlbModel game = new MlbModel();
        game.getGame("09/01/2017", "Cubs");
        assertEquals(" ", game.getSavePitchName("Cubs"));
    }

    @Test
    public void testGetSavePitchSave1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Nationals");
        assertEquals("(1)", game.getSavePitchSave("Nationals"));
    }

    @Test
    public void testGetSavePitchSave2() {
        MlbModel game = new MlbModel();
        game.getGame("09/01/2017", "Cubs");
        assertEquals("", game.getSavePitchSave("Cubs"));
    }

    @Test
    public void testGetVenue1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals("Yankee Stadium", game.getVenue("Yankees"));
    }

    @Test
    public void testGetVenue2() {
        MlbModel game = new MlbModel();
        game.getGame("08/08/2017", "Pirates");
        assertEquals("PNC Park", game.getVenue("Pirates"));
    }

    @Test
    public void testGetLocation1() {
        MlbModel game = new MlbModel();
        game.getGame("04/29/2017", "Yankees");
        assertEquals(", Bronx, NY", game.getLocation("Yankees"));
    }

    @Test
    public void testGetLocation2() {
        MlbModel game = new MlbModel();
        game.getGame("08/08/2017", "Pirates");
        assertEquals(", Pittsburgh, PA", game.getLocation("Pirates"));
    }

    @Test
    public void testGetLocation3() {
        MlbModel game = new MlbModel();
        game.getGame("06/20/2010", "Padres");
        assertEquals("", game.getLocation("Padres"));
    }

} // end of MlbModelTest
