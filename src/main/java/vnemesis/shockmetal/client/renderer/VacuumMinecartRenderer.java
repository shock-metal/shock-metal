package vnemesis.shockmetal.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import vnemesis.shockmetal.reference.ModIdReference;

@OnlyIn(Dist.CLIENT)
public class VacuumMinecartRenderer<T extends AbstractMinecart> extends EntityRenderer<T>
{
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath(
        ModIdReference.SHOCKMETAL_MOD_ID, "textures/entity/vacuum_minecart.png");

    protected final MinecartModel<T> modelMinecart;

    public VacuumMinecartRenderer(EntityRendererProvider.Context context, ModelLayerLocation modelLayer) {
        super(context);
        this.shadowRadius = 0.7F;
        this.modelMinecart = new MinecartModel<>(context.bakeLayer(modelLayer));
    }

    @Override
    public void render(T entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
        poseStack.pushPose();

        long seed = (long) entity.getId() * 493286711L;
        seed = seed * seed * 4392167121L + seed * 98761L;
        float fx = (((float)(seed >> 16 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float fy = (((float)(seed >> 20 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        float fz = (((float)(seed >> 24 & 7L) + 0.5F) / 8.0F - 0.5F) * 0.004F;
        poseStack.translate(fx, fy, fz);

        double d0 = Mth.lerp(partialTicks, entity.xOld, entity.getX());
        double d1 = Mth.lerp(partialTicks, entity.yOld, entity.getY());
        double d2 = Mth.lerp(partialTicks, entity.zOld, entity.getZ());
        float xRot = Mth.lerp(partialTicks, entity.xRotO, entity.getXRot());

        Vec3 pos = entity.getPos(d0, d1, d2);
        if (pos != null) {
            Vec3 ahead = entity.getPosOffs(d0, d1, d2, 0.3);
            Vec3 behind = entity.getPosOffs(d0, d1, d2, -0.3);
            if (ahead == null) ahead = pos;
            if (behind == null) behind = pos;

            poseStack.translate(pos.x - d0, (ahead.y + behind.y) / 2.0 - d1, pos.z - d2);
            Vec3 dir = behind.add(-ahead.x, -ahead.y, -ahead.z);
            if (dir.length() != 0.0) {
                dir = dir.normalize();
                entityYaw = (float)(Math.atan2(dir.z, dir.x) * 180.0 / Math.PI);
                xRot = (float)(Math.atan(dir.y) * 73.0);
            }
        }

        poseStack.translate(0.0, 0.375, 0.0);
        poseStack.mulPose(Axis.YP.rotationDegrees(180.0F - entityYaw));
        poseStack.mulPose(Axis.ZP.rotationDegrees(-xRot));

        float hurtTime = entity.getHurtTime() - partialTicks;
        float damage = Math.max(0.0F, entity.getDamage() - partialTicks);
        if (hurtTime > 0.0F) {
            poseStack.mulPose(Axis.XP.rotationDegrees(
                Mth.sin(hurtTime) * hurtTime * damage / 10.0F * entity.getHurtDir()));
        }

        BlockState displayState = entity.getDisplayBlockState();
        if (displayState.getRenderShape() != RenderShape.INVISIBLE) {
            poseStack.pushPose();
            poseStack.scale(0.75F, 0.75F, 0.75F);
            poseStack.translate(-0.5, (float)(entity.getDisplayOffset() - 8) / 16.0F, 0.5);
            poseStack.mulPose(Axis.YP.rotationDegrees(90.0F));
            Minecraft.getInstance().getBlockRenderer()
                .renderSingleBlock(displayState, poseStack, buffer, packedLight, OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }

        poseStack.scale(-1.0F, -1.0F, 1.0F);
        modelMinecart.setupAnim(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer consumer = buffer.getBuffer(modelMinecart.renderType(getTextureLocation(entity)));
        modelMinecart.renderToBuffer(poseStack, consumer, packedLight, OverlayTexture.NO_OVERLAY);
        poseStack.popPose();
    }

    @Override
    public ResourceLocation getTextureLocation(T entity) {
        return TEXTURE;
    }
}

