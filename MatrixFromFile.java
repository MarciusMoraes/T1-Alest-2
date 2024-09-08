import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MatrixFromFile {

    public static void main(String[] args) {
        String filePath = "casoe30.txt";

        try {
            //Método para ler o arquivo e retornar a matriz
            char[][] matrix = readPatternFromFile(filePath);


        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    // Método que transforma arquivo em matriz.
    public static char[][] readPatternFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        int wayCounter=0;
        int wayFinder=0;
        char actualChar=' ';
        int pathSum=0;

        // Ler a primeira linha que contém o número de linhas e colunas
        String[] dimensions = reader.readLine().split(" ");
        int rows = Integer.parseInt(dimensions[0]);  
        int cols = Integer.parseInt(dimensions[1]);  

        // Criar a matriz
        char[][] matrix = new char[rows][cols];

        // Ler o resto do arquivo e preencher a matriz
        for (int i = 0; i < rows; i++) {
            String line = reader.readLine();
            for (int j = 0; j < line.length(); j++) {
                matrix[i][j] = line.charAt(j);
                if (line.charAt(j)=='#') {
                    wayCounter++;
                }
            }
        }
        reader.close();
        
        

        while (wayFinder<=wayCounter) {
            wayFinder++;
        }


        return matrix;
    }

    // Método para imprimir a matriz
    public static void printMatrix(char[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println(); 
        }
    }
}
