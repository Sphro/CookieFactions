package com.jonah.cookiefactions.level.System;

import com.jonah.cookiefactions.BukkitPlugin;
import com.jonah.cookiefactions.level.LevelsManager;
import com.jonah.cookiefactions.util.ConsoleColors;
import com.jonah.cookiefactions.util.Text;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

@Deprecated
public class Levels implements Serializable, Listener {

	private static final long serialVersionUID = -6305255722861512200L;

	public Player p;
	public int level;
	public int point;
	
	private static FileConfiguration config = LevelsManager.getInstance().getConfig();
	
	public Levels(Player p) {
		this.p = p;
		this.level = this.getLevel();
		this.point = this.getPoints();
	}
	
	public Levels() {
		this.p = null;
	}

	private File path = new File(BukkitPlugin.getInstance().getDataFolder().toString() + "/data");
	private File dirlvls = new File(path.toString(), "playerlevels.dat");
	private File dirpoints = new File(path.toString(), "playerpoints.dat");
	
	static HashMap<UUID, Integer> levels;
	
	static HashMap<UUID, Integer> points;
	
	public HashMap<UUID, Integer> getLevelHash() {
		return levels;
	}
	
	public HashMap<UUID, Integer> getPointsHash() {
		return points;
	}

	@SuppressWarnings("unchecked")
	public void initlevels() {
		if (!path.exists()) path.mkdirs();
		if (!dirlvls.exists()) {
			try {
				dirlvls.createNewFile();
				System.out.println(ConsoleColors.ANSI_GREEN + "File '" + ConsoleColors.ANSI_CYAN + dirlvls + ConsoleColors.ANSI_GREEN + "' created!" + ConsoleColors.ANSI_RESET);
			} catch (IOException e) {
				System.out.println(ConsoleColors.ANSI_RED + "Failed to create file '" + ConsoleColors.ANSI_CYAN + dirlvls + ConsoleColors.ANSI_RED + "' to data folder!" + ConsoleColors.ANSI_RESET);
				System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "STACK: " + e + ConsoleColors.ANSI_RESET);
			}
		}
		if (!dirpoints.exists()) {
			try {
				dirpoints.createNewFile();
				System.out.println(ConsoleColors.ANSI_GREEN + "File '" + ConsoleColors.ANSI_CYAN + dirpoints + ConsoleColors.ANSI_GREEN + "' created!" + ConsoleColors.ANSI_RESET);
			} catch (IOException e) {
				System.out.println(ConsoleColors.ANSI_RED + "Failed to create file '" + ConsoleColors.ANSI_CYAN + dirpoints + ConsoleColors.ANSI_RED + "' to data folder!" + ConsoleColors.ANSI_RESET);
				System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "STACK: " + e + ConsoleColors.ANSI_RESET);
			}
		}
		try {
			levels = (HashMap<UUID, Integer>) this.load(dirlvls);
			if (levels == null) {
				levels = new HashMap<UUID, Integer>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			points = (HashMap<UUID, Integer>) this.load(dirpoints);
			if (points == null) {
				points = new HashMap<UUID, Integer>();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void deInitlevels() {
		this.save(levels, dirlvls);
		this.save(points, dirpoints);
	}
	
	public void save(Object obj, File f) {
		try {
			if (!f.exists()) {
				if (!f.mkdir()) {
					System.out.println(ConsoleColors.ANSI_RED + "Failed to save file '" + ConsoleColors.ANSI_CYAN + f + ConsoleColors.ANSI_RED + "' from data folder!" + ConsoleColors.ANSI_RESET);
				}
			}
			ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(obj);
			os.flush();
			os.close();
		} catch (FileNotFoundException e) {
			if (!f.exists()) {
				if (!f.mkdir()) {
					System.out.println(ConsoleColors.ANSI_RED + "Failed to save file '" + ConsoleColors.ANSI_CYAN + f + ConsoleColors.ANSI_RED + "' from data folder!" + ConsoleColors.ANSI_RESET);
					System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "STACK: " + e + ConsoleColors.ANSI_RESET);
				}
			}
		} catch (IOException e) {
			System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "STACK: " + e + ConsoleColors.ANSI_RESET);
		}
	}
	
	public Object load(File f) {
		try {
			ObjectInputStream os = new ObjectInputStream(new FileInputStream(f));
			Object val = os.readObject();
			os.close();
			return val;
		} catch (ClassNotFoundException e) {
			System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "STACK: " + e + ConsoleColors.ANSI_RESET);
		} catch (FileNotFoundException e) {
			System.out.println(ConsoleColors.ANSI_RED + "Failed to load file '" + ConsoleColors.ANSI_CYAN + f + ConsoleColors.ANSI_RED + "' from data folder!" + ConsoleColors.ANSI_RESET);
			System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "STACK: " + e + ConsoleColors.ANSI_RESET);
		} catch (IOException e) {
			System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "STACK: " + e + ConsoleColors.ANSI_RESET);
		}
		return null;
	}
	
	// if 1/5 = 0.25
	
	public static int roundUp(double i) {
		String[] s = String.valueOf(i).split("\\.", 2);
		if (s[1].equals("00") || s[1].equals("0") || s.length == 1) {
			return Integer.valueOf(s[0]);
		} else {
			return Integer.valueOf(s[0]) + 1;
		}
	}
	
	public static int getRequiredPoints(int lvl) {
		if (lvl == 1) return 0;
		int base = config.getInt("Foreach");
		while (lvl > 1) {
			int rndup = Levels.roundUp((double) lvl / (double) 5);
			base = base + ((rndup * config.getInt("Foreach")) + config.getInt("Foreach"));
			lvl--;
		}
		return base;
	}
	
	public void addPoints(int i) {
		if (!points.containsKey(this.p.getUniqueId())) points.put(this.p.getUniqueId(), 1);
		points.put(this.p.getUniqueId(), points.get(this.p.getUniqueId()) + i);
		while (points.get(this.p.getUniqueId()) >= Levels.getRequiredPoints(levels.get(this.p.getUniqueId()))) {
			levels.put(this.p.getUniqueId(), levels.get(this.p.getUniqueId()) + 1);
		}
	}
	
	public void addPoints(int i, boolean b) {
		if (i < 0) throw new IllegalArgumentException("Input cannot be lower than 0");
		if (!points.containsKey(this.p.getUniqueId())) points.put(this.p.getUniqueId(), 1);
		points.put(this.p.getUniqueId(), points.get(this.p.getUniqueId()) + i);
		if (b) this.p.sendMessage(Text.colorize("&0(&e+&0)&r &6You have earned " + i + " Level Points"));
		while (points.get(this.p.getUniqueId()) >= Levels.getRequiredPoints(levels.get(this.p.getUniqueId()) + 1)){
			levels.put(this.p.getUniqueId(), levels.get(this.p.getUniqueId()) + 1);
			if (b) {
				this.p.playSound(this.p.getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
				this.p.sendMessage(Text.colorize("&0(&e+&0)&r &6You leveled up to Level " + levels.get(this.p.getUniqueId())+ "! You are now required of " + Levels.getRequiredPoints(levels.get(this.p.getUniqueId()) + 1) + " Level Points to reach Level " + (levels.get(this.p.getUniqueId()) + 1)));
			}
		}
	}
	
	public void reset() {
		points.put(this.p.getUniqueId(), 0);
		levels.put(this.p.getUniqueId(), 1);
	}
	
	public int getPoints() {
		if (!points.containsKey(this.p.getUniqueId())) points.put(this.p.getUniqueId(), 1);
		return points.get(this.p.getUniqueId());
	}
	
	public int getLevel() {
		if (!levels.containsKey(this.p.getUniqueId())) levels.put(this.p.getUniqueId(), 1);
		return levels.get(this.p.getUniqueId());
	}
	
	public void addLevel(int amount) {
		if ((this.p) == null) throw new NullPointerException("Player cannot be NULL");
		if (!levels.containsKey(this.p.getUniqueId())) levels.put(this.p.getUniqueId(), 1);
	}
	
	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		if (!levels.containsKey(e.getPlayer().getUniqueId())) levels.put(e.getPlayer().getUniqueId(), 1);
		if (!points.containsKey(e.getPlayer().getUniqueId())) points.put(e.getPlayer().getUniqueId(), 0);
	}
}