import java.util.Scanner;

class DCG {
	
	public static final int sf = 1;		//singular feminino
	public static final int pf = 2;		//plural feminino
	public static final int sm = 3;		//singular masculino
	public static final int pm = 4;		//plural masculino
	public static final int nada = 5;   //sem genero nem ordem
	
	public static void main(String[] args) {
	
		Scanner stdin = new Scanner(System.in);
		
		while(stdin.hasNextLine()) {
			String frase = stdin.nextLine();
			String[] palavras = frase.split(" ");
		
			int size = palavras.length;
		
			//Se a frase tiver pelo menos uma palavra, passa a maiuscula a minuscula
			if(size >=1) {
				char c = Character.toLowerCase(palavras[0].charAt(0));
				palavras[0] = c + palavras[0].substring(1,palavras[0].length());
			}
			System.out.print(frase + " ----> ");
			verificarFrase(palavras,size);
			System.out.println();
		}
	}
	
	public static void verificarFrase(String[] palavras, int size) {
		Data vr = new Data();
		
		boolean ans = false; 
		
		//-------------------------------------------------
		int res = vr.verificarDeterminantesF(palavras[0]);
		if(res == -1) {
			res = vr.verificarDeterminantesM(palavras[0]);
			if(res == -1) {
				System.out.println("Frase invalida");
				return;
			}
			else if(res == 0)  //frase em singular masculino
				ans = estadoDeterminante(vr,palavras,size,0,sm);
			else if(res == 1) //frase em plural masculino
				ans = estadoDeterminante(vr,palavras,size,0,pm);
		}
		else if (res == 0) //frase em singular feminino
			ans = estadoDeterminante(vr,palavras,size,0,sf);
		else if (res == 1) //frase em plural feminino
			ans = estadoDeterminante(vr,palavras,size,0,pf);
		//-------------------------------------------------
		if(!ans) {
			System.out.println("Frase invalida");
			return;
		}
		else {
			System.out.println("Frase correta");
			return;
		}
	}
	
	//Os seguintes metodos correspondem ao estado em que a palavra esta, tem nome estadoX, onde X foi o ultimo tipo de palavra verificado
	//Quando pos = size - 1 chegou a ultima posicao da frase e para alem de nao fazer mais nenhuma chamada retorna true,
	//porque esse estado foi validado pelo estado anterior
	
