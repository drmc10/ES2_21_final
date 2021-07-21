import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class APIFakeGetClosestServerTest {
    private static API api;

    @BeforeAll
    public static void setUp() {
        api = APIFake.INSTANCE;
    }

    @Test
    public void testGetServerEmptyContinent() {
        Assertions.assertThrows(ServerNotFoundException.class, () -> api.getClosestServer(""));
    }

    @Test
    public void testGetServerWrongContinent() {
        Assertions.assertThrows(ServerNotFoundException.class, () -> api.getClosestServer("Antartica"));
    }

    @Test
    public void testGetServerEurope() throws ServerNotFoundException {
        Server server = api.getClosestServer("Europe");
        Assertions.assertEquals("Europe", server.getRegion());
    }

    @Test
    public void testGetServerAmerica() throws ServerNotFoundException {
        Server server = api.getClosestServer("America");
        Assertions.assertEquals("America", server.getRegion());
    }

    @Test
    public void testGetServerAsia() throws ServerNotFoundException {
        Server server = api.getClosestServer("Asia");
        Assertions.assertEquals("Asia", server.getRegion());
    }

    @Test
    public void testGetServerAfrica() throws ServerNotFoundException {
        Server server = api.getClosestServer("Africa");
        Assertions.assertEquals("Africa", server.getRegion());
    }

    @Test
    public void testGetServerAustralia() throws ServerNotFoundException {
        Server server = api.getClosestServer("Australia");
        Assertions.assertEquals("Australia", server.getRegion());
    }
}
