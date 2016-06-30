import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Servidor extends Thread{
    
    /* Variaveis usadas para escrita no arquivo */
    public int c1=0;
    public int c2=0;
    public int c3=0;
    public int c4=0;
    public int c5=0;
    public int b;
    public int n;
    
	public static void main(String args[]) throws IOException{
                
            int branco = 0, nulo = 0; 
            
		ServerSocket s = null;
		try{//tentando criar uma conexao
			s = new ServerSocket(40002);//Cria um SocketServer com porta 40002
                        //s.setReuseAddress(true);
			//if(!s.isBound()){
                          //  s.close();
                        //}
                        while(true){
	           /* Cria o objeto Socket que abre uma porta e aguarda a conexao do cliente
	            */
				Socket conexao = s.accept();
				//cria uma thread que envia a conexao
				Thread t = new Servidor(conexao);
				//inicia a thread t
				t.start();
			}
		}
                
                catch(IOException e){
			System.out.println("IOException "+e);
                        //s.close();
		}
                
                
	}
	private Socket conexao;
	public Servidor(Socket s){//recebe o valor do socket enviado na thread
		conexao = s;
	}
	public void run(){
            
            /* Criacao dos objetos referentes aos candidatos */
             Candidato c1 = new Candidato();
             c1.codigo_votacao = 13;
             c1.nome_candidato = "Dilma";
             c1.num_votos = 0;
             c1.partido = "PT";
             
             Candidato c2 = new Candidato();
             c2.codigo_votacao = 10;
             c2.nome_candidato = "Eneas";
             c2.num_votos = 0;
             c2.partido = "PRONA";                   
             
             Candidato c3 = new Candidato();
             c3.codigo_votacao = 42;
             c3.nome_candidato = "Eduardo Jorge";
             c3.num_votos = 0;
             c3.partido = "PV";
             
             Candidato c4 = new Candidato();
             c4.codigo_votacao = 99;
             c4.nome_candidato = "Bolsonaro";
             c4.num_votos = 0;
             c4.partido = "PSC";
             
             Candidato c5 = new Candidato();
             c5.codigo_votacao = 22;
             c5.nome_candidato = "Tiririca";
             c5.num_votos = 0;
             c5.partido = "PR";
             
             Votacao v = new Votacao();
             v.brancos = 0;
             v.nulos = 0;
             v.c1 = 0;
             v.c2 = 0;
             v.c3 = 0;
             v.c4 = 0;
             v.c5 = 0;
             
            int i;
            
		try{
                    
            // Cria uma buffer que vai armazenar os dados enviados pelo cliente
            BufferedReader inFromClient = new BufferedReader(new InputStreamReader(conexao.getInputStream()));
            
            // Faz a leitura das informaÃ§Ãµes enviadas pelo cliente as amazenam na variÃ¡vel "EscritaCliente"
            String EscritaCliente = "xxx";
            
            int allow = 1;
            
            // Laco de repeticao do MENU
            while(!"777".equals(EscritaCliente)){
                
                EscritaCliente = inFromClient.readLine();
                
                if("1".equals(EscritaCliente) && allow!=1){
                    
                     EscritaCliente = inFromClient.readLine();
                     if("13".equals(EscritaCliente)){
                         v.c1++;
                     }
                     if("10".equals(EscritaCliente)){
                         v.c2++;
                     }
                     if("42".equals(EscritaCliente)){
                         v.c3++;
                     }
                     if("99".equals(EscritaCliente)){
                         v.c4++;
                     }
                     if("22".equals(EscritaCliente)){
                         v.c5++;
                     }
                }
                
                if("2".equals(EscritaCliente) && allow!=1){
                     v.brancos++;
                }
                
                if("3".equals(EscritaCliente) && allow!=1){
                    v.nulos++;
                }
                
                if("888".equals(EscritaCliente) && allow!=1){
                    //Receber os objetos referentes aos candidatos, dos clientes
                    
                    System.out.println("Uma das urnas solicitou a escrita dos votos");
                    
                   /* System.out.println("Os seguintes votos foram somados ao arquivo:");
                        System.out.println("Brancos: " + v.brancos + "\nNulos: " + v.nulos + 
                                "\nDilma: " + v.c1 + "\nEneas: " + this.c2 +
                                "\nEduardo Jorge: " + v.c3 + "\nBolsonaro: " + v.c4 +
                                "\nTiririca: " + v.c5); */
                        
                        /*  Faz a leitura do arquivo */
                        
                        //File arquivo = new File("~/votacao.txt");
                        File arquivo = new File("votacao.txt");
                        //System.out.print(arquivo.getAbsolutePath());
                        
                        if(arquivo.exists()){   //Arquivo existe - leia, some e escreva novamente
                            
                            FileReader fr = new FileReader(arquivo);
                            BufferedReader br = new BufferedReader(fr);
                            
                            /*  Leitura e calculo   */
                            
                            int contador = 0;
                            while(br.ready()){
                                
                                String linha = br.readLine();
                                
                                if(contador==0){
                                   this.b = v.brancos + Integer.parseInt(linha);
                                }
                                if(contador==1){
                                    this.n = v.nulos  + Integer.parseInt(linha);
                                }
                                if(contador==2){
                                    this.c1 = v.c1 + Integer.parseInt(linha);
                                }
                                if(contador==3){
                                    this.c2 = v.c2 + Integer.parseInt(linha);
                                }
                                if(contador==4){
                                    this.c3 = v.c3 + Integer.parseInt(linha);
                                }
                                if(contador==5){
                                    this.c4 = v.c4 + Integer.parseInt(linha);
                                }
                                if(contador==6){
                                    this.c5 = v.c5 + Integer.parseInt(linha);
                                }
                                
                                contador++;
                            }
                            
                            br.close();
                            fr.close();
                            
                            /*  Escrita */
                            
                            FileWriter fw = new FileWriter(arquivo);
                            
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(new Integer(this.b).toString());
                            bw.newLine();
                            bw.write(new Integer(this.n).toString());
                            bw.newLine();
                            bw.write(new Integer(this.c1).toString());
                            bw.newLine();
                            bw.write(new Integer(this.c2).toString());
                            bw.newLine();
                            bw.write(new Integer(this.c3).toString());
                            bw.newLine();
                            bw.write(new Integer(this.c4).toString());
                            bw.newLine();
                            bw.write(new Integer(this.c5).toString());
                            bw.newLine();
                            
                            bw.close();
                            fw.close();
                            
                        }
                        else{ //Arquivo nao existe - apenas escreva
                            
                            this.b = v.brancos;
                            this.n = v.nulos;
                            this.c1 = v.c1;
                            this.c2 = v.c2;
                            this.c3 = v.c3;
                            this.c4 = v.c4;
                            this.c5 = v.c5;
                    
                            arquivo.createNewFile();
                            
                            FileWriter fw = new FileWriter(arquivo);
                            
                            /* Escreve linha por linha */
                            
                            BufferedWriter bw = new BufferedWriter(fw);
                            bw.write(new Integer(this.b).toString());
                            bw.newLine();
                            bw.write(new Integer(this.n).toString());
                            bw.newLine();
                            bw.write(new Integer(this.c1).toString());
                            bw.newLine();
                            bw.write(new Integer(this.c2).toString());
                            bw.newLine();
                            bw.write(new Integer(this.c3).toString());
                            bw.newLine();
                            bw.write(new Integer(this.c4).toString());
                            bw.newLine();
                            bw.write(new Integer(this.c5).toString());
                            bw.newLine();
                            
                            bw.close();
                            fw.close();
                        }
                        
                }
                
                if("999".equals(EscritaCliente)){
                    
                    allow = 0;
                    
                    //Enviar os objetos rederentes aos candidatos, para os clientes
                    System.out.println("Enviando lista de candidatos para os clientes");
                     ArrayList<Candidato> listCand = new ArrayList<Candidato>();
                    
                   listCand.add(c1);
                   listCand.add(c2);
                   listCand.add(c3);
                   listCand.add(c4);
                   listCand.add(c5);
                    
                    ObjectOutputStream  ENVIA_OBJETO;
                    ENVIA_OBJETO = new ObjectOutputStream(conexao.getOutputStream());
                    ENVIA_OBJETO.writeObject(listCand);
                            
                }
                
            }
            
            conexao.close();
            
                          System.out.println("\nResultado Final:\n");
                            
                            //File arquivo = new File("~/votacao.txt");
                            File arquivo = new File("votacao.txt");
                            
                            FileReader fr = new FileReader(arquivo);
                            BufferedReader br = new BufferedReader(fr);
                            
                            /*  Leitura  */
                            
                            int contador = 0;
                            while(br.ready()){
                                
                                String linha = br.readLine();
                            
                                if(contador==0){
                                   System.out.println("Brancos: " + linha);
                                }
                                if(contador==1){
                                   System.out.println("Nulos: " + linha);
                                }
                                if(contador==2){
                                    System.out.println(c1.nome_candidato + ": " + linha);
                                }
                                if(contador==3){
                                   System.out.println(c2.nome_candidato + ": " + linha);
                                }
                                if(contador==4){
                                    System.out.println(c3.nome_candidato + ": " + linha);
                                }
                                if(contador==5){
                                    System.out.println(c4.nome_candidato + ": " + linha);
                                }
                                if(contador==6){
                                    System.out.println(c5.nome_candidato + ": " + linha);
                                }
                                
                                contador++;
                            }
                            
            
            
            
		}
                catch(IOException e){
			System.out.println("IOException "+e);
		}
	}
}