package com.bsm.projectTest.jwt.Controller;

public class Test {
    public static void main(String[] args) {
        int[][] matrix1 = {{0, 1, 4, 4}, {3, 1, 5, 3}};
        int[][] matrix2 = {{1, 1, 6, 5}, {2, 0, 4, 2}, {2, 4, 5, 7}, {4, 3, 8, 6}, {7, 5, 9, 7}};

        int result1 = maxSumPath(matrix1);
        int result2 = maxSumPath(matrix2);

        System.out.println("Result 1: " + result1);
        System.out.println("Result 2: " + result2);
    }

    public static int maxSumPath(int[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;
        int[][] dp = new int[rows][cols];

        // Initialize the first row of dp array
        for (int i = 0; i < cols; i++) {
            dp[0][i] = matrix[0][i];
        }

        // Fill the dp array with maximum sum values
        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int left = (j > 0) ? dp[i - 1][j - 1] : 0;
                int up = dp[i - 1][j];
                int right = (j < cols - 1) ? dp[i - 1][j + 1] : 0;

                dp[i][j] = matrix[i][j] + Math.max(left, Math.max(up, right));
            }
        }

        // Find the maximum sum in the last row
        int maxSum = dp[rows - 1][0];
        for (int i = 1; i < cols; i++) {
            maxSum = Math.max(maxSum, dp[rows - 1][i]);
        }

        return maxSum;
    }
}
