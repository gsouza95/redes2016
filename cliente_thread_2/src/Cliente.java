import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente extends Thread {
	public static void main(String args[]){
		try{
			// Cria um Socket conexao passando como parâmetro o endereco e a porta do servidor 
			
                        //Socket conexao = new Socket("localhost",40002);   //para simulacao local
                        Socket conexao = new Socket("cosmos.lasdpc.icmc.usp.br",40002);
			
                        //cria uma thread que envia a conexao
			Thread t = new Cliente(conexao);
			//inicia o thread
			t.start();
		}catch(IOException e){
			System.out.println("IOException"+e);
		}
	}
	private Socket conexao;
	public Cliente(Socket s){//recebe o valor do socket enviado na thread
		conexao = s;
	}
	public void run(){
		try{
             
                        Votacao v = new Votacao();
                        v.brancos = 0;
                        v.nulos = 0;
                        int i;
                        v.c1 = 0;
                        v.c2 = 0;
                        v.c3 = 0;
                        v.c4 = 0;
                        v.c5 = 0;
                        
                        System.out.println("Execucao do Cliente - Urna Eletronica\n"
                                + "Antes de registrar votos, e acionar outros comandos, é necessário carregar a lista de candidatos: Digite o opcode 999 + Enter");

                        //cria uma printstream para pegar as informacoes enviadas do servidor
			PrintStream saida = new PrintStream(conexao.getOutputStream());
			
                        //cria um bufferedreader para receber informacoes digitadas
			BufferedReader teclado = new BufferedReader (new InputStreamReader(System.in));
			
                        String digito="x";
                        
                        int allow = 1;
                        
                        while(!"777".equals(digito)){                           
                            
                             System.out.println("\n --- MENU ---\n"
                                + "1 - Votar\n"
                                + "2 - Votar Branco\n"
                                + "3 - Votar Nulo\n"
                                + "888 - Salvar Votacao\n"
                                + "999 - Obter Lista de Candidatos\n"
                                + "777 - Terminar Eleição em Todas as Urnas\n");
                            
                            //escreve uma mensagem ao usuario
                           System.out.println("Digite o comando: ");
                                 //digito recebe o valor digitado pelo usuario
                                digito = teclado.readLine();
                                //imprime o valor de digito
                                saida.println(digito);
                                
                                if("1".equals(digito) && allow!=1){
                                   
                                    //Ler o codigo de votacao do candidato
                                    System.out.println("Digite o numero do candidato: ");
                                    digito = teclado.readLine();
                                    saida.println(digito);
                                    //Scanner in = new Scanner(System.in);

                                    if(
                                            (!"13".equals(digito))
                                        &   (!"10".equals(digito))
                                        &   (!"42".equals(digito))
                                        &   (!"99".equals(digito))
                                        &   (!"22".equals(digito))
                                    ){
                                       System.out.println("Codigo de Votacao Invalido");
                                    }
                                    
                                }
                                else if("2".equals(digito) && allow!=1){
                                    //Votar branco
                                    v.brancos = v.brancos + 1;
                                }
                                else if("3".equals(digito) && allow!=1){
                                    //Votar nulo
                                    v.nulos = v.nulos + 1;
                                }
                                else if("888".equals(digito) && allow!=1){
                                    //Comando para o servidor salvar a eleicao
                                    
                                    //verifica se houve ao menos um voto
                                    int num_votos = v.brancos + v.nulos;
                                    num_votos += v.c1 + v.c2 + v.c3 + v.c4 + v.c5;
                                    
                                    if(num_votos==0){
                                        System.out.println("Nao ha votos para serem enviados ao servidor\n");
                                    }
                                    
                                }
                                
                                else if("999".equals(digito)){
                                    
                                        allow = 0;
                                    
                                          ArrayList<Candidato> listCand = new ArrayList<Candidato>();                                   
                                        
                                        //Carregar lista de candidatos do servidor
                                        ObjectInputStream RECEBE_OBJETO = new ObjectInputStream(conexao.getInputStream());
                                        Object oTemp = RECEBE_OBJETO.readObject();
                                        listCand = (ArrayList<Candidato>) oTemp;                                        
                                        
                                        //Exibe na tela
                                        System.out.println("Lista de candidatos carregada do servidor:");
                                        for(i=0;i<5;i++){
                                            System.out.println("\nNome: " + listCand.get(i).nome_candidato + "\nPartido: " + listCand.get(i).partido + "\nCodigo: " + listCand.get(i).codigo_votacao);
                                        }                                          
                                  
                                }
                        }
                        
                        conexao.close();
                        
		}catch(IOException e){
			System.out.println("IOException"+e);
		} catch (ClassNotFoundException ex) {
                Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
			try {
				conexao.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}