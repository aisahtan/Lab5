package clientSide;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import model.ItemProduct;

public class ClientSideApplication {
	
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws InterruptedException {

		ItemProduct ip = new ItemProduct();
		ip.setName("MARY KAY TIMEWISE 3D CLEANSER");
		ip.setPrice(115);
		
		ItemProduct ip2 = new ItemProduct();
		ip2.setName("MARY KAY OIL FREE HYDRATING GEL");
		ip2.setPrice(130);
		
		ItemProduct ip3 = new ItemProduct();
		ip3.setName("MARY KAY SEMI MATTE LIPSTICK ");
		ip3.setPrice(52);
		
		List<ItemProduct> itemProduct = new ArrayList<ItemProduct>();
		itemProduct.add(ip);
		itemProduct.add(ip2);
		itemProduct.add(ip3);
		
		try {
			
			// Data to establish connection to server
			int portNo = 4228;
			InetAddress serverAddress = InetAddress.getLocalHost();
			
			// Connect to the server at local host, port 4228
			Socket socket = new Socket(serverAddress, portNo);
			
			// Open stream to send object
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
			
			//Send request to server
			System.out.println("Send object to server: " + itemProduct);
			objectOutputStream.writeUnshared(itemProduct);
			objectOutputStream.flush();
			
			//Open stream to receive object
			ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
			
			// Get object from stream, cast and display details
			itemProduct = (ArrayList<ItemProduct>) objectInputStream.readObject();
			
			for(ItemProduct itemproduct: itemProduct) {
				System.out.println ("Id product =  " + itemproduct.getItemProductId() + "\n"
						+ "Name product =  " + itemproduct.getName() + "\n"
						+ "Price product =  " + itemproduct.getPrice() + "\n");
			}		
			
			Thread.sleep(1000);
			
			// Close all closable objects
			objectOutputStream.close();			
			objectInputStream.close();
			socket.close();			
			
		} catch (IOException | ClassNotFoundException e) {			
			e.printStackTrace();				
		} 
		
		System.out.println("\nClientSideApplication: End of application.\n");			
	}

}
