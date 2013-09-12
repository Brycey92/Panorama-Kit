/* 
 * This code isn't copyrighted. Do what you want with it. :) 
 */
package panoramakit.engine.task;

import panoramakit.mod.PanoramaKit;

/**
 * Task
 * 
 * @author dayanto
 */
public abstract class Task
{
	private boolean silent = false;
	
	/**
	 * Initalizes the task at the moment it ends up first in the tasklist. This allows things to be
	 * defined at the moment of execution.
	 */
	public void init()
	{}
	
	/**
	 * Performs the task. Until the task has finished, this will repeat once every frame.
	 */
	public abstract void perform();
	
	/**
	 * Cleans up after the task has finished running if necessary.
	 */
	public void finish()
	{}
	
	/**
	 * Politely asks the task to abort as quickly as possible, performing any cleanup that has to be
	 * done. While this will usually has an immediate effect, threaded tasks might take some time to
	 * come to a halt and there is no guarantee that a task will be able to stop at all before
	 * finishing.
	 */
	public void stop()
	{}
	
	// current states of the task
	private boolean completed = false;
	private boolean stopped = false;
	
	/**
	 * Once the task has finished running, this method must be called to signal that the task should
	 * be removed. (the task will perpetually run its perfom method until this has been done)
	 */
	public void setCompleted()
	{
		completed = true;
	}
	
	/**
	 * Once any cleanup has been performed after stopping a task, this has to be called to signal
	 * that the task is done. If this is never done, the task manager will keep waiting forever.
	 */
	public void setStopped()
	{
		stopped = true;
	}
	
	/**
	 * Whether this task has been completed.
	 */
	public final boolean hasCompleted()
	{
		return completed;
	}
	
	/**
	 * If this task has been aborted, returns whether it has done all cleanup and come to a halt.
	 */
	public final boolean hasStopped()
	{
		return stopped;
	}
	
	/**
	 * Gives how far the task has progressed as a proportion.
	 */
	public double getProgress()
	{
		return 0;
	}
	
	/**
	 * Prevents this task from sending any messages to the chat.
	 */
	public void setSilent()
	{
		silent = true;
	}
	
	/**
	 * Attempts to send a message to the chat, but fails if this task has been silenced.
	 */
	public void printChat(String msg, Object... params) {
		if(!silent)
		{
			PanoramaKit.instance.printChat(msg, params);
		}
	}
	
}