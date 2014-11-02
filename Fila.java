class Fila{
	
	public No inicio;
	private No ultimo;
	
	Fila(){
		inicio = null;
		ultimo = null;
	}
	
	/* adiciona no a fila */
	public void push(Processo p){
		if(inicio == null){
			No n = new No(p);
			inicio = n;
			ultimo = n;
		}
		else{
			No n = new No(p);
			ultimo.prox = n;
			ultimo = n;
		}
	}
	
	/* remove no da fila, retornando o respectivo processo */
	public Processo pop(){
		if(inicio == null) return null;
		No aux = inicio;
		inicio = inicio.prox;
		return aux.processo;
	}
}