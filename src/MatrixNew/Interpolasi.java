package MatrixNew;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Interpolasi extends Spl {
	public int n; // banyaknya tuple titik yang akan dimasukkan
	public int nx; // banyaknya nilai x yang akan dihitung hasilnya
	public boolean validpoint;
	public double[] pointx; // meyimpan titik x dari tuple titik yang diinput
	public double[] pointy; // meyimpan titik x dari tuple titik yang diinput
	public double[] nilaix; // menyimpan nilai x yang valid yang akan dicari nilainya

	public Interpolasi() {

	}

	public double solvex(double x)
	// untuk mencari hasil dari nilai x yang akan dikira
	{
		double solusi = 0;
		for (int i = 1; i <= n; i++) {
			solusi = solusi + this.solusiSpl[i] * Math.pow(x, i - 1);
		}
		return solusi;
	}

	public void outputHasil(int counter) throws IOException
	// menyimpan persamaan dalam array of string
	{
		FileWriter fw = new FileWriter("output.txt", counter != 0);
		BufferedWriter bw = new BufferedWriter(fw);
		PrintWriter out = new PrintWriter(bw);

		System.out.println(counter + 1 + ":");
		out.println(counter + 1 + ":");

		if (validpoint) {
			System.out.println("Persamaan interpolasi polinom adalah : ");
			out.println("Persamaan interpolasi polinom adalah : ");

			persamaan = new String[1];
			persamaan[0] = "F(x)= ";
			if (this.solusiSpl[n] != 0) {
				persamaan[0] += solusiSpl[n] + "X^" + (n - 1); // variabel pertama
			}
			for (int j = n - 1; j > 1; j--) {
				if (this.solusiSpl[j] != 0) {
					if (this.solusiSpl[j] > 0) {
						persamaan[0] += " + ";
					} else if (this.solusiSpl[j] < 0) {
						persamaan[0] += " - ";
					}
					if (j > 2) {

						if (Math.abs(solusiSpl[j]) == 1.0) {
							persamaan[0] += "X^" + (j - 1);
						} else {
							persamaan[0] += Math.abs(solusiSpl[j]) + "X^" + (j - 1);
						}
					} else {
						persamaan[0] += Math.abs(solusiSpl[j]) + "X";
					}
				}
			}
			if (this.solusiSpl[1] != 0) {
				if (this.solusiSpl[1] > 0) {
					persamaan[0] += " + ";
				} else if (this.solusiSpl[1] < 0) {
					persamaan[0] += " - ";
				}
				persamaan[0] += Math.abs(this.solusiSpl[1]);
			}
			System.out.println(persamaan[0]);
			out.println(persamaan[0]);

			// print titik valid
			System.out.println("Hasil nilai x yang valid adalah: ");
			out.println("Hasil nilai x yang valid adalah: ");
			for (int i = 1; i <= nx; i++) {
				System.out.println("x" + i + "= " + nilaix[i]);
				out.println("x" + i + "= " + nilaix[i]);
			}

			for (int i = 1; i <= nx; i++) {
				System.out.println("F(" + nilaix[i] + ")= " + solvex(nilaix[i]));
				out.println("F(" + nilaix[i] + ")= " + solvex(nilaix[i]));
			}
		} else {
			System.out.println("Titik yang dimasukkan tidak valid, proses interpolasi dibatalkan!");
			out.println("Titik yang dimasukkan tidak valid, proses interpolasi dibatalkan!");
		}
		System.out.println();
		out.println();
		System.out.println("-------------------------------------------------------------------------");
		out.println("-------------------------------------------------------------------------");
		out.close();
	}

	public void olahTitik()
	/* Prosedur untuk mengolah titik menjadi matrix spl untuk interpolasi */
	{
		solusiSpl = new double[n + 1];
		/*
		 * Membuat matriks dan mengisi elemen-elemen matriks sesuai dengan titik yang
		 * ada
		 */
		TD = new double[n + 1][n + 2];
		NBrsEff = n;
		NKolEff = n + 1;
		// mengisi hingga 1 kolom sebelum kolom terakhir
		for (int i = 1; i <= n; i++) {
			for (int j = 1; j <= n; j++) {
				TD[i][j] = Math.pow(pointx[i], j - 1);
			}
		}

		// mengisi kolom paling terakhir
		int j = n + 1;
		for (int i = 1; i <= n; i++) {
			TD[i][j] = pointy[i];
		}
		this.EliminasiGauss();
		this.cariSolusiUnik();
	}

	/* ----------------------Baca Interpolasi----------------------- */
	public void bacaInterpolasiKeyboard()
	/* membuat matriks dari titik yang diberikan */
	{
		System.out.print("Masukkan jumlah titik: ");
		n = in.nextInt(); // n adalah jumlah titik
		System.out.print("Masukkan jumlah nilai x yang akan dicari nilai interpolasinya: ");
		nx = in.nextInt(); // nx adalah jumlah nilai x dimana akan dicari nilai interpolasinya

		pointx = new double[n + 1];
		pointy = new double[n + 1];
		nilaix = new double[nx + 1];
		validpoint = true;

		System.out.println("Masukkan nilai tuple titik:");
		int i = 1;
		int j;
		while (i <= n) // menerima inputan titik
		{
			pointx[i] = in.nextDouble();
			pointy[i] = in.nextDouble();

			// memvalidasi titik yang diterima
			if (n > 1) {
				int k = 2;
				while (k <= i && validpoint) {
					j = k;
					while (validpoint && j > 1) {
						if (pointx[k] == pointx[j - 1]) {
							validpoint = false;
						} else {
							j--;
						}
					}
					k++;
				}
			}
			i++;
		}

		if (validpoint) {
			// mencari max dan min dari pointx
			double max = pointx[1];
			double min = pointx[1];

			for (i = 2; i <= n; i++) {
				if (pointx[i] > max) {
					max = pointx[i];
				}
				if (pointx[i] < min) {
					min = pointx[i];
				}
			}

			this.olahTitik();

			System.out.println("Masukkan nilai x yang akan dicari hasil interpolasinya:");
			for (i = 1; i <= nx; i++) {
				System.out.print("x" + i + "= ");
				nilaix[i] = in.nextDouble();
			}

			i = 1;
			// validasi out of range
			while (i <= nx) {

				if (nilaix[i] > max || nilaix[i] < min) // out of range - del elmt
				{
					for (j = i; j <= nx - 1; j++) {
						nilaix[j] = nilaix[j + 1];
					}
					nx = nx - 1;
				} else {
					i++;
				}

			}
		}
	}

	public void bacaInterpolasiFile(String namaFile) throws FileNotFoundException
	/* membuat matriks dari titik yang diberikan melalui file namaFile */
	{
		validpoint = true;
		nx = 0;
		n = 0;
		Scanner masukan = new Scanner(new File(namaFile));

		/* Baca nilai x yang akan dicari nilainya melalui interpolasi */
		Scanner firstLine = new Scanner(masukan.nextLine());

		/* Hitung banyaknya x yang ingin dimasukkan */
		while (firstLine.hasNextDouble()) {
			firstLine.nextDouble();
			nx++;
		}
		firstLine.close();
		nilaix = new double[nx + 1];

		/* Hitung banyaknya point yang akan dimasukkan */
		while (masukan.hasNextLine()) {
			masukan.nextLine();
			n++;
		}
		pointx = new double[n + 1];
		pointy = new double[n + 1];

		/* Reset scanner to beginning of file */
		masukan = new Scanner(new File(namaFile));

		/* Inputkan nilai x yang akan di interpolasikan */
		for (int i = 1; i <= nx; i++) {
			nilaix[i] = masukan.nextDouble();
		}

		/* input pointx dan pointy */
		for (int i = 1; i <= n; i++) {
			pointx[i] = masukan.nextDouble();
			pointy[i] = masukan.nextDouble();
		}

		masukan.close();

		int i = 1;
		int j;
		while (i <= n) // menerima inputan titik
		{
			// memvalidasi titik yang diterima
			if (n > 1) {
				int k = 2;
				while (k <= i && validpoint) {
					j = k;
					while (validpoint && j > 1) {
						if (pointx[k] == pointx[j - 1]) {
							validpoint = false;
						} else {
							j--;
						}
					}
					k++;
				}
			}
			i++;
		}

		if (validpoint) {
			// mencari max dan min dari pointx
			double max = pointx[1];
			double min = pointx[1];

			for (i = 2; i <= n; i++) {
				if (pointx[i] > max) {
					max = pointx[i];
				}
				if (pointx[i] < min) {
					min = pointx[i];
				}
			}

			this.olahTitik();

			i = 1;
			// validasi out of range
			while (i <= nx) {
				if (nilaix[i] > max || nilaix[i] < min) // out of range - del elmt
				{
					for (j = i; j <= nx - 1; j++) {
						nilaix[j] = nilaix[j + 1];
					}
					nx = nx - 1;
				} else {
					i++;
				}

			}
		}
	}

}
