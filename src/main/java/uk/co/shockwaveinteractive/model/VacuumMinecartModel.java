package uk.co.shockwaveinteractive.model;


//public class VacuumMinecartModel<T extends Entity> extends EntityModel<T> {
//
//    private final ModelPart dome;
//    private final ModelPart minecart;
//
//    public VacuumMinecartModel() {
//
//        texWidth = 128;
//        texHeight = 128;
//
//        dome = new ModelBlockRenderer(this);
//        dome.addBox(-8, -20, -10, 16, 15, 20);
//
//        minecart = new ModelBlockRenderer();
//        minecart.texOffs(0, 42).addBox(-8, -5, -10, 16, 10, 20);
//        minecart.texOffs(56, 56).addBox(-6, -5, -8, 12, 8, 16);
//
//        dome.yRot = ((float) Math.PI / 2F);
//        minecart.yRot = ((float) Math.PI / 2F);
//    }
//
//    @Override
//    public void setupAnim(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
//        //previously the render function, render code was moved to a method below
//    }
//
//    @Override
//    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexCon, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
//        dome.render(poseStack, vertexCon, packedLight, packedOverlay);
//        minecart.render(poseStack, vertexCon, packedLight, packedOverlay);
//    }
//}