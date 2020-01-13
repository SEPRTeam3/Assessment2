package com.kroy.game;

public class DebugClass
{
    public static String get2DBoolArrayPrint(boolean[][] matrix)
    {
		/*
		For array debugging
		 */
        String output = new String();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j])
                {
                    output = output + ('x' + "\t");

                }
                else
                {
                    output = output + ('o' + "\t");
                }
            }
            output = output + "\n";
        }
        return output;
    }

    public static String get2DIntArrayPrint(int[][] matrix)
    {
        String output = new String();
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                output = output + (matrix[j][i] + "\t");
            }
            output = output + "\n";
        }
        return output;
    }
}
