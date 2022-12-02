package com.samjo.modloader;

/**
 * Base class for game mods. Every mod should provide a subclass as the mod entry point,
 * and use the provided lifecycle hooks to implement desired functionality.
 */
public class Mod {
    /**
     * Called every frame.
     */
    public void step() {};

    /**
     * Called when the mod is loaded, before the game starts.
     */
    public void onLoad() {};

    /**
     * Called immediately after the Scene is created.
     * From this point until onDestroy is called, it is safe to create Entities.
     */
    public void onSceneStart() {};

    /**
     * Called when the Scene is destroyed (the game is closing).
     */
    public void onDestroy() {};

    /**
     * Called when the player transitions to a new room.
     * @param roomX The X coordinate of the room being entered.
     * @param roomY The Y coordinate of the room being entered.
     */
    public void onEnterRoom(int roomX, int roomY) {};

}