package server;

import org.restlet.*;
import org.restlet.data.Protocol;

public class ServerMain extends Application {
    public static void main(String...args) throws Exception {
        Server server = new Server(Protocol.HTTP, 8111,
                MainResource.class);
        server.start();

//        final Component component = new Component();
//        component.getServers().add(Protocol.HTTP, 12345);
//        ServerMain server = new ServerMain(component.getContext().createChildContext());
//        component.getDefaultHost().attach(server);
//        component.start();
    }

    public ServerMain(Context context) {
        super(context);
    }
}
