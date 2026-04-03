package vnemesis.shockmetal.capabilities;

import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.ICapabilityProvider;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.Nullable;

public class EnergyCapabilityProvider implements ICapabilityProvider
{
    protected IEnergyStorage instance;

    public EnergyCapabilityProvider(IEnergyStorage instance) {
        this.instance = instance;
    }

    @Override
    public @Nullable Object getCapability(Object o, Object o2)
    {
        return this.instance;
    }
}
