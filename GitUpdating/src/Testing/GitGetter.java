package Testing;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

public class GitGetter {
	private static ArrayList<String> urlList;
	private static ArrayList<String> nameList;
	private static Process pr;
	private static Runtime rt = Runtime.getRuntime();

	
	public static void main(String[] args) throws IOException, InterruptedException {
		urlList = new ArrayList<String>();
		nameList = new ArrayList<String>();
		doThings();
	}
	
	public static void doThings() throws IOException, InterruptedException {
		File file = new File("src/resources/Test.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		System.out.println("Begin");
		long startTime = System.currentTimeMillis();
		while(line != null) {
			goGetUrls(line);
			line = br.readLine();
		}
		for(int i = 0; i < urlList.size(); i++) {
			String url = urlList.get(i);
			String name = nameList.get(i);
			addFile(url, name, i);
			
		}
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("----------------------------------------------------------------------------");
		System.out.println("Finished   :)");
		long endTime = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Total Time: " + totalTime);
		
	}


	private static void goGetUrls(String line) throws IOException {
		String urlName = "http://github.corp.rlws.com/RiceLake-Engineering/";
		String nameMaker = "\"name\": ";
		if(line.contains(nameMaker)) {
			line = line.substring(15, line.length()-2);
			urlList.add(urlName + line);
			
			nameList.add(line);
		}
	}
	
	
	private static void addFile(String url, String name, int i) throws IOException, InterruptedException {
				String[] command = { "cmd", };
				Process p;
				p = Runtime.getRuntime().exec(command);
				new Thread(new SyncPipe(p.getErrorStream(), System.err)).start();
			    new Thread(new SyncPipe(p.getInputStream(), System.out)).start();
			    PrintWriter stdin = new PrintWriter(p.getOutputStream());
			    
			    if (i < 500) {
				    stdin.println("cd c:\\Users\\marbri\\desktop\\gitTestFolder1");
			    }
			    else if (i >= 500 && i < 1000) {
			    	stdin.println("cd c:\\Users\\marbri\\desktop\\gitTestFolder2");
			    }
			    else if (i >= 1000 && i < 1500) {
			    	stdin.println("cd c:\\Users\\marbri\\desktop\\gitTestFolder3");
			    }
			    else if (i >= 1500 && i < 2000) {
			    	stdin.println("cd c:\\Users\\marbri\\desktop\\gitTestFolder4");
			    }
			    else if (i >= 2000 && i < 2500) {
			    	stdin.println("cd c:\\Users\\marbri\\desktop\\gitTestFolder5");
			    }
			    else if (i >= 2500 && i < 3000) {
			    	stdin.println("cd c:\\Users\\marbri\\desktop\\gitTestFolder6");
			    }

			    stdin.println("git clone " + url);
			    stdin.println("cd " + name);
			    
////////////////////////////////////////////////////////////////////////////////////////
			    stdin.println("xcopy /y c:\\Users\\marbri\\desktop\\.gitattributes");			   
////////////////////////////////////////////////////////////////////////////////////////
			    stdin.println("git add .");
			    stdin.println("git commit -m \"Updated .gitattributes\"");
			    stdin.println("git push");
			    
			    stdin.close();
			    int returnCode = p.waitFor();
	}

	static class SyncPipe implements Runnable
	{
	public SyncPipe(InputStream istrm, OutputStream ostrm) {
	      istrm_ = istrm;
	      ostrm_ = ostrm;
	  }
	  public void run() {
	      try
	      {
	          final byte[] buffer = new byte[1024];
	          for (int length = 0; (length = istrm_.read(buffer)) != -1; )
	          {
	              ostrm_.write(buffer, 0, length);
	          }
	      }
	      catch (Exception e)
	      {
	          e.printStackTrace();
	      }
	  }
	  private final OutputStream ostrm_;
	  private final InputStream istrm_;
	}
}
