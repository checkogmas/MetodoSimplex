package proyecto.com.example.programa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileOutputStream;
import java.io.*;
import java.util.Scanner;

@SpringBootApplication
public class ProgramaApplication {

    public static void main(String[] args) {

        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Datos");

            // Pedir la función objetivo al usuario
            Scanner scanner = new Scanner(System.in);
            boolean ingresoCorrecto = false;
            double[] funcionObjetivo = null;
            while (!ingresoCorrecto) {
                System.out.print("Ingrese la función objetivo (coeficientes separados por espacios): ");
                try {
                    String[] funcionObjetivoStr = scanner.nextLine().split(" ");
                    funcionObjetivo = new double[funcionObjetivoStr.length];
                    for (int i = 0; i < funcionObjetivoStr.length; i++) {
                        funcionObjetivo[i] = Double.parseDouble(funcionObjetivoStr[i]);
                    }
                    ingresoCorrecto = true;
                } catch (NumberFormatException e) {
                    System.out.println("Error: los coeficientes de la función solo pueden ser números. Intente de nuevo.");
                }
            }

            // Pedir las restricciones al usuario
            double[][] coeficientes = new double[3][2];
            double[] valores = new double[3];
            for (int i = 1; i <= 3; i++) {
                System.out.print("Ingrese los coeficientes de la restricción " + i + " (separados por espacio): ");
                String[] restriccionStr = scanner.nextLine().split(" ");
                for (int j = 0; j < restriccionStr.length - 1; j++) {
                    coeficientes[i - 1][j] = Double.parseDouble(restriccionStr[j]);
                }
                valores[i - 1] = Double.parseDouble(restriccionStr[restriccionStr.length - 1]);
            }

            // Construir la tabla del Simplex
            double[][] tabla = construirTabla(coeficientes, valores, funcionObjetivo);

            // Aplicar el método Simplex
            tabla = simplex(tabla);

            // Imprimir la solución óptima
            System.out.println("Solución óptima:");
            System.out.println("Función objetivo = " + -tabla[tabla.length - 1][tabla[0].length - 1]);
            for (int i = 0; i < coeficientes[0].length; i++) {
                double[] columna = obtenerColumna(tabla, i);
                if (suma(columna) == 1) {
                    int fila = filaUnos(columna);
                    System.out.println("x" + (i + 1) + " = " + tabla[fila][tabla[0].length - 1]);
                } else {
                    System.out.println("x" + (i + 1) + " = 0");
                }
            }

            try (var fileOut = new FileOutputStream("valores.xlsx")){
                workbook.write(fileOut);
                System.out.println("Archivo 'valores.xlsx' creado exitosamente.");

            } catch (Exception e) {
                e.printStackTrace();
            }
            workbook.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static double[][] construirTabla(double[][] coeficientes, double[] valores, double[] funcionObjetivo) {
        int filas = coeficientes.length;
        int columnas = coeficientes[0].length + filas + 1;
        double[][] tabla = new double[filas + 1][columnas];

        // Llenar los coeficientes de las restricciones
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < coeficientes[i].length; j++) {
                tabla[i][j] = coeficientes[i][j];
            }
            tabla[i][columnas - 1] = valores[i];
        }

        // Llenar los coeficientes de la función objetivo
        for (int i = 0; i < funcionObjetivo.length; i++) {
            tabla[filas][i] = -funcionObjetivo[i];
        }

        return tabla;
    }

    private static void imprimirTabla(double[][] tabla) {
        for (double[] fila : tabla) {
            for (double valor : fila) {
                System.out.print(valor + "\t");
            }
            System.out.println();
        }
    }

    private static int columnaPivote(double[][] tabla) {
        int columnaPivote = 0;
        double minValor = Double.MAX_VALUE;
        int columnas = tabla[0].length;

        for (int j = 0; j < columnas - 1; j++) {
            if (tabla[tabla.length - 1][j] < minValor) {
                minValor = tabla[tabla.length - 1][j];
                columnaPivote = j;
            }
        }

        return columnaPivote;
    }

    private static int filaPivote(double[][] tabla, int columnaPivote) {
        int filaPivote = 0;
        double minRatio = Double.MAX_VALUE;

        for (int i = 0; i < tabla.length - 1; i++) {
            if (tabla[i][columnaPivote] > 0) {
                double ratio = tabla[i][tabla[0].length - 1] / tabla[i][columnaPivote];
                if (ratio < minRatio) {
                    minRatio = ratio;
                    filaPivote = i;
                }
            }
        }

        return filaPivote;
    }

    private static void pivote(double[][] tabla, int filaPivote, int columnaPivote) {
        int filas = tabla.length;
        int columnas = tabla[0].length;
        double pivotElement = tabla[filaPivote][columnaPivote];

        // Dividir la fila pivote por el elemento pivote
        for (int j = 0; j < columnas; j++) {
            tabla[filaPivote][j] /= pivotElement;
        }

        // Actualizar las otras filas
        for (int i = 0; i < filas; i++) {
            if (i != filaPivote) {
                double multiplier = tabla[i][columnaPivote];
                for (int j = 0; j < columnas; j++) {
                    tabla[i][j] -= multiplier * tabla[filaPivote][j];
                }
            }
        }
    }

    private static double[][] simplex(double[][] tabla) {
        int iteracion = 1;
        while (!esOptima(tabla)) {
            int columnaPivote = columnaPivote(tabla);
            int filaPivote = filaPivote(tabla, columnaPivote);
            pivote(tabla, filaPivote, columnaPivote);
            System.out.println("Tabla " + iteracion + ":");
            imprimirTabla(tabla);
            iteracion++;
        }
        return tabla;
    }

    private static boolean esOptima(double[][] tabla) {
        int columnas = tabla[0].length;

        for (int j = 0; j < columnas - 1; j++) {
            if (tabla[tabla.length - 1][j] < 0) {
                return false;
            }
        }

        return true;
    }

    private static double[] obtenerColumna(double[][] tabla, int columna) {
        double[] columnaArr = new double[tabla.length];
        for (int i = 0; i < columnaArr.length; i++) {
            columnaArr[i] = tabla[i][columna];
        }
        return columnaArr;
    }

    private static int filaUnos(double[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 1) {
                return i;
            }
        }
        return -1;
    }

    /// Test
    private static double suma(double[] arr) {
        double suma = 0;
        for (double numero : arr) {
            suma += numero;
        }
        return suma;
    }

}