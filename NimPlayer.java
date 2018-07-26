import java.util.Comparator;
/**
 * Class of game player
 * @author Zhuo Liu (StudentID: 924767)
 * @version 1.0
 */
public class NimPlayer implements Comparable<NimPlayer>{

    // player information
    protected String userName;
    protected String givenName;
    protected String familyName;
    protected int gamesNumber;
    protected int wonNumber;
    protected int wonRate;
    protected String type;

    /**
     * Construct a Nimplayer by username,givenName,familyName
     * @param userName
     * @param givenName
     * @param familyName
     */
    public NimPlayer(String userName, String givenName, String familyName) {
        super();
        this.userName = userName;
        this.givenName = givenName;
        this.familyName = familyName;
        this.gamesNumber = 0;
        this.wonNumber = 0;
        this.wonRate = 0;
    }

    /**
     * Construct a Nimplayer
     */
    public NimPlayer(){
    }

    public String getUserName() {
        return userName;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName){
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }
    public void setFamilyName(String familyName){
        this.familyName = familyName;
    }

    public int getGamesNumber() {
        return gamesNumber;
    }

    public void setGamesNumber() {
        this.gamesNumber = this.gamesNumber + 1;
    }

    public void setGamesNumber(int number) {
        this.gamesNumber = number;
    }

    public int getWonNumber() {
        return wonNumber;
    }
    public void setWonNumber(int number) {
        this.wonNumber = number;
    }

    public void setWonNumber() {
        this.wonNumber = this.wonNumber + 1;
    }

    public int getWonRate() {
        return wonRate;
    }

    public void setWonRate() {
        if(this.gamesNumber == 0) {
            this.wonRate = 0;
        } else {
            this.wonRate = (int) Math.round(this.wonNumber * 1.0/ this.gamesNumber * 100);
        }
    }

    public void setType(String type){
        this.type = type;
    }

    /**
     * If type is NimAIPlayer return true, otherwise return false
     * @return boolean
     */
    public boolean isAI(){
        if (this.type.equals(NimsysConstant.TYPE_AIPLAYER)){
            return true;
        } else {
            return false;
        }
    }

    /**
     * Rmoval stones
     * @param takeStoneCount
     * @return removal stone
     */
    public int removalStone(int takeStoneCount){
        return takeStoneCount;
    }

    /**
     * Override of compareTo, comparing NimPlayer's username in ascending order
     * @param o
     * @return Integer
     */
    @Override
    public int compareTo(NimPlayer o) {
        return this.getUserName().compareTo(o.userName);
    }

    /**
     * Implementation of Comparator, comparing NimPlayer's username in ascending order
     */
    public static class UserNameComparatorAsc implements Comparator<NimPlayer> {
        @Override
        public int compare(NimPlayer o1, NimPlayer o2) {
            return o1.getUserName().compareTo(o2.getUserName());
        }
    }

    /**
     * Implementation of Comparator, comparing NimPlayer's won rate in ascending order
     */
    public static class WonRateComparatorAsc implements Comparator<NimPlayer> {
        @Override
        public int compare(NimPlayer o1, NimPlayer o2) {
            if(0 == o1.getWonRate()-o2.getWonRate()) {
                return o1.getUserName().compareTo(o2.getUserName());
            } else {
                return o1.getWonRate()-o2.getWonRate();
            }
        }
    }

    /**
     * Implementation of Comparator, compare NimPlayer's won rate in descending order
     */
    public static class WonRateComparatorDesc implements Comparator<NimPlayer> {
        @Override
        public int compare(NimPlayer o1, NimPlayer o2) {
            if(0 == o1.getWonRate()-o2.getWonRate()) {
                return o1.getUserName().compareTo(o2.getUserName());
            } else {
                return o2.getWonRate()-o1.getWonRate();
            }
        }
    }

    /**
     * To return max removal
     * if current stones > upper bound, return upper bound
     * else return current stones
     * @param currentStones
     * @param upperbound
     * @return max removal
     */
    public int setMaxRemoval(int currentStones, int upperbound) {
        if( upperbound > currentStones){
            return currentStones;
        } else {
            return upperbound;
        }
    }

    /**
     * change Nimplay to String
     * @return string
     */
    @Override
    public String toString() {
        String s = this.userName + ","
                + this.givenName + ","
                + this.familyName + ","
                + Integer.toString(this.gamesNumber) + ","
                + Integer.toString(this.wonNumber) + ","
                + Integer.toString(this.wonRate) + ","
                + this.type;
        return s;
    }

    /**
     * set NimPlayer info
     */
    public void setNimPlayer(String[] content) throws Exception{
        if (content ==null || content.length != 7){
            throw new Exception(NimsysConstant.ERROR_INVALIDATE_CONTENT);
        }
        this.userName = content[NimsysConstant.PLAYERSINFOR_USERNAME_INDEX];
        this.givenName = content[NimsysConstant.PLAYERSINFOR_GVNNAME_INDEX];
        this.familyName = content[NimsysConstant.PLAYERSINFOR_FMYNAME_INDEX];
        this.gamesNumber = Integer.parseInt(content[NimsysConstant.PLAYERSINFOR_GAMESNUMBER_INDEX]);
        this.wonNumber = Integer.parseInt(content[NimsysConstant.PLAYERSINFOR_WONNUMBER_INDEX]);
        this.wonRate = Integer.parseInt(content[NimsysConstant.PLAYERSINFOR_WONRATE_INDEX]);
        this.type = content[NimsysConstant.PLAYERSINFOR_TYPE_INDEX];
    }
}



