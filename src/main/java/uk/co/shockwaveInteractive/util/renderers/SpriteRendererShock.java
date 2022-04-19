package uk.co.shockwaveinteractive.util.renderers;

import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ItemSupplier;

public class SpriteRendererShock<T extends Entity & ItemSupplier> extends ThrownItemRenderer<T> {

    public SpriteRendererShock(EntityRendererProvider.Context context) {
        super(context);
    }
}

