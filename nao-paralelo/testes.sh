n=$1

for i in 4 6 8 10 12 14
do
	echo "Tabuleiro com $i rainhas"
	time (
		for j in {1..10}
		do
			java nQueens $i $i > /dev/null
		done
	)
done