package live.moku.mqexercise;

import live.moku.mqexercise.callback.Caller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MqExerciseApplicationTests {

	@Autowired
	private Caller caller;

	@Test
	void testCallback() throws InterruptedException {
		caller.calculate();
		Thread.sleep(10000);
	}

}
