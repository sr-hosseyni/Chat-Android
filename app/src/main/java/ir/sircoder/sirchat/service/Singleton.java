package ir.sircoder.sirchat.service;

public class Singleton {
    private static final Singleton ourInstance = new Singleton();
    private ServerInformation serverInformation;

    public static Singleton getInstance() {
        return ourInstance;
    }

    private Singleton() {
    }

    public void setServerInformation(ServerInformation serverInformation) {
        this.serverInformation = serverInformation;
    }

    public ServerInformation getServerInformation() {
        return serverInformation;
    }
    
    public static class ServerInformation {
        String username;
        String password;
        SirChatService.Server server;

        public ServerInformation(SirChatService.Server server, String username, String password) {
            this.server = server;
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

        public SirChatService.Server getServer() {
            return server;
        }

        public String getHttpApiBaseURI() {
            return String.format("http://%s:%d/", server.address, server.httpPort);
        }
    }
}
