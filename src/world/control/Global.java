package world.control;
import java.util.HashMap;

public class Global {
    // public static final Font FONT = new Font("font");
    // public static final Font CRAZDFONT = new Font("crazdFont");
    // public static final Sprite sHEART = Sprite.get("sHeart");
    // public static final Sprite sBLOCK = Sprite.get("sBlock");
    // public static final Sprite sBALLOON = Sprite.get("sBalloon");
    // public static final Sprite sBOMBROCK = Sprite.get("sBombRock");
    // public static final Sprite sDOOR = Sprite.get("sDoor");
    // public static final Sprite sDOORHOLE = Sprite.get("sDoorHole");
    // public static final Sprite sUPBLOCK = Sprite.get("sUpblock");
    // public static final Sprite sEXPLODE = Sprite.get("sExplode");
    public static final int VERSION = 1;
    public static final double REAL_VERSION = 1.12;
    public static int identifier;
    public static int roomX;
    public static int roomY;
    public static int lastX;
    public static int lastY;
    public static int dreams;
    public static int currentDream;
    public static boolean dream;
    public static boolean[] dreamCleared;
    public static double returnX;
    public static double returnY;
    public static int returnRX;
    public static int returnRY;
    public static final boolean canDebug = true;
    public static boolean teleport;
    public static boolean debug;
    public static double playerX;
    public static double playerY;
    public static boolean heroMode;
    public static double jumpSpeed;
    public static double runSpeed;
    public static double luck;
    public static double tempMultLuck;
    public static int maxWeapons;
    public static boolean gotDuck;
    public static boolean gotWallSlide;
    public static boolean gotGun;
    public static boolean gotBombs;
    public static boolean gotMapDoor;
    public static boolean gotMapIcons;
    public static boolean gotGlasses;
    public static boolean gotBoots;
    public static boolean gotStench;
    public static boolean gotClover;
    public static boolean glassesEnabled;
    public static int playerHealth;
    public static int playerMaxHealth;
    public static int playerBombs;
    public static int playerMaxBombs;
    public static int playerBullets;
    public static int playerMaxBullets;
    public static int playerBulletRefillTimer;
    public static int money;
    public static int selectedWeapon;
    public static int totalSaves;
    public static int totalDeaths;
    public static int gemMilestones;
    public static long framesPlayed;
    public static long framesPaused;
    public static String[] tweetText;
    public static String[] tweetTimestamp;
    public static boolean gotDoors;
    public static int playerDoors;
    public static String playerDoorColor;
    public static boolean paused;
    public static boolean alwaysShowHud;
    public static int LEFT;
    public static int RIGHT;
    public static int UP;
    public static int DOWN;
    public static int JUMP;
    public static int PAUSE;
    public static int ACTION;
    public static int INTERACT;
    public static String controls;
    public static boolean mute;
    public static String bloodColor;
    public static String bloodOutlineColor;
    public static int which;
    public static boolean awaitKey;
    // public static SoundLoader soundLoadClaimed;
    public static boolean lastDream;
    public static String menuBackgroundColor;
    public static String menuLineColor;
    public static int menuOverlay;
    public static int menuSong;
    public static int storedSaveFile;
    public static boolean storedHeroine;
    public static boolean playerDead;
    public static double storedA;
    public static final int CONTROLLER = -1;
    public static final int PLAYER = 5;
    public static final int RAINBOW = 6;
    public static final int DEACTIVATEME = 7;
    public static final int BED = 8;
    public static final int BOSSCOLLISION = 9;
    public static final int SOLIDBLOCK = 10;
    public static final int UPBLOCK = 11;
    public static final int SECRET = 12;
    public static final int HEART = 13;
    public static final int ITEM = 14;
    public static final int INTERACTABLE = 15;
    public static final int TEMPSOLID = 16;
    public static final int BLOCK = 17;
    public static final int BOSSBLOCK = 18;
    public static final int BOMBROCK = 19;
    public static final int JELLYBLOCK = 20;
    public static final int BULLET = 21;
    public static final int STONEBELLON = 22;
    public static final int NOOCCUPY = 23;
    public static final int BASICNPC = 24;
    public static final int TRUCKWINDOW = 25;
    public static final int MONSTER = 26;
    public static final int DEACTIVATEENEMY = 27;
    public static final int WATER = 28;
    public static final int WATERSOLID = 29;
    public static final int KILLATLEAVE = 30;
    public static final int DINGUS = 31;
    public static final int STUPIDORB = 32;
    public static final int KEY = 50;
    public static final int LOCKED = 51;
    public static final int UNLOCKED = 52;
    public static final int TURNER = 66;
    public static final int DOOR = 69;
    public static final int JAR = 100;
    public static final int BASICENEMY = 101;
    public static String[] hexMap;
    public static int[] symMap;
    // public static Sprite action;
    public static double actionImg;
    public static double actionX;
    public static double actionY;
    public static boolean actionVis;
    // public static Sprite down;
    public static double downX;
    public static double downY;
    public static boolean downVis;
    public static boolean mainActive;
    public static String currentArea;
    public static String stepSound;
    public static String roomMusic;
    public static String roomColor;
    public static int keyAmount;
    public static String heldKey;
    public static boolean[] pickedUp;
    public static boolean[] unlocked;
    public static int[] dropRmX;
    public static int[] dropRmY;
    public static double[] dropX;
    public static double[] dropY;
    public static String[] dropColor;
    public static int gemAmount;
    public static boolean[] gemGot;
    public static final int eventAmount = 100;
    public static int[] event;
    public static final int eventItemAmount = 16;
    public static int[] eventItem;
    public static final String[] eventItemNames;
    public static boolean[] butterfly;
    public static int[] doorRmX;
    public static int[] doorRmY;
    public static int[] doorToRmX;
    public static int[] doorToRmY;
    public static double[] doorX;
    public static double[] doorY;
    public static double[] doorToX;
    public static double[] doorToY;
    public static String[] doorColor;
    public static int doorSetsPlaced;
    public static int doorsPlaced;
    public static int doorSetsMax;
    public static int doorSetsPurchased;
    public static int heartAmount;
    public static boolean[] heartGot;
    public static int[] mapDoorX;
    public static int[] mapDoorY;
    public static int[] mapDoorToX;
    public static int[] mapDoorToY;
    public static int mapDoorAmount;
    public static HashMap<String, String> areaNames;
    public static final String BLUELINE = "#20a1ff";
    public static boolean isHalloween;
    public static String cutsceneTweet;
    public static int screenMode;
    public static final int totalBosses = 7;
    public static final int totalChests = 11;
    public static int saveFileNumber;
    public static double storedPitch;
    public static boolean iLoveYou;
    // public static Shake rain;

    static {
        dreams = 40;
        teleport = false;
        debug = false;
        maxWeapons = 3;
        controls = "res/data/controls.dongs";
        bloodColor = "FF1443";
        bloodOutlineColor = "4784FF";
        menuBackgroundColor = "#FFFFFF";
        menuLineColor = "#000000";
        storedA = 0.0;
        currentArea = "";
        roomColor = "#FFFFFF";
        keyAmount = 50;
        gemAmount = 300;
        eventItemNames = new String[]{"AXE", "DASSKEY", "BUTTERFLIES", "DINOFOOTPRINT", "DINOFEMUR", "DINOCLAW #DINOTOOTH #DINOHORN???", "EROR", "ERROR", "ERO", "CUM", "", "", "", "", "", ""};
        doorSetsMax = 25;
        heartAmount = 72;
        mapDoorAmount = 256;
        isHalloween = false;
        saveFileNumber = 1;
    }
}