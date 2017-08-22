/*
 *	- Author: Marcus Brion
 *	- Date Completed: 8/21/2017
 *	- Project Overview: This project is meant to grab as many irite repos in GitHub as possible
 *		and update the .gitattributes files in each one. To run this project, you will need
 *		git 2.13.3 or greater and you will need to change some file directory information
 *		in the actual code below. This project will clone all irite repos specified in a
 *		text document included in the resources folder and then copy in a new .gitattributes
 *		file and then push that repo back to git. This project can take well over 4 hours
 *		to complete so make sure to test on a junk/test repo(s) before starting the whole thing.
 */
 
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

public class GitUpdater {
	private static ArrayList<String> urlList;
	private static ArrayList<String> nameList;
	private static Process pr;
	private static Runtime rt = Runtime.getRuntime();

	
	public static void main(String[] args) throws IOException, InterruptedException {
		//creates new array Lists and starts the process
		urlList = new ArrayList<String>();
		nameList = new ArrayList<String>();
		doThings();
	}
	
	
	/*
	 * doThings() is the main method where most action takes place
	 * 	It begins the process of grabbing the urls as well as copying the
	 * 	new file into the repos and then pushing them back
	 */
	public static void doThings() throws IOException, InterruptedException {
		File file = new File("src/resources/Test.txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = br.readLine();
		System.out.println("Begin");
		long startTime = System.currentTimeMillis();
		
		//This loop will walk through the Test.txt document (document with all info on repos from 
		// the command line calls like those in Final.PNG) and grab the appropriate URLS
		while(line != null) {
			goGetUrls(line);
			line = br.readLine();
		}
		
		//This loop will use the urls and names of the repos and then add the file to the repo
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


	/*
	 * goGetUrls(...) is responisble for grabbing the appropriate URLs from command line
	 * 	call returns. You can see an example of one of these command line calls in Final.PNG.
	 * 	If it finds a name, it will grab the appropriate URL and name and add those to their
	 * 	Array List
	 */
	private static void goGetUrls(String line) throws IOException {
		String urlName = "http://github.corp.rlws.com/RiceLake-Engineering/";
		String nameMaker = "\"name\": ";
		if(line.contains(nameMaker)) {
			line = line.substring(15, line.length()-2);
			urlList.add(urlName + line);
			
			nameList.add(line);
		}
	}
	
	
	/*
	 * addFile(...) is responsible for cloning the repos into one of 6 folders.
	 * 	**There are 6 folders due to folder size and depth requirements**
	 * 	It also copys the .gitattributes file into that repo and pushes it back
	 * 	to GitHub
	 */
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
			    stdin.println("git commit -m \"Updated .gitattributes for the 2nd time.\"");
			    stdin.println("git push");
			    
			    stdin.close();
			    int returnCode = p.waitFor();
	}

	/*
	 * SyncPipe is a class that allows for another thread to be started up inside of an already running
	 * 	thread. This helps to make command line calls and for the program to wait for results before
	 * 	continuing. 
	 */
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
