import java.util.ArrayList;

public enum ServerDataBase {
    INSTANCE();

    private final ArrayList<Server> serverList;

    ServerDataBase() {
        serverList = new ArrayList<>();
        serverList.add(new Server("Europe"));
        serverList.add(new Server("America"));
        serverList.add(new Server("Asia"));
    }

    public Server getServer(String region) throws ServerNotFoundException {
        for (Server server : serverList) {
            if(server.getRegion().equals(region))
                return server;
        }

        throw new ServerNotFoundException();
    }
}
