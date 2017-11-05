import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.PrimitiveIterator.OfInt;
import java.util.PrimitiveIterator.OfLong;
import java.util.Random;
import java.util.Stack;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

/**
 * 
 * use probalistic prime distinctive method for all primitive long data type. 
 * 
 * @author Aultistic lycan
 * 	
 * 
 */
public class FuckPrimeNumbers 
{
	public static void main(String[] args) throws Exception 
	{
//		TreeSet<Integer> s = new TreeSet<Integer>();
//		long  time = System.currentTimeMillis();
//		int counting =0;
//		for(int i =2;i<=999;i++)
//		{
//			if(isProbablePrime(i))
//			{
//				counting++;
//				s.add(i);
//			}
//		}
//		time = System.currentTimeMillis()-time;
//		
//		System.out.println("There are: "+counting);
//		
//		System.out.println("It takes: "+time);
//		
//		FilterPrimes fp = new FilterPrimes(s); 
//		fp.filter();
//		
//		System.out.print("Deterministic test shows: "+fp.G_notprimes.size());
//		
		
		FuckPrimeNumbers.FilterPrimes_SpeedEdition.main(args);
		
	}
	
	
	private static long GCD_(long a, long b)
	{
		if(a==0 | b==0)
		{
			if(a==0)return b;
			else{return a;}
		}
		else
		{
			a = a%b;
			return GCD_(b,a);
		}
	}
	
	/**
	 * Greatest common divisor of two long numbers. 
	 * @param a
	 * @param b
	 * @return	The createst common divisor of a and b. 
	 * 
	 */
	public static long GCD(long a, long b)
	{
		if(a>b){return GCD_(a,b);}
		else{return GCD_(b,a);}
	}
	
	/**
	 * 
	 * Determine the primality of a number using fermat test; 
	 * and gcd. 
	 * 
	 * if(input>than 2^32), return false to prevent overflow. 
	 * @param arg
	 * @return
	 */
	public static boolean isProbablePrime(long arg) 
	{
		
		if(arg>=0xFFFFFFFFL){return false;}
		
		if(arg<3)return true;
		// fermat little theorem
		Random rd = new Random();
		
		
			int randombound = (int) (arg-2);
			
		if(randombound<0)
		{
			randombound = 0x0effffff;
		}
		
		
		for(int i =0; i <100; i++)
		{
			int r = rd.nextInt(randombound)+2;
			
			if
			(GCD(arg,r)!=1||!fermatTest(arg,r))
			{
				
				return false; // failed...
			}
			
		}
		return true;
		
	}
	
	/**
	 * determine primes by trial methods, single threaded. 
	 * 
	 * 	complexity: omega(n)=sqrt(n)
	 * @param a
	 * @return
	 */
	public static boolean isDefinitelyAPrime(long a)
	{	
		if(a==2)return true;
		if(a%2==0||a==1)return false;
		
		int lowbound = (int)Math.sqrt(a)+1;
		int factor =3;
		while(factor<=lowbound)
		{
			if(a%factor==0)
			{
				print("This is a strong liar: "+a);
				return false;
			}
			factor+=2;
		}
		return true;
	}
	
	


	public static boolean fermatTest(long target, long witness)
	{
		// pass test
		if(powerMod(witness,target-1,target)==1)
		{
			return true;
		}
		else
		{return false;}
		
	}
	
	
	
	private static void print(String s)
	{
		System.out.println(s);
	}
	
	public static long intshifttolong(int a)
	{
		long result = (long)a;
		if(a<0)
		{
			result = 0xffffffff00000000L^result;
			return result;
		}
		return result;
	}
	
	
	public static long powerMod(long factor , long power, long modu)
	{
		
		long result = 1;
		while(power>=1)
		{
			if(power%2==1)
			{	
				
				result =(result*factor)%modu;
			}
			power/=2;
	
			factor =(factor*factor)%modu;
			
		}
		//print("("+factor+"^"+power+")"+"="+result);
		return result;
	}
	
	
	
	
	
	/**
	 * return the number that is overflowed in turn of long
	 * 
	 * a*b = long.max*c +remainder ; c is the number got returned. 
	 */
//	public static long getoverflow(long a, long b)
//	{
//		if(a*b>0)
//		return 0; 
//		else
//		{
//			
//		}
//	}
//	
	public static String getBinary(long a)
	{
//		boolean[] result = new boolean[64];
//		boolean temp = false;
//		 for(int i =0; i!=64; i++)
//		 {
//			 if(a%2==1)
//			 result[i]=true;
//			 a=a>>1;
//			if(a==0)break;
//		 }
//		 System.out.println(Arrays.toString(result));
//		return result;
		
		
		StringBuilder sb = new StringBuilder();
		
		for(byte i =63; i>=0; i--)
		{
		if(isOneInBIN(a,i))
		{
			sb.append("1");
		}
		else
		{
			sb.append("0");
		}
		}
		return sb.toString();
	}
	
