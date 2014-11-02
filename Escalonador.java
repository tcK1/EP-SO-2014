import java.io.*;
import java.util.Scanner;

public class Escalonador{

	static final int NUM_PROCESSOS = 10; /* numero total de processos */
	static Processo tabela[] = new Processo[NUM_PROCESSOS]; /* tabela de procesos */
	static int quantum;
	static Processo executando = null;
	static Fila pronto = new Fila();
	static Fila bloqueado = new Fila();
	
	public static void main(String[] args){
		try{
			int trocas = 0;
			int instrucoes = 0;
			leQuantum(); /* obtem o valor do quantum a ser utilizado */
			String diretorioAtual = System.getProperty("user.dir");
			File log = new File(diretorioAtual + "/log" + quantum + ".txt"); /* instancia novo documento de texto para o log */
			if(!log.exists()) log.createNewFile(); /* caso este nao exista, um novo e criado */
			criaProcessos(log);
			
			while(!tabelaEstaVazia()){ /* enquanto houver processos na tabela de processos */
				decrementaBloqueados(); /* decrementa o contador da fila dos bloqueados */
				executando = pronto.pop(); /* obtem o proximo a executar */
				trocas++;
				if(executando == null) continue;
				executando.status = "Executando";		
				for(int c = 0; c < quantum; c++){  /* operacoes que o processo tem direito */
					PrintWriter pw = new PrintWriter(
						new BufferedWriter(
							new FileWriter(log.getPath(), true)));
					String instrucao = executando.DS[executando.PC];
					executando.PC++;
					instrucoes++;
					switch(instrucao){
						case "COM": 
							pw.println("Executando " + executando.nome);
							pw.close();
							if(quantum - c == 1){ /* depois da ultima iteracao, inserir na fila de prontos */
								executando.status = "Pronto";
								pronto.push(executando);
								pw.println("Interrompendo " + executando.nome + " após " + quantum + " instruções");
								pw.close();
								break;
							}
							break;
						case "E/S":
							executando.status = "Bloqueado";
							bloqueado.push(executando);
							/* c = numero de comandos executadores durante o quantum */
							if(c == 0){
								pw.println("Interrompendo " + executando.nome + " após 0 instrução (havia apenas a E/S)");
								pw.close();
							}
							else if(c == 1){
								pw.println("Interrompendo " + executando.nome + " após 1 instrução (havia um comando antes da E/S)");
								pw.close();
							}
							else{
								pw.println("Interrompendo " + executando.nome + " após " + c + " instruções (haviam " + c + " comandos antes da E/S)");
								pw.close();
							}
							c = quantum;
							break;
						case "SAIDA":
							finalizaProcesso(executando); /* remove processo da fila de bloqueados */
							c = quantum;
							pw.println(executando.nome + " terminado. X=" + executando.X + ". Y=" + executando.Y);
							pw.close();
							break;
						default:
							switch(instrucao.substring(0,1)){ /* primeiro caractere eh X ou Y */
								case "X":
									executando.X = Integer.parseInt(instrucao.substring(2));
									pw.println("Executando " + executando.nome);
									pw.close();
									if(quantum - c == 1){ /* depois da ultima iteracao, inserir na fila de prontos */
										executando.status = "Pronto";
										pronto.push(executando);
										pw.println("Interrompendo " + executando.nome + " após " + quantum + " instruções");
										pw.close();	
									}
									break;
								case "Y":
									executando.Y = Integer.parseInt(instrucao.substring(2));
									pw.println("Executando " + executando.nome);
									pw.close();
									if(quantum - c == 1){ /* depois da ultima iteracao, inserir na fila de prontos */
										executando.status = "Pronto";
										pronto.push(executando);
										pw.println("Interrompendo " + executando.nome + " após " + quantum + " instruções");
										pw.close();	
									}
									break;
							}
					}
				}
			}
			
			PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(log.getPath(), true)));
			float mediaTrocas = (float)trocas / (float)NUM_PROCESSOS;
			float mediaInstrucoes = (float)instrucoes / (float)trocas;
			pw.println("MEDIA DE TROCAS: " + mediaTrocas);
			pw.println("MEDIA DE INSTRUCOES: " + mediaInstrucoes);
			pw.println("QUANTUM: " + quantum);
			pw.close();
		}
		catch(IOException ioe){
			System.out.println(ioe.getMessage());
		}
		catch(NullPointerException npe){
			npe.printStackTrace();
		}
	}
	
	static void criaProcessos(File log) throws IOException{
		FileReader fr;
		Processo p;
		for(int i = 1; i <= NUM_PROCESSOS; i++){
			if(i < 10) fr = new FileReader("processos/0" + i + ".txt");
			else fr = new FileReader("processos/" + i + ".txt");
			p = new Processo(fr); 
			tabela[i - 1] = p;
			pronto.push(p);
			
			FileWriter olog = new FileWriter(log.getPath(), true);
			BufferedWriter wlog = new BufferedWriter(olog);
			PrintWriter pw = new PrintWriter(wlog);
			pw.println("Carregando " + p.nome);
			pw.close();
		}
	}
	
	static void leQuantum() throws IOException{
		FileReader fr = new FileReader("processos/quantum.txt");
		Scanner sc = new Scanner(fr);
		quantum = sc.nextInt();
	}
	
	/* verifica se a tabela de processos esta vazia */
	static boolean tabelaEstaVazia(){
		for(int i = 0; i < NUM_PROCESSOS; i++)
			if(tabela[i] != null) return false;
		return true;
	}
	
	/* decrementa o contador da fila de bloqueados
	se algum processo tiver, ao final do procedimento, cont == 0, ele eh adicionado a fila de prontos */
	static void decrementaBloqueados(){
		if(bloqueado.inicio != null){
			for(No n = bloqueado.inicio; n != null; n = n.prox) n.indice--;
			No n = bloqueado.inicio;
			while(n != null && n.indice == 0){ /* os mais antigos da fila de bloqueados esta sempre no inicio*/
				Processo p = n.processo;
				p.status = "Pronto";
				pronto.push(p);
				bloqueado.inicio = n.prox;
				n = n.prox;
			}
		}
	}

	/* remove processo da fila de processos */
	static void finalizaProcesso(Processo p){
		for(int i = 0; i < NUM_PROCESSOS; i++)
			if(tabela[i] == p) /* comparacao de ponteiros */
				tabela[i] = null;
	}

}