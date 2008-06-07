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
		while (index < observers.length && observers[index] != observer) {
			index++;
		}

		if (index < observers.length) {
			return;
		}

		JobObserver[] newObservers = new JobObserver[observers.length + 1];
		System.arraycopy(observers, 0, newObservers, 0, observers.length);
		newObservers[observers.length] = observer;
		observers = newObservers;
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.Job#getPercentCompleted()
	 */
	public int getPercentCompleted() {
		return percent;
	}

	/**
	 * Use by sub-class to notify Observer.
	 */
	protected void notifyObservers() {
		for (int index = 0; index < observers.length; index++) {
			observers[index].jobProgressHasChanged(this);
		}
	}

	/**
	 * @see net.homeip.entreprisesmd.mvconv.core.Job#removeJobObserver(net.homeip.entreprisesmd.mvconv.core.JobObserver)
	 */
	public void removeJobObserver(JobObserver observer) {

		int index = 0;
		while (index < observers.length && observers[index] != observer) {
			index++;
		}

		if (index >= observers.length) {
			return;
		}

		JobObserver[] newObservers = new JobObserver[observers.length - 1];
		System.arraycopy(observers, 0, newObservers, 0, index);
		System.arraycopy(observers, index + 1, newObservers, index,
				observers.length - index - 1);
		observers = newObservers;

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