	/**
	 * At position of posi at a is one, we will return true;
	 * 0 is the first digit in binary. 
	 * @param a
	 * @param posi
	 * @return
	 */
	public static boolean isOneInBIN(long a, byte posi)
	{
		if((a=a>>posi)%2==1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	/**
	 * a*b = (2^63)n+R
	 * n is the overflow; 
	 * How much is overflowed from long?
	 * 
	 * The binary extension of the product. beyond the 63th digit
	 * the 64 digit is included in the overflow. 
	 * 
	 * return both the overflow and under flow value. 
	 * @param a
	 * @param b
	 * @return
	 */
	public static long getOverFlow(long a, long b)
	{
		if(a<0||b<0)
		{
			//>> operator will not work;
			throw new ArithmeticException();
			
		}
		long overflow = 0;
		long underflow = 0;
		for(byte i =0; i<64;i++)
		{
			if(isOneInBIN(a,  i))
			{
				long shift = b<<i;
				overflow+= b>>>(63-i);
				
				if(shift<0)shift^=0x8000000000000000L;
				if((underflow+=shift)<0)
				{
					underflow^=0x8000000000000000L;
					overflow++;
				}

				
			}
			
		}
		return overflow;
		
	}
	
	/**
	 * A method that can return (a*b)even if over a*b 
	 * overflow long; 
	 * 
	 * Parse the overflow unmber into lots of 
	 * @param a
	 * @param b
	 * @param m
	 * @return
	 */
	public static long multiplicationModulate(long a, long b, long m)
	{
		long overflow = getOverFlow( a,  b);
		
		if(overflow ==0)
		{
			return (a*b)%m;
		}
		else
		{	//a*b = 
			//overflow*(max+1)
			//Overflow*Max+overflow=?
			
			long underflow = a*b;
			if(underflow<0)
			{
				underflow = underflow^0x8000000000000000L;
			}
			
			long partresult = 
			return (partresult+underflow%m)%m;
		}
	}
	
	
	
	
	
	//private static final long twopow62=
	/**
	 * This class will get a set of numbers and return 
	 * Numbers that are primes in the set, using 
	 * trial division method for primality testing. 
	 * 
	 * 
	 * @author autistic lycan
	 *
	 */
	public static class FilterPrimes implements Runnable
	{
		public static final int CPUCORES = Runtime.getRuntime().availableProcessors();
		public volatile TreeSet<Integer> G_allnumbers;
		public volatile Stack<Integer> G_targetstack = new Stack<Integer>();
		public volatile TreeSet<Integer> G_notprimes = new TreeSet<Integer>();
		
		
		/**
		 * 
		 * Create an instance and calculate everything 
		 * @param arg
		 */
		private FilterPrimes(TreeSet<Integer> arg)
		{
			G_allnumbers = arg;
			for(Integer e : G_allnumbers)
			{
				this.G_targetstack.add(e);
			}
			
		}
		
		/**
		 * return number from the stack of the pool of numbers. 
		 * 
		 */
		public synchronized Integer getNum()
		{
			if(G_targetstack.size()!=0)
			{
				return this.G_targetstack.pop();
			}
			else
			{
				return null;
			}
		}
		
		
		public synchronized void removeNotPrime(Integer arg)
		{
			
			this.G_allnumbers.remove(arg);
		}
		
		public synchronized ArrayList<Integer> getNum(int n)
		{	
			ArrayList<Integer> alist = new ArrayList<Integer>();
			
			if(G_targetstack.size()!=0)
			{
				int c=0;
				while(G_targetstack.size()>0&&++c<n)
				{
					
					alist.add(this.G_targetstack.pop());
					
				}
				
				return alist;
			}
			else
			{
				return null;
			}
		}
		/**
		 * 
		 * Create several threads and implment the method
		 * to filer out number, it uses the method in FuckPrimeNumbers class
		 * @throws InterruptedException 
		 */
		public void filter() throws InterruptedException
		{
			Thread[] threadpool = new Thread[CPUCORES];
			for(int i =0; i<CPUCORES;i++)
			{
				threadpool[i]= new Thread
						(
							this	
						);
				threadpool[i].start();
			}
			for(Thread t : threadpool)
			{
				t.join();
			}
			
		}

		
		public void run() 
		{
			while (this.G_targetstack.size()>0)
			{
				ArrayList<Integer> temp =this.getNum(1000);
				for(Integer i : temp)
				{
					if(!FuckPrimeNumbers.isDefinitelyAPrime(i))
						{
						this.removeNotPrime(i);;
						}
				}
			}
			
			
			
		}
	}
	
	
	/**
	 * A generic method that can save the object onto a disk 
	 * This is a generic method, has to specified wehn invoked. 
	 * @author victo
	 *
	 * @param <T>
	 */
	public static class ChacheObject<T> implements Serializable
	{
		public T G_something;
		public File G_f;
		public String G_address;
		public String G_Name;
		/**
		 * receive an object, a file name, and a string for directory; 
		 * @param obj
		 * @param Name
		 * @param Drectory
		 * @throws IOException 
		 */
		public ChacheObject(T obj, String Name,String Directory) throws IOException
		{
			
			this.G_something= obj;
			this.G_f = new File("E:/MyLoveForPrimes/ChacheObject.txt");
			
			if(!this.G_f.exists())
			{
				if(!this.G_f.getParentFile().exists())
				{
					this.G_f.getParentFile().mkdirs();
				}
				else if(!this.G_f.exists())
				{
					this.G_f.createNewFile();
				}
			
			}
			
		}
		
		
		/**
		 * Save the this object, the class object to the disk
		 * and return true is it is successful; else false;
		 * 
		 * @return
		 */
		public boolean save()
		{
			try 
			{
				
				ObjectOutputStream oos =
				new ObjectOutputStream(new FileOutputStream(this.G_f));
				oos.writeObject(this);
				oos.close();
				return true;
				
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				return false;
			}
			
			
		}
		
		/**
		 * This is a statie method that receive a file 
		 * object and return the object cast to the specific 
		 * type 
		 * @param arg
		 * @return
		 */
		public static <T> T recover(File arg)
		{
			
			File f = arg;
			try 
			{
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f));
				
				return (T)ois.readObject();
				
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
				return null;
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
				return null;
			}
			
		}
		/**
		 * Recover a specified type of object from a string; 
		 * an address. 
		 * 
		 */
		public static <T> T rocover(String address)
		{
			return recover(new File(address));
		}
		
		public void destroy()
		{
			this.G_something = null; 
		}
		
		
	}
	
	
	public static class PrimeLibrary
	{
		private int[] G_primelist;
		
