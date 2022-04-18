package uk.co.shockwaveinteractive.util.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;

public class SpriteRendererShock<T extends Entity & ItemSupplier> extends ThrownItemRenderer<T> {

    public SpriteRendererShock(EntityRenderDispatcher renderManagerIn) {

        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }
}

