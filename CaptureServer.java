package screencapture;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.imageio.ImageIO;

public class CaptureServer {
	public static void main(String[] args) throws IOException, AWTException, ClassNotFoundException {

		int portNumber = 4567;
		String action;
		
		if (args.length > 0) {
			portNumber = Integer.parseInt(args[0]);
		}

		ServerSocket serverSocket = new ServerSocket(portNumber);
		System.out.println("Started Server on port " + portNumber + "\nWaiting for connection...");

		while (true) {
			Socket clientSocket = serverSocket.accept();
			System.out.println("Accepted a connection");

			ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());

			System.out.println("Wait for messages");

			action = (String) in.readObject();
			System.out.println("Got a message with action " + action);

			if (action.equalsIgnoreCase("exit")) break;

			if (action.equalsIgnoreCase("takeScreenshot")) {
				try {
					BufferedImage screenshot = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
					ImageIO.write(screenshot, "png", out);
				} finally {
					System.out.println("Closing client socket");
					out.close();
					in.close();
					clientSocket.close();
				}
			}
		}
		System.out.println("Closing server socket");
		serverSocket.close();
	}
}
