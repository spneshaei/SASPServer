package server;

import model.DataManager;
import org.restlet.*;
import org.restlet.data.Protocol;

import java.util.Scanner;

public class ServerMain extends Application {
    public static void main(String...args) throws Exception {
        System.out.print("Enter 1 to initialize DB and 2 to start the server: ");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        if (input == 1) {
            DBHandler.initializeProductTable();
        } else {
            Server server = new Server(Protocol.HTTP, 8111,
                    MainResource.class);
            server.start();
        }


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
