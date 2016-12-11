package app.verticle;

import java.util.LinkedHashMap;
import java.util.Map;

import app.verticle.modal.Whisky;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonArray;
import io.vertx.ext.jdbc.JDBCClient;
import io.vertx.ext.sql.SQLConnection;
import io.vertx.ext.sql.UpdateResult;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * my first verticle.
 * @author ramkumarsundarajan
 *
 */
public class MyFirstVerticle extends AbstractVerticle{

	private Map<Integer, Whisky> products = new LinkedHashMap<>();

	private JDBCClient jdbcClient;

	/**
	 * 
	 */
	public void start(Future<Void> future){

		jdbcClient = JDBCClient.createShared(vertx, config(),"Collection-Database");


		startBackend(
				(result) -> createSomeData(result,(nothing) -> startWebApp((http) -> completeStartup(http, future)), future), future);

		//startBackend((result) -> createSomeData(result, (nothing)-> startWebApp((http) -> completeStartup(http, future)), future), future);

		
	}

	/**
	 * 
	 * @param http
	 * @param fut
	 */
	private void completeStartup(AsyncResult<HttpServer> http, Future<Void> fut) {
		if (http.succeeded()) {
			fut.complete();
		} else {
			fut.fail(http.cause());
		}
	}


	/**
	 * 
	 * @param result
	 * @param next
	 * @param fut
	 */
	private void createSomeData(AsyncResult<SQLConnection> result,Handler<AsyncResult<Void>> next, Future<Void> fut) {
		if (result.failed()) {
			fut.fail(result.cause());
		} else {
			SQLConnection connection = result.result();
			connection.execute(
					"CREATE TABLE IF NOT EXISTS Whisky (id INTEGER IDENTITY, name varchar(100), " +
							"origin varchar(100))",
							ar -> {
								if (ar.failed()) {
									fut.fail(ar.cause());
									connection.close();
									return;
								}
								connection.query("SELECT * FROM Whisky", select -> {
									if (select.failed()) {
										fut.fail(ar.cause());
										connection.close();
										return;
									}
									if (select.result().getNumRows() == 0) {
										insert(
												new Whisky("Bowmore 15 Years Laimrig", "Scotland, Islay"),
												connection,
												(v) -> insert(new Whisky("Talisker 57° North", "Scotland, Island"),
														connection,
														(r) -> {
															next.handle(Future.<Void>succeededFuture());
															connection.close();
														}));                                                    
									} else {
										next.handle(Future.<Void>succeededFuture());
										connection.close();
									}
								});
							});
		}
	}

	/**
	 * start web app. 
	 * @param future
	 */
	private void startWebApp(Handler<AsyncResult<HttpServer>> next) {
		Router router = Router.router(vertx);
		router.route("/").handler(routingContext ->{

			HttpServerResponse response = routingContext.response();
			response.putHeader("content-type", "text/html")
			.end("<h1>Hello from my first Vert.x 3 application</h1>");
		});

		router.get("/api/whiskies").handler(this::getAll);
		router.route("/assets/*").handler(StaticHandler.create("assets"));
		router.route("/api/whiskies*").handler(BodyHandler.create());
		router.post("/api/whiskies").handler(this::addOne);
		router.delete("/api/whiskies/:id").handler(this::deleteOne);

		HttpServer httpServer = vertx.createHttpServer();
		httpServer.requestHandler(router::accept);
		httpServer.listen(
				// Retrieve the port from the configuration,default to 8080.
				config().getInteger("http.port", 8080),next::handle);
	}


	/**
	 * 
	 * @param next
	 * @param fut
	 */
	private void startBackend(Handler<AsyncResult<SQLConnection>> next, Future<Void> fut) {
		jdbcClient.getConnection(ar -> {
			if (ar.failed()) {
				fut.fail(ar.cause());
			} else {
				next.handle(Future.succeededFuture(ar.result()));
			}
		});
	}


	/**
	 * 
	 */
	@Deprecated
	private void createSomeData() {
		Whisky bowmore = new Whisky("Bowmore 15 Years Laimrig", "Scotland, Islay");
		products.put(bowmore.getId(), bowmore);
		Whisky talisker = new Whisky("Talisker 57° North", "Scotland, Island");
		products.put(talisker.getId(), talisker);
	}

	/**
	 * insert into the table whisky
	 * @param whisky
	 * @param connection
	 * @param next
	 */
	private void insert(Whisky whisky, SQLConnection connection, Handler<AsyncResult<Whisky>> next) {
		String sql = "INSERT INTO Whisky (name, origin) VALUES ?, ?";
		connection.updateWithParams(sql,
				new JsonArray().add(whisky.getName()).add(whisky.getOrigin()),
				(ar) -> {
					if (ar.failed()) {
						next.handle(Future.failedFuture(ar.cause()));
						return;
					}
					UpdateResult result = ar.result();
					// Build a new whisky instance with the generated id.
					Whisky w = new Whisky(result.getKeys().getInteger(0), whisky.getName(), whisky.getOrigin());
					next.handle(Future.succeededFuture(w));
				});
	}

	/**
	 * 
	 * @param routingContext
	 */
	private void getAll(RoutingContext routingContext) {
		routingContext.response()
		.putHeader("content-type", "application/json; charset=utf-8")
		.end(Json.encodePrettily(products.values()));
	}

	/**
	 * 
	 * @param routingContext
	 */
	private void addOne(RoutingContext routingContext) {
		final Whisky whisky = Json.decodeValue(routingContext.getBodyAsString(),
				Whisky.class);
		products.put(whisky.getId(), whisky);
		routingContext.response()
		.setStatusCode(201)
		.putHeader("content-type", "application/json; charset=utf-8")
		.end(Json.encodePrettily(whisky));
	}

	/**
	 * 
	 * @param routingContext
	 */
	private void deleteOne(RoutingContext routingContext) {
		String id = routingContext.request().getParam("id");
		if (id == null) {
			routingContext.response().setStatusCode(400).end();
		} else {
			Integer idAsInteger = Integer.valueOf(id);
			products.remove(idAsInteger);
		}
		routingContext.response().setStatusCode(204).end();
	}
}
