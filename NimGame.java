/**
 * Class of NimGame
 *
 * @author Zhuo Liu (StudentID: 924767)
 * @version 1.0
 */
public class NimGame {
    // The current stone count
    private int currentStones;
    // upper bound on stone removal
    private int upperBound;
    // Players
    private NimPlayer[] players;
    // Rounds
    private int rounds;

    public int getCurrentStones(){
        return this.currentStones;
    }

    /**
     * Initialize current stone, upper bound removal, player, rounds of game and display message
     * @param currentStones current stone number
     * @param upperBound upper bound number of removal stones
     * @param players NimPlayers
     */
    public void init(int currentStones, int upperBound, NimPlayer[] players) {

        this.currentStones = currentStones;
        this.upperBound = upperBound;
        this.players = players;
        this.rounds = 0;

        System.out.println();
        System.out.printf(NimsysConstant.MSG_INIT_STONE_COUNT, currentStones);
        System.out.printf(NimsysConstant.MSG_MAX_STONE_REMOVAL, upperBound);
        for (int i = 0; i < NimsysConstant.PLAYER_NUMBER; i++) {
            System.out.printf(NimsysConstant.MSG_INIT_PLAYERS_DISPLAY, i + 1,
                    players[i].getGivenName(), players[i].getFamilyName());
        }
    }

    public void play(int takeStoneCount) {
        int removal;
        NimPlayer currentPlayer = this.players[getCurrentPlayerIndex()];
        if (currentPlayer.isAI()){
            removal = ((NimAIPlayer)currentPlayer)
                    .removalStone(this.currentStones, this.upperBound);
        } else {
            removal = currentPlayer.removalStone(takeStoneCount);
        }
        // player removal stones
        this.currentStones = this.currentStones - removal;
        // rounds increase 1
        this.rounds = this.rounds + 1;

        if(this.currentStones <= 0) {
            // when current stones <= 0, game over
            // winner's won number increase 1
            NimPlayer winner = this.players[this.rounds%NimsysConstant.PLAYER_NUMBER];
            winner.setWonNumber();

            // Update game players' Game number and won rate
            for (int i = 0; i < NimsysConstant.PLAYER_NUMBER; i++) {
                this.players[i].setGamesNumber();
                this.players[i].setWonRate();
            }

            // Display message
            System.out.println();
            System.out.println(NimsysConstant.MSG_GAME_OVER);
            System.out.printf(NimsysConstant.MSG_WINNER,
                    winner.getGivenName(),
                    winner.getFamilyName());
        }
    }

    /**
     * Get current player index
     * @return player index
     */
    public int getCurrentPlayerIndex() {
        return this.rounds%NimsysConstant.PLAYER_NUMBER;
    }

    /**
     * Display currentStones by stone mark
     * @param stones stones number
     * @param players Nimplayers
     */
    public void displayCurrentStones(int stones, NimPlayer[] players) {
        System.out.println();
        System.out.printf(NimsysConstant.MSG_REST_STONE, stones);
        for (int i = 0; i < stones; i++) {
            if (i != stones - 1) {
                System.out.print(NimsysConstant.STONE_MARK + " ");
            } else {
                System.out.print(NimsysConstant.STONE_MARK);
            }
        }
        System.out.println();
        System.out.printf(NimsysConstant.MSG_REMOVE_STONE,
                players[getCurrentPlayerIndex()].getGivenName());
    }

    /**
     * Validate removal stones count is between 1 and upper bound or current stone count
     * @param takeStoneCount number of removal stones
     * @return Exception
     */
    public void validateRemovalCount(int takeStoneCount) throws Exception {
        String msg;
        int min = Math.min(this.upperBound,this.currentStones);
        if ( takeStoneCount < NimsysConstant.MIN_REMOVAL_NUM
                || takeStoneCount > min) {
            msg = String.format(NimsysConstant.ERROR_INVALID_REMOVE,
                    NimsysConstant.MIN_REMOVAL_NUM,min);
            throw new Exception(msg);
        }
    }
}
