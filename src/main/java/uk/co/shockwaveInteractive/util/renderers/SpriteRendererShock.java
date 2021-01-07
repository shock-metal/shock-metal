package uk.co.shockwaveinteractive.util.renderers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.SpriteRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IRendersAsItem;

public class SpriteRendererShock<T extends Entity & IRendersAsItem> extends SpriteRenderer<T> {

    public SpriteRendererShock(EntityRendererManager renderManagerIn) {

        super(renderManagerIn, Minecraft.getInstance().getItemRenderer());
    }
}

