package wtf.norma.nekito.module.impl;

import me.zero.alpine.listener.Listener;
import me.zero.alpine.listener.Subscribe;
import me.zero.alpine.listener.Subscriber;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import org.lwjgl.input.Keyboard;
import wtf.norma.nekito.Nekito;
import wtf.norma.nekito.module.Module;
import wtf.norma.nekito.event.impl.update.EventUpdate;
import wtf.norma.nekito.settings.impl.BooleanSetting;
import wtf.norma.nekito.settings.impl.NumberSetting;
import wtf.norma.nekito.util.Time.TimerUtility;


public class Stealer extends Module implements Subscriber {


    public static NumberSetting delay = new NumberSetting("Delay", 50, 0, 250, 1);
    public static BooleanSetting checkname = new BooleanSetting("Check Name", false);
    public static boolean isromanian;
    private final TimerUtility timer = new TimerUtility();

    public Stealer() {
        super("romanian simulator", Category.OTHER, Keyboard.KEY_NONE);
        addSettings(delay, checkname);
    }

    @Override
    public void onEnable() {
        Nekito.EVENT_BUS.subscribe(this);
        isromanian = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        Nekito.EVENT_BUS.unsubscribe(this);
        super.onDisable();
    }

    public boolean czyjestpusty(Container container) {
        boolean temp = true;
        for (int i = 0, slotAmount = (container.inventorySlots.size() == 90) ? 55 : 28; i < slotAmount; ++i) {
            if (container.getSlot(i).getHasStack()) {
                temp = false;
            }
        }
        return temp;
    }

    @Subscribe
    private final Listener<EventUpdate> listener = new Listener<>(event ->{
        if (mc.thePlayer.openContainer instanceof ContainerChest) {
            final ContainerChest container = (ContainerChest) mc.thePlayer.openContainer;
            for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
                if (container.getLowerChestInventory().getStackInSlot(i) != null && this.timer.hasReached(delay.getValue())) {
                    if (checkname.isEnabled()) {
                        if (container.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Chest") || container.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Storage")) {
                            isromanian = true;
                            mc.playerController.windowClick(container.windowId, i, 0, 1, mc.thePlayer);
                        }
                    } else {
                        isromanian = true;
                        mc.playerController.windowClick(container.windowId, i, 0, 1, mc.thePlayer);
                    }
                    this.timer.reset();
                }
            }
            if (this.czyjestpusty(container)) {
                mc.thePlayer.closeScreen();
            }
        } else {
            isromanian = false;
        }
    });


//    @Override
//    public void onEvent(Event e) {
//        if (e instanceof EventUpdate) {
//            if (mc.thePlayer.openContainer instanceof ContainerChest) {
//                final ContainerChest container = (ContainerChest) mc.thePlayer.openContainer;
//                for (int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
//                    if (container.getLowerChestInventory().getStackInSlot(i) != null && this.timer.hasReached(delay.getValue())) {
//                        if (checkname.isEnabled()) {
//                            if (container.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Chest") || container.getLowerChestInventory().getDisplayName().getUnformattedText().contains("Storage")) {
//                                isromanian = true;
//                                mc.playerController.windowClick(container.windowId, i, 0, 1, mc.thePlayer);
//                            }
//                        } else {
//                            isromanian = true;
//                            mc.playerController.windowClick(container.windowId, i, 0, 1, mc.thePlayer);
//                        }
//                        this.timer.reset();
//                    }
//                }
//                if (this.czyjestpusty(container)) {
//                    mc.thePlayer.closeScreen();
//                }
//            } else {
//                isromanian = false;
//            }
//        }
//    }
}