/* 
 * This code is in the public domain. You are free to do whatever you want with it. :)
 */
package panoramakit.engine.task.threadedtasks;

import java.io.File;
import panoramakit.engine.task.ThreadedTask;
import panoramakit.engine.tools.Zipper;

/** 
 * @author dayanto
 */
public class ZipTask extends ThreadedTask
{
	private File folder;
	private File zipFile;
	
	public ZipTask(File folderToZip, File zipDest)
	{
		folder = folderToZip;
		zipFile = zipDest;
	}
	
	@Override
	public void performThreaded() throws Exception
	{
		chat.print("Zipping...");
		Zipper.zipFolder(folder.toPath(), zipFile.toPath());
		chat.print("Saved as " + zipFile.getName());
	}
}