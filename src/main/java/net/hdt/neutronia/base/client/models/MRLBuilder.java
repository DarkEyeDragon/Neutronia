package net.hdt.neutronia.base.client.models;

import net.hdt.neutronia.base.util.ModelUtil;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

import static net.hdt.neutronia.base.lib.LibMisc.MOD_ID;

public class MRLBuilder {
    ResourceLocation location;
    List<String> prepends = new ArrayList<>();
    List<String> appends = new ArrayList<>();
    String variant = "";

    public MRLBuilder(String location) {
        this.location = new ResourceLocation(MOD_ID, location);
    }

    public MRLBuilder(ResourceLocation location) {
        this.location = location;
    }

    public MRLBuilder prepend(String prepend) {
        this.prepends.add(prepend);
        return this;
    }

    public MRLBuilder append(String append) {
        this.appends.add(append);
        return this;
    }

    public ModelResourceLocation build() {
        return new ModelResourceLocation(getLocation(), ModelUtil.addPrependsAndAppends(variant, prepends, appends));
    }

    public ResourceLocation getLocation() {
        return location;
    }

    public List<String> getPrepends() {
        return prepends;
    }

    public MRLBuilder setPrepends(List<String> prepends) {
        this.prepends = prepends;
        return this;
    }

    public List<String> getAppends() {
        return appends;
    }

    public MRLBuilder setAppends(List<String> appends) {
        this.appends = appends;
        return this;
    }

    public String getVariant() {
        return variant;
    }

    public MRLBuilder setVariant(String variant) {
        this.variant = variant;
        return this;
    }
}
