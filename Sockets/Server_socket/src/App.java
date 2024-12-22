import transfert.Server_socket;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class App {
    private static Server_socket server;

    public static void main(String[] args) {
        String slaveConfigPath = "D:\\lolah\\Sockets\\Slave_socket\\src\\slaves.txt";
        String localIp = "localhost"; // Valeur par défaut
        int port = 12345; // Port par défaut

        // Obtenir l'adresse IP locale
        try {
            localIp = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            System.err.println("Impossible de récupérer l'adresse IP locale. Utilisation de l'adresse par défaut : " + localIp);
        }

        // Démarrer le serveur
        try {
            System.out.println("Démarrage du serveur sur l'adresse IP : " + localIp + " et le port : " + port);
            server = new Server_socket(port, slaveConfigPath);
            server.startServer(); // Lancement direct du serveur
            System.out.println("Serveur démarré avec succès !");
        } catch (Exception e) {
            System.err.println("Erreur lors du démarrage du serveur : " + e.getMessage());
        }

        // Ajout d'un hook pour fermer proprement le serveur à la fin de l'exécution
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (server != null) {
                System.out.println("Fermeture du serveur...");
                server.closeServer();
                System.out.println("Serveur arrêté.");
            }
        }));
    }
}
