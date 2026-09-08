package potatowolfie.dems_quivers.network;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import potatowolfie.dems_quivers.DemSQuivers;

public record ServerboundSelectQuiverItemPacket(int slotId, int selectedItemIndex) implements CustomPacketPayload {
    public static final Type<ServerboundSelectQuiverItemPacket> TYPE =
            new Type<>(DemSQuivers.id("select_quiver_item"));

    public static final StreamCodec<RegistryFriendlyByteBuf, ServerboundSelectQuiverItemPacket> STREAM_CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.VAR_INT, ServerboundSelectQuiverItemPacket::slotId,
                    ByteBufCodecs.VAR_INT, ServerboundSelectQuiverItemPacket::selectedItemIndex,
                    ServerboundSelectQuiverItemPacket::new
            );

    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}