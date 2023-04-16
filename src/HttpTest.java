import java.io.*;
import java.net.Socket;
import java.net.URLEncoder;

public class HttpTest {
    static final String host = "localhost";
    static final int port = 8081;
    static final String path = "/";

    public static void simpleGetRequest() {
        try {
            Socket socket = new Socket(host, port);

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            // 아래 2줄은 필수값
            out.println("GET " + path + " HTTP/1.1"); // method, path, http spec
            out.println("Host: " + host); // 요청 도메인
            out.println("Connection: close");
            out.println();

            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                System.out.println(responseLine);
            }

            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void simplePostRequest() {
        try {
            String data = URLEncoder.encode("key1", "UTF-8")
                    + "=" + URLEncoder.encode("value1", "UTF-8");

            Socket socket = new Socket(host, port);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out.println("POST " + path + " HTTP/1.1");
            out.println("Host: " + host);
            out.println("Content-Type: application/x-www-form-urlencoded");
            out.println("Content-Length: " + data.length());
            out.println("Connection: close");
            out.println(); // Http Format 을 위해 공백 추가
            out.print(data); // write
            out.flush(); // flush

            String responseLine;
            while ((responseLine = in.readLine()) != null) {
                System.out.println(responseLine);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        simpleGetRequest();
        simplePostRequest();
    }
}
