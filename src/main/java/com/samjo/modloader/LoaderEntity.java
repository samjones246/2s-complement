package com.samjo.modloader;

import main.Entity;
import world.control.Global;

public class LoaderEntity extends Entity {
    private int prevRoomX = -1;
    private int prevRoomY = -1;

    public LoaderEntity(){
        super(0.0, 0.0);
        persistent = true;
        visible = false;
        LoaderAgent
            .loadedMods
            .forEach(Mod::onSceneStart);
    }

    @Override
    public void step() {
        super.step();
        if (Global.roomX != prevRoomX || Global.roomY != prevRoomY) {
            prevRoomX = Global.roomX;
            prevRoomY = Global.roomY;
            LoaderAgent.loadedMods
                .forEach(m ->
                    m.onEnterRoom(Global.roomX, Global.roomY)
                );
        }
        LoaderAgent.loadedMods
                .forEach(Mod::step);
    }
}
