package app.verticle.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import app.verticle.MyFirstVerticle;
import io.vertx.core.Vertx;
import io.vertx.core.VertxOptions;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;

/**
 * unit test application 
 * @author ramkumarsundarajan
 *
 */
@RunWith(VertxUnitRunner.class)
public class MyVerticleTest {

	private Vertx vertx;
	
	@Before
	public void setup(TestContext context){
		VertxOptions vertxOptions = new VertxOptions();
		/* TODO: why this has to be set ? */
	    vertxOptions.setMaxWorkerExecuteTime(2147483647); // maximum number for 2^31 - 1
	    vertxOptions.setMaxEventLoopExecuteTime(Long.MAX_VALUE);
		vertx = Vertx.vertx(vertxOptions);
		vertx.deployVerticle(MyFirstVerticle.class.getName(),context.asyncAssertSuccess());
	}
	
	@After
	public void tearDown(TestContext context){
		
		vertx.close(context.asyncAssertSuccess());
	}
	
	@Test
	public void testApplication(TestContext context){
		final Async async = context.async();
		vertx.createHttpClient().getNow(8080, "localhost","/",response ->{
			response.handler(body->{
				//context.assertTrue(body.)
				context.assertTrue(body.toString().contains("Hello"));
				async.complete();
			});
		});
	}
}
