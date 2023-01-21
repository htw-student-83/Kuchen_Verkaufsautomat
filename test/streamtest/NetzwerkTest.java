package streamtest;

import geschaeftslogik.Hersteller;
import geschaeftslogik.Kuchentyp;
import geschaeftslogik.verkaufsobjekt.Verwaltung;
import netzwerk.tcp.Kuchenautomat_Server_TCP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import vertrag.Allergene;

import java.io.*;
import java.net.Socket;
import java.time.Duration;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NetzwerkTest {
    @Test
    @DisplayName("Stellvertretertest für's Einfügen eines Kuchens")
    public void insertKuchen() throws IOException {
        Socket serversocket = mock(Socket.class);
        Kuchenautomat_Server_TCP server = new Kuchenautomat_Server_TCP(serversocket,3);
        ByteArrayOutputStream bos = new ByteArrayOutputStream(14);
        DataOutputStream dos = new DataOutputStream(bos);
        dos.writeUTF("Test1");
        dos.writeInt(1);
        dos.writeUTF("Daniel");
        server.run();
        when(serversocket.getInputStream()).thenReturn(new ByteArrayInputStream(bos.toByteArray()));
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bos.toByteArray()));
        Assertions.assertEquals("Test1", dis.readUTF());
        Assertions.assertEquals(1, dis.readInt());
        Assertions.assertEquals("Daniel", dis.readUTF());
    }
}
