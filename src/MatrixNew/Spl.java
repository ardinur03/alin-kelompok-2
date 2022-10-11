package MatrixNew;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



public class Spl extends Matrix
{
	/* Atribut Kelas */
	
	public String [] persamaan;
	/* Array string untuk menyimpan persamaan output */
	
	
	public double [] solusiSpl;
	/* Solusi untuk x1 - xn sesuai dengan index dari array */
	
	public int [] solved;
	/* Status solved untuk x1 - xn sesuai dengan index dari array */
	/* 1 untuk x yang diketahui nilai eksaknya, 2 untuk yang bisa disubstitusikan 
	 * Untuk yang masih 0 harusnya sudah parametric dasar */
	
	/* Method Kelas */
	/*------------Konstruktor-------------*/
	public Spl()
	{
		
	}
	
	public Spl(int nb, int nk)
	/* Membuat matrix ukuran ixj */
	{
		super(nb,nk);
		solved = new int [this.GetLastIdxKol()];
		solusiSpl = new double [this.GetLastIdxKol()];
	}
	
	/*------------Baca Spl------------ */
	public void bacaSplKeyboard(int nb, int nk)
	/* Membaca matrix spl dari keyboard */
	{
		TD = new double [nb+1][nk+1];
		NBrsEff=nb;
		NKolEff=nk;
		solved = new int [this.GetLastIdxKol()];
		solusiSpl = new double [this.GetLastIdxKol()];
		this.BacaMatrix(nb, nk);
	}
	
	public void bacaSplFile(String namaFile) throws FileNotFoundException
	/* Membaca matrix spl dari file external */
	{
		Matrix temp = new Matrix(501,501);
		temp.BacaFileMatrix(namaFile);
		NBrsEff = temp.NBrsEff;
		NKolEff = temp.NKolEff;
		TD = new double [NBrsEff+1][NKolEff+1];
		solved = new int [this.GetLastIdxKol()];
		solusiSpl = new double [this.GetLastIdxKol()];
		int i,j;
		for (i=this.GetFirstIdxBrs(); i<=this.GetLastIdxBrs(); i++)
		{
			for (j=this.GetFirstIdxKol(); j<=this.GetLastIdxKol(); j++)
			{
				TD[i][j] = temp.Elmt(i, j);
			}
		}
	}
	
	/* --------------Fungsi penentu jenis solusi dari spl ------------ */
	public boolean kiriKosong (int i)
	/* Mengembalikan true jika bagian kiri (exclude bagian hasil) dari suatu baris di array berisi 0 semua */
	{
		int j=this.GetFirstIdxKol();
		while(this.Elmt(i, j)==0 && j<this.GetLastIdxKol()-1)
		{
			j++;
		}
		return (this.Elmt(i, j)==0);
	}
	
	public int classifier()
	/*Mengembalikan nilai 1 jika solusinya unik, 2 jika solusinya banyak, -1 jika tidak ada solusi*/
	{
		int kembalian;
		boolean getVerdict=false;
		int i = this.GetLastIdxBrs();
		while (this.kiriKosong(i) && i>=this.GetFirstIdxBrs() && !getVerdict)
		{
			if (this.Elmt(i, this.GetLastIdxKol())!=0)
			{
				getVerdict=true;
			}
			i--;
		}
		if (getVerdict)// fix no solution
		{
			kembalian = -1;
		}
		else if (this.GetLastIdxBrs() < (this.GetLastIdxKol()-1) ) //banyak solusi
		{
			kembalian = 2;
		}
		else 
		{
			int j = this.GetFirstIdxKol();
			while (this.Elmt(j, j)!=0 && (j<this.GetLastIdxKol()-1))
			{
				j++;
			}
			if (this.Elmt(j, j)==0)//ada parametric
			{
				kembalian = 2;
			}
			else
			{
				kembalian = 1;
			}
		}
		return kembalian;
	}
	
