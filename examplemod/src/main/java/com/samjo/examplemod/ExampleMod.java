package com.samjo.examplemod;

import com.samjo.modloader.Mod;
import graphics.Color;
import main.Input;
import main.Main;
import world.Root;
import world.control.Global;
import world.control.Pause;
import world.control.Sound;

import java.awt.event.KeyEvent;
import java.io.File;

public class ExampleMod extends Mod {

    private int selectedRoomX = 0;
    private int selectedRoomY = 0;
    private MapHighlightEntity highlight;
    private static final int MAP_ROWS = 8;
    private static final int MAP_COLUMNS = 16;
    private static final double MAP_CELL_WIDTH = 15.0;
    private static final double MAP_CELL_HEIGHT = 15.0;
    private static final double MAP_CELL_SPACING = 1.0;
    private static final double MAP_START_X = 192.0;
    private static final double MAP_START_Y = 176.0;

    @Override
    public void onEnterRoom(int roomX, int roomY) {
        System.out.println("Entered room: (" + roomX + ", " + roomY + ")");
    }

    @Override
    public void onLoad() {
        System.out.println("[ExampleMod] onLoad");
    }

    @Override
    public void onSceneStart() {
        System.out.println("[ExampleMod] onSceneStart");
        highlight = new MapHighlightEntity(
                MAP_START_X,
                MAP_START_Y,
                MAP_CELL_WIDTH,
                MAP_CELL_HEIGHT,
                MAP_CELL_SPACING * 2,
                new Color(0, 255, 0)
        );
        highlight.visible = false;
    }

    @Override
    public void step() {
        boolean mapShowing = Pause.me.paused && Pause.me.page == 0;
        if (mapShowing) {
            if (Input.checkFrameKey(KeyEvent.VK_E)) {
                teleport(selectedRoomX, selectedRoomY);
            }
            else if (Input.checkFrameKey(KeyEvent.VK_W)) {
                selectedRoomY = Math.max(selectedRoomY - 1, 0);
            }

            else if (Input.checkFrameKey(KeyEvent.VK_A)) {
                selectedRoomX = Math.max(selectedRoomX - 1, 0);
            }

            else if (Input.checkFrameKey(KeyEvent.VK_S)) {
                selectedRoomY = Math.min(selectedRoomY + 1, 7);
            }

            else if (Input.checkFrameKey(KeyEvent.VK_D)) {
                selectedRoomX = Math.min(selectedRoomX + 1, 15);
            }
        }
        highlight.visible = mapShowing;
        moveMapHighlight(selectedRoomX, selectedRoomY);
    }

    private void moveMapHighlight(int x, int y) {
        highlight.x = MAP_START_X + MAP_CELL_WIDTH * x;
        highlight.y = MAP_START_Y + MAP_CELL_HEIGHT * y;
    }

    private void teleport(int roomX, int roomY) {
        int preX = Global.lastX;
        int preY = Global.lastY;
        Global.lastX = Global.roomX;
        Global.lastY = Global.roomY;
        Global.roomX = roomX;
        Global.roomY = roomY;
        Global.teleport = true;
        Global.refreshIconMap();
        File fuck = new File(Main.DIRECTORY, "res/rooms/rm" + Global.roomX + "_" + Global.roomY + ".dongs");
        if ((Global.roomX <= 15 || Global.roomY <= 0) && Global.roomY <= 15 && fuck.exists()) {
            this.refreshDream();
            Root.changeRoom(Global.roomX, Global.roomY, Global.dream);
        } else {
            Global.roomX = Global.lastX;
            Global.roomY = Global.lastY;
            Global.lastX = preX;
            Global.lastY = preY;
            Sound.errorPlay();
            this.refreshDream();
        }
    }

    private void refreshDream() {
        if (Global.roomX > 15) {
            Global.dream = true;
            Global.currentDream = Global.roomX - 16;
        } else {
            Global.dream = false;
            Global.currentDream = -1;
        }
    }
}
