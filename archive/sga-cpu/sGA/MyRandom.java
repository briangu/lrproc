/*
 * sGA.java       version 1.00      8 August 1996
 *   A simple genetic algorithm where the fitness function takes
 * non-negative values only: generate initial population, then for each
 * generation select a new population, apply crossover and mutation, until
 * the maximum number of generations is exceeded or, if known, the best
 * fitness is attained.  Make sure the most fit member survives to the
 * next generation (elitism).
 *   This Java code was derived from the C code in the Appendix of "Genetic
 * Algorithms + Data Structures = Evolution Programs," by Zbigniew
 * Michalewicz, Second Extended Edition, New York: Springer-Verlag (1994).
 * Other ideas and code were drawn from AGAC by Bill Spears (12 June 1991).
 * Available by anonymous ftp from ftp.aic.nrl.navy.mil in directory
 * /pub/galist/src/ga.
 *
 * (C) 1996 Stephen J. Hartley.  All rights reserved.
 * Permission to use, copy, modify, and distribute this software for
 * non-commercial uses is hereby granted provided this notice is kept
 * intact within the source file.
 *
 * mailto:shartley@mcs.drexel.edu http://www.mcs.drexel.edu/~shartley
 * Drexel University, Math and Computer Science Department
 * Philadelphia, PA 19104 USA  telephone: +1-215-895-2678
 */

import java.io.*;

public class MyRandom {

   // Return a random double in [0,1).
   public static double dblRandom() {
      return Math.random();
   }

   // Return a random double in [0,high).
   public static double dblRandom(double high) {
      return Math.random()*high;
   }

   // Return a random double in [low,high).
   public static double dblRandom(double low, double high) {
      return Math.random()*(high - low) + low;
   }

   // Return a random integer from low to high (inclusive).
   public static int intRandom(int low, int high) {
      int nb = high - low + 1;
      return (int)(Math.random()*nb + low);
   }

   // Return a random integer from 1 to n inclusive.
   public static int intRandom(int n) {
      return (int)(Math.random()*n + 1);
   }

   // Return a random bit (0 or 1).
   public static int bitRandom() {
      return (int)(Math.random()*2);
   }

   // Return a random boolean (false or true).
   public static boolean boolRandom() {
      return Math.random() >= 0.5;
   }
}

