package net.kiki.adammod.item;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class EnderiteSwordItem extends SwordItem {
    public EnderiteSwordItem(ToolMaterial toolMaterial, Settings settings) {
        super(toolMaterial, settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        if (!world.isClient) {
            double maxDistance = 8.0;
            Vec3d eyePos = player.getCameraPosVec(1.0F);
            Vec3d lookVec = player.getRotationVec(1.0F).multiply(maxDistance);
            Vec3d targetVec = eyePos.add(lookVec);

            var hitResult = world.raycast(new RaycastContext(
                    eyePos, targetVec,
                    RaycastContext.ShapeType.COLLIDER,
                    RaycastContext.FluidHandling.NONE,
                    player
            ));

            Vec3d teleportPos = hitResult.getPos();
            if (hitResult.getType() == HitResult.Type.BLOCK) {
                teleportPos = new Vec3d(
                        teleportPos.x,
                        Math.floor(teleportPos.y),
                        teleportPos.z
                );
            }
            player.requestTeleport(teleportPos.x, teleportPos.y, teleportPos.z);
            player.getItemCooldownManager().set(this, 40);
            stack.damage(10, player, EquipmentSlot.MAINHAND);
        }
        return TypedActionResult.success(stack, world.isClient());
    }
}
