reset
set title 'Path Length 1'
set xlabel 'Shift Bits'
set ylabel 'Value Bits'
set zlabel 'Error'
splot '-' title 'Average Error' with lines, '-' title 'Standard Deviation' with lines
# error-avg x,y->z
1 2 3
2 7 5
5 3 2
9 8 2
8 0 9
EOF
# error-std x,y->z
9 7 1
2 5 2
9 7 7
2 4 6
0 2 5
EOF