	/* ----------------Procedure Output Hasil ------*/
	public void outputHeaderSpl(int counter,int metodePenyelesaian) throws IOException
	{
		FileWriter fw = new FileWriter("output.txt", counter!=0);
	    BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter out = new PrintWriter(bw);
	    System.out.println(counter+1+":");
	    out.println(counter+1+":");
	    if (metodePenyelesaian==1)
	    {
	    	System.out.println("Dengan eliminasi gauss, dihasilkan matrix:");
	    	out.println("Dengan eliminasi gauss, dihasilkan matrix:");
	    	this.EliminasiGauss();
	    }
	    else
	    {
	    	System.out.println("Dengan eliminasi gauss-jordan, dihasilkan matrix:");
	    	out.println("Dengan eliminasi gauss-jordan, dihasilkan matrix:");
	    	this.EliminasiGauss();
	    	this.EliminasiGaussJordan();
	    }
	    /* Output matrix ke file external */
	    this.TulisMATRIKSFile(out);
	    this.TulisMATRIKS();
	    out.close();
	}
	
	public void outputSolusi(int jenisSolusi) throws IOException
	/* Procedure output untuk solusi banyak */
	{
		FileWriter fw = new FileWriter("output.txt", true);
	    BufferedWriter bw = new BufferedWriter(fw);
	    PrintWriter out = new PrintWriter(bw);
	    if (jenisSolusi==1)//tunggal
	    {
	    	System.out.println("Jenis solusi SPL adalah solusi unik:");
			out.println("Jenis solusi SPL adalah solusi unik:");
	    }
	    else if (jenisSolusi==2)//banyak
	    {
	    	System.out.println("Jenis solusi SPL adalah solusi banyak:");
			out.println("Jenis solusi SPL adalah solusi banyak:");
	    }
	    else
	    {
	    	System.out.println("Solusi SPL tidak ada");
			out.println("Solusi tidak ada");
	    }
	    if (jenisSolusi!=-1)
	    {
	    	for (int j=this.GetFirstIdxKol(); j<=GetLastIdxKol()-1; j++) 
			{
				System.out.println(persamaan[j]);
				out.println(persamaan[j]);
			}
	    }
	    if (jenisSolusi==2)
	    {
	    	outputTambahanSolusiBanyak(out);
	    }
	    System.out.println("\n-------------------------------------------------------------------------");
	    out.println();
	    out.println("-------------------------------------------------------------------------");
		out.close();
		
	}
	
	public void outputTambahanSolusiBanyak(PrintWriter out) throws IOException
	/* Procedure output untuk solusi banyak */
	{
		System.out.print("Dengan ");
		out.print("Dengan ");
		for (int j=this.GetFirstIdxKol(); j<=this.GetLastIdxKol()-1; j++)
		{
			if (solved[j]==0)
			{
				System.out.print("P"+j+" ");
				out.print("P"+j+" ");
			}
		}
		System.out.println("adalah element dari bilangan rill");
		out.println("adalah element dari bilangan rill");
	}
	
	/* ----------------Pencari Solusi --------------*/
	public void cariSolusiUnik()
	/* Menuliskan semua solusi unik dari matriks ke dalam suatu array */
	{
		int i = this.GetLastIdxBrs();
		int j = this.GetFirstIdxKol();
		persamaan = new String [this.GetLastIdxKol()];
		int n = 0;
		boolean found = true;
		while (found && i>=this.GetFirstIdxBrs())
		{	
			while (j<= this.GetLastIdxKol()-1 && found)
			{
				found = this.Elmt(i, j)==0;
				j++;
			}
			if(found)
			{
				n++;
			}
			i--;
			j=1;
		}
		//Mengurang ruas kanan persamaan dengan ruas kiri
		for (i=this.GetLastIdxBrs()-n;i>=this.GetFirstIdxBrs();i--)
		{	
			for (j=this.GetFirstIdxKol();j<=this.GetLastIdxKol()-1;j++)
			{
				solusiSpl[i]=solusiSpl[i]-this.Elmt(i,j)*solusiSpl[j];
			}
			solusiSpl[i]=solusiSpl[i]+this.Elmt(i,this.GetLastIdxKol());
		}
		
		//simpan solusi ke suatu array of string
		for (i=this.GetFirstIdxKol();i<=this.GetLastIdxKol()-1;i++)
		{
			
			persamaan[i] = "X" + i + " = " + Math.round(solusiSpl[i]*1000.0)/1000.0;
		}
		
	}
	
