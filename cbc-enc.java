import java.util.LinkedList;



public class Main
{
	public static void main(String[] args) throws Exception
	{
		String test = "1234567890abcdef";
		String key_file;
		String input_file;
		String iv_file;
		
		
		key_file = ctfuncs.key_file(args);
		input_file = ctfuncs.input_file(args);
		iv_file = ctfuncs.iv_file(args);
		
		System.out.println(key_file);
		System.out.println(input_file);

		
		//checks to see if an iv file existed
		// if it doesn't, we generate a new iv
		if ( iv_file.equals( "-1" ) )
		{
			//Make IV
			LinkedList<Integer> IVrand = new LinkedList<Integer>();
			ctfuncs.IVgen(IVrand);
			
			//test IV
			for(int i : IVrand)
			{
				//System.out.print(i);
			}
			
			//System.out.println();
		}
		
		System.out.println(iv_file);
		
		//encrypt
		//getBytes returns an array of bytes
		byte[] ct = ctfuncs.encrypt_data(test.getBytes());
		//System.out.println(new String(ct));
		
		

		
		ctfuncs.output_file(args, "output");
	}
}
