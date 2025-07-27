package MCMiniGames;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginMain extends JavaPlugin{

	// The singleton instance 
	private static PluginMain instance;
	private static GameTypeRegistry gameTypeRegistry;
	
	public PluginMain() {		
		if(instance == null) {
			instance = this;
		}
	}
	
    @Override
    public void onEnable() {
		
    	this.getConfig().options().copyDefaults(true);
    	setGameTypeRegistryistry(new GameTypeRegistry());
    }
    
    @Override
    public void onDisable() {
    	//TODO: Save settings for individual game types
    }

    // Getters and setters for the static instance
	public static void setGameTypeRegistryistry(GameTypeRegistry gameTypeRegistry) {
		PluginMain.gameTypeRegistry = gameTypeRegistry;
	}
	public static GameTypeRegistry getgameTypeRegistryistry() {
		return gameTypeRegistry;
	}
	
	// Getter for the main plugin
    public static PluginMain getInstance() {
    	return instance;
    }
}
