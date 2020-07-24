package server;

import model.DataManager;
import org.restlet.*;
import org.restlet.data.Protocol;

import java.util.Scanner;

public class ServerMain extends Application {
    public static void main(String... args) throws Exception {
        System.out.print("Enter 1 to initialize DB and then start the server, or 2 to start the server: ");
        Scanner scanner = new Scanner(System.in);
        int input = scanner.nextInt();
        DataManager.loadData();
        if (input == 1) DBHandler.initializeTables();
        else DataManager.loadFromDB();
        if (!DataManager.shared().isMadeAdminBankAccount()) {
            BankAPI.tellBankAndReceiveResponse("create_account Admin Admin admin kBfo#ou@yeq2 kBfo#ou@yeq2", response -> {
                DataManager.shared().setAdminBankAccountNumber(response);
                DataManager.saveData();
            });
        }
        Server server = new Server(Protocol.HTTP, 1234,
                MainResource.class);
        server.start();
        while (true) {
            int input2 = scanner.nextInt();
            if (input2 == 0) {
                DataManager.saveData();
                DataManager.saveToDB();
                System.exit(0);
            }
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
