import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MatrixFromFileTwo {

    public static void main(String[] args) {
        String filePath = "casoe300.txt";

        try {
            // Método para ler o arquivo e retornar a matriz
            char[][] matrix = readPatternFromFile(filePath);

            // Localizar a maior soma de caminhos
            int maiorSoma = encontrarMaiorCaminho(matrix);
            System.out.println("A maior soma dentre os caminhos é: " + maiorSoma);

        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        }
    }

    // Método que transforma arquivo em matriz
    public static char[][] readPatternFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

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
            }
        }
        reader.close();
        return matrix;
    }

    // Método para encontrar a maior soma de caminhos na árvore
    public static int encontrarMaiorCaminho(char[][] matrix) {
        int maiorSoma = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                // Identifica o início de um caminho (número)
                if (Character.isDigit(matrix[i][j])) {
                    int somaAtual = explorarCaminho(matrix, i, j);
                    maiorSoma = Math.max(maiorSoma, somaAtual);
                }
            }
        }
        return maiorSoma;
    }

    // Função recursiva para explorar um caminho a partir de uma posição (i, j)
    public static int explorarCaminho(char[][] matrix, int i, int j) {
        // Se a posição estiver fora dos limites da matriz, retornar 0
        if (i < 0 || i >= matrix.length || j < 0 || j >= matrix[i].length) {
            return 0;
        }

        // Se o caractere for um número, converte-o em valor
        if (Character.isDigit(matrix[i][j])) {
            int valor = Character.getNumericValue(matrix[i][j]);

            // Verifica as direções possíveis
            int esquerda = 0, direita = 0, vertical = 0;

            // Movimentos diagonais (\ e /) e verticais (|)
            if (i + 1 < matrix.length) {
                if (j > 0 && matrix[i + 1][j - 1] == '\\') {
                    esquerda = explorarCaminho(matrix, i + 2, j - 2); // Diagonal esquerda
                }
                if (j < matrix[i].length - 1 && matrix[i + 1][j + 1] == '/') {
                    direita = explorarCaminho(matrix, i + 2, j + 2); // Diagonal direita
                }
                if (matrix[i + 1][j] == '|') {
                    vertical = explorarCaminho(matrix, i + 2, j); // Vertical
                }

                // Bifurcação V
                if (matrix[i + 1][j] == 'V') {
                    esquerda = explorarCaminho(matrix, i + 2, j - 1);
                    direita = explorarCaminho(matrix, i + 2, j + 1);
                }

                // Bifurcação W
                if (matrix[i + 1][j] == 'W') {
                    esquerda = explorarCaminho(matrix, i + 2, j - 1);
                    vertical = explorarCaminho(matrix, i + 2, j);
                    direita = explorarCaminho(matrix, i + 2, j + 1);
                }
            }

            // Retorna a soma do valor atual mais o caminho de maior soma
            return valor + Math.max(vertical, Math.max(esquerda, direita));
        }

        return 0; // Retorna 0 se não for um número
    }
}