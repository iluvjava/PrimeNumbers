import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;
import java.util.TreeSet;

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
		TreeSet<Integer> s = new TreeSet<Integer>();
		long  time = System.currentTimeMillis();
		int counting =0;
		for(int i =2;i<=999;i++)
		{
			if(isProbablePrime(i))
			{
				counting++;
				s.add(i);
			}
		}
		time = System.currentTimeMillis()-time;
		
		System.out.println("There are: "+counting);
		
		System.out.println("It takes: "+time);
		
		FilterPrimes fp = new FilterPrimes(s); 
		fp.filter();
		
		System.out.print("Deterministic test shows: "+fp.G_notprimes.size());
		
		
			
		
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
	 * @param arg
	 * @return
	 */
	public static boolean isProbablePrime(int arg) 
	{
		if(arg<3)return true;
		// fermat little theorem
		Random rd = new Random();
		for(int i =0; i <50; i++)
		{
			int r = rd.nextInt(arg-2)+2;
			//print("random number: "+r);
			if
			(GCD(arg,r)!=1||!fermatTest(arg,r))
			{
				//print(arg+" is not a prime");
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
	public static boolean isDefinitelyAPrime(int a)
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


	public static boolean fermatTest(int target, int witness)
	{
		// pass test
		if(powerMod(witness,target-1,target)==1)
		{
			return true;
		}
		else
		{return false;}
		
	}
	
	/**
	 * gives the bit value at a position of a long number. 
	 * 
	 */
	public static byte bitat(byte posi,long target)
	{
		target = target>>(posi-1);
		
		target = target<<(63);
		
		target = target>>(63);
		
		return (byte) target;
	}
	
	
	private static void print(String s)
	{
		System.out.println(s);
	}
	
	
	public static long powerMod(long factor , int power, int modu)
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
	
	public static class filterProbablePrimes
	{
		
		
		
	}
		
		
		
}