	public static boolean estadoDeterminante(Data vr, String[] palavras, int size, int pos, int estr) {
		
		//A seguir a um determinante tem de estar um nome
		
		if(pos == size - 1)
			return true;
		
		switch(estr) {
			case sf: { if(vr.verificarNomesF(palavras[pos+1])!=0)
							return false;
					   else
							return estadoNome(vr,palavras,size,pos+1,estr);
					 }
			case pf: { if(vr.verificarNomesF(palavras[pos+1])!=1)
							return false;
					   else
							return estadoNome(vr,palavras,size,pos+1,estr);
					 }
			case sm: { if(vr.verificarNomesM(palavras[pos+1])!=0)
							return false;
					   else
							return estadoNome(vr,palavras,size,pos+1,estr);
					 }
			case pm: { if(vr.verificarNomesM(palavras[pos+1])!=1) 
							return false;
					   else
							return estadoNome(vr,palavras,size,pos+1,estr);
					 }
			default: return false;
		}
	}
	public static boolean estadoNome(Data vr, String[] palavras, int size, int pos, int estr) {
		
		//A seguir a um nome esta um verbo ou chegou ao fim da frase
		if(pos == size - 1)
			return true;
		
		switch(estr) {
			case sf: { if(vr.verificarVerbos(palavras[pos+1])!=0)
							return false;
					   else
							return estadoVerbo(vr,palavras,size,pos+1,estr);
					 }
			case pf: { if(vr.verificarVerbos(palavras[pos+1])!=1)
							return false;
					   else
							return estadoVerbo(vr,palavras,size,pos+1,estr);
					 }
			case sm: { if(vr.verificarVerbos(palavras[pos+1])!=0)
							return false;
					   else
							return estadoVerbo(vr,palavras,size,pos+1,estr);
					 }
			case pm: { if(vr.verificarVerbos(palavras[pos+1])!=1)
							return false;
					   else
							return estadoVerbo(vr,palavras,size,pos+1,estr);
					 }
			default: return false;
		}
	}
	public static boolean estadoVerbo(Data vr, String[] palavras, int size, int pos, int estr) {
		
		//A seguir a um verbo o género da frase pode mudar
		//A seguir a um verbo pode estar uma preposicao, um determinante ou entao chegou ao fim da frase
		if(pos == size - 1)
			return true;
		
		int ans1 = vr.verificarPreposicoesF(palavras[pos+1]);
		int ans2 = vr.verificarPreposicoesM(palavras[pos+1]);			
		int ans3 = vr.verificarDeterminantesF(palavras[pos+1]);
		int ans4 = vr.verificarDeterminantesM(palavras[pos+1]);
		
		if(ans1==0)
			return estadoPreposicao(vr,palavras,size,pos+1,sf);
		else if(ans1==1)
			return estadoPreposicao(vr,palavras,size,pos+1,pf);
		else if(ans1==2)
			return estadoPreposicao(vr,palavras,size,pos+1,nada);
						
		else if(ans2==0)
			return estadoPreposicao(vr,palavras,size,pos+1,sm);
		else if(ans2==1)
			return estadoPreposicao(vr,palavras,size,pos+1,pm);
							
		else if(ans3==0)
			return estadoDeterminante(vr,palavras,size,pos+1,sf);
		else if(ans3==1)
			return estadoDeterminante(vr,palavras,size,pos+1,pf);
							
		else if(ans4==0)
			return estadoDeterminante(vr,palavras,size,pos+1,sm);
		else if(ans4==1)
			return estadoDeterminante(vr,palavras,size,pos+1,pm);
	
		else
			return false;
	}
	public static boolean estadoPreposicao(Data vr, String[] palavras, int size, int pos, int estr) {
		
		//A seguir a uma preposicao pode estar um determinante, ou um nome
		if(pos == size - 1)
			return true;
		
		switch(estr) {
			case sf: { if(vr.verificarNomesF(palavras[pos+1])==0) 
							return estadoNome(vr,palavras,size,pos+1,estr);
							
					   else if(vr.verificarDeterminantesF(palavras[pos+1])==0) 
							return estadoDeterminante(vr,palavras,size,pos+1,estr);
							
					   else 
							return false;
					 }
			case pf: { if(vr.verificarNomesF(palavras[pos+1])==1) 
							return estadoNome(vr,palavras,size,pos+1,estr);
							
					   else if(vr.verificarDeterminantesF(palavras[pos+1])==1) 
							return estadoDeterminante(vr,palavras,size,pos+1,estr);
							
					   else 
							return false;
					 }
			case sm: { if(vr.verificarNomesM(palavras[pos+1])==0) 
							return estadoNome(vr,palavras,size,pos+1,estr);
							
					   else if(vr.verificarDeterminantesM(palavras[pos+1])==0) 
							return estadoDeterminante(vr,palavras,size,pos+1,estr);
							
					   else 
							return false;
					 }
			case pm: { if(vr.verificarNomesM(palavras[pos+1])==1) 
							return estadoNome(vr,palavras,size,pos+1,estr);
							
					   else if(vr.verificarDeterminantesM(palavras[pos+1])==1) 
							return estadoDeterminante(vr,palavras,size,pos+1,estr);
							
					   else 
							return false;
					 }
			case nada: { int ans1 = vr.verificarNomesF(palavras[pos+1]);
						 int ans2 = vr.verificarNomesM(palavras[pos+1]);
						 int ans3 = vr.verificarDeterminantesF(palavras[pos+1]);
						 int ans4 = vr.verificarDeterminantesM(palavras[pos+1]);
						 
						 if(ans1==0)
							return estadoNome(vr,palavras,size,pos+1,sf);
						 else if(ans1==1)
							return estadoNome(vr,palavras,size,pos+1,pf);
							
						 else if(ans2==0)
							return estadoNome(vr,palavras,size,pos+1,sm);
						 else if(ans2==1)
							return estadoNome(vr,palavras,size,pos+1,pm);
							
						 else if(ans3==0)
							return estadoDeterminante(vr,palavras,size,pos+1,sf);
						 else if(ans3==1)
							return estadoDeterminante(vr,palavras,size,pos+1,pf);
							
						 else if(ans4==0)
							return estadoDeterminante(vr,palavras,size,pos+1,sm);
						 else if(ans4==1)
							return estadoDeterminante(vr,palavras,size,pos+1,pm);
	
						 else
							return false;
						}
			default: return false;
		}
	}
}

