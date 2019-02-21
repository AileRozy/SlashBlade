package mods.flammpfeil.slashblade.client.renderer.entity;

import mods.flammpfeil.slashblade.ability.ProjectileBarrier;
import mods.flammpfeil.slashblade.client.model.obj.Face;
import mods.flammpfeil.slashblade.client.renderer.entity.layers.LayerSlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;

/**
 * Created by Furia on 2016/02/07.
 */
public class BladeFirstPersonRender {
    private LayerSlashBlade layer;
    private BladeFirstPersonRender(){
        Minecraft mc = Minecraft.getMinecraft();
        layer = new LayerSlashBlade((RenderLivingBase)mc.getRenderManager().getEntityRenderObject(mc.player));}
    private static final class SingletonHolder {
        private static final BladeFirstPersonRender instance = new BladeFirstPersonRender();
    }
    public static BladeFirstPersonRender getInstance(){
        return BladeFirstPersonRender.SingletonHolder.instance;
    }

    public void render(){
        Minecraft mc = Minecraft.getMinecraft();
        boolean flag = mc.getRenderViewEntity() instanceof EntityLivingBase && ((EntityLivingBase) mc.getRenderViewEntity()).isPlayerSleeping();
        if (!(mc.gameSettings.thirdPersonView == 0 && !flag && !mc.gameSettings.hideGUI && !mc.playerController.isSpectator())) {
            return;
        }
        EntityPlayerSP player = mc.player;
        ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
        if (stack.isEmpty()) return;
        if (!(stack.getItem() instanceof ItemSlashBlade)) return;
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();
        GlStateManager.loadIdentity();
        Face.resetColor();
        if(ProjectileBarrier.isAvailable(player, stack, player.getItemInUseCount())){
            GlStateManager.translate(0, 0.2f, -1.0f);
            GlStateManager.rotate(-25.0F, 1.0F, 0.0F, 0.0f);
        }else{
            GlStateManager.translate(-0.25F, 0.2f, -0.5f);
            GlStateManager.rotate(-10.0F, 1.0F, 0.0F, 0.0f);
        }
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0f);
        GlStateManager.translate(0.0f, 0.25f, 0);
        GlStateManager.rotate(-25.0F, 0.9F, 0.1F, 0.0F);
        GlStateManager.scale(1.2F, 1.0F, 1.0F);
        float partialTicks = mc.getRenderPartialTicks();
        layer.disableOffhandRendering();
        layer.doRenderLayer(mc.player, 0, 0, partialTicks, 0, 0, 0, 0);
        layer.enableOffhandRendering();
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

}