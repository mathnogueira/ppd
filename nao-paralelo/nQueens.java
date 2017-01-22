import java.util.Scanner;

class nQueens {
    int[][] board;
    int size;
    int rainhasThread;
    int numeroThread;

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
        if (col>=size)
            return true;
        int inicio = (this.numeroThread) * this.rainhasThread;
        int fim = inicio + this.rainhasThread;
        System.out.println("Inicio: " + inicio + "; Fim: " + fim);
        for (int i=inicio;i<fim;i++) {
            if(isSafe(i,col)){
                board[i][col]=1;
                if(parallelSolveProblem(col+1)) {
                    System.out.println("Solução:");
                    printBoard();
                    board[i][col] = 0;
                }
                board[i][col]=0;
            }
        }
        return false;
    }

    boolean parallelSolveProblem(int col){
        if (col>size-1)
            return true;
        for (int i=0;i<size;i++){
            if(isSafe(i,col)){
                board[i][col]=1;
                if(parallelSolveProblem(col+1)) {
                    System.out.println("Solução:");
                    printBoard();
                    board[i][col] = 0;
                }
                board[i][col]=0;
            }
        }
        return false;
    }

    boolean isSafe(int row,int col){
        if (row >= size || col >= size) {
            System.out.println("Linha: " + row + "; Coluna: " + col);
        }
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
        for (int i=0;i<size;i++) {
            System.out.print("\n");
            for (int j=0;j<size;j++) {
                System.out.print(board[i][j]+"\t");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("Numero de rainhas: ");
        int rainhas = in.nextInt();
        System.out.print("Rainhas por thread: ");
        int rainhasThread = in.nextInt();
        int numeroThreads = rainhas / rainhasThread;
        for (int i = 0; i < numeroThreads; i++) {
            nQueens solucao = new nQueens(rainhas, rainhasThread, i);
            solucao.solveProblem(0);
        }
    }
}