class Data {
	
	String[] nome_sf = new String[8];			//nome - singular feminino
	String[] nome_sm = new String[11];			//nome - singular masculino
	
	String[] nome_pf = new String[8];			//nomes - plural feminino
	String[] nome_pm = new String[11];			//nomes - plural masculino
	
	String[] verb_s = new String[4];			//verbo - singular 
	String[] verb_p = new String[4];			//verbos - plural
	
	String[] det_sf = new String[2];			//determinante - singular feminino
	String[] det_sm = new String[2];			//determinante - singular masculino
	
	String[] det_pf = new String[2];			//determinantes - plural  feminino
	String[] det_pm = new String[2];			//determinantes - plural  masculino
	
	String[] prep = new String[2];				//preposicao sem genero e sem ordem
	String[] prep_sf = new String[1];			//preposicao - singular feminino
	String[] prep_sm = new String[1];			//preposicao - singular masculino
	String[] prep_pf = new String[1];			//preposicoes - plural feminino
	String[] prep_pm = new String[1];			//preposicoes - plural masculino
	
	Data() {
		
		//db de nomes
		nome_sf[0] = "menina";
		nome_sf[1] = "floresta";
		nome_sf[2] = "mae";
		nome_sf[3] = "vida";
		nome_sf[4] = "noticia";
		nome_sf[5] = "lagrima";
		nome_sf[6] = "cidade";
		nome_sf[7] = "porta";
		
		nome_sm[0] = "tempo";
		nome_sm[1] = "cacador";
		nome_sm[2] = "lobo";
		nome_sm[3] = "rosto";
		nome_sm[4] = "rio";
		nome_sm[5] = "mar";
		nome_sm[6] = "vento";
		nome_sm[7] = "martelo";
		nome_sm[8] = "cachorro";
		nome_sm[9] = "tambor";
		nome_sm[10] = "sino";
		
		nome_pf[0] = "meninas";
		nome_pf[1] = "florestas";
		nome_pf[2] = "maes";
		nome_pf[3] = "vidas";
		nome_pf[4] = "noticias";
		nome_pf[5] = "lagrimas";
		nome_pf[6] = "cidades";
		nome_pf[7] = "portas";
		
		nome_pm[0] = "tempos";
		nome_pm[1] = "cacadores";
		nome_pm[2] = "lobos";
		nome_pm[3] = "rostos";
		nome_pm[4] = "rios";
		nome_pm[5] = "mares";
		nome_pm[6] = "ventos";
		nome_pm[7] = "martelos";
		nome_pm[8] = "cachorros";
		nome_pm[9] = "tambores";
		nome_pm[10] = "sinos";
		
		//db de verbos
		verb_s[0] = "corre";
		verb_s[1] = "correu";
		verb_s[2] = "corria";
		verb_s[3] = "bateu";
		
		verb_p[0] = "correm";
		verb_p[1] = "correram";
		verb_p[2] = "corriam";
		verb_p[3] = "bateram";
		
		//db de determinantes
		det_sf[0] = "a";
		det_sf[1] = "na";
		
		det_sm[0] = "o";
		det_sm[1] = "no";
		
		det_pf[0] = "as";
		det_pf[1] = "nas";
		
		det_pm[0] = "os";
		det_pm[1] = "nos";
		
		//db de preposicoes
		prep[0] = "para";
		prep[1] = "com";
		
		prep_sf[0] = "pela";
		
		prep_sm[0] = "pelo";
	
		prep_pf[0] = "pelas";
		
		prep_pm[0] = "pelos";
	}
	
	public int verificarVerbos(String wr) {
		int size = verb_s.length;
		
		//Procura se o verbo esta no array de verbos em singular
		for(int i = 0; i < size; i++)
			if(verb_s[i].equals(wr))
				return 0;
		
		size = verb_p.length;

		//Procura se o verbo esta no array de verbos em plural
		for(int i = 0; i < size; i++)
			if(verb_p[i].equals(wr))
				return 1;
		
		return -1;
		//retorna 0 se for singular, 1 se plural, -1 se nao encontrar
	}
	
