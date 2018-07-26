import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.lang.Exception;

/**
 * This program implements related operations an game of Nim.
 *
 * @author Zhuo Liu (StudentID: 924767)
 * @version 1.0
 */
public class Nimsys {
    // A collection of players
    private NimPlayer[] nimPlayers;

    // Nimsys constructor
    public Nimsys() {
        // Display welcome Message
        System.out.println(NimsysConstant.MSG_WELCOME);
    }

    public static void main(String[] args) {
        // Initialize Ninsys
        Nimsys nimsys = new Nimsys();
        Command<AddPlayerMessage> addPlayerCommand;
        Command<RemovePlayerMessage> removePlayerCommand;
        Command<EditPlayerMessage> editPlayerCommand;
        Command<ResetStatsMessage> resetStatsCommand;
        Command<DisplayPlayerMessage> displayPlayerCommand;
        Command<RankingMessage> rankingCommand;
        Command<InitGameMessage> initCommand;
        Command<PlayGameMessage> playCommand;

        // Load players data
        NimPlayerRepository npr = new NimPlayerRepository(NimsysConstant.PLAYERS_FILE);
        try {
            nimsys.nimPlayers = npr.loadPlayersInfo();
        } catch (FileNotFoundException e){
            nimsys.nimPlayers = null;
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        // Read and execute commands
        Scanner keyboard = new Scanner(System.in);
        List<String> command;
        String commandCode = null;

        while (true){
            System.out.println();
            System.out.print(NimsysConstant.COMMAND_MARK);

            // Parse command
            try {
                command = nimsys.parseCommand(keyboard.nextLine().trim());
                if (command != null && command.size() > 0){
                    nimsys.validateCommand(command);
                    commandCode = command.get(0).toLowerCase();
                } else {
                    continue;
                }

                // Execute command
                switch (commandCode) {
                    case "addplayer" : {
                        // Initialize command
                        addPlayerCommand = new AddPlayerCommand(nimsys);

                        // Validate players exist
                        if(nimsys.validatePlayerExists(
                                command.get(NimsysConstant.ADDPLAYER_USERNAME_INDEX),
                                nimsys.nimPlayers)){
                            System.out.println(NimsysConstant.ERROR_PLAYER_EXISTS);
                            continue;
                        }

                        // Execute addplayer command
                        addPlayerCommand.execute(new AddPlayerMessage(command));
                        break;
                    }
                    case "addaiplayer" : {
                        // Initialize command
                        addPlayerCommand = new AddAIPlayerCommand(nimsys);

                        // Validate players exist
                        if(nimsys.validatePlayerExists(
                                command.get(NimsysConstant.ADDPLAYER_USERNAME_INDEX),
                                nimsys.nimPlayers)){
                            System.out.println(NimsysConstant.ERROR_PLAYER_EXISTS);
                            continue;
                        }

                        // Execute addplayer command
                        addPlayerCommand.execute(new AddPlayerMessage(command));
                        break;
                    }
                    case "removeplayer" : {
                        // Initialize command
                        removePlayerCommand = new RemovePlayerCommand(nimsys);

                        if (command.size() == 1) {
                            // If remove all players, display confirmation
                            System.out.printf(NimsysConstant.MSG_REMOVE_ALL_PLAYER);
                            if (!keyboard.nextLine().trim().toLowerCase().equals("y")) {
                                continue;
                            }
                        } else {
                            // Validate player exists
                            if (!nimsys.validatePlayerExists(
                                    command.get(NimsysConstant.RMVPLAYER_USERNAME_INDEX),
                                    nimsys.nimPlayers)){
                                System.out.println(NimsysConstant.ERROR_PLAYER_NOT_EXISTS);
                                continue;
                            }
                        }
                        // Execute removeplayer command
                        removePlayerCommand.execute(new RemovePlayerMessage(command));
                        break;
                    }
                    case "editplayer" : {
                        // Initialize command
                        editPlayerCommand = new EditPlayerCommand(nimsys);

                        // Validate player exists
                        if(!nimsys.validatePlayerExists(
                                command.get(NimsysConstant.EDTPLAYER_USERNAME_INDEX),
                                nimsys.nimPlayers)){
                            System.out.println(NimsysConstant.ERROR_PLAYER_NOT_EXISTS);
                            continue;
                        }
                        // Execute editplayer command
                        editPlayerCommand.execute(new EditPlayerMessage(command));
                        break;
                    }
                    case "resetstats" : {
                        // Initialize command
                        resetStatsCommand = new ResetStatsCommand(nimsys);

                        if(command.size() == 1) {
                            // If reset statistics of all players, display confirmation
                            System.out.printf(NimsysConstant.MSG_RESET_ALL_PLAYER_STAT);
                            if (!keyboard.nextLine().trim().toLowerCase().equals("y")){
                                continue;
                            }
                        } else {
                            // Validate player exists
                            if (!nimsys.validatePlayerExists(
                                    command.get(NimsysConstant.RSTSTSPLAYER_USERNAME_INDEX),
                                    nimsys.nimPlayers)){
                                System.out.println(NimsysConstant.ERROR_PLAYER_NOT_EXISTS);
                                continue;
                            }
                        }

                        // Execute reset statistic command
                        resetStatsCommand.execute(new ResetStatsMessage(command));
                        break;
                    }
                    case "displayplayer" :{
                        // Initialize command
                        displayPlayerCommand = new DisplayPlayerCommand(nimsys);

                        if(command.size() > 1){
                            // Validate player exists
                            if (!nimsys.validatePlayerExists(
                                    command.get(NimsysConstant.DSPPLAYER_USERNAME_INDEX),
                                    nimsys.nimPlayers)) {
                                System.out.println(NimsysConstant.ERROR_PLAYER_NOT_EXISTS);
                                continue;
                            }
                        }

                        // Execute Display command
                        displayPlayerCommand.execute(new DisplayPlayerMessage(command));
                        break;
                    }
                    case "rankings" :{
                        // Initialize command
                        rankingCommand = new RankingCommand(nimsys);

                        // Execute rankings command
                        rankingCommand.execute(new RankingMessage(command));
                        break;
                    }
                    case "startgame" : {
                        NimGame game = new NimGame();

                        // Initialize command
                        initCommand = new InitGameCommand(game);
                        playCommand = new PlayGameCommand(game);

                        // Validate players are existing players
                        if (!nimsys.validatePlayerExists(command.subList(
                                NimsysConstant.STTGAME_USERNAME_INDEX,
                                NimsysConstant.STTGAME_USERNAME_INDEX
                                        + NimsysConstant.PLAYER_NUMBER),
                                nimsys.nimPlayers)){
                            System.out.println(NimsysConstant.ERROR_ONE_PLAYER_NOT_EXIST);
                            continue;
                        }

                        // Get game players
                        NimPlayer[] gamePlayers = new NimPlayer[NimsysConstant.PLAYER_NUMBER];
                        for (int i = 0; i < NimsysConstant.PLAYER_NUMBER; i++) {
                            gamePlayers[i] = nimsys.getPlayerByUsername(command
                                            .get(i + NimsysConstant.STTGAME_USERNAME_INDEX),
                                    nimsys.nimPlayers);
                        }
                        // Execute initialize game command
                        initCommand.execute(new InitGameMessage(command, gamePlayers));

                        // Play game in turn, read from scanner and loop
                        int takeStone;
                        while (game.getCurrentStones() > 0){
                            try{
                                // Display current stones and player in turn
                                game.displayCurrentStones(game.getCurrentStones(), gamePlayers);

                                // If Nimplayer is human, Input removal stones
                                if(gamePlayers[game.getCurrentPlayerIndex()].isAI()) {
                                    // Play game
                                    playCommand.execute(new PlayGameMessage());
                                } else {
                                    takeStone = Integer.parseInt(keyboard.nextLine());
                                    // Validate removal stones number
                                    game.validateRemovalCount(takeStone);
                                    // Play game
                                    playCommand.execute(new PlayGameMessage(takeStone));
                                }

                            } catch (Exception e){
                                System.out.println();
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    }
                    case "exit" : {
                        // save players information to players data file
                        if (nimsys.nimPlayers!=null && nimsys.nimPlayers.length != 0){
                            npr.savePlayerInfo(nimsys.nimPlayers);
                        }

                        System.out.println();
                        System.exit(0);
                        break;
                    }
                    default: {
                        break;
                    }
                }
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
        }

    }

    /**
     * add HumanPlayer to NimPlayers
     * @param userName: NimPlayer's username
     * @param givenName: NimPlayer's given name
     * @param familyName: NimPlayer's family name
     */
    public void addPlayer(String userName, String givenName, String familyName, String type) {
        NimPlayer player;
        if (type.equals(NimsysConstant.TYPE_AIPLAYER)){
            player = new NimAIPlayer(userName, givenName, familyName);
        } else {
            player = new NimHumanPlayer(userName, givenName, familyName);
        }

        NimPlayer[] tempPlayers;
        if (this.nimPlayers != null){
            tempPlayers = new NimPlayer[this.nimPlayers.length + 1];
            System.arraycopy(this.nimPlayers,0,tempPlayers,0,this.nimPlayers.length);
            tempPlayers[this.nimPlayers.length]=player;
            this.nimPlayers = tempPlayers;
        } else {
            this.nimPlayers = new NimPlayer[1];
            nimPlayers[0] = player;
        }
    }

    /**
     * Validate command is valid and the arguments size is correct
     * @param command
     * @throws Exception
     */
    public void validateCommand(List<String> command) throws Exception{
        String commandCode = command.get(0).toLowerCase();
        switch (commandCode) {
            case "addplayer": {
                if (command.size() < NimsysConstant.MAX_ARG_SIZE_ADDPLAYER) {
                    throw new Exception(NimsysConstant.ERROR_INVALIDATE_ARGU_NUM);
                }
                break;
            }
            case "addaiplayer": {
                if (command.size() < NimsysConstant.MAX_ARG_SIZE_ADDAIPLAYER) {
                    throw new Exception(NimsysConstant.ERROR_INVALIDATE_ARGU_NUM);
                }
                break;
            }
            case "removeplayer": {
                break;
            }
            case "editplayer": {
                if (command.size() < NimsysConstant.MAX_ARG_SIZE_EDITPLAYER) {
                    throw new Exception(NimsysConstant.ERROR_INVALIDATE_ARGU_NUM);
                }
                break;
            }
            case "resetstats": {
                break;
            }
            case "displayplayer": {
                break;
            }
            case "rankings": {
                break;
            }
            case "startgame": {
                if (command.size() < NimsysConstant.MAX_ARG_SIZE_STARTGAME) {
                    throw new Exception(NimsysConstant.ERROR_INVALIDATE_ARGU_NUM);
                }
                break;
            }
            case "exit": {
                break;
            }
            default: {
                String msg = String.format(NimsysConstant.ERROR_INVALIDATE_COMMAND,commandCode);
                throw new Exception(msg);
            }
        }
    }

    /**
     * edit a particular player by username
     * @param userName: NimPlayer's username
     * @param givenName: NimPlayer's given name
     * @param familyName: NimPlayer's family name
     */
    public void editPlayer(String userName, String familyName, String givenName) {
        int index = getPlayerIndexByUsername(userName,this.nimPlayers);
        this.nimPlayers[index].setFamilyName(familyName);
        this.nimPlayers[index].setGivenName(givenName);
    }

    /**
     * reset Statistics a particular player by username
     * @param userName: NimPlayer's username
     */
    public void resetStats(String userName) {
        int index = getPlayerIndexByUsername(userName,this.nimPlayers);
        this.nimPlayers[index].setGamesNumber(0);
        this.nimPlayers[index].setWonNumber(0);
    }

    /**
     * reset Statistics of all players
     */
    public void resetStats() {
        if (this.nimPlayers !=null){
            for (NimPlayer player:this.nimPlayers) {
                player.setGamesNumber(0);
                player.setWonNumber(0);
            }
        }
    }

    /**
     * DisPlay all players
     */
    public void displayPlayer() {
        String username, givenName, familyName;
        int gameNumber, wonNumber;
        NimPlayer[] sortList = this.nimPlayers;

        if(sortList != null){
            Arrays.sort(sortList, new NimPlayer.UserNameComparatorAsc());
            for (NimPlayer player:sortList) {
                username = player.getUserName();
                givenName = player.getGivenName();
                familyName = player.getFamilyName();
                gameNumber = player.getGamesNumber();
                wonNumber = player.getWonNumber();

                System.out.printf(NimsysConstant.MSG_DISPLAY_PLAYERS_INFO,
                        username,givenName,familyName,gameNumber,wonNumber);
            }
        }
    }

    /**
     * Display particular player by username
     * @param userName: NimPlayer's username
     */
    public void displayPlayer(String userName) {
        if(this.nimPlayers != null && userName != null){
            NimPlayer player = getPlayerByUsername(userName,this.nimPlayers);
            System.out.printf(NimsysConstant.MSG_DISPLAY_PLAYERS_INFO,
                    player.getUserName(),player.getGivenName(),player.getFamilyName(),
                    player.getGamesNumber(),player.getWonNumber());
        }
    }

    /**
     * Remove particular NimPlayer by username
     * @param userName: NimPlayer's username
     */
    public void removePlayer(String userName) {
        if(this.nimPlayers != null && userName != null){
            int index = getPlayerIndexByUsername(userName,this.nimPlayers);
            if(this.nimPlayers.length == 1){
                this.nimPlayers = null;
            } else {
                NimPlayer[] newPlayerArray = new NimPlayer[this.nimPlayers.length - 1];
                if (index == 0 ){
                    System.arraycopy(this.nimPlayers,1,newPlayerArray,0,
                            this.nimPlayers.length-1);
                } else if (index + 1 == this.nimPlayers.length) {
                    System.arraycopy(this.nimPlayers,0,newPlayerArray,0,
                            this.nimPlayers.length-1);
                } else {
                    System.arraycopy(this.nimPlayers,0,newPlayerArray,0,
                            index);
                    System.arraycopy(this.nimPlayers,index + 1,newPlayerArray,index,
                            nimPlayers.length - index - 1);
                }
                this.nimPlayers = newPlayerArray;
            }
        }
    }

    /**
     * remove all NimPlayers
     */
    public void removePlayer() {
        this.nimPlayers=null;
    }

    /**
     * rank all players in descending order
     */
    public void ranking() {
        ranking(NimsysConstant.COMMAND_ORDER_DESC);
    }

    /**
     * rank all players in descending/ascending order
     * @param order: asc or desc
     */
    public void ranking(String order) {
        if(this.nimPlayers != null){
            // sort players in descending or ascending order
            NimPlayer[] comparePlayerList = this.nimPlayers;
            if(order.toLowerCase().equals(NimsysConstant.COMMAND_ORDER_DESC)) {
                Arrays.sort(comparePlayerList, new NimPlayer.WonRateComparatorDesc());
            } else if(order.toLowerCase().equals(NimsysConstant.COMMAND_ORDER_ASC)) {
                Arrays.sort(comparePlayerList, new NimPlayer.WonRateComparatorAsc());
            }

            // Display Top 10 Players
            int wonRate;
            int gameNumber;
            String givenName;
            String familyName;
            int arrayLength;
            if(0 == comparePlayerList.length) {
                return;
            } else if (comparePlayerList.length < NimsysConstant.MAX_RANKING_PLAYERS) {
                arrayLength = comparePlayerList.length;
            } else {
                arrayLength = NimsysConstant.MAX_RANKING_PLAYERS;
            }
            for (int i = 0; i < arrayLength; i++) {
                gameNumber = comparePlayerList[i].getGamesNumber();
                wonRate = comparePlayerList[i].getWonRate();
                givenName = comparePlayerList[i].getGivenName();
                familyName = comparePlayerList[i].getFamilyName();
                System.out.printf(NimsysConstant.MSG_RANGKING,Integer.toString(wonRate)+"%",
                        gameNumber,givenName,familyName);
            }
        }
    }

    /**
     * Parse console inputs into command and parameters
     * @param textLine: input command
     * @return List {commandCode, para1, para2, ...}
     */
    public List<String> parseCommand(String textLine) {
        String regex = NimsysConstant.COMMAND_CODE_SPLIT + "|"
                + NimsysConstant.COMMAND_PARAMETER_SPLIT;
        String[] content = textLine.split(regex);

        ArrayList<String> command = new ArrayList<>();
        for (String term:content) {
            if (term.length()>0) {
                command.add(term);
            }
        }
        return command;
    }

    /**
     * Validate userName exists
     * @param userName: NimPlayer's username
     * @param players: NimPlayer List
     * @return exist
     */
    public Boolean validatePlayerExists(String userName, NimPlayer[] players) {
        boolean exist = false;
        if (players != null) {
            int index = getPlayerIndexByUsername(userName,players);
            if (index >= 0) {
                exist = true;
            } else {
                exist = false;
            }
        }
        return exist;
    }

    /**
     * Validate userNames exists, if one username doesn't exist, return false
     * @param userNames
     * @param players
     * @return exist
     */
    public Boolean validatePlayerExists(List<String> userNames, NimPlayer[] players) {
        boolean exist = true;
        for (int i = 0; i < userNames.size(); i++) {
            if(!validatePlayerExists(userNames.get(i), players)){
                return false;
            }
        }
        return exist;
    }

    /**
     * Search NimPlayer by username
     * @param userName: NimPlayer's username
     * @param players: NimPlayer List
     * @return Nimplayer
     */
    public NimPlayer getPlayerByUsername(String userName, NimPlayer[] players){
        int index = getPlayerIndexByUsername(userName, players);

        if(index >= 0){
            return players[index];
        } else {
            return null;
        }
    }

    /**
     * Search index of NimPlayer by username
     * @param userName: NimPlayer's username
     * @param players: NimPlayer List
     * @return Nimplayer
     */
    public int getPlayerIndexByUsername(String userName, NimPlayer[] players){
        int index = -1;
        if(players != null){
            for (int i = 0; i < players.length; i ++){
                if(userName.equals(players[i].getUserName())) {
                    index = i;
                }
            }
        }
        return index;
    }

    /**
     * Interface of commands
     * @param <R>: message class
     */
    public interface Command<R> {
        void execute(R message);
    }

    /**
     * Implement initial command of NimGame
     */
    public static class InitGameCommand implements Command<InitGameMessage> {
        private NimGame game;

        public InitGameCommand(NimGame game) {
            this.game = game;
        }

        @Override
        public void execute(InitGameMessage message) {
            game.init(message.getCurrentStones(), message.getUpperBound(), message.getPlayers());
        }
    }

    /**
     * Set properties received from client to initial command
     */
    public static class InitGameMessage {
        private int currentStones;
        private int upperBound;
        private NimPlayer[] players;

        public InitGameMessage(List<String> command, NimPlayer[] players) {
            if(command !=null){
                this.currentStones = Integer.parseInt(command
                        .get(NimsysConstant.STTGAME_CURSTONE_INDEX));
                this.upperBound = Integer.parseInt(command
                        .get(NimsysConstant.STTGAME_UPPERBOUND_INDEX));
            }
            this.players = players;
        }
        public int getCurrentStones() {
            return currentStones;
        }

        public int getUpperBound() {
            return upperBound;
        }

        public NimPlayer[] getPlayers() {
            return players;
        }

    }

    /**
     * Implement play game command
     */
    public static class PlayGameCommand implements Command<PlayGameMessage> {
        private NimGame game;

        public PlayGameCommand(NimGame game) {
            this.game = game;
        }

        @Override
        public void execute(PlayGameMessage message) {
            game.play(message.getTakeStoneCount());
        }
    }

    /**
     * Set properties received from client to play game command
     */
    public static class PlayGameMessage {
        private int takeStoneCount;

        public PlayGameMessage(){
            takeStoneCount = NimsysConstant.MIN_REMOVAL_NUM;
        }

        public PlayGameMessage(int takeStoneCount) {
            this.takeStoneCount = takeStoneCount;
        }

        public int getTakeStoneCount() {
            return takeStoneCount;
        }
    }

    /**
     * Implement add player command
     */
    public static class AddPlayerCommand implements Command<AddPlayerMessage> {
        protected Nimsys nimsys;

        public AddPlayerCommand(Nimsys nimsys) {
            this.nimsys = nimsys;
        }

        @Override
        public void execute(AddPlayerMessage message) {
            nimsys.addPlayer(message.getUserName(),message.getGivenName(),message.getFamilyName(),
                    NimsysConstant.TYPE_HMPLAYER);
        }
    }

    public static class AddAIPlayerCommand implements Command<AddPlayerMessage> {
        protected Nimsys nimsys;

        public AddAIPlayerCommand(Nimsys nimsys) {
            this.nimsys = nimsys;
        }

        @Override
        public void execute(AddPlayerMessage message) {
            nimsys.addPlayer(message.getUserName(),message.getGivenName(), message.getFamilyName(),
                    NimsysConstant.TYPE_AIPLAYER);
        }
    }

    /**
     * Set properties received from client to add player command
     */
    public static class AddPlayerMessage {
        private String userName;
        private String familyName;
        private String givenName;


        public AddPlayerMessage(List<String> command) {
                this.userName = command.get(NimsysConstant.ADDPLAYER_USERNAME_INDEX);
                this.familyName = command.get(NimsysConstant.ADDPLAYER_FMYNAME_INDEX);
                this.givenName = command.get(NimsysConstant.ADDPLAYER_GVNNAME_INDEX);
        }

        public String getUserName() {
            return userName;
        }

        public String getGivenName() {
            return givenName;
        }

        public String getFamilyName() {
            return familyName;
        }
    }

    /**
     * Implement display player command
     */
    public static class DisplayPlayerCommand implements Command<DisplayPlayerMessage> {
        private Nimsys nimsys;

        public DisplayPlayerCommand(Nimsys nimsys) {
            this.nimsys = nimsys;
        }

        @Override
        public void execute(DisplayPlayerMessage message) {
            if(message.userName != null){
                nimsys.displayPlayer(message.getUserName());
            } else {
                nimsys.displayPlayer();
            }

        }
    }

    /**
     * Set properties received from client to display player command
     */
    public static class DisplayPlayerMessage {
        private String userName;

        public DisplayPlayerMessage(List<String> command) {
            if(command != null && command.size() > 1){
                this.userName = command.get(NimsysConstant.DSPPLAYER_USERNAME_INDEX);
            } else {
                this.userName = null;
            }
        }

        public String getUserName() {
            return userName;
        }
    }

    /**
     * Implement remove player command
     */
    public static class RemovePlayerCommand implements Command<RemovePlayerMessage> {
        private Nimsys nimsys;

        public RemovePlayerCommand(Nimsys nimsys) {
            this.nimsys = nimsys;
        }

        @Override
        public void execute(RemovePlayerMessage message) {
            if(message.userName != null){
                nimsys.removePlayer(message.getUserName());
            } else {
                nimsys.removePlayer();
            }
        }
    }

    /**
     * Set properties received from client to display player command
     */
    public static class RemovePlayerMessage {
        private String userName;

        public RemovePlayerMessage(List<String> command) {
            if(command != null && command.size() > 1){
                this.userName = command.get(NimsysConstant.RMVPLAYER_USERNAME_INDEX);
            } else {
                this.userName = null;
            }
        }

        public String getUserName() {
            return userName;
        }
    }

    /**
     * Implement edit player command
     */
    public static class EditPlayerCommand implements Command<EditPlayerMessage> {
        private Nimsys nimsys;

        public EditPlayerCommand(Nimsys nimsys) {
            this.nimsys = nimsys;
        }

        @Override
        public void execute(EditPlayerMessage message) {
            nimsys.editPlayer(message.getUserName(), message.getFamilyName(),
                    message.getGivenName());
        }
    }

    /**
     * Set properties received from client to edit player command
     */
    public static class EditPlayerMessage {
        private String userName;
        private String givenName;
        private String familyName;

        public EditPlayerMessage(List<String> command) {
                this.userName = command.get(NimsysConstant.EDTPLAYER_USERNAME_INDEX);
                this.familyName = command.get(NimsysConstant.EDTPLAYER_FMYNAME_INDEX);
                this.givenName = command.get(NimsysConstant.EDTPLAYER_GVNNAME_INDEX);
        }

        public String getUserName() {
            return userName;
        }
        public String getGivenName() {
            return givenName;
        }
        public String getFamilyName() {
            return familyName;
        }
    }

    /**
     * Implement reset statistic command
     */
    public static class ResetStatsCommand implements Command<ResetStatsMessage> {
        private Nimsys nimsys;

        public ResetStatsCommand(Nimsys nimsys) {
            this.nimsys = nimsys;
        }

        @Override
        public void execute(ResetStatsMessage message) {
            if(message.userName != null){
                nimsys.resetStats(message.getUserName());
            } else {
                nimsys.resetStats();
            }
        }
    }

    /**
     * Set properties received from client to reset statistic command
     */
    public static class ResetStatsMessage {
        private String userName;

        public ResetStatsMessage(List<String> command) {
            if(command != null && command.size() > 1){
                this.userName = command.get(NimsysConstant.RSTSTSPLAYER_USERNAME_INDEX);
            } else {
                this.userName = null;
            }
        }

        public String getUserName() {
            return userName;
        }
    }

    /**
     * Implement ranking command
     */
    public static class RankingCommand implements Command<RankingMessage> {
        private Nimsys nimsys;

        public RankingCommand(Nimsys nimsys) {
            this.nimsys = nimsys;
        }

        @Override
        public void execute(RankingMessage message) {
            if (message.order != null) {
                nimsys.ranking(message.getOrder());
            } else {
                nimsys.ranking();
            }
        }
    }

    /**
     * Set properties received from client to ranking command
     */
    public static class RankingMessage {
        private String order;

        public RankingMessage(List<String> command) {
            if(command.size() == 2) {
                this.order = command.get(NimsysConstant.RKG_ORDER_INDEX);
            } else{
                this.order = null;
            }
        }

        public String getOrder() {
            return order;
        }
    }

}
