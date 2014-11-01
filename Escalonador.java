import java.io.*;
import java.util.Scanner;

public class Escalonador{

	static Processo tabela[] = new Processo[3]; /* tabela de procesos */ //@@@@@@@@@@@@MODIFICADO O NUMERO PROCESSOS NA TABELA
	static int quantum;
	static Processo executando = null;
	static Fila pronto = new Fila();
	static Fila bloqueado = new Fila();
	
	public static void main(String[] args){
		try{
			
<<<<<<< HEAD
=======
			String diretorioAtual = System.getProperty("user.dir");
>>>>>>> 9f9c2da7010ed1958893c7935de41f5fe28e51aa
			int mediaTrocas = 0;
			int mediaInstrucoes = 0;
			
			leQuantum();
<<<<<<< HEAD
			
			File log = new File( "/log"+quantum+".txt" );/* instancia novo documento de texto para o log */
=======
					
			File log = new File( diretorioAtual + "/log"+quantum+".txt" );/* instancia novo documento de texto para o log */
>>>>>>> 9f9c2da7010ed1958893c7935de41f5fe28e51aa
			if(!log.exists()) log.createNewFile(); /* caso este nao exista, um novo e criado */
			
			criaProcessos(log);
			
<<<<<<< HEAD
			FileWriter olog = new FileWriter(log);
			BufferedWriter wlog = new BufferedWriter(olog);
			
			while(!estaVazia()){ /* enquanto houver processos na tabela de processos */
=======
			while(!tabelaDeProcessosEstaVazia()) /* enquanto houver processos na tabela de processos */
			{ 
>>>>>>> 9f9c2da7010ed1958893c7935de41f5fe28e51aa
				decrementaBloqueados(); /* decrementa o contador da fila dos bloqueados */
				executando = pronto.pop(); /* obtem o proximo a executar */	
				if(executando == null)
				{
					continue;
				}
				executando.status = "Executando";		

				for(int c = 0; c < quantum; c++)  /* operacoes que o processo tem direito */
				{
					FileWriter olog = new FileWriter(log.getPath(), true);
					BufferedWriter wlog = new BufferedWriter(olog);
					PrintWriter pw = new PrintWriter(wlog);
					
					String instrucao = executando.DS[executando.PC];
					executando.PC++;
					switch(instrucao){
						case "COM": 
							if(quantum - c == 1){ /* depois da ultima iteracao, inserir na fila de prontos */
								executando.status = "Pronto";
								pronto.push(executando);
<<<<<<< HEAD
								
								wlog.write("Interrompendo "+executando.nome+" apos "+quantum+" operacoes");
								wlog.newLine();
								break;
							}
							wlog.write("Executando "+ executando.nome);
							wlog.newLine();
=======
								pw.println("Interrompendo "+executando.nome+" apos "+quantum+" operacoes");
								pw.close();
//								wlog.write("Interrompendo "+executando.nome+" apos "+quantum+" operacoes");
//								wlog.newLine();
								break;
							}
							pw.println("Executando "+ executando.nome);
							pw.close();
//							wlog.write("Executando "+ executando.nome);
//							wlog.newLine();
>>>>>>> 9f9c2da7010ed1958893c7935de41f5fe28e51aa
							break;
						case "E/S":
							executando.status = "Bloqueado";
							bloqueado.push(executando);
							int linhasPercorridas = c;
							c = quantum;
<<<<<<< HEAD
							if(c == 0){
								wlog.write("Interrompendo "+executando.nome+" apos 0 instrucao (havia apenas a E/S)");
								wlog.newLine();
							}
							if(c == 1){
								wlog.write("Interrompendo "+executando.nome+" apos 1 instrucao (havia um comando antes da E/S)");
								wlog.newLine();
							}
							if(c >= 2){
								wlog.write("Interrompendo "+executando.nome+" apos "+c+" instrucoes (haviam "+c+" comandos antes da E/S)");
								wlog.newLine();
=======
							if(linhasPercorridas == 0){
								pw.println("Interrompendo "+executando.nome+" apos 0 instrucao (havia apenas a E/S)");
								pw.close();
//								wlog.write("Interrompendo "+executando.nome+" apos 0 instrucao (havia apenas a E/S)");
//								wlog.newLine();
							}
							if(linhasPercorridas == 1){
								pw.println("Interrompendo "+executando.nome+" apos 1 instrucao (havia um comando antes da E/S)");
								pw.close();
//								wlog.write("Interrompendo "+executando.nome+" apos 1 instrucao (havia um comando antes da E/S)");
//								wlog.newLine();
							}
							if(linhasPercorridas >= 2){
								pw.println("Interrompendo "+executando.nome+" apos "+linhasPercorridas+" instrucoes (haviam "+c+" comandos antes da E/S)");
								pw.close();
//								wlog.newLine();
>>>>>>> 9f9c2da7010ed1958893c7935de41f5fe28e51aa
							}
							break;
						case "SAIDA":
							finalizaProcesso(executando); /* remove processo da fila de bloqueados */
							c = quantum;
<<<<<<< HEAD
							wlog.write(executando.nome+" terminado. X="+executando.X+" Y="+executando.Y);
							wlog.newLine();
=======
							pw.println(executando.nome+" terminado. X="+executando.X+" Y="+executando.Y);
							pw.close();
//							wlog.newLine();
>>>>>>> 9f9c2da7010ed1958893c7935de41f5fe28e51aa
							break;
						default:
							switch(instrucao.substring(0,1)){
								case "X":
									executando.X = Integer.parseInt(instrucao.substring(2));
									if(quantum - c == 1){ /* depois da ultima iteracao, inserir na fila de prontos */
										executando.status = "Pronto";
										pronto.push(executando);	
									}
<<<<<<< HEAD
									wlog.write("Executando " + executando.nome);
									wlog.newLine();
=======
									pw.println("Executando " + executando.nome);
									pw.close();
>>>>>>> 9f9c2da7010ed1958893c7935de41f5fe28e51aa
									break;
								case "Y":
									executando.Y = Integer.parseInt(instrucao.substring(2));
									if(quantum - c == 1){ /* depois da ultima iteracao, inserir na fila de prontos */
										executando.status = "Pronto";
										pronto.push(executando);	
									}
<<<<<<< HEAD
									wlog.write("Executando " + executando.nome);
									wlog.newLine();
=======
									pw.println("Executando " + executando.nome);
									pw.close();
//									wlog.newLine();
>>>>>>> 9f9c2da7010ed1958893c7935de41f5fe28e51aa
									break;
							}
						mediaInstrucoes++;
					}
					mediaTrocas++;
				}
			}
<<<<<<< HEAD
			wlog.write("MEDIA DE TROCAS: "+(mediaTrocas/10));
			wlog.newLine();
			wlog.write("MEDIA DE INSTRUCOES: "+(mediaInstrucoes/10));
			wlog.newLine();
			wlog.write("QUANTUM: "+quantum);						
			wlog.newLine();
			wlog.close();
			olog.close();
=======
			
			FileWriter olog = new FileWriter(log.getPath(), true);
			BufferedWriter wlog = new BufferedWriter(olog);
			PrintWriter pw = new PrintWriter(wlog);

			pw.println("MEDIA DE TROCAS: "+(mediaTrocas/10));
			pw.println("MEDIA DE INSTRUCOES: "+(mediaInstrucoes/10));
			pw.println("QUANTUM: "+quantum);
			pw.close();
			wlog.close();
			olog.close();
			
//			wlog.write("MEDIA DE TROCAS: "+(mediaTrocas/10));
//			wlog.newLine();
//			wlog.write("MEDIA DE INSTRUCOES: "+(mediaInstrucoes/10));
//			wlog.newLine();
//			wlog.write("QUANTUM: "+quantum);						
//			wlog.newLine();
>>>>>>> 9f9c2da7010ed1958893c7935de41f5fe28e51aa
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
	}
	
	static void criaProcessos(File log) throws IOException{
		FileReader fr;
		Processo p;
		for(int i = 1; i <= 3; i++){ //@@@@@@@@@@@@MODIFICADO O NUMERO DE LOOPS PARA LER APENAS 3 ARQUIVOS
			if(i < 10) fr = new FileReader("processos/0" + i + ".txt");
			else fr = new FileReader("processos/" + i + ".txt");
			p = new Processo(fr); 
			tabela[i - 1] = p;
			pronto.push(p);
			
<<<<<<< HEAD
			FileWriter olog = new FileWriter(log);
			BufferedWriter wlog = new BufferedWriter(olog);
			wlog.write("Carregando "+p.nome);
			wlog.newLine();
			wlog.close();
			olog.close();
=======
			FileWriter olog = new FileWriter(log.getPath(), true);
			BufferedWriter wlog = new BufferedWriter(olog);
			PrintWriter pw = new PrintWriter(wlog);
			pw.println("Carregando "+p.nome);
			pw.close();
>>>>>>> 9f9c2da7010ed1958893c7935de41f5fe28e51aa
		}
	}
	
	static void leQuantum() throws IOException{
		FileReader fr = new FileReader("processos/quantum.txt");
		Scanner sc = new Scanner(fr);
		quantum = sc.nextInt();
	}
	
	/* verifica se a tabela de processos esta vazia */
	static boolean tabelaDeProcessosEstaVazia(){//@@@@@@@@@@@@MODIFICADO O NUMERO DE LOOPS PARA LER APENAS 3 ESPAÇOS
		for(int i = 0; i < 3; i++)
			if(tabela[i] != null) return false;
		return true;
	}
	
	/* decrementa o contador da fila de bloqueados
	se algum processo tiver, ao final do procedimento, cont == 0, ele eh adicionado a fila de prontos */
	static void decrementaBloqueados()
	{
		if(bloqueado.inicio != null)
		{
			for(No n = bloqueado.inicio; n != null; n = n.prox) n.indice--;
			No n = bloqueado.inicio;
			while(n != null && n.indice == 0){ /* os mais antigos da fila de bloqueados esta sempre no inicio*/
				Processo p = n.src;
				p.status = "Pronto";
				pronto.push(p);
				bloqueado.inicio = n.prox;
				n = n.prox;
			}
		}
	}

	/* remove processo da fila de processos, obtendo estatisticas */
	static void finalizaProcesso(Processo p){//@@@@@@@@@@@@MODIFICADO O NUMERO DE LOOPS PARA FINALIZAR APENAS 3
		for(int i = 0; i < 3; i++)
			if(tabela[i] == p) /* comparacao de ponteiros */
				tabela[i] = null;
		/* OBTEM ESTATISTICAS */ 
	}

}

class Processo{

	public String nome;
	public String status = "Pronto";
	public String DS[] = new String[21]; /* segmento de dados */
	public int PC = 0; /* contador de programa */
	public int X = 0;
	public int Y = 0;
	
	Processo(FileReader fr){
		Scanner sc = new Scanner(fr);
		int i = 0;
		nome = sc.nextLine();
		while(sc.hasNextLine()){
			DS[i] = sc.nextLine();
			i++;
		}
	}
	
}

class Fila{
	
	public No inicio;
	private No ultimo;
	
	Fila(){
		inicio = null;
		ultimo = null;
	}
	
	/* adiciona no a fila */
	public void push(Processo p){
		if(inicio == null)
		{
			No n = new No(p);
			inicio = n;
			ultimo = n;
		}
		else
		{
			No novoNo = new No(p);
			ultimo.prox = novoNo;
			ultimo = novoNo;
		}
	}
	
	/* remove no da fila, retornando o respectivo processo */
	public Processo pop()
	{
		if(inicio != null)
		{
			No aux = inicio;
			if(inicio != null) inicio = inicio.prox;
			else inicio = null;
			return aux.src;	
		}
		return null;
	}
	
}

class No{

	public Processo src;
	public No prox;
	public int indice = 3; /* desbloqueia depois de outros dois processos*/
	
	No(Processo p){
		src = p;
		prox = null;
	}

}