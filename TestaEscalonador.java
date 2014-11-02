import java.io.*;
import java.util.Scanner;

public class TestaEscalonador{

	static final int MAX_QUANTUM = 21; /* numero maximo de instrucoes */
	
	public static void main(String[] args) throws IOException{
		for(int i = 1; i <= MAX_QUANTUM; i++){
			String diretorioAtual = System.getProperty("user.dir");
			File f = new File(diretorioAtual + "/processos/quantum.txt");
			if(!f.exists())
				f.createNewFile();
			PrintWriter pw = new PrintWriter(
				new BufferedWriter(
					new FileWriter(f.getPath(), false)));
			pw.println(i);
			pw.close();
			Escalonador.main(new String[1]);
		}
	}

}