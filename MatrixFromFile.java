import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;




public class MatrixFromFile {
    public static char[][] matrix;

    public static void main(String[] args) {
        String filePath = args[0];

        try {
            //Método para ler o arquivo e retornar a matriz
            matrix = readPatternFromFile(filePath);


        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    // Método que transforma arquivo em matriz.
    public static char[][] readPatternFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        
        

        // Ler a primeira linha que contém o número de linhas e colunas
        String[] dimensions = reader.readLine().split(" ");
        int rows = Integer.parseInt(dimensions[0]);  
        int cols = Integer.parseInt(dimensions[1]);  
        int initialPos= 0;

        
        matrix = new char[rows][cols];

        
        for (int i = 0; i < rows; i++) {
            String line = reader.readLine();
            for (int j = 0; j < line.length(); j++) {
                matrix[i][j] = line.charAt(j);
            }
        }

        reader.close();

        for(int i=0;i<cols;i++){
            if (matrix[rows-1][i]!=' ') {
                initialPos=i;
                break;
            }
        }



        
        System.out.println("I Am Here:");
        System.out.println(maxSum(initialPos, rows-1, matrix[rows-1][initialPos]));

        return matrix;
    }

    
    public static int maxSum(int horiPosition,int verPosition, char direction){
        int sum=0;
        switch (matrix[verPosition][horiPosition]) {
            case '#':
                return 0;
                
            case '/':
                return maxSum(horiPosition + 1, verPosition - 1, '/');

            case '\\':
                return maxSum( horiPosition - 1, verPosition - 1, '\\');

            case '|':
                return maxSum(horiPosition , verPosition - 1, '|');
            case 'V':
                return Math.max(maxSum(horiPosition + 1, verPosition - 1, '/'),
                maxSum( horiPosition - 1, verPosition - 1, '\\'));    
            case 'W': 
                return Math.max(maxSum (horiPosition, verPosition - 1, '|'),
                Math.max(maxSum( horiPosition + 1, verPosition - 1, '/'),
                maxSum( horiPosition - 1, verPosition - 1, '\\')));
            
            default:
                switch (direction) {
                    case '/':
                        sum= maxSum( horiPosition + 1, verPosition - 1, '/');
                        break;
                    case '\\':
                        sum= maxSum( horiPosition - 1, verPosition - 1, '\\');
                        break;
                    case '|':
                        sum= maxSum( horiPosition, verPosition - 1, '|');
                        break;
                    
                }
                
                return Integer.parseInt("" + matrix[verPosition][horiPosition]) + sum;
                
        }
    }



    public static void printMatrix(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println(); 
        }
    }
}
