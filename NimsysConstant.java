import java.util.Map;

/**
 * Class of Nimsys Constants
 * @autho Zhuo Liu (StudentID: 924767)
 * @version 1.0
 */
public final class NimsysConstant {
    // Dialog Messages
    public static final String MSG_WELCOME = "Welcome to Nim";
    public static final String MSG_REST_STONE = "%d stones left: ";
    public static final String MSG_REMOVE_STONE = "%s's turn - remove how many?%n";
    public static final String MSG_GAME_OVER = "Game Over";
    public static final String MSG_WINNER = "%s %s wins!%n";
    public static final String MSG_REMOVE_ALL_PLAYER =
            "Are you sure you want to remove all players? (y/n)%n";
    public static final String MSG_RESET_ALL_PLAYER_STAT =
            "Are you sure you want to reset all player statistics? (y/n)%n";
    public static final String MSG_INIT_STONE_COUNT = "Initial stone count: %d%n";
    public static final String MSG_MAX_STONE_REMOVAL = "Maximum stone removal: %d%n";
    public static final String MSG_INIT_PLAYERS_DISPLAY = "Player %d: %s %s%n";
    public static final String MSG_DISPLAY_PLAYERS_INFO = "%s,%s,%s,%d games,%d wins%n";
    public static final String MSG_RANGKING = "%-4s | %02d games | %s %s%n";

    // Validation Messages
    public static final String ERROR_PLAYER_NOT_EXISTS = "The player does not exist.";
    public static final String ERROR_PLAYER_EXISTS = "The player already exists.";
    public static final String ERROR_ONE_PLAYER_NOT_EXIST =
            "One of the players does not exist.";
    public static final String ERROR_INVALID_REMOVE =
            "Invalid move. You must remove between %d and %d stones.";
    public static final String ERROR_INVALIDATE_COMMAND = "'%s' is not a valid command.";
    public static final String ERROR_INVALIDATE_ARGU_NUM =
            "Incorrect number of arguments supplied to command.";
    public static final String ERROR_INVALIDATE_CONTENT = "File Content is invalidate.";

    // Configurations
    public static final String COMMAND_MARK = "$";
    public static final String STONE_MARK = "*";
    public static final String COMMAND_CODE_SPLIT = " ";
    public static final String COMMAND_PARAMETER_SPLIT = ",";
    public static final String PLAYERINFO_SPLIT = ",";
    public static final String COMMAND_ORDER_DESC = "desc";
    public static final String COMMAND_ORDER_ASC = "asc";
    public static final String TYPE_HMPLAYER = "NimHumanPlayer";
    public static final String TYPE_AIPLAYER = "NimAIPlayer";
    public static final String PLAYERS_FILE = "players.dat";
    public static final int PLAYER_NUMBER = 2;
    public static final int MAX_RANKING_PLAYERS = 10;
    public static final int ADDPLAYER_USERNAME_INDEX = 1;
    public static final int ADDPLAYER_FMYNAME_INDEX = 2;
    public static final int ADDPLAYER_GVNNAME_INDEX = 3;
    public static final int EDTPLAYER_USERNAME_INDEX = 1;
    public static final int EDTPLAYER_FMYNAME_INDEX = 2;
    public static final int EDTPLAYER_GVNNAME_INDEX = 3;
    public static final int RMVPLAYER_USERNAME_INDEX = 1;
    public static final int DSPPLAYER_USERNAME_INDEX = 1;
    public static final int RSTSTSPLAYER_USERNAME_INDEX = 1;
    public static final int STTGAME_CURSTONE_INDEX = 1;
    public static final int STTGAME_UPPERBOUND_INDEX = 2;
    public static final int STTGAME_USERNAME_INDEX = 3;
    public static final int RKG_ORDER_INDEX = 1;
    public static final int MIN_REMOVAL_NUM = 1;
    public static final int MAX_ARG_SIZE_ADDPLAYER = 4;
    public static final int MAX_ARG_SIZE_ADDAIPLAYER = 4;
    public static final int MAX_ARG_SIZE_REMOVEPLAYER = 2;
    public static final int MAX_ARG_SIZE_EDITPLAYER = 4;
    public static final int MAX_ARG_SIZE_RESETSTATS = 2;
    public static final int MAX_ARG_SIZE_DISPLAYPLAYER = 2;
    public static final int MAX_ARG_SIZE_RANKINGS = 2;
    public static final int MAX_ARG_SIZE_STARTGAME = 5;
    public static final int MAX_ARG_SIZE_EXIT = 1;
    public static final int PLAYERSINFOR_USERNAME_INDEX = 0;
    public static final int PLAYERSINFOR_GVNNAME_INDEX = 1;
    public static final int PLAYERSINFOR_FMYNAME_INDEX = 2;
    public static final int PLAYERSINFOR_GAMESNUMBER_INDEX = 3;
    public static final int PLAYERSINFOR_WONNUMBER_INDEX = 4;
    public static final int PLAYERSINFOR_WONRATE_INDEX = 5;
    public static final int PLAYERSINFOR_TYPE_INDEX = 6;
}
