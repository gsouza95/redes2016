# redes2016
Projeto referente à disciplina de Redes de Computadores no ICMC - USP.
Neste diretório encontra-se o projeto implementado em Java para a simulação de uma eleição da visão das urnas eletrônicas (pasta cliente_thread), e um servidor (pasta servidor_thread). 
O servidor é responsável por computar os votos recebidos dos clientes.
Os clientes são os terminais responsáveis pela interface da urna. Permite-se fazer múltiplas execuções simultâneas do projeto do cliente com uma mesma conexão de servidor.
As pastas estão organizadas pela IDE Netbeans 8.1. Os arquivos .java encontram-se na subpasta src/.
Para mais informações, e descrição da implementação acesse o relatório nesse diretório.

Instruções de uso:

1. Conectar no servidor via acesso remoto.

2. Acessar o ssh (ssh node10) - para o cluster do ICMC

3. Apagar o arquivo votacao.txt, se existir, no diretório do cluster. Para que a votação comece do zero [ comando: rm votacao.txt ]. Caso se deseje continuar uma votação anterior, mantenha esse arquivo; desse modo os votos serão somados com os anteriores.

4. Executar o programa no servidor [ comando: java Servidor ].

	4.1. Caso se deseje simular a aplicação em outro host deve-se copiar as três classes .java que estão no diretório \servidor_thread\src\ para o servidor de destino.

	4.2. Alterar a seguinte linha de código do cliente, caso necessário:
	 Socket conexao = new Socket("cosmos.lasdpc.icmc.usp.br",40002); 
	[alterar o parâmetro 1 para o endereço do novo host, e o parâmetro 2 para a porta desejada, se necessário].

	4.3. Caso a porta tenha sido alterada nos clientes no passo anterior, é necessário alterá-la no servidor também
	no código: s = new ServerSocket(40002);
	[alterar o parâmetro para a porta desejada].

5. Caso a porta esteja ocupada ("address already used"), é necessário matar o processo java do usuário grupo02bsi - essa medida é necessária, pois existem múltiplos clientes se conectando a porta em várias threads criadas, então talvez a porta esteja aberta devido a uma execução anterior por uma das urnas. 
[ comando: top (para listar os processos); comando: kill -9 PID (onde PID é o número do processo).

6. Abrir os projetos do netbeans cliente_thread em na pasta cliente_thread.

7. Executar a classe principal (Cliente.java).

8. Caso o menu tenha sido exibido nos terminais do(s) cliente(s), siga as instruçoes do menu para a execução.
