# MetodoSimplex
Snippet Método Simplex Instrucciones : Lee detalladamente cada uno de los métodos propuestos para la resolución del problema,tanto el orden como la firma del método son soluciones sugeridas para la resolución del problema del método simplex, sin embargo se ha considerado cada punto de la resolución del método como paso indispensable y base, por lo que podrás usarlo como tu arquetipo o plantilla para iniciar el desarrollo de tu programa.

initializeSimplexTableau(double[][] coefficients, double[] rightHandSide, double[] objectiveFunction) • Descripción: Este método inicializa el tableau simplex con los coeficientes de las restricciones, el lado derecho de las restricciones y la función objetivo. • Entradas: • double[][] coefficients: Matriz de coeficientes de las restricciones. • double[] rightHandSide: Vector del lado derecho de las restricciones. • double[] objectiveFunction: Coeficientes de la función objetivo. • Salida: Una matriz que representa el tableau simplex inicial.

performPivoting(double[][] tableau, int pivotRow, int pivotColumn) • Descripción: Realiza la operación de pivoteo en el tableau simplex. • Entradas: • double[][] tableau: El tableau simplex actual. • int pivotRow: La fila del elemento pivote. • int pivotColumn: La columna del elemento pivote. • Salida: No retorna nada, pero modifica el tableau directamente.

findPivotColumn(double[][] tableau) • Descripción: Encuentra la columna pivote, basándose en la regla del mayor costo reducido negativo en la fila de la función objetivo. • Entradas: • double[][] tableau: El tableau simplex actual. • Salida: Índice de la columna pivote. Snippet Método Simplex

findPivotRow(double[][] tableau, int pivotColumn) • Descripción: Encuentra la fila pivote, utilizando la regla del mínimo cociente. • Entradas: • double[][] tableau: El tableau simplex actual. • int pivotColumn: Columna pivote identificada. • Salida: Índice de la fila pivote.

isOptimal(double[][] tableau) • Descripción: Determina si la solución actual es óptima. • Entradas: • double[][] tableau: El tableau simplex actual. • Salida: true si es óptima, false si no lo es.

extractSolution(double[][] tableau) • Descripción: Extrae la solución del tableau simplex. • Entradas: • double[][] tableau: El tableau simplex óptimo. • Salida: Un vector con los valores de las variables de decisión.

simplexMethod(double[][] coefficients, double[] rightHandSide, double[] objectiveFunction) • Descripción: Método principal que orquesta todo el proceso del método simplex. • Entradas: • double[][] coefficients: Matriz de coeficientes de las restricciones. • double[] rightHandSide: Vector del lado derecho de las restricciones. • double[] objectiveFunction: Coeficientes de la función objetivo. • Salida: Solución óptima del problema

el programa comienza por pedir los valores de la funcion y las restricciones
![Captura de pantalla 2024-05-02 104745](https://github.com/checkogmas/MetodoSimplex/assets/160876969/e141f40b-ce0c-43dc-9872-3c552b54f77b)
esta sera la funcion que vamos a ingresar y se ingresa de este modo cada valor va separado por un espacio

![Captura de pantalla 2024-05-02 104700](https://github.com/checkogmas/MetodoSimplex/assets/160876969/b6f63800-2282-4c87-b488-8ad9600b4d10)

a continuacion se genera la primera tabla
![Captura de pantalla 2024-05-02 105040](https://github.com/checkogmas/MetodoSimplex/assets/160876969/bd3cfcbf-a9e7-4c64-b9c7-70a0dc845447)

se genera la segunda tabla 
![Captura de pantalla 2024-05-02 105124](https://github.com/checkogmas/MetodoSimplex/assets/160876969/d1928d42-c8c5-4694-8bda-8b6d3eb0ec16) 

ya que se contruyeron las dos tablas se genera sobre ellas la busqueda de los resultados en este caso la llamaremos solucion optima y se guarda en un arcivo.xlsx
![image](https://github.com/checkogmas/MetodoSimplex/assets/160876969/cfda2b5b-7bbe-4747-a37f-5bb06422a4c0)



