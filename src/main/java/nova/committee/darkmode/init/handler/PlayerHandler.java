package nova.committee.darkmode.init.handler;

import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nova.committee.darkmode.Static;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/4 9:24
 * Version: 1.0
 */
@Mod.EventBusSubscriber
public class PlayerHandler {
    private static final Random rand = new Random();

    private static final double expM = 0.6D;

    @SubscribeEvent
    public static void expGet(PlayerXpEvent.XpChange event) {
        if (Static.MODE) {
            int mul = 1;
            if (event.getAmount() > 1) {
                mul = rand.nextInt(event.getAmount());
                event.setAmount(mul);
            }
        }
    }

    @SubscribeEvent
    public static void levelGet(PlayerXpEvent.LevelChange event) {
        if (Static.MODE) {
            var randNum = rand.nextDouble();
            var bigDecimal = new BigDecimal(randNum);
            double f = bigDecimal.setScale(2, RoundingMode.HALF_UP).doubleValue();
            if (f > expM) event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public static void hurtOthers(LivingHurtEvent event) {
        if (Static.MODE) {
            event.setAmount(event.getAmount() * 0.1F);
        }
    }

}
