# redes2016
Foram disponibilizados 3 pastas com projetos de clientes idênticos em java, para simular uma votação com várias "urnas" no servidor multitarefas. Entretanto, é possível executar a aplicação de clientes em locais diferentes, com mais clientes ou com menos.

Instruções de uso:

1. Conectar no servidor via acesso remoto.

2. Acessar o ssh (ssh node10) - para o cluster do ICMC

3. Apagar o arquivo votacao.txt, se existir, no diretório do cluster. Para que a votação comece do zero [ comando: rm votacao.txt ]. Caso se deseje continuar uma votação anterior, mantenha esse arquivo. Desse modo os votos serão somados com a anterior.

4. Executar o programa no servidor [ comando: java Servidor ].

	4.1. Caso se deseje simular a aplicação em outro host deve-se copiar as três classes .java que estão no diretório \servidor_thread\src\ para o servidor de destino.

	4.2. Alterar a seguinte linha de código do cliente:
	 Socket conexao = new Socket("cosmos.lasdpc.icmc.usp.br",40002); 
	[alterar o parâmetro 1 para o endereço do novo host, e o parâmetro 2 para a porta desejada, se necessário].

	4.3. Caso a porta tenha sido alterada nos clientes no passo anterior, é necessário alterá-la no servidor também
	no código: s = new ServerSocket(40002);
	[alterar o parâmetro para a porta desejada].

5. Caso a porta esteja ocupada ("address already used"), é necessário matar o processo java do usuário grupo02bsi - essa medida é necessária, pois existem múltiplos clientes se conectando a porta em várias threads criadas, então talvez a porta esteja aberta devido a uma execução anterior inadequada por uma das urnas (clientes) - ver o tópico 8. 
[ comando: top (para listar os processos); comando: kill -9 PID (onde PID é o número do processo).

6. Abrir os projetos do netbeans cliente_thread em uma ou cada uma das pastas cliente_thread_X nesse diretório.

7. Executar a classe principal (Cliente.java).

8. Para terminar a execução em cada um dos clientes digite o opcode 777 + Enter. Isso faz com que o socket seja fechado, e execuções posteriores sejam possíveis, além de zerar o arquivo no servidor.
