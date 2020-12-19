//package uk.co.shockwaveinteractive.util.handlers;
//
//import net.minecraft.util.IStringSerializable;
//
//public class EnumHandler
//{
//	public static enum EnumType implements IStringSerializable
//	{
//		SHOCKMETAL(0, "assets/shockmetal");
//
//		public static final EnumType[] META_LOOKUP = new EnumType[values().length];
//		private final int meta;
//		private final String name;
//		private final String unlocalisedName;
//
//
//		private EnumType(int meta, String name)
//		{
//			this(meta, name, name);
//		}
//
//		private EnumType(int meta, String name, String unlocalisedName)
//		{
//			this.meta = meta;
//			this.name = name;
//			this.unlocalisedName = unlocalisedName;
//		}
//
//
//		@Override
//		public String getName() {
//
//			return this.name;
//		}
//
//		public int getMeta() {
//			return this.meta;
//		}
//
//		public String getUnlocalisedName() {
//			return this.unlocalisedName;
//		}
//
//		@Override
//		public String toString() {
//			return this.name;
//		}
//
//		public static EnumType byMetadtata(int meta)
//		{
//			return META_LOOKUP[meta];
//		}
//
//		static
//		{
//			for(EnumType enumtype : values())
//			{
//				META_LOOKUP[enumtype.getMeta()] = enumtype;
//			}
//		}
//	}
//
//}
