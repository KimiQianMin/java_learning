package parallel.forkjoin;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.stream.LongStream;

public class TestForkJoinPool {

	public static void main(String[] args) {
		
		System.out.println("availableProcessors >>> " + Runtime.getRuntime().availableProcessors());

		long total = 9000000000L;
		Long sum = 0L;

		Instant startTime = Instant.now();

		// I
//		for (long i = 0L; i <= total; i++) {
//			sum += i;
//		}

		// II
		ForkJoinPool pool = new ForkJoinPool(6);
		ForkJoinTask<Long> task = new ForkJoinSumCalculate(0L, total);
		sum = pool.invoke(task);

		// III
//		sum = LongStream.rangeClosed(0L, total)
//				  		.parallel()
//				  		.reduce(0L, Long::sum);
		
		System.out.println(sum);

		Instant endTime = Instant.now();

		System.out.println("startTime >>> " + startTime.toString());
		System.out.println("endTime >>> " + endTime.toString());
		System.out.println("cost time is >>> " + Duration.between(startTime, endTime).toMillis());

	}

}

class ForkJoinSumCalculate extends RecursiveTask<Long> {

	private static final long serialVersionUID = 489716414005367148L;

	private long start;
	private long end;

	private static final long THRESHOLD = 10000L;

	public ForkJoinSumCalculate(long start, long end) {
		this.start = start;
		this.end = end;
	}

	@Override
	protected Long compute() {
		long length = end - start;

		if (length <= THRESHOLD) {
			long sum = 0L;

			for (long i = start; i <= end; i++) {
				sum += i;
			}

			return sum;

		} else {

			long middle = (start + end) / 2;

			ForkJoinSumCalculate left = new ForkJoinSumCalculate(start, middle);
			left.fork();

			ForkJoinSumCalculate right = new ForkJoinSumCalculate(middle + 1, end);
			right.fork();

			return left.join() + right.join();
		}

	}

}