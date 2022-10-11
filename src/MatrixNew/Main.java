package MatrixNew;

import java.io.IOException;

import java.util.Scanner;

public class Main {
	static Scanner in = new Scanner(System.in);
	static int pilihan;
	static int metodePenyelesaian;
	static int caraInput;

	public static int input(int bBawah, int bAtas)
	/*
	 * Fungsi untuk input validator
	 * Fungsi ini akan selalu prompt user untuk mengulangi input selama input berada
	 * diluar range
	 */
	{
		int target;
		target = in.nextInt();
		while (target > bAtas || target < bBawah) {
			System.out.println("Input masih salah! Silahkan mengulangi input!(" + bBawah + "," + bAtas + ")");
			target = in.nextInt();
		}
		return target;
	}

	public static void output(int i) {
		if (i == 1) {
			System.out.println(
					"MENU\n" +
							"1. Sistem Persamaaan Linier\r\n" +
							"2. Keluar");
		} else if (i == 2) {
			System.out.println(
					"Pilih Metode:\n" +
							"1. Eliminasi Gauss\r\n" +
							"2. Eliminasi Gauss Jordan");
		} else if (i == 3) {
			System.out.println(
					"Pilih Metode Input:\n" +
							"1. Dari Keyboard\r\n" +
							"2. Dari File Eksternal");
		}
	}

	public static void main(String[] args) throws IOException {
		int counter = 0;
		output(1); //
		pilihan = input(1, 2);
		while (pilihan != 3) // belum pilih exit
		{
			if (pilihan == 1) // kalau spl
			{
				Spl data = new Spl();
				output(2); // prompt metode eliminasi
				metodePenyelesaian = input(1, 2);
				output(3); // prompt metode input
				caraInput = input(1, 2);

				if (caraInput == 1) // input dari keyboard
				{
					int nb, nk;
					System.out.println("Input jumlah baris:");
					nb = input(1, 500);
					System.out.println("Input jumlah kolom:");
					nk = input(2, 500);
					data.bacaSplKeyboard(nb, nk);
				} else // input dari file
				{
					data.bacaSplFile("array.txt");
				}

				data.outputHeaderSpl(counter, metodePenyelesaian);
				if (data.classifier() == 1) {
					data.cariSolusiUnik();
				} else if (data.classifier() == 2) {
					data.solusiBanyak();
				}
				data.outputSolusi(data.classifier());

			} else if (pilihan == 2) {
				System.out.println("Keluar");
				break;
			}

			counter++;
			output(1);
			pilihan = input(1, 2);
		}
		System.out.println("Akhirnya kelar juga!");
	}

}
