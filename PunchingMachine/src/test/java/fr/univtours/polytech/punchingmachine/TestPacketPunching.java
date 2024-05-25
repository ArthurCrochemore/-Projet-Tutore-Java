package fr.univtours.polytech.punchingmachine;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

import fr.univtours.polytech.punchingcommon.controller.TimeUtils;
import fr.univtours.polytech.punchingcommon.model.PacketPunching;

public class TestPacketPunching {
    String uuidStr;

    @Before
    public void setUp() {
        uuidStr = "da50fe4f-c938-469e-8aa1-bf28323b94e8";
    }

    @Test
    public void testPacket() {
        PacketPunching packet = new PacketPunching(UUID.fromString(uuidStr));

        assertEquals(uuidStr, packet.getUUIDEmployee().toString());
        assertEquals(LocalDate.now(), packet.getDate());
        assertEquals(TimeUtils.roundTo15Minutes(packet.getRoundedTime()),
        packet.getRoundedTime());
        assertEquals(0, packet.getRoundedTime().getMinute() % 15);
    }
}
