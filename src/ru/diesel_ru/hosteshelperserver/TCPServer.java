package ru.diesel_ru.hosteshelperserver;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;

public class TCPServer {
    try {  
        ServerSocket server = null;  
        client = null;  
        try {  
            server = new ServerSocket(3456);  
            System.out.println("Waiting...");  
            numberOfOnline = 0;  
            // Сервер ждет подключения клиентов в бесконечном цикле и каждому подключившемуся клиенту создает  
            // свой поток, что позволяет подключаться к серверу более чем 1му потоку одновременно  
            while(true) {  
                client = server.accept(); //Ожидает подключение клиента  
                numberOfOnline++; // Увеличивается счетчик активных клиентов  
                System.out.println("One more client has been connected");  
                System.out.println("There are " + TCPServer.numberOfOnline + " clients online");  
                Runnable r = new ThreadEchoHandler(client);  
                Thread t = new Thread(r);  
                t.start();  
            }  
        } //Закрываем сокеты  
        finally {  
            client.close();  
            server.close();  
        }  
    }  
    catch (IOException e) {  
        e.printStackTrace();  
    }  
	}  
	private Socket client;  
	static int numberOfOnline;  
}

/** 
 * Класс реализует интерфейс Runnable и в свое методе run() поддерживает взаимодействие с программой клиентом 
 * Программа клиент передает серверу строку, содержащую URL ресурса в сети. Сервер возвращает клиенту  
 * содержимое ресурса 
*/  
class ThreadEchoHandler implements Runnable {  
    public ThreadEchoHandler(Socket st) {  
        client = st;  
    }  
    public void run() {  
        try {  
            //Создаем входной поток сервера  
            InputStream inStream = client.getInputStream();  
            BufferedReader inputLine = new BufferedReader(new InputStreamReader(inStream));  
            String stringFromClient = inputLine.readLine(); //Строка, полученная от клиента  
            System.out.println(stringFromClient);                 
          
            //Создаем выходной поток сервера  
            OutputStream outStream = client.getOutputStream();  
            PrintWriter out = new PrintWriter(outStream, true);  
            //Получение содержимого страницы и ответ клиенту  
            try {  
                URL url = new URL(stringFromClient);  
                LineNumberReader lineReader = new LineNumberReader(new  
                    InputStreamReader(url.openStream()));  
                      
                String s = lineReader.readLine(); // читаем строку из html-содержимого страницы  
                // В цикле передаем все строки клиенту  
                while(s!=null) {  
                    out.println(s);  
                    s = lineReader.readLine();  
                }  
                  
                lineReader.close();  
            }  
            catch(MalformedURLException e) {  
                out.println("Malformed URL");  
            }  
            catch (IOException e) {  
                out.println("Probably this page does not exist");  
            }  
            client.close();  
            TCPServer.numberOfOnline--; // уменьшает число клиентов онлайн при отсоединении клиента  
            System.out.println("There are " + TCPServer.numberOfOnline + " clients online");  
        }  
        catch(IOException e) {  
            e.printStackTrace();  
        }  
    }  
    private Socket client;  
}  