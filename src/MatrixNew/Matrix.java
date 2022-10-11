package MatrixNew;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Matrix
{
	
	//Atribut objectf
	public int NBrsEff;
	public int NKolEff;
	public double [][] TD;
	
	public static int BrsMin = 1;
	public static int KolMin = 1;
	public Scanner in = new Scanner(System.in);
	
	
	//Method
	
	//Konstruktor
	public Matrix()
	{
		NBrsEff=0;
		NKolEff=0;
	}
	
	public Matrix(int nb, int nk)
	/* Membuat matrix ukuran ixj */
	{
		TD = new double [nb+1][nk+1];
		NBrsEff=nb;
		NKolEff=nk;
	}
	
	//Getter
	public double Elmt(int i, int j)
	{
		return TD[i][j];
	}
	
	//Setter
	public void SetElmt(int i, int j, double val)
	{
		TD[i][j] = val;
	}
	
	//Selektor
	public int GetFirstIdxBrs()
	/* Mengirimkan index baris terkecil M */
	{
		return BrsMin;
	}
	
	public int GetFirstIdxKol()
	/* Mengirimkan index kolom terkecil M */
	{
		return KolMin;
	}
	
	public int GetLastIdxBrs ()
	/* Mengirimkan index baris terbesar M */
	{
		return this.GetFirstIdxBrs()+this.NBrsEff-1;
	}
	
	public int GetLastIdxKol ()
	/* Mengirimkan index kolom terbesar M */
	{
		return this.GetFirstIdxKol()+this.NKolEff-1;
	}
	
	public double GetDiagonal (int i)
	/* Mengirimkan elemen M(i,i) */
	{
		return this.Elmt(i,i);
	}
	
	/* ********** KELOMPOK OBE ***************** */
	public void MinusRow(int basis, int target, double koef)
	/* Mengurangi row ke target dengan koef * basis */
	{
		for (int j=this.GetFirstIdxKol(); j<= this.GetLastIdxKol(); j++)
		{
			this.SetElmt(target, j,this.Elmt(target,j)-(koef*this.TD[basis][j]));
		}
	}
	
	public void SwapRow(int a, int b)
	/* Melakukan swap matrix m antara a dengan b */
	{
		for (int j=this.GetFirstIdxKol(); j<= this.GetLastIdxKol(); j++)
		{
			double temp = this.Elmt(a, j);
			this.SetElmt(a, j, this.Elmt(b, j));
			this.SetElmt(b, j, temp);
		}
	}
	
	public void MakeSatu(int i, double koef)
	/* Membagi baris ke i dengan konstanta koef dengan tujuan untuk membuat depannya menjadi 1 */
	{
		for (int j=this.GetFirstIdxKol(); j<= this.GetLastIdxKol(); j++)
		{
			this.SetElmt(i, j,this.Elmt(i,j)/koef);
		}
	}
	
	public boolean findSwap(int j)
	/* Mencari baris yang tidak 0 di kolom ke j, lalu di swap */
	/* Jika berhasil melakukan swap akan return true, jika tidak maka returnnya false */
	{
		int x=j+1;
		while (this.Elmt(x,j)==0 && x< this.GetLastIdxBrs())
		{
			x++;
		}
		if (Math.floor(this.Elmt(x,j))!=0)//swap
		{
			this.SwapRow(x, j);
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/* ********** KELOMPOK BACA/TULIS ********** */ 
	public void BacaMatrix (int NB, int NK)
	/* I.S. IsIdxValid(NB,NK) */ 
	/* F.S. M terdefinisi nilai elemen efektifnya, berukuran NB x NK */
	{
		TD = new double [NB+1][NK+1];
		int i,j;
		for (i=BrsMin; i<BrsMin+NB; i++)
		{
			for (j=KolMin; j<KolMin+NK; j++)
			{
				SetElmt(i,j,in.nextDouble());
			}
		}
	}
	
	public void BacaFileMatrix(String namaFile) throws FileNotFoundException
	{
		Scanner input = new Scanner (new File(namaFile));
		int rows = 0;
		int columns = 0;
		
	    //Hitung jumlah kolom matrix
		Scanner eachLine = new Scanner(input.nextLine());
	    while(eachLine.hasNextDouble())
	    {
	    	eachLine.nextDouble();
	        ++columns;
	    }
	    eachLine.close();
	    
	    //Hitung jumlah baris matrix
	    input = new Scanner(new File(namaFile));
		while(input.hasNextLine())
		{
		    ++rows;
		    input.nextLine();
		}
		input.close();
		
		NBrsEff=rows;
		NKolEff=columns;
	
		//Baca data
		input = new Scanner(new File(namaFile));
		for(int i = 1; i <= rows; ++i)
		{
		    for(int j = 1; j <= columns; ++j)
		    {
		    	TD[i][j] = input.nextDouble();
		    }
		}
		input.close();
	}
	
	public void TulisMATRIKSFile (PrintWriter out) throws IOException
	/* I.S. M terdefinisi */
	/* F.S. Nilai M(i,j) ditulis ke layar per baris per kolom, masing-masing elemen per baris 
	   dipisahkan sebuah spasi */
	{
		double angka;
		for (int i=this.GetFirstIdxBrs(); i<=this.GetLastIdxBrs(); i++)
		{
			angka = Math.round(this.Elmt(i,this.GetFirstIdxKol())*1000.0)/1000.0;
			out.print(angka);
			for (int j=this.GetFirstIdxKol()+1; j<=this.GetLastIdxKol(); j++)
			{
				angka = Math.round(this.Elmt(i,j)*1000.0)/1000.0;
				out.print(" " + angka);
			}
			out.println();
		}
	}
	
	public void TulisMATRIKS ()
	/* I.S. M terdefinisi */
	/* F.S. Nilai M(i,j) ditulis ke layar per baris per kolom, masing-masing elemen per baris 
	   dipisahkan sebuah spasi */
	{
		double angka;
		for (int i=this.GetFirstIdxBrs(); i<=this.GetLastIdxBrs(); i++)
		{
			angka = Math.round(this.Elmt(i,this.GetFirstIdxKol())*1000.0)/1000.0;
			System.out.print(angka);
			for (int j=this.GetFirstIdxKol()+1; j<=this.GetLastIdxKol(); j++)
			{
				angka = Math.round(this.Elmt(i,j)*1000.0)/1000.0;
				System.out.print(" " + angka);
			}
			System.out.print("\n");
		}
	}
	
	/*Eliminasi Gauss dan Gauss-Jordan*/
	public void EliminasiGauss()
	/*I.S. Matriks M terdefinisi*/
	/*F.S. Matriks M merupakan matriks echelon*/
	/*Proses: melakukan OBE sampai dengan semua baris merupakan leading 1*/
	{
		int i=this.GetFirstIdxBrs();
		int j;
		int x;
		double koef;
		for (j=this.GetFirstIdxKol(); (j <this.GetLastIdxKol()) && (i<=this.GetLastIdxBrs()); j++)
		{
			boolean lanjut = true; //lanjut ke baris bawah, kalau false artinya ke kolom sampingnya pada baris sama
			if (this.Elmt(i, j)==0) 
			{
				x=i+1;
				boolean found=false;
				while ((x<=this.GetLastIdxBrs())&&(!found))
				{
					if(this.Elmt(x, j)!=0)
					{
						found=true;
					}
					else
					{
						x++;
					}
				}
				if (found) //swap
				{
					this.SwapRow(i, x);
				}
				else
				{
					lanjut = false;
				}
			}
			if (lanjut) /* pengurangan matrix */
			{
				this.MakeSatu(i,this.Elmt(i, j));
				for (x=i+1;x<=this.GetLastIdxBrs();x++)
				{
					koef = this.Elmt(x, j)/this.Elmt(i, j);
					this.MinusRow(i, x, koef);
				}
				i++;
			}
			
		}
	}
	
	public void EliminasiGaussJordan()
	/*I.S. Matriks M terdefinisi*/
	/*F.S. Matriks M echelon tereduksi*/
	/*Proses: melakukan eliminasi Gauss kemudian dari kolom dan baris terakhir,
	menjadikan setiap kolom memiliki elemen diagonal 1 dan elemen lainnya 0*/
	{
		for(int j=this.GetLastIdxKol()-1; j>=this.GetFirstIdxKol()+1; j--)
		{
			int i=this.GetLastIdxBrs();
			while ((i>=this.GetFirstIdxBrs())&&(this.Elmt(i, j)==0))
			{
				i--;
			}
			if ( (Math.floor(this.Elmt(i, j))==1) && (i-1>=this.GetFirstIdxBrs()))
			{
				for (int x=i-1; x>=this.GetFirstIdxBrs(); x--)
				{
					double koef = this.Elmt(x, j);
					this.MinusRow(i, x, koef);
				}
			}
		}
	}
	
}