		private PrimeLibrary()
		{
			System.out.println("Instanciating prime library. ");
			List<Integer> something = new ArrayList<Integer>();
			IntStream st=IntStream.range(2, 65536).parallel()
			.filter(l->isProbablePrime(l)).filter(l->FuckPrimeNumbers.isDefinitelyAPrime(l)).sorted();
			OfInt itr = st.iterator();
			
			while(itr.hasNext())
			{
				something.add(itr.next());
			}
			this.G_primelist = new int[something.size()];
			for(int i = 0; i <something.size();i++)
			{
				this.G_primelist[i] = something.get(i);
			}
			something = null;
			System.out.println("Instance created, we have a reference library now. ");
		}
		/**
		 * Use a preestabished prime library to speed up the definite prime process.
		 * @param a
		 * @return
		 */
		public boolean isDefinitelyAPrime(long a)
		{	
			if(a==2)return true;
			if(a%2==0||a==1)return false;
			
			int lowbound = (int)Math.sqrt(a);
			for(int i : this.G_primelist)
			{
				if(a%i==0)return false;
				if(i>=lowbound)break;
			}
			return true;
		}
		
		public static PrimeLibrary getAnInstance()
		{
			return new PrimeLibrary();
		}
		
		
	}
		
	
	/**
	 * Let's quit the classical programming and look for something that is new, ok? 
	 * We want to use some stream api! 
	 * @author victo
	 *
	 */
	public static class FilterPrimes_SpeedEdition
	{
		public static void main(String[] args) throws FileNotFoundException
		{
			
			
			PrimeLibrary pl = PrimeLibrary.getAnInstance();
			
			
			
			//There is a non static method in the list class that will return the 
			// the collection as a stream. 
			IntStream st=IntStream.range(2,1000000000).parallel()
					
					.filter(l->isProbablePrime(l)).filter(l->pl.isDefinitelyAPrime(l)).sorted();
			
			// There is a non staticc method in the stream class that will 
			// accept a collectors object and return the stream as a list, 
			// the collectior object is created with a static method in 
			// the class collector. 
			int[] itr = st.toArray();
			
			st=null;
			
			PrintStream ps = new PrintStream(new File("E:/WTFsomanyprimes.txt"));
			
			for(long temp : itr)
			{
				System.out.println(temp);
				ps.print(temp+" ");
				
			}
			System.out.print("number of primes found: "+itr.length);
		}
		
		
		
	}
}


