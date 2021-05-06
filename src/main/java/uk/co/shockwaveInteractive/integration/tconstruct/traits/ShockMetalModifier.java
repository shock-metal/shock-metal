//package uk.co.shockwaveinteractive.integration.tconstruct.traits;
//
//import net.minecraft.entity.CreatureAttribute;
//import net.minecraft.entity.LivingEntity;
//import net.minecraft.potion.EffectInstance;
//import net.minecraft.potion.Effects;
//import slimeknights.tconstruct.library.modifiers.Modifier;
//import slimeknights.tconstruct.library.tools.nbt.IModifierToolStack;
//import uk.co.shockwaveinteractive.ShockMetalMain;
//
//public class ShockMetalModifier extends Modifier {
//
//	public static final ShockMetalModifier SHOCK_METAL_MODIFIER = new ShockMetalModifier();
//
//	public ShockMetalModifier()
//	{
//		super(0xFFFF4F);
//	}
//
//	@Override
//	public int afterLivingHit(IModifierToolStack tool, int level, LivingEntity attacker, LivingEntity target, float damageDealt, boolean isCritical, boolean fullyCharged) {
//
//		if(target.isLiving() && (target).getCreatureAttribute() == CreatureAttribute.UNDEAD && ShockMetalMain.rnd.nextInt(100) < 19)
//		{
//			target.setFire(5);
//			attacker.addPotionEffect(new EffectInstance(Effects.REGENERATION, 100));
//		}
//
//		return super.afterLivingHit(tool, level, attacker, target, damageDealt, isCritical, fullyCharged);
//	}
//
//}
