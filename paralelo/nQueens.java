import java.util.Scanner;
import java.util.concurrent.Semaphore;

class nQueens implements Runnable {
    int[][] board;
    int size;
    int rainhasThread;
    int numeroThread;
    static Semaphore semaphore = new Semaphore(1);

    public nQueens(int size, int rainhasThread,  int thread) {
        this.size = size;
        this.rainhasThread = rainhasThread;
        this.numeroThread = thread;
        board = new int[size][size];

        int i,j;
        for(i=0;i<size;i++)
            for(j=0;j<size;j++)
                board[i][j]=0;
    }

    boolean solveProblem(int col){
        // Essa função só executa a primeira iteração da recursão, então ela só verifica
        // a solução na primeira coluna do tabuleiro. O restante das colunas é verificada pela
        // função parallelSolveProblem.
        // Essa função também é a função de entrada da thread, e ela é responsável por
        // dizer quais são as casas que a thread pode verificar na primeira coluna (para evitar
        // que uma thread encontre uma solução já encontrada por outra). 
        // Essa função divide a primeira coluna entre as N threads do tabuleiro (note que isso só funciona
        // se o número de rainhas for divisível pelo número de threads). Portanto, se tivermos um tabuleiro
        // 10x10, e ele for dividido para 5 threads, a divisão ficará assim:
        // Thread 0: Verifica todas as soluções que na primeira coluna estão nas casas (0,0) e (0,1)
        // Thread 1: Verifica todas as soluções que na primeira coluna estão nas casas (0,2) e (0,3)
        // Thread 2: Verifica todas as soluções que na primeira coluna estão nas casas (0,4) e (0,5)
        // Thread 3: Verifica todas as soluções que na primeira coluna estão nas casas (0,6) e (0,7)
        // Thread 4: Verifica todas as soluções que na primeira coluna estão nas casas (0,8) e (0,9)
        if (col>=size)
            return true;
        int inicio = (this.numeroThread) * this.rainhasThread;
        int fim = inicio + this.rainhasThread;
        for (int i=inicio;i<fim;i++) {
            if(isSafe(i,col)){
                board[i][col]=1;
                if(parallelSolveProblem(col+1)) {
                    board[i][col] = 0;
                }
                board[i][col]=0;
            }
        }
        return false;
    }

    boolean parallelSolveProblem(int col){
        // Essa função executa de forma recursiva o algoritmo para procurar a solução
        // nas colunas 1 até a coluna N.
        if (col>size-1)
            return true;
        for (int i=0;i<size;i++){
            if(isSafe(i,col)){
                board[i][col]=1;
                if(parallelSolveProblem(col+1)) {
                    //printBoard();
                    board[i][col] = 0;
                }
                board[i][col]=0;
            }
        }
        return false;
    }

    boolean isSafe(int row,int col){
        // Retorna true se a casa pode conter uma rainha.
        int i,j;
        for(i=0;i<col;i++)
            if (board[row][i]==1)
                return false;
        for(i=row,j=col;(i>=0)&&(j>=0);i--,j--)
            if (board[i][j]==1)
                return false;
        for(i=row,j=col;(j>=0)&&(i<size);i++,j--)
            if (board[i][j]==1)
                return false;
        return true;
    }

    void printBoard(){
        // Aqui tem um semafaro para que as saídas são saiam todas embaralhadas
        // Então quando uma thread está escrevendo uma solução na saída padrão, a outra
        // tem que esperar ela terminar.
        try {
            semaphore.acquire();
            System.out.println("Solução encontrada pela thread " + this.numeroThread);
            for (int i = 0; i < size; i++) {
                System.out.print("\n");
                for (int j = 0; j < size; j++) {
                    System.out.print(board[i][j] + "\t");
                }
            }
            System.out.println();
        } catch (Exception e) {

        } finally {
            semaphore.release();
        }
    }

    public void run() {
        this.solveProblem(0);
    }

    public static void main(String[] args) {
//        Scanner in = new Scanner(System.in);
//        System.out.print("Numero de rainhas: ");
//        int rainhas = in.nextInt();
//        System.out.print("Rainhas por thread: ");
//        int rainhasThread = in.nextInt();
	System.out.println(args[1]);
        int rainhas = Integer.parseInt(args[0]);
        int rainhasThread = Integer.parseInt(args[1]);
        int numeroThreads = rainhas / rainhasThread;
        for (int i = 0; i < numeroThreads; i++) {
            // Inicia numeroThread instâncias do tabuleiro, onde cada uma delas será executada em
            // uma thread.
            nQueens solucao = new nQueens(rainhas, rainhasThread, i);
            // Roda a solução na thread.
            Thread thread = new Thread(solucao);
            thread.start();
            // solucao.solveProblem(0);
        }
    }
}