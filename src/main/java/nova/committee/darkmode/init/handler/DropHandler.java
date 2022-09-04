package nova.committee.darkmode.init.handler;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import nova.committee.darkmode.Static;

import java.util.*;

/**
 * Description:
 * Author: cnlimiter
 * Date: 2022/9/3 21:57
 * Version: 1.0
 */
@Mod.EventBusSubscriber
public class DropHandler {
    private static final Random rand = new Random();

//    @SubscribeEvent
//    public static void blockExpDrop(BlockEvent.BreakEvent event){
//        if (event.getPlayer() == null) return;
//        if (event.getPlayer() instanceof FakePlayer) return;
//        int mul = 1;
//        if (event.getExpToDrop() > 1){
//            mul = rand.nextInt(event.getExpToDrop());
//            event.setExpToDrop(mul);
//        }
//
//    }

    @SubscribeEvent
    public void mobItemDrop(LivingDropsEvent event) {
        if (Static.MODE) {

            var entity = event.getEntity();
            var world = entity.getCommandSenderWorld();
            int delNum = 0;
            if (world.isClientSide) {
                return;
            }

            if (entity instanceof Player) {
                return;
            }

            if (event.getDrops().size() > 1) {
                delNum = rand.nextInt(event.getDrops().size());
                var dropsA = event.getDrops();
                var dropsB = createRandoms(event.getDrops().stream().toList(), delNum);

                //var AnB = dropsA.stream().filter(dropsB::contains).toList();

                for (ItemEntity drop : dropsA) {
                    for (ItemEntity sub : dropsB) {
                        if (sub == drop) {
                            var sunItem = sub.getItem();
                            drop.setItem(sunItem);
                        } else {
                            drop.setItem(ItemStack.EMPTY);
                        }
                    }
                }
            }
        }


    }

    private List<ItemEntity> createRandoms(List<ItemEntity> list, int n) {
        Map<Integer, String> map = new HashMap<>();
        List<ItemEntity> news = new ArrayList<>();
        if (list.size() <= n) {
            return list;
        } else {
            while (map.size() < n) {
                int random = (int) (Math.random() * list.size());
                if (!map.containsKey(random)) {
                    map.put(random, "");
                    news.add(list.get(random));
                }
            }
            return news;
        }
    }
}
