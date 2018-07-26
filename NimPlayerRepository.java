import java.io.*;
import java.util.*;

public class NimPlayerRepository {
    private File playersFile;

    public NimPlayerRepository(String fileName){
        playersFile = new File(fileName);
    }

    /**
     * save Nimplayer data to players file
     * @param playerInfo Nimplayers
     */
    public void savePlayerInfo(NimPlayer[] playerInfo) throws Exception{
        FileWriter fw = new FileWriter(this.playersFile,false);
        for (NimPlayer player:playerInfo) {
            fw.write(player.toString());
            fw.write(System.lineSeparator());
        }
        fw.close();
    }

    /**
     * load players information from players file
     * @return Nimplayer Array
     * @throws Exception
     */
    public NimPlayer[] loadPlayersInfo() throws Exception {
        NimPlayer[] players = null;
        List<NimPlayer> playerList = new ArrayList<NimPlayer>();
        NimPlayer player;
        String[] content;
        BufferedReader br = new BufferedReader(new FileReader(playersFile));
        String st;
        while ((st = br.readLine()) != null) {
            content = st.split(NimsysConstant.PLAYERINFO_SPLIT);
            if(content[NimsysConstant.PLAYERSINFOR_TYPE_INDEX]
                    .equals(NimsysConstant.TYPE_AIPLAYER)){
                player = new NimAIPlayer();
            }
            else {
                player = new NimHumanPlayer();
            }
            player.setNimPlayer(content);
            playerList.add(player);
        }
        if(playerList.size() > 0){
            players = playerList.toArray(new NimPlayer[playerList.size()]);
        }
        return players;
    }
}
