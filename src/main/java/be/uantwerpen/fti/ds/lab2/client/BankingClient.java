package be.uantwerpen.fti.ds.lab2.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.DataOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

public class BankingClient {
    private String serverBaseUrl;
    private String emailClient;

    private int clientId;

    public BankingClient(String hostnameServer, int portServer, String emailClient) {
        // base-url of server. e.g., localhost:8080
        this.serverBaseUrl = "http://" + hostnameServer + ":" + portServer;
        // email of client, used for logging into the server.
        this.emailClient = emailClient;

        run();
    }

    private void run() {
        // login user to get ID
        login();

        // get accounts for User
        String bankAccountsJSONString = getBankAccounts();
        System.out.println(bankAccountsJSONString);

        // get account numbers
        List<String> accounts = JSONHelper.getAllValuesForGivenKey(bankAccountsJSONString, "accountNumber");

        // deposit some money
        depositOnAccount(accounts.get(1), 20.0);

        // check accounts after deposit
        bankAccountsJSONString = getBankAccounts();
        System.out.println(bankAccountsJSONString);

        // withdraw some money
        withdrawFromAccount(accounts.get(1), 30.0);

        bankAccountsJSONString = getBankAccounts();
        System.out.println(bankAccountsJSONString);

        // try again to withdraw some money
        withdrawFromAccount(accounts.get(1), 10.0);

        bankAccountsJSONString = getBankAccounts();
        System.out.println(bankAccountsJSONString);

    }

    private void login() {
        String loginEndpoint = serverBaseUrl + "/login";
        String loginBody = "{\"email\" : \"" + emailClient + "\"}" ;
//        System.out.println(loginBody);
        clientId = Integer.parseInt(Objects.requireNonNull(postRequest(loginEndpoint, loginBody, "login")));

    }

    private String getBankAccounts() {
        String accountsEndpoint = serverBaseUrl + "/users/" + clientId + "/bank-accounts";
//        System.out.println(accountsEndpoint);
        return getRequest(accountsEndpoint);
    }

    private void depositOnAccount(String accountNumber, double amount) {
        String depositEndpoint = serverBaseUrl + "/users/" + clientId + "/bank-accounts/" + accountNumber + "/action";
//        System.out.println(depositEndpoint);
        String depositBody = "{\"action\" : \"deposit\", \"amount\" : \"" + amount + "\"}" ;
        System.out.println(depositBody);
        postRequest(depositEndpoint, depositBody, "deposit");

    }

    private void withdrawFromAccount(String accountNumber, double amount) {
        String withdrawEndpoint = serverBaseUrl + "/users/" + clientId + "/bank-accounts/" + accountNumber + "/action";
//        System.out.println(depositEndpoint);
        String withdrawBody = "{\"action\" : \"withdraw\", \"amount\" : \"" + amount + "\"}" ;
        System.out.println(withdrawBody);
        postRequest(withdrawEndpoint, withdrawBody, "withdraw");

    }


    //https://www.baeldung.com/java-http-request
    private String getRequest(String endpoint) {
        try {
            URL url = new URL(endpoint);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // If the request successful (status code 200), we can read response.
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // Reader reads the response from the input stream.
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                // Builder is used to build the full response from the lines we read with the reader.
                StringBuilder response = new StringBuilder();
                // building the full response, including status messages, headers, ...
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                //
                return response.toString();
            } else {
                // If the request was not successful, handle the error accordingly
                System.out.println("Failed to retrieve bank account details. HTTP Error: " + connection.getResponseCode());
            }
            connection.disconnect();
        } catch (IOException e) {
            // Handling network-related errors
            e.printStackTrace();
        }
        return null;
    }

    private String postRequest(String endpoint, String requestbody, String request) {

        try {
            URL url = new URL(endpoint);
//            System.out.println(url);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);

            // Try writing the email to JSON
            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                byte[] requestBody = requestbody.getBytes(StandardCharsets.UTF_8);
                outputStream.write(requestBody, 0, requestBody.length);
            }

            // If connection is successful, we can read the response
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                // reader
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                // response
                StringBuilder response = new StringBuilder();
                String line;
                // build response = adding status code, status message, headers, ...
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                return response.toString();
            }
            else {
                // If the request was not successful, handle the error accordingly
                System.out.println("Failed to " + request + ". HTTP Error: " + connection.getResponseCode());
            }
            connection.disconnect();
            return null;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