	public int verificarNomesF(String wr) {
		int size = nome_sf.length;
		
		//Procura se o nome esta no array de nomes em singular feminino
		for(int i = 0; i < size; i++)
			if(nome_sf[i].equals(wr))
				return 0;
		
		size = nome_pf.length;
		
		//Procura se o nome esta no array de nomes em plural feminino
		for(int i = 0; i < size; i++)
			if(nome_pf[i].equals(wr))
				return 1;

		return -1;
		
		//retorna 0 se for singular feminino
		//retorna 1 se for plural feminino
		//retorna -1 se nao encontrar
	}
	
	public int verificarNomesM(String wr) {
		int size = nome_sm.length;
		
		//Procura se o nome esta no array de nomes em singular masculino
		for(int i = 0; i < size; i++)
			if(nome_sm[i].equals(wr))
				return 0;
		
		size = nome_pm.length;
		
		//Procura se o nome esta no array de nomes em plural masculino
		for(int i = 0; i < size; i++)
			if(nome_pm[i].equals(wr))
				return 1;
		
		return -1;
		
		//retorna 0 se for singular masculino
		//retorna 1 se for plural masculino
		//retorna -1 se nao encontrar
	}
	
	public int verificarDeterminantesF(String wr) {
		int size = det_sf.length;
		
		//Procura se o determinante esta no array de determinantes em singular feminino
		for(int i = 0; i < size; i++)
			if(det_sf[i].equals(wr))
				return 0;
		
		size = det_pf.length;
		
		//Procura se o determinante esta no array de determinantes em plural feminino
		for(int i = 0; i < size; i++)
			if(det_pf[i].equals(wr))
				return 1;
				
		return -1;
		
		//retorna 0 se for singular feminino
		//retorna 1 se for plural feminino
		//retorna -1 se nao encontrar
	}
	
	public int verificarDeterminantesM(String wr) {
		int size = det_sm.length;
		
		//Procura se o determinante esta no array de determinantes em singular masculino
		for(int i = 0; i < size; i++)
			if(det_sm[i].equals(wr))
				return 0;
		
		size = det_pm.length;
		
		//Procura se o determinante esta no array de determinantes em plural masculino
		for(int i = 0; i < size; i++)
			if(det_pm[i].equals(wr))
				return 1;
		
		return -1;
		
		//retorna 0 se for singular masculino
		//retorna 1 se for plural masculino
		//retorna -1 se nao encontrar
	}
	
	public int verificarPreposicoesF(String wr) {
		int size = prep.length;
		
		//Procura se o preposicao esta no array de preposicoes sem genero
		for(int i = 0; i < size; i++)
			if(prep[i].equals(wr))
				return 2;
		
		size = prep_sf.length;
		
		//Procura se o preposicao esta no array de preposicoes em singular feminino
		for(int i = 0; i < size; i++)
			if(prep_sf[i].equals(wr))
				return 0;
		
		size = prep_pf.length;
		
		//Procura se o preposicao esta no array de preposicoes em plural feminino
		for(int i = 0; i < size; i++)
			if(prep_pf[i].equals(wr))
				return 1;
		
		return -1;
		
		//retorna 2 se nao tiver ordem nem genero
		//retorna 0 se for singular feminino
		//retorna 1 se for plural feminino
		//retorna -1 se nao encontrar
	}
	
	public int verificarPreposicoesM(String wr) {
		int size = prep.length;
		
		//Procura se o preposicao esta no array de preposicoes sem genero
		for(int i = 0; i < size; i++)
			if(prep[i].equals(wr))
				return 2;
		
		size = prep_sm.length;
		
		//Procura se o preposicao esta no array de preposicoes em singular masculino
		for(int i = 0; i < size; i++)
			if(prep_sm[i].equals(wr))
				return 0;
		
		size = prep_pm.length;
		
		//Procura se o preposicao esta no array de preposicoes em plural masculino
		for(int i = 0; i < size; i++)
			if(prep_pm[i].equals(wr))
				return 1;
		
		return -1;
		
		//retorna 2 se nao tiver ordem nem genero
		//retorna 0 se for singular masculino
		//retorna 1 se for plural masculino
		//retorna -1 se nao encontrar
	}
}
