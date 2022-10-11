# program detterminantCrammer.py without numpy

import sys

def determinantCrammer(matrix):
        if len(matrix) == 1:
                return matrix[0][0]
        elif len(matrix) == 2:
                return -1*(matrix[0][1] * matrix[1][0]) + (matrix[0][0] * matrix[1][1])
        else:
                det = 0
                for i in range(0,len(matrix)):
                 newmatrix = []
                for j in range(1,len(matrix)):
                        newmatrix.append(matrix[j][:i] + matrix[j][i+1:])
                if i % 2 == 0:
                        det += determinantCrammer(newmatrix) * matrix[0][i]
                else:
                        det -= determinantCrammer(newmatrix) * matrix[0][i]
                return det
        
def main():
        matrix = [[3,2,1],[1,-2,0],[2,1,-2]]
        print(determinantCrammer(matrix))

if __name__ == "__main__":
        main()