	public void solusiBanyak()
	/* Procedure solver untuk spl tipe solusi banyak */
	{
		persamaan = new String [this.GetLastIdxKol()];
		for (int i=this.GetLastIdxBrs(); i>=this.GetFirstIdxBrs(); i--) //looping dri baris terbawah sampe atas
		{
			int j=GetFirstIdxKol();
			while (this.Elmt(i, j)==0 && j<this.GetLastIdxKol()-1) //mencari idx keberapa yang tidak nol
			{
				j++;
			}
			if (this.Elmt(i, j)!=0) // kalau nemu 1 baru di proses, kalau 0 pasti baris yang semuanya 0
			{
				persamaan[j] = "X" + j + " = ";
				boolean foundParametric = false; //found variabel lain di baris itu, kalau engga, berarti lsg solved eksak
				for (int k = j+1; k < GetLastIdxKol(); k++) //looping cari bagian koef yang tidak 0
				{
					if (this.Elmt(i, k)!=0) // kalau bukan 0 kita proses, kalau 0 lewatin saja
					{
						if (solved[k]==0) //selidiki (harusnya itu fixed parametric) jadi langsung output saja
						{
							if (this.Elmt(i,k) < 0 && foundParametric)
							{
								persamaan[j] += "+";
							}
							else if (this.Elmt(i, k) > 0)
							{
								persamaan[j] += "-";
							}
							persamaan[j] += Math.abs(this.Elmt(i, k))+ "P"+ k + " ";
							foundParametric=true;
						}
						else if (solved[k]==1) /* kalau dia udh solved untuk nilai eksak */
						{
							/* kurangin hasil ujungnya, lalu dia sendiri di nol kan */
							double result = this.Elmt(i, this.GetLastIdxKol())-(this.Elmt(i,k) * solusiSpl[k]) ;
							this.SetElmt(i, this.GetLastIdxKol(), result);
							this.SetElmt(i, k, 0);
						}
						else if (solved[k]==2) //bisa disubstitusikan
						{
							for (int l = k+1; l<=this.GetLastIdxKol(); l++) //proses substitusi
							{
								double result = this.Elmt(i, l) - (this.Elmt(i, k) * this.Elmt((int)Math.round(solusiSpl[k]), l) );
								this.SetElmt(i, l, result);
							}
							this.SetElmt(i, k, 0); //set dia sendiri jadi 0
						}
						else
						{
							System.out.print("Exceptional case!\n");
						}
					}
				}
				if (!foundParametric) //eksak
				{	
					solusiSpl[j] = Math.round(this.Elmt(i, GetLastIdxKol())*1000.0)/1000.0; //solusiSpl nya berupa nilai akhirnya
					solved[j] = 1; //assign status solved dia sebagai yang eksak
				}
				else //kalau ketemu yang parametric
				{
					solved[j] = 2;
					solusiSpl[j] = i;
				}
				/* Rounding to 3 decimal places */
				this.SetElmt(i, this.GetLastIdxKol(), Math.round(this.Elmt(i, this.GetLastIdxKol())*1000.0)/1000.0 );
				
				/* Untuk bagian ujung alias konstantanya */
				this.SetElmt(i, GetLastIdxKol(), Math.round(Elmt(i,GetLastIdxKol())*1000.0)/1000.0);
				if (this.Elmt(i, this.GetLastIdxKol()) > 0 && foundParametric)
				{
					persamaan[j] += "+" + this.Elmt(i, this.GetLastIdxKol());
				}
				else if (this.Elmt(i, this.GetLastIdxKol()) > 0 && !foundParametric)
				{
					persamaan[j] += this.Elmt(i, this.GetLastIdxKol());
				}
				else if (this.Elmt(i, this.GetLastIdxKol()) < 0)
				{
					persamaan[j] += this.Elmt(i, this.GetLastIdxKol());
				}
			}
		}
		/* Untuk yang absolute parametric */
		for (int j=this.GetFirstIdxKol(); j<this.GetLastIdxKol(); j++)
		{
			if (solved[j]==0)
			{
				persamaan[j] = "X" + j + " = " + "P" + j;
			}
		}
	}
}
