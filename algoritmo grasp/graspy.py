import numpy as np
import re
import time
import random


def knapsack_greedy(solution, value, weight, capacity):
    remaining_capacity = capacity
    total_value = 0
    total_weight = 0

    for item in solution:
        if weight[item] <= remaining_capacity:
            total_value += value[item]
            total_weight += weight[item]
            remaining_capacity -= weight[item]

    return total_value, total_weight


def construct_greedy_solution(value, weight, capacity, alpha):
    n = len(value)
    sorted_items = sorted(range(n), key=lambda x: value[x] / weight[x], reverse=True)

    solution = []
    remaining_capacity = capacity

    for item in sorted_items:
        if weight[item] <= remaining_capacity:
            if random.random() <= alpha:
                solution.append(item)
                remaining_capacity -= weight[item]

    return solution


def grasp_knapsack(value, weight, capacity, max_iterations, alpha):
    best_solution = []
    best_value = 0
    best_weigth = 0

    for _ in range(max_iterations):
        candidate_solution = construct_greedy_solution(value, weight, capacity, alpha)
        candidate_value, candidate_weight = knapsack_greedy(candidate_solution, value, weight, capacity)

        if candidate_value > best_value:
            best_solution = candidate_solution
            best_value = candidate_value
            best_weigth = candidate_weight


    return best_solution, best_value, best_weigth


def solve_knapsack_problem():
    n = int(10)
    capacity = int(75)

    id, value, weight = [], [], []
    id.append(0)
    value.append(int(100))
    weight.append(int(10))

    id.append(1)
    value.append(int(135))
    weight.append(int(5))

    id.append(2)
    value.append(int(150))
    weight.append(int(20))

    id.append(3)
    value.append(int(300))
    weight.append(int(8))

    id.append(4)
    value.append(int(45))
    weight.append(int(12))

    id.append(5)
    value.append(int(200))
    weight.append(int(10))

    id.append(6)
    value.append(int(80))
    weight.append(int(30))

    id.append(7)
    value.append(int(90))
    weight.append(int(19))

    id.append(8)
    value.append(int(50))
    weight.append(int(27))

    id.append(9)
    value.append(int(300))
    weight.append(int(14))

    return n, value, weight, capacity


def main():
    max_iterations = 100
    alpha = 0.3

    output_max_values = []
    execs = int(1)
    list_melhores_resultados = []
    for iterator in range(1,11):
        for iterator in range(1, 11):
            start_time = time.perf_counter()
            n, value, weight, capacity = solve_knapsack_problem()
            solution, max_value, max_weigth = grasp_knapsack(value, weight, capacity, max_iterations, alpha)
            output_max_values.append(max_value)
            execution_time = time.perf_counter() - start_time

            # print("\nExecução: ", execs)
            # print("Peso Total: ", max_weigth)
            # print("Valor Total: ", max_value)
            # print(f"Execution time: {execution_time} ")
            list_melhores_resultados.append(max_value)
            execs = execs + int(1)

        print(list_melhores_resultados)
        list_melhores_resultados = []

if __name__ == "__main__":
    main()
