public class NimHumanPlayer extends NimPlayer{

    /**
     * Construct a NimHumanPlayer by username,givenName,familyName
     *
     * @param userName
     * @param givenName
     * @param familyName
     */
    public NimHumanPlayer(String userName, String givenName, String familyName) {
        super(userName, givenName, familyName);
        this.type = NimsysConstant.TYPE_HMPLAYER;
    }

    /**
     * Construct a NimHumanPlayer
     */
    public NimHumanPlayer(){

    }
}
