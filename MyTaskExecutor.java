package executors;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.testng.TestNG;

import simpletest.xmlUnitTest;

public class MyTaskExecutor {
	ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
	IExecutable myTask;
	volatile boolean isStopIssued;

	public MyTaskExecutor(IExecutable myTask$) {
		myTask = myTask$;
	}

	public void startExecutionAt(int targetHour, int targetMin, int targetSec) {
		Runnable taskWrapper = () -> {
			myTask.execute();
			// startExecutionAt(targetHour, targetMin, targetSec);
		};

		long delay = computeNextDelay(targetHour, targetMin, targetSec);
		executorService.scheduleAtFixedRate(taskWrapper, delay, 15, TimeUnit.SECONDS);
	}

	private long computeNextDelay(int targetHour, int targetMin, int targetSec) {
		LocalDateTime localNow = LocalDateTime.now();
		ZoneId currentZone = ZoneId.systemDefault();
		ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
		ZonedDateTime zonedNextTarget = zonedNow.withHour(targetHour).withMinute(targetMin).withSecond(targetSec);
		if (zonedNow.compareTo(zonedNextTarget) > 0)
			zonedNextTarget = zonedNextTarget.plusDays(1);

		Duration duration = Duration.between(zonedNow, zonedNextTarget);
		return duration.getSeconds();
	}

	public void stop() {
		executorService.shutdown();
		try {
			executorService.awaitTermination(1, TimeUnit.DAYS);
		} catch (InterruptedException ex) {
		}
	}

	public static void main(String[] args) {
		MyTaskExecutor executor = new MyTaskExecutor( () -> {
			TestNG testng = new TestNG();
			testng.setTestClasses(new Class[] { xmlUnitTest.class });
			testng.run();
		});

		executor.startExecutionAt(14, 7, 40);
		/*executor.stop();*/
	}
}