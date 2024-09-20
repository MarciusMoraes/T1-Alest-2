import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;




public class MatrixFromFile {
    public static char[][] matrix;

        public static void main(String[] args) {
            String filePath = "casoe90.txt";
            
            
            try {
                BufferedReader reader = new BufferedReader(new FileReader(filePath));
    
                // Ler o número de linhas e colunas
                String[] dimensions = reader.readLine().split(" ");
                int rows = Integer.parseInt(dimensions[0]);
                int cols = Integer.parseInt(dimensions[1]);
                int initialPos = 0;
    
                matrix = new char[rows][cols];
    
                for (int i = 0; i < rows; i++) {
                    String line = reader.readLine();
                    for (int j = 0; j < line.length(); j++) {
                        matrix[i][j] = line.charAt(j);
                    }
                }
    
                reader.close();
    
                for (int i = 0; i < cols; i++) {
                    if (matrix[rows - 1][i] == 'W' || matrix[rows - 1][i] == '|' || matrix[rows - 1][i] == 'V') {
                        initialPos = i;
                        break;
                    }
                }
    
                // Exemplo de chamada para maxSum
                System.out.println(maxSum(initialPos, rows - 1, matrix[rows - 1][initialPos]));
    
            } catch (IOException e) {
                System.out.println("Erro ao ler o arquivo: " + e.getMessage());
            }
        }

    
    public static int maxSum(int horiPosition,int verPosition, char direction){
        int sum=0;
        if (horiPosition < 0 || horiPosition >= matrix[0].length || verPosition < 0 || verPosition >= matrix.length) {
            return 0;
        }
        char actualChar=matrix[verPosition][horiPosition];   
        switch (actualChar) {
            case '#':
                return 0;

            case ' ':
                return 0;    

            case '/':
                return maxSum(horiPosition + 1, verPosition - 1, '/');

            case '\\':
                return maxSum( horiPosition - 1, verPosition - 1, '\\');

            case '|':
                if (direction=='\\') {
                    return maxSum( horiPosition - 1, verPosition - 1, '\\');  
                }
                if (direction=='/') {
                    return maxSum(horiPosition + 1, verPosition - 1, '/');  
                }
                return maxSum(horiPosition , verPosition - 1, '|');

            case 'V':
                return Math.max(maxSum (horiPosition, verPosition - 1, '|'),
                Math.max(maxSum( horiPosition + 1, verPosition - 1, '/'),
                maxSum( horiPosition - 1, verPosition - 1, '\\')));    

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
