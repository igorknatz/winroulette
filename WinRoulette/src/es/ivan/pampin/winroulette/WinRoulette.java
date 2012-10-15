package es.ivan.pampin.winroulette;

import java.util.Arrays;
import java.util.LinkedList;

import android.graphics.Color;

public class WinRoulette
{
	public static final int[] rojo = {1,3,5,7,9,12,14,16,18,19,21,23,25,27,30,32,34,36};
	public static final int[] negro = {2,4,6,8,10,11,13,15,17,20,22,24,26,28,29,33,35};
	
	public static final int[] vecinos = {0,2,3,4,7,12,15,18,19,21,22,25,26,28,29,32,35};
	public static final int[] tercio = {5,8,10,11,13,16,23,24,27,30,33,36};
	public static final int[] huerfanos = {1,6,9,14,17,20,31,34};

	public static int getColor(int num)
	{
		if (num == 0)
			return Color.GREEN;
		if (Arrays.binarySearch(rojo, num) >= 0)
			return Color.RED;
		return Color.BLACK;
	}
	
	public LinkedList<Integer> numbers;
	
	public WinRoulette()
	{
		numbers = new LinkedList<Integer>();
	}
	
	public void addNumber(Integer num)
	{
		numbers.addFirst(num);
	}
	
	public void deleteLastNumber()
	{
		numbers.removeFirst();
	}

	public Integer getVecesRojo()
	{
		return getVecesInArray(rojo);
	}

	public Integer getVecesNegro()
	{
		return getVecesInArray(negro);
	}

	public Integer getVecesVecinos()
	{
		return getVecesInArray(vecinos);
	}
	
	public Integer getVecesTercio()
	{
		return getVecesInArray(tercio);
	}
	
	public Integer getVecesHuerfanos()
	{
		return getVecesInArray(huerfanos);
	}

	private Integer getVecesInArray(int[] array)
	{
		for (int i=0; i<numbers.size(); i++) {
			Integer n = numbers.get(i);
			if (Arrays.binarySearch(array, n) >= 0)
				return i;
		}
		return numbers.size();
	}
	
	public Integer getVecesPar()
	{
		return getVecesParImpar(true);
	}
	
	public Integer getVecesImpar()
	{
		return getVecesParImpar(false);
	}

	private Integer getVecesParImpar(boolean par)
	{
		for (int i=0; i<numbers.size(); i++) {
			Integer n = numbers.get(i);
			if (n == 0) continue;
			if (par) {
				if (n % 2 == 0)
					return i;
			}
			else
				if (n % 2 == 1)
					return i;
		}
		return numbers.size();
	}
	
	public Integer getVecesPrimeraMitad()
	{
		return getVecesRango(1, 18);
	}
	
	public Integer getVecesSegundaMitad()
	{
		return getVecesRango(19, 36);
	}
	
	public Integer getVecesPrimeraDocena()
	{
		return getVecesRango(1, 12);
	}
	
	public Integer getVecesSegundaDocena()
	{
		return getVecesRango(13, 24);
	}

	public Integer getVecesTerceraDocena()
	{
		return getVecesRango(25, 36);
	}

	private Integer getVecesRango(int from, int to)
	{
		for (int i=0; i<numbers.size(); i++) {
			Integer n = numbers.get(i);
			if (n >= from && n <= to)
				return i;
		}
		return numbers.size();
	}
	
	public Integer getVecesPrimeraColumna()
	{
		for (int i=0; i<numbers.size(); i++) {
			Integer n = numbers.get(i);
			if (n == 0) continue;
			if ((n+2) % 3 == 0)
				return i;
		}
		return numbers.size();
	}
	
	public Integer getVecesSegundaColumna()
	{
		for (int i=0; i<numbers.size(); i++) {
			Integer n = numbers.get(i);
			if (n == 0) continue;
			if ((n+1) % 3 == 0)
				return i;
		}
		return numbers.size();
	}
	
	public Integer getVecesTerceraColumna()
	{
		for (int i=0; i<numbers.size(); i++) {
			Integer n = numbers.get(i);
			if (n == 0) continue;
			if (n % 3 == 0)
				return i;
		}
		return numbers.size();
	}
}
