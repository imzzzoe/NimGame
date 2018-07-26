import java.util.Random;

public class NimAIPlayer extends NimPlayer{

    /**
     * Construct a NimAIPlayer by username,givenName,familyName
     * @param userName
     * @param givenName
     * @param familyName
     */
    public NimAIPlayer(String userName, String givenName, String familyName) {
        super(userName, givenName, familyName);
        this.type = NimsysConstant.TYPE_AIPLAYER;
    }

    /**
     * Construct a NimAIPlayer
     */
    public NimAIPlayer(){
    }

    /**
     * AI player removal stones
     * @param currentStones
     * @param upperbound
     * @return
     */
    public int removalStone(int currentStones, int upperbound) {
        int takeStoneCount;
        int maxRemoval;

        maxRemoval = setMaxRemoval(currentStones, upperbound);

        Random r = new Random();
        // When not in win condition, random removal
        if (!checkAIWinCondition(currentStones, maxRemoval)) {
            takeStoneCount = r.nextInt(maxRemoval) + 1;
        } else {
            // When in win condition, execute ai strategy
            takeStoneCount = aiStrategy(currentStones, upperbound);
        }
        return takeStoneCount;
    }

    /**
     * If current stones is k(M+1)+1, it is not in win condition
     * @param currentStone
     * @param maxRemoval
     * @return boolean
     */
    public boolean checkAIWinCondition(int currentStone, int maxRemoval) {
        if (0 == (currentStone - 1) % (maxRemoval + 1)) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Calculate removal stones number to ensure the left stone number is k(M+1)+1
     * k = {0,1,2,...}, M = Maximum removal stones
     * @param currentStones
     * @param upperBound
     * @return removel stones number
     */
    public int aiStrategy(int currentStones, int upperBound) {
        // Initialize left stones and maximum removal stones
        int maxRemoval = setMaxRemoval(currentStones,upperBound);

        int leftStones;
        int currentRemoval;
        int nextMaxRemoval;

        for(currentRemoval = maxRemoval; currentRemoval >= NimsysConstant.MIN_REMOVAL_NUM;
                currentRemoval--) {
            // left stones after ai removal
            leftStones = currentStones - currentRemoval;
            // rival player max removal after ai removal
            nextMaxRemoval = setMaxRemoval(leftStones,upperBound);
            // If not in win condition, return
            if( leftStones > 0 && !checkAIWinCondition(leftStones,nextMaxRemoval)){
                return currentRemoval;
            }
        }
        // If can not find a Strategical move, return min removal
        return NimsysConstant.MIN_REMOVAL_NUM;
    }
}