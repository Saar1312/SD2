package ttt;

import java.rmi.*;
import java.rmi.registry.*;

public class TTTServer {
	public static void main(String[] args) {
		int registryPort = 1099;
		try{
			TTTService g = new TTT();
			Registry reg = LocateRegistry.createRegistry(registryPort);

			reg.rebind("TTT", g);

			// Terminating when pressing a key
			System.out.println("TTTServer ready");
			System.in.read();
			System.exit(0);
		}catch(Exception e){
			System.out.println("TTT server main " + e.getMessage());
		}
	}
}
