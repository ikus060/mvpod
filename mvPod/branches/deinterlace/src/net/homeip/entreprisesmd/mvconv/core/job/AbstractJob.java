package net.homeip.entreprisesmd.mvconv.core.job;

/**
 * This class are a default implementation of the Job interface.
 * 
 * @author patapouf
 * 
 */
public abstract class AbstractJob implements Job {

	/**
	 * list of observer.
	 */
	private JobObserver[] observers = new JobObserver[0];

	/**
	 * percent completed.
	 */
	private int percent;

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.Job#addJobObserver(net.homeip.entreprisesmd.mvconv.core.JobObserver)
	 */
	public void addJobObserver(JobObserver observer) {
		int index = 0;
		while (index < this.observers.length
				&& this.observers[index] != observer) {
			index++;
		}

		if (index < this.observers.length) {
			return;
		}

		JobObserver[] newObservers = new JobObserver[this.observers.length + 1];
		System.arraycopy(this.observers, 0, newObservers, 0,
				this.observers.length);
		newObservers[this.observers.length] = observer;
		this.observers = newObservers;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.Job#getPercentCompleted()
	 */
	public int getPercentCompleted() {
		return this.percent;
	}

	/**
	 * Use by sub-class to notify Observer.
	 */
	protected void notifyObservers() {
		for (int index = 0; index < this.observers.length; index++) {
			this.observers[index].jobProgressHasChanged(this);
		}
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.Job#removeJobObserver(net.homeip.entreprisesmd.mvconv.core.JobObserver)
	 */
	public void removeJobObserver(JobObserver observer) {

		int index = 0;
		while (index < this.observers.length
				&& this.observers[index] != observer) {
			index++;
		}

		if (index >= this.observers.length) {
			return;
		}

		JobObserver[] newObservers = new JobObserver[this.observers.length - 1];
		System.arraycopy(this.observers, 0, newObservers, 0, index);
		System.arraycopy(this.observers, index + 1, newObservers, index,
				this.observers.length - index - 1);
		this.observers = newObservers;

	}

	/**
	 * Use by sub class to set the percent completed and notify observers.
	 * 
	 * @param percent
	 *            the new percent value.
	 */
	protected void setPercentCompleted(int percent) {
		this.percent = percent;
		notifyObservers();
	}

}
