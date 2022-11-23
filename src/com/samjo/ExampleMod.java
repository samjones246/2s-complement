package com.samjo;
import modding.Mod;
import world.control.Global;

public class ExampleMod implements Mod {
    @Override
    public void init() {
        System.out.println("ExampleMod init");
    }

    @Override
    public void step() {
        if (Global.framesPlayed > 0 && Global.framesPlayed % 100 == 0) {
            System.out.println("" + Global.framesPlayed);
        }
    }
}
