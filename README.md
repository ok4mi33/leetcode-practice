# LeetCode Practice

Practica de algoritmos en Python, Java y JavaScript, con foco en entender la complejidad Big O de cada solucion, no solo resolver el problema.

## Setup del entorno

Requiere tener instalada:

- Python 3 → `python3 --version`
- JDK → `java --version` y `javac --version`
- Node.js → `node --version`


## Cómo correr cada lenguaje

\`\`\`bash
python3 python/arrays/two_sum.py
javac java/arrays/TwoSum.java && java -cp java/arrays TwoSum
node javascript/arrays/twoSum.js
\`\`\`

## Flujo de trabajo con Git

\`\`\`bash
git add .
git commit -m "descripcion del problema y su complejidad"
git push
\`\`\`

## Estructura del repositorio

\`\`\`
leetcode-practice/
├── python/          # soluciones en Python, por categoría
├── java/            # soluciones en Java, por categoría
├── javascript/       # soluciones en JavaScript, por categoría
└── notes/            # notas por problema
    └── two-sum/
        ├── java.md
        ├── python.md
        └── javascript.md
\`\`\`

Cada nota en `notes/` documenta: enfoque de fuerza bruta, errores
cometidos en el proceso, enfoque óptimo, complejidad (tiempo y
espacio) de ambos, y sintaxis específica del lenguaje usada.
