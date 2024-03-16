package be.uantwerpen.fti.ds.lab2.client;

public class RunnableClient {
    public static void main(String[] args) {
        if (args.length < 3) {
            System.out.println("Syntax: provide parameter \"hostname server\" \"port server\" \"an email to login to the service\"");
            return;
        }

        String hostname = args[0];
        int port = Integer.parseInt(args[1]);
        String email = args[2];

        new BankingClient(hostname, port, email);



    }

}
