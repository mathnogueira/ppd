# Trabalho 1 de PPD

## Como executar os testes não paralelos:

```bash
cd nao-paralelo
chmod +x testes.sh
./testes.sh
```
Esse comando vai exibir o tempo total das 10 execuções do problema
com 4, 6, 8, 10, 12 e 14 rainhas. Tempo médio = o tempo exibido/10.

## Como executar os testes paralelos:

```bash
cd paralelo
chmod +x testes.sh
.testes.sh <numero_threads>
```
Esse comando faz praticamente a mesma coisa que o comando acima, porém,
ele executa o programa usando o número de threads passado como argumento.

Note que só serão executados testes onde o número de rainhas é divisível pelo
número de threads. Portanto, com 4 threads, somente 4, 8 e 12.