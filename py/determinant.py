#
# Source https://github.com/fabrizziop/matrix-determinant
#

import copy
def input_matrix():
    correct_value_ms = False
    while correct_value_ms == False:
        try:
            n = int(input("n x n square matrix, n: "))
            if n >= 1:
                correct_value_ms = True
            else:
                print("Please input an integer equal or higher than 1.")
        except ValueError:
            correct_value_ms = False
            print("Please input a valid integer.")
    om = []
    for i in range(0,n):
        current_row = []
        for j in range(0,n):
            correct_value_me = False
            cur_prompt = "Element " + str(i+1) + "," + str(j+1) + ": "
            while correct_value_me == False:
                try:
                    current_row.append(float(input(cur_prompt)))
                    correct_value_me = True
                except ValueError:
                    correct_value_me = False
                    print("Please input a valid number.")
        om.append(current_row)
    return om
    
def validate_square_matrix(input_matrix):
    num_of_rows = len(input_matrix)
    for row in input_matrix:
        if len(row) != num_of_rows and num_of_rows != 0:
            return False
    return True

def get_determinant(input_matrix):
    matrix_eval = validate_square_matrix(input_matrix)
    if matrix_eval == False:
        return "Matrix is not square, or isn't made of numbers!"
    matrix_length = len(input_matrix)
    det = 0
    if matrix_length == 1:
        det = input_matrix[0][0]
    elif matrix_length == 2:
        det = -1*(input_matrix[0][1] * input_matrix[1][0]) + (input_matrix[0][0] * input_matrix[1][1])
    else:
        for i in range(0,matrix_length):
            newmatrix = copy.deepcopy(input_matrix[1:])
            for j in range(0,matrix_length-1):
                del newmatrix[j][i]
            if i % 2 == 0:
                det += get_determinant(newmatrix) * input_matrix[0][i]
            else:
                det -= get_determinant(newmatrix) * input_matrix[0][i]
    return det

print("Matrix determinant calculator r1.0")
print("By fabrizziop")
print("MIT Licence")
matrix = input_matrix()
print(get_determinant(matrix))